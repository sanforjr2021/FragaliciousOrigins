package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ExtinguishAbility extends Ability {

    public ExtinguishAbility(PlayerDeathEvent e, Blazeborn origin) {
        if(origin.getHeat() <= 0){
            e.setDeathSound(Sound.BLOCK_CANDLE_EXTINGUISH);
            PlayerUtils.generateParticle(e.getPlayer(), Particle.SMOKE_NORMAL, 50);
            e.deathMessage(MessageUtil.generateComponenet(e.getPlayer().getName() + " was extinguished."));
        }
    }
}
