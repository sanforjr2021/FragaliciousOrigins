package com.github.sanforjr2021.ability.shulker;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.menus.ShulkInventoryHolder;
import com.github.sanforjr2021.origins.Shulk;
import org.bukkit.entity.Player;

public class ShulkInventoryAbility extends Ability {

    public ShulkInventoryAbility(Shulk origin) {
        Player player = origin.getPlayer();
        ShulkInventoryHolder shulkInventoryHolder = new ShulkInventoryHolder(player);
        player.openInventory(shulkInventoryHolder.getInventory());
    }
}
