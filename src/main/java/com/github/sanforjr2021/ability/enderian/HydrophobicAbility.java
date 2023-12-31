package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Enderian;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class HydrophobicAbility extends Ability {
    public HydrophobicAbility(Enderian origin) {
        Player player = origin.getPlayer();
        if(PlayerUtils.isWetIgnoringConduit(player) && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)){
            player.damage(1.0);
        }
    }
}
