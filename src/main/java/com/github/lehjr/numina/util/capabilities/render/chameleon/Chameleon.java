package com.github.lehjr.numina.util.capabilities.render.chameleon;

import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Chameleon implements IChameleon, INBTSerializable<StringNBT> {
    ResourceLocation blockRegName = Blocks.AIR.getRegistryName();
    @Nonnull ItemStack module;
    public Chameleon(@Nonnull ItemStack module) {
        this.module = module;
        this.blockRegName = MuseNBTUtils.getModuleResourceLocation(module, "block").orElse(null);
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
}