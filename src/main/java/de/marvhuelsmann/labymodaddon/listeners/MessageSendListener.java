package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;

import java.util.UUID;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String message) {
        if (LabyHelp.getInstace().getSettingsManger().AddonSettingsEnable) {

            if (LabyHelp.getInstace().getSettingsManger().commentChat) {
                LabyPlayer clientLabyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                UUID commentUuid = LabyHelp.getInstace().getSettingsManger().commentMap.get(LabyMod.getInstance().getPlayerUUID());

                if (LabyHelp.getInstace().getSettingsManger().commentMap.containsValue(commentUuid)) {
                    LabyHelp.getInstace().getCommentManager().sendComment(commentUuid, message);
                    clientLabyPlayer.sendTranslMessage("comments.send");
                } else {
                    clientLabyPlayer.sendTranslMessage("main.error");
                }
                LabyHelp.getInstace().getSettingsManger().commentChat = false;

                return true;
            }


            return LabyHelp.getInstace().getCommandHandler().executeCommand(message);

            /*        for (CommandsList commands : CommandsList.values()) {
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
            }*/

        } else {
            return false;
        }
    }

}
