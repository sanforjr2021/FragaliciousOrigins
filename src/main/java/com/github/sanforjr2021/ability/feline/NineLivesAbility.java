package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class NineLivesAbility extends Ability {
    public NineLivesAbility(PlayerDeathEvent e) {
        if(new Random().nextInt(9) == 0){
            e.deathMessage(MessageUtil.generateComponenet(e.getPlayer().getName() + "escaped death with one of their 9 lives."));
            e.getPlayer().setHealth(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            e.setCancelled(true);
        }
    }
}
