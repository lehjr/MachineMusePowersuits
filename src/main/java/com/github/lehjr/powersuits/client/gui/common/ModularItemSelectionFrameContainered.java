package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.powersuits.container.IModularItemContainerSlotProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ModularItemSelectionFrameContainered<C extends IModularItemContainerSlotProvider> extends ModularItemSelectionFrame {
    C container;
    public ModularItemSelectionFrameContainered(C container ) {
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