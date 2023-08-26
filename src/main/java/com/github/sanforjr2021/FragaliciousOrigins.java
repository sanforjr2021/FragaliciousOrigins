package com.github.sanforjr2021;

import com.github.sanforjr2021.ability.AbilityListener;
import com.github.sanforjr2021.commands.*;
import com.github.sanforjr2021.menus.MenuListener;
import com.github.sanforjr2021.util.bossBar.OriginBossBar;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import com.github.sanforjr2021.data.jdbc.DAOController;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.sanforjr2021.util.MessageUtil.log;

public class FragaliciousOrigins extends JavaPlugin {
    private static FragaliciousOrigins instance;
    private static AbilityListener abilityListener;
    private static DAOController daoController;
    private static PlayerManager playerManager;
    private static ConfigHandler configHandler;
    private static MenuListener menuListener;
    private static OriginBossBarManager originBossBarManager;

    @Override
    public void onEnable() {
        instance = this;
        log("is starting to load");
        configHandler = new ConfigHandler(instance);
        daoController = new DAOController(configHandler.getJdbcURL());
        registerCommands();
        playerManager = new PlayerManager();
        abilityListener = new AbilityListener();
        menuListener = new MenuListener();
        getServer().getPluginManager().registerEvents(abilityListener, this);
        getServer().getPluginManager().registerEvents(menuListener, this);
        originBossBarManager = new OriginBossBarManager();
        log("is loaded");
    }

    public void onDisable() {
        log("is beginning shutdown process");
        PlayerOriginDAO.write(playerManager.getPlayerMap());
        OriginBossBarManager.removeAllBossBars();
        log("is Shut Down");
        //save JDBC info
    }

    public static FragaliciousOrigins getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        configHandler.loadConfig();
        PlayerManager.reload();
        AbilityListener.reload();
    }
    private void registerCommands(){
        this.getCommand("origin").setExecutor(new OriginCommandListener());
        this.getCommand("leavebed").setExecutor(new LeaveBedCommandListener());
        this.getCommand("primaryability").setExecutor(new PrimaryAbilityCommandListener());
        this.getCommand("secondaryability").setExecutor(new SecondaryAbilityCommandListener());
        this.getCommand("shulkchest").setExecutor(new ShulkChestCommandListener());
    }

}
