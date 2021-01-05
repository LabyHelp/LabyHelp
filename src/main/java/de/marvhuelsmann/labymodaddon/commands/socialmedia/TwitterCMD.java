package de.marvhuelsmann.labymodaddon.commands.socialmedia;

import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class TwitterCMD implements HelpCommand {
    @Override
    public String getName() {
        return "twitter";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        final UUID uuid = UUIDFetcher.getUUID(args[1]);

        LabyPlayer labyTarget = new LabyPlayer(uuid);
        labyPlayer.openTwitter(labyTarget.getSocialMedia(SocialMediaType.TWITTER));
    }
}
