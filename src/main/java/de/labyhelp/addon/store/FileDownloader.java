package de.labyhelp.addon.store;

import de.labyhelp.addon.LabyHelp;
import net.labymod.addon.AddonLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    public void update() {
        File addonDir = AddonLoader.getAddonsDirectory();
        File addon = new File(addonDir, "LabyHelp.jar");

        try {
            FileUtils.copyURLToFile(LABYHELP_URL, addon);
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


}
