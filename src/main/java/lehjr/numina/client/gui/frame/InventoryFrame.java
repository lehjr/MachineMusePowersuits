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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.geometry.IDrawable;
import lehjr.numina.client.gui.geometry.IDrawableRect;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.gui.slot.IHideableSlot;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class InventoryFrame extends ScrollableFrame implements IContainerULOffSet {
    IContainerULOffSet.ulGetter ulGetter;
    AbstractContainerMenu container;
//    Color gridColor;
    public final int gridWidth;
    protected List<Integer> slotIndexes;
    List<SlotRect> tiles;
    MusePoint2D slot_ulShift = new MusePoint2D(0, 0);
    int slotWidth = 18;
    int slotHeight = 18;

    /** number of visible rows */
    int numVisibleRows = -1;
    int scrollLimit = -1;
    int currentScroll=0;

    public InventoryFrame(Rect rect,
                          AbstractContainerMenu containerIn,
                          int gridWidth,
                          List<Integer> slotIndexesIn,
                          IContainerULOffSet.ulGetter ulGetter) {
        super(rect);
        this.ulGetter = ulGetter;
        this.container = containerIn;
        this.gridWidth = gridWidth;
        this.slotIndexes = slotIndexesIn;
        this.tiles = new ArrayList<>();
        setNewValues(slotIndexesIn);
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
        if (totalRows * slotHeight > height())  {
            this.numVisibleRows = (int) Math.ceil(height() / slotHeight);
            scrollLimit = totalRows - this.numVisibleRows;
        }
        loadSlots();
        return this;
    }

    Pair<Integer, Integer> getVisibleRows() {
        int totalRows = getTotalRows();
        this.numVisibleRows = (int) Math.ceil(height() / slotHeight);
        if (numVisibleRows < totalRows) {
            return Pair.of(currentScroll, numVisibleRows + currentScroll);
        }
        return Pair.of(0, numVisibleRows);
    }

//    int getVisibileGridHeight() {
//        return (visibleRows > 0 ? visibleRows : gridHeight) * slotHeight;
//    }

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

        MusePoint2D ul = new MusePoint2D(left(), top());

        int i = gridRange.getLeft() * gridWidth;
        outerLoop:
        for(int row = gridRange.getLeft(); row < gridRange.getRight(); row ++) {
            for (int col = 0; col < gridWidth; col ++) {
                if (i == slotIndexes.size()){
                    break outerLoop;
                }
                tiles.add(getNewRect(ul.plus(slot_ulShift)));//.setBorderShrinkValue(0.5F));

                if (i > 0) {
                    if (col > 0) {
                        this.tiles.get(i).setRightOf(this.tiles.get(i - 1));
                    }

                    if (row > 0) {
                        this.tiles.get(i).setBelow(this.tiles.get(i - this.gridWidth));
                    }
                }

                MusePoint2D position = new MusePoint2D(this.tiles.get(i).left(), this.tiles.get(i).top()).minus(slot_ulShift);
                Slot slot = container.getSlot(slotIndexes.get(i));

                if (slot instanceof IHideableSlot) {
                    ((IHideableSlot) slot).setPosition(position);
                    ((IHideableSlot) slot).enable();
                } else {
                    slot.x = (int) position.x();
                    slot.y = (int) position.y();
                }
                i++;
            }
        }
    }

    public SlotRect getNewRect(MusePoint2D ul) {
        return new SlotRect(ul);
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
//        if (super.mouseClicked(mouseX, mouseY, button)) {
//           for(int i =0; i < tiles.size(); i++) {
//               if (tiles.get(i).containsPoint(mouseX, mouseY)) {
//                   NuminaLogger.logDebug("mouse over slot: " + i);
//               }
//           }
//        }


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
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        loadSlots();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        if (this.tiles != null && !this.tiles.isEmpty()) {
            for (SlotRect tile : tiles) {
                // add slight offset so the lines show up (this is why the param was added)
                tile.render(matrixStack, mouseX, mouseY, partialTick);
            }
        }
    }

    @Override
    public List<Component> getToolTip(int i, int i1) {
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


    class SlotRect extends Rect implements IDrawableRect {
        public final ResourceLocation BACKGROUND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/container/slot_rect.png");
        float zLevel = 0;

        public SlotRect(MusePoint2D ul) {
            super(ul, ul.plus(slotWidth, slotHeight));
        }

        @Override
        public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, BACKGROUND);
            IconUtils.INSTANCE.blit(matrixStack, left(), top(), this.getZLevel(), 0, 0, width(), height(), 18, 18);
        }

        @Override
        public float getZLevel() {
            return zLevel = 0;
        }

        @Override
        public IDrawable setZLevel(float zLevel) {
            this.zLevel = zLevel;
            return this;
        }

        Minecraft getMinecraft() {
            return Minecraft.getInstance();
        }
    }
}