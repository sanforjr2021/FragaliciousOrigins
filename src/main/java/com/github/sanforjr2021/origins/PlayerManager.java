package com.github.sanforjr2021.origins;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private static final HashMap<UUID, Origin> playerMap = new HashMap<UUID,Origin>();
    public PlayerManager(){}
    public static void setOrigin(UUID uuid, Origin origin){
        playerMap.put(uuid,origin);
    }
    public static void remove(UUID uuid){
        playerMap.remove(uuid);
    }
    public static boolean contains(UUID uuid){
        return playerMap.containsKey(uuid);
    }
    public static Origin getOrigin(UUID uuid){
        return playerMap.get(uuid);
    }
    public static HashMap<UUID, Origin> getPlayerMap(){
        return playerMap;
    }

}
