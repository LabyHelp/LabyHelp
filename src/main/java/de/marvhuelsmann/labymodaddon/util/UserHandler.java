package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserHandler {


    public final Map<UUID, Boolean> voiceMuteExtendedList = new HashMap<>();
    public final Map<UUID, HelpGroups> userGroups = new HashMap<UUID, HelpGroups>();
    public final Map<UUID, String> userNameTags = new HashMap<UUID, String>();

    public final Map<UUID, String> instaName = new HashMap<UUID, String>();
    public final Map<UUID, String> discordName = new HashMap<UUID, String>();
    public final Map<UUID, String> youtubeName = new HashMap<UUID, String>();
    public final Map<UUID, String> twitchName = new HashMap<UUID, String>();
    public final Map<UUID, String> twitterName = new HashMap<UUID, String>();
    public final Map<UUID, String> tiktokName = new HashMap<UUID, String>();
    public final Map<UUID, String> snapchatName = new HashMap<UUID, String>();
    public final Map<UUID, String> isOnline = new HashMap<UUID, String>();

    //instagram 1 |
    //discord 2
    //youtube 3
    //twitch 4
    //twitter 5
    //tiktok 6
    //snapchat 7


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
                        instaName.put(UUID.fromString(data[0]), String.valueOf(data[1]));
                    }
                    if (!String.valueOf(data[2]).equals("")) {
                        String end = String.valueOf(data[2]).replace("@", "#");
                        discordName.put(UUID.fromString(data[0]), end);
                    }
                    if (!String.valueOf(data[3]).equals("")) {
                        youtubeName.put(UUID.fromString(data[0]), String.valueOf(data[3]));
                    }
                    if (!String.valueOf(data[4]).equals("")) {
                        twitchName.put(UUID.fromString(data[0]), String.valueOf(data[4]));
                    }
                    if (!String.valueOf(data[5]).equals("")) {
                        twitterName.put(UUID.fromString(data[0]), String.valueOf(data[5]));
                    }
                    if (!String.valueOf(data[6]).equals("")) {
                        tiktokName.put(UUID.fromString(data[0]), String.valueOf(data[6]));
                    }
                    if (!String.valueOf(data[7]).equals("")) {
                        snapchatName.put(UUID.fromString(data[0]), String.valueOf(data[7]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read socialmedia!", e);
        }
    }

    public void readMute() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/muteList.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            if (result != null) {
                final String[] split;
                final String[] entries = split = result.split(",");
                for (final String entry : split) {
                    final String[] data = entry.split(":");
                    if (data.length == 2) {
                        String uuid = data[0];
                        if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                            voiceMuteExtendedList.put(UUID.fromString(data[0]), data[1].equalsIgnoreCase("true"));

                            LabyMod.getInstance().displayMessageInChat("read mute update ArrayList");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch mutes!", e);
        }
    }

    public void readIsOnline() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/online.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            if (result != null) {
                final String[] split;
                final String[] entries = split = result.split(",");
                for (final String entry : split) {
                    final String[] data = entry.split(":");
                    if (data.length == 2) {
                        String uuid = data[0];
                        if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                            isOnline.put(UUID.fromString(data[0]), data[1]);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch online state!", e);
        }
    }

    public String sendOnline(final UUID uuid, boolean isOnline) {
        try {
            if (uuid != null) {
                if (isOnline) {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendOnline.php?uuid=" + uuid.toString() + "&isOnline=ONLINE").openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                } else {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendOnline.php?uuid=" + uuid.toString() + "&isOnline=OFFLINE").openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch onlinemode!", e);
        }
    }

    public void readGroups() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/database.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            if (result != null) {
                final String[] split;
                final String[] entries = split = result.split(",");
                for (final String entry : split) {
                    final String[] data = entry.split(":");
                    if (data.length == 2 && HelpGroups.isExist(data[1])) {
                        String uuid = data[0];
                        if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                            userGroups.put(UUID.fromString(data[0]), HelpGroups.valueOf(data[1]));
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch groups!", e);
        }
    }

    public void readNameTag() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/nametag.php").openConnection();
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
                if (data.length == 2) {
                    userNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read NameTag!", e);
        }
    }

    public void readUserInformations(boolean groups) {
        if (groups) {
            readGroups();
        } else {
            readNameTag();
        }
    }
}
