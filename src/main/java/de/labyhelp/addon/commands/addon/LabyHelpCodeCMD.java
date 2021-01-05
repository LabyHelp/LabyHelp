package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class LabyHelpCodeCMD implements HelpCommand {

    @Override
    public String getName() {
        return "lhcode";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        if (args.length == 2) {
            UUID uuid = UUIDFetcher.getUUID(args[1]);
            if (uuid != null) {
                LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        if (!args[1].equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
                            if (!LabyHelp.getInstance().getInviteManager().isOldPlayer(LabyMod.getInstance().getPlayerUUID())) {
                                LabyHelp.getInstance().getInviteManager().sendInvite(LabyMod.getInstance().getPlayerUUID(), uuid);
                                clientLabyPlayer.sendTranslMessage("code.enter");

                            } else {
                                clientLabyPlayer.sendTranslMessage("code.newplayer");
                            }
                        } else {
                            clientLabyPlayer.sendTranslMessage("code.redeem.self");
                        }
                    }
                });
            } else {
                clientLabyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            clientLabyPlayer.sendDefaultMessage("- /lhcode -" + LabyHelp.getInstance().getTranslationManager().getTranslation("player"));
        }
    }
}
