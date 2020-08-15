package de.marvhuelsmann.labymodaddon.util;

import net.labymod.addon.AddonLoader;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URLConnection;
import java.util.Objects;

public class Util {

    public static File initFile() {
        File dir = null;
        File file = null;
        try {
            dir = AddonLoader.getAddonsDirectory();
        }
        catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        if (dir != null) {
            file = new File(dir, "LabyHelp.jar");
            if (!file.exists()) {
                File[] listFiles;
                for (int length = (Objects.requireNonNull(listFiles = dir.listFiles())).length, i = 0; i < length; ++i) {
                    final File f = listFiles[i];
                    if (f.getName().toLowerCase().contains("labyhelp")) {
                        file = f;
                        break;
                    }
                }
            }
        }
        if (dir != null) {
            if (file.exists()) {
                return file;
            }
        }
        try {
            final URLConnection con = Utils.class.getProtectionDomain().getCodeSource().getLocation().openConnection();
            file = new File(((JarURLConnection)con).getJarFileURL().getPath());
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return file;
    }

}
