package de.labyhelp.addon.commands.socialmedia;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;

public class VoiceChatCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhvoicechat";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().sendTranslMessage("main.voicechat.open");
        LabyHelp.getInstance().getVoiceChatManager().readVoiceChats();
    }
}
