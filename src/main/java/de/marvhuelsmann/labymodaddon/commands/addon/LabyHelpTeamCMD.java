package de.marvhuelsmann.labymodaddon.commands.addon;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
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
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Administation");
        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "- marvhuel");
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.ADMIN) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.OWNER)) {
                            if (!groupsEntry.getKey().toString().equals("d4389488-2692-436b-bc10-fce879f7441d")) {
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                            }
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Developers");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.DEVELOPER)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Moderation");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.MODERATOR) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRMODERATOR)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Contents");
                for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                        if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.CONTENT) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRCONTENT)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                        }
                    }
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.DARK_RED + LabyHelp.getInstace().getTranslationManager().getTranslation("main.teamlist") + EnumChatFormatting.WHITE + " https://labyhelp.de/");
            }
        });
    }
}
