package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public class GroupManager {

    private static Map<UUID, HelpGroups> groupsMap;
    private static Map<UUID, String> nameTagMap;


    public static boolean isPremium(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getPremium();
    }

    public static boolean isTeam(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getTeam();
    }

    public static boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            GroupManager.groupsMap = WebServer.readGroups();
        }

        if (groupsMap != null && !groupsMap.isEmpty()) {
            return groupsMap.containsKey(uuid) && groupsMap.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }

    public static void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            GroupManager.groupsMap = WebServer.readGroups();
            return;
        }

        if (groupsMap != null && !groupsMap.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                HelpGroups group = groupsMap.getOrDefault(uuidUserEntry.getKey(), null);

                if (group != null) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(group.getPrefix());
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.9);
                }
            }
        }
    }

    public static void updateNameTag(boolean readDatabase) {
        if (readDatabase) {
            GroupManager.nameTagMap = WebServer.readNameTag();
            return;
        }

        if (nameTagMap != null && !nameTagMap.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                String name = nameTagMap.getOrDefault(uuidUserEntry.getKey(), null);

                    if (GroupManager.isBanned(uuidUserEntry.getKey(), false)) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.8);
                        return;
                    }

                    if (name != null) {
                        if (GroupManager.isPremium(uuidUserEntry.getKey())) {
                            LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.BOLD + name);
                        } else {
                            LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(name);
                        }
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.8);
                    }
            }
        }
    }

}
