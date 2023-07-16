package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
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
        return player.getName().startsWith(".");
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
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
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

    public static void setWalkSpeed(Player player, double walkSpeed) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeInstance.setBaseValue(walkSpeed);
    }

    public static void resetWalkSpeed(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
    }

    public static void setFlySpeed(Player player, double flySpeed) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_FLYING_SPEED);
        attributeInstance.setBaseValue(flySpeed);
    }

    public static void resetFlySpeed(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_FLYING_SPEED);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
    }

    public static void setSaturation(Player player, float saturation) {
        player.setSaturation(saturation);
    }

    public static void setHunger(Player player, int food) {
        player.setFoodLevel(food);
    }

    public static void addEffect(Player player, PotionEffectType effectType,int amplifier, int duration){
        PotionEffect potionEffect = new PotionEffect(effectType, amplifier, duration, false, false, false);
        player.addPotionEffect(potionEffect);
    }

    public static void addEffect(Player player, PotionEffectType effectType,int amplifier){
        PotionEffect potionEffect = new PotionEffect(effectType,amplifier, Integer.MAX_VALUE, false, false, false);
        player.addPotionEffect(potionEffect);
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

}
