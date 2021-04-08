package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;
import lombok.Getter;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public enum Tags {

    NOTHING("", false, "Nothing", "none", null, false),
    DISCORD_NORMAL_TAG(EnumChatFormatting.GRAY + " ✪ ", false, "NORMAL", "tag", LabyHelp.getInstance().getTagManager().normalDiscordTag, true),
    DISCORD_RAINBOW_TAG(" ✪ ", true, "CHROME", "tag", LabyHelp.getInstance().getTagManager().chromeDiscordTag, true),

    PREMIUM_TAG(EnumChatFormatting.GOLD + " Ⓟ ", false, "PREMIUM", "premium", LabyHelp.getInstance().getGroupManager().getAllPremiumPlayers(), false),

    SERVER_TAG(EnumChatFormatting.DARK_AQUA + " Ⓢ ", false, "SERVER", "serverPartner", LabyHelp.getInstance().getTagManager().serverTag, true),
    EASTER_2021_TAG(EnumChatFormatting.GREEN + " ⚘ ", false, "EASTER2021", "easter", LabyHelp.getInstance().getTagManager().easterDiscordTag, false);

    private final String tagDisplayed;
    private final Boolean isRainbow;
    private final String requestName;
    private final String dataName;
    private final ArrayList<UUID> array;
    private final boolean isSpecial;

    Tags(String tagDisplayed, Boolean isRainbow, String requestName, String dataName, ArrayList<UUID> array, Boolean isSpecial) {
        this.tagDisplayed = tagDisplayed;
        this.isRainbow = isRainbow;
        this.requestName = requestName;
        this.dataName = dataName;
        this.array = array;
        this.isSpecial = isSpecial;
    }

    public static Boolean isExist(final String name) {
        return name.equalsIgnoreCase(Tags.NOTHING.name()) ||
                name.equalsIgnoreCase(Tags.DISCORD_RAINBOW_TAG.name()) ||
                name.equalsIgnoreCase(Tags.DISCORD_NORMAL_TAG.name()) ||
                name.equalsIgnoreCase(Tags.PREMIUM_TAG.name()) ||
                name.equalsIgnoreCase(Tags.SERVER_TAG.name()) ||
                name.equalsIgnoreCase(Tags.EASTER_2021_TAG.name());
    }
}
