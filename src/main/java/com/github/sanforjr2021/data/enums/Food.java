package com.github.sanforjr2021.data.enums;

import org.bukkit.Material;

public enum Food {

    APPLE(Material.APPLE, 4, 2.4, false, true, false),
    BAKED_POTATO(Material.BAKED_POTATO, 5, 6, true, true, false),
    BEETROOT(Material.BEETROOT, 1, 1.2, false, true, false),
    BEETROOT_SOUP(Material.BEETROOT_SOUP, 6, 7.2, false, true, false),
    BREAD(Material.BREAD, 5, 6, false, true, false),
    CAKE(Material.CAKE, 2, 0.4, false, true, false),
    CARROT(Material.CARROT, 3, 3.6, false, true, false),
    CHORUS_FRUIT(Material.CHORUS_FRUIT, 4, 2.4, false, true, false),
    COOKED_CHICKEN(Material.COOKED_CHICKEN, 6, 7.2, true, false, true),
    COOKED_COD(Material.COOKED_COD, 5, 6, true, false, true),
    COOKED_MUTTON(Material.COOKED_MUTTON, 6, 9.6, true, false, true),
    COOKED_PORKCHOP(Material.COOKED_PORKCHOP, 8, 12.8, true, false, true),
    COOKED_RABBIT(Material.COOKED_RABBIT, 5, 6, true, false, true),
    COOKED_SALMON(Material.COOKED_SALMON, 6, 9.6, true, false, true),
    COOKIE(Material.COOKIE, 2, 0.4, false, true, false),
    DRIED_KELP(Material.DRIED_KELP, 1, 0.6, true, true, false),
    ENCHANTED_GOLDEN_APPLE(Material.ENCHANTED_GOLDEN_APPLE, 4, 9.6, false, true, false),
    GOLDEN_APPLE(Material.GOLDEN_APPLE, 4, 9.6, false, true, false),
    GLOW_BERRIES(Material.GLOW_BERRIES, 2, 0.4, false, true, false),
    GOLDEN_CARROT(Material.GOLDEN_CARROT, 6, 14.4, false, true, false),
    HONEY_BOTTLE(Material.HONEY_BOTTLE, 6, 1.2, false, true, false),
    MELON_SLICE(Material.MELON_SLICE, 2, 1.2, false, true, false),
    MUSHROOM_STEW(Material.MUSHROOM_STEW, 6, 7.2, false, true, false),
    POISONOUS_POTATO(Material.POISONOUS_POTATO, 2, 1.2, false, true, false),
    POTATO(Material.POTATO, 1, 0.6, false, true, false),
    PUFFERFISH(Material.PUFFERFISH, 1, 0.2, false, false, true),
    PUMPKIN_PIE(Material.PUMPKIN_PIE, 8, 4.8, false, true, false),
    RABBIT_STEW(Material.RABBIT_STEW, 10, 12, false, false, true),
    BEEF(Material.BEEF, 3, 1.8, false, false, true),
    CHICKEN(Material.CHICKEN, 2, 1.2, false, false, true),
    COD(Material.COD, 2, 0.4, false, false, true),
    MUTTON(Material.MUTTON, 2, 1.2, false, false, true),
    PORKCHOP(Material.PORKCHOP, 3, 1.8, false, false, true),
    RABBIT(Material.RABBIT, 3, 1.8, false, false, true),
    SALMON(Material.SALMON, 2, 0.4, false, false, true),
    ROTTEN_FLESH(Material.ROTTEN_FLESH, 4, 0.8, false, false, true),
    SPIDER_EYE(Material.SPIDER_EYE, 2, 3.2, false, false, true),
    COOKED_BEEF(Material.COOKED_BEEF, 8, 12.8, true, false, true),
    SUSPICIOUS_STEW(Material.SUSPICIOUS_STEW, 6, 7.2, false, true, false),
    SWEET_BERRIES(Material.SWEET_BERRIES, 2, 0.4, false, true, false),
    TROPICAL_FISH(Material.TROPICAL_FISH, 1, 0.2, false, false, true);



    private Material type;
    private int hunger;
    private double saturation;
    private boolean isCooked;
    private boolean isPlant;
    private boolean isMeat;

    Food(Material type, int hunger, double saturation, boolean isCooked, boolean isPlant, boolean isMeat) {
        this.type = type;
        this.hunger = hunger;
        this.saturation = saturation;
        this.isCooked = isCooked;
        this.isPlant = isPlant;
        this.isMeat = isMeat;
    }

    public Material getType() {
        return type;
    }

    public int getHunger() {
        return hunger;
    }

    public double getSaturation() {
        return saturation;
    }

    public boolean isCooked() {
        return isCooked;
    }

    public boolean isPlant() {
        return isPlant;
    }

    public boolean isMeat() {
        return isMeat;
    }
}
