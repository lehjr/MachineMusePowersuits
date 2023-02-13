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

package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.frame.LabelBox;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

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
    /*
        slider background x
        top 0-75; 130-200




     */







    boolean isVisible = true;
    boolean isEnabled = true;
    LabelBox labelBox;
    public Slider slider;

    public ClickableSlider(MusePoint2D position, double size, String id, TranslationTextComponent label) {
        this(position, true, size, id, 0, label);
    }

    public ClickableSlider(MusePoint2D position,
                           boolean isHorizontal,
                           double size,
                           String id,
                           double currentVal, TranslationTextComponent label) {
        this(position, isHorizontal, size, id, currentVal, null, label);
    }

    public ClickableSlider(MusePoint2D position,
                           boolean isHorizontal,
                           double size,
                           String id,
                           double currentVal,
                           @Nullable Slider.ISlider iSlider, TranslationTextComponent label) {
        super(1,1,1,1);//position);
        if (isHorizontal) {
            super.setWidth(size);
            super.setHeight(16 + 8);

        } else {
            super.setHeight(size);
            super.setWidth(16 + 8);
        }
        this.labelBox = new LabelBox(size, 16, label);
        this.labelBox.setColor(Colour.WHITE);
        this.slider = new Slider(position, isHorizontal, size, id, currentVal, iSlider);
        this.slider.setThickness(6);
        this.slider.setBelow(this.labelBox);
        System.out.println("slider height: " + slider.height());


        super.setHeight(labelBox.height() + slider.height());
        super.setWidth(labelBox.width());
        super.setPosition(position);
    }
    public String id() {
        return this.slider.id();
    }

    public void setLabel(TranslationTextComponent label) {
        this.labelBox.setLabel(label);
    }

    public void setLabelColour(Colour colour) {
        this.labelBox.setColor(colour);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        labelBox.setPosition(new MusePoint2D(centerX(), top() + (labelBox.height() * 0.5)));
        slider.setPosition(new MusePoint2D(centerX(), bottom() - (slider.height() * 0.5)));

        if (this.isVisible()) {
            this.slider.render(matrixStack, mouseX, mouseY, frameTime);
            this.renderButton(matrixStack, mouseX, mouseY, frameTime);

            this.labelBox.renderLabel(matrixStack, 0, 2);
        }
    }

    /**
     * FIXME... this renders right of where it should (also, this is the button )
     * @param pMatrixStack
     * @param pMouseX
     * @param pMouseY
     */


    protected void renderBg(MatrixStack pMatrixStack, int pMouseX, int pMouseY) {
        Minecraft.getInstance().getTextureManager().bind(Widget.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.containsPoint(pMouseX, pMouseY) ? 2 : 1) * 20;

        // position.getx should be just left(), position.getY should be top()
        Minecraft.getInstance().screen.blit(pMatrixStack,
                (int) (this.centerX() + (int)(this.getValue() * (double)(this.width() - 8))),
                (int) this.centerY(), 0, 46 + i, 4, 20);
        Minecraft.getInstance().screen.blit(pMatrixStack,
                (int) (this.centerX() + (int)(this.getValue() * (double)(this.width() - 8)) + 4),
                (int) this.centerY(), 196, 46 + i, 4, 20);


        // public void blit(MatrixStack pMatrixStack, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight) {
    }

    float alpha = 1;

    /**
     * FIXME: this renders above where it should
     *
     * @param pMatrixStack
     * @param pMouseX
     * @param pMouseY
     * @param pPartialTicks
     */
    public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(Widget.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(containsPoint(pMouseX, pMouseY));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        Minecraft.getInstance().screen.blit(pMatrixStack,
                (int) this.getUL().x(),
                (int) this.getUL().y(),
                0, 46 + i * 20, (int) (this.width() / 2), (int) this.height());
        Minecraft.getInstance().screen.blit(pMatrixStack, (int) (this.getUL().x() + this.width() / 2), (int) this.getUL().y(), (int) (200 - this.width() / 2), 46 + i * 20, (int) (this.width() / 2), (int) this.height());
        this.renderBg(pMatrixStack, pMouseX, pMouseY);
        int j = getFGColor();
//        drawCenteredString(pMatrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public static final int UNSET_FG_COLOR = -1;
    protected int packedFGColor = UNSET_FG_COLOR;
    public int getFGColor() {
        if (packedFGColor != UNSET_FG_COLOR) return packedFGColor;
        return this.active ? 16777215 : 10526880; // White : Light Grey
    }


    boolean active = false;

    protected int getYImage(boolean pIsHovered) {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (pIsHovered) {
            i = 2;
        }

        return i;
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
            labelBox.setTop(this.top());
        }
    }

    @Override
    public Rect setWH(MusePoint2D wh) {
        return this;
    }

    @Override
    public Rect setWidth(double value) {
        return this;
    }

    @Override
    public Rect setHeight(double value) {
        return this;
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return x > left() - 2 && x < right() + 2 && y > top() - 2 && y < bottom() + 2;
    }
}