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

public class InventoryFrame extends ScrollableFrame {
    Container container;
    Colour backgroundColour;
    Colour gridColour;
    public final int gridWidth;
    public final int gridHeight;
    List<Integer> slotIndexes;
    List<DrawableTile> tiles;
    MusePoint2D slot_ulShift = new MusePoint2D(0, 0);
   boolean drawBackground = false;
    boolean drawBorder = false;

    public InventoryFrame(Container containerIn,
                          MusePoint2D topleft,
                          MusePoint2D bottomright,
                          float zLevel,
                          Colour backgroundColour,
                          Colour borderColour,
                          Colour gridColourIn,
                          int gridWidth,
                          int gridHeight,
                          List<Integer> slotIndexesIn) {
        super(topleft, bottomright, zLevel, backgroundColour, borderColour);
        this.container = containerIn;
        this.backgroundColour = backgroundColour;
        this.gridColour = gridColourIn;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.slotIndexes = slotIndexesIn;
        this.tiles = new ArrayList<>();
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
    }

    public void loadSlots() {
        MusePoint2D wh = new MusePoint2D(18, 18);
        MusePoint2D ul = new MusePoint2D(border.left(), border.top());
        tiles = new ArrayList<>();
        int i = 0;
        outerLoop:
        for(int row = 0; row < gridHeight; row ++) {
            for (int col = 0; col < gridWidth; col ++) {
                if (i == slotIndexes.size()){
                    break outerLoop;
                }
                tiles.add(new DrawableTile(ul, ul.plus(wh)).setBorderShrinkValue(0.5F));

                if (i > 0) {
                    if (col > 0) {
                        this.tiles.get(i).setMeRightOf(this.tiles.get(i - 1));
                    }

                    if (row > 0) {
                        this.tiles.get(i).setMeBelow(this.tiles.get(i - this.gridWidth));
                    }
                }

                MusePoint2D position = this.tiles.get(i).center().copy().minus(slot_ulShift);
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

    @Override
    public boolean mouseClicked(double v, double v1, int i) {
        return false;
    }

    @Override
    public boolean mouseReleased(double v, double v1, int i) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double v, double v1, double v2) {
        return false;
    }

    public MusePoint2D getUlShift() {
        return slot_ulShift;
    }

    public void setUlShift(MusePoint2D ulShift) {
        this.slot_ulShift = ulShift;
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        loadSlots();
    }

    @Override
    public void update(double mouseX, double mouseY) {
        loadSlots();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(0);
//        RenderSystem.disableDepthTest();
        if (drawBorder || drawBackground) {
            buffer = border.getVertices(3);
            border.setzLevel(zLevel);
        }
        if (drawBackground) {
            border.drawBackground(matrixStack, buffer);
        }
        if (this.tiles != null && !this.tiles.isEmpty()) {
            for (DrawableTile tile : tiles) {
                // add slight offset so the lines show up (this is why the param was added)
                tile.draw(matrixStack,zLevel);
            }
        }
        if (drawBorder) {
            border.drawBorder(matrixStack, buffer); // fixme
        }
//        RenderSystem.enableDepthTest();
    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }
}
