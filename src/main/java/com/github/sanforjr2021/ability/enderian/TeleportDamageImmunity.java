package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.origins.Enderian;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportDamageImmunity extends Ability {

    public TeleportDamageImmunity(PlayerTeleportEvent e) {
        Enderian origin = (Enderian) PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if(!origin.isTeleportInvulnerability() && origin.getPlayer().getHealth() > 0){
            origin.setTeleportInvulnerability(true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    origin.setTeleportInvulnerability(false);
                }
            }.runTaskTimer(FragaliciousOrigins.getInstance(), 10l,15);
        }
    }
}
