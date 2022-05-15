package com.lehjr.numina.common.item;

import com.lehjr.numina.common.base.NuminaObjects;
import net.minecraft.world.item.Item;

public class ComponentItem extends Item {
    public ComponentItem() {
        super(new Item.Properties().tab(NuminaObjects.creativeTab));
    }
}
