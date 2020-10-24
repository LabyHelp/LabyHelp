package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.cosmetic.cosmetics.partner.CosmeticStegi;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamManager {

    public HashMap<UUID, String> list = new HashMap<>();

    private void readTeam() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/color.php").openConnection();
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
                            list.put(UUID.fromString(data[0]), data[1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch mutes!", e);
        }
    }

    public void update() {
        readTeam();
        try {
            if (!LabyHelp.getInstace().getUserHandler().userGroups.isEmpty()) {
                if (!LabyMod.getInstance().getUserManager().getUsers().isEmpty()) {
                    for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                        if (list.containsKey(uuidUserEntry.getKey())) {
                            if (!LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).getCosmetics().containsKey(17)) {

                                LabyMod.getInstance().getUserManager().setChecked(uuidUserEntry.getKey(), true);

                                CosmeticStegi.CosmeticStegiData data = new CosmeticStegi.CosmeticStegiData();
                                data.loadData(new String[]{list.get(uuidUserEntry.getKey())});

                                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).getCosmetics().put(17, data);

                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
