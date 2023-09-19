package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedSwimAbility extends Ability {
    private static double VELOCITY_MULTIPLIER;
    public SpeedSwimAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Merling merling = (Merling) PlayerManager.getOrigin(player.getUniqueId());
        if(merling.hasSwimModifier() && player.isInWater()){
            player.setVelocity(player.getLocation().getDirection().multiply(VELOCITY_MULTIPLIER));

        }
    }

    public static void reload(){
        VELOCITY_MULTIPLIER = ConfigHandler.getMerlingVelocityMultiplier();
    }
}
