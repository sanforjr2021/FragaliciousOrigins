package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class Chicken extends Origin {
    private static float SPEED;
    private int spawnChickenCooldown;
    public Chicken(Player player) {
        super(OriginType.CHICKEN, player);
        PlayerUtils.setWalkSpeed(player, SPEED);
        spawnChickenCooldown = 0;
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {

    }
    public static void reload(){
        SPEED = (float) ConfigHandler.getChickenSpeed();
    }

    public int getSpawnChickenCooldown() {
        return spawnChickenCooldown;
    }

    public void setSpawnChickenCooldown(int spawnChickenCooldown) {
        this.spawnChickenCooldown = spawnChickenCooldown;
    }
}
