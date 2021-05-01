package de.labyhelp.addon.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.enums.LabyVersion;
import de.labyhelp.addon.util.settings.SettingsManager;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommunicatorHandler {

    public final String SERVER_ID = "4ed1f46bbe04bc756bcb17c0c7ce3e4632f06a48";

    public final Map<UUID, String> isOnline = new HashMap<>();

    public void sendClient(String sip) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://sessionserver.mojang.com/session/minecraft/join");
            httpPost.setHeader("Content-Type", "application/json");

            JsonObject request = new JsonObject();
            request.addProperty("accessToken",
                    LabyHelp.getInstance().getVersionHandler().isGameVersion(LabyVersion.ONE_TWELVE) ?
                            LabyMod.getInstance().getAccountManager().getUserAccount(LabyMod.getInstance().getPlayerUUID()).getAccessToken()
                            : Minecraft.getMinecraft().getSession().getToken());
            request.addProperty("selectedProfile", LabyMod.getInstance().getPlayerId());
            request.addProperty("serverId", SERVER_ID);
            httpPost.setEntity(new StringEntity(new Gson().toJson(request)));

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 204) {
                LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/auth.php?username=" + URLEncoder.encode(LabyMod.getInstance().getPlayerName(), "UTF-8") + "&sip=" + URLEncoder.encode(sip, "UTF-8") + "&clversion=" + URLEncoder.encode(SettingsManager.currentVersion, "UTF-8") + "&mcversion=" + LabyHelp.getInstance().getVersionHandler().getGameVersion().getVersionName());
                LabyHelp.getInstance().sendDeveloperMessage("register player: " + LabyMod.getInstance().getPlayerName() + " with sip: " + sip + " in version" + LabyHelp.getInstance().getVersionHandler().getGameVersion().getVersionName());

            } else {
                if (LabyHelp.getInstance().getSettingsManager().settingsAdversting) {
                    LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                    labyPlayer.sendTranslMessage("main.verify");
                    throw new IllegalStateException("Could not authenticate with mojang sessionserver!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch client!", e);
        }
    }

    public void readGroups() {
        for (final String entry : LabyHelp.getInstance().getRequestManager().getRequest("https://marvhuelsmann.de/database.php")) {
            final String[] data = entry.split(":");
            if (data.length == 2 && HelpGroups.isExist(data[1])) {
                String uuid = data[0];
                if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {

                    LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.valueOf(data[1]));

                    if (!LabyHelp.getInstance().getGroupManager().isTeam(UUID.fromString(uuid))
                            && !LabyHelp.getInstance().getGroupManager().isPremiumExtra(UUID.fromString(uuid))
                            && !LabyHelp.getInstance().getGroupManager().isBanned(UUID.fromString(uuid))) {
                        if (Integer.parseInt(LabyHelp.getInstance().getInviteManager().getInvites(UUID.fromString(uuid))) >= 25) {
                            LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.PREMIUM_);
                        } else if (Integer.parseInt(LabyHelp.getInstance().getInviteManager().getInvites(UUID.fromString(uuid))) >= 10) {
                            LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.INVITER);
                        }

                        if (LabyHelp.getInstance().getLikeManager().getFamousLikePlayer().toString().equals(uuid)) {
                            LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.FAMOUS);
                        } else {
                            List<Map.Entry<String, Integer>> list = LabyHelp.getInstance().getLikeManager().getTops5();
                            for (Map.Entry<String, Integer> uuidStringEntry : list) {
                                if (uuidStringEntry.getKey().equalsIgnoreCase(uuid)) {
                                    LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.FAME);
                                }
                            }
                        }
                    }

                    //adding the targets to the Target Mode
                   if (LabyHelp.getInstance().getSettingsManager().getTargetPlayers().containsKey(UUID.fromString(data[0]))) {
                       LabyHelp.getInstance().getGroupManager().userGroups.put(UUID.fromString(data[0]), HelpGroups.TARGET);
                   }

                }
            }
        }
        LabyHelp.getInstance().sendDeveloperMessage("called method: readGroups");
    }

    public void readFastStart() {

        LabyHelp.getInstance().getNameTagManager().readNameTags();
        LabyHelp.getInstance().getTagManager().readServerPartner();

        readUserInformations(true);
        LabyHelp.getInstance().getTagManager().initTagManager();

        LabyHelp.getInstance().sendDeveloperMessage("readFast finish");
    }


    public void readUserInformations(boolean groups) {
        if (groups) {

            for (Map.Entry<UUID, HelpGroups> group : LabyHelp.getInstance().getGroupManager().userGroups.entrySet()) {
                LabyHelp.getInstance().getGroupManager().oldGroups.put(group.getKey(), group.getValue());
            }

            for (Map.Entry<UUID, String> likes : LabyHelp.getInstance().getLikeManager().userLikes.entrySet()) {
                LabyHelp.getInstance().getLikeManager().oldLikes.put(likes.getKey(), likes.getValue());
            }


            for (Map.Entry<UUID, String> likes : LabyHelp.getInstance().getInviteManager().userInvites.entrySet()) {
                LabyHelp.getInstance().getInviteManager().oldInvites.put(likes.getKey(), likes.getValue());
            }

            LabyHelp.getInstance().sendDeveloperMessage("called method: readUserInformations");

            LabyHelp.getInstance().getLikeManager().readUserLikes();
            LabyHelp.getInstance().getLikeManager().readLikes();
            LabyHelp.getInstance().sendDeveloperMessage("Like refresh");
            LabyHelp.getInstance().getInviteManager().readUserInvites();

            LabyHelp.getInstance().getTagManager().readServerPartner();
            LabyHelp.getInstance().getPartnerHandler().readPartner();

            LabyHelp.getInstance().getInviteManager().readOldPlayer();
            readGroups();
            LabyHelp.getInstance().getTagManager().initTagManager();

            TranslationManager translationManager = LabyHelp.getInstance().getTranslationManager();

            if (!LabyHelp.getInstance().getGroupManager().getNowRanked().getName().equalsIgnoreCase(LabyHelp.getInstance().getGroupManager().getBeforeRanked().getName())) {
                if (LabyHelp.getInstance().getGroupManager().getNowRanked().equals(HelpGroups.BANNED)) {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§f" + " ---------LabyHelp----------");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§c" + " Your NameTag has been banned for one day");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§e" + translationManager.getTranslation("main.rules") + ": https://labyhelp.de/tag-rules");
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§f" + " ---------LabyHelp----------");

                } else {
                    LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§a" + translationManager.getTranslation("main.rankchange") + " (" + "§f" + LabyHelp.getInstance().getGroupManager().getNowRanked().getName() + "§a" + ")");
                }
            }

            if (!LabyHelp.getInstance().getLikeManager().getBeforeLikes().equalsIgnoreCase(LabyHelp.getInstance().getLikeManager().getNowLikes())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§a" + translationManager.getTranslation("main.profilelike") + " (" + "§f" + LabyHelp.getInstance().getLikeManager().getNowLikes() + "§a" + ")");
            }

            if (!LabyHelp.getInstance().getInviteManager().getBeforeInvites().equalsIgnoreCase(LabyHelp.getInstance().getInviteManager().getNowInvites())) {
                LabyMod.getInstance().displayMessageInChat(LabyPlayer.prefix + "§a" + translationManager.getTranslation("main.predeemedcode") + " (" + "§f" + LabyHelp.getInstance().getInviteManager().getNowInvites() + "§a" + ")");
            }

        }
        LabyHelp.getInstance().getNameTagManager().readNameTags();
    }
}