package de.labyhelp.addon.commands.team;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class LabyHelpBanCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhban";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        TranslationManager transManager = LabyHelp.getInstance().getTranslationManager();
        if (LabyHelp.getInstance().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
            if (args.length == 2) {
                final UUID uuid = UUIDFetcher.getUUID(args[1]);
                if (!LabyHelp.getInstance().getGroupManager().isTeam(uuid)) {
                    if (uuid != null) {
                        clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("player") + " " + "§f" + args[1] + "" + "§f" + transManager.getTranslation("staff.banned.nametag"));
                        sendBanned(uuid);
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendTranslMessage("main.isteam");
                }
            } else {
                clientLabyPlayer.sendTranslMessage("use.ban");
            }
        } else {
            clientLabyPlayer.sendNoPerms();
        }
    }

    private void sendBanned(UUID uuid) {
        LabyHelp.getInstance().sendDeveloperMessage("banned user uuid: " + uuid.toString());
        LabyHelp.getInstance().sendDeveloperMessage("from user uuid: " + LabyMod.getInstance().getPlayerUUID());
        LabyHelp.getInstance().sendDeveloperMessage("reason: NAMETAG");
        LabyHelp.getInstance().getRequestManager().sendRequest("https://labyhelp.de/sendBan.php?uuid=" + uuid + "&fromUuid=" + LabyMod.getInstance().getPlayerUUID() + "&reason=NAMETAG" );
    }
}
