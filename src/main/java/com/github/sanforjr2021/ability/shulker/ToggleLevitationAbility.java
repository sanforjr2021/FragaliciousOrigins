package com.github.sanforjr2021.ability.shulker;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Shulk;
import com.github.sanforjr2021.util.MessageUtil;

public class ToggleLevitationAbility extends Ability {
    public ToggleLevitationAbility(Shulk origin) {
        origin.setLevitationActive(!origin.isLevitationActive());
        if (origin.isLevitationActive()) {
            MessageUtil.sendMessage("You are now actively applying levitation", origin.getPlayer());
        } else {
            MessageUtil.sendMessage("You are no longer actively applying levitation", origin.getPlayer());
        }
    }
}
