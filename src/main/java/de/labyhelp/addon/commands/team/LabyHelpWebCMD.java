package de.labyhelp.addon.commands.team;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class LabyHelpWebCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhweb";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
            LabyMod.getInstance().openWebpage("https://labyhelp.de/dashboard/index.php", false);
    }
}
