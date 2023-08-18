package com.github.sanforjr2021.data;

import com.github.sanforjr2021.FragaliciousOrigins;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.*;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import com.github.sanforjr2021.util.time.TimeCycle;
import org.apache.logging.log4j.message.Message;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.sanforjr2021.FragaliciousOrigins.getInstance;

public class PlayerManager {
    private static final HashMap<UUID, Origin> playerMap = new HashMap<UUID,Origin>();
    public PlayerManager(){
        reload();
    }
    public static void setOrigin(UUID uuid, Origin origin){
        setOrigin(uuid,origin,false);
    }
    public static void setOrigin(UUID uuid, Origin origin, boolean joiningServer){
        playerMap.put(uuid,origin);
        if(!joiningServer){ //Is changing origin, and not relogging
            OriginBossBarManager.removeBossBars(uuid);
            PlayerOriginDAO.write(uuid, origin.getOriginType());

        }
    }
    public static void remove(UUID uuid){
        if(contains(uuid)) {
            PlayerOriginDAO.write(uuid, PlayerManager.getOrigin(uuid).getOriginType());
            playerMap.remove(uuid);
            OriginBossBarManager.removeBossBars(uuid);
        }
    }
    public static boolean contains(UUID uuid){
        return playerMap.containsKey(uuid);
    }
    public static Origin getOrigin(UUID uuid){
       if(contains(uuid)){
           return playerMap.get(uuid);
       }
       return null;
    }
    public static HashMap<UUID, Origin> getPlayerMap(){
        return playerMap;
    }

    public static void sleepCheck() {
        int totatPlayers = 0;
        int sleepCount = 0;
        World world = getInstance().getServer().getWorlds().get(0);
        //Calculate sleeping player ratio for overworld
        for (Map.Entry<UUID, Origin> playerOrigin : playerMap.entrySet()) {
            if (playerOrigin.getValue().getPlayer().getWorld() == world) {
                totatPlayers++;
                if (playerOrigin.getValue().isSleeping()) {
                    sleepCount++;
                }
            }
        }
        //Update profile with sleep percentage & skip day if needed
        float percentage = ((float) sleepCount) / ((float) totatPlayers);
        boolean skipDay = percentage >= 0.5;
        String sleepMsg =  "&e" + sleepCount + "/" + totatPlayers + "players sleeping. Skipping to night.";
        if (skipDay) {
            for (Map.Entry<UUID, Origin> playerOrigin : playerMap.entrySet()) {
                if (playerOrigin.getValue().isSleeping()) {
                    playerOrigin.getValue().setSleeping(false);
                }
                MessageUtil.sendMessage(sleepMsg, playerOrigin.getValue().getPlayer());
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    world.setTime(TimeCycle.NIGHT.getTime());
                    cancel();
                }
            }.runTaskTimer(getInstance(), 02l, 10000l);
        }

    }
    public static void reload(){
        Human.reload();
        Phantom.reload();
        Feline.reload();
        Enderian.reload();
        //Update players
        for (Map.Entry<UUID, Origin> mapElement : playerMap.entrySet()) {
            mapElement.getValue().onDeath();
        }
    }

}
