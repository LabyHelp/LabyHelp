package de.labyhelp.addon.util.commands;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.LabyPlayer;
import net.labymod.main.LabyMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    private final List<HelpCommand> commands = new ArrayList<>();

    public void registerCommand(HelpCommand helpCommand) {
        commands.add(helpCommand);
    }

    public void registerCommand(HelpCommand... helpCommands) {
        commands.addAll(Arrays.asList(helpCommands));
    }

    public boolean executeCommand(String command) {
        String rawCommand = command.substring(1);
        String[] args = rawCommand.split(" ");

        for (HelpCommand helpCommand : commands) {
            if (rawCommand.split(" ")[0].equalsIgnoreCase(helpCommand.getName())) {
                LabyHelp.getInstance().sendDeveloperMessage("execute command: " + command);

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