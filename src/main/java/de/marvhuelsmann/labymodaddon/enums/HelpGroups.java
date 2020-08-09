package de.marvhuelsmann.labymodaddon.enums;

import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public enum HelpGroups {

    NICK("NICK", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.GRAY + " USER" + EnumChatFormatting.DARK_PURPLE + " (NICK)", false, false, false),
    USER("USER", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.GRAY + " USER", false, false, false),
    BAN("BAN", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.WHITE + " BLOCKED", false, false, false),
    PREMIUM("PREMIUM", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.DARK_AQUA + " PREMIUM", true, false, true),
    FRIEND("FRIEND", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.GOLD + " FRIEND", true, false, true),
    CREATOR("CREATOR", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.DARK_PURPLE + " CREATOR", true, false, true),
    LABYTEAM("LABYTEAM", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.BOLD + " LABYMOD " + EnumChatFormatting.BOLD + "TEAM", true, false, true),
    PARTNER("PARTNER", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.DARK_PURPLE + " PARTNER", true, false, true),
    HELPER("HELPER", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.BLUE + " HELPER", true, false, true),
    DESIGNER("DESIGNER", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.GREEN + " DESIGNER", true, true, true),
    CONTENT("CONTENT", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + " CONTENT", true, true, true),
    SRCONTENT("SRCONTENT", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + EnumChatFormatting.BOLD + " SrCONTENT", true, true, true),
    JRMODERATOR("JRMODERATOR", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.RED + " JrMOD", true, true, true),
    MODERATOR("MODERATOR", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.RED + " MOD", true, true, true),
    SRMODERATOR("SRMODERATOR", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.RED + EnumChatFormatting.BOLD + " SrMOD", true, true, true),
    DEVELOPER("DEVELOPER", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.DARK_AQUA + " DEVELOPER", true, true, true),
    ADMIN("ADMIN", EnumChatFormatting.WHITE + "LabyHelp" + EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " ADMIN", true, true, true);


    private final String name, prefix;
    private final Boolean isPremium, isTeam, showTag;

    HelpGroups(String name, String prefix, Boolean isPremium, Boolean isTeam, Boolean showTag) {
        this.name = name;
        this.prefix = prefix;
        this.isPremium = isPremium;
        this.isTeam = isTeam;
        this.showTag = showTag;

    }

    public static Boolean isExist(final String name) {
        if (HelpGroups.USER.getName().equalsIgnoreCase(name)
                || HelpGroups.PREMIUM.getName().equalsIgnoreCase(name) ||
                HelpGroups.NICK.getName().equalsIgnoreCase(name) ||
                HelpGroups.FRIEND.getName().equalsIgnoreCase(name) ||
                HelpGroups.BAN.getName().equalsIgnoreCase(name) ||
                HelpGroups.CREATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.LABYTEAM.getName().equalsIgnoreCase(name) ||
                HelpGroups.PARTNER.getName().equalsIgnoreCase(name) ||
                HelpGroups.HELPER.getName().equalsIgnoreCase(name) ||
                HelpGroups.DESIGNER.getName().equalsIgnoreCase(name) ||
                HelpGroups.CONTENT.getName().equalsIgnoreCase(name) ||
                HelpGroups.JRMODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.MODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.SRMODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.DEVELOPER.getName().equalsIgnoreCase(name) ||
                HelpGroups.ADMIN.getName().equalsIgnoreCase(name)) {
            return true;
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public Boolean getTeam() {
        return isTeam;
    }

    public Boolean getShowTag() {
        return showTag;
    }

}
