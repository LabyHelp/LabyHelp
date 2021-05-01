package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

import java.util.Map;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstance().getSettingsManager().onServer = true;
        LabyHelp.getInstance().getSettingsManager().currentServer = serverData.getIp();

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
        for (Map.Entry<String, String> partners : LabyHelp.getInstance().getPartnerHandler().getPartner().entrySet()) {
            if (serverIp.endsWith(partners.getValue())) {
                if (LabyHelp.getInstance().getSettingsManager().partnerNotify) {
                    LabyHelp.getInstance().sendTranslMessage("main.partnerserver");
                }
            }
        }
    }

    public void postJoin(ServerData serverData) {
        LabyHelp.getInstance().getExecutor().submit(() -> {
            try {
                LabyHelp.getInstance().getTranslationManager().initTranslation(LabyHelp.getInstance().getTranslationManager().getChooseTranslation(LabyHelp.getInstance().getTranslationManager().chooseLanguage));

                if (LabyHelp.getInstance().getSettingsManager().isAddonEnabled()) {
                LabyHelp.getInstance().getCommunicatorHandler().sendClient(serverData.getIp());

                sendBetaMessage();
                sendFirstJoinMessage();
                LabyHelp.getInstance().getVersionHandler().checkNewestLabyHelpVersion();

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
        });
    }
}
