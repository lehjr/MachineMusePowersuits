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


import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.tileentity.MuseTileEntity;
import com.github.lehjr.powersuits.basemod.MPSObjects;

public class LuxCapacitorTileEntity extends MuseTileEntity {
    private Colour color = Colour.CYAN;

    public LuxCapacitorTileEntity() {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
        this.color = /*LuxCapacitorBlock.defaultColor*/ Colour.CYAN;
    }

    public LuxCapacitorTileEntity(Colour colour) {
        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
        this.color = colour;
    }

    public void setColor(Colour colour) {
        this.color = colour;
    }

//    @Override
//    public CompoundNBT write(CompoundNBT nbt) {
//        super.write(nbt);
//        if (color == null)
//            color = LuxCapacitorBlock.defaultColor;
//        nbt.putInt("c", color.getInt());
//        return nbt;
//    }
//
//    @Nonnull
//    @Override
//    public IModelData getModelData() {
//        return LuxCapHelper.getModelData(getColor().getInt());
//    }
//
//    @Override
//    public void read(CompoundNBT nbt) {
//        super.read(nbt);
//        if (nbt.contains("c")) {
//            color = new Colour(nbt.getInt("c"));
//        } else {
//            MuseLogger.logger.debug("No NBT found! D:");
//        }
//    }
//
//    public Colour getColor() {
//        return color != null ? color : LuxCapacitorBlock.defaultColor;
//    }
}
