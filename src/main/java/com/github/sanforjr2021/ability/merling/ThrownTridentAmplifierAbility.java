package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class ThrownTridentAmplifierAbility extends Ability {
    private static double TRIDENT_DAMAGE_MULTIPLIER;
    public ThrownTridentAmplifierAbility(EntityDamageByEntityEvent e) {
        Trident trident = (Trident) e.getDamager();
        if (trident.getShooter() instanceof Player) {
            Player shooter = (Player) trident.getShooter();
            Origin origin = PlayerManager.getOrigin(shooter.getUniqueId());
            if(origin.getOriginType().equals(OriginType.MERLING)){
                e.setDamage(e.getDamage()*TRIDENT_DAMAGE_MULTIPLIER*2.0);
                if(e.getEntity() instanceof LivingEntity){
                    LivingEntity target = (LivingEntity) e.getEntity();
                    PlayerUtils.addEffect(target, PotionEffectType.GLOWING,0,60);
                    PlayerUtils.addEffect(target, PotionEffectType.SLOW,4,30);
                    PlayerUtils.addEffect(target, PotionEffectType.CONFUSION,0,10);
                }
            }
        }
}

    public static void reload(){
        TRIDENT_DAMAGE_MULTIPLIER = ConfigHandler.getMerlingTridentDamageMultiplier();
    }
}

