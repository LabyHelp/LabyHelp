package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.UUID;

public class Commands {

   /* private static Map<UUID, String> instaMap;
    private static Map<UUID, String> discordMap;
    private static Map<UUID, String> twitterMap;
    private static Map<UUID, String> youtubeMap;
    private static Map<UUID, String> twitchMap;
    private static Map<UUID, String> tiktokMap;
    private static Map<UUID, String> snapchatMap;
    */

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

                final UUID uuid = UUIDFetcher.getUUID(components[1]);
                if (LabyHelp.getInstace().getGroupManager().isTeam(LabyMod.getInstance().getPlayerUUID())) {
                    if (!LabyHelp.getInstace().getGroupManager().isTeam(uuid)) {
                        if (uuid != null) {
                            if (components[2] != null) {
                                labyPlayer.sendMessage(EnumChatFormatting.RED + "Der Spieler " + EnumChatFormatting.WHITE + components[1] + EnumChatFormatting.RED + " wurde wegen " + EnumChatFormatting.WHITE + components[2] + EnumChatFormatting.RED + " fuer ein Tag gebannt!");
                                WebServer.sendBanned(uuid, components[2]);
                            } else {
                                labyPlayer.sendMessage("Bitte benutze /lhban <Spieler> <Grund>");
                            }
                        } else {
                            labyPlayer.sendMessage("Der Spieler existiert nicht!");
                        }
                    } else {
                        labyPlayer.sendMessage("Der Spieler ist im LabyHelp Team!");
                    }
                } else {
                    labyPlayer.sendNoPerms();
                }

            } else if (message.startsWith("/social")) {
                final String decode = message.replaceAll("/social ", "");
                final UUID uuid = UUIDFetcher.getUUID(decode);
                labyPlayer.openSocial(uuid, decode);
            } else if (message.startsWith("/lhreload")) {
                labyPlayer.sendMessage(EnumChatFormatting.GREEN + "Das LabyHelp Addon wurde neugeladen!");
                try {
                    LabyHelp.getInstace().getUserHandler().isOnline.clear();
                    //LabyHelp.getInstace().getUserHandler().readIsOnline();
                    LabyHelp.getInstace().getGroupManager().updateSubTitles(true);
                    LabyHelp.getInstace().getGroupManager().updateNameTag(false);
                    System.out.println("subtitles updating..");
                    final String webVersion = WebServer.readVersion();
                    LabyHelp.getInstace().isNewerVersion = !webVersion.equalsIgnoreCase(LabyHelp.currentVersion);
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
                labyPlayer.sendMessage("- /lhreload");
                labyPlayer.sendMessage("- /labyhelp");
            }
        } else {
            labyPlayer.sendMessage("You have deactivated the Addon!");
        }
    }
}