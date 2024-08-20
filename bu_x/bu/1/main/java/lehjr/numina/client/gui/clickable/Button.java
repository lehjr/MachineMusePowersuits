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
//import com.lehjr.numina.client.gui.geometry.MusePoint2D;
//import com.lehjr.numina.client.gui.geometry.Rect;
//import com.lehjr.numina.common.math.Color;
//
//public class Button extends DrawableRect implements IClickable {
//    protected IPressable onPressed;
//    protected IReleasable onReleased;
//
//    protected boolean isEnabled = true;
//    protected boolean isVisible = true;
//    Color backgroundColorEnabled;
//    Color backgroundColorDisabled;
//    Color borderColorEnabled;
//    Color borderColorDisabled;
//
//    int highlight = 5;
//
//    public Button(double left, double top, double right, double bottom, boolean growFromMiddle,
//                  Color backgroundColorEnabled,
//                  Color backgroundColorDisabled,
//                  Color borderColorEnabled,
//                  Color borderColorDisabled) {
//        super(left, top, right, bottom, growFromMiddle, backgroundColorEnabled, borderColorEnabled);
//        this.backgroundColorEnabled = backgroundColorEnabled;
//        this.backgroundColorDisabled = backgroundColorDisabled;
//        this.borderColorEnabled = borderColorEnabled;
//        this.borderColorDisabled = borderColorDisabled;
//    }
//
//    public Button(double left, double top, double right, double bottom,
//                  Color backgroundColorEnabled,
//                  Color backgroundColorDisabled,
//                  Color borderColorEnabled,
//                  Color borderColorDisabled) {
//        super(left, top, right, bottom, backgroundColorEnabled, borderColorEnabled);
//        this.backgroundColorEnabled = backgroundColorEnabled;
//        this.backgroundColorDisabled = backgroundColorDisabled;
//        this.borderColorEnabled = borderColorEnabled;
//        this.borderColorDisabled = borderColorDisabled;
//    }
//
//    public Button(MusePoint2D ul, MusePoint2D br,
//                  Color backgroundColorEnabled,
//                  Color backgroundColorDisabled,
//                  Color borderColorEnabled,
//                  Color borderColorDisabled) {
//        super(ul, br, backgroundColorEnabled, borderColorEnabled);
//        this.backgroundColorEnabled = backgroundColorEnabled;
//        this.backgroundColorDisabled = backgroundColorDisabled;
//        this.borderColorEnabled = borderColorEnabled;
//        this.borderColorDisabled = borderColorDisabled;
//    }
//
//    public Button(Rect ref,
//                  Color backgroundColorEnabled,
//                  Color backgroundColorDisabled,
//                  Color borderColorEnabled,
//                  Color borderColorDisabled) {
//        super(ref, backgroundColorEnabled, borderColorEnabled);
//        this.backgroundColorEnabled = backgroundColorEnabled;
//        this.backgroundColorDisabled = backgroundColorDisabled;
//        this.borderColorEnabled = borderColorEnabled;
//
//        this.borderColorDisabled = borderColorDisabled;
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
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        if (isVisible) {
//            if (isEnabled()) {
//                if (containsPoint(mouseX, mouseY)) {
//                    super.setBackgroundColor(new Color(
//                            (byte)(this.backgroundColorEnabled.r + highlight < 255 ? this.backgroundColorEnabled.r + highlight : 255),
//                            (byte)(this.backgroundColorEnabled.g + highlight < 255 ? this.backgroundColorEnabled.g + highlight : 255),
//                            (byte)(this.backgroundColorEnabled.b + highlight < 255 ? this.backgroundColorEnabled.b + highlight : 255),
//                            1));
//                } else {
//                    super.setBackgroundColor(this.backgroundColorEnabled);
//                }
//                super.setBorderColor(this.borderColorEnabled);
//            } else {
//                super.setBackgroundColor(backgroundColorDisabled);
//                super.setBorderColor(this.borderColorDisabled);
//            }
//            super.render(matrixStack, mouseX, mouseY, partialTick);
//        }
//    }
//
//    @Override
//    public boolean containsPoint(double x, double y) {
//        if (isVisible() && isEnabled())
//            return x >= left() && x <= right() && y >= top() && y <= bottom();
//        return false;
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
//    public void onReleased() {
//        if (this.isVisible && this.isEnabled && this.onReleased != null) {
//            this.onReleased.onReleased(this);
//        }
//    }
//
//    @Override
//    public void onPressed() {
//        if (this.isVisible && this.isEnabled && this.onPressed != null) {
//            this.onPressed.onPressed(this);
//        }
//    }
//}