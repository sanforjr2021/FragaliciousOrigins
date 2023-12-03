package com.github.sanforjr2021.origins;

import org.bukkit.entity.Player;

public class Human extends Origin {


    public Human(Player player) {
        super(OriginType.HUMAN, player);
    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {

    }

    @Override
    public void setPrimaryCooldown(int primaryCooldown) {

    }

    @Override
    public void setSecondaryCooldown(int secondaryCooldown) {

    }

    @Override
    public int getPrimaryMaxCooldown() {
        return 0;
    }

    @Override
    public void primaryAbilityActivate() {

    }

    @Override
    public void primaryAbilityTick() {

    }

    @Override
    public int getSecondaryMaxCooldown() {
        return 0;
    }

    @Override
    public void secondaryAbilityActivate() {

    }

    @Override
    public void secondaryAbilityTick() {

    }

    @Override
    public void passiveAbilitiesTick() {

    }
}
