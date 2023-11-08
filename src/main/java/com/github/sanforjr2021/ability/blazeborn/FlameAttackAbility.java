package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FlameAttackAbility extends Ability {
    public FlameAttackAbility(EntityDamageByEntityEvent e) {
        Blazeborn blazeborn = (Blazeborn) PlayerManager.getOrigin(e.getDamager().getUniqueId());
        Blazeborn.HeatLevel level = blazeborn.getLevel();
        if(e.isCancelled()){
            return;
        }
        if(e.getEntity() instanceof LivingEntity && e.getFinalDamage() > 0.01){
            LivingEntity entity = (LivingEntity) e.getEntity();
            //scorch target
            if(blazeborn.isScorching()){
                new BukkitRunnable(){
                    int timer = level.getDamageDuration();
                    @Override
                    public void run() {
                        timer--;
                        entity.damage(1.0);
                        PlayerUtils.generateParticle(entity, Material.SOUL_FIRE, 50);
                        if(timer <= 0 || entity.isDead()){
                            cancel();
                        }
                    }
                }.runTaskTimer(FragaliciousOrigins.getInstance(), 10l,20l);
            }
            //burn target
            if(level.getBurnChance() > Math.random()){
                entity.setFireTicks(level.getBurnDuration()*20);
            }
        }

    }
}
