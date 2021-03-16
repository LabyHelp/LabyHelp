package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
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


    private final Random RANDOM = new Random();
    private EnumChatFormatting chooseColor;

    private int getRandomNumberInRange() {
        return RANDOM.nextInt((11 - 1) + 1) + 1;
    }

    public EnumChatFormatting randomeColor() {
        if (getRandomNumberInRange() == 1) {
            chooseColor(EnumChatFormatting.YELLOW);
        } else if (getRandomNumberInRange() == 2) {
            return chooseColor(EnumChatFormatting.BLUE);
        } else if (getRandomNumberInRange() == 3) {
            return chooseColor(EnumChatFormatting.RED);
        } else if (getRandomNumberInRange() == 4) {
            return chooseColor(EnumChatFormatting.AQUA);
        } else if (getRandomNumberInRange() == 5) {
            return chooseColor(EnumChatFormatting.DARK_GREEN);
        } else if (getRandomNumberInRange() == 6) {
            return chooseColor(EnumChatFormatting.DARK_PURPLE);
        } else if (getRandomNumberInRange() == 7) {
            return chooseColor(EnumChatFormatting.DARK_GREEN);
        } else if (getRandomNumberInRange() == 8) {
            return chooseColor(EnumChatFormatting.GOLD);
        } else if (getRandomNumberInRange() == 9) {
            return chooseColor(EnumChatFormatting.GREEN);
        } else if (getRandomNumberInRange() == 10) {
            return chooseColor(EnumChatFormatting.LIGHT_PURPLE);
        } else if (getRandomNumberInRange() == 11) {
            return chooseColor(EnumChatFormatting.DARK_AQUA);
        }
        return randomeColor();
    }

    private EnumChatFormatting chooseColor(EnumChatFormatting color) {
        if (rainbow) {
            chooseColor = color;
            rainbow = false;

            return color;
        } else {
            if (chooseColor != null) {
                return chooseColor;
            } else {
                chooseColor = color;
                return color;
            }
        }
    }
}
