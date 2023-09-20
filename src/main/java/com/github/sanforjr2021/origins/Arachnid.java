package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class Arachnid extends Origin{
    private boolean climbActive;
    private int cooldown;
    public Arachnid( Player player) {
        super(OriginType.ARACHNID, player);
        cooldown = 0;
        climbActive = true;
        PlayerUtils.setMaxHealth(player,16);
        getPlayer().setAllowFlight(false);
        getPlayer().setFlying(false);
        PlayerUtils.resetFlySpeed(getPlayer());
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {
        PlayerUtils.setMaxHealth(getPlayer(),16);
        getPlayer().setAllowFlight(false);
        getPlayer().setFlying(false);
        PlayerUtils.resetFlySpeed(getPlayer());
    }

    public boolean isClimbActive() {
        return climbActive;
    }

    public void setClimbActive(boolean climbActive) {
        this.climbActive = climbActive;
        if(!climbActive){
            PlayerUtils.resetFlySpeed(getPlayer());
            getPlayer().setFlying(false);
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        if(cooldown < 0 ){
            cooldown = 0;
        }
        this.cooldown = cooldown;
    }
}
