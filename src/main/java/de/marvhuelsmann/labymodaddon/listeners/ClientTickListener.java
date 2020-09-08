package de.marvhuelsmann.labymodaddon.listeners;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTickListener {

    private int reloadTick = 0;
    private int nameTick = 0;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (reloadTick > 930) {
            LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        GroupManager.updateSubTitles(true);
                        GroupManager.updateNameTag(true);
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
                            GroupManager.updateNameTag(false);
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
                            GroupManager.updateSubTitles(false);
                        }
                    });
                }
            }
        } else {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        GroupManager.updateSubTitles(false);
                    }
                });
            }
        }
        nameTick++;
        reloadTick++;
    }
}
