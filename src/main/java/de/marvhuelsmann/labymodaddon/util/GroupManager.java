package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import de.marvhuelsmann.labymodaddon.menu.LabyHelpMenu;
import net.labymod.main.LabyMod;
import net.labymod.user.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupManager {

    private static Map<UUID, HelpGroups> groupsMap;


    public static boolean isPremium(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getPremium();
    }

    public static boolean isTeam(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getTeam();
    }

    public static boolean isBanned(final UUID uuid) {
        GroupManager.groupsMap = WebServer.readGroups();

        if (groupsMap != null && !groupsMap.isEmpty()) {
            return groupsMap.containsKey(uuid) && groupsMap.containsValue(HelpGroups.BAN);
        }
        return false;
    }

    public static void updateSubTitles() {
        GroupManager.groupsMap = WebServer.readGroups();

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
}
