package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

public class LabyHelpAddonsCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhaddons";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {

                LabyHelp.getInstace().getStoreHandler().readHelpAddons();
                labyPlayer.sendDefaultMessage(EnumChatFormatting.BLUE + "LabyHelp Addons:");
                for (Map.Entry<String, String> addons : LabyHelp.getInstace().getStoreHandler().getAddonsList().entrySet()) {

                    labyPlayer.sendDefaultMessage(EnumChatFormatting.BOLD + addons.getKey() +  EnumChatFormatting.GRAY + " " + LabyHelp.getInstace().getTranslationManager().getTranslation("main.from") + " " + EnumChatFormatting.BOLD + LabyHelp.getInstace().getStoreHandler().getAddonAuthor(addons.getKey()));

                }
            }
        });
    }
}
