package com.lehjr.powersuits.common.base;

import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MPSCreativeTab extends CreativeModeTab {
    public MPSCreativeTab() {
        super(MPSConstants.MOD_ID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack makeIcon() {
        Item item = MPSObjects.POWER_ARMOR_HELMET.get();
        return new ItemStack(item);
    }
}
