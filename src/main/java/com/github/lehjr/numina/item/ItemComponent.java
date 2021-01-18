package com.github.lehjr.numina.item;

import com.github.lehjr.numina.basemod.NuminaObjects;
import net.minecraft.item.Item;

public class ItemComponent extends Item {
    public ItemComponent() {
        super(new Item.Properties()
                .maxStackSize(64)
                .group(NuminaObjects.creativeTab)
                .defaultMaxDamage(-1)
                .setNoRepair());
    }
}