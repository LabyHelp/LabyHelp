package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class LabyHelpHelpCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhhelp";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyMod.getInstance().openWebpage("https://labyhelp.de/commands", false);

        labyPlayer.sendTranslMessage("main.openlink");

        if (LabyHelp.getInstance().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            labyPlayer.sendDefaultMessage("- /lhban <Spieler> / NameTag");
            labyPlayer.sendDefaultMessage("- /lhweb");
        }
    }
}
