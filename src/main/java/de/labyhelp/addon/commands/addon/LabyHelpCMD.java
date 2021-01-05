package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.CommunicatorHandler;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

public class LabyHelpCMD implements HelpCommand {

    @Override
    public String getName() {
        return "labyhelp";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        try {
            LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    LabyHelp.getInstance().getSettingsManager().addonEnabled = true;
                    final String webVersion = CommunicatorHandler.readVersion();
                    LabyHelp.getInstance().getSettingsManager().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.getInstance().getSettingsManager().currentVersion);
                    LabyHelp.getInstance().getSettingsManager().newestVersion = webVersion;
                    if (!LabyHelp.getInstance().getSettingsManager().isNewerVersion) {
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.getInstance().getSettingsManager().currentVersion + transManager.getTranslation("new"));
                    }
                    if (LabyHelp.getInstance().getSettingsManager().isNewerVersion) {
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.getInstance().getSettingsManager().currentVersion + transManager.getTranslation("old"));
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + transManager.getTranslation("info.new") + " " + webVersion);
                        labyPlayer.sendAlertTranslMessage("info.restart");
                    }
                }
            });
        } catch (Exception ignored) {
            labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + transManager.getTranslation("info.responding") + EnumChatFormatting.BOLD + "909");
        }
        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Teamspeak: https://labyhelp.de/teamspeak");
        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Discord: https://labyhelp.de/discord");
    }
}
