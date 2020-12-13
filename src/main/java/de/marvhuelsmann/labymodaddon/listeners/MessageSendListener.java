package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.CommandsList;
import de.marvhuelsmann.labymodaddon.util.Commands;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String message) {
        if (LabyHelp.getInstace().AddonSettingsEnable) {

            if (LabyHelp.getInstace().commentChat) {
                LabyPlayer clientLabyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                UUID commentUuid = LabyHelp.getInstace().commentMap.get(LabyMod.getInstance().getPlayerUUID());

                if (LabyHelp.getInstace().commentMap.containsValue(commentUuid)) {
                    LabyHelp.getInstace().getCommentManager().sendComment(commentUuid, message);
                    clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "You have successfully sent the comment!");
                } else {
                    clientLabyPlayer.sendMessage("An error has occurred!");
                }
                LabyHelp.getInstace().commentChat = false;

                return true;
            }


            for (CommandsList commands : CommandsList.values()) {
                if (commands.isMoreArgs()) {
                    if (message.startsWith("/" + commands.getName())) {
                        LabyHelp.getInstace().getCommands().commandMessage(message);
                    }
                } else {
                    if (message.equalsIgnoreCase("/" + commands.getName())) {
                        LabyHelp.getInstace().getCommands().commandMessage(message);
                    }
                }
            }

            for (CommandsList commandsList : CommandsList.values()) {
                if (message.startsWith("/" + commandsList.getName())) {
                    return true;
                }
            }

        } else {
            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
            labyPlayer.sendMessage("You have deactivated the Addon!");
            return false;
        }
        return false;
    }

}
