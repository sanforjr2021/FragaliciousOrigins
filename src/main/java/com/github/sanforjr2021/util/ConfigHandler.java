package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
    private FileConfiguration config;
    private static String jdbcURL, bedrockPrefix, msgPrefix;
    //Phantom
    private static int phantomGhostmodeCooldown, phantomGhostModeDuration, phantomLightExposuretime, phantomFireResistDamageChance;
    private static double phantomGhostmodeSpeed, phantomHealth;
    //Feline
    private static int felinePounceCooldown;
    private static double felineJumpMultiplier, felineSpeed;
    //Enderian
    private static int enderianMaxHealth, enderianTeleportCooldown, enderianTeleportDistance, enderianDamageTeleportRange, enderianDamageTeleportAttempts;
    //Shulk
    private static double shulkToughness,shulkArmor,shulkKnockback,shulkSpeed,shulkFoodLossChance, shulkSelfLevitationChance, shulkTargetLevitationChance;
    private static double chickenHungerCancel, chickenSpeed;
    private static int chickenSpawnBirdCooldown;
    public ConfigHandler(FragaliciousOrigins instance) {
        config = instance.getConfig();
        buildConfig(instance);
        loadConfig();
    }




    private void buildConfig(FragaliciousOrigins instance) {
        config.addDefault("#######################General Settings#####################", "");
        config.addDefault("JDBC URL", "url");
        config.addDefault("Bedrock Prefix", ".");
        config.addDefault("Message Prefix", "&b[&cFragalicious Origins&b]&r");
        config.addDefault("#######################Phantom Settings#####################", "");
        config.addDefault("Phantom Ghostmode Cooldown", 60);
        config.addDefault("Phantom Ghostmode Duration", 15);
        config.addDefault("Phantom Ghostmode Speed", 0.07);
        config.addDefault("Phantom Health", 16);
        config.addDefault("Phantom Light Exposure Time", 10);
        config.addDefault("Phantom Fire Resist Damage Chance", 20);
        config.addDefault("########################Feline Settings######################", "");
        config.addDefault("Feline Jump Multiplier", 3.1);
        config.addDefault("Feline Speed", 0.4);
        config.addDefault("Feline Pounce Cooldown", 30);
        config.addDefault("#######################Enderian Settings#####################", "");
        config.addDefault("Enderian Max Health", 24);
        config.addDefault("Enderian Teleport Cooldown", 10);
        config.addDefault("Enderian Teleport Distance", 15);
        config.addDefault("Enderian Damage Teleport Attempts", 30);
        config.addDefault("Enderian Danage Teleport Range", 15);
        config.addDefault("#######################Shulk Settings#####################", "");
        config.addDefault("Shulk Armor", 10.0);
        config.addDefault("Shulk Toughness", 4.0);
        config.addDefault("Shulk Knockback", 0.5);
        config.addDefault("Shulk Speed", 0.18);
        config.addDefault("Shulk Food Loss Chance", 0.2);
        config.addDefault("Shulk Self Levitation Chance", 0.2);
        config.addDefault("Shulk Other Levitation Chance", 0.1);
        config.addDefault("######################Chicken Settings#####################", "");
        config.addDefault("Chicken Speed", 0.35);
        config.addDefault("Chicken Hunger Cancel Chance", 0.2);
        config.addDefault("Chicken Spawn Bird Cooldown",180);
        config.options().copyDefaults(true);
        instance.saveConfig();
    }

    public void loadConfig() {
        jdbcURL = config.getString("JDBC URL");
        bedrockPrefix = config.getString("Bedrock Prefix");
        msgPrefix = config.getString("Message Prefix");
        phantomGhostmodeCooldown = config.getInt("Phantom Ghostmode Cooldown");
        phantomGhostModeDuration = config.getInt("Phantom Ghostmode Duration");
        phantomGhostmodeSpeed = config.getDouble("Phantom Ghostmode Speed");
        phantomHealth = config.getInt("Phantom Health");
        phantomLightExposuretime = config.getInt("Phantom Light Exposure Time");
        phantomFireResistDamageChance = config.getInt("Phantom Fire Resist Damage Chance");
        felineJumpMultiplier = config.getDouble("Phantom Light Exposure Time");
        felineSpeed = config.getDouble("Feline Speed");
        felinePounceCooldown = config.getInt("Feline Pounce Cooldown");
        enderianMaxHealth = config.getInt("Enderian Max Health");
        enderianTeleportCooldown = config.getInt("Enderian Teleport Cooldown");
        enderianTeleportDistance = config.getInt("Enderian Teleport Distance");
        enderianDamageTeleportAttempts = config.getInt("Enderian Damage Teleport Attempts");
        enderianDamageTeleportRange = config.getInt("Enderian Danage Teleport Range");
        shulkArmor = config.getDouble("Shulk Armor");
        shulkKnockback = config.getDouble("Shulk Knockback");
        shulkToughness = config.getDouble("Shulk Toughness");
        shulkSpeed = config.getDouble("Shulk Speed");
        shulkFoodLossChance = config.getDouble("Shulk Food Loss Chance");
        shulkSelfLevitationChance = config.getDouble("Shulk Self Levitation Chance");
        shulkTargetLevitationChance = config.getDouble("Shulk Other Levitation Chance");
        chickenSpeed = config.getDouble("Chicken Speed");
        chickenHungerCancel = config.getDouble("Chicken Hunger Cancel Chance");
        chickenSpawnBirdCooldown = config.getInt("Chicken Spawn Bird Cooldown");
    }

    public static String getJdbcURL() {
        return jdbcURL;
    }

    public static String getBedrockPrefix() {
        return bedrockPrefix;
    }

    public static String getMsgPrefix() {
        return msgPrefix;
    }

    public static int getPhantomGhostmodeCooldown() {
        return phantomGhostmodeCooldown;
    }

    public static int getPhantomGhostModeDuration() {
        return phantomGhostModeDuration;
    }

    public static double getPhantomHealth() {
        return phantomHealth;
    }

    public static double getPhantomGhostmodeSpeed() {
        return phantomGhostmodeSpeed;
    }

    public static int getPhantomLightExposuretime() {
        return phantomLightExposuretime;
    }

    public static int getPhantomFireResistDamageChance() {
        return phantomFireResistDamageChance;
    }

    public static int getFelinePounceCooldown() {
        return felinePounceCooldown;
    }

    public static double getFelineJumpMultiplier() {
        return felineJumpMultiplier;
    }

    public static double getFelineSpeed() {
        return felineSpeed;
    }

    public static int getEnderianMaxHealth() {
        return enderianMaxHealth;
    }

    public static int getEnderianTeleportDistance() {
        return enderianTeleportDistance;
    }

    public static int getEnderianTeleportCooldown() {
        return enderianTeleportCooldown;
    }

    public static int getEnderianDamageTeleportRange() {
        return enderianDamageTeleportRange;
    }

    public static int getEnderianDamageTeleportAttempts() {
        return enderianDamageTeleportAttempts;
    }

    public static double getShulkToughness() {
        return shulkToughness;
    }

    public static double getShulkArmor() {
        return shulkArmor;
    }

    public static double getShulkKnockback() {
        return shulkKnockback;
    }
    public static double getShulkSpeed() {
        return shulkSpeed;
    }

    public static double getShulkFoodLossChance() {
        return shulkFoodLossChance;
    }

    public static double getShulkSelfLevitationChance() {
        return shulkSelfLevitationChance;
    }

    public static double getShulkTargetLevitationChance() {
        return shulkTargetLevitationChance;
    }

    public static double getChickenHungerCancel() {
        return chickenHungerCancel;
    }

    public static double getChickenSpeed() {
        return chickenSpeed;
    }

    public static int getChickenSpawnBirdCooldown() {
        return chickenSpawnBirdCooldown;
    }
}
