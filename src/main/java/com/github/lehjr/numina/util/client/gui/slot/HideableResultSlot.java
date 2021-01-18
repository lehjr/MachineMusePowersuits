package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;

public class HideableResultSlot extends CraftingResultSlot implements IHideableSlot {
    boolean isEnabled = false;

    public HideableResultSlot(PlayerEntity playerEntity, CraftingInventory craftingInventory, IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(playerEntity, craftingInventory, inventory, slotIndex, xPosition, yPosition);
    }

    public HideableResultSlot(PlayerEntity playerEntity, CraftingInventory craftingInventory, IInventory inventory, int slotIndex, int xPosition, int yPosition, boolean isEnabled) {
        super(playerEntity, craftingInventory, inventory, slotIndex, xPosition, yPosition);
        this.isEnabled = isEnabled;
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