package com.github.sanforjr2021.ability.blazeborn;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.origins.Blazeborn;
import com.github.sanforjr2021.util.ConfigHandler;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HeatAbility extends Ability {
    private static int ON_FIRE_GAIN;
    public HeatAbility(Blazeborn blazeborn) {
        Blazeborn.HeatLevel heatLevel = blazeborn.getLevel();
        Player player = blazeborn.getPlayer();
        int drain = 0;
        //Gain heat when on fire
        if(heatLevel.isPassiveBurn() && player.getFireTicks() > 0){
            drain +=  ON_FIRE_GAIN;
        }else{
            //If nether, don't drain, else check for passive and wetnesss
            if(!(player.getWorld().getEnvironment() == World.Environment.NETHER && !heatLevel.isDrainInNether())){
                drain = drain - heatLevel.getPassiveDrain();
                if(PlayerUtils.isWet(player)){
                    drain = drain - heatLevel.getWaterDrain();
                }
            }
        }
        //player particle effect
        switch (heatLevel){
            case INCENDIUM:
                PlayerUtils.generateParticle(player, Material.NETHERITE_BLOCK, 5);
            case INFERNO:
                PlayerUtils.generateParticle(player, Material.CRYING_OBSIDIAN,5);
            case FlAME:
                PlayerUtils.generateParticle(player, Particle.FLAME, 5);
            case KINDLE:
                PlayerUtils.generateParticle(player, Particle.ASH, 3);
            break;
        }

        blazeborn.setHeat(blazeborn.getHeat() + drain);
        //murder player if 0 heat
        if(blazeborn.getHeat() <= 0){
            player.setHealth(0);
        }
    }
    
    public static void reload(){
        ON_FIRE_GAIN = ConfigHandler.getBlazeOnFire();
    }
}
