package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class HideableSlot extends Slot implements IHideableSlot {
    boolean isEnabled = false;

    public HideableSlot(IInventory iInventory, int slotIndex, int xPosition, int yPosition) {
        super(iInventory, slotIndex, xPosition, yPosition);
    }

    public HideableSlot(IInventory iInventory, int slotIndex, int xPosition, int yPosition, boolean enabled) {
        super(iInventory, slotIndex, xPosition, yPosition);
        this.isEnabled = enabled;
    }

    @Override
    public void enable() {
        this.isEnabled = true;
    }

    @Override
    public void disable() {
        this.isEnabled = false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setPosition(MusePoint2D position) {
        this.xPos = (int) position.getX();
        this.yPos = (int) position.getY();
    }
}