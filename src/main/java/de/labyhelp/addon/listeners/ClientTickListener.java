package de.labyhelp.addon.listeners;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import net.labymod.main.LabyMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickListener {

    private int reloadTick = 0;
    private int nameTick = 0;
    private int rainbowTick = 0;

    private int adverstingTick = 0;
    private boolean adverdStage = false;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {


        /* ADVERSTING */
        if (LabyHelp.getInstance().getSettingsManager().settingsAdversting) {
            if (!adverdStage) {
                if (adverstingTick > 1500) {
                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                    labyPlayer.sendAdversting(true);
                    adverstingTick = 0;
                    adverdStage = true;
                }
            } else {
                if (adverstingTick > 23900) {
                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                    labyPlayer.sendAdversting(false);
                    adverstingTick = 0;
                }
            }
        }

        /* UPDATING DATA */
        if (reloadTick > 870) {
            LabyHelp.getInstance().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(true);
                        LabyHelp.getInstance().getSettingsManager().addonEnabled = true;
                    } catch (Exception ignored) {
                        LabyHelp.getInstance().getSettingsManager().addonEnabled = false;
                    }

                    LabyHelp.getInstance().sendDeveloperMessage("reloading via tick");
                }
            });
            reloadTick = 0;
        }

        /* REFRESHING NAMETAGS */
        if (LabyHelp.getInstance().getNameTagManager().updateNameTags(nameTick)) {
            nameTick = 0;
        }

        nameTick++;
        reloadTick++;
        rainbowTick++;

        if (rainbowTick > LabyHelp.getInstance().getSettingsManager().nameTagRainbwSwitching * 6) {
            LabyHelp.getInstance().getGroupManager().rainbow = true;
            rainbowTick = 0;
        }

        if (LabyHelp.getInstance().getSettingsManager().settingsAdversting) {
            adverstingTick++;
        }
    }
}
