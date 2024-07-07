package lehjr.numina.common.capability.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyExtended extends IEnergyStorage {
    int getMaxTransfer();

    @Override
    int getMaxEnergyStored();
}
