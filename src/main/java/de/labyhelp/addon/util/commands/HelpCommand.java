package de.labyhelp.addon.util.commands;

import de.labyhelp.addon.LabyPlayer;

public interface HelpCommand {
    public String getName();
    public void execute(LabyPlayer labyPlayer, String[] args);
}