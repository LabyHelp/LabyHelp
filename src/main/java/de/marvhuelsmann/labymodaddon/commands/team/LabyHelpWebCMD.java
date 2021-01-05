package de.marvhuelsmann.labymodaddon.commands.team;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class LabyHelpWebCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhweb";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            LabyMod.getInstance().openWebpage("https://labyhelp.de/dashboard/index.php", false);
        } else {
            labyPlayer.sendNoPerms();
        }
    }
}
