package de.labyhelp.addon.commands.feature;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import de.labyhelp.addon.util.commands.HelpCommand;
import net.labymod.utils.UUIDFetcher;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LikeListCMD implements HelpCommand {
    @Override
    public String getName() {
        return "likelist";
    }

    @Override
    public void execute(LabyPlayer labyPlayer, String[] args) {
        LabyHelp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<Map.Entry<String, Integer>> list = LabyHelp.getInstance().getLikeManager().getTops5();

                int i = 1;

                for (Map.Entry<String, Integer> uuidStringEntry : list) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Likes");
                    i++;
                }
            }
        });
    }
}
