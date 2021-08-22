package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.powersuits.dev.crafting.container.IModularItemContainerSlotProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;

public class ModularItemSelectionFrame<C extends IModularItemContainerSlotProvider> extends ModularItemSelectionFrameContainerless {
    C container;
    public ModularItemSelectionFrame(C container ) {
        super();
        this.container = container;
    }

    @Override
    void disableContainerSlots() {
        for (Slot slot : ((Container)container).slots) {
            if (slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
                slot.x = -1000;
                slot.y = -1000;
            }
        }
    }
}