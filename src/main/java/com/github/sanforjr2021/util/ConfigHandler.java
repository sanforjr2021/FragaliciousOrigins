package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
    private FileConfiguration config;
    private static String jdbcURL, bedrockPrefix, msgPrefix;
    private static int phantomGhostmodeCooldown, phantomGhostModeDuration, phantomLightExposuretime, phantomFireResistDamageChance;
    private static double phantomGhostmodeSpeed, phantomHealth;

    public ConfigHandler(FragaliciousOrigins instance) {
        config = instance.getConfig();
        buildConfig(instance);
        loadConfig();
    }

    private void buildConfig(FragaliciousOrigins instance) {
        config.addDefault("#######################General Settings#####################","");
        config.addDefault("JDBC URL", "url");
        config.addDefault("Bedrock Prefix", ".");
        config.addDefault("Message Prefix", "&b[&cFragalicious Origins&b]&r");
        config.addDefault("#######################Phantom Settings#####################","");
        config.addDefault("Phantom Ghostmode Cooldown", 60);
        config.addDefault("Phantom Ghostmode Duration", 15);
        config.addDefault("Phantom Ghostmode Speed", 0.07);
        config.addDefault("Phantom Health", 16);
        config.addDefault("Phantom Light Exposure Time", 10);
        config.addDefault("Fire Resist Damage Chance", 40);
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
        phantomFireResistDamageChance = config.getInt("Fire Resist Damage Chance");
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
}
