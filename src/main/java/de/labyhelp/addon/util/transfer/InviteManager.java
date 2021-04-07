package de.labyhelp.addon.util.transfer;

import de.labyhelp.addon.LabyHelp;
import net.labymod.main.LabyMod;

import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

public class InviteManager {

    public ArrayList<UUID> oldPlayer = new ArrayList<>();

    public final Map<UUID, String> userInvites = new HashMap<>();
    public final Map<UUID, String> oldInvites = new HashMap<>();

    private final Map<String, Integer> collectors = new HashMap<>();

    public List<Map.Entry<String, Integer>> getTops5() {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> uuidStringEntry : userInvites.entrySet()) {
                collectors.put(uuidStringEntry.getKey().toString(), Integer.parseInt(uuidStringEntry.getValue()));
            }
        }
        return collectors.entrySet().stream().sorted(Map.Entry.comparingByValue(reverseOrder())).limit(5).collect(toList());
    }

    public String getInvites(UUID uuid) {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userInvites.entrySet()) {
                if (likes.getKey().equals(uuid)) {
                    return likes.getValue();
                }
            }
        } else {
            return "0";
        }
        return "0";
    }

    public String getBeforeInvites() {
        if (!oldInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : oldInvites.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstance().getInviteManager().oldInvites.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public String getNowInvites() {
        if (!userInvites.isEmpty()) {
            for (Map.Entry<UUID, String> likes : userInvites.entrySet()) {
                if (likes.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    return LabyHelp.getInstance().getInviteManager().userInvites.get(likes.getKey());
                }
            }
        }
        return null;
    }

    public void readUserInvites() {
        LabyHelp.getInstance().getRequestManager().getStandardHashMap("https://marvhuelsmann.de/userInvites.php", (HashMap<UUID, String>) userInvites);
    }

    public void sendInvite(final UUID uuid, UUID likeUuid) {
      LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendInvite.php?uuid=" + uuid.toString() + "&inviteUuid=" + likeUuid);
    }

    public void readOldPlayer() {
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://marvhuelsmann.de/oldPlayer.php")) {
            final String[] data = entry.split(":");
            if (data.length == 2) {
                if (data[1].equalsIgnoreCase("TRUE")) {
                    oldPlayer.add(UUID.fromString(data[0]));
                }
            }
        }
    }

    public boolean isOldPlayer(UUID uuid) {
        readOldPlayer();
        return oldPlayer.contains(uuid);
    }
}
