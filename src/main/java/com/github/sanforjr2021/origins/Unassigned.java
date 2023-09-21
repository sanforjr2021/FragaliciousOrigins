package com.github.sanforjr2021.origins;

import org.bukkit.entity.Player;

public class Unassigned extends Origin{
    public Unassigned(Player player) {
        super(OriginType.UNASSIGNED, player);
    }


    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {

    }
}
