package de.labyhelp.addon.enums;

import com.sun.org.apache.xpath.internal.operations.Bool;
import de.labyhelp.addon.LabyHelp;
import lombok.Getter;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.UUID;

@Getter
public enum Tags {

    DISCORD_NORMAL_TAG(EnumChatFormatting.GRAY + "✪ ", false, LabyHelp.getInstance().getTagManager().discordTagList, "NORMAL"),
    DISCORD_RAINBOW_TAG( "✪ ", true, LabyHelp.getInstance().getTagManager().discordTagList, "CHROME"),

    SERVER_TAG(EnumChatFormatting.DARK_AQUA + " Ⓢ", false, LabyHelp.getInstance().getTagManager().serverTagList, "SERVER");

    private final String tagDisplayed;
    private final Boolean isRainbow;
    private final HashMap<UUID, Tags> mapName;
    private final String requestName;

    Tags(String tagDisplayed, Boolean isRainbow, HashMap<UUID, Tags> mapName, String requestName) {
        this.tagDisplayed = tagDisplayed;
        this.isRainbow = isRainbow;
        this.mapName = mapName;
        this.requestName = requestName;
    }
}
