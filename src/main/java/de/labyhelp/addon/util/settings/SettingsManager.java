package de.labyhelp.addon.util.settings;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class SettingsManager {

    public static String currentVersion = "2.6.32";
    public String versionTag = null;

    public boolean firstPlay = true;
    public boolean newVersionMessage = false;

    public boolean translationLoaded = false;

    public Boolean isNewerVersion = false;
    public String newestVersion;

    public boolean AddonSettingsEnable = true;
    public boolean settingsAdversting = true;

    public boolean developerMode = false;

    public boolean seeNameTags = true;

    public boolean onServer = false;

    public boolean isInitLoading = true;

    public boolean seePlayerMenu = true;

    public String nameTagSettings;
    public int nameTagSwitchingSetting;
    public int nameTagRainbwSwitching;
    public int nameTagSize;

    public boolean oldVersion = false;
    public boolean addonEnabled = false;

    public boolean commentChat = false;
    public HashMap<UUID, UUID> commentMap = new HashMap<>();

}
