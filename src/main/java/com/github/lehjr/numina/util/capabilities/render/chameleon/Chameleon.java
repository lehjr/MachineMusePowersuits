package com.github.lehjr.numina.util.capabilities.render.chameleon;

import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Chameleon implements IChameleon {
    @Nonnull ItemStack module;
    public Chameleon(@Nonnull ItemStack module) {
        this.module = module;
    }

    @Override
    public Optional<ResourceLocation> getTargetBlockRegName() {
        return MuseNBTUtils.getModuleResourceLocation(module, "block");
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
}