package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class LabyHelpLikeCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhlike";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        if (args.length == 2) {
            final UUID uuid = UUIDFetcher.getUUID(args[1]);
            if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {
                if (!LabyHelp.getInstance().getLikeManager().isLiked.contains(uuid)) {
                    if (uuid != null) {
                        if (uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                            if (LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid)) {
                                LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                                    @Override
                                    public void run() {

                                        LabyHelp.getInstance().getLikeManager().sendLike(LabyMod.getInstance().getPlayerUUID(), uuid);

                                        LabyHelp.getInstance().getLikeManager().readUserLikes();
                                        LabyHelp.getInstance().getLikeManager().readLikes();

                                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("likes.like") + EnumChatFormatting.DARK_RED + args[1].toUpperCase() + EnumChatFormatting.RED + "!");
                                        if (LabyHelp.getInstance().getLikeManager().getLikes(uuid).equalsIgnoreCase("1")) {
                                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() + transManager.getTranslation("likes.has.only") + LabyHelp.getInstance().getLikeManager().getLikes(uuid) + " Like!");
                                        } else {
                                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + args[1].toUpperCase() + transManager.getTranslation("likes.has") + LabyHelp.getInstance().getLikeManager().getLikes(uuid) + " Likes!");
                                        }
                                    }
                                });
                            } else {
                                clientLabyPlayer.sendTranslMessage("main.hasnot");
                            }
                        } else {
                            clientLabyPlayer.sendTranslMessage("main.not.exist");
                        }
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("likes.already") + EnumChatFormatting.WHITE + args[1]);
                }
            } else {
                clientLabyPlayer.sendTranslMessage("likes.self");
            }
        } else {
            clientLabyPlayer.sendDefaultMessage("/lhlike -" + transManager.getTranslation("player"));
        }
    }
}
