package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.util.Commands;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.ingamechat.tools.playermenu.PlayerMenu;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LabyHelpAddon extends net.labymod.api.LabyModAddon {


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

    public static boolean AddonRankShow = false;
    public static Boolean isNewerVersion = false;
    public static final String currentVersion = "1.9.1";

    public static boolean onServer = false;

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static LabyHelpAddon instace;

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

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add((PlayerMenu.PlayerMenuEntry) new BandanaMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add((PlayerMenu.PlayerMenuEntry) new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add((PlayerMenu.PlayerMenuEntry) new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add((PlayerMenu.PlayerMenuEntry) new CosmeticsClearerMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add((PlayerMenu.PlayerMenuEntry) new SocialMediaMenu());

        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
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
                    GroupManager.updateSubTitles();
                } else {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "LabyHelp dont load correctly...");
                }
            }
        });

        this.getApi().getEventManager().registerOnQuit(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                onServer = false;
            }
        });

        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String message) {
                if (message.startsWith("/bandana") || message.startsWith("/cape") || message.startsWith("/skin") || message.startsWith("/cosmeticsC") || message.equalsIgnoreCase("/LhHelp") || message.equalsIgnoreCase("/lhreload") || message.startsWith("/insta") || message.startsWith("/discord") || message.startsWith("/youtube") || message.startsWith("/twitch") || message.startsWith("/twitter") || message.startsWith("/tiktok") || message.startsWith("/social") || message.startsWith("/snapchat") || message.startsWith("/lhban")) {
                    Commands.CommandMessage(message);
                    return true;
                } else {
                    return false;
                }
            }
        });
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
        this.tiktokName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        this.statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";
    }

    public static LabyHelpAddon getInstace() {
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

    int tick = 0;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (tick > 525) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        GroupManager.updateSubTitles();
                        addonEnabled = true;
                    } catch (Exception ignored) {
                        addonEnabled = false;
                    }

                    System.out.println("update subtitles");
                    tick = 0;
                }
            });
        }
        tick++;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {


        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                AddonEnable = enable;


                LabyHelpAddon.this.getConfig().addProperty("enable", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, AddonEnable);

        list.add(settingsEnabled);


        final BooleanElement settingsJoin = new BooleanElement("Join help message", new ControlElement.IconData(Material.REDSTONE_COMPARATOR), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelpAddon.this.AddonHelpMessage = enable;

                LabyHelpAddon.this.getConfig().addProperty("join", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, this.AddonHelpMessage);

        list.add(settingsJoin);

        StringElement channelStringElement = new StringElement("Instagram username", new ControlElement.IconData(Material.PAPER), instaName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendInstagram(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.instaName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("instaname", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement channelTikTok = new StringElement("TikTok username", new ControlElement.IconData(Material.PAPER), tiktokName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendTikTok(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.tiktokName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("tiktokname", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement stringTwitch = new StringElement("Twitch username", new ControlElement.IconData(Material.PAPER), twitchName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendTwitch(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.twitchName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("twitchname", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement stringDiscord = new StringElement("Discord username", new ControlElement.IconData(Material.PAPER), discordName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendDiscord(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.discordName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("discordname", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement stringYoutube = new StringElement("Youtube username", new ControlElement.IconData(Material.PAPER), youtubeName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendYoutube(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.youtubeName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("youtubename", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement stringTwitter = new StringElement("Twitter username", new ControlElement.IconData(Material.PAPER), twitterName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendTwitter(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.twitterName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("twittername", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement stringSnap = new StringElement("Snapchat username", new ControlElement.IconData(Material.PAPER), snapchatName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendSnapchat(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.snapchatName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("snapchatname", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });

        StringElement status = new StringElement("Status", new ControlElement.IconData(Material.PAPER), statusName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                WebServer.sendStatus(LabyMod.getInstance().getPlayerUUID(), accepted);

                LabyHelpAddon.this.statusName = accepted;

                LabyHelpAddon.this.getConfig().addProperty("status", accepted);
                LabyHelpAddon.this.saveConfig();
            }
        });
        list.add(status);

        ////

        list.add(channelStringElement);
        list.add(stringDiscord);
        list.add(stringTwitter);
        list.add(stringSnap);
        list.add(channelTikTok);
        list.add(stringYoutube);
        list.add(stringTwitch);

    }
}
