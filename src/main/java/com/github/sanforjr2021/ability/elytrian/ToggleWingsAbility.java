package com.github.sanforjr2021.ability.elytrian;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Elytrian;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.entity.Player;

public class ToggleWingsAbility extends Ability {

    public ToggleWingsAbility(Elytrian elytrian) {
        boolean wingsAbility = !elytrian.isWingsActive();
        Player player = elytrian.getPlayer();
        if(wingsAbility){
            MessageUtil.sendMessage("Wings Ability is &aActivated", elytrian.getPlayer());
            if(!PlayerUtils.isOnGround(player)){
                player.setGliding(true);
            }
        }else{
            MessageUtil.sendMessage("Wings Ability is &cDeactivated", elytrian.getPlayer());
            if(player.isGliding()){
                player.setGliding(false);
            }
            player.setAllowFlight(false);

        }
        elytrian.setWingsActive(wingsAbility);
    }
}
