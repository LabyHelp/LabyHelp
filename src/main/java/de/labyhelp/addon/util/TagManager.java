package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.enums.Tags;
import de.labyhelp.addon.enums.TagsSide;
import net.labymod.main.LabyMod;
import net.labymod.user.group.LabyGroup;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TagManager {

    public HashMap<UUID, String> hasServer = new HashMap<>();

    public ArrayList<UUID> normalDiscordTag = new ArrayList<>();
    public ArrayList<UUID> serverTag = new ArrayList<>();
    public ArrayList<UUID> chromeDiscordTag = new ArrayList<>();
    public ArrayList<UUID> easterDiscordTag = new ArrayList<>();
    private EnumChatFormatting colorCache;

    public HashMap<UUID, Tags> rightPlayerList = new HashMap<>();
    public HashMap<UUID, Tags> leftPlayerList = new HashMap<>();


    /**
     * Init the TagManager and reload all
     */
    public void initTagManager() {
        for (Tags tag : Tags.values()) {
            if (!tag.equals(Tags.NOTHING)) {
                tag.getArray().clear();
                if (!tag.equals(Tags.SERVER_TAG) && !tag.equals(Tags.PREMIUM_TAG)) {
                    readSpecificTag(tag);
                }
            }
        }

        readServerPartner();
        for (TagsSide side : TagsSide.values()) {
            readSideTags(side);
        }
    }

    /**
     * Send the specific Badge with the side to the database
     *
     * @param defaultSide The side of the Badge
     * @param uuid        For which uuid
     * @param tags        For which tag
     */
    public void sendSpecificTag(boolean defaultSide, UUID uuid, Tags tags) {
        LabyHelp.getInstance().getRequestManager().sendRequest("https://labyhelp.de/sendTagSide.php?uuid=" + uuid + "&side=" + (defaultSide ? "RIGHT" : "LEFT") + "&tag=" + tags.name() + "&data=" + tags.getDataName());
    }

    /**
     * Read the the player with the badges
     */
    private void readSpecificTag(Tags tag) {
        tag.getArray().clear();
        LabyHelp.getInstance().getRequestManager().getStandardArrayList("https://labyhelp.de/tags.php?ex=" + tag.getRequestName(), tag.getArray());
    }


    /**
     * Read the the player with the badge side
     *
     * @param tag The specific badge side
     */
    private void readSideTags(TagsSide tag) {
        tag.getMap().clear();

        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://labyhelp.de/tagsSide.php?side=" + tag.name())) {
            final String[] data = entry.split(":");
            if (data.length == 2) {
                String uuid = data[0];
                if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    if (hasPermissionToTag(UUID.fromString(data[0]), Tags.valueOf(data[1]))) {
                        tag.getMap().put(UUID.fromString(data[0]), Tags.valueOf(data[1]));
                    }
                }
            }
        }
    }

    /**
     * Set the normal Tag into the Subtitle area
     *
     * @param uuid  For the specific uuid
     * @param group For the specific group
     */
    public void setNormalTag(UUID uuid, HelpGroups group) {
        LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(getSideTag(uuid, false) + group.getSubtitle() + getSideTag(uuid, true));
    }

    /**
     * Get if the Badge is already set at the side of the boolean
     *
     * @param uuid For the specific uuid
     * @param tag  For the specific group
     * @param side For the side to check
     * @return The boolean if it is set or not
     */
    public boolean hasAlreadySet(UUID uuid, Tags tag, Boolean side) {
        if (!tag.equals(Tags.NOTHING)) {
            for (TagsSide tags : TagsSide.values()) {
                if (side ? tags.equals(TagsSide.RIGHT) : tags.equals(TagsSide.LEFT)) {
                    for (Map.Entry<UUID, Tags> map : tags.getMap().entrySet()) {
                        if (map.getKey().equals(uuid) && map.getValue().equals(tag)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * Check if the player has the permission to the tag
     *
     * @param uuid To get the Tag with the UUID
     * @param tag  The special tag
     * @return if the player has the permission
     */
    public boolean hasPermissionToTag(UUID uuid, Tags tag) {
        if (!LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            if (!tag.equals(Tags.NOTHING)) {
                if (LabyHelp.getInstance().getGroupManager().isTeam(uuid) && !tag.isSpecial()) {
                    return true;
                } else {
                    return tag.getArray().contains(uuid);
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Get the side of the Tag
     *
     * @param uuid        To set the Tag with the UUID
     * @param defaultSide This means if it is right or left
     *  Note* defaultSide = right
     */
    private String getSideTag(UUID uuid, boolean defaultSide) {
        if (defaultSide) {
            if (TagsSide.RIGHT.getMap().containsKey(uuid)) {
                return getTag(uuid, TagsSide.RIGHT.getMap().get(uuid));
            } else {
                return "";
            }
        } else {
            if (TagsSide.LEFT.getMap().containsKey(uuid)) {
                return getTag(uuid, TagsSide.LEFT.getMap().get(uuid));
            } else {
                return "";
            }
        }
    }

    /**
     * Get special Tag
     *
     * @param uuid To set the Tag with the UUID
     * @param tag  To set the special Tag for the player
     */
    private String getTag(UUID uuid, Tags tag) {
        if (hasPermissionToTag(uuid, tag)) {
            if (tag.getIsRainbow() && colorCache == null || LabyHelp.getInstance().getGroupManager().rainbow) {
                colorCache = LabyHelp.getInstance().getGroupManager().randomColor(false);
                LabyHelp.getInstance().getGroupManager().rainbow = false;
            }
            return tag.getIsRainbow() ? colorCache + tag.getTagDisplayed() : tag.getTagDisplayed();
        }
        return "";
    }


    /**
     * Special Badge to reload
     */
    public void readServerPartner() {
        LabyHelp.getInstance().sendDeveloperMessage("re-loading server tags");
        hasServer.clear();
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://labyhelp.de/tags.php?ex=SERVER")) {
            final String[] data = entry.split(":");
            if (data.length == 2) {
                String uuid = data[0];
                if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    hasServer.put(UUID.fromString(data[0]), data[1]);
                    Tags.SERVER_TAG.getArray().add(UUID.fromString(data[0]));
                }
            }
        }
    }

}


