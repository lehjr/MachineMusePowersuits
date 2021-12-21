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

package com.github.lehjr.powersuits.constants;

import net.minecraft.util.ResourceLocation;

public class MPSRegistryNames {
    /**
     * Entities -----------------------------------------------------------------------------------
     */
    public static final String SPINNING_BLADE = "spinning_blade";
    public static final String PLASMA_BALL = "plasma_ball";
    public static final String RAILGUN_BOLT = "railgun_bolt";

    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final String TINKER_TABLE = "tinkertable";
    public static final String LUX_CAPACITOR = "luxcapacitor";

    /**
     * Armor --------------------------------------------------------------------------------------
     */
    public static final String POWER_ARMOR_HELMET = "powerarmor_head";
    public static final String POWER_ARMOR_CHESTPLATE = "powerarmor_torso";
    public static final String POWER_ARMOR_LEGGINGS = "powerarmor_legs";
    public static final String POWER_ARMOR_BOOTS = "powerarmor_feet";

    /**
     * HandHeld -----------------------------------------------------------------------------------
     */
    public static final String POWER_FIST = "powerfist";

    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final String LEATHER_PLATING_MODULE = "plating_leather";
    public static final String IRON_PLATING_MODULE =  "plating_iron";
    public static final String DIAMOND_PLATING_MODULE = "plating_diamond";
    public static final String ENERGY_SHIELD_MODULE = "energy_shield";

    // Cosmetic -----------------------------------------------------------------------------------
    public static final String TRANSPARENT_ARMOR_MODULE = "transparent_armor";

    // Energy Generation -----------------------------------------------------------------------------
    public static final String SOLAR_GENERATOR_MODULE = "generator_solar";
    public static final String ADVANCED_SOLAR_GENERATOR_MODULE = "generator_solar_adv";
    public static final String KINETIC_GENERATOR_MODULE = "generator_kinetic";
    public static final String THERMAL_GENERATOR_MODULE = "generator_thermal";

    // todo
    // Debug --------------------------------------------------------------------------------------
    public static final String DEBUG = "debug_module";

    // Environmental ------------------------------------------------------------------------------
    public static final String COOLING_SYSTEM_MODULE = "cooling_system";
    public static final String FLUID_TANK_MODULE = "fluid_tank";
    public static final String AUTO_FEEDER_MODULE = "auto_feeder";
    public static final String MOB_REPULSOR_MODULE = "mob_repulsor";
    public static final String WATER_ELECTROLYZER_MODULE = "water_electrolyzer";

    // Mining Enhancements ------------------------------------------------------------------------
    public static final String AOE_PICK_UPGRADE_MODULE = "aoe_pick_upgrade"; // no icon
    public static final String SILK_TOUCH_MODULE = "silk_touch";
    public static final String FORTUNE_MODULE = "fortune";
    public static final String VEIN_MINER_MODULE = "vein_miner";

    // Movement -----------------------------------------------------------------------------------
    public static final String BLINK_DRIVE_MODULE = "blink_drive";
    public static final String CLIMB_ASSIST_MODULE = "climb_assist";
    public static final String DIMENSIONAL_RIFT_MODULE = "dim_rift_gen";
    public static final String FLIGHT_CONTROL_MODULE = "flight_control";
    public static final String GLIDER_MODULE = "glider";
    public static final String JETBOOTS_MODULE = "jet_boots";
    public static final String JETPACK_MODULE = "jetpack";
    public static final String JUMP_ASSIST_MODULE = "jump_assist";
    public static final String PARACHUTE_MODULE = "parachute";
    public static final String SHOCK_ABSORBER_MODULE = "shock_absorber";
    public static final String SPRINT_ASSIST_MODULE = "sprint_assist";
    public static final String SWIM_BOOST_MODULE = "swim_assist";

    // Special ------------------------------------------------------------------------------------
    public static final String CLOCK_MODULE = "clock";
    public static final String COMPASS_MODULE = "compass";
    public static final String ACTIVE_CAMOUFLAGE_MODULE = "invisibility";
    public static final String MAGNET_MODULE = "magnet";
    public static final String PIGLIN_PACIFICATION_MODULE = "piglin_pacification_module";

    // Tools --------------------------------------------------------------------------------------
    public static final String AQUA_AFFINITY_MODULE = "aqua_affinity";
    public static final String AXE_MODULE = "axe";
    public static final String DIAMOND_PICK_UPGRADE_MODULE = "diamond_pick_upgrade";
    public static final String PORTABLE_WORKBENCH_MODULE = "portable_tinkertable";
    public static final String FLINT_AND_STEEL_MODULE = "flint_and_steel";
    public static final String HOE_MODULE = "hoe";
    public static final String LEAF_BLOWER_MODULE = "leaf_blower";
    public static final String LUX_CAPACITOR_MODULE = "luxcapacitor_module";
    public static final String PORTABLE_CRAFTING_MODULE = "portable_crafting_table";
    public static final String PICKAXE_MODULE = "pickaxe";
    public static final String SHEARS_MODULE = "shears";
    public static final String SHOVEL_MODULE = "shovel";

    // Vision -------------------------------------------------------------------------------------
    public static final String BINOCULARS_MODULE = "binoculars";
    public static final String NIGHT_VISION_MODULE = "night_vision";

    // Weapons ------------------------------------------------------------------------------------
    public static final String BLADE_LAUNCHER_MODULE = "blade_launcher";
    public static final String LIGHTNING_MODULE = "lightning_summoner";
    public static final String MELEE_ASSIST_MODULE = "melee_assist";
    public static final String PLASMA_CANNON_MODULE = "plasma_cannon";
    public static final String RAILGUN_MODULE = "railgun";
//    public static final String SONIC_WEAPON_MODULE = "sonic_weapon"; // TODO?

    /**
     * Container ----------------------------------------------------------------------------------
     */
    public static final String MPS_CRAFTING_CONTAINER_TYPE = "mps_crafting_container";
    public static final String TINKERTABLE_CONTAINER_TYPE = "tinkertable_container_type";

    public static final String INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE = "install_salvage_craft_container_type";
    public static final String INSTALL_SALVAGE_CONTAINER_TYPE = "install_salvage_container_type";
    public static final String MODULE_TWEAK_CONTAINER_TYPE = "module_tweak_container_type";

    //-------------------------------------------
    // actual registry names
    public static final ResourceLocation FLUID_TANK_MODULE_REGNAME = getRegName(FLUID_TANK_MODULE);
    public static final ResourceLocation ACTIVE_CAMOUFLAGE_MODULE_REGNAME = getRegName(ACTIVE_CAMOUFLAGE_MODULE);
    public static final ResourceLocation FLIGHT_CONTROL_MODULE_REGNAME = getRegName(FLIGHT_CONTROL_MODULE);
    public static final ResourceLocation MELEE_ASSIST_MODULE_REGNAME =  getRegName(MELEE_ASSIST_MODULE);
    public static final ResourceLocation PICKAXE_MODULE_REGNAME = getRegName(PICKAXE_MODULE);
    public static final ResourceLocation AXE_MODULE_REGNAME = getRegName(AXE_MODULE);
    public static final ResourceLocation SHOVEL_MODULE_REGNAME = getRegName(SHOVEL_MODULE);
    public static final ResourceLocation HOE_MODULE_REGNAME = getRegName(HOE_MODULE);
    public static final ResourceLocation PARACHUTE_MODULE_REGNAME = getRegName(PARACHUTE_MODULE);
    public static final ResourceLocation SPRINT_ASSIST_MODULE_REGNAME =  getRegName(SPRINT_ASSIST_MODULE);
    public static final ResourceLocation BINOCULARS_MODULE_REGNAME = getRegName(BINOCULARS_MODULE);
    public static final ResourceLocation JETPACK_MODULE_REGNAME = getRegName(JETPACK_MODULE);
    public static final ResourceLocation GLIDER_MODULE_REGNAME = getRegName(GLIDER_MODULE);
    public static final ResourceLocation JETBOOTS_MODULE_REGNAME = getRegName(JETBOOTS_MODULE);
    public static final ResourceLocation JUMP_ASSIST_MODULE_REGNAME =getRegName(JUMP_ASSIST_MODULE);
    public static final ResourceLocation KINETIC_GENERATOR_MODULE_REGNAME = getRegName(KINETIC_GENERATOR_MODULE);
    public static final ResourceLocation SHOCK_ABSORBER_MODULE_REGNAME = getRegName(SHOCK_ABSORBER_MODULE);
    public static final ResourceLocation PLASMA_CANNON_MODULE_REGNAME = getRegName(PLASMA_CANNON_MODULE);
    public static final ResourceLocation AUTO_FEEDER_MODULE_REG = getRegName(AUTO_FEEDER_MODULE);
    public static final ResourceLocation CLOCK_MODULE_REG = getRegName(CLOCK_MODULE);
    public static final ResourceLocation COMPASS_MODULE_REG = getRegName(COMPASS_MODULE);
    public static final ResourceLocation PORTABLE_WORKBENCH_MODULE_REG = getRegName(PORTABLE_WORKBENCH_MODULE);
    public static final ResourceLocation PIGLIN_PACIFICATION_MODULE_REGNAME = getRegName(PIGLIN_PACIFICATION_MODULE);

    public static ResourceLocation getRegName(String regNameString) {
        return new ResourceLocation(MPSConstants.MOD_ID, regNameString);
    }
}
