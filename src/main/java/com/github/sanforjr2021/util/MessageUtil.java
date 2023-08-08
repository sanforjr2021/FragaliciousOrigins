package com.github.sanforjr2021.util;

import com.github.sanforjr2021.FragaliciousOrigins;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import static com.github.sanforjr2021.util.ConfigHandler.getMsgPrefix;

public class MessageUtil {
    public static void logError(String error){
        FragaliciousOrigins.getInstance().getLogger().severe(error);
    }
    public static void log(String msg){
        FragaliciousOrigins.getInstance().getLogger().severe(msg);
    }
    public static void logWarning(String warning){
        FragaliciousOrigins.getInstance().getLogger().warning(warning);
    }
    public static void sendMessage(String msg, Player player){
        TextComponent textComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(getMsgPrefix() + msg);
        player.sendMessage(textComponent);
    }
}
