/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.constants;

import net.minecraft.resources.ResourceLocation;

public class MPSRegistryNames {
    /**
     * Entities -----------------------------------------------------------------------------------
     */
    public static final ResourceLocation SPINNING_BLADE = getRegName("spinning_blade");
    public static final ResourceLocation PLASMA_BALL = getRegName("plasma_ball");
    public static final ResourceLocation RAILGUN_BOLT = getRegName("railgun_bolt");
    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final ResourceLocation TINKER_TABLE = getRegName("tinkertable");
    public static final ResourceLocation LUX_CAPACITOR = getRegName("luxcapacitor");

    /**
     * Armor --------------------------------------------------------------------------------------
     */
    public static final ResourceLocation POWER_ARMOR_HELMET = getRegName("powerarmor_head");
    public static final ResourceLocation POWER_ARMOR_CHESTPLATE = getRegName("powerarmor_torso");
    public static final ResourceLocation POWER_ARMOR_LEGGINGS = getRegName("powerarmor_legs");
    public static final ResourceLocation POWER_ARMOR_BOOTS = getRegName("powerarmor_feet");

    /**
     * HandHeld -----------------------------------------------------------------------------------
     */
    public static final ResourceLocation POWER_FIST = getRegName("powerfist");

    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final ResourceLocation LEATHER_PLATING_MODULE = getRegName("plating_leather");


    // Cosmetic -----------------------------------------------------------------------------------
    public static final ResourceLocation TRANSPARENT_ARMOR_MODULE = getRegName("transparent_armor");

    // Energy Generation -----------------------------------------------------------------------------
    public static final ResourceLocation SOLAR_GENERATOR_MODULE = getRegName("generator_solar");
    public static final ResourceLocation ADVANCED_SOLAR_GENERATOR_MODULE = getRegName("generator_solar_adv");
    public static final ResourceLocation KINETIC_GENERATOR_MODULE = getRegName("generator_kinetic");
    public static final ResourceLocation THERMAL_GENERATOR_MODULE = getRegName("generator_thermal");

    // todo
    // Debug --------------------------------------------------------------------------------------
    public static final ResourceLocation DEBUG_MODULE = getRegName("debug_module");

    // Environmental ------------------------------------------------------------------------------
    public static final ResourceLocation COOLING_SYSTEM_MODULE = getRegName("cooling_system");
    public static final ResourceLocation FLUID_TANK_MODULE = getRegName("fluid_tank");
    public static final ResourceLocation AUTO_FEEDER_MODULE = getRegName("auto_feeder");
    public static final ResourceLocation MOB_REPULSOR_MODULE = getRegName("mob_repulsor");
    public static final ResourceLocation WATER_ELECTROLYZER_MODULE = getRegName("water_electrolyzer");

    // Mining Enhancements ------------------------------------------------------------------------
    public static final ResourceLocation SILK_TOUCH_MODULE = getRegName("silk_touch");
    public static final ResourceLocation FORTUNE_MODULE = getRegName("fortune");
    public static final ResourceLocation VEIN_MINER_MODULE = getRegName("vein_miner");
    public static final ResourceLocation TUNNEL_BORE_MODULE = getRegName("tunnel_bore"); // no icon
    public static final ResourceLocation AOE_PICK_UPGRADE_MODULE2 = getRegName("selective_miner"); // no icon

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
    public static final ResourceLocation AE2_METEOR_COMPASS = getRegName("meteorite_compass");
    public static final ResourceLocation RECOVERY_COMPASS = getRegName("recovery_compass");





    // Tools --------------------------------------------------------------------------------------
    public static final ResourceLocation AQUA_AFFINITY_MODULE = getRegName("aqua_affinity");
    public static final ResourceLocation AXE_MODULE = getRegName("axe");
    public static final ResourceLocation DIAMOND_PICK_UPGRADE_MODULE = getRegName("diamond_pick_upgrade");
    public static final ResourceLocation PORTABLE_WORKBENCH_MODULE = getRegName("portable_tinkertable");
    public static final ResourceLocation FLINT_AND_STEEL_MODULE = getRegName("flint_and_steel");
    public static final ResourceLocation HOE_MODULE = getRegName("hoe");
    public static final ResourceLocation LEAF_BLOWER_MODULE = getRegName("leaf_blower");
    public static final ResourceLocation LUX_CAPACITOR_MODULE = getRegName("luxcapacitor_module");
    public static final ResourceLocation PORTABLE_CRAFTING_MODULE = getRegName("portable_crafting_table");
    public static final ResourceLocation PICKAXE_MODULE = getRegName("pickaxe");
    public static final ResourceLocation SHEARS_MODULE = getRegName("shears");
    public static final ResourceLocation SHOVEL_MODULE = getRegName("shovel");

    // Vision -------------------------------------------------------------------------------------
    public static final ResourceLocation BINOCULARS_MODULE = getRegName("binoculars");
    public static final ResourceLocation NIGHT_VISION_MODULE = getRegName("night_vision");

    // Weapons ------------------------------------------------------------------------------------
    public static final ResourceLocation BLADE_LAUNCHER_MODULE = getRegName("blade_launcher");
    public static final ResourceLocation LIGHTNING_MODULE = getRegName("lightning_summoner");
    public static final ResourceLocation MELEE_ASSIST_MODULE = getRegName("melee_assist");
    public static final ResourceLocation PLASMA_CANNON_MODULE = getRegName("plasma_cannon");
    public static final ResourceLocation RAILGUN_MODULE = getRegName("railgun");
//    public static final ResourceLocation SONIC_WEAPON_MODULE = "sonic_weapon"; // TODO?

    /**
     * AbstractContainerMenu ----------------------------------------------------------------------------------
     */
    public static final String MPS_CRAFTING_CONTAINER_TYPE = "mps_crafting_container";
    public static final String TINKERTABLE_CONTAINER_TYPE = "tinkertable_container_type";

    public static final String INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE = "install_salvage_craft_container_type";
    public static final String INSTALL_SALVAGE_CONTAINER_TYPE = "install_salvage_container_type";
    public static final String MODULE_TWEAK_CONTAINER_TYPE = "module_tweak_container_type";


}
