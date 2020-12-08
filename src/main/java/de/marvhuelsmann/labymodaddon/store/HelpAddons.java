package de.marvhuelsmann.labymodaddon.store;

public enum HelpAddons {

    LABYHELP("LabyHelp", "https://drive.google.com/u/0/uc?id=1_04KoR3sb9CEPQoxffezVsM_8V2G7q1s&export=download", true),
    STAYSAFE("StaySafe", "https://drive.google.com/u/0/uc?id=1ZWWFfu2zTqy555yV6Alt2t5-Zls85eh8&export=download", false);

    private final String name;
    private final String downloadURL;
    private final boolean mainAddon;

    HelpAddons(String fileName, String downloadURL, boolean mainAddon) {
        this.name = fileName;
        this.downloadURL = downloadURL;
        this.mainAddon = mainAddon;
    }

    public String getFileName() {
        return name;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public boolean isMainAddon() {
        return mainAddon;
    }


}
