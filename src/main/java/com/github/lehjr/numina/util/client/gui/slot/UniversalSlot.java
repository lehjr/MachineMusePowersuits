/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A universal base for both IInventory and IItemHandler based slots
 *
 */
public class UniversalSlot extends Slot implements IClickable {
    private static IInventory emptyInventory = new Inventory(0);
    private final IItemHandler itemHandler;
    public static final int offsetx = 8;
    public static final int offsety = 8;
    protected IPressable onPressed;
    protected IReleasable onReleased;
    protected final int xPos=0, yPos=0;
    private final int index;
    protected final boolean isIItemHandler;
    protected MusePoint2D position;
    boolean isVisible;
    boolean isEnabled;

    public UniversalSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        this(inventory, index, new MusePoint2D(xPosition, yPosition));
    }

    public UniversalSlot(IInventory inventory, int index, MusePoint2D position) {
        super(inventory, index, (int)position.getX(), (int)position.getX());
        this.index = index;
        this.position = position;
        this.itemHandler = new ItemStackHandler();
        isIItemHandler = false;
    }

    public UniversalSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, new MusePoint2D(xPosition, yPosition));
    }

    public UniversalSlot(IItemHandler itemHandler, int index, MusePoint2D position) {
        super(emptyInventory, index, (int)position.getX(), (int)position.getX());
        this.position = position;
        this.itemHandler = itemHandler;
        this.index = index;
        isIItemHandler = true;
        this.isVisible = true;
        this.isEnabled = true;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        if (!isIItemHandler)
            return super.isItemValid(stack);
        return itemHandler.isItemValid(index, stack);
    }

    @Override
    public int getSlotStackLimit() {
        if (!isIItemHandler)
            return super.getSlotStackLimit();
        return this.itemHandler.getSlotLimit(this.index);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        if (!isIItemHandler)
            return super.getItemStackLimit(stack);

        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);

        IItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(index);
        if (handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;
            handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);
            ItemStack remainder = handlerModifiable.insertItem(index, maxAdd, true);
            handlerModifiable.setStackInSlot(index, currentStack);
            return maxInput - remainder.getCount();
        } else {
            ItemStack remainder = handler.insertItem(index, maxAdd, true);
            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }

    public boolean isIItemHandler () {
        return isIItemHandler;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        if (!isIItemHandler)
            return true;
        return !this.getItemHandler().extractItem(index, 1, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount) {
        if (!isIItemHandler)
            return super.decrStackSize(amount);
        return this.getItemHandler().extractItem(index, amount, false);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public IInventory getInventoryHandler() {
        return this.inventory;
    }

    public void setPosition(MusePoint2D position) {
        this.position = position;
    }

    @Override
    @Nonnull
    public ItemStack getStack() {
        if (!isIItemHandler)
            return super.getStack();
        return this.getItemHandler().getStackInSlot(index);
    }

    // Override if your IItemHandler does not implement IItemHandlerModifiable
    @Override
    public void putStack(@Nonnull ItemStack stack) {
        if (!isIItemHandler)
            super.putStack(stack);
        else {
            ((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
        }
        this.onSlotChanged();
    }

    @Override
    public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_) {

    }

    /** IClickable -------------------------------------------------------------------------------- */
    /**
     * Not implemented here due to being handled elsewhere
     *
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     * @param zLevel
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {

    }

    @Override
    public MusePoint2D getPosition() {
        return position;
    }

    @Override
    public void move(double x, double y) {
        // FIXME: move should set how much to move by rather than absolute value of position

        System.out.println("FIXME... behaviour change coming!!");
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public boolean hitBox(double x, double y) {
        boolean hitx = Math.abs(x - position.getX()) < offsetx;
        boolean hity = Math.abs(y - position.getY()) < offsety;
        return hitx && hity;
    }

    @Override
    public List<ITextComponent> getToolTip() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onReleased() {
        if (this.isVisible && this.isEnabled && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }

    @Override
    public void onPressed() {
        if (this.isVisible && this.isEnabled && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}