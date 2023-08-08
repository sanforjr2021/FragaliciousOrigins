package com.github.sanforjr2021.util.bossBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OriginBossBarManager {
    private static final HashMap<UUID, ArrayList<OriginBossBar>> playerBossBars = new HashMap<UUID, ArrayList<OriginBossBar>>();

    public static void addBossBar(UUID uuid, OriginBossBar bar) {
        if (playerBossBars.containsKey(uuid)) {
            ArrayList<OriginBossBar> playerBars = playerBossBars.get(uuid);
            for (int i = 0; i < playerBars.size(); i++) {
                if (playerBars.get(i).getType().equals(bar.getType())) {
                    return;
                }
            }
            playerBossBars.get(uuid).add(bar);
        } else {
            ArrayList<OriginBossBar> newBossBar = new ArrayList<OriginBossBar>();
            newBossBar.add(bar);
            playerBossBars.put(uuid, newBossBar);
        }
    }

    public static void removeBossBar(UUID uuid, BossBarType type) {
        if (playerBossBars.containsKey(uuid)) {
            ArrayList<OriginBossBar> playerBars = playerBossBars.get(uuid);
            for (int i = 0; i < playerBars.size(); i++) {
                if (playerBars.get(i).getType() == type) {
                    playerBossBars.get(uuid).get(i).removeBossBar();
                    playerBossBars.get(uuid).remove(playerBars.get(i));

                }
            }
        }
    }

    public static void removeBossBars(UUID uuid) {
        if (playerBossBars.containsKey(uuid)) {
           playerBossBars.remove(uuid);
        }
    }

    public static OriginBossBar geOriginBossBar(UUID uuid, BossBarType type) {
        if (playerBossBars.containsKey(uuid)) {
            ArrayList<OriginBossBar> playerBars = playerBossBars.get(uuid);
            for (int i = 0; i < playerBars.size(); i++) {
                if (playerBars.get(i).getType() == type) {
                    return playerBossBars.get(uuid).get(i);
                }
            }
        }
        return null;
    }
}
