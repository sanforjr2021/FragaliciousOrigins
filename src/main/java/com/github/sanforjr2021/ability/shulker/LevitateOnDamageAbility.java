package com.github.sanforjr2021.ability.shulker;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.origins.Shulk;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class LevitateOnDamageAbility extends Ability {
    private static double SELF_LEVITATE_CHANCE, LOSE_HUNGER_CHANCE;

    public LevitateOnDamageAbility(EntityDamageEvent e) {
        Player player = (Player) e.getEntity();
        Shulk shulk = (Shulk) PlayerManager.getOrigin(player.getUniqueId());
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL && shulk.isLevitationActive() && SELF_LEVITATE_CHANCE > Math.random()) {
            PlayerUtils.addEffect(player, PotionEffectType.LEVITATION, 0, (int) (7 + (e.getDamage() * 7)));
        }
        if (LOSE_HUNGER_CHANCE > Math.random()) {
            if (player.getFoodLevel() >= 1) {
                player.setFoodLevel(player.getFoodLevel() - 1);
            }
            if (player.getSaturation() >= 1.0f) {
                player.setSaturation(player.getSaturation() - 1.0f);
            }
        }

    }

    public static void reload() {
        SELF_LEVITATE_CHANCE = ConfigHandler.getShulkSelfLevitationChance();
        LOSE_HUNGER_CHANCE = ConfigHandler.getShulkFoodLossChance();
    }
}
