package com.github.sanforjr2021.ability.elytrian;

import com.github.sanforjr2021.origins.Elytrian;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class SpreadWingsAbility {
    public SpreadWingsAbility(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR){
            Elytrian elytrian = (Elytrian) PlayerManager.getOrigin(player.getUniqueId());
            player.setAllowFlight(false);
            if(elytrian.isWingsActive()){
                player.setGliding(true);
            }
            e.setCancelled(true);
        }
    }
    public SpreadWingsAbility(EntityToggleGlideEvent e){
        Player player = (Player) e.getEntity();
        Elytrian elytrian = (Elytrian) PlayerManager.getOrigin(player.getUniqueId());
        if(elytrian.isWingsActive() && !e.isGliding() && !PlayerUtils.isOnGround(player)) {
            e.setCancelled(true);
        }
    }
}
