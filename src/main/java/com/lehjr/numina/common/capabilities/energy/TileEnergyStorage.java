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

package com.lehjr.numina.common.capabilities.energy;

import com.lehjr.numina.common.capabilities.CapabilityUpdate;
import net.minecraftforge.energy.EnergyStorage;

/**
 * Energy handler for tile entity itself
 */
public class TileEnergyStorage extends EnergyStorage implements CapabilityUpdate {

    public TileEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        onLoad();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onValueChanged() {

    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onValueChanged();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int recieved =  super.receiveEnergy(maxReceive, simulate);
        if (recieved > 0) {
            onValueChanged();
        }
        return recieved;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if(extracted > 0) {
            onValueChanged();
        }
        return extracted;
    }
}
