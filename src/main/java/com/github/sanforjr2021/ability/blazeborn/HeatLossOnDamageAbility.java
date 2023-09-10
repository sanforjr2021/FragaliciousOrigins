package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.origins.PlayerManager;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeatLossOnDamageAbility extends Ability {

    public HeatLossOnDamageAbility(EntityDamageEvent e) {
        Blazeborn blazeborn = (Blazeborn) PlayerManager.getOrigin(e.getEntity().getUniqueId());
        if(e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
            e.setCancelled(true);
        }else{
            blazeborn.setHeat(blazeborn.getHeat() - blazeborn.getLevel().getDamageDrain());
        }
    }

    public static void reload(){

    }
}
