package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;

public class LabyHelpDeveloperCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhdev";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {

        if (LabyHelp.getInstance().getSettingsManager().developerMode) {
            LabyHelp.getInstance().sendTranslMessage("main.developer.mode.off");

            LabyHelp.getInstance().getSettingsManager().developerMode = false;
        } else {
            LabyHelp.getInstance().sendTranslMessage("main.developer.mode.on");

            LabyHelp.getInstance().getSettingsManager().developerMode = true;
        }
    }
}