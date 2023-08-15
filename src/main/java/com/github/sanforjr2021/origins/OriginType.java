package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.data.PlayerManager;
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
        if(PlayerManager.contains(player.getUniqueId())){
            PlayerManager.getOrigin(player.getUniqueId()).remove();
        }
        switch(this){
            case PHANTOM:
                return new Phantom(player);
            case FELINE:
                return new Feline(player);
            default:
                return new Human(player);
        }
    }
}
