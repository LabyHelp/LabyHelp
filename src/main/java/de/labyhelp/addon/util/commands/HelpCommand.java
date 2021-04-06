package de.labyhelp.addon.util.commands;

import de.labyhelp.addon.LabyPlayer;

public interface HelpCommand {
    String getName();
    void execute(LabyPlayer labyPlayer, String[] args);
}