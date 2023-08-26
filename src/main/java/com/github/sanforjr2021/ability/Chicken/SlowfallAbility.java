package com.github.sanforjr2021.ability.Chicken;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class SlowfallAbility extends Ability {

    public SlowfallAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(player.isSneaking() && !player.isInWater()){
            PlayerUtils.addEffect(player, PotionEffectType.SLOW_FALLING, 0, 3);
        }
    }

}
