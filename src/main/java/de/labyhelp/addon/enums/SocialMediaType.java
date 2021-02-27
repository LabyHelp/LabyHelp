package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;

import java.util.Map;
import java.util.UUID;

public enum SocialMediaType {

    INSTAGRAM(null, LabyHelp.getInstance().getSocialMediaManager().instaName, "Insta"),
    TWITTER(null, LabyHelp.getInstance().getSocialMediaManager().twitterName, "Twitter"),
    YOUTUBE(null, LabyHelp.getInstance().getSocialMediaManager().youtubeName, "Youtube"),
    TIKTOK(null, LabyHelp.getInstance().getSocialMediaManager().tiktokName, "TikTok"),
    SNAPCHAT(null, LabyHelp.getInstance().getSocialMediaManager().snapchatName, "Snapchat"),
    TWTICH(null, LabyHelp.getInstance().getSocialMediaManager().twitchName, "Twitch"),
    DISCORD(null, LabyHelp.getInstance().getSocialMediaManager().discordName, "Discord"),
    STATUS(LabyHelp.getInstance().getCommunicatorHandler().userNameTags, LabyHelp.getInstance().getSocialMediaManager().statusName, "Status"),
    SERVER(LabyHelp.getInstance().getServerManager().hasServer, null, "Server"),
    NAMETAG(null, LabyHelp.getInstance().getSocialMediaManager().nameTagName, "NameTag");

    private final Map<UUID, String> map;
    private final String name;
    private final String urlName;

    SocialMediaType(Map<UUID, String> name, String names, String urlName) {
        this.map = name;
        this.name = names;
        this.urlName = urlName;
    }

    public Map<UUID, String> getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public String getUrlName() {
        return urlName;
    }
}