package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.MessageUtil;
import me.rockyhawk.commandpanels.CommandPanels;
import me.rockyhawk.commandpanels.api.CommandPanelsAPI;
import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OriginCommandListener implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0){
            return false;
        }
        boolean isPlayer = commandSender instanceof Player;
        switch (strings[0].toLowerCase()) {
            case "set":
                if (!commandSender.hasPermission("fragalicious.origins.command.set")) {
                    return false;
                }
                try {
                    OriginType originType = OriginType.valueOf(strings[1].toUpperCase());
                    Player player = Bukkit.getPlayer(strings[2]);
                    PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player));
                    if (isPlayer) {
                        MessageUtil.sendMessage(player.getName() + "is set as a " + originType, ((Player) commandSender).getPlayer());
                        return true;
                    }
                } catch (Exception e) {
                    MessageUtil.logWarning(e.toString());
                    return false;
                }
                return false;
            case "choose":
                if (isPlayer) {
                    try {
                        OriginType originType = OriginType.valueOf(strings[1].toUpperCase());
                        Player player = ((Player) commandSender).getPlayer();

                        if(!(PlayerManager.getOrigin(player.getUniqueId()) == null || PlayerManager.getOrigin(player.getUniqueId()).getOriginType() == OriginType.UNASSIGNED)){
                            MessageUtil.sendMessage("&cYou cannot select an origin if you already have an origin.", player);
                            return false;
                        }
                        PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player));
                        CommandPanelsAPI api = CommandPanels.getAPI();
                        if (api.hasNormalInventory(player)) {
                            player.getOpenInventory().close();
                        }

                        return true;
                    } catch (Exception e) {
                        openMenu((Player) commandSender);
                        return true;
                    }
                }
                return true;
        }
        return false;
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

    private void openMenu(Player player) {
        CommandPanelsAPI api = CommandPanels.getAPI();
        Panel panel = api.getPanel("main");
        panel.open(player, PanelPosition.Top);
    }
}
