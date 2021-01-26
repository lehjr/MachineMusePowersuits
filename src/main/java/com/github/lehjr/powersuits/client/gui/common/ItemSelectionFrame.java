/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableItem;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.FlyFromPointToPoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.GradientAndArcCalculator;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.powersuits.container.MPSWorkbenchContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemSelectionFrame extends ScrollableFrame {
    protected ArrayList<ClickableItem> itemButtons = new ArrayList<>();
    protected int selectedItemStack = -1;
    protected PlayerEntity player;
    protected int lastItemSlot = -1;
    protected List<MusePoint2D> itemPoints;
    protected List<Integer> indices;
    MPSWorkbenchContainer container;

    public ItemSelectionFrame(
            @Nullable MPSWorkbenchContainer container,
            MusePoint2D topleft,
            MusePoint2D bottomright,
            float zLevel,
            Colour borderColour,
            Colour insideColour,
            PlayerEntity player) {
        super(topleft, bottomright, zLevel, borderColour, insideColour);
        this.container = container;
        this.player = player;

        if (container != null) {
            loadPoints(container.getModularItemToSlotMap().keySet().size());
        } else {
            loadIndices();
            if (indices != null && !indices.isEmpty()) {
                loadPoints(indices.size());
                loadItems();
            }
        }
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        if (container != null) {
            loadPoints(container.getModularItemToSlotMap().keySet().size());
        } else {
            loadIndices();
            if (indices != null && !indices.isEmpty()) {
                loadPoints(indices.size());
                loadItems();
            }
        }
    }

    public int getLastItemSlot() {
        return lastItemSlot;
    }

    public int getSelectedItemSlot() {
        return selectedItemStack;
    }

    private void loadPoints(int num) {
        double centerx = (border.left() + border.right()) / 2;
        double centery = (border.top() + border.bottom()) / 2;

        itemPoints = new ArrayList();
        List<MusePoint2D> targetPoints = GradientAndArcCalculator.pointsInLine(num,
                new MusePoint2D(centerx, border.top()),
                new MusePoint2D(centerx, border.bottom()), 0, 18);
        for (MusePoint2D point : targetPoints) {
            // Fly from middle over 200 ms
            itemPoints.add(new FlyFromPointToPoint2D(new MusePoint2D(centerx, centery), point, 200));
        }
        totalsize = (targetPoints.size() + 1) * 18; // slot height of 16 + spacing of 2
    }

    private void loadIndices() {
        indices = new ArrayList<>();
        for(int index = 0; index < player.inventory.getSizeInventory(); index++) {
            // only load equipped items.
            if (index > 35 || player.inventory.currentItem == index) {
                if (player.inventory.getStackInSlot(index).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m -> m instanceof IModularItem).orElse(false)) {
                    indices.add(index);
                }
            }
        }
    }

    private void loadItems() {
        if (container != null) {
            itemButtons = new ArrayList<>();
            Iterator<MusePoint2D> pointiterator = itemPoints.iterator();
            for (Integer slotIndex : container.getModularItemToSlotMap().keySet()) {
                Slot slot = container.getSlot(slotIndex);
                int index = slot.getSlotIndex();

                ClickableItem button = new ClickableItem(pointiterator.next(), index);
                button.containerIndex = slotIndex;
                itemButtons.add(button);
            }
            // cosmetic preset gui doesn't use a container.
        } else if (indices != null && !indices.isEmpty()) {
            itemButtons = new ArrayList<>();
            Iterator<MusePoint2D> pointiterator = itemPoints.iterator();
            for (Integer index : indices) {
                itemButtons.add(new ClickableItem(pointiterator.next(), index));
            }
        }
    }

    @Override
    public void update(double mousex, double mousey) {
        loadItems();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.currentscrollpixels = Math.min(currentscrollpixels, getMaxScrollPixels());
        super.preRender(matrixStack, mouseX, mouseY, partialTicks);
        matrixStack.push();
        matrixStack.translate(0, -currentscrollpixels, 0);
        drawItems(matrixStack, mouseX, mouseY, partialTicks);
        drawSelection(matrixStack, mouseX, mouseY, partialTicks);
        matrixStack.pop();
        super.postRender(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (this.border.containsPoint(mouseX, mouseY)) {
            this.currentscrollpixels = (int) MuseMathUtils.clampDouble((double)(this.currentscrollpixels =
                    (int)((double)this.currentscrollpixels - dWheel * this.getScrollAmount())), 0.0D, this.getMaxScrollPixels());
            return true;
        } else {
            return false;
        }
    }

    private void drawItems(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (ClickableItem item : itemButtons) {
            item.render(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        }
    }

    private void drawSelection(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (selectedItemStack != -1) {
            MusePoint2D pos = itemButtons.get(selectedItemStack).getPosition();
            if (pos.getY() > this.currentscrollpixels + border.top() + 4 && pos.getY() < this.currentscrollpixels + border.top() + border.height() - 4) {
                MuseRenderer.drawCircleAround(matrixStack, pos.getX(), pos.getY(), 10, getzLevel());
            }
        }
    }

    public boolean hasNoItems() {
        return itemButtons.size() == 0;
    }

    public ClickableItem getPreviousSelectedItem() {
        if (itemButtons.size() > lastItemSlot && lastItemSlot != -1) {
            return itemButtons.get(lastItemSlot);
        } else {
            return null;
        }
    }

    public ClickableItem getSelectedItem() {
        if (itemButtons.size() > selectedItemStack && selectedItemStack != -1) {
            return itemButtons.get(selectedItemStack);
        } else {
            return null;
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (super.mouseClicked(x, y, button)) {
            return true;
        }

        if (border.containsPoint(x, y)) {
            y += currentscrollpixels;
            int i = 0;
            for (ClickableItem item : itemButtons) {
                if (item.hitBox(x, y)) {
                    lastItemSlot = selectedItemStack;
                    Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                    selectedItemStack = i;
                    if(getSelectedItem() != getPreviousSelectedItem())
                        onSelected();
                    return true;
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (border.containsPoint(x, y)) {
            y += currentscrollpixels;
            if (itemButtons != null) {
                for (ClickableItem item : itemButtons) {
                    if (item.hitBox(x, y)) {
                        return item.getToolTip();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets code to be executed when a new item is selected
     * @param doThisIn
     */
    OnSelectNewItem doThis;
    public void setDoOnNewSelect(OnSelectNewItem doThisIn) {
        doThis = doThisIn;
    }

    /**
     * runs preset code when new item is selected
     */
    void onSelected() {
        if(this.doThis != null) {
            this.doThis.onSelected(this);
        }
    }

    public interface OnSelectNewItem {
        void onSelected(ItemSelectionFrame doThis);
    }
}