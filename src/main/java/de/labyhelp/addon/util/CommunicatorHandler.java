package de.labyhelp.addon.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.enums.LabyVersion;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommunicatorHandler {

    public final String SERVER_ID = "4ed1f46bbe04bc756bcb17c0c7ce3e4632f06a48";

    public final Map<UUID, HelpGroups> userGroups = new HashMap<UUID, HelpGroups>();
    public final Map<UUID, HelpGroups> oldGroups = new HashMap<UUID, HelpGroups>();
    public final Map<UUID, String> userNameTags = new HashMap<UUID, String>();

    public final Map<UUID, String> isOnline = new HashMap<UUID, String>();

    public void targetMode(boolean activated) {
        LabyHelp.getInstance().getSettingsManager().targetMode = activated;
    }

    public String sendClient(String sip) {
        try {

            if (LabyHelp.getInstance().getVersionHandler().isGameVersion(LabyVersion.ONE_TWELVE)) {

                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost("https://sessionserver.mojang.com/session/minecraft/join");
                httpPost.setHeader("Content-Type", "application/json");

                JsonObject request = new JsonObject();
                request.addProperty("accessToken", LabyMod.getInstance().getAccountManager().getAccount(LabyMod.getInstance().getPlayerUUID()).getAccessToken());
                request.addProperty("selectedProfile", LabyMod.getInstance().getPlayerId());
                request.addProperty("serverId", SERVER_ID);
                httpPost.setEntity(new StringEntity(new Gson().toJson(request)));


                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 204) {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/auth.php?username=" + URLEncoder.encode(LabyMod.getInstance().getPlayerName(), "UTF-8") + "&sip=" + URLEncoder.encode(sip, "UTF-8")).openConnection(); con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                } else {
                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                    labyPlayer.sendTranslMessage("main.verify");
                    System.out.println(response);
                    throw new IllegalStateException("Could not authenticate with mojang sessionserver!");
                }

            } else {

                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost("https://sessionserver.mojang.com/session/minecraft/join");
                httpPost.setHeader("Content-Type", "application/json");

                JsonObject request = new JsonObject();
                request.addProperty("accessToken", Minecraft.getMinecraft().getSession().getToken());
                request.addProperty("selectedProfile", LabyMod.getInstance().getPlayerId());
                request.addProperty("serverId", SERVER_ID);
                httpPost.setEntity(new StringEntity(new Gson().toJson(request)));


                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 204) {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/auth.php?username=" + URLEncoder.encode(LabyMod.getInstance().getPlayerName(), "UTF-8") + "&sip=" + URLEncoder.encode(sip, "UTF-8")).openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                } else {
                    if (LabyHelp.getInstance().getSettingsManager().settingsAdversting) {
                        LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                        labyPlayer.sendTranslMessage("main.verify");
                    }
                    System.out.println(response);
                    throw new IllegalStateException("Could not authenticate with mojang sessionserver!");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch client!", e);
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


                            if (!LabyHelp.getInstance().getGroupManager().isTeam(UUID.fromString(uuid)) || !LabyHelp.getInstance().getGroupManager().isPremiumExtra(UUID.fromString(uuid))) {
                                if (Integer.parseInt(LabyHelp.getInstance().getInviteManager().getInvites(UUID.fromString(uuid))) >= 25) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.PREMIUM_);
                                } else if (Integer.parseInt(LabyHelp.getInstance().getInviteManager().getInvites(UUID.fromString(uuid))) >= 10) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.INVITER);
                                }

                                if (LabyHelp.getInstance().getLikeManager().getFamousLikePlayer().toString().equals(uuid)) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.FAMOUS);
                                } else {
                                    List<Map.Entry<String, Integer>> list = LabyHelp.getInstance().getLikeManager().getTops5();
                                    for (Map.Entry<String, Integer> uuidStringEntry : list) {
                                        if (uuidStringEntry.getKey().equalsIgnoreCase(uuid)) {
                                            userGroups.put(UUID.fromString(data[0]), HelpGroups.FAME);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch groups!", e);
        }
    }

    public void readUserInformations(boolean groups) {
        if (groups) {

            for (Map.Entry<UUID, HelpGroups> group : userGroups.entrySet()) {
                oldGroups.put(group.getKey(), group.getValue());
            }

            for (Map.Entry<UUID, String> likes : LabyHelp.getInstance().getLikeManager().userLikes.entrySet()) {
                LabyHelp.getInstance().getLikeManager().oldLikes.put(likes.getKey(), likes.getValue());
            }


            for (Map.Entry<UUID, String> likes : LabyHelp.getInstance().getInviteManager().userInvites.entrySet()) {
                LabyHelp.getInstance().getInviteManager().oldInvites.put(likes.getKey(), likes.getValue());
            }

            LabyHelp.getInstance().getLikeManager().readUserLikes();
            LabyHelp.getInstance().getLikeManager().readLikes();
            LabyHelp.getInstance().getInviteManager().readUserInvites();
            LabyHelp.getInstance().getInviteManager().readOldPlayer();

            LabyHelp.getInstance().getServerManager().readServerPartner();
            LabyHelp.getInstance().getServerManager().readTagList();
            readGroups();

            TranslationManager translationManager = LabyHelp.getInstance().getTranslationManager();

            if (!getNowRanked().getName().equalsIgnoreCase(getBeforeRanked().getName())) {
                if (getNowRanked().equals(HelpGroups.BANNED)) {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.WHITE + " ---------LabyHelp----------");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Your NameTag has been banned for one day");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.YELLOW + translationManager.getTranslation("main.rules") + ": https://labyhelp.de/tag-rules");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.WHITE + " ---------LabyHelp----------");

                } else {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN + translationManager.getTranslation("main.rankchange") + " (" + EnumChatFormatting.WHITE + getNowRanked().getName() + EnumChatFormatting.GREEN + ")");
                }
            }

            if (!    LabyHelp.getInstance().getLikeManager().getBeforeLikes().equalsIgnoreCase(    LabyHelp.getInstance().getLikeManager().getNowLikes())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN + translationManager.getTranslation("main.profilelike") + " (" + EnumChatFormatting.WHITE + LabyHelp.getInstance().getLikeManager().getNowLikes() + EnumChatFormatting.GREEN + ")");
            }

            if (!LabyHelp.getInstance().getInviteManager().getBeforeInvites().equalsIgnoreCase(LabyHelp.getInstance().getInviteManager().getNowInvites())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN +  translationManager.getTranslation("main.predeemedcode") + " (" + EnumChatFormatting.WHITE + LabyHelp.getInstance().getInviteManager().getNowInvites() + EnumChatFormatting.GREEN + ")");
            }

        } else {
            readNameTag();
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

    public String getIp() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/ip.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read ip!", e);
        }
    }

    public String sendBanned(final UUID uuid, String reason) {
        try {
            if (uuid != null) {
                reason = reason.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendBan.php?uuid=" + uuid.toString() + "&fromUuid=" + LabyMod.getInstance().getPlayerUUID() + "&reason=" + URLEncoder.encode(reason, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch ban!", e);
        }
    }

    public HelpGroups getBeforeRanked() {
        if (!oldGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : oldGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return oldGroups.get(groups.getKey());
                }
            }
        }
        return null;
    }

    public HelpGroups getNowRanked() {
        if (!userGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : userGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return userGroups.get(groups.getKey());
                }
            }
        }
        return null;
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

    public static String readVersion() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/version.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read version!", e);
        }
    }
}