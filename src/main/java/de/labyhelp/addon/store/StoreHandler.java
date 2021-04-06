package de.labyhelp.addon.store;

import de.labyhelp.addon.LabyHelp;
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
        helpAddonInfo.clear();
        helpAddons.clear();
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://marvhuelsmann.de/addonslist.php")) {
            final String[] data = entry.split(":");
            if (data.length == 3) {
                helpAddonInfo.put(data[0], data[1]);
                helpAddons.put(data[0], data[2]);
            }
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
