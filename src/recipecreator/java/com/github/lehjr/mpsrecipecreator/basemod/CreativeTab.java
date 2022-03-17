package com.github.lehjr.mpsrecipecreator.basemod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author lehjr
 */
public class CreativeTab extends ItemGroup {
    public CreativeTab() {
        super(Constants.MOD_ID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModObjects.INSTANCE.recipeWorkBench);
    }
}