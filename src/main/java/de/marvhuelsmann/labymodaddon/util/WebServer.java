package de.marvhuelsmann.labymodaddon.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
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
import java.util.Map;
import java.util.UUID;

public class WebServer {

    private static final String SERVER_ID = "4ed1f46bbe04bc756bcb17c0c7ce3e4632f06a48";

    public static String sendClient() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://sessionserver.mojang.com/session/minecraft/join");
            httpPost.setHeader("Content-Type", "application/json");

            JsonObject request = new JsonObject();
            request.addProperty("accessToken", Minecraft.getMinecraft().getSession().getToken());
            request.addProperty("selectedProfile", Minecraft.getMinecraft().getSession().getPlayerID());
            request.addProperty("serverId", SERVER_ID);
            httpPost.setEntity(new StringEntity(new Gson().toJson(request)));

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 204) {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/authenticate.php?username=" + URLEncoder.encode(Minecraft.getMinecraft().getSession().getUsername(), "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            } else {
                System.out.println(response);
                throw new IllegalStateException("Could not authenticate with mojang sessionserver!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch client!", e);
        }
    }

    public static String sendBanned(final UUID uuid, String reason) {
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

    public static String sendTwitch(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                name = name.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendTwitch.php?uuid=" + uuid.toString() + "&name=" +  URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch twitch!", e);
        }
    }

    public static String sendNameTag(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                name = name.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendNameTag.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch NameTag!", e);
        }
    }

    public static String sendTikTok(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                name = name.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendTikTok.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch TikTok!", e);
        }
    }

    public static String sendInstagram(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                name = name.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendInsta.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch instagram!", e);
        }
    }

    public static String sendYoutube(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                name = name.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendYoutube.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch youtube!", e);
        }
    }

    public static String sendStatus(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                if (name != null) {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendStatus.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
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
            throw new IllegalStateException("Could not fetch Status!", e);
        }
    }

    public static String sendTwitter(final UUID uuid, String name) {
        try {
            if (uuid != null) {

                name = name.replace(",", "").replace(":", "").replace("#", "@");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendTwitter.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch Twitter!", e);
        }
    }

    public static String sendSnapchat(final UUID uuid, String name) {
        try {
            if (uuid != null) {
                if (name != null) {
                    name = name.replace(",", "").replace(":", "").replace("#", "@");

                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendSnapchat.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
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
            throw new IllegalStateException("Could not fetch Snapchat!", e);
        }
    }

    public static String sendDiscord(final UUID uuid, String name) {
        try {
            if (uuid != null) {

                name = name.replace(",", "").replace(":", "").replace("#", "@");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendDiscord.php?uuid=" + uuid.toString() + "&name=" + URLEncoder.encode(name, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch Discord!", e);
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