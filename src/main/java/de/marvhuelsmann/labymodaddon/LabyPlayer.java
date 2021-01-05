package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map;
import java.util.UUID;

public class LabyPlayer {

    private final UUID uuid;

    public LabyPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static final String prefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.YELLOW + "Helper" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.WHITE;


    public String getSocialMedia(SocialMediaType socialMedia) {
        LabyHelp.getInstace().getSocialMediaManager().readSocialMedia();

        if (socialMedia.getMap().containsKey(uuid)) {
            for (final Map.Entry<UUID, String> entry : socialMedia.getMap().entrySet()) {
                if (entry.getKey() != null) {
                    if (entry.getKey().equals(uuid)) {
                        return entry.getValue();
                    }
                }
            }
        } else {
            sendTranslMessage("social.null");
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

        sendDefaultMessage(LabyHelp.getInstace().getTranslationManager().getTranslation("social.discord")  + " " + EnumChatFormatting.RED + name + EnumChatFormatting.GRAY + LabyHelp.getInstace().getTranslationManager().getTranslation("main.clipboard"));
    }

    public void sendSnapchat(String name) {

        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        sendDefaultMessage(LabyHelp.getInstace().getTranslationManager().getTranslation("social.snap") + " "  + EnumChatFormatting.RED + name + EnumChatFormatting.GRAY + LabyHelp.getInstace().getTranslationManager().getTranslation("main.clipboard"));
    }

    public void sendAdversting(boolean tip) {
        sendTranslMessage("main.adversting");
        sendDefaultMessage(EnumChatFormatting.YELLOW + "LabyHelp Teamspeak:" + EnumChatFormatting.BOLD + " https://labyhelp.de/teamspeak");
        sendDefaultMessage(EnumChatFormatting.YELLOW + "LabyHelp Discord:" + EnumChatFormatting.BOLD + " https://labyhelp.de/discord");

        if (tip) {
            sendTranslMessage("adversting.turnoff");
        }
    }

    public void openCapeUrl(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://labyhelp.de/cape.php?uuid=" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
        }
    }

    public void openBandanaUrl(UUID uuid) {
        if (uuid != null) {
            try {
                if (getPermissions(uuid)) {
                    LabyMod.getInstance().openWebpage("https://labyhelp.de/bandana.php?uuid=" + uuid, false);
                }
            } catch (Exception ignored) {
                sendError();
            }
        } else {
            sendTranslMessage("main.not.exist");
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
            sendTranslMessage("main.not.exist");
        }
    }

    public boolean getPermissions(UUID uuid) {
        if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
            if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                return true;
            } else {
                sendTranslMessage("main.team");
            }
        } else {
            sendNoPermsMessage();
        }
        return false;
    }

    public void sendNoPermsMessage() {
        sendTranslMessage("main.premium");
    }

    public void sendNoPerms() {
        sendTranslMessage("main.noperms");
    }

    public void sendError() {
        sendTranslMessage("main.error");
    }

    public void sendTranslMessage(String key) {
        if (LabyHelp.getInstace().getSettingsManger().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + " " + LabyHelp.getInstace().getTranslationManager().getTranslation(key));
        }
    }

    public void sendAlertTranslMessage(String key) {
        if (LabyHelp.getInstace().getSettingsManger().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + EnumChatFormatting.RED + " " + LabyHelp.getInstace().getTranslationManager().getTranslation(key));
        }
    }

    public void sendDefaultMessage(String message) {
        if (LabyHelp.getInstace().getSettingsManger().onServer) {
            LabyMod.getInstance().displayMessageInChat(prefix + " " + message);
        }
    }


    public UUID getUuid() {
        return this.uuid;
    }
}
