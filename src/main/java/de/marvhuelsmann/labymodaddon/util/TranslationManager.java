package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.Languages;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TranslationManager {

    public String chooseLanguage;

    public HashMap<String, String> currentLanguagePack = new HashMap<>();


    public Languages getChooseTranslation(String lang) {
        for (Languages langu : Languages.values()) {
            if (langu.getName().equalsIgnoreCase(lang)) {
                return langu;
            }
        }
        return null;
    }

    public void initTranslation(Languages lang) {
        if (lang == null) {
            try {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/translations.php").openConnection();
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
                        if (data[0].equalsIgnoreCase(Languages.DEUTSCH.getName())) {
                            currentLanguagePack.put(data[1], data[2]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Could not read translations!", e);
            }
        } else {
            try {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/translations.php").openConnection();
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
                        if (data[0].equalsIgnoreCase(lang.getName())) {
                            currentLanguagePack.put(data[1], data[2]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Could not read translations!", e);
            }
        }
    }

    public String getTranslation(String key) {
        return getMessage(key);
    }

    private String getMessage(String key) {
        for (Map.Entry<String, String> langList : currentLanguagePack.entrySet()) {
            if (langList.getKey().equalsIgnoreCase(key)) {
                return langList.getValue();
            }
        }
        return key;
    }
}
