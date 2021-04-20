package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class TargetCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhtarget";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                labyPlayer.sendTranslMessage("main.target.contains");

                if (!LabyHelp.getInstance().getSettingsManager().targetPlayers.isEmpty()) {
                    for (UUID targetPlayer : LabyHelp.getInstance().getSettingsManager().getTargetPlayers()) {
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "-" + UUIDFetcher.getName(targetPlayer));
                    }
                } else {
                    labyPlayer.sendAlertTranslMessage("main.target.null");
                }

            } else {
                labyPlayer.sendWarningTranslMessage("main.target.command.help");
            }
        } else if (args.length == 3) {

            if (!args[2].equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
                if (args[1].equalsIgnoreCase("add")) {

                    if (UUIDFetcher.getUUID(args[2]) == null) {
                        labyPlayer.sendAlertTranslMessage("minecraft.null");
                        return;
                    }

                    labyPlayer.sendSuccessTranslMessage("main.target.add");
                    LabyHelp.getInstance().getSettingsManager().targetPlayers.add(UUIDFetcher.getUUID(args[2]));
                    LabyHelp.getInstance().getGroupManager().userGroups.put(UUIDFetcher.getUUID(args[2]), HelpGroups.TARGET);

                    labyPlayer.sendDeveloperMessage("target add " + args[2]);

                } else if (args[1].equalsIgnoreCase("remove")) {

                    if (UUIDFetcher.getUUID(args[2]) == null) {
                        labyPlayer.sendAlertTranslMessage("minecraft.null");
                        return;
                    }

                    if (LabyHelp.getInstance().getSettingsManager().getTargetPlayers().contains(UUIDFetcher.getUUID(args[2]))) {
                        labyPlayer.sendSuccessTranslMessage("main.target.remove");

                        LabyHelp.getInstance().getSettingsManager().targetPlayers.remove(UUIDFetcher.getUUID(args[2]));
                        LabyHelp.getInstance().getGroupManager().userGroups.remove(UUIDFetcher.getUUID(args[2]));

                        labyPlayer.sendDeveloperMessage("target remove " + args[2]);
                    } else {
                        labyPlayer.sendAlertTranslMessage("main.target.list.null");
                    }


                } else {
                    labyPlayer.sendWarningTranslMessage("main.target.command.help");
                }
            } else {
                labyPlayer.sendAlertTranslMessage("main.target.self");
            }
        } else {
            labyPlayer.sendWarningTranslMessage("main.target.command.help");
        }
    }
}
