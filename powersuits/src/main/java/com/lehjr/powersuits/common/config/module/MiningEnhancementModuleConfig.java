package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MiningEnhancementModuleConfig {
    private static final ModConfigSpec.Builder SELECTIVE_MINER_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Mining_Enhancement").push("Selective_Miner");
    private static final ModConfigSpec.BooleanValue SELECTIVE_MINER_MODULE__IS_ALLOWED = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder TUNNEL_BORE_MODULE__SETTINGS_BUILDER = SELECTIVE_MINER_MODULE__SETTINGS_BUILDER.pop().push("Tunnel_Bore");
    private static final ModConfigSpec.BooleanValue TUNNEL_BORE_MODULE__IS_ALLOWED = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_BASE = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_DIAMETER_MULTIPLIER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__DIAMETER_MULTIPLIER, 9500, 0, 100000.0D);

    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__HARVEST_SPEED_BASE = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue TUNNEL_BORE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);







    private static final ModConfigSpec.Builder VEIN_MINER_MODULE__SETTINGS_BUILDER = TUNNEL_BORE_MODULE__SETTINGS_BUILDER.pop().push("Vein_Miner");
    private static final ModConfigSpec.BooleanValue VEIN_MINER_MODULE__IS_ALLOWED = VEIN_MINER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);



    // selectiveMinerModule
    public static boolean tunnelBoreModuleIsAllowed;

    public static double tunnelBoreModuleEnergyConsumptionBase;
    public static double tunnelBoreModuleEnergyConsumptionOverclockMultiplier;
    public static double tunnelBoreModuleEnergyConsumptionDiameterMultiplier;
    public static double tunnelBoreModuleHarvestSpeedBase;
    public static double tunnelBoreModuleHarvestSpeedOverclockMultiplier;






    // veinMinerModule

    public static final ModConfigSpec MINING_ENHANCEMENT_MODULE_SPEC = VEIN_MINER_MODULE__SETTINGS_BUILDER.build();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MINING_ENHANCEMENT_MODULE_SPEC) {

            // Tunnel Bore
            tunnelBoreModuleIsAllowed = TUNNEL_BORE_MODULE__IS_ALLOWED.get();
            tunnelBoreModuleEnergyConsumptionBase = TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            tunnelBoreModuleEnergyConsumptionOverclockMultiplier = TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            tunnelBoreModuleEnergyConsumptionDiameterMultiplier = TUNNEL_BORE_MODULE__ENERGY_CONSUMPTION_DIAMETER_MULTIPLIER.get();

            tunnelBoreModuleHarvestSpeedBase = TUNNEL_BORE_MODULE__HARVEST_SPEED_BASE.get();
            tunnelBoreModuleHarvestSpeedOverclockMultiplier = TUNNEL_BORE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
        }
    }
}
