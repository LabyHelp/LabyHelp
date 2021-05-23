package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;


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
        clientLabyPlayer.sendDefaultMessage("§c" + "LabyHelp Team:");
        clientLabyPlayer.sendDefaultMessage("§c" + "Position: " + LocalDate.now());
        clientLabyPlayer.sendDefaultMessage("§f" + "Addon Administration");
        clientLabyPlayer.sendDefaultMessage("§e" + "" + "§l" + "- marvhuel");
        LabyHelp.getInstance().getExecutor().submit(() -> {
            for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.ADMIN) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.OWNER)) {
                        if (!groupsEntry.getKey().toString().equals("d4389488-2692-436b-bc10-fce879f7441d")) {
                            clientLabyPlayer.sendDefaultMessage("§e" + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
            }
            clientLabyPlayer.sendDefaultMessage("§f" + "Addon Developers");
            for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.DEVELOPER) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRDEVELOPER)) {
                        clientLabyPlayer.sendDefaultMessage("§e" + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                    }
                }
            }
            clientLabyPlayer.sendDefaultMessage("§f" + "Addon Moderation");
            for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.MODERATOR) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRMODERATOR)) {
                        clientLabyPlayer.sendDefaultMessage("§e" + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                    }
                }
            }
            clientLabyPlayer.sendDefaultMessage("§f" + "Addon Contents");
            for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                if (LabyHelp.getInstance().getGroupManager().isTeam(groupsEntry.getKey())) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.CONTENT) || LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRCONTENT)) {
                        clientLabyPlayer.sendDefaultMessage("§e" + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                    }
                }
            }
            clientLabyPlayer.sendDefaultMessage("§f" + "Addon Supporter");
            for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                    if (LabyHelp.getInstance().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SUPPORTER)) {
                        clientLabyPlayer.sendDefaultMessage("§e" + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                    }
            }
          });
    }
}
