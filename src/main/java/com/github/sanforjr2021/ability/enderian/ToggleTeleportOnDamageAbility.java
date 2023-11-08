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
                MessageUtil.sendMessage("&cWarning - When enabled in the nether, you will teleport to the nether roof and die on hit.", origin.getPlayer());
            }
        }else{
            MessageUtil.sendMessage("You will no longer teleport when taking damage", origin.getPlayer());
        }
    }
}
