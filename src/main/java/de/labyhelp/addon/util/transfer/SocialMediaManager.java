package de.labyhelp.addon.util.transfer;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.SocialMediaType;
import net.labymod.main.LabyMod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sendSocial(type, message);
            }
        });
    }

    private String sendSocial(SocialMediaType type, String message) {
        try {

            if (!type.equals(SocialMediaType.NAMETAG) && !type.equals(SocialMediaType.SECOND_NAMETAG)) {
                String name = message.replace(",", "").replace(":", "").replace("#", "@");

                LabyHelp.getInstance().sendDeveloperMessage("send social: " + name);

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/send" + type.getUrlName() + ".php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            } else {
                String name = message.replace(",", "").replace(":", "");

                LabyHelp.getInstance().sendDeveloperMessage("send social: " + name);

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/send" + type.getUrlName() + ".php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LabyHelp.getInstance().sendDeveloperMessage("error handling: " + e);
            throw new IllegalStateException("Could not fetch SocialMedias!", e);
        }
    }



}
