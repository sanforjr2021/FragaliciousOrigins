package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.origins.Feline;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class WaterWeaknessAbility extends Ability {

    public WaterWeaknessAbility(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Feline feline = (Feline) PlayerManager.getOrigin(player.getUniqueId());
        if(PlayerUtils.isWetIgnoringConduit(player) && !player.hasPotionEffect(PotionEffectType.CONDUIT_POWER) && !feline.isWet()){
            PlayerUtils.addEffect(player, PotionEffectType.WEAKNESS, 1);
            PlayerUtils.addEffect(player, PotionEffectType.SLOW, 0);
            feline.setWet(true);
        }else if(((!PlayerUtils.isWet(player)) || player.hasPotionEffect(PotionEffectType.CONDUIT_POWER))  && feline.isWet()){
            PlayerUtils.removeEffect(player, PotionEffectType.WEAKNESS);
            PlayerUtils.removeEffect(player, PotionEffectType.SLOW);
            feline.setWet(false);
        }
    }
}
