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
        if (LabyHelp.getInstace().AddonSettingsEnable) {
            LabyMod.getInstance().notifyMessageRaw("LabyHelp | Addon", "Use /LhHelp for all Commands");
        }

        LabyHelp.getInstace().onServer = true;

        if (LabyHelp.getInstace().isNewerVersion) {
            LabyMod.getInstance().notifyMessageRaw("LabyHelp | Addon", "There is a newer version.");
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + EnumChatFormatting.BOLD + " Please restart your game or download it manually");
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Nothing open? https://labyhelp.de");
        }

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
