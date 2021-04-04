package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.LabyVersion;
import de.labyhelp.addon.util.settings.SettingsManager;
import net.minecraft.util.EnumChatFormatting;

public class VersionHandler {

    public LabyVersion gameVersion;

    public void initGameVersion(String version) {
        if (version.startsWith("1.8")) {
            gameVersion = LabyVersion.ONE_EIGHTEEN;
        } else if (version.startsWith("1.16")) {
            gameVersion = LabyVersion.ONE_SIXTEEN;
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

    public String checkNewestLabyHelpVersion() {
        LabyHelp.getInstance().sendDeveloperMessage("called: checkNewestLabyHelpVersion");

        String webVersion = LabyHelp.getInstance().getCommunicatorHandler().readVersion();
        LabyHelp.getInstance().getSettingsManager().newestVersion = webVersion;
        if (!webVersion.equalsIgnoreCase(SettingsManager.currentVersion)) {
            LabyHelp.getInstance().getSettingsManager().isNewerVersion = true;

            LabyHelp.getInstance().getSettingsManager().newVersionMessage = true;
            LabyHelp.getInstance().getConfig().addProperty("newVersionMessage", true);

            LabyHelp.getInstance().saveConfig();
        } else {
            LabyHelp.getInstance().getSettingsManager().isNewerVersion = false;
        }


        return webVersion;
    }

    public void sendNewFeaturesMessage() {
        if (LabyHelp.getInstance().getSettingsManager().newVersionMessage && !LabyHelp.getInstance().getSettingsManager().isInitLoading) {
            if (!LabyHelp.getInstance().getTranslationManager().getTranslation("main.newFeatures").equals("")
                    && !LabyHelp.getInstance().getTranslationManager().getTranslation("main.newFeatures").equals("main.newFeatures")) {
                if (!LabyHelp.getInstance().getSettingsManager().isNewerVersion) {
                    LabyHelp.getInstance().sendSpecficTranslMessage(EnumChatFormatting.RED + " " + LabyHelp.getInstance().getTranslationManager().getTranslation("info.new") + ":" + EnumChatFormatting.WHITE, "main.newFeatures");

                    LabyHelp.getInstance().getConfig().addProperty("newVersionMessage", false);
                    LabyHelp.getInstance().getSettingsManager().newVersionMessage = false;
                    LabyHelp.getInstance().saveConfig();

                    LabyHelp.getInstance().sendDeveloperMessage("set isNewerVersionMessage: false");
                }
            }
        }

    }

}

