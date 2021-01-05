package de.labyhelp.addon.store;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class StoreHandler {

    private final FileDownloader fileDownloader = new FileDownloader();
    private final StoreSettings storeSettings = new StoreSettings();

    private final HashMap<String, String> helpAddons = new HashMap<>();
    private final HashMap<String, String> helpAddonInfo = new HashMap<>();

    public void readHelpAddons() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/addonslist.php").openConnection();
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
                if (data.length == 3) {
                    helpAddonInfo.put(data[0], data[1]);
                    helpAddons.put(data[0], data[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read HelpAddons!", e);
        }
    }

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }

    public HashMap<String, String> getAddonsList() {
        return helpAddons;
    }

    public String getAddonAuthor(String name) {
        return helpAddonInfo.get(name);
    }
}
