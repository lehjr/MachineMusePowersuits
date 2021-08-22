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
//package com.github.lehjr.powersuits.client.gui.modding.cosmetic;
//
//import com.github.lehjr.numina.util.client.gui.ContainerlessGui;
//import com.github.lehjr.numina.util.client.gui.frame.EntityRenderFrame;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.math.Colour;
//import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
//import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
//import com.github.lehjr.powersuits.config.MPSSettings;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.server.management.OpEntry;
//import net.minecraft.util.text.ITextComponent;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 6:32 PM, 29/04/13
// * <p>
// * Ported to Java by lehjr on 10/19/16.
// */
//public class CosmeticGui extends ContainerlessGui {
//    PlayerEntity player;
//    final int spacer = 7;
//
//    ItemSelectionFrame itemSelectFrame;
//    EntityRenderFrame renderframe;
//    ColourPickerFrame colourpicker;
//    PartManipContainer partframe;
//
//
//    TabSelectFrame tabSelectFrame;
//
////    LoadSaveResetSubFrame loadSaveResetSubFrame;
//
//    protected final boolean allowCosmeticPresetCreation;
//    protected final boolean usingCosmeticPresets;
//
//    public CosmeticGui(PlayerInventory inventory, ITextComponent title) {
//        super(title);
//        this.player = inventory.player;
//        this.minecraft = Minecraft.getInstance();
//
//        usingCosmeticPresets = !MPSSettings.useLegacyCosmeticSystem();
//        if (usingCosmeticPresets) {
//            // check if player is the server owner
//            if (minecraft.hasSingleplayerServer()) {
//                allowCosmeticPresetCreation = player.getName().equals(minecraft.getSingleplayerServer().getSingleplayerName());
//            } else {
//                // check if player is top level op
//                OpEntry opEntry = minecraft.player.getServer().getPlayerList().getOps().get(player.getGameProfile());
//                int opLevel = opEntry != null ? opEntry.getLevel() : 0;
//                allowCosmeticPresetCreation = opLevel == 4;
//            }
//        } else {
//            allowCosmeticPresetCreation = false;
//        }
//
//        itemSelectFrame = new ItemSelectionFrame(
//                null,
//                new MusePoint2D(absX(-0.95), absY(-0.95)),
//                new MusePoint2D(absX(-0.78), absY(-0.025)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F),
//                player);
//        addFrame(itemSelectFrame);
//
//        renderframe = new EntityRenderFrame(false);
//        renderframe.setWidth(48);
//        renderframe.setHeight(80);
//
////                new EntityRenderFrame(
////                itemSelectFrame,
////                new MusePoint2D(absX(-0.75), absY(-0.95)),
////                new MusePoint2D(absX(0.15), absY(-0.025)),
////                this.getBlitOffset(),
////                Colour.DARKBLUE.withAlpha(0.8F),
////                Colour.LIGHT_BLUE.withAlpha(0.8F));
//        addFrame(renderframe);
//
//        colourpicker = new ColourPickerFrame(
//                new MusePoint2D(absX(0.18),
//                        absY(-0.95)),
//
//                new MusePoint2D(absX(0.95),
//                        absY(-0.27)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F),
//                itemSelectFrame);
//        addFrame(colourpicker);
//
//        partframe = new PartManipContainer(
//                itemSelectFrame, colourpicker,
//                new MusePoint2D(absX(-0.95F), absY(0.025f)),
//                new MusePoint2D(absX(+0.95F), absY(0.95f)),
//                this.getBlitOffset(),
//                Colour.DARKBLUE.withAlpha(0.8F),
//                Colour.LIGHT_BLUE.withAlpha(0.8F));
//        addFrame(partframe);
//
//        tabSelectFrame = new TabSelectFrame(player, 2, this.getBlitOffset());
//        addFrame(tabSelectFrame);
//
//        itemSelectFrame.setDoOnNewSelect(doThis -> partframe.getModelframes());
//    }
//
//    public void rescale() {
//        this.setXSize((Math.min(minecraft.getWindow().getGuiScaledWidth()- 50, 500)));
//        this.setYSize((Math.min(minecraft.getWindow().getGuiScaledHeight() - 50, 300)));
//    }
//
//
//    /**
//     * Add the buttons (and other controls) to the screen.
//     */
//    @Override
//    public void init() {
//        super.init();
//        rescale();
//
//        itemSelectFrame.init(
//                backgroundRect.finalLeft()  + spacer,
//                backgroundRect.finalTop() + spacer,
//                backgroundRect.finalLeft() + spacer + 36,
//                backgroundRect.centery() - spacer
//        );
//
//        renderframe.init(
//                backgroundRect.finalLeft() + spacer + 36 + spacer,
//                backgroundRect.finalTop() + spacer,
//                backgroundRect.finalRight() - spacer - 150 -spacer,
//                backgroundRect.centery() - spacer
//        );
//
//        colourpicker.init(
//                backgroundRect.finalRight() - spacer - 150,
//                backgroundRect.finalTop() + spacer,
//                backgroundRect.finalRight() - spacer,
//                backgroundRect.centery() - spacer
//        );
//
//        partframe.init(
//                backgroundRect.finalLeft()  + spacer,
//                backgroundRect.centery(),
//                backgroundRect.finalRight() - spacer,
//                backgroundRect.finalBottom() - spacer
//
//        );
//
//
////        CosmeticPresetContainer cosmeticFrame = new CosmeticPresetContainer(
////                itemSelect, colourpicker,
////                new Point2F(absX(-0.95F), absY(0.025f)),
////                new Point2F(absX(+0.95F), absY(0.95f)),
////                Colour.LIGHTBLUE.withAlpha(0.8F),
////                Colour.DARKBLUE.withAlpha(0.8F));
////        frames.add(cosmeticFrame);
////
////        // if not using presets then only the reset button is displayed
////        loadSaveResetSubFrame = new LoadSaveResetSubFrame(
////                colourpicker,
////                player,
////                new Rect(
////                        absX(0.18f),
////                        absY(-0.23f),
////                        absX(0.95f),
////                        absY(-0.025f)),
////                Colour.LIGHTBLUE.withAlpha(0.8F),
////                Colour.DARKBLUE.withAlpha(0.8F),
////                itemSelect,
////                usingCosmeticPresets,
////                allowCosmeticPresetCreation,
////                partframe,
////                cosmeticFrame);
////        frames.add(loadSaveResetSubFrame);
////
//
//
//        tabSelectFrame.init(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
//    }
//
//
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        if (backgroundRect.width() == backgroundRect.finalWidth() && backgroundRect.height() == backgroundRect.finalHeight()) {
//            super.render(matrixStack, mouseX, mouseY, partialTicks);
//            drawToolTip(matrixStack, mouseX, mouseY);
//        } else {
//            this.renderBackground(matrixStack);
//        }
//    }
//
//    @Override
//    public void renderBackground(MatrixStack matrixStack) {
//        super.renderBackground(matrixStack);
//    }
//
//    @Override
//    public void removed() {
//        super.removed();
////        loadSaveResetSubFrame.onClose();
//    }
//    // FIXME!!! I have no idea what this return value should be based on... 0 documentation
////    @Override
////    public boolean charTyped(char typedChar, int keyCode) {
////        boolean ret = super.charTyped(typedChar, keyCode);
////        if (loadSaveResetSubFrame != null)
////            loadSaveResetSubFrame.charTyped(typedChar, keyCode);
////        return ret;
////    }
//}