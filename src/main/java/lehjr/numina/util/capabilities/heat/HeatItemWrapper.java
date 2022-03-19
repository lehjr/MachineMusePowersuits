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

package lehjr.numina.util.capabilities.heat;

import lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeatItemWrapper extends HeatStorage implements ICapabilityProvider, IHeatWrapper, INBTSerializable<DoubleNBT> {
    ItemStack stack;
    private final LazyOptional<IHeatStorage> holder = LazyOptional.of(() -> this);

    public HeatItemWrapper(@Nonnull ItemStack stack, double baseMax, LazyOptional<IPowerModule> moduleCap) {
        this(stack, baseMax + moduleCap.map(cap->cap.applyPropertyModifiers(HeatCapability.MAXIMUM_HEAT)).orElse(0D));
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity) {
        this(stack, capacity, capacity, capacity);
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity, double maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer);
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity, double maxReceive, double maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
        this.stack = stack;
    }

    /** IItemStackContainerUpdate ----------------------------------------------------------------- */
    @Override
    public void updateFromNBT() {
        heat = Math.min(capacity, MuseNBTUtils.getModularItemDoubleOrZero(stack, HeatCapability.CURRENT_HEAT));
    }
    @Override
    public double receiveHeat(double heatProvided, boolean simulate) {
        final double heatReceived = super.receiveHeat(heatProvided, simulate);
        if (!simulate && heatReceived > 0) {
            MuseNBTUtils.setModularItemDoubleOrRemove(stack, HeatCapability.CURRENT_HEAT, heat);
        }
        return heatReceived;
    }

    @Override
    public double extractHeat(double heatRequested, boolean simulate) {
        final double heatExtracted = super.extractHeat(heatRequested, simulate);
        if (!simulate && heatExtracted > 0) {
            MuseNBTUtils.setModularItemDoubleOrRemove(stack, HeatCapability.CURRENT_HEAT, heat);
        }
        return heatExtracted;
    }

    /** INBTSerializable -------------------------------------------------------------------------- */
    @Override
    public DoubleNBT serializeNBT() {
        return (DoubleNBT) HeatCapability.HEAT.writeNBT(this, null);
    }

    @Override
    public void deserializeNBT(final DoubleNBT nbt) {
        HeatCapability.HEAT.readNBT(this, null, nbt);
    }

    /** INBTSerializable<NBTTagDouble> ------------------------------------------------------------ */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return HeatCapability.HEAT.orEmpty(cap, holder);
    }
}