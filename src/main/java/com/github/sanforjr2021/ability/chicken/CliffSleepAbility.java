package com.github.sanforjr2021.ability.chicken;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class CliffSleepAbility extends Ability {

    public CliffSleepAbility(PlayerBedEnterEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBed().getLocation();
        if (location.getY() < 100) {
            e.setUseBed(Event.Result.DENY);
            MessageUtil.sendMessage("&cYour bed must be at least Y 100 or higher to sleep", player);
        }
    }
}
