package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.main.LabyMod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

public class InviteManager {

    public ArrayList<UUID> oldPlayer = new ArrayList<>();

    public final Map<UUID, String> userInvites = new HashMap<UUID, String>();
    public final Map<UUID, String> oldInvites = new HashMap<UUID, String>();
    public final ArrayList<UUID> isInvited = new ArrayList<>();


    private static final Map<String, Integer> collectors = new HashMap<>();

    public List<Map.Entry<String, Integer>> getTops5() {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userInvites.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(5).collect(toList());
    }

    public List<Map.Entry<String, Integer>> getTops3() {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userInvites.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(3).collect(toList());
    }

    public UUID getFamousInvitePlayer() {

        HashMap<UUID, Integer> likeList = new HashMap<UUID, Integer>();

        if (!userInvites.isEmpty()) {
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

    public Integer getFamousInviteAmount() {
        HashMap<UUID, Integer> inviteList = new HashMap<UUID, Integer>();

        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : LabyHelp.getInstace().getInviteManager().userInvites.entrySet()) {
                int i = Integer.parseInt(LabyHelp.getInstace().getInviteManager().userInvites.get(uuidStringEntry.getKey()));
                inviteList.put(uuidStringEntry.getKey(), i);
            }

            int maxValueInMap = (Collections.max(inviteList.values()));
            for (Map.Entry<UUID, Integer> entry : inviteList.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public String getInvites(UUID uuid) {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userInvites.entrySet()) {
                if (likes.getKey().equals(uuid)) {
                    return likes.getValue();
                }
            }
        }
        return null;
    }

    public String getBeforeInvites() {
        if (!oldInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : oldInvites.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getInviteManager().oldInvites.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public String getNowInvites() {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userInvites.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstace().getInviteManager().userInvites.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public void readUserInvites() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/userInvites.php").openConnection();
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
                        userInvites.put(UUID.fromString(data[0]), data[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read userInvites!", e);
        }
    }

    public String sendInvite(final UUID uuid, UUID likeUuid) {
        try {
            if (uuid != null) {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendInvite.php?uuid=" + uuid.toString() + "&inviteUuid=" + likeUuid).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch sendInvite!", e);
        }
    }

    public void readOldPlayer() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/oldPlayer.php").openConnection();
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
                    if (data[1].equalsIgnoreCase("TRUE")) {
                        oldPlayer.add(UUID.fromString(data[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read oldPlayer!", e);
        }
    }

    public boolean isOldPlayer(UUID uuid) {
        readOldPlayer();
        return oldPlayer.contains(uuid);
    }
}