package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.util.Commands;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LabyHelp extends net.labymod.api.LabyModAddon {


    public static boolean AddonEnable = true;
    private boolean AddonHelpMessage = true;
    public String instaName;
    public String discordName;
    public String youtubeName;
    public String twitchName;
    public String twitterName;
    public String tiktokName;
    public String snapchatName;
    public String statusName;
    public String nameTagString;

    public static boolean AddonRankShow = false;
    public static Boolean isNewerVersion = false;
    public static final String currentVersion = "1.9.2";

    public static boolean onServer = false;

    private ExecutorService executor;

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static LabyHelp instace;

    private boolean addonEnabled = false;


    @Override
    public void onEnable() {
        instace = this;
        this.getApi().registerForgeListener(false);
        this.getApi().registerForgeListener(this);

        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        try {
            String webVersion = WebServer.readVersion();
            if (!webVersion.equalsIgnoreCase(currentVersion)) {
                isNewerVersion = true;
            }
            addonEnabled = true;

        } catch (Exception ignored) {
            addonEnabled = false;
        }

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().clear();

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new BandanaMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CosmeticsClearerMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SocialMediaMenu());


        this.getApi().getEventManager().registerOnJoin(serverData -> {
            if (AddonHelpMessage) {
                LabyMod.getInstance().notifyMessageRaw("LabyHelp | Addon", "Use /LhHelp for all Commands");
            }

            onServer = true;

            if (isNewerVersion) {
                LabyMod.getInstance().notifyMessageRaw("LabyHelp | Addon", "Es gibt eine neuere LabyHelp Version!");
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + EnumChatFormatting.BOLD + " Es gibt eine neuere LabyHelp Version. Dein Browser wurde geoeffnet!");
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Nichts geoeffnet? https://labyhelp.de");
                LabyMod.getInstance().openWebpage("https://labyhelp.de", true);
            }

            if (addonEnabled) {
                WebServer.sendClient(LabyMod.getInstance().getPlayerUUID());
                GroupManager.updateSubTitles(true);
                GroupManager.updateSubTitles(false);
            } else {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + " LabyHelp doenst load correctly...");
            }
        });

        this.getApi().getEventManager().registerOnQuit(serverData -> onServer = false);

        this.getApi().getEventManager().register((MessageSendEvent) message -> {
            if (message.startsWith("/bandana") || message.startsWith("/cape") || message.startsWith("/skin") || message.startsWith("/cosmeticsC") || message.equalsIgnoreCase("/LhHelp") || message.equalsIgnoreCase("/lhreload") || message.startsWith("/insta") || message.startsWith("/discord") || message.startsWith("/youtube") || message.startsWith("/twitch") || message.startsWith("/twitter") || message.startsWith("/tiktok") || message.startsWith("/social") || message.startsWith("/snapchat") || message.startsWith("/lhban") || message.startsWith("nametag")) {
                Commands.CommandMessage(message);
                return true;
            } else {
                return false;
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isNewerVersion) {
                System.out.println("UPDATE IS INSTALLATION");
                System.out.println("UPDATE IS INSTALLATION");
                System.out.println("UPDATE IS INSTALLATION");
                System.out.println("UPDATE IS INSTALLATION");
                //   FileDownloader.update();
            }
        }));
    }

    @Override
    public void loadConfig() {
        AddonEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        this.AddonHelpMessage = !this.getConfig().has("join") || this.getConfig().get("join").getAsBoolean();

        AddonRankShow = !this.getConfig().has("rankshow") || this.getConfig().get("rankshow").getAsBoolean();

        this.instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        this.discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        this.youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        this.twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        this.twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        this.tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
        this.snapchatName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        this.nameTagString = this.getConfig().has("nametag") ? this.getConfig().get("nametag").getAsString() : "nametag";
        this.statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";
    }

    public static LabyHelp getInstace() {
        return instace;
    }

    public UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    public boolean getUUIDBoolean(String name) {

        UUID uuid = MinecraftServer.getServer().getPlayerProfileCache().getProfileByUUID(UUID.fromString(name)).getId();

        return uuid != null;
    }

    public ExecutorService getExecutor() {
        return this.executor;
    }

    int tick = 0;
    int reloadTick = 0;
    int nameTick = 0;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (reloadTick > 930) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        GroupManager.updateSubTitles(true);
                        GroupManager.updateNameTag(true);
                        addonEnabled = true;
                    } catch (Exception ignored) {
                        addonEnabled = false;
                    }

                    System.out.println("update subtitle & nametags");
                }
            });

            reloadTick = 0;
        }
        if (nameTagString != null) {
            if (nameTick > 200) {
                if (onServer) {
                    threadPool.submit(new Runnable() {
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
                if (onServer) {
                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            GroupManager.updateSubTitles(false);
                        }
                    });
                }
            }
        } else {
            if (onServer) {
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        GroupManager.updateSubTitles(false);
                    }
                });
            }
        }
        nameTick++;
        tick++;
        reloadTick++;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {


        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                AddonEnable = enable;


                LabyHelp.this.getConfig().addProperty("enable", enable);
                LabyHelp.this.saveConfig();
            }
        }, AddonEnable);

        list.add(settingsEnabled);


        final BooleanElement settingsJoin = new BooleanElement("Join help message", new ControlElement.IconData(Material.REDSTONE_COMPARATOR), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.this.AddonHelpMessage = enable;

                LabyHelp.this.getConfig().addProperty("join", enable);
                LabyHelp.this.saveConfig();
            }
        }, this.AddonHelpMessage);

        list.add(settingsJoin);

        StringElement channelStringElement = new StringElement("Instagram username", new ControlElement.IconData(Material.PAPER), instaName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendInstagram(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.instaName = accepted;

                LabyHelp.this.getConfig().addProperty("instaname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement channelTikTok = new StringElement("TikTok username", new ControlElement.IconData(Material.PAPER), tiktokName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendTikTok(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.tiktokName = accepted;

                LabyHelp.this.getConfig().addProperty("tiktokname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitch = new StringElement("Twitch username", new ControlElement.IconData(Material.PAPER), twitchName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendTwitch(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.twitchName = accepted;

                LabyHelp.this.getConfig().addProperty("twitchname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringDiscord = new StringElement("Discord username", new ControlElement.IconData(Material.PAPER), discordName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendDiscord(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.discordName = accepted;

                LabyHelp.this.getConfig().addProperty("discordname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringYoutube = new StringElement("Youtube username", new ControlElement.IconData(Material.PAPER), youtubeName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendYoutube(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.youtubeName = accepted;

                LabyHelp.this.getConfig().addProperty("youtubename", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitter = new StringElement("Twitter username", new ControlElement.IconData(Material.PAPER), twitterName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendTwitter(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.twitterName = accepted;

                LabyHelp.this.getConfig().addProperty("twittername", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement snapchat = new StringElement("Snapchat username", new ControlElement.IconData(Material.PAPER), snapchatName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendSnapchat(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.snapchatName = accepted;

                LabyHelp.this.getConfig().addProperty("snapchatname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement status = new StringElement("Status", new ControlElement.IconData(Material.PAPER), statusName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendStatus(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.statusName = accepted;

                LabyHelp.this.getConfig().addProperty("status", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        list.add(status);

        StringElement nameTag = new StringElement("NameTag", new ControlElement.IconData(Material.PAPER), nameTagString, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    WebServer.sendNameTag(LabyMod.getInstance().getPlayerUUID(), accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.statusName = accepted;

                LabyHelp.this.getConfig().addProperty("nametag", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        list.add(nameTag);

        ////

        list.add(channelStringElement);
        list.add(stringDiscord);
        list.add(stringTwitter);
        list.add(snapchat);
        list.add(channelTikTok);
        list.add(stringYoutube);
        list.add(stringTwitch);

    }
}
