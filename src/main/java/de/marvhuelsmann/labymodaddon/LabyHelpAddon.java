package de.marvhuelsmann.labymodaddon;

import de.marvhuelsmann.labymodaddon.menu.*;
import de.marvhuelsmann.labymodaddon.module.TexturePackModule;
import de.marvhuelsmann.labymodaddon.util.Commands;
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
import java.util.UUID;


public class LabyHelpAddon extends net.labymod.api.LabyModAddon {

    public static boolean AddonEnable = true;
    private boolean AddonHelpMessage = true;


    @Override
    public void onEnable() {
        this.getApi().registerForgeListener(false);

        this.getApi().registerModule(new TexturePackModule());


        LabyMod.getInstance().getLabyModAPI().updateCurrentGamemode("LabyHelp");


        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new BandanaMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CapeMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new SkinMenu());
        LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(new CosmeticsClearerMenu());



        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                if (AddonHelpMessage) {
                    LabyMod.getInstance().notifyMessageRaw("LabyAddon | Help", "Use /LhHelp for all Commands");
                }
            }
        });


        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String message) {
                if (message.startsWith("/bandana") || message.startsWith("/cape") || message.startsWith("/skin") || message.startsWith("cosmeticsCC") || message.equalsIgnoreCase("/LhHelp")) {
                    Commands.CommandMessage(message);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void loadConfig() {
        AddonEnable = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
        this.AddonHelpMessage = !this.getConfig().has("enable") || this.getConfig().get("enable").getAsBoolean();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        final BooleanElement booleanElement = new BooleanElement("Addon activated", new ControlElement.IconData(Material.GOLD_INGOT), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                AddonEnable = enable;

                LabyHelpAddon.this.getConfig().addProperty("enable", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, AddonEnable);

        final BooleanElement booleanElement2 = new BooleanElement("Join help message", new ControlElement.IconData(Material.REDSTONE_COMPARATOR), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyHelpAddon.this.AddonHelpMessage = enable;

                LabyHelpAddon.this.getConfig().addProperty("enable", enable);
                LabyHelpAddon.this.saveConfig();
            }
        }, this.AddonHelpMessage);


        list.add(booleanElement);
        list.add(booleanElement2);
    }
}
