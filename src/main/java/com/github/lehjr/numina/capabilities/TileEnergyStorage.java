package com.github.lehjr.numina.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * Energy handler for tile entity itself
 */
public class TileEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    public TileEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void onEnergyChanged() {

    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int recieved =  super.receiveEnergy(maxReceive, simulate);
        if (recieved > 0) {
            onEnergyChanged();
        }
        return recieved;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if(extracted > 0) {
            onEnergyChanged();
        }
        return extracted;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }
}