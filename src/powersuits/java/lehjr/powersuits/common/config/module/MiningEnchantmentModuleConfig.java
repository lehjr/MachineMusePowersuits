package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class MiningEnchantmentModuleConfig {
    // AquaAffinity
    private static final ForgeConfigSpec.Builder AQUA_AFINITY_MODULE__SETTINGS_BUILDER = new ForgeConfigSpec.Builder().push("Mining_Enchantment").push("Aqua_Affinity");
    private static final ForgeConfigSpec.BooleanValue AQUA_AFINITY_MODULE__IS_ALLOWED = AQUA_AFINITY_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue AQUA_AFINITY_MODULE__ENERGY_CONSUMPTION_BASE = AQUA_AFINITY_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 50000, 0, 10000000.0D);

    // Fortune
    private static final ForgeConfigSpec.Builder FORTUNE_MODULE__SETTINGS_BUILDER = AQUA_AFINITY_MODULE__SETTINGS_BUILDER .pop().push("Fortune");
    private static final ForgeConfigSpec.BooleanValue FORTUNE_MODULE__IS_ALLOWED = FORTUNE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue FORTUNE_MODULE__ENERGY_CONSUMPTION_BASE = FORTUNE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 10000000.0D);
    private static final ForgeConfigSpec.DoubleValue FORTUNE_MODULE__ENERGY_CONSUMPTION_ENCHANTMENT_LEVEL_MULTIPLIER = FORTUNE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION + MPSConstants.ENCHANTMENT_LEVEL + MPSConstants.MULTIPLIER, 9500, 0, 10000000.0D);

    // Silk Touch
    private static final ForgeConfigSpec.Builder SILK_TOUCH_MODULE__SETTINGS_BUILDER = FORTUNE_MODULE__SETTINGS_BUILDER.pop().push("Silk_Touch");
    private static final ForgeConfigSpec.BooleanValue SILK_TOUCH_MODULE__IS_ALLOWED = SILK_TOUCH_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue SILK_TOUCH_MODULE__ENERGY_CONSUMPTION_BASE = SILK_TOUCH_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 50000, 0, 10000000.0D);

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


    public static final ForgeConfigSpec MINING_ENCHANTMENT_MODULE_CONFIG_SPEC = SILK_TOUCH_MODULE__SETTINGS_BUILDER.build();

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
