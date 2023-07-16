package com.github.sanforjr2021.origins;

public enum OriginType {
    ARACHNID(0,0, 8, 0, 1.0, 1.0),
    AVIAN(1,0, 8, 0, 1.0, 1.0),
    BLAZEBORNE(2,0, 8, 0, 1.0, 1.0),
    ELYTRIAN(3,0, 8, 0, 1.0, 1.0),
    ENDERIAN(4,0, 8, 0, 1.0, 1.0),
    FELINE(5,0, 8, 0, 1.0, 1.0),
    HUMAN(6,0, 8, 0, 1.0, 1.0),
    MERLING(7,0, 8, 0, 1.0, 1.0),
    PHANTOM(8,0, 8, 0, 1.0, 1.0),
    SHULK(9,0, 8, 0, 1.0, 1.0);

    //Values
    private final int index;
    private final int armor;
    private final int health;
    private final int saturation;
    private final double jumpHeight;
    private final double speed;


    OriginType(int index, int armor, int health, int saturation, double jumpHeight, double speed) {
        this.index = index;
        this.armor = armor;
        this.health = health;
        this.saturation = saturation;
        this.jumpHeight = jumpHeight;
        this.speed = speed;
    }

    public int getArmor() {
        return armor;
    }

    public int getHealth() {
        return health;
    }

    public int getSaturation() {
        return saturation;
    }

    public double getJumpHeight() {
        return jumpHeight;
    }

    public double getSpeed() {
        return speed;
    }

    public int getIndex() {
        return index;
    }
}
