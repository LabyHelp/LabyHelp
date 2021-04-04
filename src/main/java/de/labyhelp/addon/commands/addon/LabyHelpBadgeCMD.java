package de.labyhelp.addon.commands.addon;

import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.enums.Tags;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.minecraft.util.EnumChatFormatting;

public class LabyHelpBadgeCMD implements HelpCommand {
    @Override
    public String getName() {
        return "lhbadge";
    }

    private int i = 0;

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        labyPlayer.sendTranslMessage("main.badges.current");
        for (Tags tags : Tags.values()) {
            if (!tags.equals(Tags.NOTHING)) {
                if (tags.getArray().contains(labyPlayer.getUuid())) {
                    i++;
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.GREEN + "- " + tags.getRequestName());
                }
            }
        }
        if (i == 0) {
            labyPlayer.sendAlertTranslMessage("main.badges.null");
        }
    }
}
