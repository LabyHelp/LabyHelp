package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;


public class ClientQuitListener implements Consumer<ServerData> {

    @Override
    public void accept(ServerData serverData) {
        LabyHelp.getInstace().onServer = false;
    }
}
