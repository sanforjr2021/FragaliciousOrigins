package com.github.sanforjr2021.ability.Chicken;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ReducedHungerAbility extends Ability {
    private static double HUNGER_CANCEL_CHANCE;
    public ReducedHungerAbility(FoodLevelChangeEvent e) {
        Player player = (Player) e.getEntity();
        if(player.getFoodLevel() > e.getFoodLevel()){ //hunger went down
            if(Math.random() < HUNGER_CANCEL_CHANCE){
                e.setCancelled(true);
            }
        }else{
        }
    }
    public static void reload(){
        HUNGER_CANCEL_CHANCE = ConfigHandler.getChickenHungerCancel();
    }
}
