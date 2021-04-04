package de.labyhelp.addon.enums;

import de.labyhelp.addon.LabyHelp;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public enum TagsSide {

    RIGHT(LabyHelp.getInstance().getTagManager().rightPlayerList),
    LEFT(LabyHelp.getInstance().getTagManager().leftPlayerList);

    private final HashMap<UUID, Tags> map;

    TagsSide(HashMap<UUID, Tags> map) {
        this.map = map;
    }
}
