package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import net.labymod.main.LabyMod;
import net.labymod.user.cosmetic.cosmetics.shop.head.CosmeticCowHat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickListener {

    private int reloadTick = 0;
    private int nameTick = 0;
    private int normalTick = 0;

    private int adverstingTick = 0;
    private boolean adverdStage = false;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {


        /* ADVERSTING */
        if (LabyHelp.getInstace().settingsAdversting) {
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


        /* CHECKING */
        if (normalTick > 1240) {
            try {
                LabyHelp.getInstace().addonEnabled = true;
            } catch (Exception ignored) {
                LabyHelp.getInstace().addonEnabled = false;
            }

            normalTick = 0;
        }


        /* UPDATING DATA */
        if (reloadTick > 870) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                        LabyHelp.getInstace().getGroupManager().updateNameTag(true);
                        LabyHelp.getInstace().addonEnabled = true;
                    } catch (Exception ignored) {
                        LabyHelp.getInstace().addonEnabled = false;
                    }

                    System.out.println("update subtitle & nametags");
                }
            });
            reloadTick = 0;
        }

        /* REFRESHING NAMETAGS */
        if (LabyHelp.getInstace().nameTagSettings.equalsIgnoreCase("SWITCHING")) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    if (nameTick > LabyHelp.getInstace().nameTagSwitchingSetting * 20) {
                        if (LabyHelp.getInstace().onServer) {
                            if (nameTick < LabyHelp.getInstace().nameTagSwitchingSetting * 40) {
                                LabyHelp.getInstace().getGroupManager().updateNameTag(false);
                            }
                            if (nameTick > LabyHelp.getInstace().nameTagSwitchingSetting * 40) {
                                nameTick = 0;
                            }
                        }
                    } else {
                        if (LabyHelp.getInstace().onServer) {
                            if (nameTick < LabyHelp.getInstace().nameTagSwitchingSetting * 40) {
                                LabyHelp.getInstace().getGroupManager().updateSubTitles(false);
                            }
                        }
                    }
                }
            });
        } else if (LabyHelp.getInstace().nameTagSettings.equalsIgnoreCase("NAMETAG")) {
            LabyHelp.getInstace().getGroupManager().updateNameTag(false);
        } else {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        LabyHelp.getInstace().getGroupManager().updateSubTitles(false);
                    }
                });
            }
        }
        normalTick++;
        nameTick++;
        reloadTick++;

        if (LabyHelp.getInstace().settingsAdversting) {
            adverstingTick++;
        }
    }
}
