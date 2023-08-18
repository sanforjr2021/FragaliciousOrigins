package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Enderian;
import org.bukkit.entity.Player;

public class HydrophobicAbility extends Ability {
    public HydrophobicAbility(Enderian origin) {
        Player player = origin.getPlayer();
        if(player.isInWater()){
            player.damage(1.0);
        }
    }
}
