package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;


public abstract class Origin {
    private OriginType originType;
    private UUID uuid;
    private boolean isSleeping = false;
    private boolean isTemp = false;
    protected int primaryCooldown;
    protected int secondaryCooldown;
    public Origin(OriginType origin, Player player){
        originType = origin;
        uuid = player.getUniqueId();
        MessageUtil.log(player.getName() + " is playing as " + origin);
        if(origin != OriginType.UNASSIGNED){
            MessageUtil.sendMessage("&eYou are playing as a &c" + origin, player);
        }
        primaryCooldown = 0;
        secondaryCooldown = 0;
        onLogin();
    }
    public abstract void onLogin();
    public abstract void onDeath();
    public void remove(){
        Player player = getPlayer();
        PlayerUtils.removeEffects(player);
        PlayerUtils.resetArmor(player);
        PlayerUtils.resetFlySpeed(player);
        PlayerUtils.resetWalkSpeed(player);
        PlayerUtils.resetMaxHealth(player);
        PlayerUtils.resetToughness(player);
        PlayerUtils.resetKnockbackResistance(player);
        PlayerUtils.resetAttackSpeed(player);
        PlayerUtils.resetLuck(player);
        if(player.getGameMode() == GameMode.SURVIVAL){
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        OriginBossBarManager.removeBossBars(player.getUniqueId());
        if(originType != OriginType.UNASSIGNED){
            MessageUtil.sendMessage("&eYou are no longer the origin &b" + originType, player);
        }

    }
    public OriginType getOriginType() {
        return originType;
    }

    public static void reload() {}
    public Player getPlayer(){
        return PlayerUtils.getPlayer(uuid);
    }
    public UUID getUuid() {
        return uuid;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        PlayerUtils.resetSleep(getPlayer());
        isSleeping = sleeping;
        if(!isSleeping && getPlayer().isSleeping()){
            getPlayer().wakeup(true);
        }
    }
    public boolean isTemp() {
        return isTemp;
    }
    public void setTemp(boolean temp) {
        isTemp = temp;
    }
    public int getPrimaryCooldown() {
        return primaryCooldown;
    }

    public abstract void setPrimaryCooldown(int primaryCooldown);

    public abstract int getPrimaryMaxCooldown();
    public abstract void primaryAbilityActivate();
    public abstract void primaryAbilityTick();

    public int getSecondaryCooldown() {
        return secondaryCooldown;
    }
    public abstract void setSecondaryCooldown(int secondaryCooldown);
    public abstract int getSecondaryMaxCooldown();
    public abstract void secondaryAbilityActivate();
    public abstract void secondaryAbilityTick();

    public abstract void passiveAbilitiesTick();

    protected void primaryAbilityCooldownMessage(String abilityName){
        MessageUtil.sendMessage("&e" + abilityName + " is on cooldown for &c" + primaryCooldown + " &eseconds.", getPlayer());
    }
    protected void secondaryAbilityCooldownMessage(String abilityName){
        MessageUtil.sendMessage("&e" + abilityName + " is on cooldown for &c" + secondaryCooldown + " &eseconds.", getPlayer());
    }
}
