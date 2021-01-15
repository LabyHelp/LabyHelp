package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class SkinCMD implements HelpCommand {
    @Override
    public String getName() {
        return "skin";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            final UUID uuid = UUIDFetcher.getUUID(args[1]);
            labyPlayer.openSkin(uuid);
        }
    }
}
