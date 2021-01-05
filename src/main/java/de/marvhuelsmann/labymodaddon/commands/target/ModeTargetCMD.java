package de.marvhuelsmann.labymodaddon.commands.target;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.menu.TargetMenu;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.user.User;

import java.util.Map;
import java.util.UUID;

public class ModeTargetCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhmodetarget";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (LabyHelp.getInstace().getSettingsManger().targetMode) {
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Target"));
            labyPlayer.sendTranslMessage("target.left");


            if (LabyHelp.getInstace().getSettingsManger().targetMode) {
                for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                    if (LabyHelp.getInstace().getSettingsManger().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(null);
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }
            }

            LabyHelp.getInstace().getCommunicationManager().targetMode(false);

        } else {
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new TargetMenu());
            labyPlayer.sendTranslMessage("target.join");
            LabyHelp.getInstace().getCommunicationManager().targetMode(true);
        }
    }
}
