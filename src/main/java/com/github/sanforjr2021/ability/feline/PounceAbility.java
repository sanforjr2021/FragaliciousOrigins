package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.origins.Feline;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class PounceAbility extends Ability {
    private static int POUNCE_COOLDOWN;
    public PounceAbility(Player player){
        Feline origin = (Feline) PlayerManager.getOrigin(player.getUniqueId());
        if(origin.getPounceCooldown() > 0){
            sendCooldownMessage("Pounce", origin.getPounceCooldown(), player);
        }else{
            Vector lookDirection = player.getEyeLocation().getDirection();
            if(player.getLocation().getPitch() < -5){
                player.setVelocity(lookDirection.multiply(2.5));
                PlayerUtils.addEffect(player, PotionEffectType.INCREASE_DAMAGE, 2, 60);
                origin.setPounceCooldown(POUNCE_COOLDOWN);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try{
                            origin.setPounceCooldown(origin.getPounceCooldown()-1);
                            if(origin.getPounceCooldown() <= 0){
                                cancel();
                            }
                        }catch (Exception e){
                            origin.setPounceCooldown(0);
                            cancel();
                        }
                    }
                }.runTaskTimer(FragaliciousOrigins.getInstance(), 20l, 20l);
            }

        }
    }


    public static void reload() {
        POUNCE_COOLDOWN = ConfigHandler.getFelinePounceCooldown();
    }
}
