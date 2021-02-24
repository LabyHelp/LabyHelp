package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.SocialMediaType;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class TwitterCMD implements HelpCommand {
    @Override
    public String getName() {
        return "twitter";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            final String decode = args[1];
            final UUID uuid = UUIDFetcher.getUUID(decode);
            labyPlayer.openSocial(uuid, decode);
        } else {
            LabyHelp.getInstance().sendDefaultMessage("/social <Player>");
        }
    }
}
