package com.github.sanforjr2021.origins;

import org.bukkit.entity.Player;

public enum OriginType {
    ARACHNID,
    AVIAN,
    BLAZEBORNE,
    ELYTRIAN,
    ENDERIAN,
    FELINE,
    HUMAN,
    MERLING,
    PHANTOM,
    SHULK;

    public Origin getOrigin(Player player){
        switch(this){
            case PHANTOM:
                return new Phantom(player);
            default:
                return new Human(player);
        }
    }
}
