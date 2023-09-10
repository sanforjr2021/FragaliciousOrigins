package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerlessAbility extends Ability {
    public HungerlessAbility(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
