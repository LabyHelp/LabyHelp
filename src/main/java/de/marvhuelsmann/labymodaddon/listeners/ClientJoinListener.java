package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
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
                    // LabyHelp.getInstace().getUserHandler().readIsOnline();
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(false);


                    LabyHelp.getInstace().getCommentManager().refreshComments();

                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());

                    if (serverData.getIp().equalsIgnoreCase("mcone.eu")) {
                        labyPlayer.sendMessage(EnumChatFormatting.GREEN + "Du bist auf MCONE.EU gejoint... Auf diesem Server erhälts du weitere LabyHelp Features!");
                    } else if (serverData.getIp().equalsIgnoreCase("labor.mcone.eu")) {
                        labyPlayer.sendMessage(EnumChatFormatting.GREEN + "Du bist auf LABOR.MCONE.EU gejoint... Auf diesem Server erhälts du weitere LabyHelp Features!");
                    }

                }
            });
        } else {
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + " LabyHelp doesnt load correctly...");
        }
    }
}
