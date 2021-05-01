package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;


public enum HelpGroups {

    USER("USER", "§f§lLabyHelp§7 ", false, false,false, false, "rank.user"),

    BANNED("BANNED", "§f§lLabyHelp§7 ", false, false, false, false, "rank.user"),
    TARGET("TARGET", "§4§l ", false, false, false, false, "rank.target"),

    FAME("FAME", "§f§lLabyHelp§e "+ " FAME", true, false, false, false, null),
    FAMOUS("FAMOUS", "§f§lLabyHelp§e§l "+ " FAMOUS", false, true, false, false, null),
    INVITER("INVITER", "§f§lLabyHelp§3 ",true, false, false, false, "rank.inviter"),

    BOOSTER("BOOSTER", "§f§lLabyHelp§d§l ",false, true, false, false, "rank.booster"),

    PREMIUM("PREMIUM", "§f§lLabyHelp§3" + " Premium",true, true, false, false, null),
    DONATOR("DONATOR", "§f§lLabyHelp§e§l" + " DONATOR",true, true, false, false,null),
    FRIEND("FRIEND", "§f§lLabyHelp§6 ", true, true,false, true, "rank.friend"),
    PREMIUM_("PREMIUM_", "§f§lLabyHelp§e§l" +" PREMIUM+", true, true, false, false,null),

    CREATOR("CREATOR", "§f§lLabyHelp§5§l ", true, true,false, true, "rank.creator"),
    PARTNER("PARTNER", "§f§lLabyHelp§5§l ", true, true,false, true, "rank.partner"),

    TRANSLATOR("TRANSLATOR", "§f§lLabyHelp§d" + " CONTENT", true, true,false, true, null),

    JRCONTENT("JRCONTENT", "§f§lLabyHelp§d" + " CONTENT", true, true,false, true, null),
    CONTENT("CONTENT", "§f§lLabyHelp§d" + " CONTENT", true, true,true, true, null),

    SRCONTENT("SRCONTENT", "§f§lLabyHelp§d§l" + " SRCONTENT", true,true, true, true, null),

    JRSUPPORTER("JRSUPPORTER", "§f§lLabyHelp§c§l" + " SUPPORTER", true, true,false, true, null),
    SUPPORTER("SUPPORTER", "§f§lLabyHelp§c" + " SUPPORTER", true, true,false, true, null),

    JRMODERATOR("JRMODERATOR", "§f§lLabyHelp§c" + " JRMODERATOR", true, true,false, true, null),
    MODERATOR("MODERATOR", "§f§lLabyHelp§c" + " MODERATOR", true, true,true, true, null),
    SRMODERATOR("SRMODERATOR", "§f§lLabyHelp§c§l" + " SRMODERATOR", true,true, true, true, null),

    DEVELOPER("DEVELOPER", "§f§lLabyHelp§3" + " Developer", true, true,true, true, null),
    SRDEVELOPER("SRDEVELOPER", "§f§lLabyHelp§3§l" + " SRDEVELOPER", true, true,true, true, null),

    ADMIN("ADMIN", "§f§lLabyHelp§4§l" + " ADMIN", true,true, true, true, null),
    OWNER("OWNER", "§f§lLabyHelp§4§l" + " ADMIN", true,true, true, true, null);


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
