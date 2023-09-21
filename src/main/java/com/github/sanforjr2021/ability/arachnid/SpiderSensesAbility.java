package com.github.sanforjr2021.ability.arachnid;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Arachnid;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class SpiderSensesAbility extends Ability {
    private static int RANGE, DURATION, COOLDOWN_MAX;

    public SpiderSensesAbility(Arachnid arachnid) {
        if(arachnid.getCooldown() > 0){
            this.sendCooldownMessage("Spider Senses", arachnid.getCooldown(),arachnid.getPlayer());
        }else{
            Player player = arachnid.getPlayer();
            player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f,2.0f);
            Iterator<Entity> entities = player.getLocation().getNearbyEntities(RANGE,RANGE,RANGE).iterator();
            while(entities.hasNext()){
                Entity entity = entities.next();
                if (entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    PlayerUtils.addEffect(livingEntity, PotionEffectType.GLOWING, 0,DURATION*20);
                }
            }
            arachnid.setCooldown(COOLDOWN_MAX);
            new BukkitRunnable() {
                @Override
                public void run() {
                    arachnid.setCooldown(arachnid.getCooldown()-1);
                    if(arachnid.getCooldown() == 0){
                        cancel();
                    }
                }
            }.runTaskTimer(FragaliciousOrigins.getInstance(), 20l, 20l);
        }
    }

    public static void reload(){
        RANGE = ConfigHandler.getArachnidSpiderSenseRange();
        DURATION = ConfigHandler.getArachnidSpiderSenseDuraion();
        COOLDOWN_MAX = ConfigHandler.getArachnidSpiderSenseCooldown();
    }
}
