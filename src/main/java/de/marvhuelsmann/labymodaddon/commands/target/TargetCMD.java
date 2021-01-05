package de.marvhuelsmann.labymodaddon.commands.target;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
import net.labymod.main.LabyMod;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.UUID;

public class TargetCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhtarget";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        if (args.length == 2) {
            UUID uuid = UUIDFetcher.getUUID(args[1]);

            if (uuid != null) {
                if (LabyHelp.getInstace().getSettingsManger().targetList.contains(uuid)) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You remove " + args[1] + " from your local Target List");

                    if (LabyHelp.getInstace().getSettingsManger().targetMode) {
                        if (LabyHelp.getInstace().getSettingsManger().targetList.contains(uuid)) {
                            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(null);
                            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitleSize(1);
                        }
                    }

                    LabyHelp.getInstace().getSettingsManger().targetList.remove(uuid);

                } else {
                    LabyHelp.getInstace().getSettingsManger().targetList.add(uuid);
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You added " + args[1] + " to your local Target List");
                }
            } else {
                labyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            labyPlayer.sendDefaultMessage("- /lhtarget -" + LabyHelp.getInstace().getTranslationManager().getTranslation("player"));
        }
    }
}
