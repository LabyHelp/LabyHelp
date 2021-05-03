package de.labyhelp.addon.commands.auth;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class OneGamingIDCMD implements HelpCommand {
    @Override
    public String getName() {
        return "onegamingid";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(() -> {
            String code = LabyHelp.getInstance().getRequestManager().getVanillaRequest("https://marvhuelsmann.de/onegaming_id.php?uuid="  + LabyMod.getInstance().getPlayerUUID());

            labyPlayer.sendTranslMessage("main.onegamingid.thanks");
            labyPlayer.sendDefaultMessage("§7" + LabyHelp.getInstance().getTranslationManager().getTranslation("main.onegamingid.code") + "§c§l "+ code);
            labyPlayer.sendTranslMessage("main.token.finish");
            labyPlayer.sendDefaultMessage("https://id.onegaming.group/link/minecraft");

        });
    }
}
