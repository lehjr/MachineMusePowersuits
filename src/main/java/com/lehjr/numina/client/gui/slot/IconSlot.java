package com.lehjr.numina.client.gui.slot;

import com.lehjr.numina.client.gui.IIConProvider;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public abstract class IconSlot extends Slot implements IIConProvider {
    public IconSlot(Container iInventory, int inventoryIndex, int posX, int posY) {
        super(iInventory, inventoryIndex, posX, posY);
    }
}