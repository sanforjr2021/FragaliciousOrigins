package com.github.sanforjr2021.ability.phantom;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Phantom;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import com.github.sanforjr2021.util.time.TimeCycle;
import com.github.sanforjr2021.util.time.TimeUtil;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static com.github.sanforjr2021.util.ConfigHandler.getPhantomFireResistDamageChance;
import static com.github.sanforjr2021.util.ConfigHandler.getPhantomLightExposuretime;

public class ShadowSkinAbility extends Ability {
    private static int LIGHT_EXPOSURE_LIMIT;
    private static int FIRE_RESIST_DAMAGE_CHANCE;
    private static final Random random = new Random();
    public ShadowSkinAbility(Phantom phantom) {
        Player player = phantom.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        Block block = player.getLocation().getBlock();
        int lightExposure = phantom.getLightExposure();
        if (TimeUtil.getCurrentTime(player) == TimeCycle.DAY && block.getLightFromSky() > 10 && !PlayerUtils.isWet(player)) {
            //burn in day if past exposure limit
            if (lightExposure >= LIGHT_EXPOSURE_LIMIT) {
                if (player.getFireTicks() < 5 && !player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    player.setFireTicks(30);
                }
                if(generateChance()){
                    player.damage(2.0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_BURN, 3.0f, 3.0f);
                }
                //increase exposure limit
            } else {
                phantom.setLightExposure(lightExposure + 5);
            }
        } else {
            //update exposure
            if (lightExposure > 0) {
                phantom.setLightExposure(lightExposure - 1);
            }
            //grant invisibility if in true darkness
            if (block.getLightLevel() == 0 && !player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                PlayerUtils.addEffect(player, PotionEffectType.INVISIBILITY, 0);
                PlayerUtils.addEffect(player, PotionEffectType.NIGHT_VISION, 0);
            //Remove when out of darkness
            }else if(player.hasPotionEffect(PotionEffectType.INVISIBILITY) && block.getLightLevel() > 0){
                PlayerUtils.removeEffect(player, PotionEffectType.INVISIBILITY);
                PlayerUtils.removeEffect(player, PotionEffectType.NIGHT_VISION);
            }
        }
        //update boss bar
        if (OriginBossBarManager.geOriginBossBar(phantom.getPlayer().getUniqueId(), BossBarType.LIGHT_EXPOSURE) == null) {
            //If boss bar does not exist and are in sunlight, create the bar
            if (phantom.getLightExposure() > 0) {
                OriginBossBar bossBar = new OriginBossBar(phantom.getPlayer(), BossBarType.LIGHT_EXPOSURE, "Sunlight Exposure", BarColor.GREEN, 0.0);
                bossBar.setProgressInverse(phantom.getLightExposure(), LIGHT_EXPOSURE_LIMIT);
                OriginBossBarManager.addBossBar(phantom.getUuid(), bossBar);
            }
        } else {
            //if bar does exist and has light, update their view.
            if (phantom.getLightExposure() > 0) {
                OriginBossBar bar = OriginBossBarManager.geOriginBossBar(phantom.getUuid(), BossBarType.LIGHT_EXPOSURE);
                bar.setProgressInverse(phantom.getLightExposure(), LIGHT_EXPOSURE_LIMIT);
                bar.setColor(calculateBarColor(bar.getProgress()));
                //if bar exist but has 0 light exposure, remove the bar.
            } else {
                OriginBossBarManager.removeBossBar(phantom.getUuid(), BossBarType.LIGHT_EXPOSURE);
            }
        }
    }

    public static void reload() {
        LIGHT_EXPOSURE_LIMIT = getPhantomLightExposuretime() * 20;
        FIRE_RESIST_DAMAGE_CHANCE = getPhantomFireResistDamageChance();
    }

    private BarColor calculateBarColor(double progress) {
        if (progress > 0.66) {
            return BarColor.GREEN;
        } else if (progress > 0.33) {
            return BarColor.YELLOW;
        } else {
            return BarColor.RED;
        }
    }
    private boolean generateChance(){
        return random.nextInt(FIRE_RESIST_DAMAGE_CHANCE) == 0;
    }

}
