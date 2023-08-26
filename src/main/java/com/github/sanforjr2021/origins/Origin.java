package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.github.sanforjr2021.util.PlayerUtils.getPlayer;


public abstract class Origin {
    private OriginType originType;
    private UUID uuid;
    private boolean isSleeping = false;
    public Origin(OriginType origin, Player player){
        originType = origin;
        uuid = player.getUniqueId();
        MessageUtil.log(player.getName() + " is playing as " + origin);
        MessageUtil.sendMessage("&eYou are playing as a &c" + origin, player);
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
        OriginBossBarManager.removeBossBars(player.getUniqueId());
        MessageUtil.sendMessage("&eYou are no longer the origin &b" + originType, player);
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
}
