package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffectType;

public class TridentTotemAbility extends Ability {
    private static int TRIDENT_DAMAGE;
    private static final int TRIDENT_DURABILITY = 250;
    public TridentTotemAbility(PlayerDeathEvent e, Merling origin) {
        Player player = origin.getPlayer();
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if (mainhand.getType() == Material.TRIDENT) {
            Damageable mainhandItemMeta = (Damageable) mainhand.getItemMeta();
            int damage = mainhandItemMeta.getDamage();
            if (TRIDENT_DURABILITY <= TRIDENT_DAMAGE + damage) {
                mainhand.setAmount(0);
            } else {
                mainhandItemMeta.setDamage(damage + TRIDENT_DAMAGE);
                mainhand.setItemMeta(mainhandItemMeta);
                player.getInventory().setItemInMainHand(mainhand);
            }
            MessageUtil.sendMessage("&bYour trident imbues you with health", player);
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            PlayerUtils.generateParticle(player,Particle.SQUID_INK,50);
            PlayerUtils.generateParticle(player,Particle.WATER_SPLASH,50);
            PlayerUtils.addEffect(player, PotionEffectType.DAMAGE_RESISTANCE, 3, 20);
            PlayerUtils.addEffect(player,PotionEffectType.GLOWING,0,20);
            PlayerUtils.setHunger(player,20);
            PlayerUtils.setSaturation(player,20f);
            e.setCancelled(true);
        }
    }

    public static void reload() {
        TRIDENT_DAMAGE = ConfigHandler.getMerlingTridentBreakDamage();
    }
}
