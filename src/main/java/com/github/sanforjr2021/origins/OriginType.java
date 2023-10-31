package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.ChatColor;
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
            case ELYTRIAN:
                if(!PlayerUtils.isBedrock(player)){
                    return new Elytrian(player);
                }else{
                    MessageUtil.sendMessage("&cBedrock cannot play as Elytrian", player);
                }
            default:
                return new Unassigned(player);
        }
    }
}
