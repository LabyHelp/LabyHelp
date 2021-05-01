package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;


import java.util.Map;

public class LabyHelpAddonsCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhaddons";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(() -> {

            LabyHelp.getInstance().getStoreHandler().readHelpAddons();
            labyPlayer.sendDefaultMessage("§9" + "LabyHelp Addons:");
            for (Map.Entry<String, String> addons : LabyHelp.getInstance().getStoreHandler().getAddonsList().entrySet()) {

                labyPlayer.sendDefaultMessage("§l" + addons.getKey() +  "§7" + " " + LabyHelp.getInstance().getTranslationManager().getTranslation("main.from") + " " + "§l" + LabyHelp.getInstance().getStoreHandler().getAddonAuthor(addons.getKey()));
            }
        });
    }
}
