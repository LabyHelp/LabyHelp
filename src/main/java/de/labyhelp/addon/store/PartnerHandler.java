package de.labyhelp.addon.store;

import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PartnerHandler {

    @Getter
    private final HashMap<String, String> partner = new HashMap<>();

    public boolean isPartnerIp(String ip) {
        for (Map.Entry<String, String> partner : partner.entrySet()) {
            if (ip.equalsIgnoreCase(partner.getKey()) || ip.equalsIgnoreCase(partner.getValue())) {
                return true;
            }
        }
        return false;
    }

    public String getPartnerIp(String ip) {
        for (Map.Entry<String, String> partner : partner.entrySet()) {
            if (ip.equalsIgnoreCase(partner.getKey())) {
                return partner.getValue();
            } else if (ip.equalsIgnoreCase(partner.getValue())) {
                return partner.getValue();
            }
        }
        return null;
    }

    public void readPartner() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://labyhelp.de/partner.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            partner.clear();
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    partner.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read Partner!", e);
        }
    }
}
