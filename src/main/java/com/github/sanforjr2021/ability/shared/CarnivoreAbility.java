package com.github.sanforjr2021.ability.shared;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.ability.enums.Food;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class CarnivoreAbility extends Ability {
    public CarnivoreAbility(PlayerItemConsumeEvent e){
        Material material = e.getItem().getType();
        try{
            Food food = Food.valueOf(material.toString());
            if(!food.isMeat()){
                MessageUtil.sendMessage("You cannot eat non meat foods", e.getPlayer());
                e.setCancelled(true);
            }
        }catch (IllegalArgumentException exception){
            //this marks
        }
    }
}
