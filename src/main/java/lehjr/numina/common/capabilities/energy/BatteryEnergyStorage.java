package lehjr.numina.common.capabilities.energy;

import lehjr.numina.common.config.NuminaCommonConfig;

public class BatteryEnergyStorage extends ExtendedEnergyStorage {
    int tier;
    public BatteryEnergyStorage(int tier) {
        super();
        this.tier = tier;
    }

    @Override
    public int getMaxEnergyStored() {
        return switch (tier) {
            case 1 -> NuminaCommonConfig.batteryBasicMaxEnergy;
            case 2 -> NuminaCommonConfig.batteryAdvancedMaxEnergy;
            case 3 -> NuminaCommonConfig.batteryEliteMaxEnergy;
            case 4 -> NuminaCommonConfig.batteryUltimateMaxEnergy;
            default -> 0;
        };
    }

    @Override
    public int getMaxTransfer() {
        return switch (tier) {
            case 1 -> NuminaCommonConfig.batteryBasicMaxTransfer;
            case 2 -> NuminaCommonConfig.batteryAdvancedMaxTransfer;
            case 3 -> NuminaCommonConfig.batteryEliteMaxTransfer;
            case 4 -> NuminaCommonConfig.batteryUltimateMaxTransfer;
            default -> 0;
        };
    }
}