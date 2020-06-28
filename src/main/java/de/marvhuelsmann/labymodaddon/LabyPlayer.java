package de.marvhuelsmann.labymodaddon;

import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class LabyPlayer {

    private final String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.YELLOW + "Searcher" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.GRAY;


    public void openCapeUrl(UUID uuid) {
        LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?cape&uuid=" + uuid, false);
    }

    public void openBandanaUrl(UUID uuid) {
        LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?bandana&uuid=" + uuid, false);
    }

    public void openSkin(UUID uuid) {
        LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
    }

    public void sendMessage(String message) {

        LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
    }
}
