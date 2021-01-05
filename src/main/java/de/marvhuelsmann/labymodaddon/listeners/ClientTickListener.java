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
    private int rainbowTick = 0;

    private int adverstingTick = 0;
    private boolean adverdStage = false;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {


        /* ADVERSTING */
        if (LabyHelp.getInstace().getSettingsManger().settingsAdversting) {
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
                LabyHelp.getInstace().getSettingsManger().addonEnabled = true;
            } catch (Exception ignored) {
                LabyHelp.getInstace().getSettingsManger().addonEnabled = false;
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
                        LabyHelp.getInstace().getSettingsManger().addonEnabled = true;
                    } catch (Exception ignored) {
                        LabyHelp.getInstace().getSettingsManger().addonEnabled = false;
                    }

                    System.out.println("update subtitle & nametags");
                }
            });
            reloadTick = 0;
        }

        /* REFRESHING NAMETAGS */
        if (LabyHelp.getInstace().getSettingsManger().nameTagSettings.equalsIgnoreCase("SWITCHING")) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    if (nameTick > LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting * 20) {
                        if (LabyHelp.getInstace().getSettingsManger().onServer) {
                            if (nameTick < LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting * 40) {
                                LabyHelp.getInstace().getGroupManager().updateNameTag(false);

                            }
                            if (nameTick > LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting * 40) {
                                nameTick = 0;
                            }
                        }
                    } else {
                        if (LabyHelp.getInstace().getSettingsManger().onServer) {
                            if (nameTick < LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting * 40) {
                                LabyHelp.getInstace().getGroupManager().updateSubTitles(false);
                            }
                        }
                    }
                }
            });
        } else if (LabyHelp.getInstace().getSettingsManger().nameTagSettings.equalsIgnoreCase("NAMETAG")) {
            LabyHelp.getInstace().getGroupManager().updateNameTag(false);
        } else {
            if (LabyHelp.getInstace().getSettingsManger().onServer) {
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
        rainbowTick++;

        if (rainbowTick > LabyHelp.getInstace().getSettingsManger().nameTagRainbwSwitching * 6) {
            LabyHelp.getInstace().getGroupManager().rainbow = true;
            rainbowTick = 0;
        }

        if (LabyHelp.getInstace().getSettingsManger().settingsAdversting) {
            adverstingTick++;
        }
    }
}
