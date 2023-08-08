package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        boolean isPlayer = commandSender instanceof Player;
        switch (strings[0].toLowerCase()){
            case "set":
                try{
                    OriginType originType = OriginType.valueOf(strings[1].toUpperCase());
                    Player player = Bukkit.getPlayer(strings[2]);
                    PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player));
                    if(isPlayer){
                        MessageUtil.sendMessage(player.getName() + "is set as a " + originType , ((Player) commandSender).getPlayer());
                        return true;
                    }
                }catch (Exception e){
                    MessageUtil.logWarning(e.toString());
                    return false;
                }
                return false;
            case "choose":
                if(isPlayer){
                    try{
                        OriginType originType = OriginType.valueOf(strings[1].toUpperCase());
                        Player player = ((Player) commandSender).getPlayer();
                        PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player));
                    }catch(Exception e){
                        MessageUtil.logWarning(e.toString());
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("choose");
            arguments.add("set");
            return arguments;
        }
        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("set") || strings[0].equalsIgnoreCase("choose")) {
                List<String> arguments = new ArrayList<>();
                for (OriginType type : OriginType.values()) {
                    arguments.add(type.name());
                }
                return arguments;
            }
        }
        return null;
    }
}
