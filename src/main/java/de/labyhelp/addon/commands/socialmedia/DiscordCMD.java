package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.SocialMediaType;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class DiscordCMD implements HelpCommand {
    @Override
    public String getName() {
        return "discord";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        final UUID uuid = UUIDFetcher.getUUID(args[1]);

        LabyPlayer labyTarget = new LabyPlayer(uuid);
        labyPlayer.sendDiscord(labyTarget.getSocialMedia(SocialMediaType.DISCORD));
    }
}
