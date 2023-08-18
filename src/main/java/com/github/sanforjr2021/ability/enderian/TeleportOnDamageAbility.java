package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.origins.Enderian;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class TeleportOnDamageAbility extends Ability {
    private static int TELEPORT_RANGE;
    private static int TELEPORT_ATTEMPTS;
    public TeleportOnDamageAbility(EntityDamageEvent e) {
        Player player = (Player) e.getEntity();
        Enderian origin = (Enderian) PlayerManager.getOrigin(player.getUniqueId());
        //if invulnerable
        if(origin.isTeleportInvulnerability()){
            e.setCancelled(true);
        //if not invernable check to see toggle
        } else if (!player.isDead() && e.getCause() != EntityDamageEvent.DamageCause.FALL
                && origin.isTeleportOnDamage() && e.getDamage() > 0.1) {
            teleportRandomly(player);
        }
    }

    private void teleportRandomly(Player player) {
        for (int i = 0; i < TELEPORT_ATTEMPTS; i++) {
            Location newLocation = generateRandomLocation(player.getLocation());
            newLocation = findSafeLocation(newLocation);
            if (newLocation != null) {
                player.teleport(newLocation);
                player.playEffect(EntityEffect.TELEPORT_ENDER);
                return;
            }
        }
        // If no safe location found after 20 tries, teleport back to original location
    }

    private Location generateRandomLocation(Location center) {
        Random random = new Random();
        double angle = Math.random() * Math.PI * 2;
        double distanceX = random.nextDouble() * TELEPORT_RANGE;
        double distanceY = random.nextDouble() * TELEPORT_RANGE;
        double x = center.getX() + distanceX * Math.cos(angle);
        double z = center.getZ() + distanceY * Math.sin(angle);
        double y = center.getWorld().getHighestBlockYAt((int) x, (int) z) + 1;
        return new Location(center.getWorld(), x, y, z);
    }

    private Location findSafeLocation(Location location) {
        // Check blocks above head and below feet
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Block headBlock = world.getBlockAt(x, y + 2, z);
        Block feetBlock = world.getBlockAt(x, y - 1, z);
        Block belowBlock = world.getBlockAt(x, y - 2, z);
        if (headBlock.getType().isAir() && feetBlock.getType().isAir() && belowBlock.getType().isSolid()) {
            return new Location(world, x + 0.5, y, z + 0.5, location.getYaw(), location.getPitch());
        }
        // If not safe, search for a safe location nearby
        for (int yOffset = -1; yOffset <= 3; yOffset++) {
            Block candidateBlock = world.getBlockAt(x, y - yOffset, z);
            Block canidateFeetBLock = world.getBlockAt(x, y - yOffset + 1, z);
            Block canidateHeadBlock = world.getBlockAt(x, y - yOffset + 2, z);
            if (candidateBlock.getType().isSolid() && canidateHeadBlock.getType().isAir() && canidateFeetBLock.getType().isAir()) {
                return new Location(world, x + 0.5, y - yOffset + 1, z + 0.5, location.getYaw(), location.getPitch());
            }
        }
        return null; // No safe location found
    }
    public static void reload(){
        TELEPORT_RANGE = ConfigHandler.getEnderianDamageTeleportRange();
        TELEPORT_ATTEMPTS = ConfigHandler.getEnderianDamageTeleportAttempts();
    }

}
