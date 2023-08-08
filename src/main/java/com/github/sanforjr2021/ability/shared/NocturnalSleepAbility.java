package com.github.sanforjr2021.ability.shared;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.time.TimeCycle;
import com.github.sanforjr2021.util.time.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

//TODO: Add check for https://wiki.vg/Protocol#Player_Action for bed exit button
public class NocturnalSleepAbility extends Ability {
    public NocturnalSleepAbility(Origin origin, PlayerBedEnterEvent e) {
        Player player = origin.getPlayer();
        if ((e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_NOW || e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK)) {
            if (TimeUtil.getCurrentTime(player) == TimeCycle.DAY) {
                e.setUseBed(Event.Result.ALLOW);
                origin.setSleeping(true);
                MessageUtil.sendMessage("&eYou are sleeping in the day. Type &c/leavebed &e to exit your bed.", player);
                PlayerManager.sleepCheck();
            } else {
                MessageUtil.sendMessage("You can only sleep in the day", player);
                e.setCancelled(true);
            }
        }
        player.setBedSpawnLocation(e.getBed().getLocation());
    }

    public static void exitBed(PlayerBedLeaveEvent e) {
        Player player = e.getPlayer();
        Origin origin = PlayerManager.getOrigin(player.getUniqueId());
        // wake up always unless it is day and is sleeping
        if (TimeUtil.getCurrentTime(player) == TimeCycle.DAY && origin.isSleeping()) {
            e.setCancelled(true);
        }
        else if(origin.isSleeping()){
            origin.setSleeping(false);
        }
    }
}