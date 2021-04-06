package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.network.server.DisconnectServerEvent;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ClientQuitListener {

    @Subscribe
    public void accept(final DisconnectServerEvent event) {
        LabyHelp.getInstance().getSettingsManager().onServer = false;
    }
}
