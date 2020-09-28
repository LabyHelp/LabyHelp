package de.marvhuelsmann.labymodaddon;

import com.google.gson.JsonObject;
import de.marvhuelsmann.labymodaddon.listeners.ClientJoinListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientQuitListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientTickListener;
import de.marvhuelsmann.labymodaddon.listeners.MessageSendListener;
import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.util.*;
import de.marvhuelsmann.labymodaddon.voicechat.VoiceChatHandler;
import net.labymod.addon.AddonLoader;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.user.group.LabyGroup;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LabyHelp extends net.labymod.api.LabyModAddon {

    private static LabyHelp instace;

    public boolean AddonSettingsEnable = true;
    public Boolean isNewerVersion = false;
    public static final String currentVersion = "1.9.5.3";
    public String newestVersion;
    public boolean onServer = false;

    private final UserHandler userHandler = new UserHandler();
    private final de.marvhuelsmann.labymodaddon.util.GroupManager groupManager = new de.marvhuelsmann.labymodaddon.util.GroupManager();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final Commands commands = new Commands();
    private final VoiceChatHandler voiceChatHandler = new VoiceChatHandler();

    public String instaName;
    public String discordName;
    public String youtubeName;
    public String twitchName;
    public String twitterName;
    public String tiktokName;
    public String snapchatName;
    public String statusName;
    public String nameTagString;

    public boolean oldVersion = false;

    public boolean addonEnabled = false;


    @Override
    public void onEnable() {
        instace = this;


        this.getApi().registerForgeListener(new ClientTickListener());
        this.getApi().getEventManager().register(new MessageSendListener());
        this.getApi().getEventManager().registerOnJoin(new ClientJoinListener());
        this.getApi().getEventManager().registerOnQuit(new ClientQuitListener());

        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            oldVersion = true;
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        try {
            String webVersion = WebServer.readVersion();
            newestVersion = webVersion;
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


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LabyHelp.getInstace().getUserHandler().sendOnline(LabyMod.getInstance().getPlayerUUID(), false);
            if (isNewerVersion) {
                FileDownloader.update();
            }
        }));
    }

    public static LabyHelp getInstace() {
        return instace;
    }

    public UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public VoiceChatHandler getVoiceChatHandler() {
        return voiceChatHandler;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public ExecutorService getExecutor() {
        return threadPool;
    }

    public Commands getCommands() {
        return commands;
    }


    @Override
    public void loadConfig() {
        AddonSettingsEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();

        this.statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

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

    @Override
    protected void fillSettings(List<SettingsElement> settingsElements) {


        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().AddonSettingsEnable = enable;


                LabyHelp.this.getConfig().addProperty("enable", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().AddonSettingsEnable);

        settingsElements.add(settingsEnabled);


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
        settingsElements.add(nameTag);

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
        settingsElements.add(status);


        settingsElements.add(channelStringElement);
        settingsElements.add(stringDiscord);
        settingsElements.add(stringTwitter);
        settingsElements.add(snapchat);
        settingsElements.add(channelTikTok);
        settingsElements.add(stringYoutube);
        settingsElements.add(stringTwitch);
    }
}
