package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.util.MessageUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class BurnItemAbility extends Ability {
    public BurnItemAbility(Blazeborn blazeborn) {
        int levelNum = blazeborn.getLevel().getLevel();
        PlayerInventory inventory = blazeborn.getPlayer().getInventory();
        Player player = blazeborn.getPlayer();
        float saturation = player.getSaturation();
        try{
        Blazeborn.Food food = Blazeborn.Food.valueOf(inventory.getItemInMainHand().getType().toString());
            if(food.getMaxLevel() >= levelNum){
                inventory.getItemInMainHand().setAmount(inventory.getItemInMainHand().getAmount()-1);
                blazeborn.setHeat(blazeborn.getHeat() + food.getHeatValue());
                if(saturation < 20f){
                    player.setSaturation(saturation + 2f);
                }

            }else{
                MessageUtil.sendMessage("&cThis will not provide any additional heat at your current temperature", blazeborn.getPlayer());
            }
        }catch (IllegalArgumentException e){
            MessageUtil.sendMessage("&cThis will not provide any additional heat", blazeborn.getPlayer());
        }
    }
}
