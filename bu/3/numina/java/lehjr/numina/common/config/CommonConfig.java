package lehjr.numina.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    /**
     * Charging Base ------------------------------------------------------------------------------
     */
    private static final ModConfigSpec.Builder CHARGING_BASE_BUILDER = new ModConfigSpec.Builder().push("ChargingBase settings");

    private static final ModConfigSpec.IntValue CHARGING_BASE_MAX_ENERGY = CHARGING_BASE_BUILDER
            .comment("Max base power for charging base")
            .defineInRange("chargingBaseMaxEnergy", 1000000, 100, 10000000);

    private static final ModConfigSpec.IntValue CHARGING_BASE_MAX_TRANSFER = CHARGING_BASE_BUILDER
            .comment("Max power transfer for charging base")
            .defineInRange("chargingBaseMaxTransfer", 10000, 10, 1000000);

    /**
     * Batteries ----------------------------------------------------------------------------------
     */

    // Basic
    private static final ModConfigSpec.Builder BATTERIES_BUILDER1 = CHARGING_BASE_BUILDER.pop().push("Batteries").push(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME);

    private static final ModConfigSpec.IntValue BATTERY_BASIC__MAX_ENERGY = BATTERIES_BUILDER1.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue BATTERY_BASIC__MAX_TRANSFER = BATTERIES_BUILDER1.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue BATTERY_BASIC__IS_ALLOWED = BATTERIES_BUILDER1.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Advanced
    private static final ModConfigSpec.Builder BATTERIES_BUILDER2 = BATTERIES_BUILDER1.pop().push(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME);
    private static final ModConfigSpec.IntValue BATTERY_ADVANCED__MAX_ENERGY = BATTERIES_BUILDER2.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue BATTERY_ADVANCED__MAX_TRANSFER = BATTERIES_BUILDER2.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue BATTERY_ADVANCED__IS_ALLOWED = BATTERIES_BUILDER2.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Elite
    private static final ModConfigSpec.Builder BATTERIES_BUILDER3 = BATTERIES_BUILDER2.pop().push(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME);
    private static final ModConfigSpec.IntValue BATTERY_ELITE__MAX_ENERGY = BATTERIES_BUILDER3.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue BATTERY_ELITE__MAX_TRANSFER = BATTERIES_BUILDER3.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue BATTERY_ELITE__IS_ALLOWED = BATTERIES_BUILDER3.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Ultimate
    private static final ModConfigSpec.Builder BATTERIES_BUILDER4 = BATTERIES_BUILDER3.pop().push(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME);
    private static final ModConfigSpec.IntValue BATTERY_ULTIMATE__MAX_ENERGY = BATTERIES_BUILDER4.defineInRange(NuminaConstants.CONFIG_MAX_ENERGY, 1000000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue BATTERY_ULTIMATE__MAX_TRANSFER = BATTERIES_BUILDER4.defineInRange(NuminaConstants.CONFIG_MAX_TRANSFER, 1250000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue BATTERY_ULTIMATE__IS_ALLOWED = BATTERIES_BUILDER4.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder BUILDER = BATTERIES_BUILDER4.pop(2);

    public static final ModConfigSpec COMMON_SPEC = BUILDER.build();


    public static int chargingBaseMaxEnergy;
    public static int chargingBaseMaxTransfer;


    public static int batteryBasicMaxEnergy;
    public static int batteryBasicMaxTransfer;
    public static boolean batteryBasicIsAllowed;

    public static int batteryAdvancedMaxEnergy;
    public static int batteryAdvancedMaxTransfer;
    public static boolean batteryAdvancedIsAllowed;

    public static int batteryEliteMaxEnergy;
    public static int batteryEliteMaxTransfer;
    public static boolean batteryEliteIsAllowed;

    public static int batteryUltimateMaxEnergy;
    public static int batteryUltimateMaxTransfer;
    public static boolean batteryUltimateIsAllowed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == COMMON_SPEC) {
            chargingBaseMaxEnergy = CHARGING_BASE_MAX_ENERGY.getAsInt();
            chargingBaseMaxTransfer = CHARGING_BASE_MAX_TRANSFER.getAsInt();

            batteryBasicMaxEnergy = BATTERY_BASIC__MAX_ENERGY.getAsInt();
            batteryBasicMaxTransfer = BATTERY_BASIC__MAX_TRANSFER.getAsInt();
            batteryBasicIsAllowed = BATTERY_BASIC__IS_ALLOWED.getAsBoolean();

            batteryAdvancedMaxEnergy = BATTERY_ADVANCED__MAX_ENERGY.get();
            batteryAdvancedMaxTransfer = BATTERY_ADVANCED__MAX_TRANSFER.getAsInt();
            batteryAdvancedIsAllowed = BATTERY_ADVANCED__IS_ALLOWED.getAsBoolean();

            batteryEliteMaxEnergy = BATTERY_ELITE__MAX_ENERGY.getAsInt();
            batteryEliteMaxTransfer = BATTERY_ELITE__MAX_TRANSFER.getAsInt();
            batteryEliteIsAllowed = BATTERY_ELITE__IS_ALLOWED.getAsBoolean();

            batteryUltimateMaxEnergy = BATTERY_ULTIMATE__MAX_ENERGY.getAsInt();
            batteryUltimateMaxTransfer = BATTERY_ULTIMATE__MAX_TRANSFER.getAsInt();
            batteryUltimateIsAllowed = BATTERY_ULTIMATE__IS_ALLOWED.getAsBoolean();
        }
    }
}