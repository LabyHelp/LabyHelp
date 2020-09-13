package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public class GroupManager {

    public boolean isPremium(final UUID uuid) {
        return LabyHelp.getInstace().getSocialHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getSocialHandler().userGroups.get(uuid).getPremium();
    }

    public boolean isTeam(final UUID uuid) {
        return LabyHelp.getInstace().getSocialHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getSocialHandler().userGroups.get(uuid).getTeam();
    }

    public static boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstace().getSocialHandler().readUserInformations(true);
        }

        if (!LabyHelp.getInstace().getSocialHandler().userGroups.isEmpty()) {
            return LabyHelp.getInstace().getSocialHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getSocialHandler().userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }


    public void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getSocialHandler().readUserInformations(true);
            }
            return;
        }

        if (!LabyHelp.getInstace().getSocialHandler().userGroups.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                HelpGroups group = LabyHelp.getInstace().getSocialHandler().userGroups.getOrDefault(uuidUserEntry.getKey(), null);
                if (group != null) {
                    if (LabyHelp.getInstace().getSocialHandler().isOnline.get(uuidUserEntry.getKey()).equalsIgnoreCase("ONLINE")) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(group.getPrefix());
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.9);
                    }
                }
            }
        }
    }

    public void updateNameTag(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getSocialHandler().readUserInformations(false);
            }
            return;
        }

        if (!LabyHelp.getInstace().getSocialHandler().userNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                String name = LabyHelp.getInstace().getSocialHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null);

                if (isBanned(uuidUserEntry.getKey(), false)) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.8);
                    return;
                }

                if (name != null) {
                    if (LabyHelp.getInstace().getSocialHandler().isOnline.get(uuidUserEntry.getKey()).equalsIgnoreCase("ONLINE")) {
                        if (isPremium(uuidUserEntry.getKey())) {
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

}
