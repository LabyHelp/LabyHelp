package de.labyhelp.addon.util.settings;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class SettingsManager {

    public String currentVersion = "2.5.61";
    public Boolean isNewerVersion = false;
    public String newestVersion;

    public boolean AddonSettingsEnable = true;
    public boolean settingsAdversting = true;
    public boolean settinngsComments = true;
    public boolean onServer = false;

    public boolean isInitLoading = true;


    public ArrayList<UUID> targetList = new ArrayList<>();
    public boolean targetMode = false;

    public String nameTagSettings;
    public int nameTagSwitchingSetting;
    public int nameTagRainbwSwitching;
    public int nameTagSize;

    public boolean oldVersion = false;
    public boolean addonEnabled = false;

    public boolean commentChat = false;
    public HashMap<UUID, UUID> commentMap = new HashMap<>();

}
