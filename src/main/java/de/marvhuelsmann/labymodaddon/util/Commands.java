package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import de.marvhuelsmann.labymodaddon.menu.TargetMenu;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Commands {

    public void commandMessage(final String message) {
        LabyPlayer clientLabyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
        TranslationManager transManager = LabyHelp.getInstace().getTranslationManager();

        if (LabyHelp.getInstace().AddonSettingsEnable) {
            if (message.startsWith("/bandana")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/bandana ", ""));
                clientLabyPlayer.openBandanaUrl(uuid);
            } else if (message.startsWith("/cape")) {

                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/cape ", ""));
                clientLabyPlayer.openCapeUrl(uuid);

            } else if (message.startsWith("/skin")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/skin ", ""));
                clientLabyPlayer.openSkin(uuid);

            } else if (message.startsWith("/insta")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/insta ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.openInsta(labyPlayer.getSocialMedia(SocialMediaType.INSTAGRAM));

            } else if (message.startsWith("/discord")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/discord ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.sendDiscord(labyPlayer.getSocialMedia(SocialMediaType.DISCORD));

            } else if (message.startsWith("/youtube")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/youtube ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.openYoutube(labyPlayer.getSocialMedia(SocialMediaType.YOUTUBE));

            } else if (message.startsWith("/twitch")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/twitch ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.openTwitch(labyPlayer.getSocialMedia(SocialMediaType.TWTICH));

            } else if (message.startsWith("/twitter")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/twitter ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.openTwitter(labyPlayer.getSocialMedia(SocialMediaType.TWITTER));

            } else if (message.startsWith("/tiktok")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/tiktok ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.openTikTok(labyPlayer.getSocialMedia(SocialMediaType.TIKTOK));

            } else if (message.startsWith("/snapchat")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/snapchat ", ""));

                LabyPlayer labyPlayer = new LabyPlayer(uuid);
                clientLabyPlayer.sendSnapchat(labyPlayer.getSocialMedia(SocialMediaType.SNAPCHAT));

            } else if (message.startsWith("/lhban")) {
                String[] components = message.split(" ");
                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    if (components.length == 2) {
                        final UUID uuid = UUIDFetcher.getUUID(components[1]);
                        if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                            if (uuid != null) {
                                clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("player") + EnumChatFormatting.WHITE + components[1] + EnumChatFormatting.RED + transManager.getTranslation("staff.banned.nametag"));

                                clientLabyPlayer.sendDefaultMessage("");

                                CommunicatorHandler.sendBanned(uuid, "NAMETAG");
                            } else {
                                clientLabyPlayer.sendTranslMessage("main.not.exist");
                            }
                        } else {
                            clientLabyPlayer.sendTranslMessage("main.isteam");
                        }
                    } else {
                        clientLabyPlayer.sendTranslMessage("use.ban");
                    }
                } else {
                    clientLabyPlayer.sendNoPerms();
                }
            } else if (message.startsWith("/lhlike")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    final UUID uuid = UUIDFetcher.getUUID(components[1]);
                    if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {
                        if (!LabyHelp.getInstace().getLikeManager().isLiked.contains(uuid)) {
                            if (uuid != null) {
                                if (uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                                    if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                                        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                                            @Override
                                            public void run() {

                                                LabyHelp.getInstace().getLikeManager().sendLike(LabyMod.getInstance().getPlayerUUID(), uuid);

                                                LabyHelp.getInstace().getLikeManager().readUserLikes();
                                                LabyHelp.getInstace().getLikeManager().readLikes();

                                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("likes.like") + EnumChatFormatting.DARK_RED + components[1].toUpperCase() + EnumChatFormatting.RED + "!");
                                                if (LabyHelp.getInstace().getLikeManager().getLikes(uuid).equalsIgnoreCase("1")) {
                                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + transManager.getTranslation("likes.has.only") + LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Like!");
                                                } else {
                                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + transManager.getTranslation("likes.has") + LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Likes!");
                                                }
                                            }
                                        });
                                    } else {
                                        clientLabyPlayer.sendTranslMessage("main.hasnot");
                                    }
                                } else {
                                    clientLabyPlayer.sendTranslMessage("main.not.exist");
                                }
                            } else {
                                clientLabyPlayer.sendTranslMessage("main.not.exist");
                            }
                        } else {
                            clientLabyPlayer.sendDefaultMessage(transManager.getTranslation("likes.already") + EnumChatFormatting.WHITE + components[1]);
                        }
                    } else {
                        clientLabyPlayer.sendTranslMessage("likes.self");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage("/lhlike -" + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/likes")) {
                String[] components = message.split(" ");
                if (components.length == 2) {

                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
                            final UUID uuid = UUIDFetcher.getUUID(components[1]);

                            if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                                if (LabyHelp.getInstace().getLikeManager().getLikes(uuid).equalsIgnoreCase("1")) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase()+  transManager.getTranslation("likes.has.only") +LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Like!");
                                } else {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() +  transManager.getTranslation("likes.has") + LabyHelp.getInstace().getLikeManager().getLikes(uuid) + " Likes!");
                                }
                            } else {
                                clientLabyPlayer.sendTranslMessage("main.hasnot");
                            }
                        }
                    });

                } else {
                    clientLabyPlayer.sendDefaultMessage("/likes -" + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/likelist")) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getLikeManager().getTops5();

                        int i = 1;

                        for (Map.Entry<String, Integer> uuidStringEntry : list) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Likes");
                            i++;
                        }
                    }
                });
            } else if (message.startsWith("/invitelist")) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getInviteManager().getTops5();
                        int i = 1;
                        for (Map.Entry<String, Integer> uuidStringEntry : list) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + transManager.getTranslation("invite.points"));
                            i++;
                        }
                    }
                });
            } else if (message.startsWith("/invites")) {
                String[] components = message.split(" ");
                if (components.length == 2) {

                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
                            final UUID uuid = UUIDFetcher.getUUID(components[1]);

                            if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                                if (LabyHelp.getInstace().getInviteManager().getNowInvites().equalsIgnoreCase("1")) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() +  transManager.getTranslation("likes.has.only") + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") + "!");
                                } else {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + transManager.getTranslation("likes.has")  + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + transManager.getTranslation("invite.points") +"!");
                                }
                            } else {
                                clientLabyPlayer.sendTranslMessage("main.hasnot");
                            }
                        }
                    });

                } else {
                    clientLabyPlayer.sendDefaultMessage("/invites -"  + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/social")) {
                final String decode = message.replaceAll("/social ", "");
                final UUID uuid = UUIDFetcher.getUUID(decode);
                clientLabyPlayer.openSocial(uuid, decode);

            } else if (message.startsWith("/lhreload")) {
                clientLabyPlayer.sendTranslMessage("info.reload");
                try {
                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {

                            LabyHelp.getInstace().getStoreHandler().readHelpAddons();

                            LabyHelp.getInstace().getCommentManager().banned.clear();
                            LabyHelp.getInstace().getCommentManager().readBanned();

                            LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                            LabyHelp.getInstace().getGroupManager().updateNameTag(true);
                            LabyHelp.getInstace().getGroupManager().updateNameTag(false);

                            LabyHelp.getInstace().getLikeManager().isLiked.clear();
                            LabyHelp.getInstace().getLikeManager().readUserLikes();
                            LabyHelp.getInstace().getLikeManager().readLikes();

                            LabyHelp.getInstace().getTranslationManager().germanTranslations.clear();
                            LabyHelp.getInstace().getTranslationManager().englishTranslation.clear();
                            LabyHelp.getInstace().getTranslationManager().initTranslations();

                            LabyHelp.getInstace().getInviteManager().readUserInvites();
                            LabyHelp.getInstace().getInviteManager().readOldPlayer();

                            LabyHelp.getInstace().getCommunicationManager().isOnline.clear();
                            LabyHelp.getInstace().addonEnabled = true;
                            //LabyHelp.getInstace().getUserHandler().readIsOnline();

                            System.out.println("subtitles updating..");
                            final String webVersion = CommunicatorHandler.readVersion();
                            LabyHelp.getInstace().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.currentVersion);
                        }
                    });
                } catch (Exception ignored) {
                    LabyHelp.getInstace().addonEnabled = false;
                }
            } else if (message.startsWith("/labyhelp")) {
                try {
                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().addonEnabled = true;
                            final String webVersion = CommunicatorHandler.readVersion();
                            LabyHelp.getInstace().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.currentVersion);
                            LabyHelp.getInstace().newestVersion = webVersion;
                            if (!LabyHelp.getInstace().isNewerVersion) {
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.currentVersion + transManager.getTranslation("new"));
                            }
                            if (LabyHelp.getInstace().isNewerVersion) {
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + transManager.getTranslation("info.you") + " " + LabyHelp.currentVersion + transManager.getTranslation("old"));
                                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + transManager.getTranslation("info.new") + " " + webVersion);
                                clientLabyPlayer.sendAlertTranslMessage("info.restart");
                            }
                        }
                    });
                } catch (Exception ignored) {
                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + transManager.getTranslation("info.responding") + EnumChatFormatting.BOLD + "909");
                }
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Teamspeak: https://labyhelp.de/teamspeak");
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Discord: https://labyhelp.de/discord");
            } else if (message.startsWith("/lhrules")) {
                LabyMod.getInstance().openWebpage("https://labyhelp.de/rules", false);
                clientLabyPlayer.sendDefaultMessage("https://labyhelp.de/rules");
            } else if (message.startsWith("/lhteam")) {
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "LabyHelp Team:");
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "Position: " + LocalDate.now());
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Administation");
                clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "- marvhuel");
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                            if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                                if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.ADMIN) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.OWNER)) {
                                    if (!groupsEntry.getKey().toString().equals("d4389488-2692-436b-bc10-fce879f7441d")) {
                                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                                    }
                                }
                            }
                        }
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Developers");
                        for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                            if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                                if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.DEVELOPER)) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                                }
                            }
                        }
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Moderation");
                        for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                            if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                                if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.MODERATOR) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRMODERATOR)) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                                }
                            }
                        }
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.WHITE + "Addon Contents");
                        for (Map.Entry<UUID, HelpGroups> groupsEntry : LabyHelp.getInstace().getCommunicationManager().userGroups.entrySet()) {
                            if (LabyHelp.getInstace().getGroupManager().isTeam(groupsEntry.getKey())) {
                                if (LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.CONTENT) || LabyHelp.getInstace().getGroupManager().getRanked(groupsEntry.getKey()).equals(HelpGroups.SRCONTENT)) {
                                    clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "- " + UUIDFetcher.getName(groupsEntry.getKey()));
                                }
                            }
                        }
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.DARK_RED + transManager.getTranslation("main.teamlist") + EnumChatFormatting.WHITE + " https://labyhelp.de/");
                    }
                });
            }
            if (message.equalsIgnoreCase("/LhHelp")) {

                LabyMod.getInstance().openWebpage("https://labyhelp.de/support", false);

                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    clientLabyPlayer.sendDefaultMessage("- /lhban <Spieler> / NameTag");
                    clientLabyPlayer.sendDefaultMessage("- /lhweb");
                }

            } else if (message.startsWith("/lhweb")) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                        LabyMod.getInstance().openWebpage("https://labyhelp.de/dashboard/index.php", false);
                    } else {
                        clientLabyPlayer.sendNoPerms();
                    }

            } else if (message.equalsIgnoreCase("/lhmodetarget")) {
                if (LabyHelp.getInstace().targetMode) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Target"));
                    clientLabyPlayer.sendTranslMessage("target.left");


                    if (LabyHelp.getInstace().targetMode) {
                        for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                            if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(null);
                                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                            }
                        }
                    }

                    LabyHelp.getInstace().getCommunicationManager().targetMode(false);

                } else {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new TargetMenu());
                    clientLabyPlayer.sendTranslMessage("target.join");
                    LabyHelp.getInstace().getCommunicationManager().targetMode(true);
                }
            } else if (message.startsWith("/lhtarget")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    UUID uuid = UUIDFetcher.getUUID(components[1]);

                    if (uuid != null) {
                        if (LabyHelp.getInstace().targetList.contains(uuid)) {
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You remove " + components[1] + " from your local Target List");

                            if (LabyHelp.getInstace().targetMode) {
                                if (LabyHelp.getInstace().targetList.contains(uuid)) {
                                    LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(null);
                                    LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitleSize(1);
                                }
                            }

                            LabyHelp.getInstace().targetList.remove(uuid);

                        } else {
                            LabyHelp.getInstace().targetList.add(uuid);
                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You added " + components[1] + " to your local Target List");
                        }
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage("- /lhweb -" + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/lhcomment")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    UUID uuid = UUIDFetcher.getUUID(components[1]);
                    if (uuid != null) {
                        LabyHelp.getInstace().getCommentManager().refreshComments();

                        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                if (LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid)) {
                                    if (!LabyHelp.getInstace().getCommentManager().banned.contains(LabyMod.getInstance().getPlayerUUID())) {
                                        if (!LabyHelp.getInstace().getCommentManager().cooldown.contains(LabyMod.getInstance().getPlayerUUID())) {
                                            if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {

                                                LabyHelp.getInstace().commentChat = true;
                                                LabyHelp.getInstace().commentMap.put(LabyMod.getInstance().getPlayerUUID(), UUIDFetcher.getUUID(components[1]));

                                                clientLabyPlayer.sendTranslMessage("comment.info");
                                                clientLabyPlayer.sendTranslMessage("comment.info2");
                                            } else {
                                                clientLabyPlayer.sendTranslMessage("comments.self");
                                            }
                                        } else {
                                            clientLabyPlayer.sendTranslMessage("comments.wait");
                                        }
                                    } else {
                                       clientLabyPlayer.sendTranslMessage("comments.banned");
                                    }
                                } else {
                                    clientLabyPlayer.sendTranslMessage("main.hasnot");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage("- /lhcomment -" + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/showcomments")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    UUID uuid = UUIDFetcher.getUUID(components[1]);
                    if (uuid != null) {
                        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                LabyHelp.getInstace().getCommentManager().readAllComments(uuid);
                                LabyHelp.getInstace().getCommentManager().readAllowed(uuid);
                                if (!LabyHelp.getInstace().getCommentManager().comments.isEmpty()) {
                                    if (LabyHelp.getInstace().getCommentManager().isAllowed.contains(uuid)) {

                                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + components[1].toUpperCase() + transManager.getTranslation("comments.receive"));

                                        for (Map.Entry<UUID, String> entry : LabyHelp.getInstace().getCommentManager().comments.entrySet()) {
                                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + transManager.getTranslation("main.from")+ EnumChatFormatting.GRAY + UUIDFetcher.getName(entry.getKey()).toUpperCase());
                                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + transManager.getTranslation("main.message") + EnumChatFormatting.GRAY + entry.getValue());
                                            LabyMod.getInstance().displayMessageInChat("");
                                        }
                                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.GRAY + "ttps://labyhelp.de/comments.php?uuid=" + uuid + "&name=" + components[1]);
                                    } else {
                                        clientLabyPlayer.sendTranslMessage( "comments.dis");
                                    }
                                } else {
                                    clientLabyPlayer.sendTranslMessage("comments.null");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage("- /showcomments -" + transManager.getTranslation("player"));
                }
            } else if (message.startsWith("/lhaddons")) {

                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {

                        LabyHelp.getInstace().getStoreHandler().readHelpAddons();
                        clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.BLUE + "LabyHelp Addons:");
                        for (Map.Entry<String, String> addons : LabyHelp.getInstace().getStoreHandler().getAddonsList().entrySet()) {

                            clientLabyPlayer.sendDefaultMessage(EnumChatFormatting.BOLD + addons.getKey() +  EnumChatFormatting.GRAY + " " + transManager.getTranslation("main.from") + " " + EnumChatFormatting.BOLD + LabyHelp.getInstace().getStoreHandler().getAddonAuthor(addons.getKey()));

                        }
                    }
                });

            } else if (message.startsWith("/lhcode")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    UUID uuid = UUIDFetcher.getUUID(components[1]);
                    if (uuid != null) {
                        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                if (!components[1].equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
                                    if (!LabyHelp.getInstace().getInviteManager().isOldPlayer(LabyMod.getInstance().getPlayerUUID())) {
                                        LabyHelp.getInstace().getInviteManager().sendInvite(LabyMod.getInstance().getPlayerUUID(), uuid);
                                        clientLabyPlayer.sendTranslMessage("code.enter");

                                    } else {
                                        clientLabyPlayer.sendTranslMessage("code.newplayer");
                                    }
                                } else {
                                   clientLabyPlayer.sendTranslMessage("code.redeem.self");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendTranslMessage("main.not.exist");
                    }
                } else {
                    clientLabyPlayer.sendDefaultMessage("- /lhcode -" + transManager.getTranslation("player"));
                }
            }
        } else {
            clientLabyPlayer.sendTranslMessage("main.dis");
        }
    }
}