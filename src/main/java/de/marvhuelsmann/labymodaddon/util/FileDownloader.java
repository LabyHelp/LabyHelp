// 
// Decompiled by Procyon v0.5.36
// 

package de.marvhuelsmann.labymodaddon.util;

import net.labymod.addon.AddonLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {

    public static final URL DOWNLOAD_URL;

    static {
        URL url = null;
        try {
            url = new URL("https://drive.google.com/u/0/uc?id=1_04KoR3sb9CEPQoxffezVsM_8V2G7q1s&export=download");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DOWNLOAD_URL = url;
    }

    public static void update() {
        File addonDir = AddonLoader.getAddonsDirectory();
        File addon = new File(addonDir, "LabyHelp.jar");

        try {
            FileUtils.copyURLToFile(DOWNLOAD_URL, addon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
