package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Elytrian extends Origin{
    private int cooldown;
    private boolean wingsActive;
    private static float SPEED_MODIFIER;
    private static float ATTACK_SPEED_MODIFIER;
    private float armor;
    public Elytrian( Player player) {
        super(OriginType.ELYTRIAN, player);
        wingsActive = true;
        cooldown = 0;
        armor = (float) getPlayer().getAttribute(Attribute.GENERIC_ARMOR).getValue();
        updateArmorSpeedPenalty();
        PlayerUtils.setAttackSpeed(player, 4.0f * ATTACK_SPEED_MODIFIER);
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {

        updateArmorSpeedPenalty();
    }
    public void updateArmorSpeedPenalty(){
        if(armor == (float) getPlayer().getAttribute(Attribute.GENERIC_ARMOR).getValue()){
            return; //do nothing
        }
        armor = (float) getPlayer().getAttribute(Attribute.GENERIC_ARMOR).getValue();
        if(armor == 0){
            PlayerUtils.resetWalkSpeed(getPlayer());
        }else{
           PlayerUtils.setWalkSpeed(getPlayer(), 0.2f - (SPEED_MODIFIER * armor));
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        if(cooldown<0){
            cooldown=0;
        }
        this.cooldown = cooldown;
    }

    public boolean isWingsActive() {
        return wingsActive;
    }

    public void setWingsActive(boolean wingsActive) {
        this.wingsActive = wingsActive;
    }

    public static void reload(){
        SPEED_MODIFIER = (float) ConfigHandler.getElytrianArmorSpeedModifer();
        ATTACK_SPEED_MODIFIER = (float) ConfigHandler.getElytrianAttackSpeedModifer();
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }
}
