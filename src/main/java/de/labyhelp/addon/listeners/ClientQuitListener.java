package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ClientQuitListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstance().getSettingsManager().onServer = false;
    }
}
