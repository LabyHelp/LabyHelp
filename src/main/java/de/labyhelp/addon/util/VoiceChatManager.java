package de.labyhelp.addon.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.labyhelp.addon.LabyHelp;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoiceChatManager {

    public void readVoiceChats() {
        File configDirectory = AddonLoader.getConfigDirectory();
        File config = new File(configDirectory, "VoiceChat.json");

        try {
            FileInputStream in = new FileInputStream(config);
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            JsonElement json = new JsonParser().parse(reader);

            reader.close();
            in.close();

            List<UUID> mutedPlayers = new ArrayList<>();

            JsonObject values = json.getAsJsonObject().get("config").getAsJsonObject().get("playerVolumes").getAsJsonObject();
            for (Map.Entry<String, JsonElement> value : values.entrySet()) {
                if (value.getValue().getAsInt() == 0) {
                    mutedPlayers.add(
                            UUID.fromString(value.getKey())
                    );
                }
            }

            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (UUID mutedPlayer : mutedPlayers) {
                if (!first) {
                    sb.append(",");
                } else {
                    first = false;
                }

                sb.append(mutedPlayer.toString());
            }

            LabyMod.getInstance().openWebpage("https://labyhelp.de/mutedPlayers?name="+LabyMod.getInstance().getPlayerName()+"#"+sb.toString(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
