package lehjr.numina.common.capabilities.render.chameleon;

import lehjr.numina.common.capabilities.IItemStackUpdate;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Chameleon implements IChameleon, IItemStackUpdate, INBTSerializable<StringNBT> {
    public static final String BLOCK = "block";

    ResourceLocation blockRegName = Blocks.AIR.getRegistryName();
    @Nonnull
    ItemStack module;

    public Chameleon(@Nonnull ItemStack module) {
        this.module = module;
        this.blockRegName = TagUtils.getModuleResourceLocation(module, BLOCK).orElse(Blocks.AIR.getRegistryName());
    }

    @Override
    public Optional<ResourceLocation> getTargetBlockRegName() {
        return Optional.ofNullable(blockRegName);
    }

    @Override
    public Optional<Block> getTargetBlock() {
        return getTargetBlockRegName().map(regNane -> ForgeRegistries.BLOCKS.getValue(regNane));
    }

    @Nonnull
    @Override
    public ItemStack getModule() {
        return module;
    }

    @Override
    public StringNBT serializeNBT() {
        if (blockRegName != null) {
            return StringNBT.valueOf(blockRegName.toString());
        }
        return null;
    }

    @Override
    public void setTargetBlockByRegName(ResourceLocation regName) {
        IChameleon.super.setTargetBlockByRegName(regName);
        this.blockRegName = regName;
        serializeNBT();
    }

    @Override
    public void deserializeNBT(StringNBT nbt) {
        if (nbt != null) {
            this.blockRegName = new ResourceLocation(nbt.getAsString());
        }
    }

    @Override
    public void updateFromNBT() {
        final CompoundNBT nbt = TagUtils.getModuleTag(module);
        if (nbt.contains(BLOCK, Constants.NBT.TAG_STRING)) {
            deserializeNBT(StringNBT.valueOf(nbt.getString(BLOCK)));
        } else {
            this.blockRegName = Blocks.AIR.getRegistryName();
        }
    }
}