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

        String syntax = "/lhdev or /lhdev [setFirstJoin]";

        if (args.length == 0 || args.length == 1) {

            if (LabyHelp.getInstance().getSettingsManager().developerMode) {
                LabyHelp.getInstance().sendTranslMessage("main.developer.mode.off");

                LabyHelp.getInstance().getSettingsManager().developerMode = false;
            } else {
                LabyHelp.getInstance().sendTranslMessage("main.developer.mode.on");

                LabyHelp.getInstance().getSettingsManager().developerMode = true;
            }
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("setFirstJoin")) {
                LabyHelp.getInstance().sendTranslMessage("main.developer.mode.firstjoin");
                LabyHelp.getInstance().changeFirstJoin(true);
            } else {
                LabyHelp.getInstance().sendDefaultMessage(syntax);
            }
        } else {
            LabyHelp.getInstance().sendDefaultMessage(syntax);
        }
    }
}