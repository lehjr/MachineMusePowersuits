package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class HideableSlotItemHandler extends SlotItemHandler implements IHideableSlot {
    boolean isEnabled = false;
    protected int parentSlot = -1;

    public HideableSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.parentSlot = parent;
    }

    public HideableSlotItemHandler(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
        super(itemHandler, index, xPosition, yPosition);
        this.parentSlot = parent;
        this.isEnabled = isEnabled;
    }

    public int getParentSlot(){
        return parentSlot;
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