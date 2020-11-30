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
        return LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getUserHandler().userGroups.get(uuid).getPremium();
    }

    public boolean isTeam(final UUID uuid) {
        if (LabyHelp.getInstace().getUserHandler().userGroups.isEmpty()) {
            LabyHelp.getInstace().getUserHandler().readGroups();
        }
        return LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getUserHandler().userGroups.get(uuid).getTeam();
    }

    public HelpGroups getRanked(final UUID uuid) {
        return LabyHelp.getInstace().getUserHandler().userGroups.get(uuid);
    }

    public boolean isTag(final UUID uuid) {
        return LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getUserHandler().userGroups.get(uuid).getTag();
    }

    public static boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstace().getUserHandler().readUserInformations(true);
        }

        if (!LabyHelp.getInstace().getUserHandler().userGroups.isEmpty()) {
            return LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid) && LabyHelp.getInstace().getUserHandler().userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }


    public void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getUserHandler().readUserInformations(true);
            }
            return;
        }


        if (!LabyHelp.getInstace().getUserHandler().userGroups.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                HelpGroups group = LabyHelp.getInstace().getUserHandler().userGroups.getOrDefault(uuidUserEntry.getKey(), null);
                if (group != null) {
                    // if (LabyHelp.getInstace().getUserHandler().isOnline.get(uuidUserEntry.getKey()).equalsIgnoreCase("ONLINE")) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(group.getPrefix());
                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }

                    // }
                }

                if (LabyHelp.getInstace().targetMode) {
                    if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "TARGET");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(2);
                    }
                }

            }
        }
    }


    public void updateNameTag(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getUserHandler().readUserInformations(false);
            }
            return;
        }

        if (!LabyHelp.getInstace().getUserHandler().userNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                String name = LabyHelp.getInstace().getUserHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null);

                if (isBanned(uuidUserEntry.getKey(), false)) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                    return;
                }

                if (name != null) {
                    String finalTag = name.replace("&", "§");

                    String finalRo = finalTag.replace("{likes}", LabyHelp.getInstace().getUserHandler().getLikes(uuidUserEntry.getKey()));

                    if (!isTag(uuidUserEntry.getKey())) {
                        String tag = finalRo.replaceAll("LabyHelp", "");
                        String finishFinalTag = tag.replaceAll("LabyMod", "");

                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + finishFinalTag);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + finalRo);
                    }

                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }

                if (LabyHelp.getInstace().targetMode) {
                    if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "TARGET");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(2);
                    }
                }

            }
        }
    }
}
