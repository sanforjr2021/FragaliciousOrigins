package com.github.sanforjr2021.menus;

import com.github.sanforjr2021.data.jdbc.ShulkInventoryDAO;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ShulkInventoryHolder implements InventoryHolder {

    private Inventory menu;
    private final Player player;

    public ShulkInventoryHolder(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull Inventory getInventory() {
        menu = generateInventoryFromDatabase();
        return menu;
    }

    public void setInventory(Inventory inventory) {
        menu = inventory;
    }

    private Inventory generateInventoryFromDatabase() {
        String items = ShulkInventoryDAO.getShulkData(player.getUniqueId());
        Component inventoryTitle = Component.text("Shulk Inventory");
        Inventory inventory = Bukkit.createInventory(this, 9, inventoryTitle);
        if (items != null) {
            inventory = deserializeInventory(items);
        }

        return inventory;
    }

    public void writeInventoryToDatabase() {
        String data = serializeInventory(menu);
        ShulkInventoryDAO.write(player.getUniqueId(), data);
    }

    //Generated by mortis
    private String serializeInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception exp) {
            exp.printStackTrace();
            return null;
        }
    }

    private Inventory deserializeInventory(String data) {
        if (data == null) {
            return null;
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(this, dataInput.readInt(), Component.text("Shulk Inventory"));
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException | IOException exp) {
            exp.printStackTrace();
        }
        return null;
    }
}