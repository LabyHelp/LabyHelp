package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
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
                    clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN  + "You have successfully sent the comment!");
                } else {
                    clientLabyPlayer.sendMessage("An error has occurred!");
                }
                LabyHelp.getInstace().commentChat = false;

                return true;
            }

            if (
                    message.startsWith("/bandana") || message.startsWith("/cape") ||
                            message.startsWith("/skin") || message.startsWith("/cosmeticsC") ||
                            message.equalsIgnoreCase("/LhHelp") || message.equalsIgnoreCase("/lhrules") ||
                            message.equalsIgnoreCase("/labyhelp") || message.equalsIgnoreCase("/lhreload") ||
                            message.startsWith("/lhweb") ||
                            message.startsWith("/insta") || message.startsWith("/discord") ||
                            message.startsWith("/youtube") || message.startsWith("/twitch") ||
                            message.startsWith("/twitter") || message.startsWith("/tiktok") ||
                            message.startsWith("/social") || message.startsWith("/snapchat") ||
                            message.startsWith("/lhban") || message.startsWith("/lhmute") || message.startsWith("/lhteam") ||
                            message.startsWith("/lhlike") || message.startsWith("/likes") ||
                            message.startsWith("/likelist") || message.startsWith("/lhtarget") || message.startsWith("/lhcomment") || message.startsWith("/showcomments") || message.equalsIgnoreCase("/lhmodetarget") || message.startsWith("/invites") || message.startsWith("/lhcode") || message.equalsIgnoreCase("/invitelist")) {
                LabyHelp.getInstace().getCommands().commandMessage(message);
                return true;
            } else {
                return false;
            }
        } else {
            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
            labyPlayer.sendMessage("You have deactivated the Addon!");
            return false;
        }
    }
}
