package de.marvhuelsmann.labymodaddon.enums;

public enum NameTagSettings {

    SWITCHING("SWITCHING"),
    RANK("RANK"),
    NAMETAG("NAMETAG");

    private final String name;


    NameTagSettings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
