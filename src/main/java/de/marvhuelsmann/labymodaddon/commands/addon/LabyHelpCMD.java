package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.CommunicatorHandler;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

public class LabyHelpCMD implements HelpCommand {

    @Override
    public String getName() {
        return "labyhelp";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();
        try {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    LabyHelp.getInstace().getSettingsManger().addonEnabled = true;
                    final String webVersion = CommunicatorHandler.readVersion();
                    LabyHelp.getInstace().getSettingsManger().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.getInstace().getSettingsManger().currentVersion);
                    LabyHelp.getInstace().getSettingsManger().newestVersion = webVersion;
                    if (!LabyHelp.getInstace().getSettingsManger().isNewerVersion) {
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.getInstace().getSettingsManger().currentVersion + transManager.getTranslation("new"));
                    }
                    if (LabyHelp.getInstace().getSettingsManger().isNewerVersion) {
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.getInstace().getSettingsManger().currentVersion + transManager.getTranslation("old"));
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
