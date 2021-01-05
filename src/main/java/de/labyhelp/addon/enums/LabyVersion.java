package de.labyhelp.addon.enums;

public enum LabyVersion {

    ONE_EIGHTEEN("1.8"),
    ONE_TWELVE("1.12");

    private final String versionName;

    LabyVersion(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }
}
