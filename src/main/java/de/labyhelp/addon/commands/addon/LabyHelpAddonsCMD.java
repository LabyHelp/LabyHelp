package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

public class LabyHelpAddonsCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhaddons";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {

                LabyHelp.getInstance().getStoreHandler().readHelpAddons();
                labyPlayer.sendDefaultMessage(EnumChatFormatting.BLUE + "LabyHelp Addons:");
                for (Map.Entry<String, String> addons : LabyHelp.getInstance().getStoreHandler().getAddonsList().entrySet()) {

                    labyPlayer.sendDefaultMessage(EnumChatFormatting.BOLD + addons.getKey() +  EnumChatFormatting.GRAY + " " + LabyHelp.getInstance().getTranslationManager().getTranslation("main.from") + " " + EnumChatFormatting.BOLD + LabyHelp.getInstance().getStoreHandler().getAddonAuthor(addons.getKey()));

                }
            }
        });
    }
}
