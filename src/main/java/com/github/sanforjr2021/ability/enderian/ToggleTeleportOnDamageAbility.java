package com.github.sanforjr2021.ability.enderian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Enderian;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.World;

public class ToggleTeleportOnDamageAbility extends Ability {

    public ToggleTeleportOnDamageAbility(Enderian origin) {
        boolean teleportOnDamage = !origin.isTeleportOnDamage();
        origin.setTeleportOnDamage(teleportOnDamage);
        if(teleportOnDamage){
            MessageUtil.sendMessage("You will now teleport when taking damage", origin.getPlayer());
            if(origin.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER){
                MessageUtil.sendMessage("&4WARNING: &cYou are playing as extreme Enderian mode. You will die and likely lose your items when taking damage. Use at your own risk", origin.getPlayer());
            }
        }else{
            MessageUtil.sendMessage("You will no longer teleport when taking damage", origin.getPlayer());
        }
    }
}
