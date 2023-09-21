package com.github.sanforjr2021.ability;


import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.entity.Player;

public abstract class Ability{
    public Ability(){
    }

    public static void reload(){

    }
    protected void sendCooldownMessage(String abilityName, int timeInSeconds, Player player){
        MessageUtil.sendMessage("&e" + abilityName + " is on cooldown for &c" + timeInSeconds + " &eseconds.", player);
    }
}
