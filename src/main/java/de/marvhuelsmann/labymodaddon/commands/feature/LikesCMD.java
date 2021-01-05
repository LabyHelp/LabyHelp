package de.marvhuelsmann.labymodaddon.commands.feature;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class LikesCMD implements HelpCommand {
    @Override
    public String getName() {
        return "likes";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();
        if (args.length == 2) {

            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
                    final UUID uuid = UUIDFetcher.getUUID(args[1]);

                    if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                        if (LabyHelp.getInstace().getLikeManager().getLikes(uuid).equalsIgnoreCase("1")) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase()+  transManager.getTranslation("likes.has.only") +LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Like!");
                        } else {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() +  transManager.getTranslation("likes.has") + LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Likes!");
                        }
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.hasnot");
                    }
                }
            });

        } else {
            clientLabyPlayer.sendDefaultMessage("/likes -" + transManager.getTranslation("player"));
        }
    }
}
