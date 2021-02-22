package de.labyhelp.addon.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class TagManager {

    public HashMap<UUID, String> hasServer = new HashMap<>();
    public HashMap<UUID, String> tagList = new HashMap<>();

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

            hasServer.clear();
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    String uuid = data[0];
                    if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        hasServer.put(UUID.fromString(data[0]), data[1]);
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

            tagList.clear();
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    String uuid = data[0];
                    if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        tagList.put(UUID.fromString(data[0]), data[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read server Tag!", e);
        }
    }
}
