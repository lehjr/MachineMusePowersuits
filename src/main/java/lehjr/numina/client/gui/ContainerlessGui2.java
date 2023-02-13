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

package lehjr.numina.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.gemoetry.DrawableRect;
import lehjr.numina.common.math.Colour;
import net.minecraft.util.text.ITextComponent;

@Deprecated
public class ContainerlessGui2 extends ContainerlessGui {
    /** The outer gui rectangle */
    protected DrawableRect backgroundRect;

    protected long creationTime;



    public ContainerlessGui2(ITextComponent titleIn, boolean growFromMiddle) {
        super(titleIn);
        backgroundRect = new DrawableRect(0, 0, 0, 0, growFromMiddle, Colour.GREY_GUI_BACKGROUND, Colour.BLACK);
    }

    public ContainerlessGui2(ITextComponent titleIn, int guiWidth, int guiHeight, boolean growFromMiddle) {
        this(titleIn, growFromMiddle);
        this.imageWidth = guiWidth;
        this.imageHeight = guiHeight;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void init() {
        super.init();
//        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        creationTime = System.currentTimeMillis();
        backgroundRect.setLeft(absX(-1)).setTop(absY(-1)).setRight(absX(1)).setBottom(absY(1));
//        this.leftPos = (this.width - this.imageWidth) / 2;
//        this.topPos = (this.height - this.imageHeight) / 2;
    }



    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
    }

    public void renderBackgroundRect(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        backgroundRect.render(matrixStack, mouseX, mouseY, frameTime);
    }

    /**
     * Called every frame, draws the screen!
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        this.renderBackground(matrixStack);
        this.renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        update(mouseX, mouseY);
        matrixStack.pushPose();
        matrixStack.translate(0, 0, 10);
//        if (this.backgroundRect.doneGrowing()) {
            renderFrames(matrixStack, mouseX, mouseY, frameTime);
            matrixStack.translate(0, 0, 10);
            super.render(matrixStack, mouseX, mouseY, frameTime);
            matrixStack.translate(0, 0, 100);
            renderLabels(matrixStack, mouseX, mouseY);
            matrixStack.popPose();
            this.renderTooltip(matrixStack, mouseX, mouseY);
//        }
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param relx Relative X coordinate
     * @return Absolute X coordinate
     */
    public int absX(double relx) {
        int absx = (int) ((relx + 1) * getImageWidth() / 2);
        int xpadding = (width - getImageWidth()) / 2;
        return absx + xpadding;
    }

    /**
     * Returns relative coordinate (float -1.0F to +1.0F) from absolute
     * coordinates (int 0 to width)
     */
    public int relX(double absx) {
        int padding = (width - getImageWidth()) / 2;
        return (int) ((absx - padding) * 2 / getImageWidth() - 1);
    }

    /**
     * Returns absolute screen coordinates (int 0 to width) from a relative
     * coordinate (float -1.0F to +1.0F)
     *
     * @param rely Relative Y coordinate
     * @return Absolute Y coordinate
     */
    public int absY(double rely) {
        int absy = (int) ((rely + 1) * imageHeight / 2);
        int ypadding = (height - imageHeight) / 2;
        return absy + ypadding;
    }

    /**
     * Returns relative coordinate (float -1.0F to +1.0F) from absolute
     * coordinates (int 0 to width)
     */
    public int relY(float absy) {
        int padding = (height - getYSize()) / 2;
        return (int) ((absy - padding) * 2 / getYSize() - 1);
    }
}