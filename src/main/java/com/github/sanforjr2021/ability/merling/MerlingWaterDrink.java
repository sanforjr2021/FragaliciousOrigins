package com.github.sanforjr2021.ability.merling;

import com.github.sanforjr2021.origins.Merling;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MerlingWaterDrink {
    private static int MAX_BREATH;
    public MerlingWaterDrink(PlayerItemConsumeEvent e) {
        Merling merling = (Merling) PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (e.getItem().getType() == Material.POTION){
            merling.setBreathTime(MAX_BREATH);
        }
    }
    public static void reload(){
        MAX_BREATH = ConfigHandler.getMerlingBreathInSeconds();
    }
}
