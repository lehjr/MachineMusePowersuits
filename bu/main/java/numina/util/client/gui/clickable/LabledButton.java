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
//package lehjr.numina.util.client.gui.clickable;
//
//import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import lehjr.numina.util.client.gui.gemoetry.RelativeRect;
//import lehjr.numina.util.client.render.MuseRenderer;
//import lehjr.numina.util.math.Color;
//import com.mojang.blaze3d.matrix.PoseStack;
//
//@Deprecated
//public class LabledButton extends Button {
//    String label;
//
//    public LabledButton(float left, float top, float right, float bottom, boolean growFromMiddle,
//                        Color insideColorEnabled,
//                        Color insideColorDisabled,
//                        Color outsideColorEnabled,
//                        Color outsideColorDisabled,
//                        String label) {
//        super(left, top, right, bottom, growFromMiddle, insideColorEnabled, insideColorDisabled, outsideColorEnabled, outsideColorDisabled);
//        this.label = label;
//    }
//
//    public LabledButton(float left, float top, float right, float bottom,
//                        Color insideColorEnabled,
//                        Color insideColorDisabled,
//                        Color outsideColorEnabled,
//                        Color outsideColorDisabled,
//                        String label) {
//        super(left, top, right, bottom, insideColorEnabled, insideColorDisabled, outsideColorEnabled, outsideColorDisabled);
//        this.label = label;
//    }
//
//    public LabledButton(MusePoint2D ul, MusePoint2D br,
//                        Color insideColorEnabled,
//                        Color insideColorDisabled,
//                        Color outsideColorEnabled,
//                        Color outsideColorDisabled,
//                        String label) {
//        super(ul, br, insideColorEnabled, insideColorDisabled, outsideColorEnabled, outsideColorDisabled);
//        this.label = label;
//    }
//
//    public LabledButton(RelativeRect ref,
//                        Color insideColorEnabled,
//                        Color insideColorDisabled,
//                        Color outsideColorEnabled,
//                        Color outsideColorDisabled,
//                        String label) {
//        super(ref, insideColorEnabled, insideColorDisabled, outsideColorEnabled, outsideColorDisabled);
//        this.label = label;
//    }
//
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//        MuseRenderer.drawShadowedStringCentered(matrixStack, this.label, centerx(), centery());
//    }
//}