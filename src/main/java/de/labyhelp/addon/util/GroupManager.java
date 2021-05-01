package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import net.labymod.main.LabyMod;


import java.util.*;

public class GroupManager {

    public boolean rainbow = false;

    public final Map<UUID, HelpGroups> userGroups = new HashMap<>();
    public final Map<UUID, HelpGroups> oldGroups = new HashMap<>();
    public final Map<UUID, String> userNameTags = new HashMap<>();
    public final Map<UUID, String> userSecondNameTags = new HashMap<>();

    private final Random random = new Random();
    private final String[] colorCodes;

    public GroupManager() {
        final List<String> colorCodes = new ArrayList<>();
        for (int i = 0; i < 16; i++)
            colorCodes.add(Integer.toHexString(i));
        this.colorCodes = colorCodes.toArray(new String[0]);
    }

    private final ArrayList<UUID> allPremiumPlayers = new ArrayList<>();

    public ArrayList<UUID> getAllPremiumPlayers() {
        allPremiumPlayers.clear();
        for (Map.Entry<UUID, HelpGroups> playerInList : userGroups.entrySet()) {
            if (!playerInList.getValue().equals(HelpGroups.BANNED) &&
                    !playerInList.getValue().equals(HelpGroups.USER)) {
                allPremiumPlayers.add(playerInList.getKey());
            }
        }
        return allPremiumPlayers;
    }

    public HelpGroups getBeforeRanked() {
        if (!oldGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : oldGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return oldGroups.get(groups.getKey());
                }
            }
        }
        return null;
    }

    public HelpGroups getNowRanked() {
        if (!userGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : userGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return userGroups.get(groups.getKey());
                }
            }
        }
        return null;
    }

    public boolean isPremium(final UUID uuid) {
        return userGroups.containsKey(uuid) && userGroups.get(uuid).getPremium();
    }

    public boolean isPremiumExtra(final UUID uuid) {
        return userGroups.containsKey(uuid) && userGroups.get(uuid).getPremiumExtra();
    }

    public boolean isBanned(final UUID uuid) {
        return userGroups.containsKey(uuid) && userGroups.get(uuid) == HelpGroups.BANNED;
    }

    public boolean isTeam(final UUID uuid) {
        if (userGroups.isEmpty()) {
            LabyHelp.getInstance().getCommunicatorHandler().readGroups();
        }
        return userGroups.containsKey(uuid) && userGroups.get(uuid).getTeam();
    }

    public HelpGroups getRanked(final UUID uuid) {
        return userGroups.get(uuid);
    }

    public boolean isTag(final UUID uuid) {
        return userGroups.containsKey(uuid) && userGroups.get(uuid).getTag();
    }

    public boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(true);
        }

        if (!userGroups.isEmpty()) {
            return userGroups.containsKey(uuid) && userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }

    public String randomColor() {
        return "§" + colorCodes[random.nextInt(colorCodes.length)];
    }
}
