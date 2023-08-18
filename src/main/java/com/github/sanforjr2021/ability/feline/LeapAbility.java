package com.github.sanforjr2021.ability.feline;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LeapAbility extends Ability {
    private static double LEAP_MULTIPLIER;
    public LeapAbility(PlayerJumpEvent e){
        Player player = e.getPlayer();
        Vector jumpVelocity = player.getVelocity();
        jumpVelocity.setY(jumpVelocity.getY()*LEAP_MULTIPLIER);
        player.setVelocity(jumpVelocity);
    }
    public static void reload(){
        LEAP_MULTIPLIER = ConfigHandler.getFelineJumpMultiplier();
    }
}
