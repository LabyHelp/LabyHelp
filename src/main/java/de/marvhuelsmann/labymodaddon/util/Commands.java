package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.voicechat.VoiceChatHandler;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.*;

public class Commands {


    public void commandMessage(final String message) {
        final LabyPlayer labyPlayer = new LabyPlayer();


        if (LabyHelp.getInstace().AddonSettingsEnable) {
            if (message.startsWith("/bandana")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/bandana ", ""));
                if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
                    if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                        labyPlayer.openBandanaUrl(uuid);
                    } else {
                        labyPlayer.sendMessage("Diese Aktion ist bei diesem Spieler deaktiviert, weil er ein Team Mitglied ist!");
                    }
                } else {
                    labyPlayer.sendNoPermsMessage();
                }
            } else if (message.startsWith("/cape")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/cape ", ""));
                if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
                    if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                        labyPlayer.openCapeUrl(uuid);
                    } else {
                        labyPlayer.sendMessage("Diese Aktion ist bei diesem Spieler deaktiviert, weil er ein Team Mitglied ist!");
                    }
                } else {
                    labyPlayer.sendNoPermsMessage();
                }
            } else if (message.startsWith("/skin")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/skin ", ""));
                if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
                    if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                        labyPlayer.openSkin(uuid);
                    } else {
                        labyPlayer.sendMessage("Diese Aktion ist bei diesem Spieler deaktiviert, weil er ein Team Mitglied ist!");
                    }
                } else {
                    labyPlayer.sendNoPermsMessage();
                }
            } else if (message.startsWith("/cosmeticsC")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/cosmeticsC ", ""));
                if (!LabyHelp.getInstace().getGroupManager().isPremium(uuid) || LabyHelp.getInstace().getGroupManager().isPremium(LabyMod.getInstance().getPlayerUUID())) {
                    if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                        LabyMod.getInstance().getUserManager().getUser(uuid).getCosmetics().clear();
                    } else {
                        labyPlayer.sendMessage("Diese Aktion ist bei diesem Spieler deaktiviert, weil er ein Team Mitglied ist!");
                    }
                } else {
                    labyPlayer.sendNoPermsMessage();
                }
            } else if (message.startsWith("/insta")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/insta ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().instaName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> instaEntry : LabyHelp.getInstace().getUserHandler().instaName.entrySet()) {
                        if (instaEntry.getKey() != null) {
                            if (instaEntry.getKey().equals(uuid)) {
                                labyPlayer.openInsta(instaEntry.getValue());
                            }
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein Instagram hinterlegt!");
                }
            } else if (message.startsWith("/discord")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/discord ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().discordName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> discordEntry : LabyHelp.getInstace().getUserHandler().discordName.entrySet()) {
                        if (discordEntry.getKey().equals(uuid)) {
                            labyPlayer.sendDiscord(discordEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein Discord hinterlegt!");
                }
            } else if (message.startsWith("/youtube")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/youtube ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().youtubeName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> youtubeEntry : LabyHelp.getInstace().getUserHandler().youtubeName.entrySet()) {
                        if (youtubeEntry.getKey().equals(uuid)) {
                            labyPlayer.openYoutube(youtubeEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein Youtube hinterlegt!");
                }
            } else if (message.startsWith("/twitch")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/twitch ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().twitchName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> twitchEntry : LabyHelp.getInstace().getUserHandler().twitchName.entrySet()) {
                        if (twitchEntry.getKey().equals(uuid)) {
                            labyPlayer.openTwitch(twitchEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein Twitch hinterlegt!");
                }
            } else if (message.startsWith("/twitter")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/twitter ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().twitterName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> twitterEntry : LabyHelp.getInstace().getUserHandler().twitterName.entrySet()) {
                        if (twitterEntry.getKey().equals(uuid)) {
                            labyPlayer.openTwitter(twitterEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein Twitter hinterlegt!");
                }
            } else if (message.startsWith("/tiktok")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/tiktok ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (LabyHelp.getInstace().getUserHandler().tiktokName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> twitterEntry : LabyHelp.getInstace().getUserHandler().tiktokName.entrySet()) {
                        if (twitterEntry.getKey().equals(uuid)) {
                            labyPlayer.openTikTok(twitterEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein TikTok hinterlegt!");
                }
            } else if (message.startsWith("/snapchat")) {
                final UUID uuid = UUIDFetcher.getUUID(message.replaceAll("/snapchat ", ""));
                LabyHelp.getInstace().getUserHandler().readSocialMedia();
                if (!LabyHelp.getInstace().getUserHandler().snapchatName.containsKey(uuid)) {
                    for (final Map.Entry<UUID, String> snapchatEntry : LabyHelp.getInstace().getUserHandler().snapchatName.entrySet()) {
                        if (snapchatEntry.getKey().equals(uuid)) {
                            labyPlayer.sendSnapchat(snapchatEntry.getValue());
                        }
                    }
                } else {
                    labyPlayer.sendMessage("Der Spieler hat nicht sein SnapChat hinterlegt!");
                }
            } else if (message.startsWith("/lhban")) {
                String[] components = message.split(" ");
                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    if (components.length == 2) {
                        final UUID uuid = UUIDFetcher.getUUID(components[1]);
                        if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                            if (uuid != null) {
                                labyPlayer.sendMessage(EnumChatFormatting.RED + "Der Spieler " + EnumChatFormatting.WHITE + components[1] + EnumChatFormatting.RED + " wurde wegen dem NAMETAG fuer ein Tag gebannt!");
                                WebServer.sendBanned(uuid, "NAMETAG");
                            } else {
                                labyPlayer.sendMessage("Der Spieler existiert nicht!");
                            }
                        } else {
                            labyPlayer.sendMessage("Der Spieler ist im LabyHelp Team!");
                        }
                    } else {
                        labyPlayer.sendMessage("Bitte benutze /lhban <Spieler>");
                    }
                } else {
                    labyPlayer.sendNoPerms();
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

                                        LabyHelp.getInstace().getUserHandler().sendLike(LabyMod.getInstance().getPlayerUUID(), uuid);

                                        LabyHelp.getInstace().getUserHandler().readUserLikes();
                                        LabyHelp.getInstace().getUserHandler().readLikes();

                                        labyPlayer.sendMessage(EnumChatFormatting.WHITE + "You have liked " + EnumChatFormatting.DARK_RED + components[1].toUpperCase() + EnumChatFormatting.RED + "!");
                                        if (LabyHelp.getInstace().getUserHandler().getLikes(uuid).equalsIgnoreCase("1")) {
                                            labyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has now only " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Like!");
                                        } else {
                                            labyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has now " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Likes!");
                                        }
                                    } else {
                                        labyPlayer.sendMessage("This Player does not have LabyHelp!");
                                    }
                                } else {
                                    labyPlayer.sendMessage("This Player does not exist");
                                }
                            } else {
                                labyPlayer.sendMessage("This Player does not exist");
                            }
                        } else {
                            labyPlayer.sendMessage("You have already liked " + EnumChatFormatting.WHITE + components[1]);
                        }
                    } else {
                        labyPlayer.sendMessage("You can't like yourself!");
                    }
                } else {
                    labyPlayer.sendMessage("Please use /lhlike <Player>");
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
                                    labyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has only " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Like!");
                                } else {
                                    labyPlayer.sendMessage(EnumChatFormatting.WHITE + components[1].toUpperCase() + " has " + LabyHelp.getInstace().getUserHandler().getLikes(uuid) + " Likes!");
                                }
                            } else {
                                labyPlayer.sendMessage("This Player does not have LabyHelp!");
                            }
                        }
                    });

                } else {
                    labyPlayer.sendMessage("Please use /likes <Player>");
                }
            } else if (message.startsWith("/likelist")) {
                LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getUserHandler().getTops();

                        int i = 1;

                        for (Map.Entry<String, Integer> uuidStringEntry : list) {
                            labyPlayer.sendMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Likes");
                            i++;
                        }
                    }
                });
            } else if (message.startsWith("/social")) {
                final String decode = message.replaceAll("/social ", "");
                final UUID uuid = UUIDFetcher.getUUID(decode);
                labyPlayer.openSocial(uuid, decode);
            } else if (message.startsWith("/lhreload")) {
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "The LabyHelp addon has been reloaded!");
                try {
                    LabyHelp.getInstace().getExecutor().submit(new Runnable() {
                        @Override
                        public void run() {
                            LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                            LabyHelp.getInstace().getGroupManager().updateNameTag(false);

                            LabyHelp.getInstace().getUserHandler().isLiked.clear();
                            LabyHelp.getInstace().getUserHandler().readUserLikes();
                            LabyHelp.getInstace().getUserHandler().readLikes();

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
                    labyPlayer.sendMessage(EnumChatFormatting.WHITE + "Your Version: " + LabyHelp.currentVersion + " (newest)");
                }
                if (LabyHelp.getInstace().isNewerVersion) {
                    labyPlayer.sendMessage(EnumChatFormatting.WHITE + "Your Version: " + LabyHelp.currentVersion + " (old)");
                    labyPlayer.sendMessage(EnumChatFormatting.RED + "Newest Version: " + webVersion);
                    labyPlayer.sendMessage(EnumChatFormatting.RED + "Restart your game to update your version!");
                }
                labyPlayer.sendMessage(EnumChatFormatting.WHITE + "Our Teamspeak: https://labyhelp.de/teamspeak");
                labyPlayer.sendMessage(EnumChatFormatting.WHITE + "Our Discord: https://labyhelp.de/discord");
            } else if (message.startsWith("/nametag")) {
                LabyMod.getInstance().openWebpage("https://labyhelp.de/tag-rules", false);
                labyPlayer.sendMessage("Die Regel Seite hat sich automatisch geoeffnet");
                labyPlayer.sendMessage("Nichts geoeffnet? https://labyhelp.de/tag-rules");
            } else if (message.startsWith("/lhteam")) {
                labyPlayer.sendMessage(EnumChatFormatting.RED + "LabyHelp Team:");
                labyPlayer.sendMessage(EnumChatFormatting.RED + "Position: " + LocalDate.now());
                labyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Administation (3)");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Marvio");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Connan97");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Darstellung");
                labyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Developers (2)");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Rufi");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Rausgemoved");
                labyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Moderation (4)");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- reszyy");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Parodie");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- ObiiiTooo");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Regelt");
                labyPlayer.sendMessage(EnumChatFormatting.YELLOW + "Addon Contents (3)");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- Vortrag");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- ogdarkeagle");
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "- kleinerblue ");
                labyPlayer.sendMessage(EnumChatFormatting.DARK_RED + "You can also see the team page here: " + EnumChatFormatting.WHITE + " https://labyhelp.de/");
            }
            if (message.equalsIgnoreCase("/LhHelp")) {
                labyPlayer.sendMessage("- /bandana <player>");
                labyPlayer.sendMessage("- /cape <player>");
                labyPlayer.sendMessage("- /skin <player>");
                labyPlayer.sendMessage("- /cosmeticsC <player>");
                labyPlayer.sendMessage("- /insta <player>");
                labyPlayer.sendMessage("- /discord <player>");
                labyPlayer.sendMessage("- /youtube <player>");
                labyPlayer.sendMessage("- /twitch <player>");
                labyPlayer.sendMessage("- /tiktok <player>");
                labyPlayer.sendMessage("- /twitter <player>");
                labyPlayer.sendMessage("- /snapchat <player>");
                labyPlayer.sendMessage("- /social <player>");
                //    labyPlayer.sendMessage("- /lhignore <player>");
                labyPlayer.sendMessage("- /nametag");
                labyPlayer.sendMessage("- /lhteam");
                labyPlayer.sendMessage("- /lhlike <player> / Like a Player");
                labyPlayer.sendMessage("- /likes <player> / See Player likes");
                labyPlayer.sendMessage("- /likelist / Shows the most famous player");
                labyPlayer.sendMessage("- /lhreload");
                labyPlayer.sendMessage("- /labyhelp");

                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    labyPlayer.sendMessage("- /lhban <player> / Only NameTag");
                    labyPlayer.sendMessage("- /lhweb <key> / <null>");
                }

            } else if (message.startsWith("/lhweb")) {
                String[] components = message.split(" ");
                if (components.length == 2) {
                    if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                        LabyMod.getInstance().openWebpage("https://marvhuelsmann.de/linkto.php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&ip=" + LabyHelp.getInstace().getUserHandler().getIp() + "&key=" + components[1], false);
                    } else {
                        labyPlayer.sendNoPerms();
                    }
                } else {
                    labyPlayer.sendMessage("- /lhweb <key> / <null>");
                }
            }
        } else {
            labyPlayer.sendMessage("You have deactivated the Addon!");
        }
    }
}