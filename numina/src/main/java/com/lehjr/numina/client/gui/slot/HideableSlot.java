package com.lehjr.numina.client.gui.slot;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class HideableSlot extends Slot implements IHideableSlot{
    boolean isEnabled = false;
    protected int parentSlot = -1;
    protected int index;

    public HideableSlot(Container container, int parent, int index, int xPosition, int yPosition) {
        super(container, index, xPosition, yPosition);
        this.parentSlot = parent;
        this.index = index;
    }

    public HideableSlot(Container container, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
        super(container, index, xPosition, yPosition);
        this.parentSlot = parent;
        this.isEnabled = isEnabled;
        this.index = index;
    }

    public int getIndex() {
        return index;
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
    public void setPosition(MusePoint2D position) {
        this.x = (int) position.x();
        this.y = (int) position.y();
    }
}
