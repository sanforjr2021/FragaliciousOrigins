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
    private static String blazeHeat1, blazeHeat2, blazeHeat3, blazeHeat4, blazeHeat5;
    private static String blazeEffect1, blazeEffect2, blazeEffect3, blazeEffect4, blazeEffect5;
    private static String blazeAbility1, blazeAbility2, blazeAbility3, blazeAbility4, blazeAbility5;
    private static String blazeFoodBlazePowder, blazeFoodBlazeRod, blazeFoodCoal, blazeFoodCoalBlock, blazeFoodCharcoal, blazeFoodFireCharge;
    private static int blazeOnFire, blazeStartingHeat;
    private static int merlingBreathInSeconds, merlingConduitPowerAmplifier, merlingTridentBreakDamage;
    private static double merlingVelocityMultiplier, merlingTridentDamageMultiplier;
    private static double arachnidVelocity,arachnidWebChance;
    private static int arachnidSpiderSenseRange,arachnidSpiderSenseDuraion,arachnidSpiderSenseCooldown,arachnidWebDuration, arachnidWebAmplifier;
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
        config.addDefault("Phantom Ghostmode Speed", 0.05);
        config.addDefault("Phantom Health", 16);
        config.addDefault("Phantom Light Exposure Time", 10);
        config.addDefault("Phantom Fire Resist Damage Chance", 20);
        config.addDefault("########################Feline Settings######################", "");
        config.addDefault("Feline Jump Multiplier", 3.1);
        config.addDefault("Feline Speed", 0.4);
        config.addDefault("Feline Pounce Cooldown", 40);
        config.addDefault("#######################Enderian Settings#####################", "");
        config.addDefault("Enderian Max Health", 24);
        config.addDefault("Enderian Teleport Cooldown", 10);
        config.addDefault("Enderian Teleport Distance", 15);
        config.addDefault("Enderian Damage Teleport Attempts", 20);
        config.addDefault("Enderian Damage Teleport Range", 15);
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
        config.addDefault("#####################Blazeborn Settings#####################", "");
        config.addDefault("Maximum Heat (int), Passive Drain (int), Water Drain (int), damageDrain (int), Passive Drains In Nether (boolean), Gain Heat when burning (boolean)", "");
        config.addDefault("Blazeborn Heat 1", "100,1,5,5,false,true");
        config.addDefault("Blazeborn Heat 2", "500,10,25,25,false,true");
        config.addDefault("Blazeborn Heat 3", "1000,25,50,75,false,false");
        config.addDefault("Blazeborn Heat 4", "5000,35,100,100,true,false");
        config.addDefault("Blazeborn Heat 5", "10000,50,150,115,true,false");
        config.addDefault("Health(int),Speed(double),Armor Bonus(double), Toughness (double),Regen (boolean),Burn Chance(double), Burn Duration in seconds (int)","");
        config.addDefault("Blazeborn Effects 1", "16,0.18,0.0,0.0,false,0.0,0");
        config.addDefault("Blazeborn Effects 2", "20,0.2,0.0,00.0,true,0.0,0");
        config.addDefault("Blazeborn Effects 3", "22,0.21,2.0,1.0,true,0.25,20");
        config.addDefault("Blazeborn Effects 4", "26,0.23,4.0,2.0,true,0.5,40");
        config.addDefault("Blazeborn Effects 5", "30,0.25,8.0,4.0,true,1.0,60");
        config.addDefault("Purge Ability(boolean), Ability Cooldown in seconds (int), Ability Heat Restore (int),  Ability Duration in seconds (int), Damage Duration in seconds (int)", "");
        config.addDefault("Blazeborn Ability 1", "false,30,0,0,0");
        config.addDefault("Blazeborn Ability 2", "true,180,60,0,0");
        config.addDefault("Blazeborn Ability 3", "true,120,100,1,1");
        config.addDefault("Blazeborn Ability 4", "true,90,150,2,2");
        config.addDefault("Blazeborn Ability 5", "true,60,300,3,3");
        config.addDefault("Blazeborn On Fire Boost",10);
        config.addDefault("Starting Heat",100);
        config.addDefault("Blazeborn Food Blaze Powder", "3,250");
        config.addDefault("Blazeborn Food Blaze Rod", "3,100");
        config.addDefault("Blazeborn Food Coal", "4,250");
        config.addDefault("Blazeborn Food Coal Block", "4,1000");
        config.addDefault("Blazeborn Food Charcoal", "3,50");
        config.addDefault("Blazeborn Food Fire Charge", "5,500");
        config.addDefault("#####################Merling Settings#####################", "");
        config.addDefault("Merling Breath In Seconds", "30");
        config.addDefault("Merling Conduit Power Amplifier", 2);
        config.addDefault("Merling Velocity Multiplier", 2.0);
        config.addDefault("Merling Trident Multiplier", 1.5);
        config.addDefault("Merling Trident Death Damage", 83);
        config.addDefault("#####################Arachnid Settings#####################", "");
        config.addDefault("Arachnid Velocity",0.25);
        config.addDefault("Arachnid Spider Sense Range",15);
        config.addDefault("Arachnid Spider Sense Duration in Seconds",10);
        config.addDefault("Arachnid Spider Sense Cooldown in Seconds", 60);
        config.addDefault("Arachnid Web Duration in Ticks",20);
        config.addDefault("Arachnid Web Amplifier",2);
        config.addDefault("Arachnid Web Chance",0.1);
        config.options().copyDefaults(true);
        instance.saveConfig();
    }
    public void loadConfig() {
        jdbcURL = config.getString("JDBC URL");
        bedrockPrefix = config.getString("Bedrock Prefix",".");
        msgPrefix = config.getString("Message Prefix","&b[&cFragalicious Origins&b]&r");
        phantomGhostmodeCooldown = config.getInt("Phantom Ghostmode Cooldown",60);
        phantomGhostModeDuration = config.getInt("Phantom Ghostmode Duration",15);
        phantomGhostmodeSpeed = config.getDouble("Phantom Ghostmode Speed",0.05);
        phantomHealth = config.getInt("Phantom Health",16);
        phantomLightExposuretime = config.getInt("Phantom Light Exposure Time",10);
        phantomFireResistDamageChance = config.getInt("Phantom Fire Resist Damage Chance",20);
        felineJumpMultiplier = config.getDouble("Feline Jump Multiplier",3.1);
        felineSpeed = config.getDouble("Feline Speed",0.4);
        felinePounceCooldown = config.getInt("Feline Pounce Cooldown",40);
        enderianMaxHealth = config.getInt("Enderian Max Health",24);
        enderianTeleportCooldown = config.getInt("Enderian Teleport Cooldown",10);
        enderianTeleportDistance = config.getInt("Enderian Teleport Distance",15);
        enderianDamageTeleportAttempts = config.getInt("Enderian Damage Teleport Attempts",20);
        enderianDamageTeleportRange = config.getInt("Enderian Damage Teleport Range",15);
        shulkArmor = config.getDouble("Shulk Armor",10.0);
        shulkKnockback = config.getDouble("Shulk Knockback",0.5);
        shulkToughness = config.getDouble("Shulk Toughness",4.0);
        shulkSpeed = config.getDouble("Shulk Speed",0.18);
        shulkFoodLossChance = config.getDouble("Shulk Food Loss Chance",0.2);
        shulkSelfLevitationChance = config.getDouble("Shulk Self Levitation Chance",0.2);
        shulkTargetLevitationChance = config.getDouble("Shulk Other Levitation Chance",0.1);
        chickenSpeed = config.getDouble("Chicken Speed",0.35);
        chickenHungerCancel = config.getDouble("Chicken Hunger Cancel Chance",0.2);
        chickenSpawnBirdCooldown = config.getInt("Chicken Spawn Bird Cooldown",180);
        blazeHeat1 = config.getString("Blazeborn Heat 1", "100,1,5,5,false,true");
        blazeHeat2 = config.getString("Blazeborn Heat 2", "500,10,25,25,false,true");
        blazeHeat3 = config.getString("Blazeborn Heat 3", "1000,25,50,75,false,false");
        blazeHeat4 = config.getString("Blazeborn Heat 4", "5000,35,100,100,true,false");
        blazeHeat5 = config.getString("Blazeborn Heat 5", "10000,50,150,115,true,false");
        blazeEffect1 = config.getString("Blazeborn Effects 1", "16,0.18,0.0,0.0,false,0.0,0");
        blazeEffect2 = config.getString("Blazeborn Effects 2", "20,0.2,0.0,00.0,true,0.0,0");
        blazeEffect3 = config.getString("Blazeborn Effects 3", "22,0.21,2.0,1.0,true,0.25,20");
        blazeEffect4 = config.getString("Blazeborn Effects 4", "26,0.23,4.0,2.0,true,0.5,40");
        blazeEffect5 = config.getString("Blazeborn Effects 5", "30,0.25,8.0,4.0,true,1.0,60");
        blazeAbility1 = config.getString("Blazeborn Ability 1", "false,30,0,0,0");
        blazeAbility2 = config.getString("Blazeborn Ability 2", "true,180,60,0,0");
        blazeAbility3 = config.getString("Blazeborn Ability 3", "true,120,100,1,1");
        blazeAbility4 = config.getString("Blazeborn Ability 4", "true,90,150,2,2");
        blazeAbility5 = config.getString("Blazeborn Ability 5", "true,60,300,3,3");
        blazeOnFire = config.getInt("Blazeborn On Fire Boost",10);
        blazeStartingHeat = config.getInt("Starting Heat",100);
        blazeFoodBlazePowder = config.getString("Blazeborn Food Blaze Powder", "3,250");
        blazeFoodBlazeRod =config.getString("Blazeborn Food Blaze Rod", "3,100");
        blazeFoodCoal = config.getString("Blazeborn Food Coal", "4,250");
        blazeFoodCoalBlock = config.getString("Blazeborn Food Coal Block", "4,1000");
        blazeFoodCharcoal = config.getString("Blazeborn Food Charcoal", "3,50");
        blazeFoodFireCharge = config.getString("Blazeborn Food Fire Charge", "5,500");
        merlingBreathInSeconds =  config.getInt("Merling Breath In Seconds", 30);
        merlingConduitPowerAmplifier =  config.getInt("Merling Conduit Power Amplifier", 2);
        merlingVelocityMultiplier = config.getDouble("Merling Velocity Multiplier", 2.0);
        merlingTridentDamageMultiplier =  config.getDouble("Merling Trident Multiplier", 1.5);
        merlingTridentBreakDamage = config.getInt("Merling Trident Death Damage", 83);
        arachnidVelocity = config.getDouble("Arachnid Velocity",0.25);
        arachnidSpiderSenseRange = config.getInt("Arachnid Spider Sense Range",15);
        arachnidSpiderSenseDuraion = config.getInt("Arachnid Spider Sense Duration in Seconds",10);
        arachnidSpiderSenseCooldown = config.getInt("Arachnid Spider Sense Cooldown in Seconds", 60);
        arachnidWebDuration = config.getInt("Arachnid Web Duration in Ticks",20);
        arachnidWebChance = config.getDouble("Arachnid Web Chance",0.1);
        arachnidWebAmplifier = config.getInt("Arachnid Web Amplifier",2);
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

    public static String getBlazeHeat1() {
        return blazeHeat1;
    }

    public static String getBlazeHeat2() {
        return blazeHeat2;
    }

    public static String getBlazeHeat3() {
        return blazeHeat3;
    }

    public static String getBlazeHeat4() {
        return blazeHeat4;
    }

    public static String getBlazeHeat5() {
        return blazeHeat5;
    }

    public static String getBlazeEffect1() {
        return blazeEffect1;
    }

    public static String getBlazeEffect2() {
        return blazeEffect2;
    }

    public static String getBlazeEffect3() {
        return blazeEffect3;
    }

    public static String getBlazeEffect4() {
        return blazeEffect4;
    }

    public static String getBlazeEffect5() {
        return blazeEffect5;
    }

    public static String getBlazeAbility1() {
        return blazeAbility1;
    }

    public static String getBlazeAbility2() {
        return blazeAbility2;
    }

    public static String getBlazeAbility3() {
        return blazeAbility3;
    }

    public static String getBlazeAbility4() {
        return blazeAbility4;
    }

    public static String getBlazeAbility5() {
        return blazeAbility5;
    }

    public static String getBlazeFoodBlazePowder() {
        return blazeFoodBlazePowder;
    }

    public static String getBlazeFoodBlazeRod() {
        return blazeFoodBlazeRod;
    }

    public static String getBlazeFoodCoal() {
        return blazeFoodCoal;
    }

    public static String getBlazeFoodCoalBlock() {
        return blazeFoodCoalBlock;
    }

    public static String getBlazeFoodCharcoal() {
        return blazeFoodCharcoal;
    }

    public static String getBlazeFoodFireCharge() {
        return blazeFoodFireCharge;
    }

    public static int getBlazeOnFire() {
        return blazeOnFire;
    }

    public static int getBlazeStartingHeat() {
        return blazeStartingHeat;
    }

    public static int getMerlingBreathInSeconds() {

        return merlingBreathInSeconds;
    }

    public static int getMerlingConduitPowerAmplifier() {
        return merlingConduitPowerAmplifier;
    }

    public static double getMerlingVelocityMultiplier() {
        return merlingVelocityMultiplier;
    }

    public static double getMerlingTridentDamageMultiplier() {
        return merlingTridentDamageMultiplier;
    }

    public static int getMerlingTridentBreakDamage() {
        return merlingTridentBreakDamage;
    }

    public static double getArachnidVelocity() {
        return arachnidVelocity;
    }

    public static double getArachnidWebChance() {
        return arachnidWebChance;
    }

    public static int getArachnidSpiderSenseRange() {
        return arachnidSpiderSenseRange;
    }

    public static int getArachnidSpiderSenseDuraion() {
        return arachnidSpiderSenseDuraion;
    }

    public static int getArachnidSpiderSenseCooldown() {
        return arachnidSpiderSenseCooldown;
    }

    public static int getArachnidWebDuration() {
        return arachnidWebDuration;
    }

    public static int getArachnidWebAmplifier() {
        return arachnidWebAmplifier;
    }
}
