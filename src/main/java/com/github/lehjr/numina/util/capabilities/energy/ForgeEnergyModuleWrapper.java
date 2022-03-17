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

package com.github.lehjr.numina.util.capabilities.energy;

import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;

/**
 * Used for energy storage devices
 */
public class ForgeEnergyModuleWrapper extends EnergyStorage implements IEnergyWrapper, INBTSerializable<IntNBT> {
    private static final String TAG_ENERGY = "energy";
    protected ItemStack stack;

    public ForgeEnergyModuleWrapper(@Nonnull ItemStack stack, int maxEnergy, int maxTransfer) {
        super(maxEnergy, maxTransfer);
        this.stack = stack;
    }

    /** IItemStackContainerUpdate ----------------------------------------------------------------- */
    @Override
    public void updateFromNBT() {
        final CompoundNBT nbt = MuseNBTUtils.getModuleTag(stack);
        if (nbt != null && nbt.contains(TAG_ENERGY, net.minecraftforge.common.util.Constants.NBT.TAG_INT)) {
            deserializeNBT((IntNBT) nbt.get(TAG_ENERGY));
        }
    }

    /** IEnergyStorage ---------------------------------------------------------------------------- */
    @Override
    public int receiveEnergy(final int maxReceive, final boolean simulate) {
        final int energyReceived = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && energyReceived != 0) {
            MuseNBTUtils.setModuleIntOrRemove(stack, TAG_ENERGY, energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(final int maxExtract, final boolean simulate) {
        final int energyExtracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate && energyExtracted != 0) {
            MuseNBTUtils.setModuleIntOrRemove(stack, TAG_ENERGY, energy);
        }
        return energyExtracted;
    }

    /** INBTSerializable -------------------------------------------------------------------------- */
    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) CapabilityEnergy.ENERGY.writeNBT(this, null);
    }

    @Override
    public void deserializeNBT(final IntNBT nbt) {
        CapabilityEnergy.ENERGY.readNBT(this, null, nbt);
    }
}