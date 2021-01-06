package de.labyhelp.addon.enums;

public enum Languages {

    DEUTSCH("DEUTSCH"),
    ENGLISH("ENGLISH"),
    RUSSIAN("RUSSIAN"),
    SPANISH("SPANISH"),
    POLNISH("POLNISH"),
    TURKEY("TURKEY"),
    SWITZERLAND("SWITZERLAND"),
    FRANCE("FRANCE");

    private final String name;

    Languages(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
