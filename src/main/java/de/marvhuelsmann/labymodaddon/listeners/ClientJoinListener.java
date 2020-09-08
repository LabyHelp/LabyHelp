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
            LabyMod.getInstance().notifyMessageRaw("LabyHelp | Addon", "Es gibt eine neuere LabyHelp Version!");
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + EnumChatFormatting.BOLD + " Es gibt eine neuere LabyHelp Version. Dein Browser wurde geoeffnet!");
            LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Nichts geoeffnet? https://labyhelp.de");
            LabyMod.getInstance().openWebpage("https://labyhelp.de", true);
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
