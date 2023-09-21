package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HeatCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        boolean isPlayer = commandSender instanceof Player;

        String playerName = args[0];
        int heat = Integer.parseInt(args[1]);
        Player player = FragaliciousOrigins.getInstance().getServer().getPlayer(playerName);
        try {
            Origin origin = PlayerManager.getOrigin(player.getUniqueId());
            if (origin.getOriginType() == OriginType.BLAZEBORN) {
                ((Blazeborn) origin).setHeat(heat);
                if (isPlayer) {
                    MessageUtil.sendMessage("Updated " + playerName + " heat to value of " + heat, ((Player) commandSender).getPlayer());
                    return true;
                }
            } else {
                MessageUtil.sendMessage("Player is not a Blazeborn", ((Player) commandSender).getPlayer());
                return true;
            }
        } catch (NullPointerException e) {
            MessageUtil.sendMessage("Invalid player or heat.", ((Player) commandSender).getPlayer());
        }
        return false;
    }
}
