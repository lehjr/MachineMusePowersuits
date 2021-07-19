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

import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawableRect;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IGuiFrame extends IDrawableRect {
    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse click is inside this frame and is handled here, else false
     */
    boolean mouseClicked(double mouseX, double mouseY, int button);

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     * @return true if mouse release is inside this frame and is handled here, else false
     */
    boolean mouseReleased(double mouseX, double mouseY, int button);

    /**
     * @param mouseX
     * @param mouseY
     * @param dWheel
     * @return true if mouse pointer is inside this frame and scroll is handled here, else false
     */
    boolean mouseScrolled(double mouseX, double mouseY, double dWheel);

    /**
     * Fired when gui init is fired, during the creation phase and on resize. Can be used to setup the frame
     * including setting target dimensions.
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    default void init(double left, double top, double right, double bottom) {
        setLeft(left);
        setTop(top);
        setWidth(right - left);
        setHeight(bottom - top);
    }

    /**
     * Miscellaneous functions required before rendering
     * @param mouseX
     * @param mouseY
     */
    void update(double mouseX, double mouseY);

    /**
     * Render elements of this frame. Ordering is important.
     *
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    List<ITextComponent> getToolTip(int x, int y);

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void setVisible(boolean visible);

    boolean isVisible();

    default void hide() {
        setVisible(false);
    }

    default void show() {
        setVisible(true);
    }

    default void enable() {
        setEnabled(true);
    }

    default void disable() {
        setEnabled(false);
    }

    default void enableAndShow() {
        enable();
        show();
    }

    default void disableAndHide() {
        disable();
        hide();
    }
}