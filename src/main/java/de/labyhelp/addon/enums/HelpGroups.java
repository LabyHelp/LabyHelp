package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;
import net.minecraft.util.EnumChatFormatting;

public enum HelpGroups {

    USER("USER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.GRAY + " ", false, false,false, false, "rank.user"),

    BANNED("BANNED", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.GRAY + " ", false, false, false, false, "rank.user"),
    TARGET("TARGET", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " ", false, false, false, false, "rank.target"),

    FAME("FAME", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.YELLOW + " FAME", true, false, false, false, null),
    FAMOUS("FAMOUS", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + " FAMOUS", false, true, false, false, null),
    INVITER("INVITER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.AQUA + " ", true, false, false, false, "rank.inviter"),

    BOOSTER("BOOSTER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + " ", false, true, false, false, "rank.booster"),

    PREMIUM("PREMIUM", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.DARK_AQUA + " Premium", true, true, false, false, null),
    DONATOR("DONATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + " DONATOR", true, true, false, false,null),
    FRIEND("FRIEND", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.GOLD + " ", true, true,false, true, "rank.friend"),
    PREMIUM_("PREMIUM_", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + " PREMIUM+", true, true, false, false,null),

    CREATOR("CREATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.BOLD + " ", true, true,false, true, "rank.creator"),
    PARTNER("PARTNER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.BOLD + " ", true, true,false, true, "rank.partner"),

    TRANSLATOR("TRANSLATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + " CONTENT", true, true,false, true, null),

    JRCONTENT("JRCONTENT", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + " CONTENT", true, true,false, true, null),
    CONTENT("CONTENT", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + " CONTENT", true, true,true, true, null),

    SRCONTENT("SRCONTENT", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.LIGHT_PURPLE + EnumChatFormatting.BOLD + " SRCONTENT", true,true, true, true, null),

    JRSUPPORTER("JRSUPPORTER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.RED + " SUPPORTER", true, true,false, true, null),
    SUPPORTER("SUPPORTER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.RED + " SUPPORTER", true, true,false, true, null),

    JRMODERATOR("JRMODERATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.RED + " JRMODERATOR", true, true,false, true, null),
    MODERATOR("MODERATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.RED + " MODERATOR", true, true,true, true, null),
    SRMODERATOR("SRMODERATOR", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.RED + EnumChatFormatting.BOLD + " SRMODERATOR", true,true, true, true, null),

    DEVELOPER("DEVELOPER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.AQUA + " Developer", true, true,true, true, null),
    SRDEVELOPER("SRDEVELOPER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "LabyHelp" + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + " SRDEVELOPER", true, true,true, true, null),

    ADMIN("ADMIN", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + "LabyHelp" + EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD +" ADMIN", true,true, true, true, null),
    OWNER("OWNER", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + "LabyHelp" + EnumChatFormatting.DARK_RED + EnumChatFormatting.BOLD + " OWNER", true,true, true, true, null);


    private final String name, prefix;
    private final Boolean isPremium;
    private final Boolean isPremiumExtra;
    private final Boolean isTeam;
    private final Boolean showTag;
    private final String translationKey;

    HelpGroups(String name, String prefix, Boolean isPremium, Boolean isPremiumExtra, Boolean isTeam, Boolean showTag, String translationKey) {
        this.name = name;
        this.prefix = prefix;
        this.isPremium = isPremium;
        this.isPremiumExtra = isPremiumExtra;
        this.isTeam = isTeam;
        this.showTag = showTag;
        this.translationKey = translationKey;
    }

    public String getSubtitle() {
        if (translationKey == null) {
            return prefix;
        } else {
            return prefix + LabyHelp.getInstance().getTranslationManager().getTranslation(translationKey);
        }
    }

    public static Boolean isExist(final String name) {
        return HelpGroups.USER.getName().equalsIgnoreCase(name)
                || HelpGroups.PREMIUM.getName().equalsIgnoreCase(name) ||
                HelpGroups.FAME.getName().equalsIgnoreCase(name) ||
                HelpGroups.FAMOUS.getName().equalsIgnoreCase(name) ||
                HelpGroups.INVITER.getName().equalsIgnoreCase(name) ||
                HelpGroups.PREMIUM_.getName().equalsIgnoreCase(name) ||
                HelpGroups.BOOSTER.getName().equalsIgnoreCase(name) ||
                HelpGroups.TARGET.getName().equalsIgnoreCase(name) ||
                HelpGroups.DONATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.FRIEND.getName().equalsIgnoreCase(name) ||
                HelpGroups.BANNED.getName().equalsIgnoreCase(name) ||
                HelpGroups.CREATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.JRSUPPORTER.getName().equalsIgnoreCase(name) ||
                HelpGroups.SUPPORTER.getName().equalsIgnoreCase(name) ||
                HelpGroups.PARTNER.getName().equalsIgnoreCase(name) ||
                HelpGroups.TRANSLATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.JRCONTENT.getName().equalsIgnoreCase(name) ||
                HelpGroups.CONTENT.getName().equalsIgnoreCase(name) ||
                HelpGroups.SRCONTENT.getName().equalsIgnoreCase(name) ||
                HelpGroups.JRMODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.MODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.SRMODERATOR.getName().equalsIgnoreCase(name) ||
                HelpGroups.DEVELOPER.getName().equalsIgnoreCase(name) ||
                HelpGroups.SRDEVELOPER.getName().equalsIgnoreCase(name) ||
                HelpGroups.ADMIN.getName().equalsIgnoreCase(name) ||
                HelpGroups.OWNER.getName().equalsIgnoreCase(name);
    }


    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public Boolean getTeam() {
        return isTeam;
    }

    public Boolean getTag() {
        return showTag;
    }


    public Boolean getPremiumExtra() {
        return isPremiumExtra;
    }
}
