package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.NameTags;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class NameTagManager {

    public NameTags currentNameTag = NameTags.RANK;
    private Boolean updateNameTagFinish = false;

    public void readNameTags() {
        readNameTag();
        readSecondNameTag();

        LabyHelp.getInstance().sendDeveloperMessage("method: readNameTags in NameTagManager");
    }

    public void updateNameTag(boolean readDatabase, boolean firstNameTag) {
        if (readDatabase) {
            if (LabyHelp.getInstance().getSettingsManager().onServer) {
                LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(false);
            }
            return;
        }

        if (!LabyHelp.getInstance().getSettingsManager().seeNameTags) {
            return;
        }

        if (updateNameTagFinish) {
            return;
        }

        updateNameTagFinish = true;

        if (!LabyHelp.getInstance().getCommunicatorHandler().userNameTags.isEmpty() || !LabyHelp.getInstance().getCommunicatorHandler().userSecondNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                String name = firstNameTag ? LabyHelp.getInstance().getCommunicatorHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null) : LabyHelp.getInstance().getCommunicatorHandler().userSecondNameTags.getOrDefault(uuidUserEntry.getKey(), null);


                if (LabyHelp.getInstance().getGroupManager().isBanned(uuidUserEntry.getKey(), false)) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                    if (LabyHelp.getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                    return;
                }

                if (name != null) {
                    String finalTag = name.replace("&", "§");
                    String rainbow = finalTag.replace("!r", "" + LabyHelp.getInstance().getGroupManager().randomeColor() + "");

                    if (!LabyHelp.getInstance().getGroupManager().isTag(uuidUserEntry.getKey())) {
                        String tag = rainbow.replaceAll("LabyHelp", "CENSORED");
                        String finishFinalTag = tag.replaceAll("LabyMod", "CENSORED");

                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + finishFinalTag);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + rainbow);
                    }

                    if (LabyHelp.getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }
            }
        }
        updateNameTagFinish = false;
    }

    private void readNameTag() {
        try {

            LabyHelp.getInstance().sendDeveloperMessage("called method: readNameTag first");

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
                    LabyHelp.getInstance().getCommunicatorHandler().userNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read NameTag!", e);
        }
    }


    private void readSecondNameTag() {
        try {

            LabyHelp.getInstance().sendDeveloperMessage("called method: readNameTag second");

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
                    LabyHelp.getInstance().getCommunicatorHandler().userSecondNameTags.put(UUID.fromString(data[0]), data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read second NameTag!", e);
        }
    }

    private NameTags moveNameTags() {

        if (currentNameTag == NameTags.RANK) {
            LabyHelp.getInstance().getGroupManager().updateSubTitles(false);
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
            LabyHelp.getInstance().getGroupManager().updateSubTitles(false);
            currentNameTag = NameTags.RANK;
            return NameTags.RANK;
        }
    }

    public void updateCurrentNameTagRealTime() {
        if (currentNameTag == NameTags.RANK) {
            LabyHelp.getInstance().getGroupManager().updateSubTitles(false);
        } else if (currentNameTag == NameTags.FIRST_NAMETAG) {
            updateNameTag(false, true);
        } else if (currentNameTag == NameTags.SECOND_NAMETAG) {
            updateNameTag(false, false);
        } else {
            LabyHelp.getInstance().getGroupManager().updateSubTitles(false);
        }
    }


    public boolean updateNameTags(Integer currentValue) {

        if (LabyHelp.getInstance().getSettingsManager().translationLoaded) {
            updateCurrentNameTagRealTime();

            Integer chooseSeconds = LabyHelp.getInstance().getSettingsManager().nameTagSwitchingSetting * 15;

            if (LabyHelp.getInstance().getSettingsManager().isOnServer()) {
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
