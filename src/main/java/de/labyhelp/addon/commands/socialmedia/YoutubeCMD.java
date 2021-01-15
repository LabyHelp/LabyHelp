package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.SocialMediaType;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class YoutubeCMD implements HelpCommand {
    @Override
    public String getName() {
        return "youtube";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            final UUID uuid = UUIDFetcher.getUUID(args[1]);

            LabyPlayer labyTarget = new LabyPlayer(uuid);
            labyPlayer.openYoutube(labyTarget.getSocialMedia(SocialMediaType.YOUTUBE));
        }
    }
}
