package de.marvhuelsmann.labymodaddon.enums;

import de.marvhuelsmann.labymodaddon.LabyHelp;

import java.util.Map;
import java.util.UUID;

public enum SocialMediaType {

    INSTAGRAM(LabyHelp.getInstace().getSocialMediaManager().instaNameMap, LabyHelp.getInstace().getSocialMediaManager().instaName, "Insta"),
    TWITTER(LabyHelp.getInstace().getSocialMediaManager().twitterNameMap, LabyHelp.getInstace().getSocialMediaManager().twitterName, "Twitter"),
    YOUTUBE(LabyHelp.getInstace().getSocialMediaManager().youtubeNameMap, LabyHelp.getInstace().getSocialMediaManager().youtubeName, "Youtube"),
    TIKTOK(LabyHelp.getInstace().getSocialMediaManager().tiktokNameMap, LabyHelp.getInstace().getSocialMediaManager().tiktokName, "TikTok"),
    SNAPCHAT(LabyHelp.getInstace().getSocialMediaManager().snapchatNameMap, LabyHelp.getInstace().getSocialMediaManager().snapchatName, "Snapchat"),
    TWTICH(LabyHelp.getInstace().getSocialMediaManager().twitchNameMap, LabyHelp.getInstace().getSocialMediaManager().twitchName, "Twitch"),
    DISCORD(LabyHelp.getInstace().getSocialMediaManager().discordNameMap, LabyHelp.getInstace().getSocialMediaManager().discordName, "Discord"),
    STATUS(LabyHelp.getInstace().getCommunicationManager().userNameTags, LabyHelp.getInstace().getSocialMediaManager().statusName, "Status"),
    NAMETAG(null, LabyHelp.getInstace().getSocialMediaManager().nameTagName, "NameTag");

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