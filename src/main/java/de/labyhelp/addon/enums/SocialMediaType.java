package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;

import java.util.Map;
import java.util.UUID;

public enum SocialMediaType {

    INSTAGRAM(LabyHelp.getInstance().getSocialMediaManager().instaNameMap, LabyHelp.getInstance().getSocialMediaManager().instaName, "Insta"),
    TWITTER(LabyHelp.getInstance().getSocialMediaManager().twitterNameMap, LabyHelp.getInstance().getSocialMediaManager().twitterName, "Twitter"),
    YOUTUBE(LabyHelp.getInstance().getSocialMediaManager().youtubeNameMap, LabyHelp.getInstance().getSocialMediaManager().youtubeName, "Youtube"),
    TIKTOK(LabyHelp.getInstance().getSocialMediaManager().tiktokNameMap, LabyHelp.getInstance().getSocialMediaManager().tiktokName, "TikTok"),
    SNAPCHAT(LabyHelp.getInstance().getSocialMediaManager().snapchatNameMap, LabyHelp.getInstance().getSocialMediaManager().snapchatName, "Snapchat"),
    TWTICH(LabyHelp.getInstance().getSocialMediaManager().twitchNameMap, LabyHelp.getInstance().getSocialMediaManager().twitchName, "Twitch"),
    DISCORD(LabyHelp.getInstance().getSocialMediaManager().discordNameMap, LabyHelp.getInstance().getSocialMediaManager().discordName, "Discord"),
    STATUS(LabyHelp.getInstance().getCommunicatorHandler().userNameTags, LabyHelp.getInstance().getSocialMediaManager().statusName, "Status"),
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