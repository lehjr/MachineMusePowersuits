package lehjr.powersuits.common.blockentity;

import lehjr.numina.client.model.helper.LuxCapHelper;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.render.color.ColorStorage;
import lehjr.numina.common.capability.render.color.IColorTag;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LuxCapacitorBlockEntity extends BlockEntity {
    final ColorStorage colorStorage = new ColorStorage();
    final Lazy<IColorTag> colorHolder;
    public static final String COLOR_TAG = "Color";

    public LuxCapacitorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MPSBlocks.LUX_CAP_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
        colorHolder = Lazy.of(()-> colorStorage);
    }

    public void setColor(Color color) {
        IColorTag colorCap = level.getCapability(NuminaCapabilities.ColorCap.COLOR_BLOCK, getBlockPos(), null);
        if (colorCap != null) {
            colorCap.setColor(color);
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(COLOR_TAG, colorStorage.serializeNBT(provider));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if(tag.contains(COLOR_TAG)) {
            colorStorage.deserializeNBT(provider, Objects.requireNonNull(tag.get(COLOR_TAG)));
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
}

