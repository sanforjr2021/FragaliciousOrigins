package com.github.sanforjr2021.ability.individual;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class FelineAbilities extends Ability {
    private static double LEAP_MULTIPLIER;
    public static void leapAbility(PlayerJumpEvent e){
        Player player = e.getPlayer();
        Vector jumpVelocity = player.getVelocity();
        jumpVelocity.setY(jumpVelocity.getY()*LEAP_MULTIPLIER);
        player.setVelocity(jumpVelocity);
    }

    public static void nineLivesAbility(PlayerDeathEvent e) {
        if(new Random().nextInt(9) == 0){
            e.deathMessage(MessageUtil.generateComponenet(e.getPlayer().getName() + "escaped death with one of their 9 lives."));
            e.getPlayer().setHealth(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            e.setCancelled(true);
        }
    }
    public static void noFallDamageAbility(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            e.setCancelled(true);
        }
    }
    public static void weakMinerAbility(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if (isStone(block) && !player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            int count = 0;
            if (isStone(getBlockOffCenter(block, 0, 0, 1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, 0, -1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, 1, 0))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, -1, 1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 1, 0, 0))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, -1, 0, 0))) {
                count++;
            }
            if(count > 2){
                MessageUtil.sendMessage("You cannot mine stone with 2 adjacent stone", player);
                e.setCancelled(true);
            }
        }
    }
    public static void reload(){
        LEAP_MULTIPLIER = ConfigHandler.getFelineJumpMultiplier();
    }
    //Utility Abilities
    private static boolean isStone(Block block) {
        switch (block.getType()) {
            case STONE:
            case DIORITE:
            case DRIPSTONE_BLOCK:
            case POINTED_DRIPSTONE:
            case GRANITE:
            case ANDESITE:
            case SANDSTONE:
            case BASALT:
            case DEEPSLATE:
                return true;
            default:
                return false;
        }
    }

    private static Block getBlockOffCenter(Block block, int x, int y, int z) {
        int adjustedX = block.getLocation().getBlockX() + x;
        int adjustedY = block.getLocation().getBlockY() + y;
        int adjustedZ = block.getLocation().getBlockZ() + z;
        Location location = new Location(block.getWorld(), adjustedX, adjustedY, adjustedZ);
        return block.getWorld().getBlockAt(location);
    }
}
