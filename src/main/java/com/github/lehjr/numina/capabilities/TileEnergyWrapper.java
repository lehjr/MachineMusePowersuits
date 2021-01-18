package com.github.lehjr.numina.capabilities;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

/**
 * Energy wrapper to wrap tile energy handler and battery energy handler
 */
public class TileEnergyWrapper implements IEnergyStorage {
    LazyOptional<IEnergyStorage> tileEnergy;
    LazyOptional<IItemHandler> itemHandler;
    public TileEnergyWrapper(LazyOptional<IEnergyStorage> tileEnergyIn, LazyOptional<IItemHandler> itemHandlerIn) {
        tileEnergy = tileEnergyIn;
         itemHandler = itemHandlerIn;
    }

    protected TileEnergyWrapper() {
        tileEnergy = LazyOptional.empty();
        itemHandler = LazyOptional.empty();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        // charge battery first
        int batteryRecieved = getBatteryEnergyHandler().map(battery->battery.receiveEnergy(maxReceive, simulate)).orElse(0);
        int tileRecieved = tileEnergy.map(tile->tile.receiveEnergy(maxReceive - batteryRecieved, simulate)).orElse(0);

        return batteryRecieved + tileRecieved;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }
        // drain tile first
        int tileExtracted = tileEnergy.map(tile->tile.extractEnergy(maxExtract, simulate)).orElse(0);
        int batteryExtracted = getBatteryEnergyHandler().map(battery->battery.extractEnergy(maxExtract - tileExtracted, simulate)).orElse(0);
        return tileExtracted + batteryExtracted;
    }

    @Override
    public int getEnergyStored() {
        return getBatteryEnergyHandler().map(IEnergyStorage::getEnergyStored).orElse(0) +
                tileEnergy.map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public int getMaxEnergyStored() {
        return getBatteryEnergyHandler().map(IEnergyStorage::getMaxEnergyStored).orElse(0) +
                tileEnergy.map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    @Override
    public boolean canExtract() {
        return getBatteryEnergyHandler().map(IEnergyStorage::canExtract).orElse(false) ||
                tileEnergy.map(IEnergyStorage::canExtract).orElse(false);
    }

    @Override
    public boolean canReceive() {
        return getBatteryEnergyHandler().map(IEnergyStorage::canReceive).orElse(false) ||
                tileEnergy.map(IEnergyStorage::canReceive).orElse(false);
    }

    LazyOptional<IEnergyStorage> getBatteryEnergyHandler() {
        return itemHandler.map(iItemHandler -> iItemHandler.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY)).orElse(LazyOptional.empty());
    }
}
