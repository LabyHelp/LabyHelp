package de.marvhuelsmann.labymodaddon.enums;

import de.marvhuelsmann.labymodaddon.LabyHelp;

import java.util.HashMap;

public enum Languages {

    DEUTSCH("DEUTSCH", LabyHelp.getInstace().getTranslationManager().germanTranslations),
    ENGLISH("ENGLISH", LabyHelp.getInstace().getTranslationManager().englishTranslation),
    RUSSISH("RUSKIY", LabyHelp.getInstace().getTranslationManager().russichTranslation),
    SPANISH("SPANISH", LabyHelp.getInstace().getTranslationManager().spanishTranslation),
    POLNISH("POLNISH", LabyHelp.getInstace().getTranslationManager().polnishTranslation),
    TURKEY("TURKEY", LabyHelp.getInstace().getTranslationManager().turkeyTranslation),
    SWITZERLAND("SWITZERLAND", LabyHelp.getInstace().getTranslationManager().switzerlandTranslation),
    FRANCE("FRANCE", LabyHelp.getInstace().getTranslationManager().franceTranslation);

    private final String name;
    private final HashMap<String, String> list;

    Languages(String name, HashMap<String, String> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getList() {
        return list;
    }
}
