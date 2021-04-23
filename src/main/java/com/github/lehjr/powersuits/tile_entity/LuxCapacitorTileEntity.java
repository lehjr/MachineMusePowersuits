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

package com.github.lehjr.powersuits.tile_entity;


import com.github.lehjr.numina.util.capabilities.render.colour.ColourCapability;
import com.github.lehjr.numina.util.capabilities.render.colour.ColourNBT;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.tileentity.MuseTileEntity;
import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import com.github.lehjr.powersuits.block.LuxCapacitorBlock;
import com.github.lehjr.powersuits.client.model.helper.LuxCapHelper;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LuxCapacitorTileEntity extends MuseTileEntity {
    LazyOptional<ColourNBT> colourNBT = LazyOptional.of(()-> new ColourNBT());

    public LuxCapacitorTileEntity() {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
    }

    public LuxCapacitorTileEntity(Colour colour) {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
        colourNBT.ifPresent(colourNBT1 -> colourNBT1.setColour(colour));
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, -1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void setColor(Colour colour) {
//        MuseLogger.logDebug("setting colour: " + colour);
        this.colourNBT.ifPresent(colourNBT1 -> colourNBT1.setColour(colour));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
//        MuseLogger.logDebug("writing: " + nbt);
        colourNBT.ifPresent(colourNBT1 -> nbt.put("colour", colourNBT1.serializeNBT()));
        return super.write(nbt);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return LuxCapHelper.getModelData(getColor().getInt());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ColourCapability.COLOUR) {
            return colourNBT.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
//        MuseLogger.logDebug("reading");

        if (nbt.contains("colour", Constants.NBT.TAG_INT)) {
            colourNBT.ifPresent(colourNBT1 -> colourNBT1.deserializeNBT((IntNBT) nbt.get("colour")));
        } else {
            MuseLogger.logger.debug("No NBT found! D:");
        }
        super.read(state, nbt);
    }

    public Colour getColor() {
        return colourNBT.map(colourNBT1 -> colourNBT1.getColour()).orElse(LuxCapacitorBlock.defaultColor);
    }
}
