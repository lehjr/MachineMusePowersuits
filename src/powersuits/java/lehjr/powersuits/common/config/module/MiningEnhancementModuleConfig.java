package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MiningEnhancementModuleConfig {
    // Selective Miner
    private static final ModConfigSpec.Builder SELECTIVE_MINER_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Mining_Enhancement").push("Selective_Miner");
    private static final ModConfigSpec.BooleanValue SELECTIVE_MINER_MODULE__IS_ALLOWED = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SELECTIVE_MINER_MODULE__ENERGY_CONSUMPTION_BASE = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SELECTIVE_MINER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SELECTIVE_MINER_MODULE__HARVEST_SPEED_BASE = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue SELECTIVE_MINER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);
    private static final ModConfigSpec.IntValue SELECTIVE_MINER_MODULE__BLOCK_LIMIT = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 64, 1, 1000);

    // Tunnel Bore
    private static final ModConfigSpec.Builder TUNNEL_BORE_MODULE__SETTINGS_BUILDER = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.pop().push("Tunnel_Bore");
    private static final ModConfigSpec.BooleanValue TUNNEL_BORE_MODULE__IS_ALLOWED = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_BASE = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__HARVEST_SPEED_BASE = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);
    private static final ModConfigSpec.IntValue TUNNEL_BORE_MODULE__MAX_DIAMETER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.MINING_DIAMETER_MAX, 7, 3, 21);

    // Vein Miner
    private static final ModConfigSpec.Builder VEIN_MINER_MODULE__SETTINGS_BUILDER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.pop().push("Vein_Miner");
    private static final ModConfigSpec.BooleanValue VEIN_MINER_MODULE__IS_ALLOWED = VEIN_MINER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue VEIN_MINER_MODULE__ENERGY_CONSUMPTION_BASE = VEIN_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue VEIN_MINER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = VEIN_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue VEIN_MINER_MODULE__HARVEST_SPEED_BASE = VEIN_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue VEIN_MINER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = VEIN_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);
    private static final ModConfigSpec.IntValue VEIN_MINER_MODULE__BLOCK_LIMIT = VEIN_MINER_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 64, 1, 1000);


    // Selective Miner
    public static boolean selectiveMinerModuleIsAllowed;
    public static double selectiveMinerModuleEnergyConsumptionBase;
    public static double selectiveMinerModuleEnergyConsumptionOverclockMultiplier;
    public static double selectiveMinerModuleHarvestSpeedBase;
    public static double selectiveMinerModuleHarvestSpeedOverclockMultiplier;
    public static int selectiveMinerModuleBlockLimit;

    // Tunnel Bore
    public static boolean tunnelBoreModuleIsAllowed;
    public static double tunnelBoreModuleEnergyConsumptionBase;
    public static double tunnelBoreModuleEnergyConsumptionOverclockMultiplier;
    public static double tunnelBoreModuleHarvestSpeedBase;
    public static double tunnelBoreModuleHarvestSpeedOverclockMultiplier;
    public static int tunnelBoreModuleMaxDiameter;

    // Vein Miner Module
    public static boolean veinMinerModuleIsAllowed;
    public static double veinMinerModuleEnergyConsumptionBase;
    public static double veinMinerModuleEnergyConsumptionOverclockMultiplier;
    public static double veinMinerModuleHarvestSpeedBase;
    public static double veinMinerModuleHarvestSpeedOverclockMultiplier;
    public static int veinMinerModuleBlockLimit;

    public static final ModConfigSpec MINING_ENHANCEMENT_MODULE_SPEC = VEIN_MINER_MODULE__SETTINGS_BUILDER.build();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MINING_ENHANCEMENT_MODULE_SPEC) {
            // Selective Miner
            selectiveMinerModuleIsAllowed = SELECTIVE_MINER_MODULE__IS_ALLOWED.get();
            selectiveMinerModuleEnergyConsumptionBase = SELECTIVE_MINER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            selectiveMinerModuleEnergyConsumptionOverclockMultiplier = SELECTIVE_MINER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            selectiveMinerModuleHarvestSpeedBase = SELECTIVE_MINER_MODULE__HARVEST_SPEED_BASE.get();
            selectiveMinerModuleHarvestSpeedOverclockMultiplier = SELECTIVE_MINER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            selectiveMinerModuleBlockLimit = SELECTIVE_MINER_MODULE__BLOCK_LIMIT.get();

            // Tunnel Bore
            tunnelBoreModuleIsAllowed = TUNNEL_BORE_MODULE__IS_ALLOWED.get();
            tunnelBoreModuleEnergyConsumptionBase = TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            tunnelBoreModuleEnergyConsumptionOverclockMultiplier = TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            tunnelBoreModuleHarvestSpeedBase = TUNNEL_BORE_MODULE__HARVEST_SPEED_BASE.get();
            tunnelBoreModuleHarvestSpeedOverclockMultiplier = TUNNEL_BORE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            int maxDiameter = TUNNEL_BORE_MODULE__MAX_DIAMETER.get() - 2;
            if ((maxDiameter % 2) == 0) {
                tunnelBoreModuleMaxDiameter = maxDiameter;
            } else {
                tunnelBoreModuleMaxDiameter = maxDiameter + 1;
            }

            // Vein Miner
            veinMinerModuleIsAllowed = VEIN_MINER_MODULE__IS_ALLOWED.get();
            veinMinerModuleEnergyConsumptionBase = VEIN_MINER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            veinMinerModuleEnergyConsumptionOverclockMultiplier = VEIN_MINER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            veinMinerModuleHarvestSpeedBase = VEIN_MINER_MODULE__HARVEST_SPEED_BASE.get();
            veinMinerModuleHarvestSpeedOverclockMultiplier = VEIN_MINER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            veinMinerModuleBlockLimit = VEIN_MINER_MODULE__BLOCK_LIMIT.get();
        }
    }
}
