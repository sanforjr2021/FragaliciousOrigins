package com.github.sanforjr2021.ability.feline;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

public class WeakMiner extends Ability {
    public WeakMiner(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if (isStone(block) && !player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            int count = 0;
            if (isStone(getBlockOffCenter(block, 0, 0, 1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, 0, -1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, 1, 0))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 0, -1, 1))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, 1, 0, 0))) {
                count++;
            }
            if (isStone(getBlockOffCenter(block, -1, 0, 0))) {
                count++;
            }
            if(count > 2){
                MessageUtil.sendMessage("You cannot mine stone with 2 adjacent stone", player);
                e.setCancelled(true);
            }
        }
    }

    private boolean isStone(Block block) {
        switch (block.getType()) {
            case STONE:
            case DIORITE:
            case DRIPSTONE_BLOCK:
            case POINTED_DRIPSTONE:
            case GRANITE:
            case ANDESITE:
            case SANDSTONE:
            case BASALT:
            case DEEPSLATE:
                return true;
            default:
                return false;
        }
    }

    private Block getBlockOffCenter(Block block, int x, int y, int z) {
        int adjustedX = block.getLocation().getBlockX() + x;
        int adjustedY = block.getLocation().getBlockY() + y;
        int adjustedZ = block.getLocation().getBlockZ() + z;
        Location location = new Location(block.getWorld(), adjustedX, adjustedY, adjustedZ);
        return block.getWorld().getBlockAt(location);
    }
}
