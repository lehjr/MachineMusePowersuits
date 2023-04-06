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

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;

/**
 * Used for energy storage devices
 */
public class ModuleEnergyWrapper extends EnergyStorage implements CapabilityUpdate {
    protected ItemStack stack;

    public ModuleEnergyWrapper(@Nonnull ItemStack stack, int maxEnergy, int maxTransfer) {
        super(maxEnergy, maxTransfer);
        this.stack = stack;
    }

    /**
     * CapabilityUpdate --------------------------------------------------------------------------
     */
    @Override
    public void loadCapValues() {
        final CompoundTag nbt = TagUtils.getModuleTag(stack);
        if (nbt.contains(TagConstants.ENERGY, Tag.TAG_INT)) {
            deserializeNBT(nbt.get(TagConstants.ENERGY));
        }
    }

    @Override
    public void onValueChanged() {
        final CompoundTag nbt = TagUtils.getModuleTag(stack);
        if (nbt != null && ForgeCapabilities.ENERGY != null) { // capability is null during game loading
            nbt.put(TagConstants.ENERGY, serializeNBT());
        }
    }

    /**
     * IEnergyStorage ----------------------------------------------------------------------------
     */
    @Override
    public int receiveEnergy(final int maxReceive, final boolean simulate) {
        final int energyReceived = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && energyReceived != 0) {
            onValueChanged();
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(final int maxExtract, final boolean simulate) {
        final int energyExtracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate && energyExtracted != 0) {
            onValueChanged();
        }
        return energyExtracted;
    }
}