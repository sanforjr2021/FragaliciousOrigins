package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import static com.github.sanforjr2021.util.ConfigHandler.*;

public class Phantom extends Origin {
    //Config Settings
    private static double MAX_HEALTH;
    //instance variables
    private int ghostCooldownRemaining, ghostTimeRemaining, lightExposure;
    public Phantom(Player player) {
        super(OriginType.PHANTOM, player);
        ghostCooldownRemaining = 0;
        ghostTimeRemaining = 0;
        lightExposure = 0;
    }

    @Override
    public void onLogin() {
        Player player = getPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        PlayerUtils.setMaxHealth(player, MAX_HEALTH);
        PlayerUtils.removeEffect(player, PotionEffectType.DARKNESS);
    }

    @Override
    public void onDeath() {
        Player player = getPlayer();
        PlayerUtils.removeEffect(player, PotionEffectType.DARKNESS);
        PlayerUtils.setMaxHealth(player, MAX_HEALTH);
    }

    public static void reload() {
        MAX_HEALTH = getPhantomHealth();
    }

    public static double getMAX_HEALTH() {
        return  MAX_HEALTH;
    }

    public int getGhostCooldownRemaining() {
        return ghostCooldownRemaining;
    }

    public int getGhostTimeRemaining() {
        return ghostTimeRemaining;
    }

    public void setGhostCooldownRemaining(int ghostCooldownRemaining) {
        if (ghostCooldownRemaining < 0) {
            this.ghostCooldownRemaining = 0;
        } else {
            this.ghostCooldownRemaining = ghostCooldownRemaining;
        }
    }

    public void setGhostTimeRemaining(int ghostTimeRemaining) {
        if (ghostTimeRemaining < 0) {
            this.ghostTimeRemaining = 0;
        } else {
            this.ghostTimeRemaining = ghostTimeRemaining;
        }
    }
    public void setLightExposure(int lightExposure) {
        if (lightExposure < 0) {
            this.lightExposure = 0;
        } else {
            this.lightExposure = lightExposure;
        }
    }
    public int getLightExposure(){
        return lightExposure;
    }
}
