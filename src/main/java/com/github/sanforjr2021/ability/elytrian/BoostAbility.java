package com.github.sanforjr2021.ability.elytrian;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Elytrian;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BoostAbility extends Ability {
    private static int COOLDOWN_MAX;
    private static double GLIDE_BOOST,GROUND_BOOST, MIN_BOOST_SPEED;

    public BoostAbility(Elytrian elytrian) {
        Player player = elytrian.getPlayer();
        if(elytrian.getCooldown() > 0 ){ //when on cooldown
            sendCooldownMessage("Boost",elytrian.getCooldown(),player);
            return;
        }else if(player.isGliding()){ //while flying
            Vector velocity = setMinVelocity(player);
            player.setVelocity(velocity);
            PlayerUtils.generateParticle(player, Particle.CLOUD, 50);
            PlayerUtils.generateParticle(player,Particle.SMOKE_NORMAL,20);
            player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f,1.0f);
        }else if(!player.isSleeping() && !player.isSwimming() && !player.isClimbing()){ //while sleeping
            player.setVelocity(new Vector(0, GROUND_BOOST, 0));
            PlayerUtils.generateParticle(player, Particle.CLOUD, 50);
            PlayerUtils.generateParticle(player,Particle.SMOKE_NORMAL,20);
            player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f,1.0f);
            new BukkitRunnable() {
                @Override
                public void run() {
                    elytrian.getPlayer().setGliding(true);
                    cancel();
                }
            }.runTaskTimer(FragaliciousOrigins.getInstance(), 5l, 5l);
        }else{
            return;
        }
        //timer
        elytrian.setCooldown(COOLDOWN_MAX);
        new BukkitRunnable() {
            @Override
            public void run() {
                elytrian.setCooldown(elytrian.getCooldown()-1);
                if(elytrian.getCooldown() == 0){
                    cancel();
                }
            }
        }.runTaskTimer(FragaliciousOrigins.getInstance(), 20l, 20l);
    }
    public static void reload(){
        COOLDOWN_MAX = ConfigHandler.getElytrianCooldown();
        GLIDE_BOOST = ConfigHandler.getElytrianFlyingVelocityAbilityMultiplier();
        GROUND_BOOST = ConfigHandler.getElytrianGroundVerticalVelocityAbilityMultiplier();
        MIN_BOOST_SPEED = ConfigHandler.getElytrianMinBoostSpeed();
    }
    private static Vector setMinVelocity( Player player) {
        Vector playerVector = player.getVelocity();
        double x = playerVector.getX();
        double y = playerVector.getY();
        double z = playerVector.getZ();
        double speed = Math.abs(x) + Math.abs(y) + Math.abs(z);
        if(speed < MIN_BOOST_SPEED){
            return player.getEyeLocation().getDirection().multiply(MIN_BOOST_SPEED);
        }
        return player.getEyeLocation().getDirection().multiply(speed * GLIDE_BOOST);
    }
}
