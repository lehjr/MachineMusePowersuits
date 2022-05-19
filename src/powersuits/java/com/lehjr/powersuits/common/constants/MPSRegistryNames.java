package com.lehjr.powersuits.common.constants;

import net.minecraft.resources.ResourceLocation;

public class MPSRegistryNames {
    /**
     * Entities -----------------------------------------------------------------------------------
     */
    public static final ResourceLocation SPINNING_BLADE = toResLocation("spinning_blade");
    public static final ResourceLocation PLASMA_BALL = toResLocation("plasma_ball");
    public static final ResourceLocation RAILGUN_BOLT = toResLocation("railgun_bolt");

    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final ResourceLocation TINKER_TABLE = toResLocation("tinkertable");
    public static final ResourceLocation LUX_CAPACITOR = toResLocation("luxcapacitor");

    /**
     * Armor --------------------------------------------------------------------------------------
     */
    public static final ResourceLocation POWER_ARMOR_HELMET = toResLocation("powerarmor_head");
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE = toResLocation("powerarmor_torso");
    public static final ResourceLocation POWER_ARMOR_LEGGINGS = toResLocation("powerarmor_legs");
    public static final ResourceLocation POWER_ARMOR_BOOTS = toResLocation("powerarmor_feet");

    /**
     * HandHeld -----------------------------------------------------------------------------------
     */
    public static final ResourceLocation POWER_FIST = toResLocation("powerfist");

    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final ResourceLocation LEATHER_PLATING_MODULE = toResLocation("plating_leather");
    public static final ResourceLocation IRON_PLATING_MODULE = toResLocation(  "plating_iron");
    public static final ResourceLocation DIAMOND_PLATING_MODULE = toResLocation("plating_diamond");
    public static final ResourceLocation ENERGY_SHIELD_MODULE = toResLocation("energy_shield");

    // Cosmetic -----------------------------------------------------------------------------------
    public static final ResourceLocation TRANSPARENT_ARMOR_MODULE = toResLocation("transparent_armor");

    // Energy Generation -----------------------------------------------------------------------------
    public static final ResourceLocation SOLAR_GENERATOR_MODULE = toResLocation("generator_solar");
    public static final ResourceLocation ADVANCED_SOLAR_GENERATOR_MODULE = toResLocation("generator_solar_adv");
    public static final ResourceLocation KINETIC_GENERATOR_MODULE = toResLocation("generator_kinetic");
    public static final ResourceLocation THERMAL_GENERATOR_MODULE = toResLocation("generator_thermal");

    // todo
    // Debug --------------------------------------------------------------------------------------
    public static final ResourceLocation DEBUG = toResLocation("debug_module");

    // Environmental ------------------------------------------------------------------------------
    public static final ResourceLocation COOLING_SYSTEM_MODULE = toResLocation("cooling_system");
    public static final ResourceLocation FLUID_TANK_MODULE = toResLocation("fluid_tank");
    public static final ResourceLocation AUTO_FEEDER_MODULE = toResLocation("auto_feeder");
    public static final ResourceLocation MOB_REPULSOR_MODULE = toResLocation("mob_repulsor");
    public static final ResourceLocation WATER_ELECTROLYZER_MODULE = toResLocation("water_electrolyzer");

    // Mining Enhancements ------------------------------------------------------------------------
    public static final ResourceLocation AOE_PICK_UPGRADE_MODULE = toResLocation("aoe_pick_upgrade"); // no icon
    public static final ResourceLocation SILK_TOUCH_MODULE = toResLocation("silk_touch");
    public static final ResourceLocation FORTUNE_MODULE = toResLocation("fortune");
    public static final ResourceLocation VEIN_MINER_MODULE = toResLocation("vein_miner");

    // WIP
    public static final ResourceLocation TUNNEL_BORE_MODULE = toResLocation("tunnel_bore"); // no icon
    public static final ResourceLocation AOE_PICK_UPGRADE_MODULE2 = toResLocation("aoe_pick_upgrade2"); // no icon

    // Movement -----------------------------------------------------------------------------------
    public static final ResourceLocation BLINK_DRIVE_MODULE = toResLocation("blink_drive");
    public static final ResourceLocation CLIMB_ASSIST_MODULE = toResLocation("climb_assist");
    public static final ResourceLocation DIMENSIONAL_RIFT_MODULE = toResLocation("dim_rift_gen");
    public static final ResourceLocation FLIGHT_CONTROL_MODULE = toResLocation("flight_control");
    public static final ResourceLocation GLIDER_MODULE = toResLocation("glider");
    public static final ResourceLocation JETBOOTS_MODULE = toResLocation("jet_boots");
    public static final ResourceLocation JETPACK_MODULE = toResLocation("jetpack");
    public static final ResourceLocation JUMP_ASSIST_MODULE = toResLocation("jump_assist");
    public static final ResourceLocation PARACHUTE_MODULE = toResLocation("parachute");
    public static final ResourceLocation SHOCK_ABSORBER_MODULE = toResLocation("shock_absorber");
    public static final ResourceLocation SPRINT_ASSIST_MODULE = toResLocation("sprint_assist");
    public static final ResourceLocation SWIM_BOOST_MODULE = toResLocation("swim_assist");

    // Special ------------------------------------------------------------------------------------
    public static final ResourceLocation CLOCK_MODULE = toResLocation("clock");
    public static final ResourceLocation COMPASS_MODULE = toResLocation("compass");
    public static final ResourceLocation ACTIVE_CAMOUFLAGE_MODULE = toResLocation("invisibility");
    public static final ResourceLocation MAGNET_MODULE = toResLocation("magnet");
    public static final ResourceLocation PIGLIN_PACIFICATION_MODULE = toResLocation("piglin_pacification_module");

    // Tools --------------------------------------------------------------------------------------
    public static final ResourceLocation AQUA_AFFINITY_MODULE = toResLocation("aqua_affinity");
    public static final ResourceLocation AXE_MODULE = toResLocation("axe");
    public static final ResourceLocation DIAMOND_PICK_UPGRADE_MODULE = toResLocation("diamond_pick_upgrade");
    public static final ResourceLocation PORTABLE_WORKBENCH_MODULE = toResLocation("portable_tinkertable");
    public static final ResourceLocation FLINT_AND_STEEL_MODULE = toResLocation("flint_and_steel");
    public static final ResourceLocation HOE_MODULE = toResLocation("hoe");
    public static final ResourceLocation LEAF_BLOWER_MODULE = toResLocation("leaf_blower");
    public static final ResourceLocation LUX_CAPACITOR_MODULE = toResLocation("luxcapacitor_module");
    public static final ResourceLocation PORTABLE_CRAFTING_MODULE = toResLocation("portable_crafting_table");
    public static final ResourceLocation PICKAXE_MODULE = toResLocation("pickaxe");
    public static final ResourceLocation SHEARS_MODULE = toResLocation("shears");
    public static final ResourceLocation SHOVEL_MODULE = toResLocation("shovel");

    // Vision -------------------------------------------------------------------------------------
    public static final ResourceLocation BINOCULARS_MODULE = toResLocation("binoculars");
    public static final ResourceLocation NIGHT_VISION_MODULE = toResLocation("night_vision");

    // Weapons ------------------------------------------------------------------------------------
    public static final ResourceLocation BLADE_LAUNCHER_MODULE = toResLocation("blade_launcher");
    public static final ResourceLocation LIGHTNING_MODULE = toResLocation("lightning_summoner");
    public static final ResourceLocation MELEE_ASSIST_MODULE = toResLocation("melee_assist");
    public static final ResourceLocation PLASMA_CANNON_MODULE = toResLocation("plasma_cannon");
    public static final ResourceLocation RAILGUN_MODULE = toResLocation("railgun");

    /**
     * Menu ---------------------------------------------------------------------------------------
     */
    public static final String INSTALL_SALVAGE_MENU_TYPE = "install_salvage_menu_type";

    static ResourceLocation toResLocation(String path) {
        return new ResourceLocation(MPSConstants.MOD_ID, path);
    }
}
