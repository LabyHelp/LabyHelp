// 
// Decompiled by Procyon v0.5.36
// 

package de.marvhuelsmann.labymodaddon.util;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileDownloader implements Runnable {
    private final String url;
    private final File file;

    public static File Addon;

    public FileDownloader(final String url, final File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        if (this.file != null && this.url != null && this.url.startsWith("http")) {
            try {
                FileUtils.copyURLToFile(new URL(this.url), this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void update() {
     new FileDownloader("https://drive.google.com/u/0/uc?id=1_04KoR3sb9CEPQoxffezVsM_8V2G7q1s&export=download", Addon);

        System.out.println("DONWLOAD COMPLETE" + Addon.getName());

        try {
            final String dir = initDirectory();
            if (!new File(dir).exists()) {
            }

            final File run = Addon;
            final String[] versions = {"1.8", "1.12"};
            String[] array;
            for (int length = (array = versions).length, i = 0; i < length; ++i) {
                final String version = array[i];
                final File addonsDir = new File(dir + "addons-" + version);
                addonsDir.mkdirs();
                final File mod = new File(addonsDir, "LabyHelp.jar");
                if (!mod.exists()) {
                    File[] listFiles;
                    for (int length2 = (Objects.requireNonNull(listFiles = addonsDir.listFiles())).length, j = 0; j < length2; ++j) {
                        final File addons = listFiles[j];
                        if (addons.getName().toLowerCase().contains("labyhelp")) {
                            addons.delete();
                        }
                    }
                }
                Files.copy(run.toPath(), mod.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("finish download" + run.getName() + Addon.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static String initDirectory() {
        String dir = System.getenv("APPDATA") + "/.minecraft/LabyMod/";
        if (!new File(dir).exists()) {
            dir = System.getProperty("user.home") + "/Library/Application Support/minecraft/LabyMod/";
        }
        if (!new File(dir).exists()) {
            dir = System.getProperty("user.home") + "/.minecraft/LabyMod/";
        }
        return dir;
    }


}
