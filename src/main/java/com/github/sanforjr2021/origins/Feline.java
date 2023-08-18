package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

import static com.github.sanforjr2021.util.ConfigHandler.getFelineSpeed;

public class Feline extends Origin{
    public static float WALK_SPEED;
    private int pounceCooldown;
    private boolean wet;
    public Feline(Player player) {
        super(OriginType.FELINE, player);
        pounceCooldown = 0;
        wet = false;
    /*
    Ability list()
    - Punce (F)
    - 9 lives
    - land on feet
     */
    }

    @Override
    public void onLogin() {
        PlayerUtils.setWalkSpeed(getPlayer(),WALK_SPEED);
    }

    @Override
    public void onDeath() {
        PlayerUtils.setWalkSpeed(getPlayer(),WALK_SPEED);
    }

    public static void reload() {
        WALK_SPEED = (float) getFelineSpeed();
    }

    public int getPounceCooldown() {
        return pounceCooldown;
    }

    public void setPounceCooldown(int pounceCooldown) {
        if(pounceCooldown < 0){
            this.pounceCooldown = 0;
        }else{
            this.pounceCooldown = pounceCooldown;
        }
    }

    public boolean isWet() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }
}
