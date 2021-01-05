package de.marvhuelsmann.labymodaddon.util.commands;

import de.marvhuelsmann.labymodaddon.LabyPlayer;
import net.labymod.main.LabyMod;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private static final List<HelpCommand> commands = new ArrayList<>();

    public void registerCommand(HelpCommand helpCommand) {
        commands.add(helpCommand);
    }

    public boolean executeCommand(String command) {
        String rawCommand = command.substring(1); // Raw command: Command: *help RawCommand: help
        String[] args = rawCommand.split(" ");

        for (HelpCommand helpCommand : commands) {
            if (rawCommand.split(" ")[0].equalsIgnoreCase(helpCommand.getName())) {
                LabyPlayer labyPlayer = new LabyPlayer(LabyMod.getInstance().getPlayerUUID());
                helpCommand.execute(labyPlayer, args);
                return true;
            }
        }
        return false;
    }

    public List<HelpCommand> getCommands() {
        return commands;
    }
}