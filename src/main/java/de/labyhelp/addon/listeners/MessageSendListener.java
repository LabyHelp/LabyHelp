package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.api.event.Subscribe;

public class MessageSendListener {

    @Subscribe
    public boolean onSend(String message) {
        if (LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable) {
            return LabyHelp.getInstance().getCommandHandler().executeCommand(message);
        } else {
            return false;
        }
    }

}
