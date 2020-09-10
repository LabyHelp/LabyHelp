package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.api.events.MessageSendEvent;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String message) {
        //TODO ADD /LHIGNORE
        if (
                message.startsWith("/bandana") || message.startsWith("/cape") ||
                message.startsWith("/skin") || message.startsWith("/cosmeticsC") ||
                message.equalsIgnoreCase("/LhHelp") || message.equalsIgnoreCase("/nametag") ||
                message.equalsIgnoreCase("/labyhelp") || message.equalsIgnoreCase("/lhreload") ||
                message.startsWith("/insta") || message.startsWith("/discord") ||
                message.startsWith("/youtube") || message.startsWith("/twitch") ||
                message.startsWith("/twitter") || message.startsWith("/tiktok") ||
                message.startsWith("/social") || message.startsWith("/snapchat") ||
                message.startsWith("/lhban"))  {
            LabyHelp.getInstace().getCommands().commandMessage(message);
            return true;
        } else {
            return false;
        }
    }
}
