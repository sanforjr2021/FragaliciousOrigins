package com.github.sanforjr2021.ability.elytrian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class ImpactAbility extends Ability {
    private static double IMPACT_DAMAGE_MULTIPLIER;

    public ImpactAbility(EntityDamageEvent e) {
        if(e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL){
            e.setDamage(e.getDamage()*IMPACT_DAMAGE_MULTIPLIER);

        }
    }

    public static void reload(){
        IMPACT_DAMAGE_MULTIPLIER = ConfigHandler.getElytrianImpactDamageMultiplier();
    }
}
