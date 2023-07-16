package com.github.sanforjr2021;
import com.github.sanforjr2021.ability.AbilityListener;
import com.github.sanforjr2021.commands.CommandListener;
import com.github.sanforjr2021.data.jdbc.DAOController;
import com.github.sanforjr2021.data.jdbc.PlayerOriginDAO;
import com.github.sanforjr2021.origins.PlayerManager;
import com.github.sanforjr2021.util.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.github.sanforjr2021.util.MessageUtil.log;

public class FragaliciousOrigins extends JavaPlugin {
    private static FragaliciousOrigins instance;
    private static CommandListener commandListener;
    private static AbilityListener abilityListener;
    private static DAOController daoController;
    private static PlayerManager playerManager;
    private static ConfigHandler configHandler;
    @Override
    public void onEnable() {

        instance = this;
        configHandler = new ConfigHandler(instance);
        daoController = new DAOController(configHandler.getJdbcURL());
        commandListener = new CommandListener();
        playerManager = new PlayerManager();
        abilityListener = new AbilityListener();


        //setupSyncTimer

    }
    public void onDisable(){
        PlayerOriginDAO.write(playerManager.getPlayerMap());
        log("Shutting Down");
        //save JDBC info
    }

    private void syncDatabase(){
        new BukkitRunnable() {

            @Override
            public void run() {
                PlayerOriginDAO.write(playerManager.getPlayerMap());
            }

        }.runTaskTimer(getInstance(), 20l, 20l);

    }

    public static FragaliciousOrigins getInstance() {
        return instance;
    }

}
