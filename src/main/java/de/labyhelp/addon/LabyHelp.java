package de.labyhelp.addon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.labyhelp.addon.commands.addon.*;
import de.labyhelp.addon.commands.feature.*;
import de.labyhelp.addon.commands.socialmedia.*;
import de.labyhelp.addon.commands.team.LabyHelpBanCMD;
import de.labyhelp.addon.commands.team.LabyHelpWebCMD;
import de.labyhelp.addon.enums.LabyVersion;
import de.labyhelp.addon.enums.Languages;
import de.labyhelp.addon.enums.SocialMediaType;
import de.labyhelp.addon.enums.Tags;
import de.labyhelp.addon.listeners.ClientJoinListener;
import de.labyhelp.addon.listeners.ClientQuitListener;
import de.labyhelp.addon.listeners.ClientTickListener;
import de.labyhelp.addon.listeners.MessageSendListener;
import de.labyhelp.addon.menu.LikeMenu;
import de.labyhelp.addon.menu.ReportMenu;
import de.labyhelp.addon.menu.ServerMenu;
import de.labyhelp.addon.menu.SocialMediaMenu;
import de.labyhelp.addon.module.DegreeModule;
import de.labyhelp.addon.module.TexturePackModule;
import de.labyhelp.addon.store.PartnerHandler;
import de.labyhelp.addon.store.StoreHandler;
import de.labyhelp.addon.util.*;
import de.labyhelp.addon.util.commands.CommandHandler;
import de.labyhelp.addon.util.settings.SettingsManager;
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
import net.minecraft.util.EnumChatFormatting;

import java.util.*;
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
    private final TagManager tagManager;
    @Getter
    private final InviteManager inviteManager;
    @Getter
    private final SettingsManager settingsManager;
    @Getter
    private final RequestManager requestManager;
    @Getter
    private final NameTagManager nameTagManager;
    @Getter
    private final VoiceChatManager voiceChatManager;
    @Getter
    private final PartnerHandler partnerHandler;
    @Getter
    private final TargetManager targetManager;

    public LabyHelp() {
        instance = this;

        executor = Executors.newCachedThreadPool();

        communicatorHandler = new CommunicatorHandler();

        groupManager = new GroupManager();
        storeHandler = new StoreHandler();

        translationManager = new TranslationManager();
        versionHandler = new VersionHandler();

        tagManager = new TagManager();

        commandHandler = new CommandHandler();

        likeManager = new LikeManager();
        socialMediaManager = new SocialMediaManager();
        inviteManager = new InviteManager();
        settingsManager = new SettingsManager();

        requestManager = new RequestManager();

        nameTagManager = new NameTagManager();
        voiceChatManager = new VoiceChatManager();

        partnerHandler = new PartnerHandler();
        targetManager = new TargetManager();

    }

    @Override
    public void onEnable() {
        getVersionHandler().initGameVersion(Source.ABOUT_MC_VERSION);

        this.getApi().registerForgeListener(new ClientTickListener());
        this.getApi().getEventManager().register(new MessageSendListener());
        this.getApi().getEventManager().registerOnJoin(new ClientJoinListener());
        this.getApi().getEventManager().registerOnQuit(new ClientQuitListener());

        getCommandHandler().registerCommand(
                new LabyHelpHelpCMD(),
                new LabyHelpReportCMD(),
                new LabyHelpDeveloperCMD(),
                new SocialCMD(),
                new LabyHelpCMD(),
                new LabyHelpAddonsCMD(),
                new LabyHelpCodeCMD(),
                new LabyHelpReloadCMD(),
                new LabyHelpRulesCMD(),
                new LabyHelpTeamCMD(),
                new InviteListCMD(),
                new InvitesCMD(),
                new LabyHelpLikeCMD(),
                new LikeListCMD(),
                new LikesCMD(),
                new BandanaCMD(),
                new CapeCMD(),
                new SkinCMD(),
                new ServerCMD(),
                new SupportCMD(),
                new LabyHelpBanCMD(),
                new LabyHelpWebCMD(),
                new VoiceChatCMD(),
                new LabyHelpPartnerCMD(),
                new LabyHelpBadgeCMD(),
                new TargetCMD()
        );


        if (getVersionHandler().getGameVersion().equals(LabyVersion.ONE_EIGHTEEN)) {
            this.getApi().registerModule(new DegreeModule());
            this.getApi().registerModule(new TexturePackModule());
        }

        try {
            LabyHelp.getInstance().getStoreHandler().readHelpAddons();
            String webVersion = getStoreHandler().getFileDownloader().readAddonVersion();
            getSettingsManager().newestVersion = webVersion;
            if (!webVersion.equalsIgnoreCase(SettingsManager.currentVersion)) {
                getSettingsManager().isNewerVersion = true;
            }

            getSettingsManager().addonEnabled = true;
        } catch (Exception ignored) {
            getSettingsManager().addonEnabled = false;
        }


        changePlayerMenuItems();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendOnline.php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&isOnline=OFFLINE");
            LabyHelp.getInstance().getStoreHandler().getFileDownloader().installStoreAddons();

            if (getSettingsManager().isNewerVersion || getSettingsManager().versionTag != null) {
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

    public void sendDefaultMessage(String message) {
        LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
        labyPlayer.sendDefaultMessage(message);
    }

    public void sendSpecficTranslMessage(String start, String message) {
        LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
        labyPlayer.sendSpecificTranslMessage(message, start);
    }


    public void sendDeveloperMessage(String message) {
        if (LabyHelp.getInstance().getSettingsManager().developerMode) {
            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
            labyPlayer.sendDeveloperMessage(message);
        }
    }

    public boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    public void changePlayerMenuItems() {
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Like") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Cape") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Skin") ||
                playerMenu.getDisplayName().equalsIgnoreCase("LabyHelp Profile") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Bandana") ||
                playerMenu.getDisplayName().equalsIgnoreCase("See Server") ||
                playerMenu.getDisplayName().equalsIgnoreCase("NameTag report") ||
                playerMenu.getDisplayName().equalsIgnoreCase("Socialmedia") ||
                playerMenu.getDisplayName().equalsIgnoreCase("join Server"));

        if (settingsManager.seePlayerMenu) {
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new LikeMenu());
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new ServerMenu());
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SocialMediaMenu());
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new ReportMenu());
        }

    }

    public void changeFirstJoin(Boolean bool) {
        LabyHelp.getInstance().getSettingsManager().firstPlay = bool;
        LabyHelp.getInstance().getSettingsManager().newVersionMessage = bool;
        LabyHelp.getInstance().getConfig().addProperty("newVersionMessage", bool);
        LabyHelp.getInstance().getConfig().addProperty("firstJoin", bool);
        LabyHelp.getInstance().saveConfig();
    }

    public boolean isInitialize() {
        return instance != null;
    }

    @Override
    public void loadConfig() {
        LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        LabyHelp.getInstance().getSettingsManager().settingsAdversting = !this.getConfig().has("adversting") || this.getConfig().get("adversting").getAsBoolean();
        LabyHelp.getInstance().getSettingsManager().seePlayerMenu = !this.getConfig().has("seePlayerMenu") || this.getConfig().get("seePlayerMenu").getAsBoolean();

        LabyHelp.getInstance().getSettingsManager().firstPlay = !this.getConfig().has("firstJoin") || this.getConfig().get("firstJoin").getAsBoolean();

        LabyHelp.getInstance().getSettingsManager().newVersionMessage = !this.getConfig().has("newVersionMessage") || this.getConfig().get("newVersionMessage").getAsBoolean();

        LabyHelp.getInstance().getSettingsManager().seeNameTags = !this.getConfig().has("seeNameTags") || this.getConfig().get("seeNameTags").getAsBoolean();
        LabyHelp.getInstance().getSettingsManager().partnerNotify = !this.getConfig().has("partnerNotify") || this.getConfig().get("partnerNotify").getAsBoolean();

        LabyHelp.getInstance().getTranslationManager().chooseLanguage = this.getConfig().has("translation") ? this.getConfig().get("translation").getAsString() : "DEUTSCH";

        LabyHelp.getInstance().getSocialMediaManager().statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

        LabyHelp.getInstance().getSocialMediaManager().statusName = this.getConfig().has("status") ? this.getConfig().get("status").getAsString() : "status";

        LabyHelp.getInstance().getStoreHandler().getStoreSettings().storeAddons = !this.getConfig().has("storeAddons") || this.getConfig().get("storeAddons").getAsBoolean();

        LabyHelp.getInstance().getSettingsManager().rightTag = this.getConfig().has("rightTag") ? this.getConfig().get("rightTag").getAsString() : "NOTHING";
        LabyHelp.getInstance().getSettingsManager().leftTag = this.getConfig().has("leftTag") ? this.getConfig().get("leftTag").getAsString() : "NOTHING";

        LabyHelp.getInstance().getSocialMediaManager().instaName = this.getConfig().has("instaname") ? this.getConfig().get("instaname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().discordName = this.getConfig().has("discordname") ? this.getConfig().get("discordname").getAsString() : "user#0000";
        LabyHelp.getInstance().getSocialMediaManager().youtubeName = this.getConfig().has("youtubename") ? this.getConfig().get("youtubename").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().twitchName = this.getConfig().has("twitchname") ? this.getConfig().get("twitchname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().githubName = this.getConfig().has("githubname") ? this.getConfig().get("githubname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().twitterName = this.getConfig().has("twittername") ? this.getConfig().get("twittername").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().tiktokName = this.getConfig().has("tiktokname") ? this.getConfig().get("tiktokname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().snapchatName = this.getConfig().has("snapchatname") ? this.getConfig().get("snapchatname").getAsString() : "username";
        LabyHelp.getInstance().getSocialMediaManager().nameTagName = this.getConfig().has("nametag") ? this.getConfig().get("nametag").getAsString() : "nametag";
        LabyHelp.getInstance().getSocialMediaManager().secondNameTagName = this.getConfig().has("second_nametag") ? this.getConfig().get("second_nametag").getAsString() : "second_nametag" + "";

        LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting = this.getConfig().has("nameTagSettingsSwitching") ? this.getConfig().get("nameTagSettingsSwitching").getAsInt() : 10;
        LabyHelp.getInstance().getSettingsManager().nameTagSettings = this.getConfig().has("nameTagSettings") ? this.getConfig().get("nameTagSettings").getAsString() : "SWITCHING";
        LabyHelp.getInstance().getSettingsManager().nameTagRainbwSwitching = this.getConfig().has("nameTagRainbwSwitching") ? this.getConfig().get("nameTagRainbwSwitching").getAsInt() : 1;
        LabyHelp.getInstance().getSettingsManager().nameTagSize = this.getConfig().has("nameTagSize") ? this.getConfig().get("nameTagSize").getAsInt() : 1;
        if (this.getConfig().has("targetPlayers")) {
            final JsonObject object = this.getConfig().get("targetPlayers").getAsJsonObject();
            final HashMap<UUID, Integer> targetPlayers = new HashMap<>();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                targetPlayers.put(UUID.fromString(entry.getKey()), entry.getValue().getAsInt());
            }
            LabyHelp.getInstance().getSettingsManager().targetPlayers = targetPlayers;
        } else {
            LabyHelp.getInstance().getSettingsManager().targetPlayers = new HashMap<>();
        }
    }


    @Override
    protected void fillSettings(List<SettingsElement> settingsElements) {
        final BooleanElement settingsEnabled = new BooleanElement("Enabled", new ControlElement.IconData(Material.GOLD_BARDING), enable -> {
            LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable = enable;
            LabyHelp.this.getConfig().addProperty("enable", enable);
            LabyHelp.this.saveConfig();
        }, LabyHelp.getInstance().getSettingsManager().AddonSettingsEnable);
        settingsElements.add(settingsEnabled);
        settingsElements.add(new HeaderElement("§7Activate or disable the Addon"));
        /* */

        final DropDownMenu<Languages> alignmentDropDownMenu = new DropDownMenu<Languages>("Your Language" /* Display name */, 0, 0, 0, 0)
                .fill(Languages.values());
        DropDownElement<Languages> alignmentDropDown = new DropDownElement<Languages>("Your Language:", alignmentDropDownMenu);


        alignmentDropDownMenu.setSelected(getTranslationManager().getChooseTranslation(getTranslationManager().chooseLanguage) != null ? Languages.valueOf(getTranslationManager().chooseLanguage) : Languages.DEUTSCH);

        alignmentDropDown.setChangeListener(alignment -> {


            if (!getTranslationManager().chooseLanguage.equals(alignment.getName())) {
                LabyHelp.getInstance().getTranslationManager().chooseLanguage = alignment.getName();
                LabyHelp.getInstance().getTranslationManager().initTranslation(alignment);

                LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                labyPlayer.sendAlertTranslMessage("main.lang");
            } else {
                LabyHelp.getInstance().getTranslationManager().chooseLanguage = alignment.getName();
                LabyHelp.getInstance().getTranslationManager().initTranslation(alignment);
            }


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

        settingsElements.add(new HeaderElement("§7Install and use LabyHelp Store Addons"));
        settingsElements.add(new HeaderElement(" "));
        final BooleanElement settingsPlayerMenu = new BooleanElement("LabyHelp PlayerMenu items", new ControlElement.IconData(Material.ARROW), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstance().getSettingsManager().seePlayerMenu = enable;
                changePlayerMenuItems();


                LabyHelp.this.getConfig().addProperty("seePlayerMenu", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstance().getSettingsManager().seePlayerMenu);
        settingsElements.add(settingsPlayerMenu);

        settingsElements.add(new HeaderElement("§7See the LabyHelp PlayerMenu items"));

        final BooleanElement settingsPartner = new BooleanElement("Partner notify", new ControlElement.IconData(Material.FURNACE), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelp.getInstance().getSettingsManager().partnerNotify = enable;
                changePlayerMenuItems();


                LabyHelp.this.getConfig().addProperty("partnerNotify", enable);
                LabyHelp.this.saveConfig();
            }
        }, LabyHelp.getInstance().getSettingsManager().partnerNotify);
        settingsElements.add(settingsPartner);

        settingsElements.add(new HeaderElement("§7Get messages or features at partner servers"));
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


        settingsElements.add(new HeaderElement("§7Receive chat promotional messages"));
        settingsElements.add(new HeaderElement("§7with our Discord or TeamSpeak"));
        settingsElements.add(new HeaderElement(" "));


        final BooleanElement settingsNameTags = new BooleanElement("Show NameTags", new ControlElement.IconData(Material.STRING), enable -> {
            LabyHelp.getInstance().getSettingsManager().seeNameTags = enable;

            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
            labyPlayer.sendAlertTranslMessage("main.lang");

            LabyHelp.this.getConfig().addProperty("seeNameTags", enable);
            LabyHelp.this.saveConfig();
        }, LabyHelp.getInstance().getSettingsManager().seeNameTags);
        settingsElements.add(settingsNameTags);
        settingsElements.add(new HeaderElement("§7Choose if you will see"));
        settingsElements.add(new HeaderElement("§7LabyHelp NameTags in the game"));
        settingsElements.add(new HeaderElement(" "));

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
        settingsElements.add(new HeaderElement("§7Choose the speed in how many seconds"));
        settingsElements.add(new HeaderElement("§7the rainbow colors should change"));

        settingsElements.add(new HeaderElement(" "));

        NumberElement numberElement = new NumberElement("NameTag Switching Time" /* Display name */,
                new ControlElement.IconData(Material.WATCH) /* setting's icon */, LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting  /* current value */);

        numberElement.addCallback(accepted -> {
            LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting = accepted;

            LabyHelp.this.getConfig().addProperty("nameTagSettingsSwitching", LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting);
            LabyHelp.this.saveConfig();
        });

        settingsElements.add(numberElement);
        settingsElements.add(new HeaderElement("§7How quickly should the NameTags"));
        settingsElements.add(new HeaderElement("§7change to the NameTags ranks"));

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

        StringElement github = new StringElement("GitHub username", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().snapchatName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.GITHUB, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().githubName = accepted;

                LabyHelp.this.getConfig().addProperty("githubname", accepted);
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

        StringElement second_nameTag = new StringElement("Second NameTag", new ControlElement.IconData(Material.PAPER), LabyHelp.getInstance().getSocialMediaManager().secondNameTagName, new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                try {
                    LabyHelp.getInstance().getSocialMediaManager().sendSocialMedia(SocialMediaType.SECOND_NAMETAG, accepted);
                } catch (Exception ignored) {
                }

                LabyHelp.getInstance().getSocialMediaManager().secondNameTagName = accepted;

                LabyHelp.this.getConfig().addProperty("second_nametag", accepted);
                LabyHelp.this.saveConfig();
            }
        });
        settingsElements.add(second_nameTag);

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

        final DropDownMenu<Tags> leftTag = new DropDownMenu<Tags>("Badge left:" /* Display name */, 0, 0, 0, 0)
                .fill(Tags.values());
        DropDownElement<Tags> leftTagElement = new DropDownElement<Tags>("Badge left:", leftTag);


        leftTag.setSelected(Tags.valueOf(getSettingsManager().leftTag) != null ? Tags.valueOf(getSettingsManager().leftTag) : Tags.NOTHING);

        leftTagElement.setChangeListener(alignment -> {

            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());

            if (getTagManager().hasPermissionToTag(LabyMod.getInstance().getPlayerUUID(), alignment)) {
                if (!getTagManager().hasAlreadySet(LabyMod.getInstance().getPlayerUUID(), alignment, true)) {
                    getSettingsManager().leftTag = alignment.name();

                    LabyHelp.getInstance().getExecutor().submit(() -> {
                        getTagManager().sendSpecificTag(false, LabyMod.getInstance().getPlayerUUID(), alignment);
                        getTagManager().initTagManager();
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + LabyHelp.getInstance().getTranslationManager().getTranslation("main.tag.change") + EnumChatFormatting.WHITE + " (" + alignment.getRequestName() + ")");
                    });

                    LabyHelp.this.getConfig().addProperty("leftTag", alignment.name());
                    LabyHelp.this.saveConfig();
                } else {
                    labyPlayer.sendAlertTranslMessage("main.badges.alreadyset");
                }
            } else {
                if (!getSettingsManager().leftTag.equals(alignment.name())) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + LabyHelp.getInstance().getTranslationManager().getTranslation("main.tag.noperms") + EnumChatFormatting.WHITE + " (" + alignment.getRequestName() + ")");
                }
            }

        });

        leftTag.setEntryDrawer((object, x, y, trimmedEntry) -> {
            String entry = object.toString().toLowerCase();
            LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);

        });

        settingsElements.add(leftTagElement);

        final DropDownMenu<Tags> rightTag = new DropDownMenu<Tags>("Badge right:" /* Display name */, 0, 0, 0, 0)
                .fill(Tags.values());
        DropDownElement<Tags> rightTagElement = new DropDownElement<Tags>("Badge right:", rightTag);


        rightTag.setSelected(Tags.valueOf(getSettingsManager().rightTag) != null ? Tags.valueOf(getSettingsManager().rightTag) : Tags.NOTHING);

        rightTagElement.setChangeListener(alignment -> {

            LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());

            if (getTagManager().hasPermissionToTag(LabyMod.getInstance().getPlayerUUID(), alignment)) {
                if (!getTagManager().hasAlreadySet(LabyMod.getInstance().getPlayerUUID(), alignment, false)) {
                    getSettingsManager().rightTag = alignment.name();

                    LabyHelp.getInstance().getExecutor().submit(() -> {
                        getTagManager().sendSpecificTag(true, LabyMod.getInstance().getPlayerUUID(), alignment);
                        getTagManager().initTagManager();
                        labyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + LabyHelp.getInstance().getTranslationManager().getTranslation("main.tag.change") + EnumChatFormatting.WHITE + " (" + alignment.getRequestName() + ")");
                    });

                    LabyHelp.this.getConfig().addProperty("rightTag", alignment.name());
                    LabyHelp.this.saveConfig();
                } else {
                    labyPlayer.sendAlertTranslMessage("main.badges.alreadyset");
                }
            } else {
                if (!getSettingsManager().rightTag.equals(alignment.name())) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + LabyHelp.getInstance().getTranslationManager().getTranslation("main.tag.noperms") + EnumChatFormatting.WHITE + " (" + alignment.getRequestName() + ")");
                }
            }

        });

        rightTag.setEntryDrawer((object, x, y, trimmedEntry) -> {
            String entry = object.toString().toLowerCase();
            LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);

        });

        settingsElements.add(rightTagElement);
        settingsElements.add(new HeaderElement("§7A global badge next to your rank, more informations:"));
        settingsElements.add(new HeaderElement("§ehttps://labyhelp.de/badges"));

        settingsElements.add(new HeaderElement(" "));

        settingsElements.add(channelStringElement);
        settingsElements.add(stringDiscord);
        settingsElements.add(stringTwitter);
        settingsElements.add(snapchat);
        settingsElements.add(channelTikTok);
        settingsElements.add(stringYoutube);
        settingsElements.add(stringTwitch);
        settingsElements.add(github);

        settingsElements.add(new HeaderElement(" "));
        settingsElements.add(new HeaderElement("§fDiscord: §lhttps://labyhelp.de/discord"));
        settingsElements.add(new HeaderElement("§fTeamSpeak: §lhttps://labyhelp.de/teamspeak"));
    }
}
