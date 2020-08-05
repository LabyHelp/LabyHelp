package de.marvhuelsmann.labymodaddon;

import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.UUID;

public class LabyPlayer {

    public static final String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.YELLOW + "Helper" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.GRAY;

    public void openCapeUrl(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?cape&uuid=" + uuid, false);
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void openBandanaUrl(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?bandana&uuid=" + uuid, false);
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void openInsta(String name) {
            LabyMod.getInstance().openWebpage("https://www.instagram.com/" + name, false);
    }

    public void openTikTok(String name) {
        LabyMod.getInstance().openWebpage("https://www.tiktok.com/@" + name, false);
    }

    public void openTwitter(String name) {
        LabyMod.getInstance().openWebpage("https://www.twitter.com/" + name, false);
    }

    public void openSocial(UUID uuid, String name) {
        LabyMod.getInstance().openWebpage("https://www.labyhelp.de/profile?uuid=" + uuid + "&name=" + name, false);
    }

    public void openTwitch(String name) {
        LabyMod.getInstance().openWebpage("https://www.twitch.tv/" + name, false);
    }

    public void openYoutube(String name) {
        LabyMod.getInstance().openWebpage("https://www.youtube.com/results?search_query=" + name, false);
    }

    public void sendDiscord(String name) {

        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        sendMessage("Der Discord Name lautet: " + EnumChatFormatting.RED + name + EnumChatFormatting.GRAY + " (Der Namen wurde in deiner Zwischenablage abgespeichert)");
    }

    public void openSkin(UUID uuid) {
        if (uuid != null) {
            LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void sendNoPermsMessage() {
        LabyMod.getInstance().displayMessageInChat(prefix + " Diese Aktion ist bei diesem Spieler deaktiviert, weil er einen Premium LabyHelp Account besitzt!");
    }

    public void sendMessage(String message) {
        LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
    }
}
