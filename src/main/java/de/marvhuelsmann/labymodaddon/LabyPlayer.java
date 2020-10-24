package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LabyPlayer {

    private final UUID uuid;

    public LabyPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static final String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.YELLOW + "Helper" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.GRAY;


    public String getSocialMedia(SocialMediaType socialMedia) {
        LabyHelp.getInstace().getUserHandler().readSocialMedia();

        if (socialMedia.getMap().containsKey(uuid)) {
            for (final Map.Entry<UUID, String> entry : socialMedia.getMap().entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getKey().equals(uuid)) {
                        return entry.getValue();
                    }
                }
            }
        } else {
            sendMessage("Der Spieler hat nicht dieses SocialMedia hinterlegt!");
        }
        return null;
    }

    public void openInsta(String name) {
        try {
            if (name != null) {
                LabyMod.getInstance().openWebpage("https://www.instagram.com/" + name, false);
            }
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openTikTok(String name) {
        try {
            if (name != null) {
                LabyMod.getInstance().openWebpage("https://www.tiktok.com/@" + name, false);
            }
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openTwitter(String name) {
        try {
            if (name != null) {
                LabyMod.getInstance().openWebpage("https://www.twitter.com/" + name, false);
            }
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
            if (name != null) {
                LabyMod.getInstance().openWebpage("https://www.twitch.tv/" + name, false);
            }
        } catch (Exception ignored) {
            sendError();
        }
    }

    public void openYoutube(String name) {
        try {
            if (name != null) {
                LabyMod.getInstance().openWebpage("https://www.youtube.com/results?search_query=" + name, false);
            }
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

    public void sendAdversting(boolean tip) {
        sendMessage(EnumChatFormatting.RED + "LabyHelp Adversting:");
        sendMessage(EnumChatFormatting.YELLOW + "LabyHelp Teamspeak:" + EnumChatFormatting.BOLD + " https://labyhelp.de/teamspeak");
        sendMessage(EnumChatFormatting.YELLOW + "LabyHelp Discord:" + EnumChatFormatting.BOLD + " https://labyhelp.de/discord");

        if (tip) {
            sendMessage(EnumChatFormatting.RED + "You can deactivate the advertising messages in the LabyHelp Addon Settings");
        }
    }

    public void openCapeUrl(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?cape&uuid=" + uuid, false);
                }
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
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://www.labymod.net/page/php/getCapeTexture.php?bandana&uuid=" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public void openSkin(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://de.namemc.com/profile/" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendMessage("This Player does not exit!");
        }
    }

    public boolean getPermissions(UUID uuid) {
        if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
            if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                return true;
            } else {
                sendMessage("Diese Aktion ist bei diesem Spieler deaktiviert, weil er ein Team Mitglied ist!");
            }
        } else {
            sendNoPermsMessage();
        }
        return false;
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
        if (LabyHelp.getInstace().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
        }
    }


    public UUID getUuid() {
        return this.uuid;
    }
}
