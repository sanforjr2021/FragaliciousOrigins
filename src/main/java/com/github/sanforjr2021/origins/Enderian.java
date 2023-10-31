package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class Enderian extends Origin {
    private static int MAX_HEALTH;
    private int cooldown;
    private boolean teleportOnDamage;
    private boolean teleportInvulnerability;
    public Enderian(Player player) {
        super(OriginType.ENDERIAN, player);
        cooldown = 0;
        teleportOnDamage = false;
        teleportInvulnerability = false;
    }

    @Override
    public void onLogin() {
        PlayerUtils.setMaxHealth(getPlayer(), MAX_HEALTH);
    }

    @Override
    public void onDeath() {

    }
    public static void reload(){
        MAX_HEALTH = ConfigHandler.getEnderianMaxHealth();
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        if(cooldown < 0){
            cooldown = 0;
        }
        this.cooldown = cooldown;
    }

    public boolean isTeleportOnDamage() {
        return teleportOnDamage;
    }

    public void setTeleportOnDamage(boolean teleportOnDamage) {
        this.teleportOnDamage = teleportOnDamage;
    }

    public boolean isTeleportInvulnerability() {
        return teleportInvulnerability;
    }

    public void setTeleportInvulnerability(boolean teleportInvulnerability) {
        this.teleportInvulnerability = teleportInvulnerability;
    }
}
