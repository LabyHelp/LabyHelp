package de.marvhuelsmann.labymodaddon.commands.feature;

import de.marvhuelsmann.labymodaddon.LabyHelp;
import de.marvhuelsmann.labymodaddon.LabyPlayer;
import de.marvhuelsmann.labymodaddon.util.commands.HelpCommand;
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
        LabyHelp.getInstace().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<Map.Entry<String, Integer>> list = LabyHelp.getInstace().getLikeManager().getTops5();

                int i = 1;

                for (Map.Entry<String, Integer> uuidStringEntry : list) {
                    labyPlayer.sendDefaultMessage(EnumChatFormatting.YELLOW + "" + i + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + UUIDFetcher.getName(UUID.fromString(uuidStringEntry.getKey())).toUpperCase() + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.YELLOW + EnumChatFormatting.BOLD + uuidStringEntry.getValue() + EnumChatFormatting.WHITE + " Likes");
                    i++;
                }
            }
        });
    }
}
