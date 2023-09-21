package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.util.MessageUtil;

public class ToggleSpeedSwimAbility extends Ability{
    public ToggleSpeedSwimAbility(Merling origin) {
        origin.setSwimModifier(!origin.hasSwimModifier());
        MessageUtil.sendMessage("Toggle swimming: " + origin.hasSwimModifier(), origin.getPlayer());
    }

    public static void reload(){

    }
}
