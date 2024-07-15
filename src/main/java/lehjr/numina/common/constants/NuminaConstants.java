package lehjr.numina.common.constants;

import net.minecraft.resources.ResourceLocation;

public class NuminaConstants {
    /**
     * Mod ----------------------------------------------------------------------------------------
     */
    public static final String MOD_ID = "numina";
    public static final String CREATIVE_TAB_TRANSLATION_KEY = MOD_ID + ".creative.tab";
    public static final String RESOURCE_PREFIX = MOD_ID + ":";

    /**
     * Common Config ------------------------------------------------------------------------------
     */
    public static final String CONFIG_IS_ALLOWED = "isAllowed";
    public static final String CONFIG_MAX_TRANSFER = "maxEnergyTransfer";
    public static final String CONFIG_MAX_ENERGY = "maxEnergy";

    /**
     * Client Config ------------------------------------------------------------------------------
     */
    public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";
    public static final String CONFIG_USE_FOV_FIX = CONFIG_PREFIX + "useFOVFix";
    public static final String CONFIG_USE_FOV_NORMALIZE = CONFIG_PREFIX + "normalizeFOV";
    public static final String CONFIG_FOV_FIX_DEAULT_STATE = CONFIG_PREFIX + "FOVFixDefaultState";
    public static final String CONFIG_USE_SOUNDS = CONFIG_PREFIX + "useSounds";
    public static final String CONFIG_DEBUGGING_INFO = CONFIG_PREFIX + "useDebuggingInfo";

    /**
     * Textures -----------------------------------------------------------------------------------
     */
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";
    public static final ResourceLocation LOCATION_NUMINA_GUI_TEXTURE_ATLAS = new ResourceLocation(MOD_ID, "textures/atlas/gui.png");
    public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "gui/glass.png");
    public static final ResourceLocation BLANK_ARMOR_MODEL_PATH = new ResourceLocation(TEXTURE_PREFIX + "item/armor/blankarmor.png");
    public static final ResourceLocation WEAPON_SLOT_BACKGROUND = new ResourceLocation(TEXTURE_PREFIX + "gui/weapon.png");
    public static final ResourceLocation TEXTURE_WHITE_SHORT = new ResourceLocation(NuminaConstants.MOD_ID, "models/white");
    public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/white.png");
    public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/models/armorstand2.png");

    /**
     * Tag Constants ------------------------------------------------------------------------------
     */
    public static final String MODULE_TRADEOFF_PREFIX = "module.tradeoff.";

    public static final String MODULAR_ITEM_TAG = "MMModItem";// Machine Muse Mod
    public static final String MODULE_TAG = "MMModModule";// Machine Muse Mod Module

    public static final String TAG_MODULE_SETTINGS = "Module Settings";
    public static final String MODE = "Mode";

    // Energy Storage -----------------------------------------------------------------------------
    public static final String ENERGY_TAG = "energy";
    public static final String MAX_ENERGY = "maxEnergy";
    public static final String MAX_TRAMSFER = "maxTransfer";

    // Heat Storage -------------------------------------------------------------------------------
    public static final String HEAT = "heat";
    public static final String MAXIMUM_HEAT = "maxHeat";

    // Inventory ----------------------------------------------------------------------------------
    public static final String ITEMS_TAG = "Inventory";
    public static final String MODULES_TAG ="Modules";

    // Misc ---------------------------------------------------------------------------------------
    public static final String BLOCK = "block";

    // Toggleable ---------------------------------------------------------------------------------
    public static final String TAG_ONLINE = "online";

    // Model Stuff --------------------------------------------------------------------------------
    public static final int FULL_BRIGHTNESS = 0XF000F0;
    public static final String RENDER_TAG = "render";
    public static final String SPECLIST = "specList";
    public static final String COSMETIC_PRESET = "cosmeticPreset";
    public static final String COLOR = "color"; // single color, like LuxCapacitor
    public static final String COLORS = "colors";
    public static final String MODEL = "model";
    public static final String PART = "part";
    public static final String GLOW = "glow";
    public static final String COLOUR_INDEX = "colorindex";

    /**
     * Registry Names -----------------------------------------------------------------------------
     */
    // Mode Changing ... (it's in the name)
    public static final ResourceLocation MODE_CHANGING_ICON_RENDERER = new ResourceLocation(MOD_ID, "numina_mode_changing_icon");

    // Overheat damage type
    public static final ResourceLocation OVERHEAT_DAMAGE_REGANAME = new ResourceLocation(MOD_ID, "overheat");

    // Armor Stand
    public static final String ARMORSTAND_REGNAME = "armor_stand";
    public static final String ARMOR_STAND__ENTITY_TYPE_REGNAME = ARMORSTAND_REGNAME + "_entity";

    // Blocks
    public static final String CHARGING_BASE_REGNAME = "charging_base";

    // Batteries ----------------------------------------------------------------------------------
    public static final String MODULE_BATTERY_BASIC__REGNAME = "battery_basic";
    public static final String MODULE_BATTERY_ADVANCED__REGNAME = "battery_advanced";
    public static final String MODULE_BATTERY_ELITE__REGNAME = "battery_elite";
    public static final String MODULE_BATTERY_ULTIMATE__REGNAME = "battery_ultimate";


    // Components ---------------------------------------------------------------------------------
    public static final String COMPONENT__WIRING__REGNAME = getComponentName("wiring");
    public static final String COMPONENT__SOLENOID__REGNAME = getComponentName("solenoid");
    public static final String COMPONENT__SERVO__REGNAME = getComponentName("servo");
    public static final String COMPONENT__GLIDER_WING__REGNAME = getComponentName("glider_wing");
    public static final String COMPONENT__ION_THRUSTER__REGNAME = getComponentName("ion_thruster");
    public static final String COMPONENT__PARACHUTE__REGNAME = getComponentName("parachute");
    public static final String COMPONENT__FIELD_EMITTER__REGNAME = getComponentName("field_emitter");
    public static final String COMPONENT__LASER_EMITTER__REGNAME = getComponentName("laser_emitter");
    public static final String COMPONENT__CARBON_MYOFIBER__REGNAME = getComponentName("carbon_myofiber");
    public static final String COMPONENT__CONTROL_CIRCUIT_1__REGNAME = getComponentName("control_circuit_basic");
    public static final String COMPONENT__CONTROL_CIRCUIT_2__REGNAME = getComponentName("control_circuit_advanced");
    public static final String COMPONENT__CONTROL_CIRCUIT_3__REGNAME = getComponentName("control_circuit_elite");
    public static final String COMPONENT__CONTROL_CIRCUIT_4__REGNAME = getComponentName("control_circuit_ultimate");
    public static final String COMPONENT__MYOFIBER_GEL__REGNAME = getComponentName("myofiber_gel");
    public static final String COMPONENT__ARTIFICIAL_MUSCLE__REGNAME = getComponentName("artificial_muscle");
    public static final String COMPONENT__SOLAR_PANEL__REGNAME = getComponentName("solar_panel");
    public static final String COMPONENT__MAGNET__REGNAME = getComponentName("magnet");
    public static final String COMPONENT__COMPUTER_CHIP__REGNAME = getComponentName("computer_chip");
    public static final String COMPONENT__RUBBER_HOSE__REGNAME = getComponentName("rubber_hose");

    static String getComponentName(String component) {
        return new StringBuilder("component_").append(component).toString();
    }

    /**
     *  Tooltips -----------------------------------------------------------------------------------------
     */
    public static final String TOOLTIP_MODE = getTooltip("mode");
    public static final String TOOLTIP_CHANGE_MODES = getTooltip(  "changeModes");
    public static final String TOOLTIP_INSTALLED_MODULES = getTooltip("installedModules");
    public static final String TOOLTIP_NO_MODULES = getTooltip("noModules");
    public static final String TOOLTIP_CHARGING_BASE = getTooltip(CHARGING_BASE_REGNAME);
    public static final String TOOLTIP_BATTERY_ENERGY = getTooltip("battery.energy");
    public static final String TOOLTIP_PRESS_SHIFT = getTooltip("pressShift");
    public static final String TOOLTIP_ENERGY = getTooltip("energy");

    static String getTooltip(String tooltipName) {
        return new StringBuilder("tooltip.").append(NuminaConstants.MOD_ID).append(".").append(tooltipName).toString();
    }

    /**
     * GUI ------------------------------------------------------------------------------------------------------------
     */
    public static final String GUI_CREATIVE_INSTALL = getGUI("creative.install");
    public static final String GUI_CREATIVE_INSTALL_DESC = getGUI("creative.install.desc");

    public static final String GUI_CREATIVE_INSTALL_ALL = getGUI("creative.install.all");
    public static final String GUI_CREATIVE_INSTALL_ALL_DESC = getGUI("creative.install.all.desc");

    static String getGUI(String guiString) {
        return new StringBuilder("gui.").append(MOD_ID).append(".").append(guiString).toString();
    }

}
