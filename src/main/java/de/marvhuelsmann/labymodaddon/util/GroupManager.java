package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GroupManager {

    public boolean rainbow = false;

    public boolean isPremium(final UUID uuid) {
        return LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid) && LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid).getPremium();
    }

    public boolean isPremiumExtra(final UUID uuid) {
        return LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid) && LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid).getPremiumExtra();
    }

    public boolean isTeam(final UUID uuid) {
        if (LabyHelp.getInstace().getCommunicationManager().userGroups.isEmpty()) {
            LabyHelp.getInstace().getCommunicationManager().readGroups();
        }
        return LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid) && LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid).getTeam();
    }

    public HelpGroups getRanked(final UUID uuid) {
        return LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid);
    }

    public boolean isTag(final UUID uuid) {
        return LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid) && LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid).getTag();
    }

    public static boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
        }

        if (!LabyHelp.getInstace().getCommunicationManager().userGroups.isEmpty()) {
            return LabyHelp.getInstace().getCommunicationManager().userGroups.containsKey(uuid) && LabyHelp.getInstace().getCommunicationManager().userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }


    public void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getCommunicationManager().readUserInformations(true);
            }
            return;
        }


        if (!LabyHelp.getInstace().getCommunicationManager().userGroups.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                HelpGroups group = LabyHelp.getInstace().getCommunicationManager().userGroups.getOrDefault(uuidUserEntry.getKey(), null);
                if (group != null) {
                    // if (LabyHelp.getInstace().getUserHandler().isOnline.get(uuidUserEntry.getKey()).equalsIgnoreCase("ONLINE")) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(group.getPrefix());
                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }

                    // }
                }

                if (LabyHelp.getInstace().targetMode) {
                    if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "TARGET");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(2);
                    }
                }

            }
        }
    }

    public void updateNameTag(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstace().onServer) {
                LabyHelp.getInstace().getCommunicationManager().readUserInformations(false);
            }
            return;
        }

        if (!LabyHelp.getInstace().getCommunicationManager().userNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                String name = LabyHelp.getInstace().getCommunicationManager().userNameTags.getOrDefault(uuidUserEntry.getKey(), null);

                if (isBanned(uuidUserEntry.getKey(), false)) {
                    LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle("CENSORED");
                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                    return;
                }

                if (name != null) {
                    String finalTag = name.replace("&", "§");
                    String finalRo = finalTag.replace("{likes}", LabyHelp.getInstace().getLikeManager().getLikes(uuidUserEntry.getKey()));
                    String rainbow = finalRo.replace("!r", "" + randomeColor() + "");

                    if (!isTag(uuidUserEntry.getKey())) {
                        String tag = rainbow.replaceAll("LabyHelp", "");
                        String finishFinalTag = tag.replaceAll("LabyMod", "");

                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + finishFinalTag);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.WHITE + rainbow);
                    }

                    if (LabyHelp.getInstace().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstace().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }
                }

                if (LabyHelp.getInstace().targetMode) {
                    if (LabyHelp.getInstace().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "TARGET");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(2);
                    }
                }

            }
        }
    }

    private final Random RANDOM = new Random();
    private EnumChatFormatting chooseColor;

    private int getRandomNumberInRange() {
        return RANDOM.nextInt((11 - 1) + 1) + 1;
    }

    private EnumChatFormatting randomeColor() {
        if (getRandomNumberInRange() == 1) {
            chooseColor(EnumChatFormatting.YELLOW);
        } else if (getRandomNumberInRange() == 2) {
            return chooseColor(EnumChatFormatting.BLUE);
        } else if (getRandomNumberInRange() == 3) {
            return chooseColor(EnumChatFormatting.RED);
        } else if (getRandomNumberInRange() == 4) {
            return chooseColor(EnumChatFormatting.AQUA);
        } else if (getRandomNumberInRange() == 5) {
            return chooseColor(EnumChatFormatting.DARK_GREEN);
        } else if (getRandomNumberInRange() == 6) {
            return chooseColor(EnumChatFormatting.DARK_PURPLE);
        } else if (getRandomNumberInRange() == 7) {
            return chooseColor(EnumChatFormatting.DARK_GREEN);
        } else if (getRandomNumberInRange() == 8) {
            return chooseColor(EnumChatFormatting.GOLD);
        } else if (getRandomNumberInRange() == 9) {
            return chooseColor(EnumChatFormatting.GREEN);
        } else if (getRandomNumberInRange() == 10) {
            return chooseColor(EnumChatFormatting.LIGHT_PURPLE);
        } else if (getRandomNumberInRange() == 11) {
            return chooseColor(EnumChatFormatting.DARK_AQUA);
        }
        return randomeColor();
    }

    private EnumChatFormatting chooseColor(EnumChatFormatting color) {
        if (rainbow) {
            chooseColor = color;
            rainbow = false;

            return color;
        } else {
            if (chooseColor != null) {
                return chooseColor;
            } else {
                chooseColor = color;
                return color;
            }
        }
    }
}
