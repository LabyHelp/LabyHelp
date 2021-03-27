package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import de.labyhelp.addon.enums.NameTags;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.labyhelp.addon.LabyHelp.getInstance;

public class NameTagManager {

    public NameTags currentNameTag = NameTags.RANK;
    private Boolean updateNameTagFinish = false;


    public void readNameTags() {
        readNameTag();
        readSecondNameTag();

        getInstance().sendDeveloperMessage("method: readNameTags in NameTagManager");
    }

    public String getDiscoNameTag(String n) {

        StringBuilder finalEndTag = new StringBuilder();
        for (char ch : n.toCharArray()) {
            finalEndTag.append(getInstance().getGroupManager().randomColor(true)).append(ch);
        }

        return finalEndTag.toString();
    }

    public void updateNameTag(boolean readDatabase, boolean firstNameTag) {
        if (readDatabase) {
            if (getInstance().getSettingsManager().onServer) {
                getInstance().getCommunicatorHandler().readUserInformations(false);
            }
            return;
        }

        if (!getInstance().getSettingsManager().seeNameTags) {
            return;
        }

        if (updateNameTagFinish) {
            return;
        }

        updateNameTagFinish = true;

        if (!getInstance().getCommunicatorHandler().userNameTags.isEmpty() || !getInstance().getCommunicatorHandler().userSecondNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                String name = firstNameTag
                        ? getInstance().getCommunicatorHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null)
                        :  getInstance().getCommunicatorHandler().userSecondNameTags.getOrDefault(uuidUserEntry.getKey(), null) == null

                        ? getInstance().getCommunicatorHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null)
                        : getInstance().getCommunicatorHandler().userSecondNameTags.getOrDefault(uuidUserEntry.getKey(), null);


                if (getInstance().getGroupManager().isBanned(uuidUserEntry.getKey(), false)) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                    if (getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                    return;
                }

                if (name != null) {
                    String finalTag = name.replace("&", "§");
                    String rainbow = finalTag.replace("!r", "" + getInstance().getGroupManager().randomColor(false));

                    String disco = rainbow.replace("!d" + rainbow.replace("!d", ""),
                            getDiscoNameTag(rainbow.replace("!d", "")));

                    if (!getInstance().getGroupManager().isTag(uuidUserEntry.getKey())) {
                        String finishFinalTag = disco.replace("LabyMod", "CENSORED");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + finishFinalTag);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + disco);
                    }


                    if (getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }
            }
        }
        updateNameTagFinish = false;
    }


    public void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            if (getInstance().getSettingsManager().onServer) {
                getInstance().getCommunicatorHandler().readUserInformations(true);
            }
            return;
        }

        if (!getInstance().getSettingsManager().seeNameTags) {
            return;
        }

        if (!getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                HelpGroups group = getInstance().getCommunicatorHandler().userGroups.getOrDefault(uuidUserEntry.getKey(), null);
                if (group != null) {
                    getInstance().getTagManager().setNormalTag(uuidUserEntry.getKey(), group);

                    if (getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }

            }
        } else {
            LabyMod.getInstance().getUserManager().getUser(LabyMod.getInstance().getPlayerUUID()).setSubTitle("LabyHelp");
        }

    }

    private void readNameTag() {
        try {

            getInstance().sendDeveloperMessage("called method: readNameTag first");

            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/nametags.php?which=FIRST_NAMETAG").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));

            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    getInstance().getCommunicatorHandler().userNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read NameTag!", e);
        }
    }


    private void readSecondNameTag() {
        try {

            getInstance().sendDeveloperMessage("called method: readNameTag second");

            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/nametags.php?which=SECOND_NAMETAG").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));

            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data.length == 2) {
                    getInstance().getCommunicatorHandler().userSecondNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read second NameTag!", e);
        }
    }

    private NameTags moveNameTags() {

        if (currentNameTag == NameTags.RANK) {
            updateSubTitles(false);
            currentNameTag = NameTags.FIRST_NAMETAG;
            return NameTags.FIRST_NAMETAG;

        } else if (currentNameTag == NameTags.FIRST_NAMETAG) {
            updateNameTag(false, true);
            currentNameTag = NameTags.SECOND_NAMETAG;
            return NameTags.SECOND_NAMETAG;

        } else if (currentNameTag == NameTags.SECOND_NAMETAG) {
            updateNameTag(false, false);
            currentNameTag = NameTags.RANK;
            return NameTags.RANK;

        } else {
            updateSubTitles(false);
            currentNameTag = NameTags.RANK;
            return NameTags.RANK;
        }
    }

    public void updateCurrentNameTagRealTime() {
        if (currentNameTag == NameTags.RANK) {
            updateSubTitles(false);
        } else if (currentNameTag == NameTags.FIRST_NAMETAG) {
            updateNameTag(false, true);
        } else if (currentNameTag == NameTags.SECOND_NAMETAG) {
            updateNameTag(false, false);
        } else {
            updateSubTitles(false);
        }
    }

    public boolean updateNameTags(Integer currentValue) {

        if (getInstance().getSettingsManager().translationLoaded) {
            updateCurrentNameTagRealTime();

            Integer chooseSeconds = getInstance().getSettingsManager().nameTagSwitchingSetting * 15;

            if (getInstance().getSettingsManager().isOnServer()) {
                if (currentValue > chooseSeconds) {
                    return true;
                } else if (currentValue == 1) {
                    moveNameTags();
                }

            }
        }

        return false;
    }
}
