package de.marvhuelsmann.labymodaddon.module;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class DegreeModule extends SimpleModule {

    private String getDegree() {
        if (Minecraft.getMinecraft().thePlayer.rotationPitch == 90) {
            return EnumChatFormatting.RED + String.valueOf(Minecraft.getMinecraft().thePlayer.rotationPitch);
        } else if (Minecraft.getMinecraft().thePlayer.rotationPitch == -90) {
            return EnumChatFormatting.RED + String.valueOf(Minecraft.getMinecraft().thePlayer.rotationPitch);
        } else if (Minecraft.getMinecraft().thePlayer.rotationPitch < 1 && Minecraft.getMinecraft().thePlayer.rotationPitch > -1) {
            return EnumChatFormatting.GREEN + String.valueOf(Minecraft.getMinecraft().thePlayer.rotationPitch);
        } else if (Minecraft.getMinecraft().thePlayer.rotationPitch < 81 && Minecraft.getMinecraft().thePlayer.rotationPitch > 74) {
            return EnumChatFormatting.YELLOW + String.valueOf(Minecraft.getMinecraft().thePlayer.rotationPitch);
        } else {
            return String.valueOf(Minecraft.getMinecraft().thePlayer.rotationPitch);
        }
    }

    @Override
    public String getDisplayName() {
        return "Degree";
    }

    @Override
    public String getDisplayValue() {
        return getDegree();
    }

    @Override
    public String getDefaultValue() {
        return String.valueOf(0);
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.INK_SACK);
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public String getSettingName() {
        return "Degree amount";
    }

    @Override
    public String getDescription() {
        return "Shows your Degree amount";
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
