package de.labyhelp.addon.commands.auth;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;

public class IdCMD implements HelpCommand {

    @Override
    public String getName() {
        return "labyhelpid";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(() -> {
            String code = LabyHelp.getInstance().getRequestManager().getVanillaRequest("https://labyhelp.de/idcode.php?uuid="  + LabyMod.getInstance().getPlayerUUID());

            labyPlayer.sendTranslMessage("main.dashboard.open");
            labyPlayer.openDashBoard(LabyMod.getInstance().getPlayerUUID(), code);

        });

    }
}
