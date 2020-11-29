package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.enums.SocialMediaType;
import de.marvhuelsmann.labymodaddon.menu.TargetMenu;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.cosmetic.cosmetics.shop.head.*;
import net.labymod.user.cosmetic.cosmetics.shop.pet.CosmeticPetDragon;
import net.labymod.user.cosmetic.cosmetics.shop.shoes.CosmeticShoes;
import net.labymod.user.group.LabyGroup;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Commands {


    public void commandMessage(final String message) {
        LabyPlayer clientLabyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());

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
                                clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "Der Spieler " + EnumChatFormatting.WHITE + components[1] + EnumChatFormatting.RED + " wurde wegen dem NAMETAG fuer ein Tag gebannt!");
                                WebServer.sendBanned(uuid, "NAMETAG");
                            } else {
                                clientLabyPlayer.sendMessage("Der Spieler existiert nicht!");
                            }
                        } else {
                            clientLabyPlayer.sendMessage("Der Spieler ist im LabyHelp Team!");
                        }
                    } else {
                        clientLabyPlayer.sendMessage("Bitte benutze /lhban <Spieler>");
                    }
                } else {
                    clientLabyPlayer.sendNoPerms();
                }
            } else if (message.startsWith("/lhlike")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    final UUID uuid = UUIDFetcher.getUUID(components[1]);
                    if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {
                        if (!LabyHelp.getInstace().getUserHandler().isLiked.contains(uuid)) {
                            if (uuid != null) {
                                if (uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                                    if (LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid)) {
                                        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                                            @Override
                                            public void run() {

                                                LabyHelp.getInstace().getUserHandler().sendLike(LabyMod.getInstance().getPlayerUUID(), uuid);

                                                LabyHelp.getInstace().getUserHandler().readUserLikes();
                                                LabyHelp.getInstace().getUserHandler().readLikes();

                                                clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + "You have liked " + EnumChatFormatting.DARK_RED + components[1].toUpperCase() + EnumChatFormatting.RED + "!");
                                                if (LabyHelp.getInstace().getUserHandler().getLikes(uuid).equalsIgnoreCase("1")) {
                                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has now only " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Like!");
                                                } else {
                                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has now " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Likes!");
                                                }
                                            }
                                        });
                                    } else {
                                        clientLabyPlayer.sendMessage("This Player does not have LabyHelp!");
                                    }
                                } else {
                                    clientLabyPlayer.sendMessage("This Player does not exist");
                                }
                            } else {
                                clientLabyPlayer.sendMessage("This Player does not exist");
                            }
                        } else {
                            clientLabyPlayer.sendMessage("You have already liked " + EnumChatFormatting.WHITE + components[1]);
                        }
                    } else {
                        clientLabyPlayer.sendMessage("You can't like yourself!");
                    }
                } else {
                    clientLabyPlayer.sendMessage("Please use /lhlike <Player>");
                }
            } else if (message.startsWith("/likes")) {
                String[] components = message.split(" ");
                if (components.length == 2) {

                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().getUserHandler().readUserInformations(true);
                            final UUID uuid = UUIDFetcher.getUUID(components[1]);

                            if (LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid)) {
                                if (LabyHelp.getInstace().getUserHandler().getLikes(uuid).equalsIgnoreCase("1")) {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has only " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Like!");
                                } else {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Likes!");
                                }
                            } else {
                                clientLabyPlayer.sendMessage("This Player does not have LabyHelp!");
                            }
                        }
                    });

                } else {
                    clientLabyPlayer.sendMessage("Please use /likes <Player>");
                }
            } else if (message.startsWith("/likelist")) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getUserHandler().getTops5();

                        int i = 1;

                        for (Map.Entry<String, Integer> uuidStringEntry : list) {
                            clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Likes");
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
                            clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Invite Points");
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
                            LabyHelp.getInstace().getUserHandler().readUserInformations(true);
                            final UUID uuid = UUIDFetcher.getUUID(components[1]);

                            if (LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid)) {
                                if (LabyHelp.getInstace().getInviteManager().getNowInvites().equalsIgnoreCase("1")) {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has only " + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + " Invite Points!");
                                } else {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has " + LabyHelp.getInstace().getInviteManager().getInvites(uuid) + " Invite Points!");
                                }
                            } else {
                                clientLabyPlayer.sendMessage("This Player does not have LabyHelp!");
                            }
                        }
                    });

                } else {
                    clientLabyPlayer.sendMessage("Please use /invites <Player>");
                }
            } else if (message.startsWith("/social")) {
                final String decode = message.replaceAll("/social ", "");
                final UUID uuid = UUIDFetcher.getUUID(decode);
                clientLabyPlayer.openSocial(uuid, decode);

            } else if (message.startsWith("/lhreload")) {
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "The LabyHelp addon has been reloaded!");
                try {
                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {

                            LabyHelp.getInstace().getTeamManager().list.clear();

                            LabyHelp.getInstace().getCommentManager().banned.clear();
                            LabyHelp.getInstace().getCommentManager().readBanned();

                            LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                            LabyHelp.getInstace().getGroupManager().updateNameTag(false);

                            LabyHelp.getInstace().getUserHandler().isLiked.clear();
                            LabyHelp.getInstace().getUserHandler().readUserLikes();
                            LabyHelp.getInstace().getUserHandler().readLikes();

                            LabyHelp.getInstace().getInviteManager().readUserInvites();
                            LabyHelp.getInstace().getInviteManager().readOldPlayer();

                            LabyHelp.getInstace().getUserHandler().isOnline.clear();
                            //LabyHelp.getInstace().getUserHandler().readIsOnline();
                            System.out.println("subtitles updating..");
                            final String webVersion = WebServer.readVersion();
                            LabyHelp.getInstace().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.currentVersion);
                        }
                    });
                } catch (Exception ignored) {
                }
                System.out.println("version updating..");
            } else if (message.startsWith("/lhignore")) {
                final String decode = message.replaceAll("/lhignore ", "");
                final UUID uuid = UUIDFetcher.getUUID(decode);
                //     if (uuid != null) {
                //        labyPlayer.block(decode);
                //    } else {
                //        labyPlayer.sendMessage("Der Spieler existiert nicht!");
                //   }
            } else if (message.startsWith("/labyhelp")) {
                final String webVersion = WebServer.readVersion();
                LabyHelp.getInstace().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.currentVersion);
                LabyHelp.getInstace().newestVersion = webVersion;
                if (!LabyHelp.getInstace().isNewerVersion) {
                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + "Your Version: " + LabyHelp.currentVersion + " (newest)");
                }
                if (LabyHelp.getInstace().isNewerVersion) {
                    clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + "Your Version: " + LabyHelp.currentVersion + " (old)");
                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "Newest Version: " + webVersion);
                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "Restart your game to update your version!");
                }
                clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + "Our Teamspeak: https://labyhelp.de/teamspeak");
                clientLabyPlayer.sendMessage(EnumChatFormatting.WHITE + "Our Discord: https://labyhelp.de/discord");
            } else if (message.startsWith("/lhrules")) {
                LabyMod.getInstance().openWebpage("https://labyhelp.de/rules", false);
                clientLabyPlayer.sendMessage("Die Regel Seite hat sich automatisch geoeffnet");
                clientLabyPlayer.sendMessage("Nichts geoeffnet? https://labyhelp.de/rules");
            } else if (message.startsWith("/lhteam")) {
                clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "LabyHelp Team:");
                clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "Position: " + LocalDate.now());
                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Administation (3)");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Marvienio");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Connan97");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Tig4z");
                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Developers (1)");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Rufi");
                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Moderation (3)");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Parodie");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- ObiiiTooo");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- einMaro");
                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Contents (2)");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- ogdarkeagle");
                clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Psintx");
                clientLabyPlayer.sendMessage(EnumChatFormatting.DARK_RED + "You can also see the team page here: " + EnumChatFormatting.WHITE + " https://labyhelp.de/");
            }
            if (message.equalsIgnoreCase("/LhHelp")) {




                clientLabyPlayer.sendMessage("- /bandana <player>");
                clientLabyPlayer.sendMessage("- /cape <player>");
                clientLabyPlayer.sendMessage("- /skin <player>");
                clientLabyPlayer.sendMessage("- /insta <player>");
                clientLabyPlayer.sendMessage("- /discord <player>");
                clientLabyPlayer.sendMessage("- /youtube <player>");
                clientLabyPlayer.sendMessage("- /twitch <player>");
                clientLabyPlayer.sendMessage("- /tiktok <player>");
                clientLabyPlayer.sendMessage("- /twitter <player>");
                clientLabyPlayer.sendMessage("- /snapchat <player>");
                clientLabyPlayer.sendMessage("- /social <player>");
                //    clientLabyPlayer.sendMessage("- /lhignore <player>");
                clientLabyPlayer.sendMessage("- /lhrules");
                clientLabyPlayer.sendMessage("- /lhteam");
                clientLabyPlayer.sendMessage("- /lhlike <player> / Like a Player");
                clientLabyPlayer.sendMessage("- /likes <player> / See Player likes");
                clientLabyPlayer.sendMessage("- /likelist / Shows the most famous player");
                clientLabyPlayer.sendMessage("- /lhtarget <player> / add or remove from your list");
                clientLabyPlayer.sendMessage("- /lhmodetarget / to turn on or off the Target Mode");
                clientLabyPlayer.sendMessage("- /lhcomment <player> / write a comment to the player");
                clientLabyPlayer.sendMessage("- /showcomments <player> / see the comments from the player");
                clientLabyPlayer.sendMessage("- /lhcode <player> / To accept an invitation code");
                clientLabyPlayer.sendMessage("- /invites <player> / See the Invite Points from the Player");
                clientLabyPlayer.sendMessage("- /invitelist / Show you the players with the most invite points");
                clientLabyPlayer.sendMessage("- /lhreload");
                clientLabyPlayer.sendMessage("- /labyhelp");

                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    clientLabyPlayer.sendMessage("- /lhban <player> / Only NameTag");
                    clientLabyPlayer.sendMessage("- /lhweb <key> / <null>");
                }

            } else if (message.startsWith("/lhweb")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                        LabyMod.getInstance().openWebpage("https://marvhuelsmann.de/linkto.php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&ip=" + LabyHelp.getInstace().getUserHandler().getIp() + "&key=" + components[1], false);
                    } else {
                        clientLabyPlayer.sendNoPerms();
                    }
                } else {
                    clientLabyPlayer.sendMessage("- /lhweb <key> / <null>");
                }
            } else if (message.equalsIgnoreCase("/lhmodetarget")) {
                if (LabyHelp.getInstace().targetMode) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().removeIf(playerMenu -> playerMenu.getDisplayName().equalsIgnoreCase("Target"));
                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You have left Target mode");


                    if (LabyHelp.getInstace().targetMode) {
                        for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
                            if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(null);
                                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                            }
                        }
                    }

                    LabyHelp.getInstace().getUserHandler().targetMode(false);

                } else {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new TargetMenu());
                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You have entered Target Mode");
                    LabyHelp.getInstace().getUserHandler().targetMode(true);
                }
            } else if (message.startsWith("/lhtarget")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    UUID uuid = UUIDFetcher.getUUID(components[1]);

                    if (uuid != null) {
                        if (LabyHelp.getInstace().targetList.contains(uuid)) {
                            clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You remove " + components[1] + " from your local Target List");

                            if (LabyHelp.getInstace().targetMode) {
                                if (LabyHelp.getInstace().targetList.contains(uuid)) {
                                    LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(null);
                                    LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitleSize(1);
                                }
                            }

                            LabyHelp.getInstace().targetList.remove(uuid);

                        } else {
                            LabyHelp.getInstace().targetList.add(uuid);
                            clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You added " + components[1] + " to your local Target List");
                        }
                    } else {
                        clientLabyPlayer.sendMessage("This Player does not exit!");
                    }
                } else {
                    clientLabyPlayer.sendMessage("- /lhweb <player>");
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
                                if (LabyHelp.getInstace().getUserHandler().userGroups.containsKey(uuid)) {
                                    if (!LabyHelp.getInstace().getCommentManager().banned.contains(LabyMod.getInstance().getPlayerUUID())) {
                                        if (!LabyHelp.getInstace().getCommentManager().cooldown.contains(LabyMod.getInstance().getPlayerUUID())) {
                                            if (!LabyMod.getInstance().getPlayerUUID().equals(uuid)) {

                                                LabyHelp.getInstace().commentChat = true;
                                                LabyHelp.getInstace().commentMap.put(LabyMod.getInstance().getPlayerUUID(), UUIDFetcher.getUUID(components[1]));

                                                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Please send now your comment to this person!");
                                                clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "In the normal chat without /" + EnumChatFormatting.YELLOW + ". The message will not be sent in the public chat!");
                                            } else {
                                                clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You cannot write a comment yourself!");
                                            }
                                        } else {
                                            clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You still have to wait until you can write a comment to this person again");
                                        }
                                    } else {
                                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You are banned in the LabyHelp comment function!");
                                    }
                                } else {
                                    clientLabyPlayer.sendMessage("This Player does not have LabyHelp!");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "This Player does not exit!");
                    }
                } else {
                    clientLabyPlayer.sendMessage("- /lhcomment <player>");
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

                                        clientLabyPlayer.sendMessage(EnumChatFormatting.YELLOW + components[1].toUpperCase() + " received the following comments:");

                                        for (Map.Entry<UUID, String> entry : LabyHelp.getInstace().getCommentManager().comments.entrySet()) {
                                            clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "From: " + EnumChatFormatting.GRAY + UUIDFetcher.getName(entry.getKey()).toUpperCase());
                                            clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "Message: " + EnumChatFormatting.GRAY + entry.getValue());
                                            LabyMod.getInstance().displayMessageInChat("");
                                        }
                                        clientLabyPlayer.sendMessage(EnumChatFormatting.GRAY + "On the web: https://labyhelp.de/comments.php?uuid=" + uuid + "&name=" + components[1]);
                                    } else {
                                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "The player has deactivated his comments!");
                                    }
                                } else {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "This player has not received any comments!");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "This Player does not exit!");
                    }
                } else {
                    clientLabyPlayer.sendMessage("- /showcomments <player>");
                }
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
                                        clientLabyPlayer.sendMessage(EnumChatFormatting.GREEN + "You have successfully entered a LabyHelp Invite Code for which you have received 5 likes");

                                    } else {
                                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You have to be a new LabyHelp player or you have already redeemed a code!");
                                    }
                                } else {
                                    clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "You cannot redeem your code yourself");
                                }
                            }
                        });
                    } else {
                        clientLabyPlayer.sendMessage(EnumChatFormatting.RED + "This Player does not exit!");
                    }
                } else {
                    clientLabyPlayer.sendMessage("- /lhcode <player>");
                }
            }
        } else {
            clientLabyPlayer.sendMessage("You have deactivated the Addon!");
        }
    }
}