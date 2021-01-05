package de.labyhelp.addon.commands.target;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.menu.TargetMenu;
import de.labyhelp.addon.util.commands.HelpCommand;
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
        if (LabyHelp.getInstance().getSettingsManager().targetMode) {
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Target"));
            labyPlayer.sendTranslMessage("target.left");


            if (LabyHelp.getInstance().getSettingsManager().targetMode) {
                for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                    if (LabyHelp.getInstance().getSettingsManager().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(null);
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }
            }

            LabyHelp.getInstance().getCommunicatorHandler().targetMode(false);

        } else {
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new TargetMenu());
            labyPlayer.sendTranslMessage("target.join");
            LabyHelp.getInstance().getCommunicatorHandler().targetMode(true);
        }
    }
}
