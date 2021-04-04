package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

public class LabyHelpPartnerCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhpartner";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            if (LabyHelp.getInstance().getPartnerHandler().isPartnerIp(args[1])) {
                labyPlayer.sendServer(LabyHelp.getInstance().getPartnerHandler().getPartnerIp(args[1]));
            } else {
                labyPlayer.sendAlertTranslMessage("main.partner.already");
            }
        } else if (args.length == 1) {
            labyPlayer.sendTranslMessage("main.partner.list");
            for (Map.Entry<String, String> partnerList : LabyHelp.getInstance().getPartnerHandler().getPartner().entrySet()) {
                labyPlayer.sendDefaultMessage(EnumChatFormatting.GOLD + partnerList.getKey() + EnumChatFormatting.WHITE + " - /lhpartner " + partnerList.getKey());
            }
            labyPlayer.sendDefaultMessage(LabyHelp.getInstance().getTranslationManager().getTranslation("main.moreat") + EnumChatFormatting.GOLD + " https://labyhelp.de/partner");
        } else {
            labyPlayer.sendDefaultMessage("/lhpartner || /lhpartner <Server>");
        }
    }
}
