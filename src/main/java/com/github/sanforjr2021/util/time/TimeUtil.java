package com.github.sanforjr2021.util.time;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class TimeUtil {
    public static TimeCycle getCurrentTime(Player player){
        long currentTick = player.getWorld().getTime() % 24000;
        if(currentTick >= 23000){
            return TimeCycle.SUNRISE;
        }else if( currentTick >= 13000){
            return TimeCycle.NIGHT;
        }else if(currentTick >= 12000){
            return TimeCycle.SUNSET;
        }else{
            return TimeCycle.DAY;
        }
    }
    public static  MoonCycle getMoonCycle(Player player){
        int currentPhase = (int) ((player.getWorld().getFullTime() / 24000) / 8);
        switch(currentPhase) {
            case 0:
                return MoonCycle.FULL_MOON;
            case 2:
                return MoonCycle.WANING_GIBBOUS;
            case 3:
                return MoonCycle.LAST_QUARTER;
            case 4:
                return MoonCycle.WANING_CRESCENT;
            case 5:
                return MoonCycle.NEW_MOON;
            case 6:
                return MoonCycle.WAXING_CRESCENT;
            case 7:
                return MoonCycle.FIRST_QUARTER;
            default:
                return MoonCycle.WAXING_GIBBOUS;
        }
    }
    public static void setDay(Player player){
        World world = player.getWorld();
        long currentTick = player.getWorld().getTime() % 24000;

    }
}
