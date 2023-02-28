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

package lehjr.numina.common.capabilities.heat;

import net.minecraft.nbt.DoubleTag;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Based on Forge Energy and CoHF RF, but using doubles and max heat value is only a safety threshold, not a cap
 */
public abstract class HeatStorage implements IHeatStorage, INBTSerializable<DoubleTag> {
    protected double heat;
    protected double capacity; // this is just a safety boundary, not an absolute cap
    protected double maxReceive;
    protected double maxExtract;

    public HeatStorage(double capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public HeatStorage(double capacity, double maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public HeatStorage(double capacity, double maxReceive, double maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public HeatStorage(double capacity, double maxReceive, double maxExtract, double heat) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.heat = heat;
    }

    @Override
    public double receiveHeat(double maxReceive, boolean simulate) {
        if (!canReceive()) {
            return 0;
        }

        double heatReceived = Math.min(capacity - heat, Math.min(this.maxReceive, maxReceive));
        if (!simulate && heatReceived !=0) {
            heat += heatReceived;
            onValueChanged();
        }
        return heatReceived;
    }

    @Override
    public double extractHeat(double maxExtract, boolean simulate) {
        if (!canExtract()) {
            return 0;
        }

        double heatExtracted = Math.min(heat, maxExtract);
        if (!simulate && heatExtracted != 0) {
            heat -= heatExtracted;
            onValueChanged();
        }
        return heatExtracted;
    }

    @Override
    public double getHeatStored() {
        return heat;
    }

    @Override
    public double getMaxHeatStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.heat > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public void setHeatCapacity(double maxHeat) {
        this.capacity = maxHeat;
    }

    /** INBTSerializable -------------------------------------------------------------------------- */
    @Override
    public DoubleTag serializeNBT() {
        return DoubleTag.valueOf(heat);
    }

    @Override
    public void deserializeNBT(final DoubleTag nbt) {
        this.heat = nbt.getAsDouble();
    }
}