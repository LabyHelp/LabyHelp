package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RequestManager {

    public String sendRequest(String urlRequest) {
        return request(urlRequest);
    }

    public String sendRequest(String urlRequest, final UUID uuid) {
        if (uuid != null) {
            return request(urlRequest);
        }
        return null;
    }

    public String[] getRequest(String getRequest) {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL(getRequest).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8).split(",");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read get Request!" + getRequest, e);
        }
    }

    public void getStandardHashMap(String getRequest, HashMap<UUID, String> insertList) {
        for (final String entry :  getRequest(getRequest)) {
            final String[] args = entry.split(":");
            if (args.length == 2) {
                String uuid = args[0];
                if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    insertList.put(UUID.fromString(args[0]), args[1]);
                }
            }
        }
    }

    public void getStandardArrayList(String getRequest, ArrayList<UUID> insertList) {
        for (final String entry : getRequest(getRequest)) {
            final String[] args = entry.split(":");
            if (args.length == 2) {
                String uuid = args[0];
                if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    insertList.add(UUID.fromString(args[0]));
                }
            }
        }

    }

    private String request(String urlRequest) {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL(urlRequest).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();

            LabyHelp.getInstance().sendDeveloperMessage("requesting url: " + urlRequest);

            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            LabyHelp.getInstance().sendDeveloperMessage("error handling: " + e);
            throw new IllegalStateException("Could not fetch request with url:" + urlRequest, e);
        }
    }
}
