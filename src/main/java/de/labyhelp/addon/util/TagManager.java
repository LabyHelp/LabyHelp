package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.enums.Tags;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class TagManager {

    public HashMap<UUID, String> hasServer = new HashMap<>();
    public HashMap<UUID, Tags> serverTagList = new HashMap<>();

    public HashMap<UUID, Tags> discordTagList = new HashMap<>();
    public HashMap<UUID, String> tagList = new HashMap<>();

    private EnumChatFormatting colorCache;

    public void readServerPartner() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://labyhelp.de/server.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));

            LabyHelp.getInstance().sendDeveloperMessage("re-loading server tags");
            hasServer.clear();
            serverTagList.clear();
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    String uuid = data[0];
                    if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        hasServer.put(UUID.fromString(data[0]), data[1]);
                        serverTagList.put(UUID.fromString(data[0]), Tags.SERVER_TAG);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read server Partners!", e);
        }
    }

    public void readTagList() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://labyhelp.de/tag.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));


            LabyHelp.getInstance().sendDeveloperMessage("re-loading discord tag list");
            tagList.clear();
            discordTagList.clear();
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    String uuid = data[0];
                    if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        tagList.put(UUID.fromString(data[0]), data[1]);

                        if (data[1].equalsIgnoreCase(Tags.DISCORD_NORMAL_TAG.getRequestName())) {
                            discordTagList.put(UUID.fromString(data[0]), Tags.DISCORD_NORMAL_TAG);
                        } else if (data[1].equalsIgnoreCase(Tags.DISCORD_RAINBOW_TAG.getRequestName())) {
                            discordTagList.put(UUID.fromString(data[0]), Tags.DISCORD_RAINBOW_TAG);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read server Tag!", e);
        }
    }

    public void setNormalTag(UUID uuid, HelpGroups group) {
        LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(getDiscordTag(uuid) + group.getSubtitle() + getServerTag(uuid));
    }

    private String getDiscordTag(UUID uuid) {
        if (discordTagList.containsKey(uuid)) {
            for (Tags tags : Tags.values()) {
                if (tags.equals(Tags.DISCORD_NORMAL_TAG) || tags.equals(Tags.DISCORD_RAINBOW_TAG)) {
                    if (tags.getMapName().containsKey(uuid) && tags.equals(tags.getMapName().get(uuid))) {
                        if (colorCache == null || LabyHelp.getInstance().getGroupManager().rainbow) {
                            colorCache = LabyHelp.getInstance().getGroupManager().randomColor(false);
                            LabyHelp.getInstance().getGroupManager().rainbow = false;
                        }

                        return tags.getIsRainbow() ? colorCache + tags.getTagDisplayed() : tags.getTagDisplayed();
                    }
                }
            }
        }
        return "";
    }

    private String getServerTag(UUID uuid) {
        if (hasServer.containsKey(uuid)) {
            for (Tags tags : Tags.values()) {
                if (tags.equals(Tags.SERVER_TAG)) {
                    if (tags.getMapName().containsKey(uuid)) {
                        return tags.getTagDisplayed();
                    }
                }
            }
        }
        return "";
    }

}
