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
}
