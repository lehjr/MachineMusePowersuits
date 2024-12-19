package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MiningEnchantmentModuleConfig {
    // AquaAffinity
    private static final ModConfigSpec.Builder AQUA_AFINITY_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Mining_Enchantment").push("Aqua_Affinity");
    private static final ModConfigSpec.BooleanValue AQUA_AFINITY_MODULE__IS_ALLOWED = AQUA_AFINITY_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue AQUA_AFINITY_MODULE__ENERGY_CONSUMPTION_BASE = AQUA_AFINITY_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 50000, 0, 10000000.0D);

    // Fortune
    private static final ModConfigSpec.Builder FORTUNE_MODULE__SETTINGS_BUILDER = AQUA_AFINITY_MODULE__SETTINGS_BUILDER .pop().push("Fortune");
    private static final ModConfigSpec.BooleanValue FORTUNE_MODULE__IS_ALLOWED = FORTUNE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue FORTUNE_MODULE__ENERGY_CONSUMPTION_BASE = FORTUNE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue FORTUNE_MODULE__ENERGY_CONSUMPTION_ENCHANTMENT_LEVEL_MULTIPLIER = FORTUNE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION + MPSConstants.ENCHANTMENT_LEVEL + MPSConstants.MULTIPLIER, 9500, 0, 10000000.0D);

    // Silk Touch
    private static final ModConfigSpec.Builder SILK_TOUCH_MODULE__SETTINGS_BUILDER = FORTUNE_MODULE__SETTINGS_BUILDER.pop().push("Silk_Touch");
    private static final ModConfigSpec.BooleanValue SILK_TOUCH_MODULE__IS_ALLOWED = SILK_TOUCH_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SILK_TOUCH_MODULE__ENERGY_CONSUMPTION_BASE = SILK_TOUCH_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 50000, 0, 10000000.0D);

    // AquaAffinity
    public static boolean aquaAffinityModuleIsAllowed;
    public static double aquaAffinityModuleEnergyConsumptionBase;

    // Fortune
    public static boolean fortuneModuleIsAllowed;
    public static double fortuneModuleEnergyConsumptionBase;
    public static double fortuneModuleEnergyConsumptionEnchantmentMultiplier;

    // Silk Touch
    public static boolean silkTouchModuleIsAllowed;
    public static double silkTouchModuleEnergyConsumptionBase;


    public static final ModConfigSpec MINING_ENCHANTMENT_MODULE_CONFIG_SPEC = SILK_TOUCH_MODULE__SETTINGS_BUILDER.build();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MINING_ENCHANTMENT_MODULE_CONFIG_SPEC) {

            // AquaAffinity
            aquaAffinityModuleIsAllowed = AQUA_AFINITY_MODULE__IS_ALLOWED.get();
            aquaAffinityModuleEnergyConsumptionBase = AQUA_AFINITY_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Fortune
            fortuneModuleIsAllowed = FORTUNE_MODULE__IS_ALLOWED.get();
            fortuneModuleEnergyConsumptionBase = FORTUNE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            fortuneModuleEnergyConsumptionEnchantmentMultiplier = FORTUNE_MODULE__ENERGY_CONSUMPTION_ENCHANTMENT_LEVEL_MULTIPLIER.get();

            // Silk Touch
            silkTouchModuleIsAllowed = SILK_TOUCH_MODULE__IS_ALLOWED.get();
            silkTouchModuleEnergyConsumptionBase = SILK_TOUCH_MODULE__ENERGY_CONSUMPTION_BASE.get();
        }
    }
}
