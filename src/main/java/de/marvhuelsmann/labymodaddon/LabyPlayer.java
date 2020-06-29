package de.marvhuelsmann.labymodaddon;

import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import javax.swing.text.PlainDocument;
import java.util.UUID;

public class LabyPlayer {

    private final String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.YELLOW + "Helper" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.GRAY;


    public void openCapeUrl(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?cape&uuid=" + uuid, false);
        } else {
            sendMessage("Diesen Spieler gibt es nicht!");
        }
    }

    public void openBandanaUrl(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?bandana&uuid=" + uuid, false);
        } else {
            sendMessage("Diesen Spieler gibt es nicht!");
        }
    }

    public void openSkin(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
        } else {
            sendMessage("Diesen Spieler gibt es nicht!");
        }
    }

    public void sendMessage(String message) {
        LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
    }
}
