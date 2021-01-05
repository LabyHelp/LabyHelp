package de.marvhuelsmann.labymodaddon.commands.feature;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class InvitesCMD implements HelpCommand {
    @Override
    public String getName() {
        return "invites";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();
        if (args.length == 2) {

            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
                    final UUID uuid = UUIDFetcher.getUUID(args[1]);

                    if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                        if (LabyHelp.getInstace().getInviteManager().getNowInvites().equalsIgnoreCase("1")) {
                            labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() +  transManager.getTranslation("likes.has.only") + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") + "!");
                        } else {
                            labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() + transManager.getTranslation("likes.has")  + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") +"!");
                        }
                    } else {
                        labyPlayer.sendTranslMessage("main.hasnot");
                    }
                }
            });

        } else {
            labyPlayer.sendDefaultMessage("/invites -"  + transManager.getTranslation("player"));
        }
    }
}
