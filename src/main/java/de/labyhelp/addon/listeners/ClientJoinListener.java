package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstance().getSettingsManager().onServer = true;

        postJoin(serverData);
    }

    public void postJoin(ServerData serverData) {
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LabyHelp.getInstance().getCommunicatorHandler().sendClient();

                    LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(LabyHelp.getInstance().getTranslationManager().chooseLanguage));


                    LabyHelp.getInstance().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstance().getGroupManager().updateSubTitles(false);

                    LabyHelp.getInstance().getStoreHandler().readHelpAddons();
                    LabyHelp.getInstance().getCommentManager().refreshComments();


                    LabyHelp.getInstance().getSettingsManager().addonEnabled = true;
                    LabyHelp.getInstance().getSettingsManager().isInitLoading = false;
                } catch (Exception ignored) {
                    LabyHelp.getInstance().getSettingsManager().addonEnabled = false;
                }
            }
        });
    }
}
