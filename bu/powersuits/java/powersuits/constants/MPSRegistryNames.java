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

package lehjr.powersuits.constants;

import net.minecraft.util.ResourceLocation;

public class MPSRegistryNames {





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
