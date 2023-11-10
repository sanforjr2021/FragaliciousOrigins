package com.github.sanforjr2021.ability;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.sanforjr2021.ability.arachnid.ClimbAbility;
import com.github.sanforjr2021.ability.arachnid.SpiderSensesAbility;
import com.github.sanforjr2021.ability.arachnid.ToggleClimbAbility;
import com.github.sanforjr2021.ability.arachnid.WebbedAbility;
import com.github.sanforjr2021.ability.blazeborn.*;
import com.github.sanforjr2021.ability.chicken.CliffSleepAbility;
import com.github.sanforjr2021.ability.chicken.ReducedHungerAbility;
import com.github.sanforjr2021.ability.chicken.SlowfallAbility;
import com.github.sanforjr2021.ability.chicken.SpawnBirdAbility;
import com.github.sanforjr2021.ability.elytrian.*;
import com.github.sanforjr2021.ability.enderian.*;
import com.github.sanforjr2021.ability.feline.*;
import com.github.sanforjr2021.ability.merling.*;
import com.github.sanforjr2021.ability.phantom.GhostAbility;
import com.github.sanforjr2021.ability.phantom.ShadowSkinAbility;
import com.github.sanforjr2021.ability.shared.*;
import com.github.sanforjr2021.ability.shulker.LevitateEnemyAbility;
import com.github.sanforjr2021.ability.shulker.LevitateOnDamageAbility;
import com.github.sanforjr2021.ability.shulker.ShulkInventoryAbility;
import com.github.sanforjr2021.ability.shulker.ToggleLevitationAbility;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.*;
import com.github.sanforjr2021.util.MessageUtil;
import me.rockyhawk.commandpanels.CommandPanels;
import me.rockyhawk.commandpanels.api.CommandPanelsAPI;
import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import static com.github.sanforjr2021.FragaliciousOrigins.getInstance;

public class AbilityListener implements Listener {
    private final CommandPanelsAPI api = CommandPanels.getAPI();
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
                    if (counter == 4) {
                        switch (playerOrigin.getValue().getOriginType()) {
                            case BLAZEBORN:
                                new HeatAbility((Blazeborn) playerOrigin.getValue());
                                break;
                            case MERLING:
                                new MerlingPassiveAbility((Merling) playerOrigin.getValue());
                                break;
                        }
                    }
                }
                if (counter == 4) {
                    counter = 1;
                } else {
                    counter++;
                }
            }
        }.runTaskTimer(getInstance(), 20, 5);
    }

    @EventHandler
    public void onPlayerFlight(PlayerToggleFlightEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (origin.getOriginType() == OriginType.ELYTRIAN) {
            new SpreadWingsAbility(e);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        try {
            Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
            switch (origin.getOriginType()) {
                case FELINE:
                    new WaterWeaknessAbility(e);
                    new NightVisionAbility(e);
                    break;
                case ARACHNID:
                    new NightVisionAbility(e);
                    new ClimbAbility(e);
                    break;
                case ENDERIAN:
                    new VoidEscapeAbility(origin);
                    break;
                case CHICKEN:
                    new SlowfallAbility(e);
                    break;
                case MERLING:
                    new SpeedSwimAbility(e);
                    break;
                case ELYTRIAN:
                    new ElytrianMovementAbility(e);
                    break;
                case UNASSIGNED:
                    Panel panel = api.getPanel("main");
                    panel.open(origin.getPlayer(), PanelPosition.Top);
                    break;
            }
        } catch (NullPointerException exception) {
            PlayerManager.setOrigin(e.getPlayer().getUniqueId(), OriginType.UNASSIGNED.getOrigin(e.getPlayer()));
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
                break;
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Origin origin = PlayerManager.getOrigin(e.getEntity().getUniqueId());
            switch (origin.getOriginType()) {
                case CHICKEN:
                    new ReducedHungerAbility(e);
                    break;
                case BLAZEBORN:
                    new HungerlessAbility(e);
                    break;
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
            case MERLING:
                new TridentTotemAbility(e, (Merling) origin);
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
                if (originType == null) {
                    originType = OriginType.UNASSIGNED;
                }
                PlayerManager.setOrigin(player.getUniqueId(), originType.getOrigin(player), true);
            } catch (SQLException ex) {
                MessageUtil.sendMessage("&eWelcome back! You may select an origin by typing &c/origin choose <Origintype>", player);
                PlayerManager.setOrigin(player.getUniqueId(), new Unassigned(player));
            }
        } else {
            MessageUtil.sendMessage("&eYou may select an origin by typing &c/origin choose <Origintype>", player);
            PlayerManager.setOrigin(player.getUniqueId(), new Unassigned(player));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        origin.onDeath();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent e) {
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
    public void onWorldChange(PlayerChangedWorldEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        switch (origin.getOriginType()) {
            case ENDERIAN:
                Enderian enderian = (Enderian) origin;
                if(enderian.isTeleportOnDamage() == true && e.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER){
                    secondaryAbility(enderian);
                }
        }
    }
    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(!(player.getPlayer().getGameMode() == GameMode.SURVIVAL)){
                return;
            }
            Origin origin = PlayerManager.getOrigin(e.getEntity().getUniqueId());
            if (origin.getOriginType() == OriginType.ELYTRIAN) {
                new SpreadWingsAbility(e);
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(!(player.getPlayer().getGameMode() == GameMode.SURVIVAL)){
                return;
            }
            if(e.isCancelled()){
                return;
            }
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
                    break;
                case ELYTRIAN:
                    new ImpactAbility(e);
                    break;
                default:
                    break;
            }
            //This applies only to nocturnal origins
            if (origin.isSleeping()) {
                origin.setSleeping(false);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttackEntity(EntityDamageByEntityEvent e) {
        if(e.isCancelled()){
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if(!(player.getPlayer().getGameMode() == GameMode.SURVIVAL)){
                return;
            }
            Origin origin = PlayerManager.getOrigin(player.getUniqueId());
            switch (origin.getOriginType()) {
                case SHULK:
                    new LevitateEnemyAbility(e);
                    break;
                case BLAZEBORN:
                    new FlameAttackAbility(e);
                    break;
                case MERLING:
                    new TridentAmplifierAbility(e);
                    break;
                case ARACHNID:
                    new WebbedAbility(e);
                    break;
            }
        } else if (e.getDamager() instanceof Trident) {
            new ThrownTridentAmplifierAbility(e);
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
            case MERLING:
                new MerlingWaterDrink(e);
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
        if(!(e.getPlayer().getGameMode() == GameMode.SURVIVAL)){
            return;
        }
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (origin.getOriginType() == OriginType.FELINE) {
            new WeakMinerAbility(e);
        }
    }
    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent e){
        Origin origin = PlayerManager.getOrigin(e.getPlayer().getUniqueId());
        if (origin.getOriginType() == OriginType.PHANTOM && origin.getPlayer().getGameMode() == GameMode.SPECTATOR && e.isSprinting()) {
            GhostAbility.sprintExitSpectator((Phantom) origin);
        }
    }
    ///////////////////////////////////////////////////////////////
    //                    Utility Methods                       //
    //////////////////////////////////////////////////////////////
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
            case MERLING:
                new ToggleSpeedSwimAbility((Merling) origin);
                break;
            case ARACHNID:
                new SpiderSensesAbility((Arachnid) origin);
                break;
            case ELYTRIAN:
                new BoostAbility((Elytrian) origin);
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
            case ARACHNID:
                new ToggleClimbAbility((Arachnid) origin);
                break;
            case ELYTRIAN:
                new ToggleWingsAbility((Elytrian) origin);
                break;
        }
    }

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
        HeatLossOnDamageAbility.reload();
        HeatAbility.reload();
        TridentTotemAbility.reload();
        SpeedSwimAbility.reload();
        MerlingPassiveAbility.reload();
        MerlingWaterDrink.reload();
        TridentAmplifierAbility.reload();
        ThrownTridentAmplifierAbility.reload();
        WebbedAbility.reload();
        ClimbAbility.reload();
        SpiderSensesAbility.reload();
        BoostAbility.reload();
        ElytrianMovementAbility.reload();
        ImpactAbility.reload();
    }


}
