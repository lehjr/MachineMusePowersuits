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

package lehjr.numina.util.client.gui.clickable;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.gui.frame.LabelBox;
import lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import lehjr.numina.util.client.gui.gemoetry.IRect;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.math.Color;
import net.minecraft.util.text.TranslatableComponent;

import javax.annotation.Nullable;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:08 AM, 06/05/13
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 *
 * TODO: revisit and rewrite
 *
 */
public class ClickableSlider extends DrawableTile implements IClickable {
    boolean isVisible = true;
    boolean isEnabled = true;
    LabelBox labelBox;
    Slider slider;

    public ClickableSlider(MusePoint2D position, double size, String id, TranslatableComponent label) {
        this(position, true, size, id, 0, label);
    }

    public ClickableSlider(MusePoint2D position,
                           boolean isHorizontal,
                           double size,
                           String id,
                           double currentVal, TranslatableComponent label) {
        this(position, isHorizontal, size, id, currentVal, null, label);
    }

    public ClickableSlider(MusePoint2D position,
                           boolean isHorizontal,
                           double size,
                           String id,
                           double currentVal,
                           @Nullable Slider.ISlider iSlider, TranslatableComponent label) {
        super(1,1,1,1);//position);
        if (isHorizontal) {
            super.setWidth(size);
            super.setHeight(16 + 8);

        } else {
            super.setHeight(size);
            super.setWidth(16 + 8);
        }
        this.labelBox = new LabelBox(size, 16, label);
        this.labelBox.setColor(Color.WHITE);
        this.slider = new Slider(position, isHorizontal, size, id, currentVal, iSlider);
        this.slider.setMeBelow(this.labelBox);
        super.setHeight(labelBox.finalHeight() + slider.finalHeight());
        super.setWidth(labelBox.finalWidth());
        super.setPosition(position);
    }
    public String id() {
        return this.slider.id();
    }

    public void setLabel(TranslatableComponent label) {
        this.labelBox.setLabel(label);
    }

    public void setLabelColor(Color colour) {
        this.labelBox.setColor(colour);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
        labelBox.setPosition(new MusePoint2D(centerx(), top() + (labelBox.finalHeight() * 0.5)));
        slider.setPosition(new MusePoint2D(centerx(), bottom() - (slider.finalHeight() * 0.5)));

        if (this.isVisible()) {
            this.slider.render(matrixStack, mouseX, mouseY, frameTime);
            this.labelBox.renderLabel(matrixStack, 0, 2);
        }
    }

    public void update(double mouseX, double mouseY) {
        this.slider.update(mouseX, mouseY);
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
        this.slider.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean b) {
        this.isVisible = b;
        this.slider.setVisible(b);
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return slider.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return slider.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void setOnPressed(IPressable iPressable) {
        slider.setOnPressed(iPressable);
    }

    @Override
    public void setOnReleased(IReleasable iReleasable) {
        slider.setOnReleased(iReleasable);
    }

    @Override
    public void onPressed() {
        slider.onPressed();
    }

    @Override
    public void onReleased() {
        slider.onReleased();
    }

    public double getValue() {
        return slider.getValue();
    }

    public double getInternalVal() {
        return slider.getInternalVal();
    }

    public void setValue(double value) {
        slider.setValue(value);
    }

    public void setValueByX(double value) {
        slider.setValueByX(value);
    }

    // FIXME: seems to set trac rect UL to parent center
    @Override
    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        // Still null during initializing
        if (labelBox != null) {
            labelBox.setTop(this.finalTop());
        }
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        return this;
    }

    @Override
    public IRect setWidth(double value) {
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        return this;
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return x > left() - 2 && x < right() + 2 && y > top() - 2 && y < bottom() + 2;
    }
}