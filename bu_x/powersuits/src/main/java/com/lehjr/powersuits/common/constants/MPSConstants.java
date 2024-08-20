package com.lehjr.powersuits.common.constants;

import net.minecraft.resources.ResourceLocation;

public class MPSConstants {

    // Mod ===========================================================================================================
    public static final String MOD_ID = "powersuits";
    public static final String CREATIVE_TAB_TRANSLATION_KEY = MOD_ID + ".creative.tab";
    public static final String MPS_ITEM_GROUP =  "itemGroup." + MOD_ID;
    public static final String RESOURCE_PREFIX = MPSConstants.MOD_ID + ":";
    public static final String TEXTURE_PREFIX = RESOURCE_PREFIX + "textures/";

    // Generic
    public static final String BASE = "Base";
    public static final String MULTIPLIER = "Multiplier";
    public static final String OVERCLOCK_MULTIPLIER = "Overclock" + MULTIPLIER;
    /**
     * Tags -----------------------------------------------------------------------------------------------------------
     */
    // Generic
    public static final String ENERGY_CONSUMPTION = "energyCon";
    public static final String ENERGY_CONSUMPTION_BASE = ENERGY_CONSUMPTION + BASE;
    public static final String ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER = ENERGY_CONSUMPTION + OVERCLOCK_MULTIPLIER;



    public static final String ENERGY_CONSUMPTION_MULTIPLIER = ENERGY_CONSUMPTION + MULTIPLIER;

    public static final String POWER = "power";
    public static final String HARVEST_SPEED = "harvestSpeed";
    public static final String HARVEST_SPEED_BASE = HARVEST_SPEED + BASE;
    public static final String HARVEST_SPEED_OVERCLOCK_MULTIPLIER = HARVEST_SPEED + OVERCLOCK_MULTIPLIER;

    public static final String DIAMETER ="diameter";


    // Environmental --------------------------------------------------------------------------------------------------
    // Cooling System
    public static final String COOLING_BONUS = "coolingBonus";
    // Fluid Tank
    public static final String FLUID_TANK_SIZE = "fluidTankSize";
    public static final String HEAT_ACTIVATION_PERCENT = "heatActivationPercent";//Heat Activation Percent";
    public static final String ACTIVATION_PERCENT = "activationPercent";//"Activation Percent"

    // AutoFeeder
    public static final String TAG_FOOD = "food";
    public static final String TAG_SATURATION = "saturation";
    public static final String EATING_EFFICIENCY = "autoFeederEfficiency";
    public static final String EFFICIENCY = "efficiency";

    // Energy Gemeration ----------------------------------------------------------------------------------------------
    public static final String ENERGY_GENERATION = "energyPerBlock";
    public static final String MOVEMENT_RESISTANCE = "movementResistance";
    public static final String ENERGY_GENERATED = "energyGenerated";
    public static final String HEAT_GENERATION_DAY = "daytimeHeatGen";
    public static final String HEAT_GENERATION_NIGHT = "nightTimeHeatGen";
    public static final String ENERGY_GENERATION_DAY = "daytimeEnergyGen";
    public static final String ENERGY_GENERATION_NIGHT = "nightTimeEnergyGen";



    // Mining Enchantments --------------------------------------------------------------------------------------------
    public static final String ENCHANTMENT_LEVEL ="enchLevel";
    // Aqua Affinity
    public static final String AQUA_HARVEST_SPEED = "aquaHarvSpeed";
    // Fortune
    public static final String FORTUNE_ENCHANTMENT_LEVEL ="fortuneLevel";
    public static final String FORTUNE_ENERGY_CONSUMPTION = "fortuneEnCon";
    // Silk Touch
    public static final String SILK_TOUCH_ENERGY_CONSUMPTION = "silkTouchEnCon";

    // Mining Enhancements --------------------------------------------------------------------------------------------
    // Selective Miner
    public static final String MINING_RADIUS = "miningRadius";
    public static final String SELECTIVE_MINER_LIMIT = "selMinerLimit";

    // Movement -------------------------------------------------------------------------------------------------------
    // Generic
    public static final String THRUST = "thrust";
    // Blink Drive
    public static final String BLINK_DRIVE_RANGE = "blinkDriveRange";
    public static final String RANGE = "range";
    // Dimensional Rift
    public static final String HEAT_GENERATION = "heatGen";
    // Flight Control
    public static final String VERTICALITY = "vertically";
    public static final String FLIGHT_VERTICALITY = "yLookRatio";
    // JetBoots
    public static final String JETBOOTS_THRUST = "jetbootsThrust";
    // Jetpack
    public static final String JETPACK_THRUST = "jetpackThrust";
    // Sprint Assist
    public static final String SPRINT_ENERGY_CONSUMPTION = "sprintEnergyCon";
    public static final String SPRINT_SPEED_MULTIPLIER = "sprintSpeedMult";
    public static final String SPRINT_ASSIST = "sprintAssist";
    public static final String FOOD_COMPENSATION = "sprintExComp";
    public static final String COMPENSATION = "compensation";
    public static final String WALKING_ENERGY_CONSUMPTION = "walkingEnergyCon";
    public static final String WALKING_SPEED_MULTIPLIER = "walkingSpeedMult";
    public static final String WALKING_ASSISTANCE = "walkingAssist";
    // Swim Assist
    public static final String SWIM_BOOST_AMOUNT = "underwaterMovBoost";

    // Special --------------------------------------------------------------------------------------------------------
    public static final String ACTIVE_CAMOUFLAGE_ENERGY = "invisibilityEnergy";

    // Tool -----------------------------------------------------------------------------------------------------------
    // Generic
    public static final String OVERCLOCK = "overclock";
    public static final String RADIUS = "radius";

    // Lux Capacitor
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
    public static final String ALPHA = "alpha";
    public static final String RED_HUE = "redHue";
    public static final String RED_HUE_MULTIPLIER = RED_HUE + MULTIPLIER;
    public static final String BLUE_HUE = "blueHue";
    public static final String BLUE_HUE_MULTIPLIER = BLUE_HUE + MULTIPLIER;
    public static final String GREEN_HUE = "greenHue";
    public static final String GREEN_HUE_MULTIPLIER = GREEN_HUE + MULTIPLIER;
    public static final String OPACITY = "opacity";
    public static final String OPACITY_MULTIPLIER = OPACITY + MULTIPLIER;

    // Vision ---------------------------------------------------------------------------------------------------------
    public static final String FOV = "fieldOfView";
    public static final String FOV_BASE = FOV + BASE;
    public static final String FIELD_OF_VIEW = "fOVMult";
    public static final String FOV_FIELD_OF_VIEW_MULITIPLIER = FOV + FIELD_OF_VIEW + MULTIPLIER;


    // Weapons --------------------------------------------------------------------------------------------------------
    // Blade Launcher
    public static final String BLADE_DAMAGE = "spinBladeDam";
    // Lightning Summoner
    public static final String HEAT_EMISSION = "heatEmission";
    // Melee Assist
    public static final String PUNCH_DAMAGE = "meleeDamage";
    public static final String PUNCH_DAMAGE_BASE = PUNCH_DAMAGE + BASE;
    public static final String PUNCH_KNOCKBACK = "meleeKnockback";
    public static final String IMPACT = "Impact";
    public static final String CARRY_THROUGH = "carryThrough";
    public static final String ENERGY_CONSUMPTION_IMPACT_MULTIPLIER = ENERGY_CONSUMPTION + IMPACT + MULTIPLIER;
    public static final String ENERGY_CONSUMPTION_CARRY_THROUGH_MULTIPLIER = ENERGY_CONSUMPTION + CARRY_THROUGH + MULTIPLIER;
    public static final String PUNCH_KNOCKBACK_CARRY_THROUGH_MULTIPLIER = PUNCH_KNOCKBACK + CARRY_THROUGH + MULTIPLIER;
    public static final String PUNCH_DAMAGE_IMPACT_MULTIPLIER = PUNCH_DAMAGE + IMPACT + MULTIPLIER;

    // Plasma Cannon
    public static final String AMPERAGE = "amperage";
    public static final String CREEPER = "creeper";
    public static final String VOLTAGE = "voltage";
    public static final String ENERGY_PER_TICK = "energyPerTick";
    public static final String ENERGY_PER_TICK_BASE = ENERGY_PER_TICK + BASE;
    public static final String ENERGY_PER_TICK_VOLTAGE_MULTIPLIER = ENERGY_PER_TICK + VOLTAGE + MULTIPLIER;
    public static final String ENERGY_PER_TICK_AMPERAGE_MULTIPLIER = ENERGY_PER_TICK + AMPERAGE + MULTIPLIER;
    public static final String PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE = "plasmaDamage";
    public static final String PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_BASE = PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE + BASE;
    public static final String PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE_AMPERAGE_MULTIPLIER = PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE + AMPERAGE + MULTIPLIER;
    public static final String PLASMA_CANNON_EXPLOSIVENESS = "plasmaExplosiveness";
    public static final String PLASMA_CANNON_EXPLOSIVENESS_VOLTAGE_MULTIPLIER = PLASMA_CANNON_EXPLOSIVENESS + VOLTAGE + MULTIPLIER;

    // Railgun Module
    public static final String TOTAL_IMPULSE = "totalImpulse";
    public static final String TOTAL_IMPULSE_BASE = TOTAL_IMPULSE + BASE;
    public static final String ENERGY_CONSUMPTION_VOLTAGE_MULTIPLIER = ENERGY_CONSUMPTION + VOLTAGE + MULTIPLIER;
    public static final String TOTAL_IMPULSE_VOLTAGE_MULTIPLIER = TOTAL_IMPULSE + VOLTAGE + MULTIPLIER;
    public static final String HEAT_EMMISSION_BASE = HEAT_EMISSION + BASE;
    public static final String HEAT_EMMISSION_VOLTAGE_MULTIPLIER = HEAT_EMISSION + VOLTAGE + MULTIPLIER;
    public static final String COOLDOWN_TIMER = "cooldown";


    /**
     * AbstractContainerMenu ------------------------------------------------------------------------------------------
     */
    public static final String MPS_CRAFTING_CONTAINER_TYPE = "mps_crafting_container";
    public static final String TINKERTABLE_CONTAINER_TYPE = "tinkertable_container_type";

    public static final String INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE = "install_salvage_craft_container_type";
    public static final String INSTALL_SALVAGE_CONTAINER_TYPE = "install_salvage_container_type";
    public static final String MODULE_TWEAK_CONTAINER_TYPE = "module_tweak_container_type";

    /** GUI --------------------------------------------------------------------------------------- */
    public static final String GUI_INSTALL_SALVAGE = getGUI("tab.install.salvage");
    public static final String GUI_ARMOR = getGUI("armor");
    public static final String GUI_COLOR_PREFIX = getGUI("colorPrefix");
    public static final String GUI_COMPATIBLE_MODULES = getGUI("compatible.modules");
    public static final String GUI_ENERGY_STORAGE = getGUI("energyStorage");
    public static final String GUI_EQUIPPED_TOTALS = getGUI("equippedTotals");
    public static final String GUI_INSTALL = getGUI("install");
    public static final String GUI_INSTALL_DESC = getGUI("install.desc");

    static String getGUI(String guiString) {
        return "gui." + MOD_ID + "." + guiString;
    }


    /**
     * Common Config --------------------------------------------------------------------------------------------------
     */
    public static final String CONFIG_PREFIX = "config." + MOD_ID + ".";

    // General --------------------------------------------------------------------------------------------------------
    public static final String CONFIG_PREFIX_GENERAL = CONFIG_PREFIX + "general.";
    public static final String CONFIG_GENERAL_MAX_FLYING_SPEED = CONFIG_PREFIX_GENERAL + "maxFlyingSpeed";

    // Armor ----------------------------------------------------------------------------------------------------------
    public static final String CONFIG_INVENTORY_SLOTS = "inventorySlots";


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

    // Entities -------------------------------------------------------------------------------------------------------
    public static final ResourceLocation SPINNING_BLADE = getRegName("spinning_blade");
    public static final ResourceLocation PLASMA_BALL = getRegName("plasma_ball");
    public static final ResourceLocation RAILGUN_BOLT = getRegName("railgun_bolt");

    // Blocks ---------------------------------------------------------------------------------------------------------
    public static final ResourceLocation TINKER_TABLE = getRegName("tinkertable");
    public static final ResourceLocation LUX_CAPACITOR = getRegName("luxcapacitor");

    // Armor ----------------------------------------------------------------------------------------------------------
    // Helmet
    public static final ResourceLocation POWER_ARMOR_HELMET_1 = getRegName("powerarmor_head1");
    public static final ResourceLocation POWER_ARMOR_HELMET_2 = getRegName("powerarmor_head2");
    public static final ResourceLocation POWER_ARMOR_HELMET_3 = getRegName("powerarmor_head3");
    public static final ResourceLocation POWER_ARMOR_HELMET_4 = getRegName("powerarmor_head4");

    // ChestPlate
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE_1 = getRegName("powerarmor_torso1");
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE_2 = getRegName("powerarmor_torso2");
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE_3 = getRegName("powerarmor_torso3");
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE_4 = getRegName("powerarmor_torso4");

    // Leggings
    public static final ResourceLocation POWER_ARMOR_LEGGINGS_1 = getRegName("powerarmor_legs1");
    public static final ResourceLocation POWER_ARMOR_LEGGINGS_2 = getRegName("powerarmor_legs2");
    public static final ResourceLocation POWER_ARMOR_LEGGINGS_3 = getRegName("powerarmor_legs3");
    public static final ResourceLocation POWER_ARMOR_LEGGINGS_4 = getRegName("powerarmor_legs4");

    // Boots
    public static final ResourceLocation POWER_ARMOR_BOOTS_1 = getRegName("powerarmor_feet1");
    public static final ResourceLocation POWER_ARMOR_BOOTS_2 = getRegName("powerarmor_feet2");
    public static final ResourceLocation POWER_ARMOR_BOOTS_3 = getRegName("powerarmor_feet3");
    public static final ResourceLocation POWER_ARMOR_BOOTS_4 = getRegName("powerarmor_feet4");

    // HandHeld -------------------------------------------------------------------------------------------------------
    public static final ResourceLocation POWER_FIST_1 = getRegName("powerfist1");
    public static final ResourceLocation POWER_FIST_2 = getRegName("powerfist2");
    public static final ResourceLocation POWER_FIST_3 = getRegName("powerfist3");
    public static final ResourceLocation POWER_FIST_4 = getRegName("powerfist4");



    // Modules --------------------------------------------------------------------------------------------------------
    // Armor
    public static final ResourceLocation IRON_PLATING_MODULE = getRegName("plating_iron");
    public static final ResourceLocation DIAMOND_PLATING_MODULE = getRegName("plating_diamond");
    public static final ResourceLocation NETHERITE_PLATING_MODULE = getRegName("plating_netherite");
    public static final ResourceLocation ENERGY_SHIELD_MODULE = getRegName("energy_shield");

    // Cosmetic
    public static final ResourceLocation TRANSPARENT_ARMOR_MODULE = getRegName("transparent_armor");

    // Energy Generation ----------------------------------------------------------------------------------------------
    // Combustion
    public static final ResourceLocation COMBUSTION_GENERATOR_MODULE_1 = getRegName("generator_combustion1");
    public static final ResourceLocation COMBUSTION_GENERATOR_MODULE_2 = getRegName("generator_combustion2");
    public static final ResourceLocation COMBUSTION_GENERATOR_MODULE_3 = getRegName("generator_combustion3");
    public static final ResourceLocation COMBUSTION_GENERATOR_MODULE_4 = getRegName("generator_combustion4");

    // Thermal
    public static final ResourceLocation THERMAL_GENERATOR_MODULE_1 = getRegName("generator_thermal1");
    public static final ResourceLocation THERMAL_GENERATOR_MODULE_2 = getRegName("generator_thermal2");
    public static final ResourceLocation THERMAL_GENERATOR_MODULE_3 = getRegName("generator_thermal3");
    public static final ResourceLocation THERMAL_GENERATOR_MODULE_4 = getRegName("generator_thermal4");

    // Solar
    public static final ResourceLocation SOLAR_GENERATOR_MODULE_1 = getRegName("generator_solar1");
    public static final ResourceLocation SOLAR_GENERATOR_MODULE_2 = getRegName("generator_solar2");
    public static final ResourceLocation SOLAR_GENERATOR_MODULE_3 = getRegName("generator_solar3");
    public static final ResourceLocation SOLAR_GENERATOR_MODULE_4 = getRegName("generator_solar4");

    // Kinetic
    public static final ResourceLocation KINETIC_GENERATOR_MODULE_1 = getRegName("generator_kinetic1");
    public static final ResourceLocation KINETIC_GENERATOR_MODULE_2 = getRegName("generator_kinetic2");
    public static final ResourceLocation KINETIC_GENERATOR_MODULE_3 = getRegName("generator_kinetic3");
    public static final ResourceLocation KINETIC_GENERATOR_MODULE_4 = getRegName("generator_kinetic4");

    public static final ResourceLocation FISSION_REACTOR_MODULE_1 = getRegName("fission_reactor1");
    public static final ResourceLocation FISSION_REACTOR_MODULE_2 = getRegName("fission_reactor2");
    public static final ResourceLocation FUSION_REACTOR_MODULE_3 = getRegName("fusion_reactor3");
    public static final ResourceLocation FUSION_REACTOR_MODULE_4 = getRegName("fusion_reactor4");

    // Debug --------------------------------------------------------------------------------------
    public static final ResourceLocation DEBUG_MODULE = getRegName("debug_module");

    // Environmental ------------------------------------------------------------------------------
    // Cooling System
    public static final ResourceLocation FLUID_TANK_MODULE_1 = getRegName("fluid_tank1");
    public static final ResourceLocation FLUID_TANK_MODULE_2 = getRegName("fluid_tank2");
    public static final ResourceLocation COOLING_SYSTEM_MODULE_3 = getRegName("cooling_system3");
    public static final ResourceLocation COOLING_SYSTEM_MODULE_4 = getRegName("cooling_system4");
    public static final ResourceLocation AUTO_FEEDER_MODULE = getRegName("auto_feeder");
    public static final ResourceLocation MOB_REPULSOR_MODULE = getRegName("mob_repulsor");
    public static final ResourceLocation WATER_ELECTROLYZER_MODULE = getRegName("water_electrolyzer");

    // Mining Enhancements ------------------------------------------------------------------------
    public static final ResourceLocation VEIN_MINER_MODULE = getRegName("vein_miner");
    public static final ResourceLocation TUNNEL_BORE_MODULE = getRegName("tunnel_bore"); // no icon
    public static final ResourceLocation SELECTIVE_MINER_MODULE = getRegName("selective_miner"); // no icon

    // Mining Enchantments ------------------------------------------------------------------------
    public static final ResourceLocation AQUA_AFFINITY_MODULE = getRegName("aqua_affinity");
    public static final ResourceLocation SILK_TOUCH_MODULE = getRegName("silk_touch");
    public static final ResourceLocation FORTUNE_MODULE = getRegName("fortune");

    // Movement -----------------------------------------------------------------------------------
    public static final ResourceLocation BLINK_DRIVE_MODULE = getRegName("blink_drive");
    public static final ResourceLocation CLIMB_ASSIST_MODULE = getRegName("climb_assist");
    public static final ResourceLocation DIMENSIONAL_RIFT_MODULE = getRegName("dim_rift_gen");
    public static final ResourceLocation FLIGHT_CONTROL_MODULE = getRegName("flight_control");
    public static final ResourceLocation GLIDER_MODULE = getRegName("glider");
    public static final ResourceLocation JETBOOTS_MODULE = getRegName("jet_boots");
    public static final ResourceLocation JETPACK_MODULE = getRegName("jetpack");
    public static final ResourceLocation JUMP_ASSIST_MODULE = getRegName("jump_assist");
    public static final ResourceLocation PARACHUTE_MODULE = getRegName("parachute");
    public static final ResourceLocation SHOCK_ABSORBER_MODULE = getRegName("shock_absorber");
    public static final ResourceLocation SPRINT_ASSIST_MODULE = getRegName("sprint_assist");
    public static final ResourceLocation SWIM_BOOST_MODULE = getRegName("swim_assist");

    // Special ------------------------------------------------------------------------------------
    public static final ResourceLocation CLOCK_MODULE = getRegName("clock"); // using vanilla item as module (this is for capability name)
    public static final ResourceLocation COMPASS_MODULE = getRegName("compass"); // using vanilla item as module (this is for capability name)
    public static final ResourceLocation ACTIVE_CAMOUFLAGE_MODULE = getRegName("invisibility");
    public static final ResourceLocation MAGNET_MODULE = getRegName("magnet");
    public static final ResourceLocation PIGLIN_PACIFICATION_MODULE = getRegName("piglin_pacification_module");
    // TODO?
    //    public static final ResourceLocation AE2_METEOR_COMPASS = getRegName("meteorite_compass");
//    public static final ResourceLocation RECOVERY_COMPASS = getRegName("recovery_compass");

    // Tools --------------------------------------------------------------------------------------
    // Axe
    public static final ResourceLocation STONE_AXE_MODULE = getRegName("axe1");
    public static final ResourceLocation IRON_AXE_MODULE = getRegName("axe2");
    public static final ResourceLocation DIAMOND_AXE_MODULE = getRegName("axe3");
    public static final ResourceLocation NETHERITE_AXE_MODULE = getRegName("axe4");
    // Pickaxe
    public static final ResourceLocation STONE_PICKAXE_MODULE = getRegName("pickaxe1");
    public static final ResourceLocation IRON_PICKAXE_MODULE = getRegName("pickaxe2");
    public static final ResourceLocation DIAMOND_PICKAXE_MODULE = getRegName("pickaxe3");
    public static final ResourceLocation NETHERITE_PICKAXE_MODULE = getRegName("pickaxe4");
    // Shovel
    public static final ResourceLocation STONE_SHOVEL_MODULE = getRegName("shovel1");
    public static final ResourceLocation IRON_SHOVEL_MODULE = getRegName("shovel2");
    public static final ResourceLocation DIAMOND_SHOVEL_MODULE = getRegName("shovel3");
    public static final ResourceLocation NETHERITE_SHOVEL_MODULE = getRegName("shovel4");

    public static final ResourceLocation ROTOTILLER_MODULE = getRegName("hoe"); // Todo:  tiered for Nether stuff

    public static final ResourceLocation SHEARS_MODULE = getRegName("shears");
    public static final ResourceLocation PORTABLE_WORKBENCH_MODULE = getRegName("portable_tinkertable");
    public static final ResourceLocation FLINT_AND_STEEL_MODULE = getRegName("flint_and_steel");
    public static final ResourceLocation LEAF_BLOWER_MODULE = getRegName("leaf_blower"); //FIXME: fix or forget
    public static final ResourceLocation LUX_CAPACITOR_MODULE = getRegName("luxcapacitor_module");
    public static final ResourceLocation PORTABLE_CRAFTING_MODULE = getRegName("portable_crafting_table");

    // Vision -------------------------------------------------------------------------------------
    public static final ResourceLocation BINOCULARS_MODULE = getRegName("binoculars");
    public static final ResourceLocation NIGHT_VISION_MODULE = getRegName("night_vision");

    // Weapons ------------------------------------------------------------------------------------
    public static final ResourceLocation BLADE_LAUNCHER_MODULE = getRegName("blade_launcher");
    public static final ResourceLocation LIGHTNING_MODULE = getRegName("lightning_summoner");
    public static final ResourceLocation MELEE_ASSIST_MODULE = getRegName("melee_assist");
    public static final ResourceLocation PLASMA_CANNON_MODULE = getRegName("plasma_cannon");
    public static final ResourceLocation RAILGUN_MODULE = getRegName("railgun");
    public static final ResourceLocation SONIC_WEAPON_MODULE = getRegName("sonic_weapon"); // TODO?





    public static ResourceLocation getRegName(String regNameString) {
        return ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, regNameString);
    }
}
