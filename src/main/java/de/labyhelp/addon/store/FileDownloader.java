package de.labyhelp.addon.store;

import de.labyhelp.addon.LabyHelp;
import net.labymod.addon.AddonLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileDownloader {

    public static final URL LABYHELP_URL;

    static {
        URL url = null;
        try {
            url = new URL("https://drive.google.com/u/0/uc?id=1_04KoR3sb9CEPQoxffezVsM_8V2G7q1s&export=download");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        LABYHELP_URL = url;
    }

    public void updateLabyHelp() {
        File addonDir = AddonLoader.getAddonsDirectory();
        File addon = new File(addonDir, "LabyHelp.jar");

        try {
            FileUtils.copyURLToFile(LABYHELP_URL, addon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installSpecificStoreAddon(String storeAddonName) {
        try {
            if (LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons) {
                for (Map.Entry<String, String> addons : LabyHelp.getInstance().getStoreHandler().getAddonsList().entrySet()) {
                    if (addons.getKey().equals(storeAddonName)) {
                        File addonDir = AddonLoader.getAddonsDirectory();
                        File addon = new File(addonDir, addons.getKey() + ".jar");

                        FileUtils.copyURLToFile(new URL("https://" + addons.getValue()), addon);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void installStoreAddons() {
        try {
          if (LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons) {
              for (Map.Entry<String, String> addons : LabyHelp.getInstance().getStoreHandler().getAddonsList().entrySet()) {
                  File addonDir = AddonLoader.getAddonsDirectory();
                  File addon = new File(addonDir, addons.getKey() + ".jar");

                  FileUtils.copyURLToFile(new URL("https://" + addons.getValue()), addon);
              }
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readAddonVersion(String url) {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read addon version!", e);
        }
    }


}
