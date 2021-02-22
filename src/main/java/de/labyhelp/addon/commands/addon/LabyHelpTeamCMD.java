package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class LabyHelpTeamCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhteam";
    }

    @Override
    public void execute(LabyPlayer clientLabyPlayer, String[] args) {
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "LabyHelp Team:");
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "Position: " + LocalDate.now());
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Adminstration");
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "- marvhuel");
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.ADMIN) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.OWNER)) {
                            if (!groupsEntry.getKey().toString().equals("d4389488-2692-436b-bc10-fce879f7441d")) {
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                            }
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Developers");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.DEVELOPER) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRDEVELOPER)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Moderation");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.MODERATOR) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRMODERATOR)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Contents");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.CONTENT) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRCONTENT)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Supporter");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                        if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SUPPORTER)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Translator");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getCommunicatorHandler().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.TRANSLATOR)) {
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                    }
                }
              }
        });
    }
}
