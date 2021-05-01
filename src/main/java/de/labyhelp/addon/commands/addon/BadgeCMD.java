package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;

public class BadgeCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhreward";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (LabyHelp.getInstance().getSettingsManager().currentServer.equalsIgnoreCase("labyhelp.de")) {
            if (!LabyHelp.getInstance().getTagManager().visitLabyHelpServer.contains(labyPlayer.getUuid())) {
                labyPlayer.sendTranslMessage("main.badge.labyhelp.get");
                LabyHelp.getInstance().getRequestManager().sendRequest("https://labyhelp.de/getLabyHelpTag.php?uuid=" + labyPlayer.getUuid());
            } else {
                labyPlayer.sendTranslMessage("main.badge.labyhelp.has");
            }
        } else {
            labyPlayer.sendTranslMessage("main.labyhelp.server.null");
        }
    }
}
