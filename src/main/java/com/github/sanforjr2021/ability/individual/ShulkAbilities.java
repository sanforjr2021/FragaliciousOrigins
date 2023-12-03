package com.github.sanforjr2021.ability.individual;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.origins.Shulk;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class ShulkAbilities extends Ability {
    private static double TARGET_LEVITATE_CHANCE;

    public void levitateEnemyAbility(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Shulk shulk = (Shulk) PlayerManager.getOrigin(e.getDamager().getUniqueId());
        double chance = Math.random();
        if (shulk.isLevitationActive() && TARGET_LEVITATE_CHANCE > chance) {
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) e.getEntity();
                PlayerUtils.addEffect(target, PotionEffectType.LEVITATION, 0, (int) (7 + (e.getDamage() * 7)));
            }
        }
    }
    public static void reload(){
        TARGET_LEVITATE_CHANCE = ConfigHandler.getShulkTargetLevitationChance();
    }
}
