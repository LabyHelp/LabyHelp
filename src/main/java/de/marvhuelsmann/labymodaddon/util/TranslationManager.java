package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.Languages;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TranslationManager {

    public Languages translation;
    public String chooseLanguage;

    public HashMap<String, String> germanTranslations = new HashMap<>();
    public HashMap<String, String> englishTranslation = new HashMap<>();
    public HashMap<String, String> russichTranslation = new HashMap<>();
    public HashMap<String, String> franceTranslation = new HashMap<>();
    public HashMap<String, String> polnishTranslation = new HashMap<>();
    public HashMap<String, String> spanishTranslation = new HashMap<>();
    public HashMap<String, String> turkeyTranslation = new HashMap<>();
    public HashMap<String, String> switzerlandTranslation = new HashMap<>();

    public void initTranslations() {
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
                    for (Languages lang : Languages.values()) {
                        if (lang.getName().equalsIgnoreCase(data[0])) {
                            lang.getList().put(data[1], data[2]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read translations!", e);
        }
    }

    public String getTranslation(String key) {

        if (translation == null) {
                translation = Languages.valueOf(chooseLanguage);
        }

        if (Languages.ENGLISH.equals(translation)) {
            return getMessage(Languages.ENGLISH, key);
        } else if (Languages.DEUTSCH.equals(translation)) {
            return getMessage(Languages.DEUTSCH, key);
        } else if (Languages.RUSSISH.equals(translation)) {
            return getMessage(Languages.RUSSISH, key);
        } else if (Languages.FRANCE.equals(translation)) {
            return getMessage(Languages.FRANCE, key);
        } else if (Languages.SPANISH.equals(translation)) {
            return getMessage(Languages.SPANISH, key);
        } else if (Languages.POLNISH.equals(translation)) {
            return getMessage(Languages.POLNISH, key);
        } else if (Languages.SWITZERLAND.equals(translation)) {
            return getMessage(Languages.SWITZERLAND, key);
        } else if (Languages.TURKEY.equals(translation)) {
            return getMessage(Languages.TURKEY, key);
        }  else {
            return "Invalid Language // 404" + translation.getName();
        }
    }

    private String getMessage(Languages language, String key) {
        for (Map.Entry<String, String> langList : language.getList().entrySet()) {
            if (langList.getKey().equalsIgnoreCase(key)) {
                return langList.getValue();
            }
        }
        return key;
    }
}
