package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class MerlingPassiveAbility extends Ability {
    private static int BREATH_MAX, CONDUIT_AMPLIFIER;
    private static double TRIDENT_MULTIPLIER;

    public MerlingPassiveAbility(Merling merling) {
        Player player = merling.getPlayer();
        //remove mining fatigue
        if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
        //apply conduit power when wet
        if (PlayerUtils.isWetIgnoringConduit(player) && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)) {
            PlayerUtils.addEffect(player, PotionEffectType.CONDUIT_POWER, CONDUIT_AMPLIFIER);
        //remove conduit power when not wet
        } else if (!PlayerUtils.isWetIgnoringConduit(player) && player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)) {
            player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
        }
        //Update Bossbar
        if (merling.getBreathTime() != BREATH_MAX || !PlayerUtils.isWet(player)) {
            if (OriginBossBarManager.geOriginBossBar(player.getUniqueId(), BossBarType.WATER_BREATH) == null) {
                OriginBossBarManager.addBossBar(player.getUniqueId(), new OriginBossBar(player, BossBarType.WATER_BREATH, ChatColor.BLUE + "Water Breath", BarColor.BLUE, 1.0));
            }
            OriginBossBar bar = OriginBossBarManager.geOriginBossBar(player.getUniqueId(), BossBarType.WATER_BREATH);
            int breath = merling.getBreathTime();
            //Update when in water
            if(PlayerUtils.isWet(player)){
                merling.setBreathTime(breath + 10);
                if(merling.getBreathTime() != BREATH_MAX){
                    bar.setProgress(merling.getBreathTime(),BREATH_MAX);
                }else{

                }
            }
            //update when out of water
            else if(breath> 0){
                merling.setBreathTime(breath - 1);
                bar.setProgress(merling.getBreathTime(),BREATH_MAX);
            }else{
                player.damage(2.0);
            }

        }
    }

    public static void reload() {
        BREATH_MAX = ConfigHandler.getMerlingBreathInSeconds();
        CONDUIT_AMPLIFIER = ConfigHandler.getMerlingConduitPowerAmplifier();
        TRIDENT_MULTIPLIER = ConfigHandler.getMerlingTridentDamageMultiplier();
    }

}
