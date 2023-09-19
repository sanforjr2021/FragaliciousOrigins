package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TridentAmplifierAbility extends Ability {
    private static double TRIDENT_DAMAGE_MULTIPLIER;

    public TridentAmplifierAbility(EntityDamageByEntityEvent e) {
        Merling merling = (Merling) PlayerManager.getOrigin(e.getDamager().getUniqueId());
        if(merling.isWieldingTrident()){
            if(e.getEntity() instanceof LivingEntity){
                LivingEntity target = (LivingEntity) e.getEntity();
                PlayerUtils.generateParticle(target, Particle.WATER_DROP, 5);
                e.setDamage(e.getDamage()*TRIDENT_DAMAGE_MULTIPLIER);
            }
        }

    }

    public static void reload(){
        TRIDENT_DAMAGE_MULTIPLIER = ConfigHandler.getMerlingTridentDamageMultiplier();
    }
}
