package com.github.sanforjr2021.ability.shared;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CannotEatFoodAbility extends Ability {

    public CannotEatFoodAbility(PlayerItemConsumeEvent e) {
        e.setCancelled(true);
        MessageUtil.sendMessage("You cannot eat food.", e.getPlayer());
    }
}
