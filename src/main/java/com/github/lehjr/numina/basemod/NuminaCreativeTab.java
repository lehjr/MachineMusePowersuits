package com.github.lehjr.numina.basemod;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class NuminaCreativeTab extends ItemGroup {
    public NuminaCreativeTab() {
        super(NuminaConstants.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get());
    }
}