package de.marvhuelsmann.labymodaddon.commands;

import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class SocialCMD implements HelpCommand {
    @Override
    public String getName() {
        return "social";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        final String decode = args[1];
        final UUID uuid = UUIDFetcher.getUUID(decode);
        labyPlayer.openSocial(uuid, decode);
    }
}
