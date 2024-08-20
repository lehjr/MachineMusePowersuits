package com.lehjr.numina.client.gui.frame;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.slot.IHideableSlot;
import com.lehjr.numina.common.base.NuminaLogger;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class ModularItemSelectionFrameContainered<C extends AbstractContainerMenu> extends ModularItemSelectionFrame {
    C containerMenu;

    public ModularItemSelectionFrameContainered(C container, MusePoint2D ul, EquipmentSlot type) {
        super(ul, type);
        this.containerMenu = container;
    }

    @Override
    void disableAbstractContainerMenuSlots() {
        NuminaLogger.logDebug("containerMenu.slots: " + containerMenu.slots);
        for (Slot slot : containerMenu.slots) {
            if (slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
                slot.x = -1000;
                slot.y = -1000;
            }
        }
    }
}
