package de.marvhuelsmann.labymodaddon.util.transfer;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
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

    public final Map<UUID, String> instaNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> discordNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> youtubeNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> twitchNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> twitterNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> tiktokNameMap = new HashMap<UUID, String>();
    public final Map<UUID, String> snapchatNameMap = new HashMap<UUID, String>();

    public String instaName;
    public String discordName;
    public String youtubeName;
    public String twitchName;
    public String twitterName;
    public String tiktokName;
    public String snapchatName;
    public String statusName;
    public String nameTagName;

    public void sendSocialMedia(SocialMediaType type, String message) {
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                sendSocial(type, message);
            }
        });
    }

    private String sendSocial(SocialMediaType type, String message) {
        try {

            if (!type.equals(SocialMediaType.NAMETAG)) {
                String name = message.replace(",", "").replace(":", "").replace("#", "@");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/send" + type.getUrlName() + ".php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            } else {
                String name = message.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/send" + type.getUrlName() + ".php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch SocialMedias!", e);
        }
    }


    public void readSocialMedia() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/socialmedia.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 8) {
                    if (!String.valueOf(data[1]).equals("")) {
                        instaNameMap.put(UUID.fromString(data[0]), String.valueOf(data[1]));
                    }
                    if (!String.valueOf(data[2]).equals("")) {
                        String end = String.valueOf(data[2]).replace("@", "#");
                        discordNameMap.put(UUID.fromString(data[0]), end);
                    }
                    if (!String.valueOf(data[3]).equals("")) {
                        youtubeNameMap.put(UUID.fromString(data[0]), String.valueOf(data[3]));
                    }
                    if (!String.valueOf(data[4]).equals("")) {
                        twitchNameMap.put(UUID.fromString(data[0]), String.valueOf(data[4]));
                    }
                    if (!String.valueOf(data[5]).equals("")) {
                        twitterNameMap.put(UUID.fromString(data[0]), String.valueOf(data[5]));
                    }
                    if (!String.valueOf(data[6]).equals("")) {
                        tiktokNameMap.put(UUID.fromString(data[0]), String.valueOf(data[6]));
                    }
                    if (!String.valueOf(data[7]).equals("")) {
                        snapchatNameMap.put(UUID.fromString(data[0]), String.valueOf(data[7]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read socialmedia!", e);
        }
    }

}
