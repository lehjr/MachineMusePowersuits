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

package lehjr.powersuits.common.blockentity;


import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.render.color.ColorStorage;
import lehjr.numina.common.capabilities.render.color.IColorTag;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.base.MPSBlocks;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class LuxCapacitorBlockEntity extends BlockEntity {
    final ColorStorage colorStorage = new ColorStorage();
    final LazyOptional<IColorTag> colorHolder;
    public LuxCapacitorBlockEntity(BlockPos pos, BlockState state) {
        super(MPSBlocks.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pos, state);
        colorHolder = LazyOptional.of(()-> colorStorage);
    }

    public void setColor(Color color) {
        this.getCapability(NuminaCapabilities.COLOR, null).ifPresent(colorCap -> colorCap.setColor(color));
        this.setChanged();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> colorCapability = NuminaCapabilities.COLOR.orEmpty(cap, colorHolder);
        if (colorCapability.isPresent()) {
            return colorCapability;
        }

        return LazyOptional.empty();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        colorStorage.deserializeNBT((IntTag) tag.get(TagConstants.COLOR));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(super.getUpdateTag());
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        save(pTag);
    }

    public CompoundTag save(CompoundTag tag) {
        tag.put(TagConstants.COLOR, colorStorage.serializeNBT());
        return tag;
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
//        return LuxCapHelper.getBlockBaseModelData();
        return LuxCapHelper.getItemModelData(getColor().getAGBRInt());
    }

    public Color getColor() {
        return colorHolder.map(colorCap -> colorCap.getColor()).orElse(LuxCapacitorBlock.defaultColor);
    }

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }
}
