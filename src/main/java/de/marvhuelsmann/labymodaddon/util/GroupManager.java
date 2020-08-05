package de.marvhuelsmann.labymodaddon.util;

import de.marvhuelsmann.labymodaddon.enums.HelpGroups;
import net.labymod.api.LabyModAPI;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.UUID;

public class GroupManager implements RenderEntityEvent {

    static {
        GroupManager.groupsMap = WebServer.readGroups();
        GroupManager.nickMap = WebServer.readNick();
    }

    private static Map<UUID, HelpGroups> groupsMap;
    private static Map<UUID, String> nickMap;

    public static boolean isPremium(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getPremium();
    }

    public static boolean isTeam(final UUID uuid) {
        return GroupManager.groupsMap.containsKey(uuid) && GroupManager.groupsMap.get(uuid).getTeam();
    }

    public static void updateSubTitles() {
        GroupManager.groupsMap = WebServer.readGroups();
        GroupManager.nickMap = WebServer.readNick();

        System.out.println(LabyMod.getInstance().getUserManager().getUsers().entrySet());
        for (Map.Entry<UUID, User> uuidUserEntry : LabyMod.getInstance().getUserManager().getUsers().entrySet()) {
            HelpGroups group = groupsMap.getOrDefault(uuidUserEntry.getKey(), null);

            if (group != null) {
                String nick = nickMap.getOrDefault(uuidUserEntry.getKey(), null);

                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitle(
                        nick != null && nick.equalsIgnoreCase("true")
                                ? (groupsMap.getOrDefault(LabyMod.getInstance().getPlayerUUID(), HelpGroups.USER).getTeam()
                                    ? HelpGroups.NICK.getPrefix()
                                    : HelpGroups.USER.getPrefix())
                                : group.getPrefix()
                );
                LabyMod.getInstance().getUserManager().getUser(uuidUserEntry.getKey()).setSubTitleSize(0.9);
            }
        }
    }

    @Override
    public void onRender(Entity entity, double v, double v1, double v2, float v3) {

    }
}
