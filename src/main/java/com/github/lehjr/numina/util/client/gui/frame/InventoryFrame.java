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

package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.numina.util.client.gui.slot.UniversalSlot;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class InventoryFrame extends ScrollableFrame implements IContainerULOffSet {
    IContainerULOffSet.ulGetter ulGetter;
    Container container;
    Colour gridColour;
    public final int gridWidth;
    public final int gridHeight;
    List<Integer> slotIndexes;
    List<DrawableTile> tiles;
    MusePoint2D slot_ulShift = new MusePoint2D(0, 0);
    boolean drawBackground = false;
    boolean drawBorder = false;
    int slotWidth = 18;
    int slotHeight = 18;

    public InventoryFrame(Container containerIn,
                          int gridWidth,
                          int gridHeight,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        this(containerIn, Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY, gridWidth, gridHeight, slotIndexesIn, ulGetter);
    }

    public InventoryFrame(Container containerIn,
                          Colour backgroundColour,
                          Colour borderColour,
                          Colour gridColourIn,
                          int gridWidth,
                          int gridHeight,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        super(backgroundColour, borderColour);
        this.ulGetter = ulGetter;
        this.container = containerIn;
        this.gridColour = gridColourIn;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.slotIndexes = slotIndexesIn;
        this.tiles = new ArrayList<>();
        super.setWidth(slotWidth * gridWidth).setHeight(slotHeight * gridHeight);
        setUL(new MusePoint2D(0,0));
    }




    public DrawableRelativeRect setBackgroundColour(Colour backgroundColour) {
        super.setBackgroundColour(backgroundColour);
        return this;
    }

    public DrawableRelativeRect setBorderColour(Colour borderColour) {
        super.setBorderColour(borderColour);
        return null;
    }

    public DrawableRelativeRect setGridColour(Colour gridColour) {
        this.gridColour = gridColour;
        return this;
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    public void loadSlots() {
        this.slot_ulShift = getULShift(this);

        MusePoint2D ul = new MusePoint2D(finalLeft(), finalTop());
        tiles = new ArrayList<>();
        int i = 0;
        outerLoop:
        for(int row = 0; row < gridHeight; row ++) {
            for (int col = 0; col < gridWidth; col ++) {
                if (i == slotIndexes.size()){
                    break outerLoop;
                }
                tiles.add(new DrawableTile(ul, ul.plus(new MusePoint2D(slotWidth, slotHeight))).setBorderShrinkValue(0.5F));

                if (i > 0) {
                    if (col > 0) {
                        this.tiles.get(i).setMeRightOf(this.tiles.get(i - 1));
                    }

                    if (row > 0) {
                        this.tiles.get(i).setMeBelow(this.tiles.get(i - this.gridWidth));
                    }
                }

                MusePoint2D position = new MusePoint2D(this.tiles.get(i).finalLeft(), this.tiles.get(i).finalTop()).minus(slot_ulShift);
                Slot slot = container.getSlot(slotIndexes.get(i));
                if (slot instanceof UniversalSlot) {
                    ((UniversalSlot) slot).setPosition(position);
                } else if (slot instanceof IHideableSlot) {
                    ((IHideableSlot) slot).setPosition(position);
                } else {
                    slot.x = (int) position.getX();
                    slot.y = (int) position.getY();
                }
                i++;
            }
        }
    }

    public InventoryFrame setSlotWidth(int slotWidthIn) {
        this.slotWidth = slotWidthIn;
        setWH(new MusePoint2D(slotWidth, slotHeight));
        return this;
    }

    public InventoryFrame setSlotHeight(int slotHeightIn) {
        this.slotHeight = slotHeightIn;
        setWH(new MusePoint2D(slotWidth, slotHeight));
        return this;
    }

    public int getSlotWidth() {
        return this.slotWidth;
    }

    public int getSlotHeight() {
        return this.gridHeight;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int buton) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public void initGrowth() {
        super.initGrowth();
        loadSlots();
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        loadSlots();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(0);
        if (drawBorder || drawBackground) {
            buffer = getVertices(3);
        }
        if (drawBackground) {
            drawBackground(matrixStack, buffer);
        }
        if (this.tiles != null && !this.tiles.isEmpty()) {
            for (DrawableTile tile : tiles) {
                // add slight offset so the lines show up (this is why the param was added)
                tile.render(matrixStack, mouseX, mouseY, frameTime);
            }
        }
        if (drawBorder) {
            drawBorder(matrixStack, buffer); // fixme
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }

    @Override
    public void setULGetter(IContainerULOffSet.ulGetter ulGetter) {
        this.ulGetter = ulGetter;
    }

    /**
     * returns the offset needed to compensate for the container GUI in the super class rendering the slots with an offset.
     * Also compensates for slot render sizes larger than vanilla
     * @param frame
     * @return
     */
    @Override
    public MusePoint2D getULShift(IContainerULOffSet frame) {
        int offset = 16; // default vanilla slot size

        if (ulGetter == null) {
            return new MusePoint2D(0, 0).plus((offset - slotWidth) * 0.5, (offset - slotHeight) * 0.5);
        }
        return ulGetter.getULShift(frame).plus((offset - slotWidth) * 0.5, (offset - slotHeight) * 0.5);
    }
}