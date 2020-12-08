package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.enums.NameTagSettings;
import de.marvhuelsmann.labymodaddon.listeners.ClientJoinListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientQuitListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientTickListener;
import de.marvhuelsmann.labymodaddon.listeners.MessageSendListener;
import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.store.FileDownloader;
import de.marvhuelsmann.labymodaddon.store.StoreHandler;
import de.marvhuelsmann.labymodaddon.util.*;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LabyHelp extends net.labymod.api.LabyModAddon {

    private static LabyHelp instace;

    public boolean AddonSettingsEnable = true;
    public boolean settingsAdversting = true;
    public boolean settinngsComments = true;
    public Boolean isNewerVersion = false;
    public static final String currentVersion = "2.4.2 Winter EDITION";
    public String newestVersion;
    public boolean onServer = false;

    private final UserHandler userHandler = new UserHandler();
    private final de.marvhuelsmann.labymodaddon.util.GroupManager groupManager = new de.marvhuelsmann.labymodaddon.util.GroupManager();
    private final StoreHandler storeHandler = new StoreHandler();
    private final InviteManager inviteManager = new InviteManager();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final Commands commands = new Commands();
    private final CommentManager commentManager = new CommentManager();

    public String instaName;
    public String discordName;
    public String youtubeName;
    public String twitchName;
    public String twitterName;
    public String tiktokName;
    public String snapchatName;
    public String statusName;
    public String nameTagString;

    public ArrayList<UUID> targetList = new ArrayList<>();
    public boolean targetMode = false;

    public String nameTagSettings;
    public int nameTagSwitchingSetting;
    public int nameTagSize;

    public boolean oldVersion = false;
    public boolean addonEnabled = false;

    public boolean commentChat = false;
    public HashMap<UUID, UUID> commentMap = new HashMap<>();


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
            String webVersion = CommunicatorHandler.readVersion();
            newestVersion = webVersion;
            if (!webVersion.equalsIgnoreCase(currentVersion)) {
                isNewerVersion = true;
            }
            addonEnabled = true;
        } catch (Exception ignored) {
            addonEnabled = false;
        }


        LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Like") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Cape") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Skin") ||
                playerMenu.getDisplayName().equalsIgnoreCase("SocialMedia") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Clear cosmetics") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Bandana") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Comments"));


        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new BandanaMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new LikeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SocialMediaMenu());


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LabyHelp.getInstace().getUserHandler().sendOnline(LabyMod.getInstance().getPlayerUUID(), false);

            LabyHelp.getInstace().getStoreHandler().getFileDownloader().installStoreAddons();

            if (isNewerVersion) {
                LabyHelp.getInstace().getStoreHandler().getFileDownloader().update();
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

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public StoreHandler getStoreHandler() {
        return storeHandler;
    }

    public InviteManager getInviteManager() {
        return inviteManager;
    }

    public CommentManager getCommentManager() {
        return commentManager;
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
        settingsAdversting = !this.getConfig().has("adversting") || this.getConfig().get("adversting").getAsBoolean();
        settinngsComments = !this.getConfig().has("comment") || this.getConfig().get("comment").getAsBoolean();

        this.statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

        LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons = !this.getConfig().has("storeAddons") || this.getConfig().get("storeAddons").getAsBoolean();

        this.instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        this.discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        this.youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        this.twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        this.twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        this.tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
        this.snapchatName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        this.nameTagString = this.getConfig().has("nametag") ? this.getConfig().get("nametag").getAsString() : "nametag";


        this.nameTagSettings = this.getConfig().has("nameTagSettings") ? this.getConfig().get("nameTagSettings").getAsString() : "SWITCHING";
        this.nameTagSwitchingSetting = this.getConfig().has("nameTagSettingsSwitching") ? this.getConfig().get("nameTagSettingsSwitching").getAsInt() : 10;
        this.nameTagSize = this.getConfig().has("nameTagSize") ? this.getConfig().get("nameTagSize").getAsInt() : 1;
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

        final BooleanElement settingsStore = new BooleanElement("Other LabyHelp Addons", new ControlElement.IconData(Material.REDSTONE), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons = enable;


                LabyHelp.this.getConfig().addProperty("storeAddons", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons);
        settingsElements.add(settingsStore);


        final BooleanElement settingAdversting = new BooleanElement("Chat Adversting", new ControlElement.IconData(Material.ITEM_FRAME), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().settingsAdversting = enable;


                LabyHelp.this.getConfig().addProperty("adversting", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().settingsAdversting);

        settingsElements.add(settingAdversting);

        final BooleanElement settingsComment = new BooleanElement("Comments at your profile", new ControlElement.IconData(Material.SIGN), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().settinngsComments = enable;

                if (enable) {
                    LabyHelp.getInstace().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "TRUE");
                } else {
                    LabyHelp.getInstace().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "FALSE");
                }
                LabyHelp.this.getConfig().addProperty("comment", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().settinngsComments);

        settingsElements.add(settingsComment);


        final DropDownMenu<NameTagSettings> nameTagSettings = new DropDownMenu<NameTagSettings>("Local NameTag Settings" /* Display name */, 0, 0, 0, 0)
                .fill(NameTagSettings.values());
        DropDownElement<NameTagSettings> alignmentDropDown = new DropDownElement<>("Local NameTag Settings ", nameTagSettings);

        if (LabyHelp.this.nameTagSettings != null) {
            nameTagSettings.setSelected(NameTagSettings.valueOf(LabyHelp.this.nameTagSettings));
        } else {
            nameTagSettings.setSelected(NameTagSettings.SWITCHING);
        }

        // Listen on changes
        alignmentDropDown.setChangeListener(new Consumer<NameTagSettings>() {
            @Override
            public void accept(NameTagSettings alignment) {

                LabyHelp.this.nameTagSettings = alignment.getName();

                LabyHelp.this.getConfig().addProperty("nameTagSettings", alignment.getName());
                LabyHelp.this.saveConfig();

            }
        });

        settingsElements.add(alignmentDropDown);

        SliderElement scalingSliderElement = new SliderElement("NameTag Size" /* Display name */,
                new ControlElement.IconData(Material.ANVIL) /* setting's icon */, 1 /* current value */);
        scalingSliderElement.setRange(1, 4);
        scalingSliderElement.setSteps(1);

        scalingSliderElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                LabyHelp.this.nameTagSize = accepted;

                LabyHelp.this.getConfig().addProperty("nameTagSize", nameTagSize);
                LabyHelp.this.saveConfig();
            }
        });

        settingsElements.add(scalingSliderElement);

        NumberElement numberElement = new NumberElement("NameTag Switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, nameTagSwitchingSetting  /* current value */);

        numberElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                LabyHelp.this.nameTagSwitchingSetting = accepted;

                LabyHelp.this.getConfig().addProperty("nameTagSettingsSwitching", nameTagSwitchingSetting);
                LabyHelp.this.saveConfig();
            }
        });

        settingsElements.add(numberElement);


        StringElement channelStringElement = new StringElement("Instagram username", new ControlElement.IconData(Material.PAPER), instaName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    CommunicatorHandler.sendInstagram(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendTikTok(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendTwitch(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendDiscord(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendYoutube(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendTwitter(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendSnapchat(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendNameTag(LabyMod.getInstance().getPlayerUUID(), accepted);
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
                    CommunicatorHandler.sendStatus(LabyMod.getInstance().getPlayerUUID(), accepted);
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
