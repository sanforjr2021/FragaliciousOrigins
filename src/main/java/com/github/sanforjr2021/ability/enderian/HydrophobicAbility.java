package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Enderian;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class HydrophobicAbility extends Ability {
    public HydrophobicAbility(Enderian origin) {
        Player player = origin.getPlayer();
        if(PlayerUtils.isWet(player)){
            player.damage(1.0);
        }
    }
}
