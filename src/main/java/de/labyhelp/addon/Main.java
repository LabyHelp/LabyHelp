package de.labyhelp.addon;

import de.labyhelp.addon.util.settings.SettingsManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class Main {

    private static final HashMap<String, String> lang = new HashMap<>();

    public static void main(final String[] args) {
        initLookAndFeel();
        initLanguage();

        try {
            final String dir = initDirectory();
            if (!new File(dir).exists()) {
                throw new IOException("No .minecraft/LabyMod directory found!");
            }
            if (showConfirmDialog(String.format(Main.lang.get("installation"), SettingsManager.currentVersion))) {
                final File run = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if (!run.exists() || !run.isFile()) {
                    throw new IOException("Invalid path: " + run.getAbsolutePath());
                }
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
                }
                showMessageDialog(Main.lang.get("success"), 1);
            }
        } catch (FileSystemException e) {
            e.printStackTrace();
            if (e.getReason() != null && !e.getReason().isEmpty()) {
                showMessageDialog(Main.lang.get("closed") + "\n" + e.getReason(), 0);
            } else {
                showMessageDialog(Main.lang.get("error"), 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            showMessageDialog(Main.lang.get("error"), 0);
        }

    }


    private static void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
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

    private static void initLanguage() {
        if (Locale.getDefault().toString().toLowerCase().contains("de")) {
            Main.lang.put("installation", "LabyHelp v%s kann nun installiert werden!\nMinecraft muss bei der Installation geschlossen sein!");
            Main.lang.put("success", "LabyHelp Installation abgeschlossen!");
            Main.lang.put("closed", "Ist Minecraft geschlossen?");
            Main.lang.put("error", "Installation fehlgeschlagen!\nKopiere die Mod in das Verzeichnis .minecraft/LabyMod/addons und starte Minecraft!");
        } else if (Locale.getDefault().toString().toLowerCase().contains("es")) {
            Main.lang.put("installation", "¡Se puede instalar el LabyHelp v%s ahora!\n¡Minecraft tiene que estar cerrador para instalarlo!");
            Main.lang.put("success", "La instalaci\u00f3n del LabyHelp a terminado");
            Main.lang.put("closed", "¿Minecraft est\u00e1 cerrado?");
            Main.lang.put("error", "¡La instalaci\u00f3n fall\u00f3!\n¡Copi\u00e1 la Mod en la carpeta .minecraft/LabyMod/addons y empez\u00e1 a jugar!");
        } else {
            Main.lang.put("installation", "LabyHelp v%s is now ready for installation!\nClose Minecraft before continuing!");
            Main.lang.put("success", "LabyHelp installation finished!");
            Main.lang.put("closed", "Minecraft closed?");
            Main.lang.put("error", "Installation failed!\nCopy the file into .minecraft/LabyMod/addons and start Minecraft!");
        }
    }

    private static boolean showConfirmDialog(final String msg) {
        return JOptionPane.showConfirmDialog(null, msg, "LabyHelp from Marvio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0;
    }

    private static void showMessageDialog(final String msg, final int mode) {
        JOptionPane.showMessageDialog(null, msg, "LabyHelp from Marvio", mode);
    }


}
