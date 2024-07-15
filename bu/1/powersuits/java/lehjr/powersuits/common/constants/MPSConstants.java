package lehjr.powersuits.common.constants;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MPSConstants {



    .CREATIVE_TAB_TRANSLATION_KEY

    public static final String RESOURCE_DOMAIN = MOD_ID.toLowerCase();

    // temporary locations until model spec system up and running
    public static final String SEBK_AMROR_PANTS = TEXTURE_PREFIX + "item/armor/sebkarmorpants.png";
    public static final String SEBK_AMROR = TEXTURE_PREFIX + "item/armor/sebkarmor.png";
    public static final String CITIZEN_JOE_ARMOR_PANTS = TEXTURE_PREFIX + "item/armor/joearmorpants.png";
    public static final String CITIZEN_JOE_ARMOR = TEXTURE_PREFIX + "item/armor/joearmor.png";

    public static final ResourceLocation POWER_FIST_TEXTURE = new ResourceLocation(TEXTURE_PREFIX + "models/powerfist.png");

    public static final Component CRAFTING_TABLE_CONTAINER_NAME  = Component.translatable("container.crafting");



    /**
     * Config -------------------------------------------------------------------------------------
     */



    /** Recipes ------------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_RECIPES = CONFIG_PREFIX + "recipes.";
    public static final String CONFIG_RECIPES_USE_VANILLA = CONFIG_PREFIX_RECIPES + "useVanilla";


    public static final String CONFIG_GENERAL_ALLOW_CONFLICTING_KEYBINDS = CONFIG_PREFIX_GENERAL + "allowConflictingKeybinds";

    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_HELMET = CONFIG_PREFIX_GENERAL + "maxHeatBaseHelmet";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_CHESTPLATE = CONFIG_PREFIX_GENERAL + "maxHeatBaseChestplate";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_LEGGINGS = CONFIG_PREFIX_GENERAL +  "maxHeatBaseLeggings";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_FEET = CONFIG_PREFIX_GENERAL +  "maxHeatBaseFeet";
    public static final String CONFIG_GENERAL_BASE_MAX_HEAT_POWERFIST = CONFIG_PREFIX_GENERAL +  "maxHeatBasePowerFist";
    public static final String CONFIG_GENERAL_VEIN_MINER_ORE_LIST = CONFIG_PREFIX_GENERAL +  "maxVeinMinerOreList";
    public static final String CONFIG_GENERAL_VEIN_MINER_BLOCK_LIST = CONFIG_PREFIX_GENERAL +  "maxVeinMinerBlockList";

    /** Cosmetics ---------------------------------------------------------------------------------- */
    public static final String CONFIG_PREFIX_COSMETIC = CONFIG_PREFIX + "cosmetic.";
    public static final String CONFIG_COSMETIC_USE_LEGACY_COSMETIC_SYSTEM = CONFIG_PREFIX_COSMETIC + "useLegacyCosmeticSystem";
    public static final String CONFIG_COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS = CONFIG_PREFIX_COSMETIC + "allowHighPollyArmorModuels";
    public static final String CONFIG_COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN = CONFIG_PREFIX_COSMETIC + "allowPowerFistCustomization";




    /** Energy Generation ---------------------------------------------------------------------- */
    public static final String ENERGY_GENERATION = "energyPerBlock";
    public static final String MOVEMENT_RESISTANCE = "movementResistance";
    public static final String ENERGY_GENERATED = "energyGenerated";
    public static final String HEAT_GENERATION_DAY = "daytimeHeatGen";
    public static final String HEAT_GENERATION_NIGHT = "nightTimeHeatGen";
    public static final String ENERGY_GENERATION_DAY = "daytimeEnergyGen";
    public static final String ENERGY_GENERATION_NIGHT = "nightTimeEnergyGen";
    /** Environmental -------------------------------------------------------------------------- */
    // Auto Feeder

    // Cooling System
    public static final String COOLING_BONUS = "coolingBonus";
    // Fluid Tank
    public static final String FLUID_TANK_SIZE = "fluidTankSize";
    public static final String HEAT_ACTIVATION_PERCENT = "heatActivationPercent";//Heat Activation Percent";
    public static final String ACTIVATION_PERCENT = "activationPercent";//"Activation Percent"
    // Water Electrolyzer
    /** Mining Enhancement --------------------------------------------------------------------- */
    // Generic






    // WIP Modules
















}
