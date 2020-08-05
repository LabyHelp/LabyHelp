package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import de.marvhuelsmann.labymodaddon.menu.Menu;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.util.Commands;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import de.marvhuelsmann.labymodaddon.util.WebServer;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.api.events.UserMenuActionEvent;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.user.User;
import net.labymod.user.util.UserActionEntry;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Map;
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
    public String statusName;

    public static boolean AddonRankShow = false;
    public static Boolean isNewerVersion = false;
    public static final String currentVersion = "1.9";

    public static boolean onServer = false;

    public static boolean bandanaMenu = true;
    public static boolean capeMenu = true;
    public static boolean skinMenu = true;

    public static boolean cosmeticMenu = true;

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final Map<UUID, HelpGroups> groupsMap;
    public static LabyHelpAddon instace;

    static {
        groupsMap = WebServer.readGroups();
    }


    @Override
    public void onEnable() {
        instace = this;
        this.getApi().registerForgeListener(false);
        this.getApi().registerForgeListener(this);

        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        String webVersion = WebServer.readVersion();
        if (!webVersion.equalsIgnoreCase(currentVersion)) {
            isNewerVersion = true;
        }


        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            this.getApi().getEventManager().register(new UserMenuActionEvent() {
                @Override
                public void createActions(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo, List<UserActionEntry> list) {
                    Menu.refreshMenu();
                }
            });
        }

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

                Menu.refreshMenu();
                WebServer.sendClient(LabyMod.getInstance().getPlayerUUID());

                    GroupManager.updateSubTitles();
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
                if (message.startsWith("/bandana") || message.startsWith("/cape") || message.startsWith("/skin") || message.startsWith("/cosmeticsC") || message.equalsIgnoreCase("/LhHelp") || message.equalsIgnoreCase("/lhreload") || message.startsWith("/insta") || message.startsWith("/discord") || message.startsWith("/youtube") || message.startsWith("/twitch") || message.startsWith("/twitter") || message.startsWith("/tiktok") ||  message.startsWith("/social")) {
                    Commands.CommandMessage(message);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void loadConfig() {
        AddonEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        this.AddonHelpMessage = !this.getConfig().has("join") || this.getConfig().get("join").getAsBoolean();

        AddonRankShow = !this.getConfig().has("rankshow") || this.getConfig().get("rankshow").getAsBoolean();

        skinMenu = !this.getConfig().has("skin") || this.getConfig().get("skin").getAsBoolean();
        cosmeticMenu = !this.getConfig().has("cosmetics") || this.getConfig().get("cosmetics").getAsBoolean();
        capeMenu = !this.getConfig().has("cape") || this.getConfig().get("cape").getAsBoolean();
        bandanaMenu = !this.getConfig().has("bandana") || this.getConfig().get("bandana").getAsBoolean();

        this.instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        this.discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        this.youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        this.twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        this.twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        this.tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
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
        if (tick > 400) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {

                    GroupManager.updateSubTitles();

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

        for (final Map.Entry<UUID, HelpGroups> groupsEntry : groupsMap.entrySet()) {
            if (groupsEntry.getValue() != null && groupsEntry.getKey() != null) {
                if (groupsEntry.getValue().getShowTag() && groupsEntry.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
                    final BooleanElement settingsToggleGetting = new BooleanElement("Nick your Subtitle Tag", new ControlElement.IconData(Material.INK_SACK), new Consumer<Boolean>() {
                        @Override
                        public void accept(final Boolean enable) {
                            AddonRankShow = enable;

                            WebServer.sendNick(LabyMod.getInstance().getPlayerUUID(), enable);

                            if (LabyHelpAddon.onServer) {
                                if (!enable) {
                                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Bitte warte ein paar Sekunden um sicher zu gehen, dass dein Subtitle fuer alle Spieler entnickt wurde!");
                                } else {
                                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + EnumChatFormatting.RED + " Bitte warte ein paar Sekunden um sicher zu gehen, dass dein Subtitle fuer alle Spieler genickt wurde!");
                                }
                            }
                            LabyHelpAddon.this.getConfig().addProperty("rankshow", enable);
                            LabyHelpAddon.this.saveConfig();
                        }
                    }, AddonRankShow);
                    list.add(settingsToggleGetting);
                }
            }
        }


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
        for (final Map.Entry<UUID, HelpGroups> groupsEntry : groupsMap.entrySet()) {
            if (groupsEntry.getValue() != null && groupsEntry.getKey() != null) {
                if (groupsEntry.getValue().getPremium() && groupsEntry.getKey().equals(LabyMod.getInstance().getPlayerUUID())) {
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
                }
            }
        }

        ////

        final BooleanElement settingsSkin = new BooleanElement("show Skin in the Menu", new ControlElement.IconData(Material.MOB_SPAWNER), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                skinMenu = enable;

                Menu.refreshMenu();

                LabyHelpAddon.this.getConfig().addProperty("skin", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, skinMenu);

        final BooleanElement settingsCape = new BooleanElement("show Cape in the Menu", new ControlElement.IconData(Material.BANNER), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                capeMenu = enable;

                Menu.refreshMenu();

                LabyHelpAddon.this.getConfig().addProperty("cape", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, capeMenu);

        final BooleanElement settingsBandana = new BooleanElement("show Bandana in the Menu", new ControlElement.IconData(Material.WOOL), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                bandanaMenu = enable;

                Menu.refreshMenu();

                LabyHelpAddon.this.getConfig().addProperty("bandana", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, bandanaMenu);

        final BooleanElement settingsCosmetics = new BooleanElement("show clear Cosmetics in the menu", new ControlElement.IconData(Material.BARRIER), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                cosmeticMenu = enable;

                Menu.refreshMenu();

                LabyHelpAddon.this.getConfig().addProperty("cosmetics", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, cosmeticMenu);


        list.add(settingsCape);
        list.add(settingsBandana);
        list.add(settingsSkin);
        list.add(settingsCosmetics);

        list.add(channelStringElement);
        list.add(stringDiscord);
        list.add(stringTwitter);
        list.add(channelTikTok);
        list.add(stringYoutube);
        list.add(stringTwitch);
    }
}
