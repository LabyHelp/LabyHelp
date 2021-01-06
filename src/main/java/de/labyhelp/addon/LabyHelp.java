package de.labyhelp.addon;

import de.labyhelp.addon.commands.SocialCMD;
import de.labyhelp.addon.commands.addon.*;
import de.labyhelp.addon.commands.comment.LabyHelpCommentCMD;
import de.labyhelp.addon.commands.comment.ShowCommentsCMD;
import de.labyhelp.addon.commands.feature.*;
import de.labyhelp.addon.commands.socialmedia.*;
import de.labyhelp.addon.commands.target.ModeTargetCMD;
import de.labyhelp.addon.commands.target.TargetCMD;
import de.labyhelp.addon.commands.team.LabyHelpBanCMD;
import de.labyhelp.addon.commands.team.LabyHelpWebCMD;
import de.labyhelp.addon.enums.LabyVersion;
import de.labyhelp.addon.enums.Languages;
import de.labyhelp.addon.enums.NameTagSettings;
import de.labyhelp.addon.enums.SocialMediaType;
import de.labyhelp.addon.listeners.ClientJoinListener;
import de.labyhelp.addon.listeners.ClientQuitListener;
import de.labyhelp.addon.listeners.ClientTickListener;
import de.labyhelp.addon.listeners.MessageSendListener;
import de.labyhelp.addon.menu.*;
import de.labyhelp.addon.module.DegreeModule;
import de.labyhelp.addon.module.TexturePackModule;
import de.labyhelp.addon.store.StoreHandler;
import de.labyhelp.addon.util.CommunicatorHandler;
import de.labyhelp.addon.util.GroupManager;
import de.labyhelp.addon.util.TranslationManager;
import de.labyhelp.addon.util.VersionHandler;
import de.labyhelp.addon.util.commands.CommandHandler;
import de.labyhelp.addon.util.settings.SettingsManager;
import de.labyhelp.addon.util.transfer.CommentManager;
import de.labyhelp.addon.util.transfer.InviteManager;
import de.labyhelp.addon.util.transfer.LikeManager;
import de.labyhelp.addon.util.transfer.SocialMediaManager;
import lombok.Getter;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.main.Source;
import net.labymod.main.lang.LanguageManager;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LabyHelp extends net.labymod.api.LabyModAddon {

    private static LabyHelp instance;

    @Getter
    private final ExecutorService executor;

    @Getter
    private final CommunicatorHandler communicatorHandler;
    @Getter
    private final GroupManager groupManager;
    @Getter
    private final StoreHandler storeHandler;
    @Getter
    private final TranslationManager translationManager;
    @Getter
    private final VersionHandler versionHandler;

    @Getter
    private final CommandHandler commandHandler;
    @Getter
    private final LikeManager likeManager;
    @Getter
    private final SocialMediaManager socialMediaManager;
    @Getter
    private final InviteManager inviteManager;
    @Getter
    private final CommentManager commentManager;
    @Getter
    private final SettingsManager settingsManager;

    public LabyHelp() {
        instance = this;

        executor = Executors.newCachedThreadPool();

        communicatorHandler = new CommunicatorHandler();

        groupManager = new GroupManager();
        storeHandler = new StoreHandler();

        translationManager = new TranslationManager();
        versionHandler = new VersionHandler();

        commandHandler = new CommandHandler();

        likeManager = new LikeManager();
        socialMediaManager = new SocialMediaManager();
        inviteManager = new InviteManager();
        commentManager = new CommentManager();
        settingsManager = new SettingsManager();

    }

    @Override
    public void onEnable() {
        getVersionHandler().initGameVersion(Source.ABOUT_MC_VERSION);

        this.getApi().registerForgeListener(new ClientTickListener());
        this.getApi().getEventManager().register(new MessageSendListener());
        this.getApi().getEventManager().registerOnJoin(new ClientJoinListener());
        this.getApi().getEventManager().registerOnQuit(new ClientQuitListener());

        getCommandHandler().registerCommand(
                new SocialCMD(),
                new LabyHelpCMD(),
                new LabyHelpAddonsCMD(),
                new LabyHelpCodeCMD(),
                new LabyHelpReloadCMD(),
                new LabyHelpRulesCMD(),
                new LabyHelpTeamCMD(),
                new LabyHelpCommentCMD(),
                new ShowCommentsCMD(),
                new InviteListCMD(),
                new InvitesCMD(),
                new LabyHelpLikeCMD(),
                new LikeListCMD(),
                new LikesCMD(),
                new BandanaCMD(),
                new CapeCMD(),
                new SkinCMD(),
                new DiscordCMD(),
                new InstaCMD(),
                new SnapChatCMD(),
                new TikTokCMD(),
                new TwitchCMD(),
                new TwitterCMD(),
                new YoutubeCMD(),
                new ModeTargetCMD(),
                new TargetCMD(),
                new LabyHelpBanCMD(),
                new LabyHelpWebCMD()
        );


        if (getVersionHandler().getGameVersion().equals(LabyVersion.ONE_EIGHTEEN)) {
            getSettingsManager().oldVersion = true;
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        try {
            LabyHelp.getInstance().getStoreHandler().readHelpAddons();
            String webVersion = getStoreHandler().getFileDownloader().readAddonVersion("https://marvhuelsmann.de/version.php");
            getSettingsManager().newestVersion = webVersion;
            if (!webVersion.equalsIgnoreCase(getSettingsManager().currentVersion)) {
                getSettingsManager().isNewerVersion = true;
            }
            getSettingsManager().addonEnabled = true;
        } catch (Exception ignored) {
            getSettingsManager().addonEnabled = false;
        }


        LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Like") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Cape") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Skin") ||
                playerMenu.getDisplayName().equalsIgnoreCase("SocialMedia") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Clear cosmetics") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Bandana") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Comments"));


        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new LikeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SocialMediaMenu());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LabyHelp.getInstance().getCommunicatorHandler().sendOnline(LabyMod.getInstance().getPlayerUUID(), false);

            LabyHelp.getInstance().getStoreHandler().getFileDownloader().installStoreAddons();

            if (getSettingsManager().isNewerVersion) {
                LabyHelp.getInstance().getStoreHandler().getFileDownloader().updateLabyHelp();
            }
        }));
    }

    public static LabyHelp getInstance() {
        return instance;
    }

    public UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    public void sendTranslMessage(String message) {
        LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
        labyPlayer.sendTranslMessage(message);
    }

    public boolean isInitialize() {
        return instance != null;
    }

    @Override
    public void loadConfig() {
        LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        LabyHelp.getInstance().getSettingsManager().settingsAdversting = !this.getConfig().has("adversting") || this.getConfig().get("adversting").getAsBoolean();
        LabyHelp.getInstance().getSettingsManager().settinngsComments = !this.getConfig().has("comment") || this.getConfig().get("comment").getAsBoolean();

        LabyHelp.getInstance().getTranslationManager().chooseLanguage = this.getConfig().has("translation") ? this.getConfig().get("translation").getAsString() : "DEUTSCH";

        LabyHelp.getInstance().getSocialMediaManager().statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

        LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons = !this.getConfig().has("storeAddons") || this.getConfig().get("storeAddons").getAsBoolean();

        LabyHelp.getInstance().getSocialMediaManager().instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        LabyHelp.getInstance().getSocialMediaManager().youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().snapchatName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().nameTagName = this.getConfig().has("nametag") ? this.getConfig().get("nametag").getAsString() : "nametag";

        LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting = this.getConfig().has("nameTagSettingsSwitching") ? this.getConfig().get("nameTagSettingsSwitching").getAsInt() : 10;
        LabyHelp.getInstance().getSettingsManager().nameTagSettings = this.getConfig().has("nameTagSettings") ? this.getConfig().get("nameTagSettings").getAsString() : "SWITCHING";
        LabyHelp.getInstance().getSettingsManager().nameTagRainbwSwitching = this.getConfig().has("nameTagRainbwSwitching") ? this.getConfig().get("nameTagRainbwSwitching").getAsInt() : 1;
        LabyHelp.getInstance().getSettingsManager().nameTagSize = this.getConfig().has("nameTagSize") ? this.getConfig().get("nameTagSize").getAsInt() : 1;
    }

    @Override
    protected void fillSettings(List<SettingsElement> settingsElements) {
        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), enable -> {
            LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable = enable;
            LabyHelp.this.getConfig().addProperty("enable", enable);
            LabyHelp.this.saveConfig();
        }, LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable);
        settingsElements.add(settingsEnabled);

        final DropDownMenu<Languages> alignmentDropDownMenu = new DropDownMenu<Languages>("Your Language" /* Display name */, 0, 0, 0, 0)
                .fill(Languages.values());
        DropDownElement<Languages> alignmentDropDown = new DropDownElement<Languages>("Your Language:", alignmentDropDownMenu);


        alignmentDropDownMenu.setSelected(Languages.valueOf(LabyHelp.getInstance().getTranslationManager().chooseLanguage));

        alignmentDropDown.setChangeListener(alignment -> {
            LabyHelp.getInstance().getTranslationManager().chooseLanguage = alignment.getName();

            LabyHelp.getInstance().getTranslationManager().initTranslation(alignment);

            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
            labyPlayer.sendAlertTranslMessage("main.lang");

            LabyHelp.this.getConfig().addProperty("translation", alignment.name());
            LabyHelp.this.saveConfig();
        });

        alignmentDropDownMenu.setEntryDrawer((object, x, y, trimmedEntry) -> {
            String entry = object.toString().toLowerCase();
            LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);

        });

        settingsElements.add(new HeaderElement(" "));
        settingsElements.add(alignmentDropDown);
        settingsElements.add(new HeaderElement(" "));

        final BooleanElement settingsStore = new BooleanElement("Other LabyHelp Addons", new ControlElement.IconData(Material.REDSTONE), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons = enable;


                LabyHelp.this.getConfig().addProperty("storeAddons", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons);
        settingsElements.add(settingsStore);
        settingsElements.add(new HeaderElement(" "));

        final BooleanElement settingAdversting = new BooleanElement("Chat Adversting", new ControlElement.IconData(Material.ITEM_FRAME), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstance().getSettingsManager().settingsAdversting = enable;
                LabyHelp.this.getConfig().addProperty("adversting", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstance().getSettingsManager().settingsAdversting);

        settingsElements.add(settingAdversting);

        final BooleanElement settingsComment = new BooleanElement("Comments at your profile", new ControlElement.IconData(Material.SIGN), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstance().getSettingsManager().settinngsComments = enable;

                if (enable) {
                    LabyHelp.getInstance().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "TRUE");
                } else {
                    LabyHelp.getInstance().getCommentManager().sendToggle(LabyMod.getInstance().getPlayerUUID(), "FALSE");
                }
                LabyHelp.this.getConfig().addProperty("comment", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstance().getSettingsManager().settinngsComments);

        settingsElements.add(settingsComment);

        settingsElements.add(new HeaderElement(" "));


        final DropDownMenu<NameTagSettings> nameTagSettings = new DropDownMenu<NameTagSettings>("Local NameTag Settings" /* Display name */, 0, 0, 0, 0)
                .fill(NameTagSettings.values());
        DropDownElement<NameTagSettings> alignmentDropDownMenus = new DropDownElement<>("Local NameTag Settings ", nameTagSettings);

        if (LabyHelp.getInstance().getSettingsManager().nameTagSettings != null) {
            nameTagSettings.setSelected(NameTagSettings.valueOf(LabyHelp.getInstance().getSettingsManager().nameTagSettings));
        } else {
            nameTagSettings.setSelected(NameTagSettings.SWITCHING);
        }

        // Listen on changes
        alignmentDropDownMenus.setChangeListener(alignment -> {

            LabyHelp.getInstance().getSettingsManager().nameTagSettings = alignment.getName();

            LabyHelp.this.getConfig().addProperty("nameTagSettings", alignment.getName());
            LabyHelp.this.saveConfig();

        });

        settingsElements.add(alignmentDropDownMenus);

        SliderElement scalingSliderElement = new SliderElement("NameTag Size" /* Display name */,
                new ControlElement.IconData(Material.ANVIL) /* setting's icon */, 1 /* current value */);
        scalingSliderElement.setRange(1, 4);
        scalingSliderElement.setSteps(1);

        scalingSliderElement.addCallback(accepted -> {
            LabyHelp.getInstance().getSettingsManager().nameTagSize = accepted;

            LabyHelp.this.getConfig().addProperty("nameTagSize", LabyHelp.getInstance().getSettingsManager().nameTagSize);
            LabyHelp.this.saveConfig();
        });

        settingsElements.add(scalingSliderElement);

        SliderElement rainbowElement = new SliderElement("NameTag Rainbow switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, 1 /* current value */);
        rainbowElement.setRange(1, 5);
        rainbowElement.setSteps(1);

        rainbowElement.addCallback(accepted -> {
            LabyHelp.getInstance().getSettingsManager().nameTagRainbwSwitching = accepted;

            LabyHelp.this.getConfig().addProperty("nameTagRainbwSwitching", LabyHelp.getInstance().getSettingsManager().nameTagRainbwSwitching);
            LabyHelp.this.saveConfig();
        });

        settingsElements.add(rainbowElement);


        NumberElement numberElement = new NumberElement("NameTag Switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting  /* current value */);

        numberElement.addCallback(accepted -> {
            LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting = accepted;

            LabyHelp.this.getConfig().addProperty("nameTagSettingsSwitching", LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting);
            LabyHelp.this.saveConfig();
        });

        settingsElements.add(numberElement);


        StringElement channelStringElement = new StringElement("Instagram username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().instaName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {

                LabyHelp.getInstance().getSocialMediaManager().instaName = accepted;

                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.INSTAGRAM, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.this.getConfig().addProperty("instaname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement channelTikTok = new StringElement("TikTok username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().tiktokName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.TIKTOK, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().tiktokName = accepted;

                LabyHelp.this.getConfig().addProperty("tiktokname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitch = new StringElement("Twitch username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().twitchName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.TWTICH, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().twitchName = accepted;

                LabyHelp.this.getConfig().addProperty("twitchname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringDiscord = new StringElement("Discord username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().discordName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.DISCORD, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().discordName = accepted;

                LabyHelp.this.getConfig().addProperty("discordname", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringYoutube = new StringElement("Youtube username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().youtubeName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.YOUTUBE, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().youtubeName = accepted;

                LabyHelp.this.getConfig().addProperty("youtubename", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement stringTwitter = new StringElement("Twitter username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().twitterName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.TWITTER, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().twitterName = accepted;

                LabyHelp.this.getConfig().addProperty("twittername", accepted);
                LabyHelp.this.saveConfig();
            }
        });

        StringElement snapchat = new StringElement("Snapchat username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().snapchatName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.SNAPCHAT, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().snapchatName = accepted;

                LabyHelp.this.getConfig().addProperty("snapchatname", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add((SettingsElement) new HeaderElement(" "));

        StringElement nameTag = new StringElement("NameTag", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().nameTagName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.NAMETAG, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().nameTagName = accepted;

                LabyHelp.this.getConfig().addProperty("nametag", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add(nameTag);

        StringElement status = new StringElement("Status", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().statusName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.STATUS, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().statusName = accepted;

                LabyHelp.this.getConfig().addProperty("status", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add(status);

        settingsElements.add(new HeaderElement(" "));

        settingsElements.add(channelStringElement);
        settingsElements.add(stringDiscord);
        settingsElements.add(stringTwitter);
        settingsElements.add(snapchat);
        settingsElements.add(channelTikTok);
        settingsElements.add(stringYoutube);
        settingsElements.add(stringTwitch);

        settingsElements.add(new HeaderElement(" "));
        settingsElements.add(new HeaderElement("§fDiscord: §lhttps://labyhelp.de/discord"));
        settingsElements.add(new HeaderElement("§fTeamSpeak: §lhttps://labyhelp.de/teamspeak"));
    }
}
