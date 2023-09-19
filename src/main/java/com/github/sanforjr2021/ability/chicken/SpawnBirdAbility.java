package com.github.sanforjr2021.ability.chicken;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Chicken;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MobsUtil;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SpawnBirdAbility extends Ability {
    private static int SPAWN_BIRD_COOLDOWN;
    private static final Random random = new Random();
    private final Player player;

    public SpawnBirdAbility(Chicken origin) {
        player = origin.getPlayer();
        if (origin.getSpawnChickenCooldown() > 0) {
            sendCooldownMessage("Spawn Bird", origin.getSpawnChickenCooldown(), origin.getPlayer());
        } else {
            generateRandomBird();
            origin.setSpawnChickenCooldown(SPAWN_BIRD_COOLDOWN);
            new BukkitRunnable() {
                @Override
                public void run() {
                    origin.setSpawnChickenCooldown(origin.getSpawnChickenCooldown() - 1);
                    if (origin.getSpawnChickenCooldown() <= 0) {
                        cancel();
                    }
                }
            }.runTaskTimer(FragaliciousOrigins.getInstance(), 20L, 20L);
        }
    }

    public static void reload() {
        SPAWN_BIRD_COOLDOWN = ConfigHandler.getChickenSpawnBirdCooldown();
    }

    private void generateRandomBird() {
        if (random.nextInt(2) == 0) {
            MobsUtil.spawnChicken(player);
        } else {
            switch (random.nextInt(5)) {
                case 0:
                    MobsUtil.spawnParrot(player, Parrot.Variant.BLUE);
                    break;
                case 1:
                    MobsUtil.spawnParrot(player, Parrot.Variant.GRAY);
                    break;
                case 2:
                    MobsUtil.spawnParrot(player, Parrot.Variant.RED);
                    break;
                case 3:
                    MobsUtil.spawnParrot(player, Parrot.Variant.CYAN);
                    break;
                default:
                    MobsUtil.spawnParrot(player, Parrot.Variant.GREEN);
                    break;
            }
        }
    }
}
