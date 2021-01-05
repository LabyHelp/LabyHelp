package de.marvhuelsmann.labymodaddon.commands.team;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.CommunicatorHandler;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class LabyHelpBanCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhban";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();
        if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            if (args.length == 2) {
                final UUID uuid = UUIDFetcher.getUUID(args[1]);
                if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                    if (uuid != null) {
                        clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("player") + EnumChatFormatting.WHITE + args[1] + EnumChatFormatting.RED + transManager.getTranslation("staff.banned.nametag"));

                        clientLabyPlayer.sendDefaultMessage("");

                        CommunicatorHandler.sendBanned(uuid, "NAMETAG");
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendTranslMessage("main.isteam");
                }
            } else {
                clientLabyPlayer.sendTranslMessage("use.ban");
            }
        } else {
            clientLabyPlayer.sendNoPerms();
        }
    }
}
