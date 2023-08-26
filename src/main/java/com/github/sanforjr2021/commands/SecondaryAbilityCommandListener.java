package com.github.sanforjr2021.commands;

import com.github.sanforjr2021.ability.AbilityListener;
import com.github.sanforjr2021.origins.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SecondaryAbilityCommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            AbilityListener.secondaryAbility(PlayerManager.getOrigin(player.getUniqueId()));
            return true;
        }
        return false;
    }
}
