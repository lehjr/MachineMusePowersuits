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

package com.lehjr.powersuits.common.blockentity;


import com.lehjr.numina.common.blockentity.NuminaBlockEntity;
import com.lehjr.numina.common.capabilities.render.color.CapabilityColor;
import com.lehjr.numina.common.capabilities.render.color.ColorStorage;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.common.base.MPSObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LuxCapacitorBlockEntity extends NuminaBlockEntity /*implements ITickableBlockEntity */{
    final ColorStorage colorStorage = new ColorStorage();
    LazyOptional<IColorTag> colorHolder = LazyOptional.of(()-> new ColorStorage());

    public LuxCapacitorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(MPSObjects.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pWorldPosition, pBlockState);
    }

    public void setColor(Color color) {
        this.getCapability(CapabilityColor.COLOR, null).ifPresent(colorCap -> colorCap.setColor(color));
        // fixme save color setting?
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> colorCapability = CapabilityColor.COLOR.orEmpty(cap, colorHolder);
        if (colorCapability.isPresent()) {
            return colorCapability;
        }

        return LazyOptional.empty();
    }

    //
//    public LuxCapacitorBlockEntity() {
//        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
//    }
//
//    public LuxCapacitorBlockEntity(Color colour) {
//        super(MPSObjects.LUX_CAP_TILE_TYPE.get());
//        colourNBT.ifPresent(colourNBT1 -> colourNBT1.setColor(colour));
//    }
//
//    /**
//     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
//     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
//     */
//    @Nullable
//    @Override
//    public SUpdateBlockEntityPacket getUpdatePacket() {
//        return new SUpdateBlockEntityPacket(this.worldPosition, -1, this.getUpdateTag());
//    }
//
//    @Override
//    public CompoundTag getUpdateTag() {
//        return this.save(new CompoundTag());
//    }
//
//    public void setColor(Color colour) {
////        NuminaLogger.logDebug("setting colour: " + colour);
//        this.colourNBT.ifPresent(colourNBT1 -> colourNBT1.setColor(colour));
//    }
//
//    @Override
//    public CompoundTag save(CompoundTag nbt) {
////        NuminaLogger.logDebug("writing: " + nbt);
//        colourNBT.ifPresent(colourNBT1 -> nbt.put("colour", colourNBT1.serializeNBT()));
//        return super.save(nbt);
//    }
//
//    @Nonnull
//    @Override
//    public IModelData getModelData() {
//        return LuxCapHelper.getModelData(getColor().getInt());
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (cap == ColorCapability.COLOUR) {
//            return colourNBT.cast();
//        }
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void load(BlockState state, CompoundTag nbt) {
////        NuminaLogger.logDebug("reading");
//
//        if (nbt.contains("colour", Tag.TAG_INT)) {
//            colourNBT.ifPresent(colourNBT1 -> colourNBT1.deserializeNBT((IntTag) nbt.get("colour")));
//        } else {
//            NuminaLogger.logger.debug("No NBT found! D:");
//        }
//        super.load(state, nbt);
//    }
//
//    public Color getColor() {
//        return colourNBT.map(colourNBT1 -> colourNBT1.getColor()).orElse(LuxCapacitor.defaultColor);
//    }
}
