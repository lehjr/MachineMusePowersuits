package com.github.lehjr.powersuits.basemod;

import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MPSCreativeTab extends ItemGroup {
    public MPSCreativeTab() {
        super(MPSConstants.MOD_ID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack createIcon() {
        Item item = MPSObjects.POWER_ARMOR_HELMET.get();
        return new ItemStack(item);
    }
}