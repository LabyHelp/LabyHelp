package de.marvhuelsmann.labymodaddon.enums;

import de.marvhuelsmann.labymodaddon.LabyHelp;

import java.util.Map;
import java.util.UUID;

public enum SocialMediaType {

    INSTAGRAM(LabyHelp.getInstace().getUserHandler().instaName),
    TWITTER(LabyHelp.getInstace().getUserHandler().twitterName),
    YOUTUBE(LabyHelp.getInstace().getUserHandler().youtubeName),
    TIKTOK(LabyHelp.getInstace().getUserHandler().tiktokName),
    SNAPCHAT(LabyHelp.getInstace().getUserHandler().snapchatName),
    TWTICH(LabyHelp.getInstace().getUserHandler().twitchName),
    DISCORD(LabyHelp.getInstace().getUserHandler().discordName);

    private final Map<UUID, String> map;

    SocialMediaType(Map<UUID, String> name) {
        this.map = name;
    }

    public Map<UUID, String> getMap() {
        return map;
    }

}