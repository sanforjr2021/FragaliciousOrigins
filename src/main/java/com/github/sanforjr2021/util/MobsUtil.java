package com.github.sanforjr2021.util;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;

public class MobsUtil {
    public static void spawnChicken(Player player) {
        Chicken chicken = player.getWorld().spawn(player.getLocation(), Chicken.class);
        chicken.setBaby();
    }

    public static void spawnParrot(Player player, Parrot.Variant variant) {
        Parrot parrot = (Parrot) player.getWorld().spawnEntity(player.getLocation(), EntityType.PARROT);
    }
}
