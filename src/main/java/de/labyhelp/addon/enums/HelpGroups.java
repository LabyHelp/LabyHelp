package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;


public enum HelpGroups {

    USER("USER", "§f" + "" + "§l" + "LabyHelp" + "§7" + " ", false, false,false, false, "rank.user"),

    BANNED("BANNED", "§f" + "" + "§l" + "LabyHelp" + "§7" + " ", false, false, false, false, "rank.user"),
    TARGET("TARGET", "§4" + "" + "§l" + " ", false, false, false, false, "rank.target"),

    FAME("FAME", "§f" + "" + "§l" + "LabyHelp" + "§e" + " FAME", true, false, false, false, null),
    FAMOUS("FAMOUS", "§f" + "" + "§l" + "LabyHelp" + "§e" + "§l" + " FAMOUS", false, true, false, false, null),
    INVITER("INVITER", "§f" + "" + "§l" + "LabyHelp" + "§3" + " ", true, false, false, false, "rank.inviter"),

    BOOSTER("BOOSTER", "§f" + "" + "§l" + "LabyHelp" + "§d" + "" + "§l" + " ", false, true, false, false, "rank.booster"),

    PREMIUM("PREMIUM", "§f" + "" + "§l" + "LabyHelp" + "§3" + " Premium", true, true, false, false, null),
    DONATOR("DONATOR", "§f" + "" + "§l" + "LabyHelp" + "§e" + "" + "§l" + " DONATOR", true, true, false, false,null),
    FRIEND("FRIEND", "§f" + "" + "§l" + "LabyHelp" + "§6" + " ", true, true,false, true, "rank.friend"),
    PREMIUM_("PREMIUM_", "§f" + "" + "§l" + "LabyHelp" + "§e" + "" + "§l" + " PREMIUM+", true, true, false, false,null),

    CREATOR("CREATOR", "§f" + "" + "§l" + "LabyHelp" + "§5" + "§l" + " ", true, true,false, true, "rank.creator"),
    PARTNER("PARTNER", "§f" + "" + "§l" + "LabyHelp" + "§5" + "§l" + " ", true, true,false, true, "rank.partner"),

    TRANSLATOR("TRANSLATOR", "§f" + "" + "§l" + "LabyHelp" + "§d" + " CONTENT", true, true,false, true, null),

    JRCONTENT("JRCONTENT", "§f" + "" + "§l" + "LabyHelp" + "§d" + " CONTENT", true, true,false, true, null),
    CONTENT("CONTENT", "§f" + "" + "§l" + "LabyHelp" + "§d" + " CONTENT", true, true,true, true, null),

    SRCONTENT("SRCONTENT", "§f" + "" + "§l" + "LabyHelp" + "§d" + "§l" + " SRCONTENT", true,true, true, true, null),

    JRSUPPORTER("JRSUPPORTER", "§f" + "" + "§l" + "LabyHelp" + "§c" + " SUPPORTER", true, true,false, true, null),
    SUPPORTER("SUPPORTER", "§f" + "" + "§l" + "LabyHelp" + "§c" + " SUPPORTER", true, true,false, true, null),

    JRMODERATOR("JRMODERATOR", "§f" + "" + "§l" + "LabyHelp" + "§c" + " JRMODERATOR", true, true,false, true, null),
    MODERATOR("MODERATOR", "§f" + "" + "§l" + "LabyHelp" + "§c" + " MODERATOR", true, true,true, true, null),
    SRMODERATOR("SRMODERATOR", "§f" + "" + "§l" + "LabyHelp" + "§c" + "§l" + " SRMODERATOR", true,true, true, true, null),

    DEVELOPER("DEVELOPER", "§f" + "" + "§l" + "LabyHelp" + "§3" + " Developer", true, true,true, true, null),
    SRDEVELOPER("SRDEVELOPER", "§f" + "" + "§l" + "LabyHelp" + "§3" + "§l" + " SRDEVELOPER", true, true,true, true, null),

    ADMIN("ADMIN", "§f" + "" + "§l" + "" + "LabyHelp" + "§4" + "§l" +" ADMIN", true,true, true, true, null),
    OWNER("OWNER", "§f" + "" + "§l" + "" + "LabyHelp" + "§4" + "§l" + " ADMIN", true,true, true, true, null);


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
