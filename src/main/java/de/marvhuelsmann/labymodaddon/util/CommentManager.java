package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CommentManager {

    public ArrayList<UUID> cooldown = new ArrayList<>();
    public ArrayList<UUID> banned = new ArrayList<>();
    public ArrayList<UUID> isAllowed = new ArrayList<>();
    public HashMap<UUID, String> comments = new HashMap<>();


    public void refreshComments() {
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {

                cooldown.clear();

                readCooldown();
                readBanned();
            }
        });
    }

    public void readCooldown() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/cooldown.php?uuid=" + LabyMod.getInstance().getPlayerUUID()).openConnection();
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
                    if (data[1].equalsIgnoreCase("true")) {
                        cooldown.add(UUID.fromString(data[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read cooldown!", e);
        }
    }

    public void readAllowed(UUID uuid) {

        isAllowed.clear();

        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/commentActive.php?uuid=" + uuid).openConnection();
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
                    if (UUID.fromString(data[0]).equals(uuid)) {
                        isAllowed.add(UUID.fromString(data[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read readAllowed!", e);
        }
    }

    public void readBanned() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/banned.php").openConnection();
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
                    if (data[1].equalsIgnoreCase("true")) {
                        banned.add(UUID.fromString(data[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read banned state!", e);
        }
    }

    public void readAllComments(UUID uuid) {

        comments.clear();

        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/allcomments.php?uuid=" + uuid).openConnection();
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
                    String uuid1 = data[0];
                    if (uuid1.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        comments.put(UUID.fromString(uuid1), data[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read banned state!", e);
        }
    }

    public String sendComment(final UUID uuid, String reason) {
        try {
            if (uuid != null) {
                reason = reason.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendMessage.php?name=" + LabyMod.getInstance().getPlayerName() + "&uuid=" + uuid.toString() + "&fromUuid=" + LabyMod.getInstance().getPlayerUUID() + "&reason=" + URLEncoder.encode(reason, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch comment", e);
        }
    }

    public String sendToggle(final UUID uuid, String reason) {
        try {
            if (uuid != null) {
                reason = reason.replace(",", "").replace(":", "");

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/comment.php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&toggle=" + URLEncoder.encode(reason, "UTF-8")).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch comment", e);
        }
    }

}
