package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class NineLivesAbility extends Ability {
    public NineLivesAbility(PlayerDeathEvent e) {
        if(new Random().nextInt(9) == 0){
            MessageUtil.sendMessage("You survived a fatal hit due to one of your nine lives.", e.getPlayer());
            e.setCancelled(true);
        }
    }
}
