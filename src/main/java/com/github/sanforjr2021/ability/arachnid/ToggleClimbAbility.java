package com.github.sanforjr2021.ability.arachnid;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Arachnid;
import com.github.sanforjr2021.util.MessageUtil;

public class ToggleClimbAbility extends Ability {

    public ToggleClimbAbility(Arachnid arachnid) {
        boolean climbActive = !arachnid.isClimbActive();
        arachnid.setClimbActive(climbActive);
        if(climbActive){
            MessageUtil.sendMessage("Climbing is now &aEnabled", arachnid.getPlayer());
        }else{
            MessageUtil.sendMessage("Climbing is now &cDisabled", arachnid.getPlayer());
        }
    }
}
