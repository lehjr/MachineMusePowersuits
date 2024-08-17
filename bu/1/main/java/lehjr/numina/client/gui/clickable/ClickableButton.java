///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package com.lehjr.numina.client.gui.clickable;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.lehjr.numina.client.gui.geometry.DrawableRect;
//import com.lehjr.numina.client.gui.geometry.IDrawable;
//import com.lehjr.numina.client.gui.geometry.MusePoint2D;
//import com.lehjr.numina.common.math.Color;
//import com.lehjr.numina.common.string.StringUtils;
//import net.minecraft.network.chat.Component;
//
///**
// * @author MachineMuse
// */
//@Deprecated // REMOVE!!
//public class ClickableButton extends DrawableRect implements IClickable {
//    boolean isVisible = true;
//    boolean isEnabled = true;
//    protected Component label;
//    protected MusePoint2D radius;
//
//    private Color enabledBorder  = new Color(0.3F, 0.3F, 0.3F, 1);
//    private Color enabledBackground = new Color(0.5F, 0.6F, 0.8F, 1);
//    private Color disabledBorder = new Color(0.8F, 0.6F, 0.6F, 1);
//    private Color disabledBackground = new Color(0.8F, 0.3F, 0.3F, 1);
//    private IPressable onPressed;
//    private IReleasable onReleased;
//
//    public ClickableButton(Component label, MusePoint2D position, boolean enabled) {
//        super(0,0,0, 0, Color.BLACK, Color.BLACK);
//        this.label = label;
//        this.setPosition(position);
//
//        if (label.getString().contains("\n")) {
//            String[] x = label.getString().split("\n");
//
//            int longestIndex = 0;
//            for (int i = 0; i < x.length; i++) {
//                if (x[i].length() > x[longestIndex].length())
//                    longestIndex = i;
//            }
//            this.radius = new MusePoint2D((float) (StringUtils.getStringWidth(x[longestIndex]) / 2F + 2F), 6 * x.length);
//        } else {
//            this.radius = new MusePoint2D((float) (StringUtils.getStringWidth(label.getString()) / 2F + 2F), 6);
//        }
//
//        setLeft(position.x() - radius.x());
//        setTop(position.y() - radius.y());
//        setWidth(radius.x() * 2);
//        setHeight(radius.y() * 2);
//        setBorderColor(enabledBorder);
//        setBackgroundColor(enabledBackground);
//        this.setEnabled(enabled);
//    }
//
//    public ClickableButton setEnabledBorder(Color enabledBorder) {
//        this.enabledBorder = enabledBorder;
//        return this;
//    }
//
//    public ClickableButton setEnabledBackground(Color enabledBackground) {
//        this.enabledBackground = enabledBackground;
//        return this;
//    }
//
//    public ClickableButton setDisabledBorder(Color disabledBorder) {
//        this.disabledBorder = disabledBorder;
//        return this;
//    }
//
//    public ClickableButton setDisabledBackground(Color disabledBackground) {
//        this.disabledBackground = disabledBackground;
//        return this;
//    }
//
//    void setBackgroundColor(Color backgroundColor, boolean hovered) {
//        super.setBackgroundColor(hovered? backgroundColor.copy().lighten(0.10F) : backgroundColor);
//    }
//
//    /**
//     * AbstractContainerMenu based GUI's should use the separate button and text renderer
//     *
//     * @param mouseX
//     * @param mouseY
//     * @param partialTick
//     */
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        renderButton(matrixStack, mouseX, mouseY, partialTick);
//        renderText(matrixStack, mouseX, mouseY);
//    }
//
//    @Override
//    public float getZLevel() {
//        return 0;
//    }
//
//    @Override
//    public IDrawable setZLevel(float zLevel) {
//        return null;
//    }
//
//    /**
//     * AbstractContainerMenu based GUI's should use the separate button and text renderer
//     * Call this from the container GUI's main render loop
//     *
//     * @param mouseX
//     * @param mouseY
//     * @param frameTIme
//     */
//    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float frameTIme) {
//        if (isVisible) {
//
//            setBackgroundColor(isEnabled() ? enabledBackground : disabledBackground, containsPoint(mouseX, mouseY));
//
//
//
//            this.setBorderColor(isEnabled() ? enabledBorder : disabledBorder);
//
//
//            super.render(matrixStack, mouseX, mouseY, frameTIme);
//        }
//    }
//
//
//    /**
//     * AbstractContainerMenu based GUI's should use the separate button and text renderer
//     * Call this from the container GUI's drawGuiAbstractContainerMenuForegroundLayer
//     * @param mouseX
//     * @param mouseY
//     */
//    public void renderText(PoseStack matrixStack, int mouseX, int mouseY) {
//        if (isVisible()) {
//            if (label.getString().contains("\n")) {
//                String[] s = label.getString().split("\n");
//                for (int i = 0; i < s.length; i++) {
//                    StringUtils.drawShadowedStringCentered(matrixStack, s[i], centerX(), centerY() + (i * StringUtils.getStringHeight() + 2));
//                }
//            } else {
//                StringUtils.drawShadowedStringCentered(matrixStack, this.label.getString(), centerX(), centerY());
//            }
//        }
//    }
//
//    public MusePoint2D getRadius () {
//        return radius.copy();
//    }
//
//    @Override
//    public void setEnabled(boolean enabled) {
//        this.isEnabled = enabled;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    @Override
//    public void setVisible(boolean visible) {
//        this.isVisible = visible;
//    }
//
//    @Override
//    public boolean isVisible() {
//        return isVisible;
//    }
//
//    @Override
//    public void setOnPressed(IPressable onPressed) {
//        this.onPressed = onPressed;
//    }
//
//    @Override
//    public void setOnReleased(IReleasable onReleased) {
//        this.onReleased = onReleased;
//    }
//
//    @Override
//    public void onPressed() {
//        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
//            this.onPressed.onPressed(this);
//        }
//    }
//
//    @Override
//    public void onReleased() {
//        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
//            this.onReleased.onReleased(this);
//        }
//    }
//
//    public ClickableButton setLable(Component label) {
//        this.label = label;
//        return this;
//    }
//
//    public Component getLabel() {
//        return label;
//    }
//}
