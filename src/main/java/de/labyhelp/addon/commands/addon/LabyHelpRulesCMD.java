package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class LabyHelpRulesCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhrules";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyMod.getInstance().openWebpage("https://labyhelp.de/rules", false);
        labyPlayer.sendDefaultMessage("https://labyhelp.de/rules");
    }
}
