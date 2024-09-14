package com.lehjr.powersuits.common.blockentity;

import com.lehjr.numina.common.capabilities.render.color.ColorStorage;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.client.model.LuxCapHelper;
import com.lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LuxCapacitorBlockEntity extends BlockEntity {
    final ColorStorage colorStorage = new ColorStorage();
    final Lazy<IColorTag> colorHolder;

    public LuxCapacitorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MPSBlocks.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
        colorHolder = Lazy.of(()-> colorStorage);
    }

    public LuxCapacitorBlockEntity setColor(Color color) {
        if(this.level != null) {
            colorHolder.get().setColor(color);
            if(!this.level.isClientSide) {
                this.setChanged();
                // Added to make block sync on creation when launched from power fist
                level.blockUpdated(getBlockPos(), level.getBlockState(getBlockPos()).getBlock());
            }
        }
        return this;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(NuminaConstants.COLOR, colorStorage.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if(tag.contains(NuminaConstants.COLOR)) {
            colorStorage.deserializeNBT(provider, Objects.requireNonNull(tag.get(NuminaConstants.COLOR)));
        }
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
        return LuxCapHelper.getItemModelData(getColor().getARGBInt());
    }

    public Color getColor() {
        return colorHolder.get().getColor();
    }

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        CompoundTag tag = super.getUpdateTag(pRegistries);
        saveAdditional(tag, pRegistries);

        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}

