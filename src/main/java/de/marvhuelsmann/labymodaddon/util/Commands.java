package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelpAddon;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import net.labymod.main.LabyMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class Commands {

    private static UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    public static void CommandMessage(String message) {
        final LabyPlayer labyPlayer = new LabyPlayer();
        if (LabyHelpAddon.AddonEnable) {
            if (message.startsWith("/bandana")) {
                //SPACE MUST BE THERE
                UUID uuid = getUUID(message.replaceAll("/bandana ", ""));
                labyPlayer.openBandanaUrl(uuid);
            } else if (message.startsWith("/cape")) {
                //SPACE MUST BE THERE
                UUID uuid = getUUID(message.replaceAll("/cape ", ""));
                labyPlayer.openCapeUrl(uuid);
            } else if (message.startsWith("/skin")) {
                //SPACE MUST BE THERE
                UUID uuid = getUUID(message.replaceAll("/skin ", ""));
                labyPlayer.openSkin(uuid);
            }  else if (message.startsWith("/cosmeticsCC")) {
                UUID uuid = getUUID(message.replaceAll("/cosmeticsCC ", ""));
                LabyMod.getInstance().getUserManager().getUser(uuid).getCosmetics().clear();
            }
            if (message.equalsIgnoreCase("/LhHelp")) {
                labyPlayer.sendMessage("- /bandana <player>");
                labyPlayer.sendMessage("- /cape <player>");
                labyPlayer.sendMessage("- /skin <player>");
                labyPlayer.sendMessage("- /cosmeticsCC <player>");
            }
        } else {
            labyPlayer.sendMessage(EnumChatFormatting.RED + "You have deactivated the Addon!");
        }
    }
}
