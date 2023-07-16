package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.github.sanforjr2021.util.PlayerUtils.getPlayer;


public abstract class Origin {
    private OriginType originType;
    public Origin(OriginType orign){

    }

    public void register(Player player){

    }
    public void onDeath(Player player){

    }
    public void remove(Player player){
        PlayerUtils.removeEffects(player);
        PlayerUtils.resetArmor(player);
        PlayerUtils.resetFlySpeed(player);
        PlayerUtils.resetWalkSpeed(player);
        PlayerUtils.resetMaxHealth(player);
    }
    public OriginType getOriginType() {
        return originType;
    }

    public void reload(){}

}
