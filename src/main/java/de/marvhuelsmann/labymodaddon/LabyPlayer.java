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
            try {
                LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?cape&uuid=" + uuid, false);
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void openBandanaUrl(UUID uuid) {
        if (uuid != null) {
            try {
                LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?bandana&uuid=" + uuid, false);
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void openInsta(String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.instagram.com/" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openTikTok(String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.tiktok.com/@" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openTwitter(String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.twitter.com/" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openSocial(UUID uuid, String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.labyhelp.de/profile?uuid=" + uuid + "&name=" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openTwitch(String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.twitch.tv/" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openYoutube(String name) {
        try {
            LabyMod.getInstance().openWebpage("https://www.youtube.com/results?search_query=" + name, false);
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void sendDiscord(String name) {

        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        sendMessage("Der Discord Name lautet: " + EnumChatFormatting.RED + name + EnumChatFormatting.GRAY + " (Der Namen wurde in deiner Zwischenablage abgespeichert)");
    }

    public void sendSnapchat(String name) {

        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        sendMessage("Der SnapChat Name lautet: " + EnumChatFormatting.RED + name + EnumChatFormatting.GRAY + " (Der Namen wurde in deiner Zwischenablage abgespeichert)");
    }

    public void openSkin(UUID uuid) {
        if (uuid != null) {
            try {
                LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void sendNoPermsMessage() {
        LabyMod.getInstance().displayMessageInChat(prefix + " Diese Aktion ist bei diesem Spieler deaktiviert, weil er einen Premium LabyHelp Account besitzt!");
    }

    public void sendNoPerms() {
        LabyMod.getInstance().displayMessageInChat(prefix + " Du hast keine Berechtigung auf diesem Befehl!");
    }

    public void sendError() {
        LabyMod.getInstance().displayMessageInChat(prefix + " LabyHelp has an Error...");
    }

    public void sendMessage(String message) {
        LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
    }
}
