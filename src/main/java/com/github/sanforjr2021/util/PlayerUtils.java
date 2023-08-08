package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Attr;

import java.util.Collection;
import java.util.UUID;

public class PlayerUtils {
    public static Player getPlayer(UUID uuid) {
        return FragaliciousOrigins.getInstance().getServer().getPlayer(uuid);
    }

    public static boolean isBedrock(Player player) {
        return player.getName().startsWith(ConfigHandler.getBedrockPrefix());
    }

    /**********************************************************************************************
     ********************************** ATTRIBUTE ADJUSTMENTS *************************************
     **********************************************************************************************/

    public static void setMaxHealth(Player player, double maxHealth) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(maxHealth);

    }

    public static void resetMaxHealth(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(20.0D);
    }

    public static void setArmor(Player player, double armor) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ARMOR);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + armor);
        player.updateInventory();
    }

    public static void resetArmor(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ARMOR);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
        player.updateInventory();
    }

    public static void setWalkSpeed(Player player, float walkSpeed) {
        player.setWalkSpeed(walkSpeed);
    }

    public static void resetWalkSpeed(Player player) {
        player.setWalkSpeed(0.2f);
    }

    public static void setFlySpeed(Player player, float flySpeed) {
        player.setFlySpeed(flySpeed);
    }

    public static void resetFlySpeed(Player player) {
        player.setFlySpeed(0.1f);
    }

    public static void setSaturation(Player player, float saturation) {
        player.setSaturation(saturation);
    }

    public static void setHunger(Player player, int food) {
        player.setFoodLevel(food);
    }

    public static void addEffect(Player player, PotionEffectType effectType, int amplifier, int duration){
        player.addPotionEffect(new PotionEffect(effectType, amplifier, duration, false, false, false));

    }

    public static void addEffect(Player player, PotionEffectType effectType,int amplifier){
        player.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, amplifier, true, true, true));

    }
    public static void removeEffect(Player player, PotionEffectType effectType){
        player.removePotionEffect(effectType);
    }
    public static void removeEffects(Player player){
        Collection<PotionEffect> potionEffectCollection = player.getActivePotionEffects();
        for ( PotionEffect effect : potionEffectCollection ){
            player.removePotionEffect(effect.getType());
        }
    }
    public static void resetSleep(Player player){
        player.setStatistic(Statistic.TIME_SINCE_REST, 0);
    }

}
