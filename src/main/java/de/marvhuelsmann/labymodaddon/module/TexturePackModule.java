package de.marvhuelsmann.labymodaddon.module;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;

public class TexturePackModule extends SimpleModule {

    private String getTexurePack() {
       for (ResourcePackRepository.Entry entry : Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries()) {
           if (entry.getResourcePackName() == null) {
               return "Default";
           } else {
               return entry.getResourcePackName();
           }
       }
        return "Default";
    }

    @Override
    public String getDisplayName() {
        return "TexurePack";
    }

    @Override
    public String getDisplayValue() {
        return getTexurePack();
    }

    @Override
    public String getDefaultValue() {
        return String.valueOf(0);
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.GRASS);
    }

    @Override
    public void loadSettings() {
    }

    @Override
    public String getSettingName() {
        return "TexurePack name";
    }

    @Override
    public String getDescription() {
        return "Shows your TexurePack name";
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
