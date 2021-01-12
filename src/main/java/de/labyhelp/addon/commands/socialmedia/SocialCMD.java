package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.util.commands.HelpCommand;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
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
