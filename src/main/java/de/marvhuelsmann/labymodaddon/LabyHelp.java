package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.commands.addon.*;
import de.marvhuelsmann.labymodaddon.commands.comment.LabyHelpCommentCMD;
import de.marvhuelsmann.labymodaddon.commands.comment.ShowCommentsCMD;
import de.marvhuelsmann.labymodaddon.commands.feature.*;
import de.marvhuelsmann.labymodaddon.commands.socialmedia.*;
import de.marvhuelsmann.labymodaddon.commands.target.ModeTargetCMD;
import de.marvhuelsmann.labymodaddon.commands.target.TargetCMD;
import de.marvhuelsmann.labymodaddon.commands.team.LabyHelpBanCMD;
import de.marvhuelsmann.labymodaddon.commands.team.LabyHelpWebCMD;
import de.marvhuelsmann.labymodaddon.enums.Languages;
import de.marvhuelsmann.labymodaddon.enums.NameTagSettings;
import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import de.marvhuelsmann.labymodaddon.listeners.ClientJoinListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientQuitListener;
import de.marvhuelsmann.labymodaddon.listeners.ClientTickListener;
import de.marvhuelsmann.labymodaddon.listeners.MessageSendListener;
import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.DegreeModule;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.store.StoreHandler;
import de.marvhuelsmann.labymodaddon.util.CommunicatorHandler;
import de.marvhuelsmann.labymodaddon.util.GroupManager;
import de.marvhuelsmann.labymodaddon.util.TranslationManager;
import de.marvhuelsmann.labymodaddon.util.commands.CommandHandler;
import de.marvhuelsmann.labymodaddon.util.settings.SettingsManager;
import de.marvhuelsmann.labymodaddon.util.transfer.CommentManager;
import de.marvhuelsmann.labymodaddon.util.transfer.InviteManager;
import de.marvhuelsmann.labymodaddon.util.transfer.LikeManager;
import de.marvhuelsmann.labymodaddon.util.transfer.SocialMediaManager;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.main.lang.LanguageManager;
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


    private final de.marvhuelsmann.labymodaddon.util.GroupManager groupManager = new de.marvhuelsmann.labymodaddon.util.GroupManager();
    private final CommunicatorHandler comunicationManager = new CommunicatorHandler();
    private final StoreHandler storeHandler = new StoreHandler();
    private final TranslationManager translationManager = new TranslationManager();

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final CommandHandler commandHandler = new CommandHandler();

    private final LikeManager likeManager = new LikeManager();
    private final SocialMediaManager socialMediaManager = new SocialMediaManager();
    private final InviteManager inviteManager = new InviteManager();
    private final CommentManager commentManager = new CommentManager();

    private final SettingsManager settingsManager = new SettingsManager();


    @Override
    public void onEnable() {
        instace = this;

        this.getApi().registerForgeListener(new ClientTickListener());
        this.getApi().getEventManager().register(new MessageSendListener());
        this.getApi().getEventManager().registerOnJoin(new ClientJoinListener());
        this.getApi().getEventManager().registerOnQuit(new ClientQuitListener());


        getCommandHandler().registerCommand(new LabyHelpCMD());
        getCommandHandler().registerCommand(new LabyHelpAddonsCMD());
        getCommandHandler().registerCommand(new LabyHelpCodeCMD());
        getCommandHandler().registerCommand(new LabyHelpReloadCMD());
        getCommandHandler().registerCommand(new LabyHelpRulesCMD());
        getCommandHandler().registerCommand(new LabyHelpTeamCMD());

        getCommandHandler().registerCommand(new LabyHelpCommentCMD());
        getCommandHandler().registerCommand(new ShowCommentsCMD());

        getCommandHandler().registerCommand(new InviteListCMD());
        getCommandHandler().registerCommand(new InvitesCMD());
        getCommandHandler().registerCommand(new LabyHelpLikeCMD());
        getCommandHandler().registerCommand(new LikeListCMD());
        getCommandHandler().registerCommand(new LikesCMD());

        getCommandHandler().registerCommand(new BandanaCMD());
        getCommandHandler().registerCommand(new CapeCMD());
        getCommandHandler().registerCommand(new SkinCMD());
        getCommandHandler().registerCommand(new DiscordCMD());
        getCommandHandler().registerCommand(new InstaCMD());
        getCommandHandler().registerCommand(new SnapChatCMD());
        getCommandHandler().registerCommand(new TikTokCMD());
        getCommandHandler().registerCommand(new TwitchCMD());
        getCommandHandler().registerCommand(new TwitterCMD());
        getCommandHandler().registerCommand(new YoutubeCMD());

        getCommandHandler().registerCommand(new ModeTargetCMD());
        getCommandHandler().registerCommand(new TargetCMD());

        getCommandHandler().registerCommand(new LabyHelpBanCMD());
        getCommandHandler().registerCommand(new LabyHelpWebCMD());


        if (Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            getSettingsManger().oldVersion = true;
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        try {
            LabyHelp.getInstace().getStoreHandler().readHelpAddons();
            LabyHelp.getInstace().getTranslationManager().initTranslation(Languages.valueOf(LabyHelp.getInstace().getTranslationManager().chooseLanguage));
            String webVersion = CommunicatorHandler.readVersion();
            getSettingsManger().newestVersion = webVersion;
            if (!webVersion.equalsIgnoreCase(getSettingsManger().currentVersion)) {
                getSettingsManger().isNewerVersion = true;
            }
            getSettingsManger().addonEnabled = true;
        } catch (Exception ignored) {
            getSettingsManger().addonEnabled = false;
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
            LabyHelp.getInstace().getCommunicationManager().sendOnline(LabyMod.getInstance().getPlayerUUID(), false);

            LabyHelp.getInstace().getStoreHandler().getFileDownloader().installStoreAddons();

            if (getSettingsManger().isNewerVersion) {
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

    public LikeManager getLikeManager() {
        return likeManager;
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

    public SocialMediaManager getSocialMediaManager() {
        return socialMediaManager;
    }

    public CommunicatorHandler getCommunicationManager() {
        return comunicationManager;
    }

    public CommentManager getCommentManager() {
        return commentManager;
    }

    public TranslationManager getTranslationManager() { return translationManager; }

    public ExecutorService getExecutor() {
        return threadPool;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public SettingsManager getSettingsManger() { return settingsManager; }

    @Override
    public void loadConfig() {
        LabyHelp.getInstace().getSettingsManger().AddonSettingsEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        LabyHelp.getInstace().getSettingsManger().settingsAdversting = !this.getConfig().has("adversting") || this.getConfig().get("adversting").getAsBoolean();
        LabyHelp.getInstace().getSettingsManger().settinngsComments = !this.getConfig().has("comment") || this.getConfig().get("comment").getAsBoolean();

        LabyHelp.getInstace().getTranslationManager().chooseLanguage = this.getConfig().has("translation") ? this.getConfig().get("translation").getAsString() : "DEUTSCH";

        LabyHelp.getInstace().getSocialMediaManager().statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

        LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons = !this.getConfig().has("storeAddons") || this.getConfig().get("storeAddons").getAsBoolean();

        LabyHelp.getInstace().getSocialMediaManager().instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        LabyHelp.getInstace().getSocialMediaManager().youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().snapchatName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        LabyHelp.getInstace().getSocialMediaManager().nameTagName = this.getConfig().has("nametag") ? this.getConfig().get("nametag").getAsString() : "nametag";

        LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting = this.getConfig().has("nameTagSettingsSwitching") ? this.getConfig().get("nameTagSettingsSwitching").getAsInt() : 10;
        LabyHelp.getInstace().getSettingsManger().nameTagSettings = this.getConfig().has("nameTagSettings") ? this.getConfig().get("nameTagSettings").getAsString() : "SWITCHING";
        LabyHelp.getInstace().getSettingsManger().nameTagRainbwSwitching = this.getConfig().has("nameTagRainbwSwitching") ? this.getConfig().get("nameTagRainbwSwitching").getAsInt() : 1;
        LabyHelp.getInstace().getSettingsManger().nameTagSize = this.getConfig().has("nameTagSize") ? this.getConfig().get("nameTagSize").getAsInt() : 1;
    }

    @Override
    protected void fillSettings(List<SettingsElement> settingsElements) {


        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().getSettingsManger().AddonSettingsEnable = enable;


                LabyHelp.this.getConfig().addProperty("enable", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().getSettingsManger().AddonSettingsEnable);
        settingsElements.add(settingsEnabled);

        final DropDownMenu<Languages> alignmentDropDownMenu = new DropDownMenu<Languages>("Your Language" /* Display name */, 0, 0, 0, 0)
                .fill(Languages.values());
        DropDownElement<Languages> alignmentDropDown = new DropDownElement<Languages>("Your Language:", alignmentDropDownMenu);


        alignmentDropDownMenu.setSelected(Languages.valueOf(LabyHelp.getInstace().getTranslationManager().chooseLanguage));

        alignmentDropDown.setChangeListener(new Consumer<Languages>() {
            @Override
            public void accept(Languages alignment) {
                LabyHelp.getInstace().getTranslationManager().chooseLanguage = alignment.getName();

                LabyHelp.getInstace().getTranslationManager().initTranslation(alignment);

                LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                labyPlayer.sendAlertTranslMessage("main.lang");

                LabyHelp.this.getConfig().addProperty("translation", alignment.name());
                LabyHelp.this.saveConfig();
            }
        });

        alignmentDropDownMenu.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object object, int x, int y, String trimmedEntry) {
                String entry = object.toString().toLowerCase();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);

            }
        });
        settingsElements.add((SettingsElement) new HeaderElement(" "));
        settingsElements.add(alignmentDropDown);
        settingsElements.add((SettingsElement) new HeaderElement(" "));

        final BooleanElement settingsStore = new BooleanElement("Other LabyHelp Addons", new ControlElement.IconData(Material.REDSTONE), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons = enable;


                LabyHelp.this.getConfig().addProperty("storeAddons", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().getStoreHandler().getStoreSettings().storeAddons);
        settingsElements.add(settingsStore);
        settingsElements.add((SettingsElement) new HeaderElement(" "));

        final BooleanElement settingAdversting = new BooleanElement("Chat Adversting", new ControlElement.IconData(Material.ITEM_FRAME), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().getSettingsManger().settingsAdversting = enable;


                LabyHelp.this.getConfig().addProperty("adversting", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().getSettingsManger().settingsAdversting);

        settingsElements.add(settingAdversting);


        final BooleanElement settingsComment = new BooleanElement("Comments at your profile", new ControlElement.IconData(Material.SIGN), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstace().getSettingsManger().settinngsComments = enable;

                if (enable) {
                    LabyHelp.getInstace().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "TRUE");
                } else {
                    LabyHelp.getInstace().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "FALSE");
                }
                LabyHelp.this.getConfig().addProperty("comment", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstace().getSettingsManger().settinngsComments);

        settingsElements.add(settingsComment);

        settingsElements.add((SettingsElement) new HeaderElement(" "));


        final DropDownMenu<NameTagSettings> nameTagSettings = new DropDownMenu<NameTagSettings>("Local NameTag Settings" /* Display name */, 0, 0, 0, 0)
                .fill(NameTagSettings.values());
        DropDownElement<NameTagSettings> alignmentDropDownMenus = new DropDownElement<>("Local NameTag Settings ", nameTagSettings);

        if (LabyHelp.getInstace().getSettingsManger().nameTagSettings != null) {
            nameTagSettings.setSelected(NameTagSettings.valueOf(LabyHelp.getInstace().getSettingsManger().nameTagSettings));
        } else {
            nameTagSettings.setSelected(NameTagSettings.SWITCHING);
        }

        // Listen on changes
        alignmentDropDownMenus.setChangeListener(new Consumer<NameTagSettings>() {
            @Override
            public void accept(NameTagSettings alignment) {

                LabyHelp.getInstace().getSettingsManger().nameTagSettings = alignment.getName();

                LabyHelp.this.getConfig().addProperty("nameTagSettings", alignment.getName());
                LabyHelp.this.saveConfig();

            }
        });

        settingsElements.add(alignmentDropDownMenus);

        SliderElement scalingSliderElement = new SliderElement("NameTag Size" /* Display name */,
                new ControlElement.IconData(Material.ANVIL) /* setting's icon */, 1 /* current value */);
        scalingSliderElement.setRange(1, 4);
        scalingSliderElement.setSteps(1);

        scalingSliderElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                LabyHelp.getInstace().getSettingsManger().nameTagSize = accepted;

                LabyHelp.this.getConfig().addProperty("nameTagSize", LabyHelp.getInstace().getSettingsManger().nameTagSize);
                LabyHelp.this.saveConfig();
            }
        });

        settingsElements.add(scalingSliderElement);

        SliderElement rainbowElement = new SliderElement("NameTag Rainbow switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, 1 /* current value */);
        rainbowElement.setRange(1, 5);
        rainbowElement.setSteps(1);

        rainbowElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                LabyHelp.getInstace().getSettingsManger().nameTagRainbwSwitching = accepted;

                LabyHelp.this.getConfig().addProperty("nameTagRainbwSwitching", LabyHelp.getInstace().getSettingsManger().nameTagRainbwSwitching);
                LabyHelp.this.saveConfig();
            }
        });

        settingsElements.add(rainbowElement);


        NumberElement numberElement = new NumberElement("NameTag Switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting  /* current value */);

        numberElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting = accepted;

                LabyHelp.this.getConfig().addProperty("nameTagSettingsSwitching", LabyHelp.getInstace().getSettingsManger().nameTagSwitchingSetting);
                LabyHelp.this.saveConfig();
            }
        });

        settingsElements.add(numberElement);


        StringElement channelStringElement = new StringElement("Instagram username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().instaName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {

                LabyHelp.getInstace().getSocialMediaManager().instaName = accepted;

                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.INSTAGRAM, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.getConfig().addProperty("instaname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement channelTikTok = new StringElement("TikTok username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().tiktokName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.TIKTOK, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().tiktokName = accepted;

                LabyHelp.this.getConfig().addProperty("tiktokname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitch = new StringElement("Twitch username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().twitchName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.TWTICH, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().twitchName = accepted;

                LabyHelp.this.getConfig().addProperty("twitchname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringDiscord = new StringElement("Discord username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().discordName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.DISCORD, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().discordName = accepted;

                LabyHelp.this.getConfig().addProperty("discordname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringYoutube = new StringElement("Youtube username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().youtubeName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.YOUTUBE, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().youtubeName = accepted;

                LabyHelp.this.getConfig().addProperty("youtubename", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitter = new StringElement("Twitter username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().twitterName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.TWITTER, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().twitterName = accepted;

                LabyHelp.this.getConfig().addProperty("twittername", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement snapchat = new StringElement("Snapchat username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().snapchatName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.SNAPCHAT, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().snapchatName = accepted;

                LabyHelp.this.getConfig().addProperty("snapchatname", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add((SettingsElement) new HeaderElement(" "));

        StringElement nameTag = new StringElement("NameTag", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().nameTagName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.NAMETAG, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().nameTagName = accepted;

                LabyHelp.this.getConfig().addProperty("nametag", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add(nameTag);

        StringElement status = new StringElement("Status", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstace().getSocialMediaManager().statusName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstace().getSocialMediaManager().sendSocialMedia(SocialMediaType.STATUS, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstace().getSocialMediaManager().statusName = accepted;

                LabyHelp.this.getConfig().addProperty("status", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add(status);

        settingsElements.add((SettingsElement) new HeaderElement(" "));

        settingsElements.add(channelStringElement);
        settingsElements.add(stringDiscord);
        settingsElements.add(stringTwitter);
        settingsElements.add(snapchat);
        settingsElements.add(channelTikTok);
        settingsElements.add(stringYoutube);
        settingsElements.add(stringTwitch);

        settingsElements.add((SettingsElement) new HeaderElement(" "));
        settingsElements.add((SettingsElement) new HeaderElement("§fDiscord: §lhttps://labyhelp.de/discord"));
        settingsElements.add((SettingsElement) new HeaderElement("§fTeamSpeak: §lhttps://labyhelp.de/teamspeak"));
    }
}
