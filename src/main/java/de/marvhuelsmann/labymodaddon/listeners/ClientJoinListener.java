package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import de.marvhuelsmann.labymodaddon.util.CommunicatorHandler;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public class ClientJoinListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstace().onServer = true;

        postJoin(serverData);
    }

    public void postJoin(ServerData serverData) {
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LabyHelp.getInstace().getCommunicationManager().sendClient();

                    LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(false);

                    LabyHelp.getInstace().getStoreHandler().readHelpAddons();
                    LabyHelp.getInstace().getCommentManager().refreshComments();

                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());

                    if (serverData.getIp().equalsIgnoreCase("mcone.eu")) {
                        labyPlayer.sendMessage(EnumChatFormatting.GREEN + "Du bist auf MCONE.EU gejoint... Auf diesem Server erhälts du weitere LabyHelp Features!");
                    } else if (serverData.getIp().equalsIgnoreCase("labor.mcone.eu")) {
                        labyPlayer.sendMessage(EnumChatFormatting.GREEN + "Du bist auf LABOR.MCONE.EU gejoint... Auf diesem Server erhälts du weitere LabyHelp Features!");
                    }

                    LabyHelp.getInstace().addonEnabled = true;
                } catch (Exception ignored) {
                    LabyHelp.getInstace().addonEnabled = false;
                }
            }
        });
    }
}
