package de.labyhelp.addon.util;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.enums.HelpGroups;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GroupManager {

    public boolean rainbow = false;

    public boolean isPremium(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getPremium();
    }

    public boolean isPremiumExtra(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getPremiumExtra();
    }

    public boolean isTeam(final UUID uuid) {
        if (LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            LabyHelp.getInstance().getCommunicatorHandler().readGroups();
        }
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getTeam();
    }

    public HelpGroups getRanked(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid);
    }

    public boolean isTag(final UUID uuid) {
        return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).getTag();
    }

    public static boolean isBanned(final UUID uuid, Boolean database) {
        if (database) {
            LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(true);
        }

        if (!LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            return LabyHelp.getInstance().getCommunicatorHandler().userGroups.containsKey(uuid) && LabyHelp.getInstance().getCommunicatorHandler().userGroups.get(uuid).equals(HelpGroups.BANNED);
        }
        return false;
    }


    private void setNormalTag(UUID uuid, HelpGroups group) {
        LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(group.getPrefix());
    }

    private void seTagWithMore(UUID uuid, HelpGroups group) {
        if (LabyHelp.getInstance().getServerManager().tagList.containsKey(uuid)) {
            if (LabyHelp.getInstance().getServerManager().tagList.get(uuid).equals("NORMAL")) {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(EnumChatFormatting.GRAY + "✪ " + group.getPrefix());
            } else if (LabyHelp.getInstance().getServerManager().tagList.get(uuid).equals("CHROME")) {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(randomeColor() + "✪ " + group.getPrefix());
            } else {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(group.getPrefix());
            }
        }
    }

    private void setServerTagWithTag(UUID uuid, HelpGroups group) {
        if (LabyHelp.getInstance().getServerManager().hasServer.containsKey(uuid)) {
            if (LabyHelp.getInstance().getServerManager().tagList.get(uuid).equals("NORMAL")) {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(EnumChatFormatting.GRAY + "✪ " + group.getPrefix() + EnumChatFormatting.DARK_AQUA + " Ⓢ");
            } else if (LabyHelp.getInstance().getServerManager().tagList.get(uuid).equals("CHROME")) {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(randomeColor() + "✪ " + group.getPrefix() + EnumChatFormatting.DARK_AQUA + " Ⓢ");
            } else {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(group.getPrefix() + EnumChatFormatting.DARK_AQUA + " Ⓢ");
            }
        }
    }

    private void setServerTag(UUID uuid, HelpGroups group) {
        if (LabyHelp.getInstance().getServerManager().hasServer.containsKey(uuid)) {
                LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle( group.getPrefix() + EnumChatFormatting.DARK_AQUA + " Ⓢ");
        }
    }


    public void updateSubTitles(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstance().getSettingsManager().onServer) {
                LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(true);
            }
            return;
        }

        if (!LabyHelp.getInstance().getSettingsManager().seeNameTags) {
            return;
        }


        if (!LabyHelp.getInstance().getCommunicatorHandler().userGroups.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                HelpGroups group = LabyHelp.getInstance().getCommunicatorHandler().userGroups.getOrDefault(uuidUserEntry.getKey(), null);
                if (group != null) {
                    // if (LabyHelp.getInstace().getUserHandler().isOnline.get(uuidUserEntry.getKey()).equalsIgnoreCase("ONLINE")) {


                    if (LabyHelp.getInstance().getServerManager().tagList.containsKey(uuidUserEntry.getKey())
                            && LabyHelp.getInstance().getServerManager().hasServer.containsKey(uuidUserEntry.getKey())) {
                        setServerTagWithTag(uuidUserEntry.getKey(), group);
                    } else if (LabyHelp.getInstance().getServerManager().hasServer.containsKey(uuidUserEntry.getKey())) {
                        setServerTag(uuidUserEntry.getKey(), group);
                    } else if (LabyHelp.getInstance().getServerManager().tagList.containsKey(uuidUserEntry.getKey())) {
                        seTagWithMore(uuidUserEntry.getKey(), group);
                    } else {
                        setNormalTag(uuidUserEntry.getKey(), group);
                    }


                    if (LabyHelp.getInstance().getSettingsManager().nameTagSize != 0) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(LabyHelp.getInstance().getSettingsManager().nameTagSize);
                    } else {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(1);
                    }

                    // }
                }

                if (LabyHelp.getInstance().getSettingsManager().targetMode) {
                    if (LabyHelp.getInstance().getSettingsManager().targetList.contains(uuidUserEntry.getKey())) {
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "TARGET");
                        LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(2);
                    }
                }

            }
        }

    }

    public void updateNameTag(boolean readDatabase) {
        if (readDatabase) {
            if (LabyHelp.getInstance().getSettingsManager().onServer) {
                LabyHelp.getInstance().getCommunicatorHandler().readUserInformations(false);
            }
            return;
        }

        if (!LabyHelp.getInstance().getSettingsManager().seeNameTags) {
            return;
        }

        if (!LabyHelp.getInstance().getCommunicatorHandler().userNameTags.isEmpty()) {
            for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {

                String name = LabyHelp.getInstance().getCommunicatorHandler().userNameTags.getOrDefault(uuidUserEntry.getKey(), null);

                if (isBanned(uuidUserEntry.getKey(), false)) {
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
                    String finalRo = finalTag.replace("{likes}", LabyHelp.getInstance().getLikeManager().getLikes(uuidUserEntry.getKey()));
                    String rainbow = finalRo.replace("!r", "" + randomeColor() + "");

                    if (!isTag(uuidUserEntry.getKey())) {
                        String tag = rainbow.replaceAll("LabyHelp", "");
                        String finishFinalTag = tag.replaceAll("LabyMod", "");

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

                if (LabyHelp.getInstance().getSettingsManager().targetMode) {
                    if (LabyHelp.getInstance().getSettingsManager().targetList.contains(uuidUserEntry.getKey())) {
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
