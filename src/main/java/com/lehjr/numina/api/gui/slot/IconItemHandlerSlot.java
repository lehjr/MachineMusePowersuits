package com.lehjr.numina.api.gui.slot;

import com.lehjr.numina.api.gui.IIConProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class IconItemHandlerSlot extends SlotItemHandler implements IIConProvider {
    public IconItemHandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
}