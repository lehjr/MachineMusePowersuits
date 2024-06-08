package lehjr.numina.common.capabilities.energy;

import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BatteryCapabilityProvider implements IEnergyStorage {
    private final ItemStack itemStack;
    private final IEnergyExtended energyStorage;

    public BatteryCapabilityProvider(ItemStack itemStack, IEnergyExtended energyStorage) {
        this.itemStack = itemStack;
        this.energyStorage = energyStorage;

        if(itemStack.has(NuminaObjects.ENERGY)) {
            this.energyStorage.deserializeNBT(null, IntTag.valueOf(itemStack.getOrDefault(NuminaObjects.ENERGY, 0)));
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int ret = energyStorage.receiveEnergy(maxReceive, simulate);

        if(!simulate) {
            Tag nbt = energyStorage.serializeNBT(null);
            if(nbt instanceof IntTag nbtInt) {
                itemStack.set(NuminaObjects.ENERGY, nbtInt.getAsInt());
            }
        }
        return ret;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int ret = energyStorage.extractEnergy(maxExtract, simulate);

        if(!simulate) {
            Tag nbt = energyStorage.serializeNBT(null);
            if(nbt instanceof IntTag nbtInt) {
                itemStack.set(NuminaObjects.ENERGY, nbtInt.getAsInt());
            }
        }
        return ret;
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }

    public void setEnergy(int energy) {
        energyStorage.deserializeNBT(null, IntTag.valueOf(energy));
        Tag nbt = energyStorage.serializeNBT(null);
        if(nbt instanceof IntTag nbtInt) {
            itemStack.set(NuminaObjects.ENERGY, nbtInt.getAsInt());
        }
    }

    public IEnergyExtended getEnergyStorage() {
        return energyStorage;
    }
}