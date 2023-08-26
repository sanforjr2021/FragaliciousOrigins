package com.github.sanforjr2021.ability.shared;

import com.github.sanforjr2021.data.enums.Food;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class VegetarianAbility {

    public VegetarianAbility(PlayerItemConsumeEvent e){
        Material material = e.getItem().getType();
        try{
            Food food = Food.valueOf(material.toString());
            if(!food.isPlant()){
                MessageUtil.sendMessage("You cannot eat meat based foods", e.getPlayer());
                e.setCancelled(true);
            }
        }catch (IllegalArgumentException exception){
            //this marks
        }
    }
}
