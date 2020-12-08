package de.marvhuelsmann.labymodaddon.enums;

public enum CommandsList {

    INSTA("insta", true),
    YOUTUBE("youtube", true),
    DISCORD("discord", true),
    TWITTER("twitter", true),
    TIKTOK("tiktok", true),
    TWITCH("twitch", true),
    SNAPCHAT("snapchat", true),
    SOCIAL("social", true),
    BANDANA("bandana", true),
    CAPE("cape", true),
    HELP("lhhelp", false),
    RULES("lhrules", false),
    SKIN("skin", true),
    RELOAD("lhreload", true),
    LABYHELP("labyhelp", false),
    TEAM("lhteam", false),
    WEB("lhweb", true),
    BAN("lhban", true),
    LIKE("lhlike", true),
    LIKES("likes", true),
    LIKELIST("likelist", false),
    ADDONS("lhaddons", false),
    TARGET("lhtarget", true),
    TARGETMODE("lhmodetarget", false),
    COMMENT("lhcomment", true),
    COMMENTS("showcomments", true),
    INVITELIST("invitelist", false),
    CODE("lhcode", true),
    INVITES("invites", true);


    private final String name;
    private final boolean moreArgs;

    CommandsList(String name, Boolean moreArgs) {
        this.name = name;
        this.moreArgs = moreArgs;
    }

    public String getName() {
        return name;
    }

    public boolean isMoreArgs() {
        return moreArgs;
    }
}
