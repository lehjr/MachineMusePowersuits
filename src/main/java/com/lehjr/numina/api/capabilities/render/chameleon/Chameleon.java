package com.lehjr.numina.api.capabilities.render.chameleon;

import com.lehjr.numina.api.constants.TagConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Chameleon implements IChameleon, INBTSerializable<StringTag> {
    ResourceLocation blockRegName = null;
    @Nonnull ItemStack module;
    public Chameleon(@Nonnull ItemStack module) {
        this.module = module;
        CompoundTag tag = module.getOrCreateTag();
        if (tag.contains(TagConstants.BLOCK, Tag.TAG_STRING)) {
            deserializeNBT((StringTag) tag.get(TagConstants.BLOCK));
        }
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
    public ItemStack getStackToRender() {
        return getTargetBlock().map(block -> new ItemStack(block.asItem())).orElse(module);
    }

    @Override
    public StringTag serializeNBT() {
        if (blockRegName != null) {
            return StringTag.valueOf(blockRegName.toString());
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
    public void deserializeNBT(StringTag nbt) {
        if (nbt != null) {
            this.blockRegName = new ResourceLocation(nbt.getAsString());
        }
    }
}