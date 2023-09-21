package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Merling extends Origin{
    private boolean swimModifier;
    private int breathTime;
    private static int MAX_BREATH;
    public Merling(Player player) {
        super(OriginType.MERLING, player);
        swimModifier = false;
        breathTime = MAX_BREATH;

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
        return swimModifier;
    }

    public void setSwimModifier(boolean swimModifier) {
        this.swimModifier = swimModifier;
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
    }
}
