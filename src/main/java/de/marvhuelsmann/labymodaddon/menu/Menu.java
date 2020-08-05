package de.marvhuelsmann.labymodaddon.menu;

import de.marvhuelsmann.labymodaddon.LabyHelpAddon;
import net.labymod.ingamechat.tools.playermenu.PlayerMenu;
import net.labymod.main.LabyMod;

public class Menu {

    public static BandanaMenu bandanaMenu = new BandanaMenu();
    public static CapeMenu capeMenu = new CapeMenu();
    public static CosmeticsClearerMenu cosmeticsClearerMenu = new CosmeticsClearerMenu();
    public static SkinMenu skinMenu = new SkinMenu();

    public static SocialMediaMenu social = new SocialMediaMenu();

    public static void refreshMenu() {


/*   for (int i = 0; i < LabyMod.getInstance().getChatToolManager().getPlayerMenu().size(); i++) {
       PlayerMenu.PlayerMenuEntry playerMenu = LabyMod.getInstance().getChatToolManager().getPlayerMenu().get(i);

       if (playerMenu instanceof LabyHelpMenu) {
           LabyMod.getInstance().getChatToolManager().getPlayerMenu().remove(playerMenu);
       }
   }*/

        LabyMod.getInstance().getChatToolManager().getPlayerMenu().clear();

        if (LabyHelpAddon.AddonEnable) {
            if (LabyHelpAddon.bandanaMenu) {
                LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(bandanaMenu);
            }
            if (LabyHelpAddon.capeMenu) {
                LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(capeMenu);
            }
            if (LabyHelpAddon.skinMenu) {
                LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(skinMenu);
            }
            if (LabyHelpAddon.cosmeticMenu) {
                LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(cosmeticsClearerMenu);
            }
            LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(social);

            /*     if (LabyHelpAddon.instaMenu) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(instaMenu);
                }
                if (LabyHelpAddon.cosmeticMenu) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(cosmeticsClearerMenu);
                }
                if (LabyHelpAddon.discordMenu) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(discordMenu);
                }
                if (LabyHelpAddon.youtubeMenu) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(youtubeMenu);
                }
                if (LabyHelpAddon.twitchMenu) {
                    LabyMod.getInstance().getChatToolManager().getPlayerMenu().add(twitchMenu);
                }

                */
        }
    }
}
