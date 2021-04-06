package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import net.minecraft.util.EnumChatFormatting;

import java.util.Random;
import java.util.UUID;

public class GroupManager {

    public boolean rainbow = false;

    public boolean isPremium(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getPremium();
    }

    public boolean isPremiumExtra(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getPremiumExtra();
    }

    public boolean isBanned(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid) == HelpGroups.BANNED;
    }

    public boolean isTeam(final UUID uuid) {
        if (LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            LabyHelp.getInstance().getCommunicatorHandler().readGroups();
        }
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getTeam();
    }

    public HelpGroups getRanked(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid);
    }

    public boolean isTag(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getTag();
    }

    public boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(true);
        }

        if (!LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }


    private EnumChatFormatting chooseColor;

    public EnumChatFormatting randomColor(boolean disco) {
        return chooseColor(EnumChatFormatting.values()[new Random().nextInt(16)], disco);
    }

    private EnumChatFormatting chooseColor(EnumChatFormatting color, boolean disco) {
        if (rainbow) {

            if (disco) {
                return color;
            }

            chooseColor = color;
            rainbow = false;

            return color;
        } else {
            if (disco) {
                return color;
            } else if (chooseColor != null) {
                return chooseColor;
            } else {
                chooseColor = color;
                return color;

            }
        }
    }
}
