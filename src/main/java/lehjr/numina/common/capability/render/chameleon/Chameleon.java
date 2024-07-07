package lehjr.numina.common.capability.render.chameleon;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Chameleon implements IChameleon, INBTSerializable<Tag> {
    static final ResourceLocation AIR = new ResourceLocation("air");
    ResourceLocation blockRegName = null;

    public Chameleon() {
    }

    @Override
    public Optional<ResourceLocation> getTargetBlockRegName() {
        return Optional.ofNullable(blockRegName);
    }

    @Override
    public Optional<Block> getTargetBlock() {
        return getTargetBlockRegName().map(BuiltInRegistries.BLOCK::get);
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender() {
        return getTargetBlock().map(block -> new ItemStack(block.asItem())).orElse(ItemStack.EMPTY);
    }

    @Override
    public void setTargetBlockByRegName(ResourceLocation regName) {
        IChameleon.super.setTargetBlockByRegName(regName);
        this.blockRegName = regName;
    }

    @Override
    public @UnknownNullability StringTag serializeNBT(HolderLookup.Provider provider) {
        if (blockRegName != null) {
            return StringTag.valueOf(blockRegName.toString());
        }
        return StringTag.valueOf(AIR.toString());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        if (!(nbt instanceof StringTag stringTag)) {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
        this.blockRegName = new ResourceLocation(stringTag.getAsString());
    }
}