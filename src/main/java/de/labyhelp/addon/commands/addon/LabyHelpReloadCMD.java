package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.CommunicatorHandler;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import de.labyhelp.addon.util.settings.SettingsManager;

public class LabyHelpReloadCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhreload";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();

        labyPlayer.sendTranslMessage("info.reload");
        try {
            LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                @Override
                public void run() {

                    LabyHelp.getInstance().getStoreHandler().readHelpAddons();
                    LabyHelp.getInstance().sendDeveloperMessage("Help Addons refresh");
                    LabyHelp.getInstance().getServerManager().readServerPartner();
                    LabyHelp.getInstance().getServerManager().readTagList();

                    LabyHelp.getInstance().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstance().getNameTagManager().updateNameTag(true, true);
                    LabyHelp.getInstance().sendDeveloperMessage("NameTags/Subtitles refresh");

                    LabyHelp.getInstance().getLikeManager().isLiked.clear();
                    LabyHelp.getInstance().getLikeManager().readUserLikes();
                    LabyHelp.getInstance().getLikeManager().readLikes();
                    LabyHelp.getInstance().sendDeveloperMessage("Like refresh");

                    LabyHelp.getInstance().getTranslationManager().currentLanguagePack.clear();
                    LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(transManager.chooseLanguage));

                    LabyHelp.getInstance().getInviteManager().readUserInvites();
                    LabyHelp.getInstance().getInviteManager().readOldPlayer();
                    LabyHelp.getInstance().sendDeveloperMessage("old Player refresh");

                    LabyHelp.getInstance().getCommunicatorHandler().isOnline.clear();
                    LabyHelp.getInstance().getSettingsManager().addonEnabled = true;

                    System.out.println("subtitles updating..");
                    final String webVersion = CommunicatorHandler.readVersion();
                    LabyHelp.getInstance().getSettingsManager().isNewerVersion = !webVersion.equalsIgnoreCase(SettingsManager.currentVersion);

                    LabyHelp.getInstance().sendDeveloperMessage("updating Version");

                    labyPlayer.sendTranslMessage("info.reload.finish");
                }
            });
        } catch (Exception ignored) {
            LabyHelp.getInstance().getSettingsManager().addonEnabled = false;
        }
    }
}
