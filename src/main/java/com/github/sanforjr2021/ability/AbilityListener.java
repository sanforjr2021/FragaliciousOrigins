package com.github.sanforjr2021.ability;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.sanforjr2021.ability.Chicken.CliffSleepAbility;
import com.github.sanforjr2021.ability.Chicken.ReducedHungerAbility;
import com.github.sanforjr2021.ability.Chicken.SlowfallAbility;
import com.github.sanforjr2021.ability.Chicken.SpawnBirdAbility;
import com.github.sanforjr2021.ability.blazeborn.*;
import com.github.sanforjr2021.ability.enderian.*;
import com.github.sanforjr2021.ability.feline.*;
import com.github.sanforjr2021.ability.phantom.GhostAbility;
import com.github.sanforjr2021.ability.phantom.ShadowSkinAbility;
import com.github.sanforjr2021.ability.shared.*;
import com.github.sanforjr2021.ability.shulker.LevitateEnemyAbility;
import com.github.sanforjr2021.ability.shulker.LevitateOnDamageAbility;
import com.github.sanforjr2021.ability.shulker.ShulkInventoryAbility;
import com.github.sanforjr2021.ability.shulker.ToggleLevitationAbility;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.*;
import com.github.sanforjr2021.util.MessageUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import static com.github.sanforjr2021.FragaliciousOrigins.getInstance;

public class AbilityListener implements Listener {
    public AbilityListener() {
        reload();
        playerChecker();
    }

    private void playerChecker() {

        new BukkitRunnable() {
            int counter = 1;

            @Override
            public void run() {
                for (Map.Entry<UUID, Origin> playerOrigin : PlayerManager.getPlayerMap().entrySet()) {
                    //Every 1/4th of a second
                    switch (playerOrigin.getValue().getOriginType()) { //not sneaking
                        case PHANTOM:
                            new ShadowSkinAbility((Phantom) playerOrigin.getValue());
                            break;
                        case ENDERIAN:
                            new HydrophobicAbility((Enderian) playerOrigin.getValue());
                            break;
                        default:
                            break;
                    }
                    if(counter == 4){
                        switch (playerOrigin.getValue().getOriginType()){
                            case BLAZEBORN:
                                new HeatAbility((Blazeborn) playerOrigin.getValue());
                                break;
                        }
                    }
                }
                if(counter == 4){
                    counter = 1;
                }else{
                    counter++;
                }
            }
        }.runTaskTimer(getInstance(), 20, 5);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case FELINE:
                new WaterWeaknessAbility(e);
            case ARACHNID:
                new NightVisionAbility(e);
                break;
            case ENDERIAN:
                new VoidEscapeAbility(origin);
                break;
            case CHICKEN:
                new SlowfallAbility(e);
                break;
            case UNASSIGNED:
                MessageUtil.sendMessage("You must select an origin first before you can continue", e.getPlayer());
                e.setCancelled(true);
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnterBed(PlayerBedEnterEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case PHANTOM:
            case ARACHNID:
            case FELINE:
                new NocturnalSleepAbility(origin, e);
                break;
            case CHICKEN:
                new CliffSleepAbility(e);
        }
    }
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player){
            Origin origin = PlayerManager.getOrigin(e.getEntity().getUniqueId());
            switch (origin.getOriginType()) {
                case CHICKEN:
                    new ReducedHungerAbility(e);
                case BLAZEBORN:
                    new HungerlessAbility(e);
            }
        }

    }
    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case PHANTOM:
            case ARACHNID:
            case FELINE:
                NocturnalSleepAbility.exitBed(e);
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case FELINE:
                new NineLivesAbility(e);
                break;
            case BLAZEBORN:
                new ExtinguishAbility(e, (Blazeborn) origin);
                break;
        }
    }

    @EventHandler
    public void onHotKey(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        Origin origin = PlayerManager.getOrigin(player.getUniqueId());
        if (player.isSneaking()) { //is sneaking
            secondaryAbility(origin);
        } else {
            primaryAbility(origin);
        }
        e.setCancelled(true);
    }


    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPlayedBefore()) {
            try {
                OriginType originType = PlayerOriginDAO.getOrigin(player.getUniqueId());
                PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player), true);
            } catch (SQLException ex) {
                MessageUtil.sendMessage("&eWelcome back! You may select an origin by typing &c/origin choose <Origintype>", player);
                PlayerManager.setOrigin(player.getUniqueId(), new Unassigned(player));
            }
        }else{
            MessageUtil.sendMessage("&eYou may select an origin by typing &c/origin choose <Origintype>", player);
            PlayerManager.setOrigin(player.getUniqueId(), new Unassigned(player));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
            origin.onDeath();
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent e) {
        //cancel phantom spectator events
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case PHANTOM:
                if (e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                    e.setCancelled(true);
                }
                break;
            case ENDERIAN:
                new TeleportDamageImmunity(e);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Origin origin = PlayerManager.getOrigin(e.getEntity().getUniqueId());
            switch (origin.getOriginType()) {
                case FELINE:
                    new NoFallDamageAbility(e);
                    break;
                case ENDERIAN:
                    new TeleportOnDamageAbility(e);
                    break;
                case SHULK:
                    new LevitateOnDamageAbility(e);
                    break;
                case BLAZEBORN:
                    new HeatLossOnDamageAbility(e);
                default:
                    break;
            }
            //This applies only to nocturnal origins
            if (origin.isSleeping()) {
                origin.setSleeping(false);
            }
        }
    }

    @EventHandler
    public void onAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            Origin origin = PlayerManager.getOrigin(player.getUniqueId());
            switch (origin.getOriginType()){
                case SHULK:
                    new LevitateEnemyAbility(e);
                    break;
                case BLAZEBORN:
                    new FlameAttackAbility(e);
                    break;
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case ARACHNID:
            case FELINE:
                new CarnivoreAbility(e);
                break;
            case CHICKEN:
                new VegetarianAbility(e);
                break;
            case BLAZEBORN:
                new CannotEatFoodAbility(e);
                break;
        }
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (origin.getOriginType() == OriginType.FELINE) {
            new LeapAbility(e);
        }
    }

    @EventHandler
    public void onPlayBreakBlock(BlockBreakEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (origin.getOriginType() == OriginType.FELINE) {
            new WeakMinerAbility(e);
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
        LevitateEnemyAbility.reload();
        LevitateOnDamageAbility.reload();
        ReducedHungerAbility.reload();
        SpawnBirdAbility.reload();
        HeatAbility.reload();
    }

    public static void primaryAbility(Origin origin) {
        switch (origin.getOriginType()) {
            case PHANTOM:
                new GhostAbility((Phantom) origin);
                break;
            case FELINE:
                new PounceAbility(origin.getPlayer());
                break;
            case ENDERIAN:
                new TeleportAbility((Enderian) origin);
                break;
            case SHULK:
                new ShulkInventoryAbility((Shulk) origin);
                break;
            case CHICKEN:
                new SpawnBirdAbility((Chicken) origin);
                break;
            case BLAZEBORN:
                new BurnItemAbility((Blazeborn) origin);
                break;
        }
    }

    public static void secondaryAbility(Origin origin) {
        switch (origin.getOriginType()) {
            case ENDERIAN:
                new ToggleTeleportOnDamageAbility((Enderian) origin);
                break;
            case SHULK:
                new ToggleLevitationAbility((Shulk) origin);
                break;
            case BLAZEBORN:
                new PurgeAbility((Blazeborn) origin);
                break;
        }
    }
}
