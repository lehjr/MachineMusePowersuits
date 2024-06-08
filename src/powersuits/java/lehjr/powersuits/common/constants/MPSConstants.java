package lehjr.powersuits.common.constants;

import net.minecraft.resources.ResourceLocation;

public class MPSConstants {
    /**
     * Mod ------------------------------------------------------------------------------------------------------------
     */
    public static final String MOD_ID = "powersuits";
    public static final String CREATIVE_TAB_TRANSLATION_KEY = MOD_ID + ".creative.tab";
    /**
     * Common Config --------------------------------------------------------------------------------------------------
     */
    public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";

    // General --------------------------------------------------------------------------------------------------------
    public static final String CONFIG_PREFIX_GENERAL = CONFIG_PREFIX + "general.";
    public static final String CONFIG_GENERAL_MAX_FLYING_SPEED = CONFIG_PREFIX_GENERAL + "maxFlyingSpeed";


    // Modules --------------------------------------------------------------------------------------------------------
    // Armor
    public static final String ARMOR_VALUE_PHYSICAL = "armorPhysical";



    public static final String ARMOR_POINTS = "armorPoints";


    public static final String ARMOR_VALUE_ENERGY = "armorEnergy";
    public static final String ARMOR_ENERGY_CONSUMPTION = "armorEnergyPerDamage";
    public static final String KNOCKBACK_RESISTANCE = "knockbackResistance";
    public static final String ARMOR_TOUGHNESS = "armorToughness";
    public static final String MODULE_FIELD_STRENGTH = "fieldStrength";




    /**
     * Client Config --------------------------------------------------------------------------------------------------
     */

    // HUD ----------------------------------------------------------------------------------------
    public static final String CONFIG_PREFIX_HUD = CONFIG_PREFIX + "hud.";
    public static final String CONFIG_HUD_DISPLAY_KB_HUD = CONFIG_PREFIX_HUD + "DisplayKBHUD";
    public static final String CONFIG_HUD_KEYBIND_HUD_X = CONFIG_PREFIX_HUD + "Xposition";
    public static final String CONFIG_HUD_KEYBIND_HUD_Y = CONFIG_PREFIX_HUD + "Yposition";
    public static final String CONFIG_HUD_USE_GRAPHICAL_METERS = CONFIG_PREFIX_HUD + "useGraphicalMeters";
    public static final String CONFIG_HUD_USE_24_HOUR_CLOCK = CONFIG_PREFIX_HUD + "use24HrClock";





    /**
     * Registry Names -------------------------------------------------------------------------------------------------
     */
    // Modules --------------------------------------------------------------------------------------------------------
    // Armor
    public static final ResourceLocation IRON_PLATING_MODULE = getRegName("plating_iron");
    public static final ResourceLocation DIAMOND_PLATING_MODULE = getRegName("plating_diamond");
    public static final ResourceLocation NETHERITE_PLATING_MODULE = getRegName("plating_netherite");
    public static final ResourceLocation ENERGY_SHIELD_MODULE = getRegName("energy_shield");





    public static ResourceLocation getRegName(String regNameString) {
        return new ResourceLocation(MPSConstants.MOD_ID, regNameString);
    }
}
