package de.labyhelp.addon.util.transfer;

import de.labyhelp.addon.LabyHelp;
import net.labymod.main.LabyMod;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

public class LikeManager {

    public final Map<UUID, String> userLikes = new HashMap<>();
    public final Map<UUID, String> oldLikes = new HashMap<>();
    public final ArrayList<UUID> isLiked = new ArrayList<>();


    private static final Map<String, Integer> collectors = new HashMap<>();

    public List<Map.Entry<String, Integer>> getTops5() {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userLikes.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(5).collect(toList());
    }

    public UUID getFamousLikePlayer() {

        HashMap<UUID, Integer> likeList = new HashMap<>();

        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userLikes.entrySet()) {
                int i = Integer.parseInt(userLikes.get(uuidStringEntry.getKey()));
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

    public String getLikes(UUID uuid) {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userLikes.entrySet()) {
                if (likes.getKey().equals(uuid)) {
                    return likes.getValue();
                }
            }
        } else {
            return "0";
        }
        return "0";
    }

    public String getBeforeLikes() {
        if (!oldLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : oldLikes.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return oldLikes.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public String getNowLikes() {
        if (!userLikes.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userLikes.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return userLikes.get(likes.getKey());
                }
            }
        }
        return null;
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
        }
    }

    public void sendLike(final UUID uuid, UUID likeUuid) {
         LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendLike.php?uuid=" + uuid.toString() + "&likeUuid=" + likeUuid);
    }

    public void readUserLikes() {
        LabyHelp.getInstance().getRequestManager().getStandardHashMap("https://marvhuelsmann.de/userLikes.php", (HashMap<UUID, String>) userLikes);
    }



}
