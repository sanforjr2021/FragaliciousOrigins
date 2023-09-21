package com.github.sanforjr2021.ability.elytrian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Elytrian;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ElytrianMovementAbility extends Ability {
    private static int CEILING_LIMIT,SLOWNESS_AMPLIFIER, CURRENT_HEIGHT_MIN;
    private static double AIR_CURRENT_CHANCE, AIR_CURRENT_MULTIPLIER;

    public ElytrianMovementAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Elytrian elytrian = (Elytrian) PlayerManager.getOrigin(player.getUniqueId());
        Location location = player.getLocation();
        if(player.isGliding() && !player.getAllowFlight()){
            //check if catch current
            if(player.getWorld().getHighestBlockYAt(location) < location.getY() &&
                    location.getY() > CURRENT_HEIGHT_MIN && Math.random() < AIR_CURRENT_CHANCE
                    && player.isGliding()){
                MessageUtil.sendMessage("You catch a wind tunnel", player);
                player.setVelocity(player.getVelocity().multiply(AIR_CURRENT_MULTIPLIER));
                player.playSound(player, Sound.ENTITY_HORSE_BREATHE, 1.0f,1.0f);
                PlayerUtils.generateParticle(player, Material.WHITE_WOOL,30);
            }
        }else if(PlayerUtils.isOnGround(player)){ //if not gliding and on the ground
            if(elytrian.isWingsActive()){
                player.setAllowFlight(true);
            }else if(player.getAllowFlight()){
                player.setAllowFlight(false);
            }
        }
        if(hasRoof(location)){
            if (!player.hasPotionEffect(PotionEffectType.SLOW)) {
                PlayerUtils.addEffect(player, PotionEffectType.SLOW, SLOWNESS_AMPLIFIER);
            }
        }else if(player.hasPotionEffect(PotionEffectType.SLOW)){
            PlayerUtils.removeEffect(player,PotionEffectType.SLOW);
        }
        elytrian.updateArmorSpeedPenalty();
    }

    private boolean hasRoof(Location location){
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        World world = location.getWorld();
        for(double i = 1; i < CEILING_LIMIT; i++){
            if(new Location(world, x ,y+i,z).getBlock().isSolid()){
                return true;
            }
        }
        return false;
    }
    public static void reload(){
        CEILING_LIMIT = ConfigHandler.getElytrianCeilingLimit();
        SLOWNESS_AMPLIFIER = ConfigHandler.getElytrianCeilingSlownessAmplifier();
        CURRENT_HEIGHT_MIN = ConfigHandler.getElytrianAirCurrentHeight();
        AIR_CURRENT_CHANCE = ConfigHandler.getElytrianAirCurrentChance();
        AIR_CURRENT_MULTIPLIER = ConfigHandler.getElytrianAirCurrentMultiplier();
    }
}
