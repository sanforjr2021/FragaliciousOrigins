package com.github.sanforjr2021.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    public MenuListener() {

    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof ShulkInventoryHolder) {
            ShulkInventoryHolder shulkInventoryHolder = (ShulkInventoryHolder) e.getInventory().getHolder();
            shulkInventoryHolder.setInventory(e.getInventory());
            shulkInventoryHolder.writeInventoryToDatabase();
        }
    }

    @EventHandler
    public void onInventoryUpdate(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof ShulkInventoryHolder) {
            ShulkInventoryHolder shulkInventoryHolder = (ShulkInventoryHolder) e.getInventory().getHolder();
            shulkInventoryHolder.setInventory(e.getClickedInventory());
        }
    }
}
