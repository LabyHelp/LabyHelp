package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;


import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InviteListCMD implements HelpCommand {
    @Override
    public String getName() {
        return "invitelist";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(() -> {
            List<Map.Entry<String, Integer>> list = LabyHelp.getInstance().getInviteManager().getTops5();
            int i = 1;
            for (Map.Entry<String, Integer> uuidStringEntry : list) {
                labyPlayer.sendDefaultMessage("§e" + "" + i + "§f" + ": " + "§e" + "§l" + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + "§f" + " with " + "§e" + "§l" + uuidStringEntry.getValue() + "§f" + LabyHelp.getInstance().getTranslationManager().getTranslation("invite.points"));
                i++;
            }
        });
    }
}
