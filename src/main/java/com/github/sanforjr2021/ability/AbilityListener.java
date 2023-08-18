package com.github.sanforjr2021.ability;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.sanforjr2021.ability.enderian.*;
import com.github.sanforjr2021.ability.feline.*;
import com.github.sanforjr2021.ability.phantom.GhostAbility;
import com.github.sanforjr2021.ability.phantom.ShadowSkinAbility;
import com.github.sanforjr2021.ability.shared.CarnivoreAbility;
import com.github.sanforjr2021.ability.shared.NightVisionAbility;
import com.github.sanforjr2021.ability.shared.NocturnalSleepAbility;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.Enderian;
import com.github.sanforjr2021.origins.Origin;
import com.github.sanforjr2021.origins.OriginType;
import com.github.sanforjr2021.origins.Phantom;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import static com.github.sanforjr2021.FragaliciousOrigins.getInstance;

public class AbilityListener implements Listener {
    public AbilityListener(){
        reload();
        playerChecker();
    }

    private void playerChecker() {

        new BukkitRunnable() {
            int counter = 0;
            @Override
            public void run() {
                for (Map.Entry<UUID, Origin> playerOrigin : PlayerManager.getPlayerMap().entrySet()) {
                    //Every 1/4th of a second
                    switch (playerOrigin.getValue().getOriginType()) { //not sneaking
                        case PHANTOM:
                            new ShadowSkinAbility((Phantom) playerOrigin.getValue());
                            break;
                        default:
                            break;
                    }
                    //every seconds
                    if(counter == 1){
                        switch (playerOrigin.getValue().getOriginType()){
                            case ENDERIAN:
                                new HydrophobicAbility((Enderian)playerOrigin.getValue());
                        }
                    }
                }
                counter++;
                if(counter > 3){
                    counter = 0;
                }
            }
        }.runTaskTimer(getInstance(), 20, 5);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case FELINE:
                new WaterWeaknessAbility(e);
            case ARACHNID:
                new NightVisionAbility(e);
                break;
            case ENDERIAN:
                new VoidEscapeAbility(origin);
            default:
                break;
        }
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case PHANTOM:
            case ARACHNID:
            case FELINE:
                new NocturnalSleepAbility(origin, e);
                break;
        }
    }
    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case PHANTOM:
            case ARACHNID:
            case FELINE:
                NocturnalSleepAbility.exitBed(e);
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case FELINE:
                new NineLivesAbility(e);
                break;
            case ENDERIAN:
                Enderian enderian = (Enderian) PlayerManager.getOrigin(e.getPlayer().getUniqueId());
                if(enderian.isTeleportInvulnerability()){
                    e.setCancelled(true);
                }
                break;
            default:
                break;
        }
    }
    @EventHandler
    public void onHotKey(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        Origin origin = PlayerManager.getOrigin(player.getUniqueId());
        if(player.isSneaking()){ //is sneaking
            switch(getOriginType(player)){
                case ENDERIAN:
                    new ToggleTeleportOnDamageAbility((Enderian) origin);
                    break;
                default:
                break;
            }
        }else{
            switch(getOriginType(player)) { //not sneaking
                case PHANTOM:
                    new GhostAbility((Phantom) origin);
                    break;
                case FELINE:
                    new PounceAbility(player);
                    break;
                case ENDERIAN:
                    new TeleportAbility((Enderian) origin);
                    break;
                case HUMAN:
                    MessageUtil.sendMessage("Your not so sneaky" , player);
                    //do nothing
                    break;
            }
        }
        e.setCancelled(true);
    }



    @EventHandler
    public void onLogin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(player.hasPlayedBefore()) {
            try {
                OriginType originType = PlayerOriginDAO.getOrigin(player.getUniqueId());
                PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player), true);
            } catch (SQLException ex) {
                MessageUtil.sendMessage("&eWelcome back! You may select an origin by typing &c/origin choose <Origintype>", player);
            }
        }
        MessageUtil.sendMessage("&eYou may select an origin by typing &c/origin choose <Origintype>", player);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent e){
        //cancel phantom spectator events
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch(origin.getOriginType()){
            case PHANTOM:
                if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                    e.setCancelled(true);
                }
                break;
            case ENDERIAN:
                new TeleportDamageImmunity(e);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof  Player){
            Origin origin = PlayerManager.getOrigin(e.getEntity().getUniqueId());
            switch(origin.getOriginType()){
                case FELINE:
                    new NoFallDamageAbility(e);
                    break;
                case ENDERIAN:
                    new TeleportOnDamageAbility(e);
                    break;
                default:
                    break;
            }
            //This applies only to nocturnal origins
            if(origin.isSleeping()){
                origin.setSleeping(false);
            }
        }
    }
    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case ARACHNID:
            case FELINE:
                new CarnivoreAbility(e);
                break;
        }
    }
    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case FELINE:
                new LeapAbility(e);
            default:
                break;
        }
    }
    @EventHandler
    public void onPlayBreakBlock(BlockBreakEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()){
            case FELINE:
                    new WeakMinerAbility(e);
                break;
            default:
                break;
        }
    }
    ///////////////////////////////////////////////////////////////
    //                    Utility Methods                       //
    //////////////////////////////////////////////////////////////
    public static void reload() {
        GhostAbility.reload();
        ShadowSkinAbility.reload();
        NocturnalSleepAbility.reload();
        LeapAbility.reload();
        PounceAbility.reload();
        TeleportAbility.reload();
        TeleportOnDamageAbility.reload();
    }
    private OriginType getOriginType(Player player){
        Origin origin = PlayerManager.getOrigin(player.getUniqueId());
        if(origin == null){ //no origin set
            return null;
        }
        return PlayerManager.getOrigin(player.getUniqueId()).getOriginType();
    }
}
