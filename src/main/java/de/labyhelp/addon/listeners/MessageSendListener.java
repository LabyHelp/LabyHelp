package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;

import java.util.UUID;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String message) {
        if (LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable) {

            if (LabyHelp.getInstance().getSettingsManager().commentChat) {
                LabyPlayer clientLabyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                UUID commentUuid = LabyHelp.getInstance().getSettingsManager().commentMap.get(LabyMod.getInstance().getPlayerUUID());

                if (LabyHelp.getInstance().getSettingsManager().commentMap.containsValue(commentUuid)) {
                    LabyHelp.getInstance().getCommentManager().sendComment(commentUuid, message);
                    clientLabyPlayer.sendTranslMessage("comments.send");
                } else {
                    clientLabyPlayer.sendTranslMessage("main.error");
                }
                LabyHelp.getInstance().getSettingsManager().commentChat = false;

                return true;
            }


            return LabyHelp.getInstance().getCommandHandler().executeCommand(message);

        } else {
            return false;
        }
    }

}
