package de.labyhelp.addon.util;

import com.google.gson.JsonObject;
import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import net.labymod.main.LabyMod;

import java.util.Map;
import java.util.UUID;

public class TargetManager {

    public void saveTargetPlayers() {
        final JsonObject object = new JsonObject();
        for (final Map.Entry<UUID, Integer> entry : LabyHelp.getInstance().getSettingsManager().targetPlayers.entrySet()) {
            object.addProperty(entry.getKey().toString(), entry.getValue());
        }
        LabyHelp.getInstance().getConfig().add("targetPlayers", object);
        LabyHelp.getInstance().saveConfig();
    }

    public void setTargetNameTag(UUID uuid) {
        if (LabyHelp.getInstance().getSettingsManager().getTargetPlayers().containsKey(uuid)) {
            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(HelpGroups.TARGET.getSubtitle());
            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitleSize(LabyHelp.getInstance().getSettingsManager().getTargetPlayers().get(uuid));
        } else {
            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(null);
        }
    }
}
