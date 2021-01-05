package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.CommunicatorHandler;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;

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

                    LabyHelp.getInstance().getCommentManager().banned.clear();
                    LabyHelp.getInstance().getCommentManager().readBanned();

                    LabyHelp.getInstance().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstance().getGroupManager().updateNameTag(true);
                    LabyHelp.getInstance().getGroupManager().updateNameTag(false);

                    LabyHelp.getInstance().getLikeManager().isLiked.clear();
                    LabyHelp.getInstance().getLikeManager().readUserLikes();
                    LabyHelp.getInstance().getLikeManager().readLikes();

                    LabyHelp.getInstance().getTranslationManager().currentLanguagePack.clear();
                    LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(transManager.chooseLanguage));

                    LabyHelp.getInstance().getInviteManager().readUserInvites();
                    LabyHelp.getInstance().getInviteManager().readOldPlayer();

                    LabyHelp.getInstance().getCommunicatorHandler().isOnline.clear();
                    LabyHelp.getInstance().getSettingsManager().addonEnabled = true;

                    System.out.println("subtitles updating..");
                    final String webVersion = CommunicatorHandler.readVersion();
                    LabyHelp.getInstance().getSettingsManager().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.getInstance().getSettingsManager().currentVersion);
                }
            });
        } catch (Exception ignored) {
            LabyHelp.getInstance().getSettingsManager().addonEnabled = false;
        }
    }
}
