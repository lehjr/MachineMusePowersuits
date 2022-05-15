package com.lehjr.numina.common.constants;

import net.minecraft.resources.ResourceLocation;

public class NuminaConstants {
    // Mod
    public static final String MOD_ID = "numina";

    // Tooltips
    public static final String TOOLTIP_ENERGY = "tooltip.numina.battery.energy";

    // String for overheat damage
    public static final String OVERHEAT_DAMAGE = "Overheat";

    // Config
    public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";
    public static final String CONFIG_USE_FOV_FIX = CONFIG_PREFIX + "useFOVFix";
    public static final String CONFIG_USE_FOV_NORMALIZE = CONFIG_PREFIX + "normalizeFOV";
    public static final String CONFIG_FOV_FIX_DEAULT_STATE = CONFIG_PREFIX + "FOVFixDefaultState";
    public static final String CONFIG_USE_SOUNDS = CONFIG_PREFIX + "useSounds";
    public static final String CONFIG_DEBUGGING_INFO = CONFIG_PREFIX + "useDebuggingInfo";
    public static final String CONFIG_PREFIX_RECIPES = CONFIG_PREFIX + "recipes.";
    public static final String CONFIG_RECIPES_USE_VANILLA = CONFIG_PREFIX_RECIPES + "useVanilla";

    public static final ResourceLocation TEXTURE_WHITE_SHORT = new ResourceLocation(NuminaConstants.MOD_ID, "models/white");
    public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/white.png");
    public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/armorstand2.png");

    // Misc
    public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "gui/glass.png");
    public static final ResourceLocation LOCATION_NUMINA_GUI_TEXTURE_ATLAS = new ResourceLocation(RESOURCE_PREFIX +"atlas/gui.png");
    public static final String BLANK_ARMOR_MODEL_PATH = TEXTURE_PREFIX + "item/armor/blankarmor.png";
    public static final ResourceLocation WEAPON_SLOT_BACKGROUND = new ResourceLocation(TEXTURE_PREFIX + "gui/weapon.png");
}
