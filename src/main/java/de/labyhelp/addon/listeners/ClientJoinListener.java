package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.main.LabyMod;
import net.labymod.user.cosmetic.cosmetics.partner.CosmeticStegi;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.util.EnumChatFormatting;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstance().getSettingsManager().onServer = true;

        postJoin(serverData);
    }

    int i = 1;

    public void postJoin(ServerData serverData) {
        LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(LabyHelp.getInstance().getTranslationManager().chooseLanguage));

        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LabyHelp.getInstance().getCommunicatorHandler().sendClient();

                    i++;

                    if (i == 2) {
                        LabyHelp.getInstance().sendDefaultMessage("LabyHelp Supporter Bewerbungsphase geöffnet. Weitere Informationen findest du auf unserem Discord.");
                    }

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
