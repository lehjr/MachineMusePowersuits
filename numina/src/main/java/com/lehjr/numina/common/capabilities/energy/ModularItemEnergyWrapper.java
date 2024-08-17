package com.lehjr.numina.common.capabilities.energy;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModularItemEnergyWrapper implements IEnergyStorage {
    protected ItemStack itemStack;
    public ModularItemEnergyWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        IModularItem cap = getModularItemCap();
        if (cap != null) {
            int remaining = toReceive;
            for (int i = 0; i < cap.getSlots(); i++ ) {

                int recieved = 0;
                ItemStack module = cap.getStackInSlot(i);
                IEnergyStorage storage = module.getCapability(Capabilities.EnergyStorage.ITEM);
                if (storage != null && storage.canReceive()) {
                    recieved = storage.receiveEnergy(remaining, simulate);
                }

                if (recieved > 0) {
                    remaining -= recieved;
                    cap.updateModuleInSlot(i, module);
                }
            }
            return toReceive - remaining;
        }
        return toReceive;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        IModularItem cap = getModularItemCap();
        if (cap != null) {
            int remaining = toExtract;
            for (int i = 0; i < cap.getSlots(); i++ ) {

                int extracted = 0;
                ItemStack module = cap.getStackInSlot(i);
                IEnergyStorage storage = module.getCapability(Capabilities.EnergyStorage.ITEM);
                if (storage != null && storage.canExtract()) {
                    extracted = storage.extractEnergy(remaining, simulate);
                }
                if (extracted > 0) {
                    remaining -= extracted;
                    cap.updateModuleInSlot(i, module);
                }
            }
            return toExtract - remaining;
        }
        return toExtract;
    }

    @Override
    public int getEnergyStored() {
        return getInternalEnergyStorage().stream().mapToInt(IEnergyStorage::getEnergyStored).sum();
    }

    @Override
    public int getMaxEnergyStored() {
        return getInternalEnergyStorage().stream().mapToInt(IEnergyStorage::getMaxEnergyStored).sum();
    }

    @Override
    public boolean canExtract() {
        return getInternalEnergyStorage().stream().anyMatch(IEnergyStorage::canExtract);
    }

    @Override
    public boolean canReceive() {
        return getInternalEnergyStorage().stream().anyMatch(IEnergyStorage::canReceive);
    }

    @Nullable
    public IModularItem getModularItemCap() {
        IModularItem modularItemCap = itemStack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        if (modularItemCap != null) {
            return modularItemCap;
        }
        return itemStack.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
    }

    public List<IEnergyStorage> getInternalEnergyStorage() {
        IModularItem cap = getModularItemCap();
        List<IEnergyStorage> ret = new ArrayList<>();
        if (cap != null) {
            Pair<Integer, Integer> range = cap.getRangeForCategory(ModuleCategory.ENERGY_STORAGE);
            if(range != null) {
                for (int j = range.getFirst(); j < range.getSecond(); j++) {
                    ItemStack module = cap.getStackInSlot(j);
                    IEnergyStorage energyStorage = module.getCapability(Capabilities.EnergyStorage.ITEM);
                    if (energyStorage != null) {
                        ret.add(energyStorage);
                    }
                }
            }
        }
        return ret;
    }
}
