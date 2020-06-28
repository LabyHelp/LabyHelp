package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Random;
import java.util.UUID;


public class LabyAddon extends net.labymod.api.LabyModAddon {

    private boolean AddonEnable = true;
    private boolean AddonHelpMessage = true;

    private boolean DanceChat = false;

    private boolean StickerChat = false;

    private boolean DanceOtherId = false;
    private UUID danceUUID = UUID.randomUUID();


    private UUID getUUID(String name) {
        UUID uuid;
        uuid = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(name).getId();
        return uuid;
    }

    private int getEmoteInRange() {
        Random r = new Random();
        return r.nextInt((170) + 1);
    }

    private int getStickerInRange() {
        Random r = new Random();
        return r.nextInt((37) + 1);
    }


    @Override
    public void onEnable() {
        final LabyPlayer labyPlayer = new LabyPlayer();
        this.getApi().registerForgeListener(false);

        this.getApi().registerModule(new TexturePackModule());

        LabyMod.getInstance().getLabyModAPI().updateCurrentGamemode("LabyAddon");

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new BandanaMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CosmeticsClearerMenu());

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new DanceMenu());


        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                if (AddonHelpMessage) {
                    LabyMod.getInstance().notifyMessageRaw("LabyAddon | Help", "Benutze /LAhelp um alle Befehle zu sehen!");
                }
            }
        });


        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String message) {
                if (AddonEnable) {
                    if (DanceChat) {
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(LabyMod.getInstance().getPlayerUUID(), Short.parseShort(message));
                        DanceChat = false;
                        return true;
                    } else if (DanceOtherId) {
                        labyPlayer.sendMessage("Der Spieler Tanzt nun!");
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(danceUUID, Short.parseShort(message));
                        DanceOtherId = false;
                        return true;
                    } else if (StickerChat) {
                        LabyMod.getInstance().getStickerRegistry().handleSticker(LabyMod.getInstance().getUserManager().getUser(LabyMod.getInstance().getPlayerUUID()), Short.parseShort(message));
                        StickerChat = false;
                        return true;
                    }


                    if (message.startsWith("/bandana")) {
                        //SPACE MUST BE THERE
                        UUID uuid = getUUID(message.replaceAll("/bandana ", ""));
                        labyPlayer.openBandanaUrl(uuid);
                        return true;
                    } else if (message.startsWith("/cape")) {
                        //SPACE MUST BE THERE
                        UUID uuid = getUUID(message.replaceAll("/cape " , ""));
                        labyPlayer.openCapeUrl(uuid);
                        return true;
                    } else if (message.startsWith("/skin")) {
                        //SPACE MUST BE THERE
                        UUID uuid = getUUID(message.replaceAll("/skin ", ""));
                        labyPlayer.openSkin(uuid);
                        return true;
                    } else if (message.startsWith("/dance")) {
                        //SPACE MUST BE THERE
                        UUID uuid = getUUID(message.replaceAll("/dance ", ""));
                        labyPlayer.sendMessage("Schreibe nun die Tanz Id in den Chat");
                        danceUUID = uuid;
                        DanceOtherId = true;
                        return true;
                    } else if (message.startsWith("/cosmeticsCC")) {
                        UUID uuid = getUUID(message.replaceAll("/cosmeticsCC ", ""));
                        LabyMod.getInstance().getUserManager().getUser(uuid).getCosmetics().clear();
                        return true;
                    }


                    if (message.equalsIgnoreCase("/danceself")) {
                        short randomeEmote = (short) getEmoteInRange();
                        LabyMod.getInstance().getEmoteRegistry().handleEmote(LabyMod.getInstance().getPlayerUUID(), randomeEmote);
                        return true;
                    } else if (message.equalsIgnoreCase("/stickerself")) {
                        short randomeSticker = (short) getStickerInRange();
                        LabyMod.getInstance().getStickerRegistry().handleSticker(LabyMod.getInstance().getUserManager().getUser(LabyMod.getInstance().getPlayerUUID()), randomeSticker);
                        return true;
                    } else if (message.equalsIgnoreCase("/playdance")) {
                        labyPlayer.sendMessage("Schreibe nun die Tanz ID in den Chat!");
                        DanceChat = true;
                        return true;
                    } else if (message.equalsIgnoreCase("/playsticker")) {
                        labyPlayer.sendMessage("Schreibe nun die Sticker ID in den Chat!");
                        StickerChat = true;
                        return true;
                    }


                    if (message.equalsIgnoreCase("/LAhelp")) {
                        labyPlayer.sendMessage("- /bandana <Spieler>");
                        labyPlayer.sendMessage("- /cape <Spieler>");
                        labyPlayer.sendMessage("- /skin <Spieler>");
                        labyPlayer.sendMessage("- /danceself | Spiele einen Zuffaelligen Emote ab");
                        labyPlayer.sendMessage("- /stickerself | Spiele einen Zuffaelligen Sticker ab");
                        labyPlayer.sendMessage("- /dance <Spieler>");
                        labyPlayer.sendMessage("- /cosmeticsCC <Spieler>");
                        labyPlayer.sendMessage("- /playdance | Spiele einen ausgewaehlten Emote ab");
                        labyPlayer.sendMessage("- /playsticker | Spiele einen ausgewaehlten Sticker ab");
                        labyPlayer.sendMessage("Alle Tänze und Sticker Ids hier: https://marvhuelsmann.de/labyaddon");
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


        list.add(booleanElement);
        list.add(booleanElement2);
    }
}
