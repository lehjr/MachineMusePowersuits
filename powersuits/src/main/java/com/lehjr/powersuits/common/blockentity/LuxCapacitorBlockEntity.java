package com.lehjr.powersuits.common.blockentity;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.render.color.ColorAttachmentStorage;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class LuxCapacitorBlockEntity extends BlockEntity {
    private final ColorAttachmentStorage colorStorage = createColorStorage();

    public LuxCapacitorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MPSBlocks.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
    }

    private final Lazy<IColorTag> colorHandler = Lazy.of(() -> colorStorage);

    public Color getStoredColor() {
        return getColorHandler().getColor();
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
        return LuxCapHelper.getItemModelData(getStoredColor().getARGBInt());
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

    @Nonnull
    private ColorAttachmentStorage createColorStorage() {
        return new ColorAttachmentStorage(this) {
            @Override
            public void setColor(Color color) {
                super.setColor(color);
                requestModelDataUpdate(); // update the color data when created
            }
        };
    }

    public IColorTag getColorHandler() {
        return colorHandler.get();
    }
}
