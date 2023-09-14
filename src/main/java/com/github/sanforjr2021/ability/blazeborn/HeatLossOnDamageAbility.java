package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeatLossOnDamageAbility extends Ability {
    private static int ON_FIRE_GAIN;
    public HeatLossOnDamageAbility(EntityDamageEvent e) {
        Blazeborn blazeborn = (Blazeborn) PlayerManager.getOrigin(e.getEntity().getUniqueId());
        if(e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                || e.getCause() == EntityDamageEvent.DamageCause.LAVA || e.getCause() == EntityDamageEvent.DamageCause.MELTING
                || e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR){
            if(blazeborn.getLevel().isPassiveBurn()){
                blazeborn.setHeat(blazeborn.getHeat()+ ON_FIRE_GAIN);
            }
            e.setCancelled(true);
        }else{
            blazeborn.setHeat(blazeborn.getHeat() - blazeborn.getLevel().getDamageDrain());
        }
    }

    public static void reload(){
        ON_FIRE_GAIN = ConfigHandler.getBlazeOnFire();
    }
}
