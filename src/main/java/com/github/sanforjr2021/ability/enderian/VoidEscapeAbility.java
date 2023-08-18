package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

public class VoidEscapeAbility extends Ability {
    public VoidEscapeAbility(Origin origin) {
        Player player = origin.getPlayer();
        int yHeight = (int) player.getLocation().getY();
        Environment environment = player.getWorld().getEnvironment();
        switch (environment){
            case NORMAL:
            case CUSTOM:
                if(yHeight < -66){
                    teleportToSpawnpoint(player);
                }
                break;
            case NETHER:
            case THE_END:
                if(yHeight <  -1){
                    teleportToSpawnpoint(player);
                }
        }
    }
    private void teleportToSpawnpoint(Player player){
        Location spawnLocation = player.getBedSpawnLocation();
        if(spawnLocation == null){
            spawnLocation = player.getWorld().getSpawnLocation();
        }
        player.teleport(spawnLocation);
        player.playEffect(EntityEffect.TELEPORT_ENDER);
        MessageUtil.sendMessage("&cYou avoided losing your items by instinctually teleporting out of the void.", player);
    }
}
