package de.marvhuelsmann.labymodaddon;

import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;


import java.util.List;
import java.util.Random;
import java.util.UUID;


public class LabyAddon extends net.labymod.api.LabyModAddon {

    private boolean AddonEnable = true;
    private boolean AddonHelpMessage = true;
    private boolean NameTagged = true;


    private boolean BandanaChat = false;
    private boolean CapeChat = false;
    private boolean DanceChat = false;
    private boolean SkinChat = false;
    private boolean CosmeticClearer = false;

    private boolean DanceOtherName = false;
    private boolean DanceOtherId = false;
    private UUID danceUUID = UUID.randomUUID();
    private final int help = 0;

    public static UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    private static int getRandomNumberInRange() {
        Random r = new Random();
        return r.nextInt((170) + 1);
    }


    @Override
    public void onEnable() {
        final LabyPlayer labyPlayer = new LabyPlayer();

        this.getApi().registerForgeListener(false);

        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                if (AddonHelpMessage) {
                    LabyMod.getInstance().notifyMessageRaw("LabyAddon | Help", "Benutze #help um alle Befehle zu sehen!");
                }
            }
        });

        this.getApi().getEventManager().register(new MessageReceiveEvent() {
            @Override
            public boolean onReceive(String s, String s1) {

                if (NameTagged) {
                    if (s.equalsIgnoreCase(LabyMod.getInstance().getPlayerName()) || s.equalsIgnoreCase(LabyMod.getInstance().getPlayerName())) {
                        LabyMod.getInstance().notifyMessageRaw("Namen getagged", s1);
                    }
                }

                return false;
            }
        });


        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String message) {
                if (AddonEnable) {
                    if (BandanaChat) {
                        UUID uuid = getUUID(message);
                        labyPlayer.openBandanaUrl(uuid);
                        BandanaChat = false;
                        return true;
                    } else if (CapeChat) {
                        UUID uuid = getUUID(message);
                        labyPlayer.openCapeUrl(uuid);
                        CapeChat = false;
                        return true;
                    } else if (SkinChat) {
                        UUID uuid = getUUID(message);
                        labyPlayer.openSkin(uuid);
                        SkinChat = false;
                        return true;
                    } else if (DanceChat) {
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(LabyMod.getInstance().getPlayerUUID(), Short.parseShort(message));
                        DanceChat = false;
                        return true;
                    } else if (CosmeticClearer) {
                        labyPlayer.sendMessage("Der Spieler hat nun keine Cosmetics mehr!");
                        LabyMod.getInstance().getUserManager().getUser(getUUID(message)).getCosmetics().clear();
                        CosmeticClearer = false;
                        return true;
                    } else if (DanceOtherName) {
                        labyPlayer.sendMessage("Schreibe nun die Tanz Id in den Chat");
                        danceUUID = getUUID(message);
                        DanceOtherName = false;
                        DanceOtherId = true;
                        return true;
                    } else if (DanceOtherId) {
                        labyPlayer.sendMessage("Der Spieler Tanzt nun!");
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(danceUUID, Short.parseShort(message));
                        DanceOtherId = false;
                        return true;
                    }

                    if (message.equalsIgnoreCase("#bandana")) {
                        BandanaChat = true;
                        labyPlayer.sendMessage("Schreibe nun den Spielernamen in den Chat!");
                        return true;
                    } else if (message.equalsIgnoreCase("#cape")) {
                        CapeChat = true;
                        labyPlayer.sendMessage("Schreibe nun den Spielernamen in den Chat!");
                        return true;
                    } else if (message.equalsIgnoreCase("#skin")) {
                        SkinChat = true;
                        labyPlayer.sendMessage("Schreibe nun den Spielernamen in den Chat!");
                        return true;
                    } else if (message.equalsIgnoreCase("#danceself")) {
                        short randomeEmote = (short) getRandomNumberInRange();
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(LabyMod.getInstance().getPlayerUUID(), randomeEmote);
                        return true;
                    } else if (message.equalsIgnoreCase("#danceother")) {
                        labyPlayer.sendMessage("Schreibe nun den Spielernamen in den Chat!");
                        DanceOtherName = true;
                        return true;
                    } else if (message.equalsIgnoreCase("#playdance")) {
                        labyPlayer.sendMessage("Schreibe nun die Tanz ID in den Chat!");
                        DanceChat = true;
                        return true;
                    } else if (message.equalsIgnoreCase("#invisibleCos")) {
                        CosmeticClearer = true;
                        labyPlayer.sendMessage("Schreibe nun den Spielernamen in den Chat!");
                        return true;
                    }


                    if (message.equalsIgnoreCase("#help")) {
                        labyPlayer.sendMessage("- #bandana");
                        labyPlayer.sendMessage("- #cape");
                        labyPlayer.sendMessage("- #skin");
                        labyPlayer.sendMessage("- #danceself | Spiele einen Zuffälligen Emote ab");
                        labyPlayer.sendMessage("- #danceother");
                        labyPlayer.sendMessage("- #invisibleCos");
                        labyPlayer.sendMessage("- #playdance | Spiele einen ausgewähl");
                        labyPlayer.sendMessage("Alle Tanz Ids hier: https://marvhuelsmann.de/labyaddon");
                        return true;
                    }
                } else {
                    labyPlayer.sendMessage(EnumChatFormatting.RED + "Du hast das LabyAddon deaktiviert!");
                }
                return false;
            }
        });
    }

    @Override
    public void loadConfig() {
        this.AddonEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        this.AddonHelpMessage = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        final BooleanElement booleanElement = new BooleanElement("Addon Aktiviert", new ControlElement.IconData(Material.GOLD_INGOT), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyAddon.this.AddonEnable = enable;

                LabyAddon.this.getConfig().addProperty("enable", enable);
                LabyAddon.this.saveConfig();
            }
        }, this.AddonEnable);

        final BooleanElement booleanElement2 = new BooleanElement("Hilfe beitritts Nachricht", new ControlElement.IconData(Material.REDSTONE_COMPARATOR), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyAddon.this.AddonHelpMessage = enable;

                LabyAddon.this.getConfig().addProperty("enable", enable);
                LabyAddon.this.saveConfig();
            }
        }, this.AddonHelpMessage);

        final BooleanElement booleanElement3 = new BooleanElement("Namen Taggen Nachricht", new ControlElement.IconData(Material.SIGN), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyAddon.this.NameTagged = enable;

                LabyAddon.this.getConfig().addProperty("enable", enable);
                LabyAddon.this.saveConfig();
            }
        }, this.NameTagged);

        list.add(booleanElement);
        list.add(booleanElement2);
        list.add(booleanElement3);
    }
}
