package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.Languages;

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
        String language = lang == null ? Languages.DEUTSCH.getName() : lang.getName();
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://marvhuelsmann.de/translations.php?name=" + language)) {
            final String[] data = entry.split(":");
            if (data.length == 3) {
                if (data[0].equalsIgnoreCase(lang == null ? Languages.DEUTSCH.getName() : lang.getName())) {
                    currentLanguagePack.put(data[1], data[2]);
                }
            }
        }
        LabyHelp.getInstance().sendDeveloperMessage("Translation refresh");
        LabyHelp.getInstance().getSettingsManager().translationLoaded = true;
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
        return LabyHelp.getInstance().getSettingsManager().isInitLoading ? "..." : key;
    }
}
