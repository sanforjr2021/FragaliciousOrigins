package com.github.sanforjr2021.ability.shared;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class NightVisionAbility extends Ability {

    public NightVisionAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(player.getLocation().getBlock().getLightLevel() < 8){
            PlayerUtils.addEffect(e.getPlayer(), PotionEffectType.NIGHT_VISION, 0);
        }else if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
            PlayerUtils.removeEffect(player, PotionEffectType.NIGHT_VISION);
        }
    }
}
