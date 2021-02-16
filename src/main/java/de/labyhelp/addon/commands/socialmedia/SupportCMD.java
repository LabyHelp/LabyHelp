package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;

public class SupportCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhsupport";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        labyPlayer.sendTranslMessage("main.support");
    }
}
