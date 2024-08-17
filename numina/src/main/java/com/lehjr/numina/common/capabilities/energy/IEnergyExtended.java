package com.lehjr.numina.common.capabilities.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IEnergyExtended extends IEnergyStorage {
    int getMaxTransfer();

    @Override
    int getMaxEnergyStored();
}
