package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class Shulk extends Origin{
    private static double ARMOR,TOUGHNESS,KNOCKBACK_RESIST;
    private static float SPEED;
    private boolean levitationActive;
    public Shulk(Player player) {
        super(OriginType.SHULK, player);
        PlayerUtils.setKnockbackResistance(player,KNOCKBACK_RESIST);
        PlayerUtils.setArmor(player,ARMOR);
        PlayerUtils.setToughness(player,TOUGHNESS);
        PlayerUtils.setWalkSpeed(player, SPEED);
        levitationActive = false;
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {

    }

    public static void reload(){
        KNOCKBACK_RESIST = ConfigHandler.getShulkKnockback();
        TOUGHNESS = ConfigHandler.getShulkToughness();
        ARMOR = ConfigHandler.getShulkArmor();
        SPEED = (float) ConfigHandler.getShulkSpeed();
    }

    public boolean isLevitationActive() {
        return levitationActive;
    }

    public void setLevitationActive(boolean levitationActive) {
        this.levitationActive = levitationActive;
    }
}
