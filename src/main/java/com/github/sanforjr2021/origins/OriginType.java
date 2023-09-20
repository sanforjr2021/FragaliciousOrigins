package com.github.sanforjr2021.origins;

import org.bukkit.entity.Player;

public enum OriginType {
    ARACHNID,
    BLAZEBORN,
    CHICKEN,
    ELYTRIAN,
    ENDERIAN,
    FELINE,
    HUMAN,
    MERLING,
    PHANTOM,
    SHULK,
    UNASSIGNED;

    public Origin getOrigin(Player player){
        if(PlayerManager.contains(player.getUniqueId())){
            PlayerManager.getOrigin(player.getUniqueId()).remove();
        }
        switch(this){
            case PHANTOM:
                return new Phantom(player);
            case FELINE:
                return new Feline(player);
            case ENDERIAN:
                return new Enderian(player);
            case SHULK:
                return new Shulk(player);
            case CHICKEN:
                return new Chicken(player);
            case BLAZEBORN:
                return new Blazeborn(player);
            case HUMAN:
                return new Human(player);
            case MERLING:
                return new Merling(player);
            case ARACHNID:
                return new Arachnid(player);
            default:
                return new Unassigned(player);
        }
    }
}
