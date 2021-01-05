package de.labyhelp.addon.commands.target;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
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
                if (LabyHelp.getInstance().getSettingsManager().targetList.contains(uuid)) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You remove " + args[1] + " from your local Target List");

                    if (LabyHelp.getInstance().getSettingsManager().targetMode) {
                        if (LabyHelp.getInstance().getSettingsManager().targetList.contains(uuid)) {
                            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitle(null);
                            LabyMod.getInstance().getUserManager().getUser(uuid).setSubTitleSize(1);
                        }
                    }

                    LabyHelp.getInstance().getSettingsManager().targetList.remove(uuid);

                } else {
                    LabyHelp.getInstance().getSettingsManager().targetList.add(uuid);
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.RED + "You added " + args[1] + " to your local Target List");
                }
            } else {
                labyPlayer.sendTranslMessage("main.not.exist");
            }
        } else {
            labyPlayer.sendDefaultMessage("- /lhtarget -" + LabyHelp.getInstance().getTranslationManager().getTranslation("player"));
        }
    }
}
