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

package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.slot.IHideableSlot;
import lehjr.numina.client.gui.slot.UniversalSlot;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class InventoryFrame extends ScrollableFrame implements IContainerULOffSet {
    IContainerULOffSet.ulGetter ulGetter;
    Container container;
//    Colour gridColour;
    public final int gridWidth;
    public final int gridHeight;
    List<Integer> slotIndexes;
    List<DrawableTile> tiles;
    MusePoint2D slot_ulShift = new MusePoint2D(0, 0);
    int slotWidth = 18;
    int slotHeight = 18;
    int visibleRows = -1;
    int scrollLimit = -1;
    int currentScroll=0;

    public InventoryFrame(Container containerIn,
                          int gridWidth,
                          int gridHeight,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        this(containerIn,
                Colour.BLACK,
                new Colour(0.216F, 0.216F, 0.216F, 1F),
                Colour.WHITE.withAlpha(0.8F),
                gridWidth, gridHeight, slotIndexesIn, ulGetter);
    }

    public InventoryFrame(Container containerIn,
                          int gridWidth,
                          int gridHeight,
                          int visibleRows,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        this(containerIn,
                Colour.BLACK,
                new Colour(0.216F, 0.216F, 0.216F, 1F),
                Colour.WHITE.withAlpha(0.8F),
                gridWidth, gridHeight, visibleRows, slotIndexesIn, ulGetter);
    }

    public InventoryFrame(Container containerIn,
                          Colour backgroundColour,
                          Colour topBorderColour,
                          Colour bottomBorderColour,
                          int gridWidth,
                          int gridHeight,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        this(
                containerIn,
                backgroundColour,
                topBorderColour,
                bottomBorderColour,
                gridWidth,
                gridHeight,
                -1,
                slotIndexesIn,
                ulGetter);
    }

    public InventoryFrame(Container containerIn,
                          Colour background,
                          Colour topBorder,
                          Colour bottomBorder,
                          int gridWidth,
                          int gridHeight,
                          int visibleRows,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        super();
        super.setBackgroundColour(background);
        super.setTopBorderColour(topBorder);
        super.setBottomBorderColour(bottomBorder);
        this.ulGetter = ulGetter;
        this.container = containerIn;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.slotIndexes = slotIndexesIn;
        this.tiles = new ArrayList<>();
        this.visibleRows = visibleRows;
        int totalRows = (int) Math.ceil((double)slotIndexesIn.size() / gridWidth);
        if (totalRows > gridHeight) {
            this.visibleRows = gridHeight;
            scrollLimit = totalRows - gridHeight;
        }

        // set the height based on the grid height regardless if the number of rows fills it. Useful for swapping inventories without resizing.
        super.setWidth(slotWidth * gridWidth).setHeight( slotHeight * gridHeight );
        setUL(new MusePoint2D(0,0));
    }

    public InventoryFrame setNewValues(List<Integer> slotIndexesIn) {
        // update current slots
        for (int index : slotIndexes) {
            Slot slot = container.getSlot(index);
            if (slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
                ((IHideableSlot) slot).setPosition(new MusePoint2D(-1000,-1000));
            }
        }

        this.slotIndexes = slotIndexesIn;
        int totalRows = (int) Math.ceil((double)slotIndexesIn.size() / gridWidth);
        if (totalRows > gridHeight) {
            this.visibleRows = gridHeight;
            scrollLimit = totalRows - gridHeight;
        }
        loadSlots();
        return this;
    }

    @Override
    public DrawableTile setBackgroundColour(Colour backgroundColour) {
        super.setBackgroundColour(backgroundColour);
        return this;
    }

    @Override
    public DrawableTile setTopBorderColour(Colour borderColour) {
        super.setTopBorderColour(borderColour);
        return this;
    }

    @Override
    public DrawableTile setBottomBorderColour(Colour borderColour) {
        super.setBottomBorderColour(borderColour);
        return this;
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    Pair<Integer, Integer> getVisibleRows() {
        int totalRows = getTotalRows();
        if (visibleRows < totalRows) {
            return Pair.of(currentScroll, gridHeight + currentScroll);
        }
        return Pair.of(0, gridHeight);
    }

    int getVisibileGridHeight() {
        return (visibleRows > 0 ? visibleRows : gridHeight) * slotHeight;
    }

    int getTotalRows() {
        return (int) Math.ceil((double)slotIndexes.size() / gridWidth);
    }

    public void loadSlots() {
        tiles = new ArrayList<>();
        if (slotIndexes.isEmpty()) {
            return;
        }

        this.slot_ulShift = getULShift();
        Pair<Integer, Integer> gridRange = getVisibleRows();
        MusePoint2D ul = new MusePoint2D(finalLeft(), finalTop());

        int i = gridRange.getLeft() * gridWidth;
        outerLoop:
        for(int row = gridRange.getLeft(); row < gridRange.getRight(); row ++) {
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
                    ((IHideableSlot) slot).enable();
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
        this.currentScroll = (int) MathUtils.clampDouble(currentScroll + dWheel, 0, (scrollLimit > 0 ? scrollLimit: 0));
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
        if (drawBackground) {
            drawBackground(matrixStack);
        }
        if (this.tiles != null && !this.tiles.isEmpty()) {
            for (DrawableTile tile : tiles) {
                // add slight offset so the lines show up (this is why the param was added)
                tile.render(matrixStack, mouseX, mouseY, frameTime);
            }
        }
        if (drawBorder) {
            drawBorder(matrixStack, 0); // fixme
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
     * @return
     */
    @Override
    public MusePoint2D getULShift() {
        int offset = 16; // default vanilla slot size

        if (ulGetter == null) {
            return new MusePoint2D(0, 0).plus((offset - slotWidth) * 0.5, (offset - slotHeight) * 0.5);
        }
        return ulGetter.getULShift().plus((offset - slotWidth) * 0.5, (offset - slotHeight) * 0.5);
    }
}