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


import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.blockentity.NuminaBlockEntity;
import com.lehjr.numina.common.capabilities.render.color.CapabilityColor;
import com.lehjr.numina.common.capabilities.render.color.ColorStorage;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.client.model.helper.LuxCapHelper;
import com.lehjr.powersuits.common.base.MPSObjects;
import com.lehjr.powersuits.common.block.LuxCapacitor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class LuxCapacitorBlockEntity extends NuminaBlockEntity /*implements ITickableBlockEntity */{
    final ColorStorage colorStorage = new ColorStorage();
    final LazyOptional<IColorTag> colorHolder;
    public LuxCapacitorBlockEntity(BlockPos pos, BlockState state) {
        super(MPSObjects.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pos, state);
        colorHolder = LazyOptional.of(()-> colorStorage);
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

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("color", Tag.TAG_INT)) {
            colorHolder.ifPresent(colorNBT-> ((ColorStorage)colorNBT).deserializeNBT((IntTag) tag.get("color")));
        } else {
            colorHolder.ifPresent(colorNBT-> colorNBT.setColor(LuxCapacitor.defaultColor));

            NuminaLogger.logger.debug("No NBT found! D:");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        colorHolder.ifPresent(colorNBT -> tag.put("colour", ((ColorStorage)colorNBT).serializeNBT()));
    }

//        @Override
//    public void requestModelDataUpdate() {
//        super.requestModelDataUpdate();
//    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        // FIXME: insert luxcaphelper here
        return LuxCapHelper.getModelData(getColor().getInt());
    }

    public Color getColor() {
        return colorHolder.map(colourNBT1 -> colourNBT1.getColor()).orElse(LuxCapacitor.defaultColor);
    }
}