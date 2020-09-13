package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.labymod.main.LabyMod;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstace().onServer = true;

        if (LabyHelp.getInstace().addonEnabled) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    WebServer.sendClient();
                    LabyHelp.getInstace().getSocialHandler().readSocialMedia();
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(false);
                }
            });
        } else {
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + " LabyHelp doenst load correctly...");
        }
    }
}
