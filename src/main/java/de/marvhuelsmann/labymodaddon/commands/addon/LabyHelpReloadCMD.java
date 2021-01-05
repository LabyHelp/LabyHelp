package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.Languages;
import de.marvhuelsmann.labymodaddon.util.CommunicatorHandler;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;

public class LabyHelpReloadCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhreload";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();

        labyPlayer.sendTranslMessage("info.reload");
        try {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {

                    LabyHelp.getInstace().getStoreHandler().readHelpAddons();

                    LabyHelp.getInstace().getCommentManager().banned.clear();
                    LabyHelp.getInstace().getCommentManager().readBanned();

                    LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstace().getGroupManager().updateNameTag(true);
                    LabyHelp.getInstace().getGroupManager().updateNameTag(false);

                    LabyHelp.getInstace().getLikeManager().isLiked.clear();
                    LabyHelp.getInstace().getLikeManager().readUserLikes();
                    LabyHelp.getInstace().getLikeManager().readLikes();

                    LabyHelp.getInstace().getTranslationManager().currentLanguagePack.clear();
                    LabyHelp.getInstace().getTranslationManager().initTranslation(Languages.valueOf(transManager.chooseLanguage));

                    LabyHelp.getInstace().getInviteManager().readUserInvites();
                    LabyHelp.getInstace().getInviteManager().readOldPlayer();

                    LabyHelp.getInstace().getCommunicationManager().isOnline.clear();
                    LabyHelp.getInstace().getSettingsManger().addonEnabled = true;

                    System.out.println("subtitles updating..");
                    final String webVersion = CommunicatorHandler.readVersion();
                    LabyHelp.getInstace().getSettingsManger().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.getInstace().getSettingsManger().currentVersion);
                }
            });
        } catch (Exception ignored) {
            LabyHelp.getInstace().getSettingsManger().addonEnabled = false;
        }
    }
}
