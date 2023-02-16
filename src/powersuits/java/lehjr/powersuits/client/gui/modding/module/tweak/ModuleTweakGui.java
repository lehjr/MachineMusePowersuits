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

package lehjr.powersuits.client.gui.modding.module.tweak;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.ContainerlessGui2;
import lehjr.numina.client.gui.frame.LabelBox;
import lehjr.numina.client.gui.frame.MultiRectHolderFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.math.Colour;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Requires all module and inventory slots be accounted for before constructing
 *
 *
 */
public class ModuleTweakGui extends ContainerlessGui2 {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/install_salvage.png");


    /** commonly used spacer value */
    final int spacer = 7;
    /** colours for frames used here */
    Colour backgroundColour = Colour.DARK_GREY.withAlpha(1F);
    Colour topBorderColour = new Colour(0.216F, 0.216F, 0.216F, 1F);
    Colour bottomBorderColour = Colour.WHITE.withAlpha(0.8F);

    protected ModularItemSelectionFrame itemSelectFrame;
    protected ModuleSelectionFrame moduleSelectFrame;
    protected DetailedSummaryFrame summaryFrame;
    protected ModuleTweakFrame tweakFrame;
    protected TabSelectFrame tabSelectFrame;
    protected LabelBox modularSelectionLabel;
    MultiRectHolderFrame mainHolder;

    public ModuleTweakGui(ITextComponent titleIn, boolean growFromMiddle) {
        super(titleIn, 340, 217, growFromMiddle);
        this.minecraft = Minecraft.getInstance();
        PlayerEntity player = getMinecraft().player;
        // FIXME
        tabSelectFrame = new TabSelectFrame(0, 0, 0, player, 1);
        addFrame(tabSelectFrame);

        /** Selector for the modular item that holds the modules */
        itemSelectFrame = new ModularItemSelectionFrame(new MusePoint2D(leftPos + 35, topPos));

        /** common width for left side frames */
        double leftFrameWidth = 157;

        MultiRectHolderFrame leftFrame = new MultiRectHolderFrame(false, true, 0,0);

        /** left label (takes place of spacer) */
        modularSelectionLabel = new LabelBox(leftFrameWidth, 15, new TranslationTextComponent("gui.powersuits.installed.modules"));
        leftFrame.addRect(modularSelectionLabel);

        /** frame to display and allow selecting of installed modules */
        moduleSelectFrame = new ModuleSelectionFrame(itemSelectFrame,
                new Rect(new MusePoint2D(0,0),
                new MusePoint2D(leftFrameWidth, 195)));
        leftFrame.addRect(moduleSelectFrame);
        /** bottom left spacer */
        leftFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(leftFrameWidth, spacer)));
        leftFrame.doneAdding();

        /** setup call to make the modules reload when new button pressed */
        itemSelectFrame.setOnChanged(()-> {
            moduleSelectFrame.loadModules(false);
            tweakFrame.resetScroll();
        });
        addFrame(itemSelectFrame);

        double rightFrameWidth = 162;

        MultiRectHolderFrame rightFrame = new MultiRectHolderFrame(false, true, 0,0);
        rightFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(rightFrameWidth, spacer)));

        summaryFrame = new DetailedSummaryFrame(
                new MusePoint2D(0, 0),
                new MusePoint2D(rightFrameWidth, 40),
                backgroundColour,
                topBorderColour,
                bottomBorderColour,
                itemSelectFrame);
        rightFrame.addRect(summaryFrame);
        rightFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(rightFrameWidth, 7)));

        tweakFrame = new ModuleTweakFrame(
                new MusePoint2D(0,  0),
                new MusePoint2D(rightFrameWidth, 156),
                backgroundColour,
                topBorderColour,
                bottomBorderColour,
                itemSelectFrame,
                moduleSelectFrame);
        rightFrame.addRect(tweakFrame);

        /** bottom right spacer */
        rightFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(rightFrameWidth, 7)));
        rightFrame.doneAdding();

        mainHolder = new MultiRectHolderFrame(true, true, 0, 0);
        /** left spacer */
        mainHolder.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(spacer, 217)));

        mainHolder.addRect(leftFrame);
        /** middle spacer */
        mainHolder.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(spacer, 217)));

        mainHolder.addRect(rightFrame);
        /** right spacer */
        mainHolder.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(spacer, 217)));
        mainHolder.doneAdding();

        addFrame(mainHolder);

//        backgroundRect.setOnInit(rect -> {
//            mainHolder.setPosition(rect.getPosition());
//        });



//        moduleSelectFrame.setDoOnNewSelect();
    }

    @Override
    public void init() {
        super.init();
        itemSelectFrame.setLeftOf(mainHolder); // does nothing
        itemSelectFrame.setTop(mainHolder.top()); // displaces buttons
        itemSelectFrame.setRight(mainHolder.left());  // displaces buttons
//        itemSelectFrame.initGrowth();
//        tabSelectFrame.initFromBackgroundRect(this.backgroundRect);
        moduleSelectFrame.loadModules(true);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        if (backgroundRect.doneGrowing()) {
////            if (!itemSelectFrame.playerHasModularItems()) {
////                renderBackgroundRect(matrixStack, mouseX, mouseY, partialTicks);
////                float centerx = absX(0);
////                float centery = absY(0);
////                StringUtils.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line1"), centerx, centery - 5, Colour.WHITE);
////                StringUtils.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line2"), centerx, centery + 5, Colour.WHITE);
////                tabSelectFrame.render(matrixStack, mouseX, mouseY, partialTicks);
////            } else
////            {
////                super.render(matrixStack, mouseX, mouseY, partialTicks);
////                super.renderTooltip(matrixStack, mouseX, mouseY);
////            }
//        } else {
            this.renderBackground(matrixStack);
            renderBackgroundRect(matrixStack, mouseX, mouseY, partialTicks);
//        }
    }


    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(this.BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, this.getBlitOffset(), 0, 0, imageWidth, imageHeight, 512, 512);
    }


    @Override
    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        modularSelectionLabel.renderLabel(matrixStack, 0, 1);
    }
}