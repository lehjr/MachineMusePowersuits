package com.lehjr.numina.common.container;


import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.List;
import java.util.Map;

public interface IModularItemToSlotMapProvider<T extends AbstractContainerMenu> {
    Map<Integer, List<SlotItemHandler>> getModularItemToSlotMap();

    T getAbstractContainerMenu();
}