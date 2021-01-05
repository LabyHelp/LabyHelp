package de.labyhelp.addon.util;

import de.labyhelp.addon.enums.LabyVersion;

public class VersionHandler {

    public LabyVersion gameVersion;

    public void initGameVersion(String version) {
        if (version.startsWith("1.8")) {
            gameVersion = LabyVersion.ONE_EIGHTEEN;
        } else {
            gameVersion = LabyVersion.ONE_TWELVE;
        }
    }

    public boolean isGameVersion(LabyVersion version) {
        return version.equals(gameVersion);
    }

    public LabyVersion getGameVersion() {
        return gameVersion;
    }

}
