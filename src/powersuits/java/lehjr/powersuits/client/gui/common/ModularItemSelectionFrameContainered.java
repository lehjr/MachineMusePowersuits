package lehjr.powersuits.client.gui.common;

import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.slot.IHideableSlot;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;

public class ModularItemSelectionFrameContainered<C extends Container> extends ModularItemSelectionFrame {
    C container;

    public ModularItemSelectionFrameContainered(C container, MusePoint2D ul, EquipmentSlotType type) {
        super(ul, type);
        this.container = container;
    }

    public ModularItemSelectionFrameContainered(C container, MusePoint2D ul) {
        super(ul);
        this.container = container;
    }

    @Override
    void disableContainerSlots() {
        for (Slot slot : container.slots) {
            if (slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
                slot.x = -1000;
                slot.y = -1000;
            }
        }
    }
}