package lehjr.numina.common.capabilities.energy;

import lehjr.numina.common.config.CommonConfig;

public class BatteryEnergyStorage extends ExtendedEnergyStorage {
    int tier;
    public BatteryEnergyStorage(int tier) {
        super();
        this.tier = tier;
    }

    @Override
    public int getMaxEnergyStored() {
        return switch (tier) {
            case 1 -> CommonConfig.batteryBasicMaxEnergy;
            case 2 -> CommonConfig.batteryAdvancedMaxEnergy;
            case 3 -> CommonConfig.batteryEliteMaxEnergy;
            case 4 -> CommonConfig.batteryUltimateMaxEnergy;
            default -> 0;
        };
    }

    @Override
    public int getMaxTransfer() {
        return switch (tier) {
            case 1 -> CommonConfig.batteryBasicMaxTransfer;
            case 2 -> CommonConfig.batteryAdvancedMaxTransfer;
            case 3 -> CommonConfig.batteryEliteMaxTransfer;
            case 4 -> CommonConfig.batteryUltimateMaxTransfer;
            default -> 0;
        };
    }
}