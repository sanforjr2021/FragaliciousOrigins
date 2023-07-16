package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
    private FileConfiguration config;
    private static String jdbcURL, bedrockPrefix, msgPrefix;
    private static int phantomGhostmodeCooldown, phantomGhostModeDuration;

    public ConfigHandler(FragaliciousOrigins instance) {
        config = instance.getConfig();
        buildConfig(instance);
        loadConfig();
    }

    private void buildConfig(FragaliciousOrigins instance) {
        config.addDefault("############################################################","");
        config.addDefault("#######################General Settings#####################","");
        config.addDefault("############################################################","");
        config.addDefault("\n##This is the database connection URL to connect to the server","");
        config.addDefault("JDBC URL", "url");
        config.addDefault("\n##This is a delimiter to identify bedrock clients if you have geyser enabled","");
        config.addDefault("Bedrock Prefix", ".");
        config.addDefault("\n##This is a prefix added to all messages sent to users from the plugin","");
        config.addDefault("Message Prefix", "&b[&cFragalicious Origins&b]&r");
        config.addDefault("", "&b[&cFragalicious Origins&b]&r");
        config.addDefault("\n############################################################","");
        config.addDefault("#######################Phantom Settings#####################","");
        config.addDefault("############################################################\n","");
        config.addDefault("\n##This is the phantom's ghostmode cooldown in seconds. The default is 60","");
        config.addDefault("Phantom Ghostmode Cooldown", 60);
        config.addDefault("\n##This is the phantom's ghostmode duration in seconds. The default is 15","");
        config.addDefault("Phantom Ghostmode Duration", 15);
        config.options().copyDefaults(true);
        instance.saveConfig();
    }

    public void loadConfig() {
        jdbcURL = config.getString("JDBC URL");
        bedrockPrefix = config.getString("Bedrock Prefix");
        msgPrefix = config.getString("Message Prefix");
        phantomGhostmodeCooldown = config.getInt("Phantom Ghostmode Cooldown");
        phantomGhostModeDuration = config.getInt("Phantom Ghostmode Duration");
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

}
