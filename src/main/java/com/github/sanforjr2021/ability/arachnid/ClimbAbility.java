package com.github.sanforjr2021.ability.arachnid;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Arachnid;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ClimbAbility extends Ability {
    private static float VELOCITY;

    public ClimbAbility(PlayerMoveEvent e) {

        Player player = e.getPlayer();
        Arachnid arachnid = (Arachnid) PlayerManager.getOrigin(player.getUniqueId());
        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR){
            return;
        }
        if (arachnid.isClimbActive()) {
            // Check if there are solid blocks in all cardinal directions around the player
            boolean hasWallNorth = player.getLocation().getBlock().getRelative(0, 0, -1).isSolid();
            boolean hasWallSouth = player.getLocation().getBlock().getRelative(0, 0, 1).isSolid();
            boolean hasWallEast = player.getLocation().getBlock().getRelative(1, 0, 0).isSolid();
            boolean hasWallWest = player.getLocation().getBlock().getRelative(-1, 0, 0).isSolid();
            boolean hasUpperNorth = player.getLocation().getBlock().getRelative(0, 1, -1).isSolid();
            boolean hasUpperSouth = player.getLocation().getBlock().getRelative(0, 1, 1).isSolid();
            boolean hasUpperEast = player.getLocation().getBlock().getRelative(1, 1, 0).isSolid();
            boolean hasUpperWest = player.getLocation().getBlock().getRelative(-1, 1, 0).isSolid();
            boolean hasFloor = player.getLocation().getBlock().getRelative(0, -1, 0).isSolid();
            if (((hasWallNorth || hasWallSouth || hasWallEast || hasWallWest) && !hasFloor)
                    || (hasUpperEast || hasUpperWest || hasUpperNorth || hasUpperSouth)) {
                if (!player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    PlayerUtils.setFlySpeed(player, VELOCITY);
                }
            } else if (player.isFlying()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                PlayerUtils.resetFlySpeed(player);
            }
        }else if(player.isFlying()){
            player.setFlying(false);
        }
    }

    public static void reload(){
        VELOCITY = (float) ConfigHandler.getArachnidVelocity();
    }
}
