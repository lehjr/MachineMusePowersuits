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
//package com.github.lehjr.powersuits.client.gui.modding.module;
//
//import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.client.render.MuseRenderer;
//import com.github.lehjr.numina.util.math.Colour;
//import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
//import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
//import com.github.lehjr.powersuits.container.TinkerTableContainer;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//
///**
// * Requires all module and inventory slots be accounted for before constructing
// *
// *
// */
//public class TinkerTableGui extends ExtendedContainerScreen<TinkerTableContainer> {
//    final int spacer = 7;
//
//    TinkerTableContainer container;
//    protected ItemSelectionFrame itemSelectFrame;
//    protected ModuleSelectionFrame moduleSelectFrame;
//    protected DetailedSummaryFrame summaryFrame;
//    protected InstallSalvageFrame installFrame;
//    protected ModuleTweakFrame tweakFrame;
//    protected TabSelectFrame tabSelectFrame;
//
//    public TinkerTableGui(TinkerTableContainer containerIn, PlayerInventory playerInventory, ITextComponent titleIn) {
//        super(containerIn, playerInventory, titleIn);
//        this.minecraft = Minecraft.getInstance();
//        PlayerEntity player = getMinecraft().player;
//        this.container = containerIn;
//        rescale();
//        itemSelectFrame = new ItemSelectionFrame(
//                container,
//                new MusePoint2D(absX(-0.95), absY(-0.95)),
//                new MusePoint2D(absX(-0.78), absY(0.95)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F), player);
//        addFrame(itemSelectFrame);
//
//        moduleSelectFrame = new ModuleSelectionFrame(itemSelectFrame,
//                new MusePoint2D(absX(-0.75F), absY(-0.95f)),
//                new MusePoint2D(absX(-0.05F), absY(0.75f)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F));
//        addFrame(moduleSelectFrame);
//
//        itemSelectFrame.setDoOnNewSelect(doThis -> moduleSelectFrame.loadModules(false));
//
//        /** GLErrors */
//        summaryFrame = new DetailedSummaryFrame(player,
//                new MusePoint2D(absX(0), absY(-0.9)),
//                new MusePoint2D(absX(0.95), absY(-0.3)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F),
//                itemSelectFrame);
//        addFrame(summaryFrame);
//
//        installFrame = new InstallSalvageFrame(
//                containerIn,
//                player,
//                new MusePoint2D(absX(-0.75),
//                        absY(0.6)),
//                new MusePoint2D(absX(-0.05),
//                        absY(0.95f)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F),
//                itemSelectFrame,
//                moduleSelectFrame);
//        addFrame(installFrame);
//
//        tweakFrame = new ModuleTweakFrame(
//                new MusePoint2D(absX(0), absY(-0.25)),
//                new MusePoint2D(absX(0.95), absY(0.95)),
//                getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F),
//                itemSelectFrame,
//                moduleSelectFrame);
//        addFrame(tweakFrame);
//
//        tabSelectFrame = new TabSelectFrame(player, 0, this.getBlitOffset());
//        addFrame(tabSelectFrame);
//    }
//
//    MusePoint2D getUlOffset() {
//        return new MusePoint2D(leftPos + 8, topPos + 8);
//    }
//
//    public void rescale() {
//        this.setXSize((Math.min(minecraft.getWindow().getGuiScaledWidth() - 50, 500)));
//        this.setYSize((Math.min(minecraft.getWindow().getGuiScaledHeight() - 50, 300)));
//    }
//
//    @Override
//    public void init() {
//        rescale();
//        itemSelectFrame.init(
//                backgroundRect.finalLeft() + spacer,
//                backgroundRect.finalTop() + spacer,
//                backgroundRect.finalLeft() + spacer + 36,
//                backgroundRect.finalBottom() - spacer);
//
//        moduleSelectFrame.init(
//                backgroundRect.finalLeft() + spacer + 36 + spacer, //  border plus itemselection frame plus spacer,
//                backgroundRect.finalTop() + spacer, // border top plus spacer
//                backgroundRect.finalLeft() + spacer + 200, // adjust as needed
//                backgroundRect.finalBottom() - spacer - 18 - spacer - 3 * 18 - spacer);
//        moduleSelectFrame.loadModules(true);
//
//        summaryFrame.init(
//                backgroundRect.finalLeft() + spacer + 200 + spacer,
//                backgroundRect.finalTop() + spacer,
//                backgroundRect.finalRight() - spacer,
//                backgroundRect.finalTop() + spacer + 80);
//
//        tweakFrame.init(
//                backgroundRect.finalLeft() + spacer + 200 + spacer,
//                backgroundRect.finalTop() + spacer + 80 + spacer,
//                backgroundRect.finalRight() - spacer,
//                backgroundRect.finalBottom() - spacer);
//
//        installFrame.setUlShift(getUlOffset());
//        installFrame.init(
//                backgroundRect.finalLeft() + spacer + 36 + spacer, // border plus spacer + 9 slots wide
//                backgroundRect.finalBottom() - spacer - 18 - spacer - 3 * 18,
//                backgroundRect.finalLeft() + spacer + 200, // adjust as needed
//                backgroundRect.finalBottom() - spacer);
//
//        tabSelectFrame.init(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
//    }
//
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        this.renderBackground(matrixStack);
//        if (backgroundRect.width() == backgroundRect.finalWidth() && backgroundRect.height() == backgroundRect.finalHeight()) {
//            if (container.getModularItemToSlotMap().isEmpty()) {
//                float centerx = absX(0);
//                float centery = absY(0);
//                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line1"), centerx, centery - 5, Colour.WHITE);
//                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line2"), centerx, centery + 5, Colour.WHITE);
//                tabSelectFrame.render(matrixStack, mouseX, mouseY, partialTicks);
//            } else {
//                super.render(matrixStack, mouseX, mouseY, partialTicks);
//                drawToolTip(matrixStack, mouseX, mouseY);
//            }
//        }
//    }
//
//    @Override
//    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
//
//    }
//}