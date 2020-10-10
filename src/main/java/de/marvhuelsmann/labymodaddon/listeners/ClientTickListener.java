package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.voicechat.VoiceChatHandler;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickListener {

    private int reloadTick = 0;
    private int nameTick = 0;
    private int normalTick = 0;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {

        if (normalTick > 1240) {
            try {
                LabyHelp.getInstace().addonEnabled = true;
            } catch (Exception ignored) {
                LabyHelp.getInstace().addonEnabled = false;
            }

            normalTick = 0;
        }

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
        if (LabyHelp.getInstace().nameTagString != null) {
            if (nameTick > 200) {
                if (LabyHelp.getInstace().onServer) {
                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().getGroupManager().updateNameTag(false);
                        }
                    });
                    if (nameTick > 400) {
                        nameTick = 0;
                    }
                }
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
    }
}
