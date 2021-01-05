package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
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
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        if (!args[1].equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
                            if (!LabyHelp.getInstace().getInviteManager().isOldPlayer(LabyMod.getInstance().getPlayerUUID())) {
                                LabyHelp.getInstace().getInviteManager().sendInvite(LabyMod.getInstance().getPlayerUUID(), uuid);
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
            clientLabyPlayer.sendDefaultMessage("- /lhcode -" + LabyHelp.getInstace().getTranslationManager().getTranslation("player"));
        }
    }
}
