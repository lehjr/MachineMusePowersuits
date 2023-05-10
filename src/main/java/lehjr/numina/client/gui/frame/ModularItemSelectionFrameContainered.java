package lehjr.numina.client.gui.frame;

import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.slot.IHideableSlot;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class ModularItemSelectionFrameContainered<C extends AbstractContainerMenu> extends ModularItemSelectionFrame {
    C container;

    public ModularItemSelectionFrameContainered(C container, MusePoint2D ul, EquipmentSlot type) {
        super(ul, type);
        this.container = container;
    }

    public ModularItemSelectionFrameContainered(C container, MusePoint2D ul) {
        super(ul);
        this.container = container;
    }

    @Override
    void disableAbstractContainerMenuSlots() {
        for (Slot slot : container.slots) {
            if (slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
                slot.x = -1000;
                slot.y = -1000;
            }
        }
    }
}