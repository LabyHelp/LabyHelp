package de.marvhuelsmann.labymodaddon.commands.comment;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
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
                LabyHelp.getInstace().getCommentManager().refreshComments();

                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                            if (!LabyHelp.getInstace().getCommentManager().banned.contains(LabyMod.getInstance().getPlayerUUID())) {
                                if (!LabyHelp.getInstace().getCommentManager().cooldown.contains(LabyMod.getInstance().getPlayerUUID())) {
                                    if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {

                                        LabyHelp.getInstace().getSettingsManger().commentChat = true;
                                        LabyHelp.getInstace().getSettingsManger().commentMap.put(LabyMod.getInstance().getPlayerUUID(), UUIDFetcher.getUUID(args[1]));

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
            clientLabyPlayer.sendDefaultMessage("- /lhcomment -" + LabyHelp.getInstace().getTranslationManager().getTranslation("player"));
        }
    }
}
