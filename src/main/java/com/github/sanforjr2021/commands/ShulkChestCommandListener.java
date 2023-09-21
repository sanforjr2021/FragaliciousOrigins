package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.data.jdbc.ShulkInventoryDAO;
import com.github.sanforjr2021.menus.ShulkInventoryHolder;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShulkChestCommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender);
            if (args.length == 0) {
                return false;
            }
            String playerName = args[0];
            Player shulk = FragaliciousOrigins.getInstance().getServer().getPlayer(playerName);
            String data = ShulkInventoryDAO.getShulkData(player.getUniqueId());
            if (data != null) {
                ShulkInventoryHolder holder = new ShulkInventoryHolder(shulk);
                player.openInventory(holder.getInventory());
            } else {
                MessageUtil.log("&cUser does not have a registered shulk chest");
            }
        }
        return false;
    }
}
