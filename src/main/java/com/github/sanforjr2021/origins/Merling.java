package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Merling extends Origin{
    private boolean hasSwimBoost, hasNoWaterGravity;
    private int breathTime;
    private static int MAX_BREATH, CONDUIT_AMPLIFIER;
    private static double TRIDENT_MULTIPLIER;
    public Merling(Player player) {
        super(OriginType.MERLING, player);
        hasSwimBoost = false;
        hasNoWaterGravity = false;
        breathTime = MAX_BREATH;
        PlayerUtils.setLuck(player,2.0);
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {
        breathTime = MAX_BREATH;
    }

    public boolean isWieldingTrident(){
        return getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT);
    }

    public boolean hasSwimModifier() {
        return hasSwimBoost;
    }

    public void setHasSwimBoost(boolean hasSwimBoost) {
        this.hasSwimBoost = hasSwimBoost;
    }

    public int getBreathTime() {
        return breathTime;
    }

    public void setBreathTime(int breathTime) {
        if(breathTime < 0){
            breathTime = 0;
        }else if(breathTime > MAX_BREATH){
            breathTime = MAX_BREATH;
            OriginBossBarManager.removeBossBar(getPlayer().getUniqueId(), BossBarType.WATER_BREATH);
        }
        this.breathTime = breathTime;
    }

    public static void reload(){
        MAX_BREATH = ConfigHandler.getMerlingBreathInSeconds();
        CONDUIT_AMPLIFIER = ConfigHandler.getMerlingConduitPowerAmplifier();
        TRIDENT_MULTIPLIER = ConfigHandler.getMerlingTridentDamageMultiplier();
    }

    @Override
    public void setPrimaryCooldown(int primaryCooldown) {

    }

    @Override
    public int getPrimaryMaxCooldown() {
        return 0;
    }

    @Override
    public void primaryAbilityActivate() {
        hasSwimBoost = !hasSwimBoost;
        MessageUtil.sendMessage("Toggle swimming: " + hasSwimBoost, this.getPlayer());
    }

    @Override
    public void primaryAbilityTick() {

    }

    @Override
    public void setSecondaryCooldown(int secondaryCooldown) {

    }

    @Override
    public int getSecondaryMaxCooldown() {
        return 0;
    }

    @Override
    public void secondaryAbilityActivate() {
        hasNoWaterGravity = !hasNoWaterGravity;
        MessageUtil.sendMessage("Toggle Water Gravity: " + hasNoWaterGravity, this.getPlayer());
    }

    @Override
    public void secondaryAbilityTick() {

    }

    @Override
    public void passiveAbilitiesTick() {
        Player player = this.getPlayer();
        //remove mining fatigue
        if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
        manageGravity(player);
        manageConduitPower(player);
        manageHydrationMeter(player);
    }
    private void manageHydrationMeter(Player player){
        //Update Bossbar

        if (getBreathTime() != MAX_BREATH || !PlayerUtils.isWet(player)) {
            if (OriginBossBarManager.geOriginBossBar(player.getUniqueId(), BossBarType.WATER_BREATH) == null) {
                OriginBossBarManager.addBossBar(player.getUniqueId(), new OriginBossBar(player, BossBarType.WATER_BREATH, ChatColor.BLUE + "Water Breath", BarColor.BLUE, 1.0));
            }
            OriginBossBar bar = OriginBossBarManager.geOriginBossBar(player.getUniqueId(), BossBarType.WATER_BREATH);
            int breath = getBreathTime();
            //Update when in water
            if(PlayerUtils.isWet(player)){
                setBreathTime(breath + 10);
                if(getBreathTime() != MAX_BREATH){
                    bar.setProgress(getBreathTime(),MAX_BREATH);
                }
            }
            //update when out of water
            else if(breath> 0){
                setBreathTime(breath - 1);
                bar.setProgress(getBreathTime(),MAX_BREATH);
            }else{
                player.damage(2.0);
            }
        }
    }
    private void manageConduitPower(Player player){
        //add conduit power if does not have conduit power in the water
        if (PlayerUtils.isWetIgnoringConduit(player) && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)) {
            PlayerUtils.addEffect(player, PotionEffectType.CONDUIT_POWER, CONDUIT_AMPLIFIER);
            //remove conduit power when not wet
        } else if (!PlayerUtils.isWetIgnoringConduit(player) && player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)) {
            player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
            //add gravity when out of water
            if(!player.hasGravity()){
                player.setGravity(true);
            }
        }
    }
    private void manageGravity(Player player){
        if(PlayerUtils.isWetIgnoringConduit(player)){
            //if gravity does not match
            if(player.hasGravity() != hasNoWaterGravity){
                player.setGravity(hasNoWaterGravity);
            }
        }else if(!player.hasGravity()) {
            player.setGravity(true);
        }
    }
}
