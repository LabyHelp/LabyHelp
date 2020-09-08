package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.labymod.main.LabyMod;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.util.EnumChatFormatting;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstace().onServer = true;

        if (LabyHelp.getInstace().addonEnabled) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    WebServer.sendClient();
                    GroupManager.updateSubTitles(true);
                    GroupManager.updateSubTitles(false);
                }
            });
        } else {
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + " LabyHelp doenst load correctly...");
        }
    }
}
