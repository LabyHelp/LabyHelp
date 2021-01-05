package de.labyhelp.addon.commands.comment;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class LabyHelpCommentCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhcomment";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        if (args.length == 2) {
            UUID uuid = UUIDFetcher.getUUID(args[1]);
            if (uuid != null) {
                LabyHelp.getInstance().getCommentManager().refreshComments();

                LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        if (LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid)) {
                            if (!LabyHelp.getInstance().getCommentManager().banned.contains(LabyMod.getInstance().getPlayerUUID())) {
                                if (!LabyHelp.getInstance().getCommentManager().cooldown.contains(LabyMod.getInstance().getPlayerUUID())) {
                                    if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {

                                        LabyHelp.getInstance().getSettingsManager().commentChat = true;
                                        LabyHelp.getInstance().getSettingsManager().commentMap.put(LabyMod.getInstance().getPlayerUUID(), UUIDFetcher.getUUID(args[1]));

                                        clientLabyPlayer.sendTranslMessage("comment.info");
                                        clientLabyPlayer.sendTranslMessage("comment.info2");
                                    } else {
                                        clientLabyPlayer.sendTranslMessage("comments.self");
                                    }
                                } else {
                                    clientLabyPlayer.sendTranslMessage("comments.wait");
                                }
                            } else {
                                clientLabyPlayer.sendTranslMessage("comments.banned");
                            }
                        } else {
                            clientLabyPlayer.sendTranslMessage("main.hasnot");
                        }
                    }
                });
            } else {
                clientLabyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            clientLabyPlayer.sendDefaultMessage("- /lhcomment -" + LabyHelp.getInstance().getTranslationManager().getTranslation("player"));
        }
    }
}
