package de.marvhuelsmann.labymodaddon.voicechat;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAPI;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class VoiceChatHandler {

    public String filename = LabyMod.getMainConfig().getFile().getParent();

    public File file_8 = new File(filename + "/addons-1.8/config/VoiceChat.json");
    public File file_12 = new File(filename + "/addons-1.12/config/VoiceChat.json");


    public void updateVoiceChatMutes() {
        ArrayList<UUID> muted = new ArrayList<>();
        ArrayList<UUID> unmuted = new ArrayList<>();

        for (Map.Entry<UUID, Boolean> uuidBooleanEntry : LabyHelp.getInstace().getUserHandler().voiceMuteExtendedList.entrySet()) {
            if (uuidBooleanEntry.getValue()) {
                    muted.add(uuidBooleanEntry.getKey());
            } else {
                unmuted.add(uuidBooleanEntry.getKey());
            }
        }

        setMuted(muted);
        setUnMuted(unmuted);

        try {
            Class<?> voiceChat = Class.forName("net.labymod.addons.voicechat.VoiceChat");
            Method configLoader = voiceChat.getMethod("loadConfig");

            LabyMod.getInstance().displayMessageInChat("sended loadConfig");

            configLoader.invoke(AddonLoader.getAddonByUUID(UUID.fromString("43152d5b-ca80-4b29-8f48-39fd63e48dee")));
            System.out.println("updating voicechat config");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}


    }

    public void setMuted(ArrayList<UUID> uuids) {
        boolean oldVersion = LabyHelp.getInstace().oldVersion;

        if (!(oldVersion ? file_8 : file_12).exists()) {
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(oldVersion ? file_8 : file_12);
            InputStreamReader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
            JsonElement config = new JsonParser().parse(in);

            in.close();
            fis.close();

            JsonObject mutedPlayers = config.getAsJsonObject().get("config").getAsJsonObject().get("playerVolumes").getAsJsonObject();
            for (UUID uuid : uuids) {
                mutedPlayers.addProperty(uuid.toString(), 0);
            }

            LabyMod.getInstance().displayMessageInChat("save file with + " + uuids);

            FileOutputStream fos = new FileOutputStream(oldVersion ? file_8 : file_12);
            Writer fw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            new GsonBuilder().setPrettyPrinting().create().toJson(config, fw);

            fw.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUnMuted(ArrayList<UUID> uuids) {
        boolean oldVersion = LabyHelp.getInstace().oldVersion;

        if (!(oldVersion ? file_8 : file_12).exists()) {
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(oldVersion ? file_8 : file_12);
            InputStreamReader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
            JsonElement config = new JsonParser().parse(in);

            in.close();
            fis.close();

            JsonObject mutedPlayers = config.getAsJsonObject().get("config").getAsJsonObject().get("playerVolumes").getAsJsonObject();
            for (UUID uuid : uuids) {
                if (mutedPlayers.has(uuid.toString())) {
                    mutedPlayers.addProperty(uuids.toString(), 100);
                }
            }

            LabyMod.getInstance().displayMessageInChat("save file with unmuted: + " + uuids);

            FileOutputStream fos = new FileOutputStream(oldVersion ? file_8 : file_12);
            Writer fw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            new GsonBuilder().setPrettyPrinting().create().toJson(config, fw);

            fw.close();
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
