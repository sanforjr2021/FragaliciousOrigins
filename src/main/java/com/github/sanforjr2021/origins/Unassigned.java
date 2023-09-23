package com.github.sanforjr2021.origins;

import com.github.sanforjr2021.util.MessageUtil;
import me.rockyhawk.commandpanels.CommandPanels;
import me.rockyhawk.commandpanels.api.CommandPanelsAPI;
import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.entity.Player;

public class Unassigned extends Origin{
    public Unassigned(Player player) {
        super(OriginType.UNASSIGNED, player);
        CommandPanelsAPI api = CommandPanels.getAPI();
        Panel panel = api.getPanel("main");
        panel.open(player, PanelPosition.Top);
        MessageUtil.sendMessage("If you accidentally close the menu, you can type &c/origin choose&f to bring back up the menu", player);
    }


    @Override
    public void onLogin() {

    }

    @Override
    public void onDeath() {
        CommandPanelsAPI api = CommandPanels.getAPI();
        Panel panel = api.getPanel("main");
        panel.open(getPlayer(), PanelPosition.Top);
        MessageUtil.sendMessage("If you accidentally close the menu, you can type &c/origin choose&f to bring back up the menu", getPlayer());
    }
}
