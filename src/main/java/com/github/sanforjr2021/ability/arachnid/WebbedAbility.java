package com.github.sanforjr2021.ability.arachnid;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class WebbedAbility extends Ability {
    private static double WEB_CHANCE;
    private static int DURATION, AMPLIFIER;

    public WebbedAbility(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof LivingEntity){
            LivingEntity target = (LivingEntity) e.getEntity();
            if(WEB_CHANCE > Math.random()){
                PlayerUtils.addEffect(target, PotionEffectType.POISON,AMPLIFIER, DURATION);
                Block block = target.getLocation().getBlock();
                //If block is air, temporarily set to a cobweb
                if(target.getLocation().getBlock().isEmpty()){
                    block.setType(Material.COBWEB);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            block.setType(Material.AIR);
                            cancel();
                        }
                    }.runTaskTimer(FragaliciousOrigins.getInstance(), DURATION, 20l);
                }
            }
        }
    }

    public static void reload(){
        WEB_CHANCE = ConfigHandler.getArachnidWebChance();
        DURATION = ConfigHandler.getArachnidWebDuration();
        AMPLIFIER = ConfigHandler.getArachnidWebAmplifier();
    }
}
