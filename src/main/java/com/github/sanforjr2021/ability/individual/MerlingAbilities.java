package com.github.sanforjr2021.ability.individual;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffectType;

public class MerlingAbilities extends Ability {
    private static double VELOCITY_MULTIPLIER, TRIDENT_DAMAGE_MULTIPLIER;
    private static int MAX_BREATH, TRIDENT_DAMAGE;
    private static final int TRIDENT_DURABILITY = 250;

    public static void speedSwimAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Merling merling = (Merling) PlayerManager.getOrigin(player.getUniqueId());
        if (merling.hasSwimModifier() && player.isInWater() && player.isSwimming()) {
            player.setVelocity(player.getLocation().getDirection().multiply(VELOCITY_MULTIPLIER));
        }
    }

    public static void waterDrink(PlayerItemConsumeEvent e) {
        Merling merling = (Merling) PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (e.getItem().getType() == Material.POTION) {
            merling.setBreathTime(MAX_BREATH);
        }
    }

    public static void thrownTridentAmplifierAbility(EntityDamageByEntityEvent e) {
        Trident trident = (Trident) e.getDamager();
        if (trident.getShooter() instanceof Player) {
            Player shooter = (Player) trident.getShooter();
            Origin origin = PlayerManager.getOrigin(shooter.getUniqueId());
            if (origin.getOriginType().equals(OriginType.MERLING)) {
                e.setDamage(e.getDamage() * TRIDENT_DAMAGE_MULTIPLIER * 2.0);
                if (e.getEntity() instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) e.getEntity();
                    PlayerUtils.addEffect(target, PotionEffectType.GLOWING, 0, 60);
                    PlayerUtils.addEffect(target, PotionEffectType.SLOW, 4, 30);
                    PlayerUtils.addEffect(target, PotionEffectType.CONFUSION, 0, 10);
                }
            }
        }
    }

    public static void tridentAmplifierAbility(EntityDamageByEntityEvent e) {
        Merling merling = (Merling) PlayerManager.getOrigin(e.getDamager().getUniqueId());
        if (e.isCancelled()) {
            return;
        }
        if (merling.isWieldingTrident()) {
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) e.getEntity();
                PlayerUtils.generateParticle(target, Particle.WATER_DROP, 5);
                e.setDamage(e.getDamage() * TRIDENT_DAMAGE_MULTIPLIER);
            }
        }
    }
    public static void tridentTotemAbility(PlayerDeathEvent e, Merling origin) {
        Player player = origin.getPlayer();
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if (mainhand.getType() == Material.TRIDENT) {
            Damageable mainhandItemMeta = (Damageable) mainhand.getItemMeta();
            int damage = mainhandItemMeta.getDamage();
            if (TRIDENT_DURABILITY <= TRIDENT_DAMAGE + damage) {
                mainhand.setAmount(0);
            } else {
                if(mainhandItemMeta.hasDamage() == false){ //if not damage
                    mainhandItemMeta.setDamage(TRIDENT_DAMAGE);
                }else{
                    mainhandItemMeta.setDamage(damage + TRIDENT_DAMAGE);
                }
                mainhand.setItemMeta(mainhandItemMeta);
                player.getInventory().setItemInMainHand(mainhand);
            }
            MessageUtil.sendMessage("&bYour trident imbues you with health", player);
            e.deathMessage(MessageUtil.generateComponenet(e.getPlayer().getName() + "escaped death with their trident affinity."));
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
        VELOCITY_MULTIPLIER = ConfigHandler.getMerlingVelocityMultiplier();
        MAX_BREATH = ConfigHandler.getMerlingBreathInSeconds();
        TRIDENT_DAMAGE_MULTIPLIER = ConfigHandler.getMerlingTridentDamageMultiplier();
        TRIDENT_DAMAGE = ConfigHandler.getMerlingTridentBreakDamage();
    }
}
