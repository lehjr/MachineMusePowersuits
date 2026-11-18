package lehjr.powersuits.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class PowerFistConfig {
    // Tier 1
    private static final ForgeConfigSpec.Builder POWER_FIST_1__SETTINGS_BUILDER = new ForgeConfigSpec.Builder().push("Power_Fist").push("Tier_1");
    private static final ForgeConfigSpec.IntValue POWER_FIST_1__INVENTORY_SLOTS = POWER_FIST_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 7, 0, 10);
    private static final ForgeConfigSpec.DoubleValue POWER_FIST_1__MAX_HEAT = POWER_FIST_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 2
    private static final ForgeConfigSpec.Builder POWER_FIST_2__SETTINGS_BUILDER = POWER_FIST_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ForgeConfigSpec.IntValue POWER_FIST_2__INVENTORY_SLOTS = POWER_FIST_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 12, 0, 60);
    private static final ForgeConfigSpec.DoubleValue POWER_FIST_2__MAX_HEAT = POWER_FIST_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 3
    private static final ForgeConfigSpec.Builder POWER_FIST_3__SETTINGS_BUILDER = POWER_FIST_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ForgeConfigSpec.IntValue POWER_FIST_3__INVENTORY_SLOTS = POWER_FIST_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 30, 0, 100);
    private static final ForgeConfigSpec.DoubleValue POWER_FIST_3__MAX_HEAT = POWER_FIST_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 4
    private static final ForgeConfigSpec.Builder POWER_FIST_4__SETTINGS_BUILDER = POWER_FIST_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ForgeConfigSpec.IntValue POWER_FIST_4__INVENTORY_SLOTS = POWER_FIST_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 60, 0, 180);
    private static final ForgeConfigSpec.DoubleValue POWER_FIST_4__MAX_HEAT = POWER_FIST_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);


    public static int powerFistInventorySlots1;
    public static double powerFistMaxHeat1;

    public static int powerFistInventorySlots2;
    public static double powerFistMaxHeat2;

    public static int powerFistInventorySlots3;
    public static double powerFistMaxHeat3;

    public static int powerFistInventorySlots4;
    public static double powerFistMaxHeat4;

    public static final ForgeConfigSpec POWER_FIST_CONFIG_SPEC = POWER_FIST_4__SETTINGS_BUILDER.build();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == POWER_FIST_CONFIG_SPEC) {

            // Tier 1
            powerFistInventorySlots1 = POWER_FIST_1__INVENTORY_SLOTS.get();
            powerFistMaxHeat1 = POWER_FIST_1__MAX_HEAT.get();
            // Tier 1
            powerFistInventorySlots2 = POWER_FIST_2__INVENTORY_SLOTS.get();
            powerFistMaxHeat2 = POWER_FIST_2__MAX_HEAT.get();
            // Tier 1
            powerFistInventorySlots3 = POWER_FIST_3__INVENTORY_SLOTS.get();
            powerFistMaxHeat3 = POWER_FIST_3__MAX_HEAT.get();
            // Tier 1
            powerFistInventorySlots4 = POWER_FIST_4__INVENTORY_SLOTS.get();
            powerFistMaxHeat4 = POWER_FIST_4__MAX_HEAT.get();
        }
    }
}
