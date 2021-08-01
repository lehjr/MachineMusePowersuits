package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import net.minecraft.inventory.container.Container;

import java.util.List;

public class ScrollableInventoryFrame extends InventoryFrame {
    public ScrollableInventoryFrame(Container containerIn, int gridWidth, int gridHeight, List<Integer> slotIndexesIn, IContainerULOffSet.ulGetter ulGetter) {
        super(containerIn, gridWidth, gridHeight, slotIndexesIn, ulGetter);
    }




}