package de.marvhuelsmann.labymodaddon;

import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;

import java.util.ArrayList;
import java.util.List;


public class CommunityAddon extends net.labymod.api.LabyModAddon {

    private boolean AddonEnable = true;
    private boolean AddonHelpMessage = true;


    private String prefix = "§7[§6Community-Addon§7] §7";

    private boolean TrustChat = false;

    private ArrayList<String> TrustPlayer = new ArrayList<String>();

    @Override
    public void onEnable() {
        System.out.println("Enable Addon");
        this.getApi().registerForgeListener(false);
        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {

            @Override
            public void accept(ServerData serverData) {
                if (AddonEnable) {
                    if (serverData.getIp().equalsIgnoreCase("gommehd.net") || serverData.getIp().equalsIgnoreCase("vip.gommehd.net")) {
                        LabyMod.getInstance().displayMessageInChat(prefix + "§fYeah du bist auf GommeHD.net!");
                        if (AddonHelpMessage) {
                            LabyMod.getInstance().displayMessageInChat(prefix + "§fBenutze §6#help§f um alle Funktionen nutzen zu können!");
                        }
                    } else {
                        if (AddonEnable) {
                            LabyMod.getInstance().displayMessageInChat(prefix + "§fDu kannst auch auf diesem Server §6#help§f eingeben um hier alle Funktionen nutzen zu können!");
                        }
                    }
                }
            }
        });


        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String message) {
                if (AddonEnable) {
                    if (TrustChat) {
                        LabyMod.getInstance().displayMessageInChat(prefix + "§fDer Spieler§6 " + message + "§f ist nun getrustet!");
                        TrustPlayer.add(message);
                        TrustChat = false;
                        return true;
                    }

                    if (message.equalsIgnoreCase("#help")) {
                        LabyMod.getInstance().displayMessageInChat(prefix + "§fHier siest du alle Commands:");
                        LabyMod.getInstance().displayMessageInChat(prefix + "§6- §f#trust §8| §7Um ein Spieler in deiner Sitzung als Freund zu markieren.");
                        LabyMod.getInstance().displayMessageInChat(prefix + "§6- §f#trustlist §8| §7Sehe deine getrusteten Spieler in deiner Sitzung.");
                        //  LabyMod.getInstance().displayMessageInChat(prefix + "§6- §f#connect §8| §7Um auf dem GommeHD.net Server zu joinen.");
                        LabyMod.getInstance().displayMessageInChat(prefix + "§fInformation: §7Nach der Zeit werden hier mehr Sachen stehen..");
                        return true;
                    } else if (message.equalsIgnoreCase("#trust")) {
                        TrustChat = true;
                        LabyMod.getInstance().displayMessageInChat(prefix + "§fSchreibe nun denn Spieler in Chat den du Trusten möchtest!");
                        return true;
                    } else if (message.equalsIgnoreCase("#trustlist")) {
                        for (String trustList : TrustPlayer) {
                            if (!TrustPlayer.isEmpty()) {
                                LabyMod.getInstance().displayMessageInChat(prefix + "§fDeine getrusteten Spieler sind:§6 " + trustList);
                            } else {
                                LabyMod.getInstance().displayMessageInChat(prefix + "§cDu hast momentan keine Trust Spieler!");
                            }
                        }
                        return true;
                    } /*else if (message.equalsIgnoreCase("#connect")) {
                        if (!LabyMod.getInstance().getCurrentServerData().getIp().equalsIgnoreCase("gommehd.net") || !LabyMod.getInstance().getCurrentServerData().getIp().equalsIgnoreCase("vip.gommehd.net")) {
                            LabyMod.getInstance().connectToServer("gommehd.net");
                        }
                        return true;
                    } */
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
                CommunityAddon.this.AddonEnable = enable;

                CommunityAddon.this.getConfig().addProperty("enable", enable);
                CommunityAddon.this.saveConfig();
            }
        }, this.AddonEnable);

        final BooleanElement booleanElement2 = new BooleanElement("Hilfe beitritts Nachricht", new ControlElement.IconData(Material.REDSTONE_COMPARATOR), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                CommunityAddon.this.AddonHelpMessage = enable;

                CommunityAddon.this.getConfig().addProperty("enable", enable);
                CommunityAddon.this.saveConfig();
            }
        }, this.AddonHelpMessage);

        list.add(booleanElement);
        list.add(booleanElement2);
    }
}
