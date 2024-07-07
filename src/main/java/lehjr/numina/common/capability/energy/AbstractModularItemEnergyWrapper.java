package lehjr.numina.common.capability.energy;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractModularItemEnergyWrapper implements IEnergyStorage {
    protected ItemStack itemStack;
    public AbstractModularItemEnergyWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return getModularItemCap().map(cap->{
            int remaining = toReceive;
            for (int i = 0; i < cap.getSlots(); i++ ) {

                int recieved = 0;
                ItemStack module = cap.getStackInSlot(i).copy();
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
        }).orElse(toReceive);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return getModularItemCap().map(cap->{
            int remaining = toExtract;
            for (int i = 0; i < cap.getSlots(); i++ ) {

                int extracted = 0;
                ItemStack module = cap.getStackInSlot(i).copy();
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
        }).orElse(toExtract);
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

    public Optional<IModularItem> getModularItemCap() {
        Optional<IModularItem> modularItemCap = NuminaCapabilities.getModularItemCapability(itemStack);
        if (modularItemCap.isPresent()) {
            return modularItemCap;
        }
        Optional<IModeChangingItem> modeChangingCap = NuminaCapabilities.getModeChangingModularItemCapability(itemStack);
        if (modeChangingCap.isPresent()) {
            return modularItemCap;
        }
        return Optional.empty();
    }

    public abstract List<IEnergyStorage> getInternalEnergyStorage();
}
