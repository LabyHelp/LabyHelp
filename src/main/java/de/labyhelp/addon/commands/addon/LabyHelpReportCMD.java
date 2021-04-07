package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.UUID;

public class LabyHelpReportCMD implements HelpCommand {

    private final HashSet<UUID> alreadyReported = new HashSet<>();

    @Override
    public String getName() {
        return "lhreport";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            if (LabyHelp.getInstance().getGroupManager().userGroups.containsKey(UUIDFetcher.getUUID(args[1]))) {

                UUID playerUUID = UUIDFetcher.getUUID(args[1]);

                if (playerUUID == LabyMod.getInstance().getPlayerUUID()) {
                    LabyHelp.getInstance().sendTranslMessage("main.report.self");
                    return;
                } else if (LabyHelp.getInstance().getGroupManager().isTeam(playerUUID)) {
                    LabyHelp.getInstance().sendTranslMessage("main.report.team");
                    return;
                } else if (alreadyReported.contains(UUIDFetcher.getUUID(args[1]))) {
                    LabyHelp.getInstance().sendTranslMessage("main.report.already");
                    return;
                }

                String nameTag = LabyHelp.getInstance().getGroupManager().userNameTags.get(playerUUID) != null ? LabyHelp.getInstance().getGroupManager().userNameTags.get(playerUUID) : "ERROR";
                String secondNameTag = LabyHelp.getInstance().getGroupManager().userSecondNameTags.get(playerUUID) != null ? LabyHelp.getInstance().getGroupManager().userSecondNameTags.get(playerUUID) : "ERROR";

                try {
                    LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendReport.php?uuid=" + playerUUID + "&name=" + args[1] + "&fromUuid=" + LabyMod.getInstance().getPlayerUUID() + "&nametag=" + URLEncoder.encode(nameTag, "UTF-8") + "&secondNameTag=" + URLEncoder.encode(secondNameTag, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LabyHelp.getInstance().sendDeveloperMessage(e.getMessage());
                    e.printStackTrace();
                }

                alreadyReported.add(UUIDFetcher.getUUID(args[1]));
                labyPlayer.sendTranslMessage("main.report.success");

            } else {
                labyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            labyPlayer.sendDefaultMessage("- /lhreport -" + LabyHelp.getInstance().getTranslationManager().getTranslation("player"));
        }
    }
}
