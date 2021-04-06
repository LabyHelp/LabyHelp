package de.labyhelp.addon.commands.team;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.CommunicatorHandler;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
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
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        if (LabyHelp.getInstance().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            if (args.length == 2) {
                final UUID uuid = UUIDFetcher.getUUID(args[1]);
                if (!LabyHelp.getInstance().getGroupManager().isTeam(uuid)) {
                    if (uuid != null) {
                        clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("player") + " " + EnumChatFormatting.WHITE + args[1] + "" + EnumChatFormatting.WHITE + transManager.getTranslation("staff.banned.nametag"));

                        if (LabyHelp.getInstance().getCommunicatorHandler().sendBanned(uuid, "NAMETAG") != null) {
                            clientLabyPlayer.sendDefaultMessage("");
                        } else {
                            clientLabyPlayer.sendDefaultMessage("Oups error");
                        }

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
