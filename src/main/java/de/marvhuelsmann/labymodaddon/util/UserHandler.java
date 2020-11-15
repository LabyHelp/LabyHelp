package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class UserHandler {


    public final Map<UUID, Boolean> voiceMuteExtendedList = new HashMap<>();
    public final Map<UUID, HelpGroups> userGroups = new HashMap<UUID, HelpGroups>();
    public final Map<UUID, HelpGroups> oldGroups = new HashMap<UUID, HelpGroups>();
    public final Map<UUID, String> userNameTags = new HashMap<UUID, String>();

    public final Map<UUID, String> instaName = new HashMap<UUID, String>();
    public final Map<UUID, String> discordName = new HashMap<UUID, String>();
    public final Map<UUID, String> youtubeName = new HashMap<UUID, String>();
    public final Map<UUID, String> twitchName = new HashMap<UUID, String>();
    public final Map<UUID, String> twitterName = new HashMap<UUID, String>();
    public final Map<UUID, String> tiktokName = new HashMap<UUID, String>();
    public final Map<UUID, String> snapchatName = new HashMap<UUID, String>();
    public final Map<UUID, String> isOnline = new HashMap<UUID, String>();

    public final Map<UUID, String> userLikes = new HashMap<UUID, String>();
    public final Map<UUID, String> oldLikes = new HashMap<UUID, String>();

    public final ArrayList<UUID> isLiked = new ArrayList<>();

    //instagram 1 |
    //discord 2
    //youtube 3
    //twitch 4
    //twitter 5
    //tiktok 6
    //snapchat 7


    public void targetMode(boolean activated) {
        LabyHelp.getInstace().targetMode = activated;
    }


    private static final Map<String, Integer> collectors = new HashMap<>();

    public List<Map.Entry<String, Integer>> getTops5() {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userLikes.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(5).collect(toList());
    }

    public List<Map.Entry<String, Integer>> getTops3() {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userLikes.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(3).collect(toList());
    }

    public UUID getFamousLikePlayer() {

        HashMap<UUID, Integer> likeList = new HashMap<UUID, Integer>();

        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : LabyHelp.getInstace().getUserHandler().userLikes.entrySet()) {
                int i = Integer.parseInt(LabyHelp.getInstace().getUserHandler().userLikes.get(uuidStringEntry.getKey()));
                likeList.put(uuidStringEntry.getKey(), i);
            }

            int maxValueInMap = (Collections.max(likeList.values()));
            for (Map.Entry<UUID, Integer> entry : likeList.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public Integer getFamousLikesAmount() {
        HashMap<UUID, Integer> likeList = new HashMap<UUID, Integer>();

        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : LabyHelp.getInstace().getUserHandler().userLikes.entrySet()) {
                int i = Integer.parseInt(LabyHelp.getInstace().getUserHandler().userLikes.get(uuidStringEntry.getKey()));
                likeList.put(uuidStringEntry.getKey(), i);
            }

            int maxValueInMap = (Collections.max(likeList.values()));
            for (Map.Entry<UUID, Integer> entry : likeList.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public String getLikes(UUID uuid) {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userLikes.entrySet()) {
                if (likes.getKey().equals(uuid)) {
                    return likes.getValue();
                }
            }
        }
        return null;
    }

    public String getBeforeLikes() {
        if (!oldLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : oldLikes.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getUserHandler().oldLikes.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public String getNowLikes() {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userLikes.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getUserHandler().userLikes.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public HelpGroups getBeforeRanked() {
        if (!oldGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : oldGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getUserHandler().oldGroups.get(groups.getKey());
                }
            }
        }
        return null;
    }

    public HelpGroups getNowRanked() {
        if (!userGroups.isEmpty()) {
            for (Map.Entry<UUID, HelpGroups> groups : userGroups.entrySet()) {
                if (groups.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getUserHandler().userGroups.get(groups.getKey());
                }
            }
        }
        return null;
    }


    public void readSocialMedia() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/socialmedia.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 8) {
                    if (!String.valueOf(data[1]).equals("")) {
                        instaName.put(UUID.fromString(data[0]), String.valueOf(data[1]));
                    }
                    if (!String.valueOf(data[2]).equals("")) {
                        String end = String.valueOf(data[2]).replace("@", "#");
                        discordName.put(UUID.fromString(data[0]), end);
                    }
                    if (!String.valueOf(data[3]).equals("")) {
                        youtubeName.put(UUID.fromString(data[0]), String.valueOf(data[3]));
                    }
                    if (!String.valueOf(data[4]).equals("")) {
                        twitchName.put(UUID.fromString(data[0]), String.valueOf(data[4]));
                    }
                    if (!String.valueOf(data[5]).equals("")) {
                        twitterName.put(UUID.fromString(data[0]), String.valueOf(data[5]));
                    }
                    if (!String.valueOf(data[6]).equals("")) {
                        tiktokName.put(UUID.fromString(data[0]), String.valueOf(data[6]));
                    }
                    if (!String.valueOf(data[7]).equals("")) {
                        snapchatName.put(UUID.fromString(data[0]), String.valueOf(data[7]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read socialmedia!", e);
        }
    }

    public void readLikes() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/likes.php?uuid=" + LabyMod.getInstance().getPlayerUUID()).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries = result.split(",");

            for (String liked : entries) {
                if (liked.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    UUID uuid = UUID.fromString(liked);
                    isLiked.add(uuid);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read socialmedia!", e);
        }
    }

    public String sendLike(final UUID uuid, UUID likeUuid) {
        try {
            if (uuid != null) {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendLikes.php?uuid=" + uuid.toString() + "&likeUuid=" + likeUuid).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch sendLikes!", e);
        }
    }


    public void readIsOnline() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/online.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            if (result != null) {
                final String[] split;
                final String[] entries = split = result.split(",");
                for (final String entry : split) {
                    final String[] data = entry.split(":");
                    if (data.length == 2) {
                        String uuid = data[0];
                        if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                            isOnline.put(UUID.fromString(data[0]), data[1]);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch online state!", e);
        }
    }

    public String sendOnline(final UUID uuid, boolean isOnline) {
        try {
            if (uuid != null) {
                if (isOnline) {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendOnline.php?uuid=" + uuid.toString() + "&isOnline=ONLINE").openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                } else {
                    final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendOnline.php?uuid=" + uuid.toString() + "&isOnline=OFFLINE").openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch onlinemode!", e);
        }
    }

    public void readGroups() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/database.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            if (result != null) {
                final String[] split;
                final String[] entries = split = result.split(",");
                for (final String entry : split) {
                    final String[] data = entry.split(":");
                    if (data.length == 2 && HelpGroups.isExist(data[1])) {
                        String uuid = data[0];
                        if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {

                            userGroups.put(UUID.fromString(data[0]), HelpGroups.valueOf(data[1]));


                            if (!LabyHelp.getInstace().getGroupManager().isTeam(UUID.fromString(uuid))) {
                               if (Integer.parseInt(LabyHelp.getInstace().getInviteManager().getInvites(UUID.fromString(uuid))) >= 25) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.PREMIUM_);
                                } else if (Integer.parseInt(LabyHelp.getInstace().getInviteManager().getInvites(UUID.fromString(uuid))) >= 10) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.INVITER);
                                }

                                if (LabyHelp.getInstace().getUserHandler().getFamousLikePlayer().toString().equals(uuid)) {
                                    userGroups.put(UUID.fromString(data[0]), HelpGroups.FAMOUS);
                                } else {
                                    List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getUserHandler().getTops5();
                                    for (Map.Entry<String, Integer> uuidStringEntry : list) {
                                        if (uuidStringEntry.getKey().equalsIgnoreCase(uuid)) {
                                            userGroups.put(UUID.fromString(data[0]), HelpGroups.FAME);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch groups!", e);
        }
    }


    public void readUserLikes() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/userLikes.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    String uuid = data[0];
                    if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                        userLikes.put(UUID.fromString(data[0]), data[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read NameTag!", e);
        }
    }


    public void readNameTag() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/nametag.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    userNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read NameTag!", e);
        }
    }

    public String getIp() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/ip.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read ip!", e);
        }
    }

    public void readUserInformations(boolean groups) {
        if (groups) {

            for (Map.Entry<UUID, HelpGroups> group : userGroups.entrySet()) {
                oldGroups.put(group.getKey(), group.getValue());
            }

            for (Map.Entry<UUID, String> likes : userLikes.entrySet()) {
                oldLikes.put(likes.getKey(), likes.getValue());
            }

            for (Map.Entry<UUID, String> likes : LabyHelp.getInstace().getInviteManager().userInvites.entrySet()) {
                LabyHelp.getInstace().getInviteManager().oldInvites.put(likes.getKey(), likes.getValue());
            }


            readUserLikes();
            readLikes();
            LabyHelp.getInstace().getInviteManager().readUserInvites();
            LabyHelp.getInstace().getInviteManager().readOldPlayer();
            readGroups();

            if (!getNowRanked().getName().equalsIgnoreCase(getBeforeRanked().getName())) {
                if (getNowRanked().equals(HelpGroups.BANNED)) {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.WHITE + " ---------LabyHelp Guardian----------");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Your NameTag has been banned for one day");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.YELLOW + " Rules: https://labyhelp.de/tag-rules");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.WHITE + " ---------LabyHelp Guardian----------");

                } else {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN + " Your Rank has been change! (" + EnumChatFormatting.WHITE + getNowRanked().getName() + EnumChatFormatting.GREEN + ")");
                }
            }

            if (!getBeforeLikes().equalsIgnoreCase(getNowLikes())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN + " You got a LabyHelp Profile like (" + EnumChatFormatting.WHITE + getNowLikes() + EnumChatFormatting.GREEN + ")");
            }

            if (!LabyHelp.getInstace().getInviteManager().getBeforeInvites().equalsIgnoreCase(LabyHelp.getInstace().getInviteManager().getNowInvites())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.GREEN + " A player has redeemed your invite code (" + EnumChatFormatting.WHITE + LabyHelp.getInstace().getInviteManager().getNowInvites() + EnumChatFormatting.GREEN + ")");
            }

        } else {
            readNameTag();
        }
    }
}
