package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import de.labyhelp.addon.util.settings.SettingsManager;

public class LabyHelpCMD implements HelpCommand {

    @Override
    public String getName() {
        return "labyhelp";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        try {
            LabyHelp.getInstance().getExecutor().submit(() -> {
                LabyHelp.getInstance().getSettingsManager().addonEnabled = true;

                String newestVersion = LabyHelp.getInstance().getVersionHandler().checkNewestLabyHelpVersion();

                if (!LabyHelp.getInstance().getSettingsManager().isNewerVersion) {
                    labyPlayer.sendDefaultMessage("§f" + transManager.getTranslation("info.you") + " " + SettingsManager.currentVersion + transManager.getTranslation("new"));
                }
                if (LabyHelp.getInstance().getSettingsManager().isNewerVersion) {
                    labyPlayer.sendDefaultMessage("§f" + transManager.getTranslation("info.you") + " " + SettingsManager.currentVersion + transManager.getTranslation("old"));
                    labyPlayer.sendDefaultMessage("§c" + transManager.getTranslation("info.new") + " " + newestVersion);
                    labyPlayer.sendAlertTranslMessage("info.restart");
                }

                LabyHelp.getInstance().getVersionHandler().sendNewFeaturesMessage();
            });
        } catch (Exception ignored) {
            labyPlayer.sendDefaultMessage("§c" + transManager.getTranslation("info.responding") + "§l" + "909");
        }
        labyPlayer.sendDefaultMessage("§f" + "Teamspeak: https://labyhelp.de/teamspeak");
        labyPlayer.sendDefaultMessage("§f" + "Discord: https://labyhelp.de/discord");
    }
}
