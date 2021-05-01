package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class IdCMD implements HelpCommand {

    @Override
    public String getName() {
        return "labyhelpid";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(() -> {
            String code = LabyHelp.getInstance().getRequestManager().getVanillaRequest("https://labyhelp.de/idcode.php?uuid="  + LabyMod.getInstance().getPlayerUUID());

            labyPlayer.sendDefaultMessage("§f" + LabyHelp.getInstance().getTranslationManager().getTranslation("main.token.show") + EnumChatFormatting.BOLD + code);
            labyPlayer.sendTranslMessage("main.token.info");

            StringSelection stringSelection = new StringSelection(code);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            labyPlayer.sendAlertTranslMessage("main.token.clipboard");

        });

    }
}
