package lehjr.numina.common.capabilities.energy;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

/**
 * Energy wrapper to wrap tile energy handler and battery energy handler
 */
public class BlockEnergyWrapper implements IEnergyStorage {
    IEnergyStorage tileEnergy;
    IItemHandler itemHandler;
    public BlockEnergyWrapper(IEnergyStorage tileEnergyIn, IItemHandler itemHandlerIn) {
        tileEnergy = tileEnergyIn;
        itemHandler = itemHandlerIn;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        // charge battery first
        int batteryRecieved = getBatteryEnergyHandler().map(battery->battery.receiveEnergy(maxReceive, simulate)).orElse(0);
        int tileRecieved = tileEnergy.receiveEnergy(maxReceive - batteryRecieved, simulate);

        return batteryRecieved + tileRecieved;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }
        // drain blockentity first
        int tileExtracted = tileEnergy.extractEnergy(maxExtract, simulate);
        int batteryExtracted = getBatteryEnergyHandler().map(battery->battery.extractEnergy(maxExtract - tileExtracted, simulate)).orElse(0);
        return tileExtracted + batteryExtracted;
    }

    @Override
    public int getEnergyStored() {
        return getBatteryEnergyHandler().map(IEnergyStorage::getEnergyStored).orElse(0) + tileEnergy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return getBatteryEnergyHandler().map(IEnergyStorage::getMaxEnergyStored).orElse(0) +
                tileEnergy.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return getBatteryEnergyHandler().map(IEnergyStorage::canExtract).orElse(false) ||
                tileEnergy.canExtract();
    }

    @Override
    public boolean canReceive() {
        return getBatteryEnergyHandler().map(IEnergyStorage::canReceive).orElse(false) ||
                tileEnergy.canReceive();
    }

    Optional<IEnergyStorage> getBatteryEnergyHandler() {
        return NuminaCapabilities.getCapability(itemHandler.getStackInSlot(0), Capabilities.EnergyStorage.ITEM);
    }
}