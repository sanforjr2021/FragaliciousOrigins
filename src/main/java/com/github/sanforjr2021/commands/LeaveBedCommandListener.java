package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveBedCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            Origin origin = PlayerManager.getOrigin(player.getUniqueId());
            if(origin.isSleeping()){
                origin.setSleeping(false);
                MessageUtil.sendMessage("&eExiting Bed", player);
            } else{
                MessageUtil.sendMessage("&cOnly nocturnal sleeping players may use this command", player);
            }
            return true;
        }
        MessageUtil.logWarning("Only players can use the /leavebed command");
        return true;
    }

}
