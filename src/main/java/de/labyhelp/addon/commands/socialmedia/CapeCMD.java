package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class CapeCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhcape";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            final UUID uuid = UUIDFetcher.getUUID(args[1]);
            labyPlayer.openCapeUrl(uuid);
        }
    }
}
