package com.github.sanforjr2021.origins;

import org.bukkit.entity.Player;

public enum OriginType {
    ARACHNID,
    BLAZEBORNE,
    CHICKEN,
    ELYTRIAN,
    ENDERIAN,
    FELINE,
    HUMAN,
    MERLING,
    PHANTOM,
    SHULK;

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
            default:
                return new Human(player);
        }
    }
}
