package de.marvhuelsmann.labymodaddon.commands.comment;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public class ShowCommentsCMD implements HelpCommand {
    @Override
    public String getName() {
        return "showcomments";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();
        if (args.length == 2) {
            UUID uuid = UUIDFetcher.getUUID(args[1]);
            if (uuid != null) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        LabyHelp.getInstace().getCommentManager().readAllComments(uuid);
                        LabyHelp.getInstace().getCommentManager().readAllowed(uuid);
                        if (!LabyHelp.getInstace().getCommentManager().comments.isEmpty()) {
                            if (LabyHelp.getInstace().getCommentManager().isAllowed.contains(uuid)) {

                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + args[1].toUpperCase() + transManager.getTranslation("comments.receive"));

                                for (Map.Entry<UUID, String> entry : LabyHelp.getInstace().getCommentManager().comments.entrySet()) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + transManager.getTranslation("main.from")+ EnumChatFormatting.GRAY + UUIDFetcher.getName(entry.getKey()).toUpperCase());
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + transManager.getTranslation("main.message") + EnumChatFormatting.GRAY + entry.getValue());
                                    LabyMod.getInstance().displayMessageInChat("");
                                }
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GRAY + "ttps://labyhelp.de/comments.php?uuid=" + uuid + "&name=" + args[1]);
                            } else {
                                clientLabyPlayer.sendTranslMessage( "comments.dis");
                            }
                        } else {
                            clientLabyPlayer.sendTranslMessage("comments.null");
                        }
                    }
                });
            } else {
                clientLabyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            clientLabyPlayer.sendDefaultMessage("- /showcomments -" + transManager.getTranslation("player"));
        }
    }
}
