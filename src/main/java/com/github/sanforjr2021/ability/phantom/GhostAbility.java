package com.github.sanforjr2021.ability.phantom;

import com.github.sanforjr2021.ability.Ability;
import com.github.sanforjr2021.util.bossBar.BossBarType;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import com.github.sanforjr2021.origins.Phantom;
import com.github.sanforjr2021.util.MessageUtil;
import com.github.sanforjr2021.util.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static com.github.sanforjr2021.FragaliciousOrigins.getInstance;
import static com.github.sanforjr2021.util.ConfigHandler.*;

public class GhostAbility extends Ability {
    private static int GHOST_TIME, GHOST_COOLDOWN;
    private static float GHOST_SPEED;

    public GhostAbility(Phantom phantom){ //TODO: Rewrite phantom ghost mode to not use spectator.
         Player player = phantom.getPlayer();
         UUID uuid = player.getUniqueId();
        if(phantom.getGhostTimeRemaining() > 0){
            MessageUtil.sendMessage("&eYou are force exiting Ghost Mode", player);
            //force exit ghost mode
            exitSpectator(phantom);
        }else{
            if(phantom.getGhostCooldownRemaining() == 0){
                //enable spectator
                phantom.setGhostCooldownRemaining(GHOST_COOLDOWN);
                PlayerUtils.setFlySpeed(player, GHOST_SPEED);
                player.setGameMode(GameMode.SPECTATOR);
                phantom.setGhostTimeRemaining(GHOST_TIME);
                OriginBossBarManager.addBossBar(player.getUniqueId(), new OriginBossBar(player, BossBarType.GHOST_MODE, ChatColor.LIGHT_PURPLE + "Ghost Time", BarColor.PURPLE, 1.0));
                MessageUtil.sendMessage("&eYou are entering Ghost Mode", player);
                //begin timer
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try{
                            //if cooldown is not over
                            if(phantom.getGhostTimeRemaining() > 0) {
                                //if still in ghost mode.
                                //update bossbar
                                OriginBossBarManager.geOriginBossBar(uuid, BossBarType.GHOST_MODE).setProgress(phantom.getGhostTimeRemaining(),GHOST_TIME);
                                int ghostTimeRemaining = phantom.getGhostTimeRemaining();
                                phantom.setGhostTimeRemaining(ghostTimeRemaining - 5);
                                //check needs darkness
                                boolean isSolid = player.getLocation().getBlock().isSolid();
                                if (isSolid && !player.hasPotionEffect(PotionEffectType.DARKNESS)) {
                                    PlayerUtils.addEffect(player, PotionEffectType.DARKNESS, 0);
                                } else if (!isSolid && player.hasPotionEffect(PotionEffectType.DARKNESS)) { //remove player blindness when above ground
                                    PlayerUtils.removeEffect(player, PotionEffectType.DARKNESS);
                                }
                                if(phantom.getGhostTimeRemaining() == 0){
                                    exitSpectator(phantom);
                                }
                            }
                            else if (player.getGameMode() == GameMode.SPECTATOR ){
                                exitSpectator(phantom);
                            }
                            //Update Cooldown
                            phantom.setGhostCooldownRemaining(phantom.getGhostCooldownRemaining() - 5);
                            if(phantom.getGhostCooldownRemaining() == 0){
                                cancel();
                            }
                        }catch (Exception e){
                            phantom.setGhostCooldownRemaining(0);
                            phantom.setGhostTimeRemaining(0);
                            if(phantom.getPlayer() != null){
                                exitSpectator(phantom);
                            }
                            cancel();
                        }
                    }
                }.runTaskTimer(getInstance(), 5l, 5l);
            }else{
                //if on cooldown still
                sendCooldownMessage("Ghost Mode", phantom.getGhostCooldownRemaining()/20, player);
            }
        }
    }
    private void exitSpectator(Phantom phantom){
        Player player = phantom.getPlayer();
        PlayerUtils.resetFlySpeed(player);
        player.setGameMode(GameMode.SURVIVAL);
        phantom.setGhostTimeRemaining(0);
        PlayerUtils.removeEffect(player, PotionEffectType.DARKNESS);
        OriginBossBarManager.removeBossBar(player.getUniqueId(), BossBarType.GHOST_MODE);
        player.setFallDistance(0);
        PlayerUtils.addEffect(player, PotionEffectType.SLOW_FALLING,0,40);
    }

    public static void reload() {
        GHOST_COOLDOWN = getPhantomGhostmodeCooldown() * 20;
        GHOST_TIME = getPhantomGhostModeDuration() * 20;
        GHOST_SPEED = (float) getPhantomGhostmodeSpeed();
    }
}
