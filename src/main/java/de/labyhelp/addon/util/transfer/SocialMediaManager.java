package de.labyhelp.addon.util.transfer;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.SocialMediaType;
import net.labymod.main.LabyMod;

import java.io.IOException;
import java.net.URLEncoder;

public class SocialMediaManager {

    public String instaName;
    public String discordName;
    public String youtubeName;
    public String twitchName;
    public String twitterName;
    public String tiktokName;
    public String snapchatName;
    public String githubName;
    public String statusName;
    public String nameTagName;
    public String secondNameTagName;

    public void sendSocialMedia(SocialMediaType type, String message) {
        LabyHelp.getInstance().getExecutor().submit(() -> sendSocial(type, message));
    }

    private void sendSocial(SocialMediaType type, String message) {
        try {
            String name;
            if (!type.equals(SocialMediaType.NAMETAG) && !type.equals(SocialMediaType.SECOND_NAMETAG)) {
                name = message.replace(",", "").replace(":", "").replace("#", "@");
            } else {
                name = message.replace(",", "").replace(":", "");
            }
            LabyHelp.getInstance().sendDeveloperMessage("send social: " + name);
            LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/send" + type.getUrlName() + ".php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + URLEncoder.encode(name, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            LabyHelp.getInstance().sendDeveloperMessage("error handling: " + e);
            throw new IllegalStateException("Could not fetch SocialMedias!", e);
        }
    }

}
