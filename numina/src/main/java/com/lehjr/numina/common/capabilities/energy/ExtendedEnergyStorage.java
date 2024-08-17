package com.lehjr.numina.common.capabilities.energy;

import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.EnergyStorage;

public abstract class ExtendedEnergyStorage extends EnergyStorage implements IEnergyExtended, INBTSerializable<Tag> {
    public ExtendedEnergyStorage() {
        super(0);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(getMaxTransfer(), maxReceive));
        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }
        int energyExtracted = Math.min(energy, Math.min(getMaxTransfer(), maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public boolean canExtract() {
        return getMaxEnergyStored() > 0;
    }

    @Override
    public boolean canReceive() {
        return getMaxTransfer() > 0;
    }
}