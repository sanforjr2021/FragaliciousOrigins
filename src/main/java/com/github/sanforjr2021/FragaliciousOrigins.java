package com.github.sanforjr2021;

import com.github.sanforjr2021.ability.AbilityListener;
import com.github.sanforjr2021.commands.LeaveBedCommandListener;
import com.github.sanforjr2021.util.bossBar.OriginBossBarManager;
import com.github.sanforjr2021.commands.CommandListener;
import com.github.sanforjr2021.data.jdbc.DAOController;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.data.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.sanforjr2021.util.MessageUtil.log;

public class FragaliciousOrigins extends JavaPlugin {
    private static FragaliciousOrigins instance;
    private static AbilityListener abilityListener;
    private static DAOController daoController;
    private static PlayerManager playerManager;
    private static ConfigHandler configHandler;
    private static OriginBossBarManager originBossBarManager;

    @Override
    public void onEnable() {
        instance = this;
        log("is starting to load");
        configHandler = new ConfigHandler(instance);
        daoController = new DAOController(configHandler.getJdbcURL());
        registerCommands();
        playerManager = new PlayerManager();
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        originBossBarManager = new OriginBossBarManager();
        syncDatabase();
        log("is loaded");
    }

    public void onDisable() {
        log("is beginning shutdown process");
        PlayerOriginDAO.write(playerManager.getPlayerMap());
        log("is Shut Down");
        //save JDBC info
    }

    private void syncDatabase() {
        new BukkitRunnable() {

            @Override
            public void run() {
                PlayerOriginDAO.write(playerManager.getPlayerMap());
            }
        }.runTaskTimer(getInstance(), 18000l, 18000l);
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
        this.getCommand("origin").setExecutor(new CommandListener());
        this.getCommand("leavebed").setExecutor(new LeaveBedCommandListener());
    }

}
