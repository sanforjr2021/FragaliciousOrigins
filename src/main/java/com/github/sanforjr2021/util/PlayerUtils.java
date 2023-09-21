package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.Conduit;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

    public static void setToughness(Player player, double toughness) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + toughness);
        player.updateInventory();
    }

    public static void resetToughness(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
        player.updateInventory();
    }

    public static void setKnockbackResistance(Player player, double knockbackResistance) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue() + knockbackResistance);
        player.updateInventory();
    }

    public static void resetKnockbackResistance(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
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

    public static void setAttackSpeed(Player player, float attackspeed){
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        attributeInstance.setBaseValue(attackspeed);
        player.updateInventory();
    }
    public static void resetAttackSpeed(Player player){
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        attributeInstance.setBaseValue(attributeInstance.getDefaultValue());
        player.updateInventory();
    }

    public static void addEffect(Player player, PotionEffectType effectType, int amplifier, int duration){
        player.addPotionEffect(new PotionEffect(effectType,duration , amplifier, false, false, false));
    }

    public static void addEffect(Player player, PotionEffectType effectType,int amplifier){
        player.addPotionEffect(new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, false, false, false));
    }

    public static void addEffect(LivingEntity entity, PotionEffectType effectType, int amplifier, int duration){
        entity.addPotionEffect(new PotionEffect(effectType,duration , amplifier, false, false, false));
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
    public static boolean isWet(Player player){
        return player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)
                || isWetIgnoringConduit(player);
    }
    public static boolean isWetIgnoringConduit(Player player){
        return (player.getWorld().hasStorm() && player.getWorld().getHighestBlockYAt(player.getLocation()) <= player.getLocation().getY()) || player.isInWater();
    }
    public static void generateParticle(LivingEntity entity, Particle particle, int count){
        entity.getWorld().spawnParticle(particle, entity.getLocation(), count, 0.5, 0.5, 0.5, 0.0);
    }
    public static void generateParticle(LivingEntity entity, Material material, int count){
        BlockData data = Bukkit.createBlockData(material);
        entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().add(0, 1, 0), count, 0.2, 0.2, 0.2, 0.0, data);
    }
    public static boolean isInConduitRange(Player player, int radius) {
        Location playerLocation = player.getLocation();
        int playerX = playerLocation.getBlockX();
        int playerY = playerLocation.getBlockY();
        int playerZ = playerLocation.getBlockZ();
        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.CONDUIT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isOnGround(Player player){
        Location location = player.getLocation();
        Location belowPlayer = new Location(player.getWorld(), location.getX(),location.getY()-1, location.getZ());
        return belowPlayer.getBlock().isSolid();
    }
    public static void resetSleep(Player player){
        player.setStatistic(Statistic.TIME_SINCE_REST, 0);
    }

}
