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

    private void sendBetaMessage() {
        if (LabyHelp.getInstance().getSettingsManager().versionTag != null) {
            LabyHelp.getInstance().sendTranslMessage("main.beta.notification");
            LabyHelp.getInstance().sendDefaultMessage(LabyHelp.getInstance().getSettingsManager().versionTag);
        }
    }

    private void sendFirstJoinMessage() {
        if (LabyHelp.getInstance().getSettingsManager().firstPlay) {
            LabyHelp.getInstance().changeFirstJoin(false);
            LabyHelp.getInstance().sendTranslMessage("main.firstJoin");
        }
    }

    private void checkIfPartnerServer(String serverIp) {
        if (serverIp.equalsIgnoreCase("mcone.eu")) {
            LabyHelp.getInstance().sendDeveloperMessage("try to connect to the partner server: " + serverIp);
            //TODO: add Partner Server code
        }
    }

    public void postJoin(ServerData serverData) {
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(LabyHelp.getInstance().getTranslationManager().chooseLanguage));

                    if (LabyHelp.getInstance().getSettingsManager().isAddonEnabled()) {
                    LabyHelp.getInstance().getCommunicatorHandler().sendClient(serverData.getIp());

                    sendBetaMessage();
                    sendFirstJoinMessage();
                    LabyHelp.getInstance().getVersionHandler().checkNewestLabyHelpVersion();
                    LabyHelp.getInstance().getVersionHandler().sendNewFeaturesMessage();

                    checkIfPartnerServer(serverData.getIp());

                    if (LabyHelp.getInstance().getSettingsManager().translationLoaded) {
                        LabyHelp.getInstance().getCommunicatorHandler().readFastStart();
                    }

                    LabyHelp.getInstance().sendDeveloperMessage("updating join user list");

                    LabyHelp.getInstance().getStoreHandler().readHelpAddons();

                    } else {
                        LabyHelp.getInstance().sendDefaultMessage("The LabyHelp Server are currently in maintenance mode.");
                        LabyHelp.getInstance().sendDefaultMessage("You can see here more: https://stats.uptimerobot.com/ZrV89sM1jA");
                    }
                    LabyHelp.getInstance().getSettingsManager().isInitLoading = false;
                } catch (Exception ignored) {
                    LabyHelp.getInstance().getSettingsManager().addonEnabled = false;
                }
            }
        });
    }
}
