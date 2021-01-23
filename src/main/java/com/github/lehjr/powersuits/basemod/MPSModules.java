package com.github.lehjr.powersuits.basemod;

import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import net.minecraft.util.ResourceLocation;
import com.github.lehjr.numina.constants.NuminaConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of module registry names of modules that can be installed in the armor and power fist.
 * Used in the installation/removal Gui.
 */
public enum MPSModules {
    INSTANCE;

    protected List<ResourceLocation> moduleRegNames = new ArrayList<ResourceLocation>() {{
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_BASIC__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ELITE__REGNAME));
        add(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME));
        add(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.WORKBENCH));

        add(new ResourceLocation("clock"));
        add(new ResourceLocation("compass"));
        add(new ResourceLocation("crafting_table"));
    }};

    public void addModule(ResourceLocation regName) {
        moduleRegNames.add(regName);
    }

    public List<ResourceLocation> getModuleRegNames () {
        return moduleRegNames;
    }
}