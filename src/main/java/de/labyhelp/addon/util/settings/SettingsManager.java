package de.labyhelp.addon.util.settings;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class SettingsManager {

    public static String currentVersion = "2.6.592";
    public String versionTag = null;

    public boolean firstPlay = true;
    public boolean newVersionMessage = false;

    public boolean translationLoaded = false;
    public boolean partnerNotify = true;

    public Boolean isNewerVersion = false;
    public String newestVersion;

    public boolean AddonSettingsEnable = true;
    public boolean settingsAdversting = true;

    public boolean developerMode = false;

    public boolean seeNameTags = true;

    public boolean onServer = false;

    public boolean isInitLoading = true;

    public boolean seePlayerMenu = true;

    public String rightTag;
    public String leftTag;

    public String nameTagSettings;
    public int nameTagSwitchingSetting;
    public int nameTagRainbwSwitching;
    public int nameTagSize;
    public boolean addonEnabled = false;

    public boolean commentChat = false;
    public HashMap<UUID, UUID> commentMap = new HashMap<>();

}
