package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PurgeAbility extends Ability {

    public PurgeAbility(Blazeborn blazeborn) {
        Player player = blazeborn.getPlayer();
        if(blazeborn.getAbilityCooldown() > 0){
            this.sendCooldownMessage("Purge", blazeborn.getAbilityCooldown(), player);
        }else{
            Blazeborn.HeatLevel level = blazeborn.getLevel();
            if(level.canPurgeAbility()){ //when purge active, cleanse negative ffects
                PlayerUtils.removeEffect(player, PotionEffectType.BLINDNESS);
                PlayerUtils.removeEffect(player, PotionEffectType.POISON);
                PlayerUtils.removeEffect(player, PotionEffectType.WITHER);
                PlayerUtils.removeEffect(player, PotionEffectType.DARKNESS);
                PlayerUtils.removeEffect(player, PotionEffectType.SLOW);
                PlayerUtils.removeEffect(player, PotionEffectType.HUNGER);
                PlayerUtils.removeEffect(player, PotionEffectType.WEAKNESS);
            }
            if(level.getAbilityDuration() > 0){ //enable scorching blade
                blazeborn.setScorching(true);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        blazeborn.setScorching(false);
                        cancel();
                    }
                }.runTaskTimer(FragaliciousOrigins.getInstance(), level.getAbilityDuration(),15);
            }
            blazeborn.setAbilityCooldown(level.getCooldown());
            blazeborn.setHeat(blazeborn.getHeat() + level.getAbilityHeat());
            if(level.getAbilityDuration() > 0){
                blazeborn.setAbilityCooldown(level.getCooldown());
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        blazeborn.setAbilityCooldown(blazeborn.getAbilityCooldown() - 1);
                        if(blazeborn.getAbilityCooldown() == 0){
                            cancel();
                        }
                    }
                }.runTaskTimer(FragaliciousOrigins.getInstance(), 20l,20l);
            }
        }
    }

    public static void reload(){

    }
}
