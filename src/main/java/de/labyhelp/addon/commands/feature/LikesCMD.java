package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class LikesCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhlikes";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        if (args.length == 2) {

            LabyHelp.getInstance().getExecutor().submit(() -> {
                final UUID uuid = UUIDFetcher.getUUID(args[1]);

                if (LabyHelp.getInstance().getGroupManager().userGroups.containsKey(uuid)) {
                    if (LabyHelp.getInstance().getLikeManager().getLikes(uuid).equalsIgnoreCase("1")) {
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase()+  transManager.getTranslation("likes.has.only") +LabyHelp.getInstance().getLikeManager().getLikes(uuid) + " Like!");
                    } else {
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() +  transManager.getTranslation("likes.has") + LabyHelp.getInstance().getLikeManager().getLikes(uuid) + " Likes!");
                    }
                } else {
                    clientLabyPlayer.sendTranslMessage("main.hasnot");
                }
            });

        } else {
            clientLabyPlayer.sendDefaultMessage("/lhlikes -" + transManager.getTranslation("player"));
        }
    }
}
