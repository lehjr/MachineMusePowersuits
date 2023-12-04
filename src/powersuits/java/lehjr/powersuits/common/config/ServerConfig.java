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

package lehjr.powersuits.common.config;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerConfig {
    /* General ------------------------------------------------------------------------------------------------------- */
    protected ForgeConfigSpec.DoubleValue
            GENERAL_MAX_FLYING_SPEED,
            GENERAL_BASE_MAX_HEAT_POWERFIST,
            GENERAL_BASE_MAX_HEAT_HELMET,
            GENERAL_BASE_MAX_HEAT_CHEST,
            GENERAL_BASE_MAX_HEAT_LEGS,
            GENERAL_BASE_MAX_HEAT_FEET;

    protected ForgeConfigSpec.ConfigValue<List<? extends String>> GENERAL_VEIN_MINER_ORE_LIST;
    protected ForgeConfigSpec.ConfigValue<List<?>>
            GENERAL_VEIN_MINER_BLOCK_LIST,
            GENERAL_MOD_ITEMS_AS_TOOL_MODULES,
            GENERAL_MOD_ITEMS_AS_WEAPON_MODULES;

    /* Cosmetics ----------------------------------------------------------------------------------------------------- */
// Note: these are controlled by the server because the legacy settings can create a vast number
//      of NBT Tags for tracking the settings for each individual model part.
    protected ForgeConfigSpec.BooleanValue
            COSMETIC_USE_LEGACY_COSMETIC_SYSTEM,
            COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS,
            COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN;

    protected ServerConfig(ForgeConfigSpec.Builder builder) {
        /* General --------------------------------------------------------------------------------------------------- */
        builder.comment("General settings").push("General");
        GENERAL_MOD_ITEMS_AS_TOOL_MODULES = builder.comment("Items from other mods to allow as tools in the Power Fist")
                .defineList("externalToolItems", Arrays.asList("ae2:certus_quartz_wrench", "ae2:entropy_manipulator", "scannable:scanner"), o -> o instanceof String && !((String) o).isEmpty());

        GENERAL_MOD_ITEMS_AS_WEAPON_MODULES = builder.comment("Items from other mods to allow as weapons in the Power Fist")
                .defineList("externalWeaponItems", Collections.emptyList(), o -> o instanceof String && !((String) o).isEmpty());

        GENERAL_MAX_FLYING_SPEED = builder.comment("Maximum flight speed (in m/s)")
                .translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED)
                .defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);

        GENERAL_BASE_MAX_HEAT_POWERFIST = builder.comment("PowerFistModel2 Base Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_POWERFIST)
                .defineInRange("baseMaxHeatPowerFist", 5.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_HELMET = builder.comment("Power Armor Helmet Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_HELMET)
                .defineInRange("baseMaxHeatHelmet", 5.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_CHEST = builder.comment("Power Armor Chestplate Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_CHESTPLATE)
                .defineInRange("baseMaxHeatChest", 20.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_LEGS = builder.comment("Power Armor Leggings Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_LEGGINGS)
                .defineInRange("baseMaxHeatLegs", 15.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_FEET = builder.comment("Power Armor Boots Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_FEET)
                .defineInRange("baseMaxHeatFeet", 5.0, 0, 5000);

        GENERAL_VEIN_MINER_ORE_LIST = builder
                .comment("Ore tag list for vein miner module.")
                .translation(MPSConstants.CONFIG_GENERAL_VEIN_MINER_ORE_LIST)
                .worldRestart()
                .defineList("veinMinerOres", Arrays.asList(
                        // metals
                        "forge:ores/iron",
                        "forge:ores/copper",
                        "forge:ores/tin",
                        "forge:ores/lead",
                        "forge:ores/aluminum",
                        "forge:ores/aluminium",
                        "forge:ores/silver",
                        "forge:ores/gold",
                        "forge:ores/cinnabar",
                        "forge:ores/zinc",
                        "forge:ores/uranium",
                        "forge:ores/platinum",
                        "forge:ores/bismuth",
                        "forge:ores/osmium",

                        // non-metal
                        "forge:ores/coal",
                        "forge:ores/redstone",
                        "minecraft:glowstone",
                        "forge:ores/diamond",
                        "forge:ores/lapis",
                        "forge:ores/quartz",
                        "forge:ores/fluorite"
                ), o -> o instanceof String && !((String) o).isEmpty());

        GENERAL_VEIN_MINER_BLOCK_LIST = builder
                .comment("Block registry name whitelist for the vein miner module. \n" +
                        "Use for blocks that don't have an ore tag or to fine tune which blocks to break")
                .translation(MPSConstants.CONFIG_GENERAL_VEIN_MINER_BLOCK_LIST)
                .worldRestart()
                .defineList("veinMinerBlocks", Collections.emptyList(), o -> o instanceof String && !((String) o).isEmpty());
        builder.pop();


        /* Cosmetics ------------------------------------------------------------------------------------------------- */
        builder.comment("Model cosmetic settings").push("Cosmetic");

        COSMETIC_USE_LEGACY_COSMETIC_SYSTEM = builder
                .comment("Use legacy cosmetic configuration instead of cosmetic presets")
                .translation(MPSConstants.CONFIG_COSMETIC_USE_LEGACY_COSMETIC_SYSTEM)
                .worldRestart()
                .define("useLegacyCosmeticSystem", true);

        COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS = builder
                .comment("Allow high polly armor models instead of just skins")
                .translation(MPSConstants.CONFIG_COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS)
                .define("allowHighPollyArmorModuels", true);

        COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN = builder
                .comment("Allow PowerFistModel2 model to be customized")
                .translation(MPSConstants.CONFIG_COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN)
                .define("allowPowerFistCustomization", true);
        builder.pop();

        /* Modules --------------------------------------------------------------------------------------------------- */
        builder.push("Modules");
        {
            {
                builder.push(ModuleCategory.ARMOR.getConfigTitle());
                {
                    builder.push("plating_leather");
                    builder.defineInRange("base_armorPhysical", 3.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_maxHeat", 75.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_knockbackResistance", 0.25D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("plating_iron");
                    builder.defineInRange("base_armorPhysical", 4.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_maxHeat", 300.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_knockbackResistance", 0.25D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("plating_diamond");
                    builder.defineInRange("base_armorPhysical", 5.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_maxHeat", 400.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_knockbackResistance", 0.4000000059604645D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_armorToughness", 2.5D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("plating_netherite");
                    builder.defineInRange("base_armorPhysical", 7.5D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_maxHeat", 750.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_knockbackResistance", 1.5D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_armorToughness", 3.5D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("energy_shield");
                    builder.defineInRange("armorEnergy_fieldStrength_multiplier", 6.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("armorEnergyPerDamage_fieldStrength_multiplier", 5000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("maxHeat_fieldStrength_multiplier", 500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_knockbackResistance", 0.25D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.COSMETIC.getConfigTitle());
                {
                    builder.push("transparent_armor");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.ENERGY_GENERATION.getConfigTitle());
                {
                    builder.push("generator_kinetic");
                    builder.defineInRange("base_energyPerBlock", 2000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyPerBlock_energyGenerated_multiplier", 6000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_movementResistance", 0.009999999776482582D, 0, 1.7976931348623157E308);
                    builder.defineInRange("movementResistance_energyGenerated_multiplier", 0.49000000953674316D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("generator_solar");
                    builder.defineInRange("base_daytimeEnergyGen", 15000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_nightTimeEnergyGen", 1500.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("generator_solar_adv");
                    builder.defineInRange("base_daytimeEnergyGen", 45000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_nightTimeEnergyGen", 1500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_daytimeHeatGen", 15.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_nightTimeHeatGen", 5.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("generator_thermal");
                    builder.defineInRange("base_energyPerBlock", 250.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyPerBlock_energyGenerated_multiplier", 250.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.ENVIRONMENTAL.getConfigTitle());
                {
                    builder.push("fluid_tank");
                    builder.defineInRange("base_fluidTankSize", 20000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_heatActivationPercent", 0.5D, 0, 1.7976931348623157E308);
                    builder.defineInRange("heatActivationPercent_activationPercent_multiplier", 0.5D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("auto_feeder");
                    builder.defineInRange("base_energyCon", 100.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_autoFeederEfficiency", 50.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_efficiency_multiplier", 1000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("autoFeederEfficiency_efficiency_multiplier", 50.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("cooling_system");
                    builder.defineInRange("coolingBonen_usergyCon_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_energyCon_multiplier", 40.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("water_electrolyzer");
                    builder.defineInRange("base_energyCon", 10000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("mob_repulsor");
                    builder.defineInRange("base_energyCon", 2500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.MINING_ENHANCEMENT.getConfigTitle());
                {
                    builder.push("aoe_pick_upgrade");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_diameter_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("aoeMiningDiameter_diameter_multiplier", 5, 0, 2147483647);
                    builder.pop();
                }
                {
                    builder.push("selective_miner");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_aoe2Limit", 1.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("aoe2Limit_aoe2Limit_multiplier", 59, 0, 2147483647);
                    builder.pop();
                }
                {
                    builder.push("fortune");
                    builder.defineInRange("base_fortuneEnCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("fortuneEnCon_enchLevel_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("fortuneLevel_enchLevel_multiplier", 3, 0, 2147483647);
                    builder.pop();
                }
                {
                    builder.push("tunnel_bore");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_diameter_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("aoeMiningDiameter_diameter_multiplier", 5, 0, 2147483647);
                    builder.pop();
                }
                {
                    builder.push("vein_miner");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("silk_touch");
                    builder.defineInRange("base_silkTouchEnCon", 2500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("aqua_affinity");
                    builder.defineInRange("base_energyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_aquaHarvSpeed", 0.20000000298023224D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_power_multiplier", 1000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("aquaHarvSpeed_power_multiplier", 0.800000011920929D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.MOVEMENT.getConfigTitle());
                {
                    builder.push("jump_assist");
                    builder.defineInRange("base_energyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_power_multiplier", 250.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("multiplier_power_multiplier", 4.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_compensation_multiplier", 50.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_sprintExComp", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("sprintExComp_compensation_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("swim_assist");
                    builder.defineInRange("energyCon_thrust_multiplier", 1000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("underwaterMovBoost_thrust_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("parachute");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("dim_rift_gen");
                    builder.defineInRange("base_heatGen", 55.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_energyCon", 200000.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("sprint_assist");
                    builder.defineInRange("base_sprintEnergyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("sprintEnergyCon_sprintAssist_multiplier", 5000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_sprintSpeedMult", 0.1D, 0, 1.7976931348623157E308);
                    builder.defineInRange("sprintSpeedMult_sprintAssist_multiplier", 2.49D, 0, 1.7976931348623157E308);
                    builder.defineInRange("sprintEnergyCon_compensation_multiplier", 2000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_sprintExComp", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("sprintExComp_compensation_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_walkingEnergyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("walkingEnergyCon_walkingAssist_multiplier", 5000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_walkingSpeedMult", 0.01D, 0, 1.7976931348623157E308);
                    builder.defineInRange("walkingSpeedMult_walkingAssist_multiplier", 1.99D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("jetpack");
                    builder.defineInRange("base_energyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_jetpackThrust", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_thrust_multiplier", 15000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("jetpackThrust_thrust_multiplier", 0.1599999964237213D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("climb_assist");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("shock_absorber");
                    builder.defineInRange("base_energyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_power_multiplier", 100.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_multiplier", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("multiplier_power_multiplier", 10.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("flight_control");
                    builder.defineInRange("yLookRatio_vertically_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("glider");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("jet_boots");
                    builder.defineInRange("base_energyCon", 0.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_jetbootsThrust", 0.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_thrust_multiplier", 750.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("jetbootsThrust_thrust_multiplier", 0.07999999821186066D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("blink_drive");
                    builder.defineInRange("base_energyCon", 10000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_blinkDriveRange", 5.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_range_multiplier", 30000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("blinkDriveRange_range_multiplier", 59.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.SPECIAL.getConfigTitle());
                {
                    builder.push("piglin_pacification_module");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("magnet");
                    builder.defineInRange("base_radius", 1.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_energyCon", 5.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_radius_multiplier", 2000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("radius_radius_multiplier", 9, 0, 2147483647);
                    builder.pop();
                }
                {
                    builder.push("invisibility");
                    builder.defineInRange("base_invisibilityEnergy", 100.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("item_minecraft_clock");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("item_minecraft_compass");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.TOOL.getConfigTitle());
                {
                    builder.push("block_powersuits_tinkertable");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("item_refinedstorage_wireless_grid");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("luxcapacitor_module");
                    builder.defineInRange("base_energyCon", 1000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("redHue_red_multiplier", 1.0D, 0, 1.0D);
                    builder.defineInRange("greenHue_green_multiplier", 1.0D, 0, 1.0D);
                    builder.defineInRange("blueHue_blue_multiplier", 1.0D, 0, 1.0D);
                    builder.defineInRange("opacity_alpha_multiplier", 1.0D, 0, 1.0D);
                    builder.pop();
                }
                {
                    builder.push("diamond_pick_upgrade");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("pickaxe");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_harvestSpeed", 8.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_overclock_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("harvestSpeed_overclock_multiplier", 52.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("shovel");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_harvestSpeed", 8.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_overclock_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("harvestSpeed_overclock_multiplier", 22.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("item_appliedenergistics2_wireless_terminal");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("leaf_blower");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_radius_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_radius", 1.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("radius_radius_multiplier", 15.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("item_scannable_scanner");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("axe");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_harvestSpeed", 8.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_overclock_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("harvestSpeed_overclock_multiplier", 22.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("item_refinedstorage_wireless_fluid_grid");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("shears");
                    builder.defineInRange("base_energyCon", 1000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_harvestSpeed", 8.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("block_minecraft_crafting_table");
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("flint_and_steel");
                    builder.defineInRange("base_energyCon", 10000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("hoe");
                    builder.defineInRange("base_energyCon", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("energyCon_radius_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("radius_radius_multiplier", 8, 0, 2147483647);
                    builder.defineInRange("base_harvestSpeed", 8.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_overclock_multiplier", 9500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("harvestSpeed_overclock_multiplier", 22.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.VISION.getConfigTitle());
                {
                    builder.push("night_vision");
                    builder.defineInRange("base_energyCon", 100.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.pop();
                }
                {
                    builder.push("binoculars");
                    builder.defineInRange("base_fieldOfView", 0.5D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("fieldOfView_fOVMult_multiplier", 9.5D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
            {
                builder.push(ModuleCategory.WEAPON.getConfigTitle());
                {
                    builder.push("plasma_cannon");
                    builder.defineInRange("base_plasmaEnergyPerTick", 100.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_plasmaDamage", 2.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("plasmaEnergyPerTick_amperage_multiplier", 1500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("plasmaDamage_amperage_multiplier", 38.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("plasmaEnergyPerTick_voltage_multiplier", 500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("plasmaExplosiveness_voltage_multiplier", 0.5D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("melee_assist");
                    builder.defineInRange("base_energyCon", 10.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_meleeDamage", 2.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_impact_multiplier", 1000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("meleeDamage_impact_multiplier", 8.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("energyCon_carryThrough_multiplier", 200.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("meleeKnockback_carryThrough_multiplier", 1.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("blade_launcher");
                    builder.defineInRange("base_energyCon", 5000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_spinBladeDam", 6.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("railgun");
                    builder.defineInRange("base_railgunTotalImpulse", 500.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_railgunEnergyCost", 5000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("base_railgunHeatEm", 2.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("railgunTotalImpulse_voltage_multiplier", 2500.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("railgunEnergyCost_voltage_multiplier", 25000.0D, 0, 1.7976931348623157E308);
                    builder.defineInRange("railgunHeatEm_voltage_multiplier", 10.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                {
                    builder.push("lightning_summoner");
                    builder.defineInRange("base_energyCon", 4900000.0D, 0, 1.7976931348623157E308);
                    builder.define("isAllowed", true);
                    builder.defineInRange("base_heatEmission", 100.0D, 0, 1.7976931348623157E308);
                    builder.pop();
                }
                builder.pop();
            }
        }
        builder.pop();
    }
}
