package de.marvhuelsmann.labymodaddon.util.commands;

import de.marvhuelsmann.labymodaddon.LabyPlayer;

public interface HelpCommand {
    public String getName();
    public void execute(LabyPlayer labyPlayer, String[] args);
}