package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.ability.Ability;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamageAbility extends Ability {
    public NoFallDamageAbility(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            e.setCancelled(true);
        }
    }
}
