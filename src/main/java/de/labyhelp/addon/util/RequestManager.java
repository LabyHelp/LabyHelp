package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
            throw new IllegalStateException("Could not fetch request with url:" + urlRequest, e);
        }
    }
}
