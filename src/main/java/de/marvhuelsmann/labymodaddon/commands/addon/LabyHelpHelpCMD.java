package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class LabyHelpHelpCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhhelp";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyMod.getInstance().openWebpage("https://labyhelp.de/support", false);

        if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            labyPlayer.sendDefaultMessage("- /lhban <Spieler> / NameTag");
            labyPlayer.sendDefaultMessage("- /lhweb");
        }
    }
}
