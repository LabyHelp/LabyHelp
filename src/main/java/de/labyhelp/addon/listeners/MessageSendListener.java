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
            return LabyHelp.getInstance().getCommandHandler().executeCommand(message);
        } else {
            return false;
        }
    }

}
