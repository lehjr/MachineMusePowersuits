package lehjr.powersuits.common.config;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class PowerFistConfig {
    // Tier 1
    private static final ModConfigSpec.Builder POWER_FIST_1__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Power_Fist").push("Tier_1");
    private static final ModConfigSpec.IntValue POWER_FIST_1__INVENTORY_SLOTS = POWER_FIST_1__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 5, 0, 10);
    private static final ModConfigSpec.DoubleValue POWER_FIST_1__MAX_HEAT = POWER_FIST_1__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 2
    private static final ModConfigSpec.Builder POWER_FIST_2__SETTINGS_BUILDER = POWER_FIST_1__SETTINGS_BUILDER.pop().push("Tier_2");
    private static final ModConfigSpec.IntValue POWER_FIST_2__INVENTORY_SLOTS = POWER_FIST_2__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 12, 0, 60);
    private static final ModConfigSpec.DoubleValue POWER_FIST_2__MAX_HEAT = POWER_FIST_2__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 3
    private static final ModConfigSpec.Builder POWER_FIST_3__SETTINGS_BUILDER = POWER_FIST_2__SETTINGS_BUILDER.pop().push("Tier_3");
    private static final ModConfigSpec.IntValue POWER_FIST_3__INVENTORY_SLOTS = POWER_FIST_3__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 30, 0, 100);
    private static final ModConfigSpec.DoubleValue POWER_FIST_3__MAX_HEAT = POWER_FIST_3__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);

    // Tier 4
    private static final ModConfigSpec.Builder POWER_FIST_4__SETTINGS_BUILDER = POWER_FIST_3__SETTINGS_BUILDER.pop().push("Tier_4");
    private static final ModConfigSpec.IntValue POWER_FIST_4__INVENTORY_SLOTS = POWER_FIST_4__SETTINGS_BUILDER.defineInRange(MPSConstants.CONFIG_INVENTORY_SLOTS, 60, 0, 180);
    private static final ModConfigSpec.DoubleValue POWER_FIST_4__MAX_HEAT = POWER_FIST_4__SETTINGS_BUILDER.defineInRange(NuminaConstants.MAXIMUM_HEAT, 50.0, 0, 1000.0D);


    public static int powerFistInventorySlots1;
    public static double powerFistMaxHeat1;

    public static int powerFistInventorySlots2;
    public static double powerFistMaxHeat2;

    public static int powerFistInventorySlots3;
    public static double powerFistMaxHeat3;

    public static int powerFistInventorySlots4;
    public static double powerFistMaxHeat4;

    public static final ModConfigSpec MPS_POWER_FIST_CONFIG_SPEC = POWER_FIST_4__SETTINGS_BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_POWER_FIST_CONFIG_SPEC) {
            NuminaLogger.logDebug("power fist config onLoad: " + event.getConfig() + ", " + event.getConfig().getFileName());

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

            NuminaLogger.logDebug("powerFistInventorySlots1: " + powerFistInventorySlots1);
            NuminaLogger.logDebug("powerFistInventorySlots2: " + powerFistInventorySlots2);
            NuminaLogger.logDebug("powerFistInventorySlots3: " + powerFistInventorySlots3);
            NuminaLogger.logDebug("powerFistInventorySlots4: " + powerFistInventorySlots4);
        }
    }
}
