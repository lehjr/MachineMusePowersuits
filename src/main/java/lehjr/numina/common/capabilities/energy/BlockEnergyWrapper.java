/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.capabilities.energy;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

/**
 * Energy wrapper to wrap tile energy handler and battery energy handler
 */
public class BlockEnergyWrapper implements IEnergyStorage {
    LazyOptional<IEnergyStorage> tileEnergy;
    LazyOptional<IItemHandler> itemHandler;
    public BlockEnergyWrapper(LazyOptional<IEnergyStorage> tileEnergyIn, LazyOptional<IItemHandler> itemHandlerIn) {
        tileEnergy = tileEnergyIn;
        itemHandler = itemHandlerIn;
    }

    protected BlockEnergyWrapper() {
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