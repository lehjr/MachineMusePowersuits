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
import lehjr.numina.util.client.gui.ContainerlessGui;
import lehjr.numina.util.client.gui.frame.GUISpacer;
import lehjr.numina.util.client.gui.frame.LabelBox;
import lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Colour;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Requires all module and inventory slots be accounted for before constructing
 *
 *
 */
public class ModuleTweakGui extends ContainerlessGui {
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
        tabSelectFrame = new TabSelectFrame(player, 1);
        addFrame(tabSelectFrame);

        /** Selector for the modular item that holds the modules */
        itemSelectFrame = new ModularItemSelectionFrame();

        /** common width for left side frames */
        double leftFrameWidth = 157;

        MultiRectHolderFrame leftFrame = new MultiRectHolderFrame(false, true, 0,0);

        /** left label (takes place of spacer) */
        modularSelectionLabel = new LabelBox(leftFrameWidth, 15, new TranslationTextComponent("gui.powersuits.installed.modules"));
        leftFrame.addRect(modularSelectionLabel);

        /** frame to display and allow selecting of installed modules */
        moduleSelectFrame = new ModuleSelectionFrame(itemSelectFrame,
                new MusePoint2D(0,0),
                new MusePoint2D(leftFrameWidth, 195),
                backgroundColour,
                topBorderColour,
                bottomBorderColour);
        leftFrame.addRect(moduleSelectFrame);
        /** bottom left spacer */
        leftFrame.addRect(new GUISpacer(leftFrameWidth, spacer));
        leftFrame.doneAdding();

        /** setup call to make the modules reload when new button pressed */
        itemSelectFrame.setOnChanged(()-> {
            moduleSelectFrame.loadModules(false);
            tweakFrame.resetScroll();
        });
        addFrame(itemSelectFrame);

        double rightFrameWidth = 162;

        MultiRectHolderFrame rightFrame = new MultiRectHolderFrame(false, true, 0,0);
        rightFrame.addRect(new GUISpacer(rightFrameWidth, spacer));

        summaryFrame = new DetailedSummaryFrame(
                new MusePoint2D(0, 0),
                new MusePoint2D(rightFrameWidth, 40),
                backgroundColour,
                topBorderColour,
                bottomBorderColour,
                itemSelectFrame);
        rightFrame.addRect(summaryFrame);
        rightFrame.addRect(new GUISpacer(rightFrameWidth, 7));

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
        rightFrame.addRect(new GUISpacer(rightFrameWidth, 7));
        rightFrame.doneAdding();

        mainHolder = new MultiRectHolderFrame(true, true, 0, 0);
        /** left spacer */
        mainHolder.addRect(new GUISpacer(spacer, 217));

        mainHolder.addRect(leftFrame);
        /** middle spacer */
        mainHolder.addRect(new GUISpacer(spacer, 217));

        mainHolder.addRect(rightFrame);
        /** right spacer */
        mainHolder.addRect(new GUISpacer(spacer, 217));
        mainHolder.doneAdding();

        addFrame(mainHolder);

        backgroundRect.setOnInit(rect -> {
            mainHolder.setPosition(rect.getPosition());
        });



//        moduleSelectFrame.setDoOnNewSelect();
    }

    @Override
    public void init() {
        super.init();
        itemSelectFrame.setMeLeftOf(mainHolder); // does nothing
        itemSelectFrame.setTop(mainHolder.finalTop()); // displaces buttons
        itemSelectFrame.setRight(mainHolder.finalLeft());  // displaces buttons
        itemSelectFrame.initGrowth();
        tabSelectFrame.initFromBackgroundRect(this.backgroundRect);
        moduleSelectFrame.loadModules(true);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (backgroundRect.doneGrowing()) {
            if (!itemSelectFrame.playerHasModularItems()) {
                renderBackgroundRect(matrixStack, mouseX, mouseY, partialTicks);
                float centerx = absX(0);
                float centery = absY(0);
                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line1"), centerx, centery - 5, Colour.WHITE);
                MuseRenderer.drawCenteredText(matrixStack, new TranslationTextComponent("gui.powersuits.noModulesFound.line2"), centerx, centery + 5, Colour.WHITE);
                tabSelectFrame.render(matrixStack, mouseX, mouseY, partialTicks);
            } else {
                super.render(matrixStack, mouseX, mouseY, partialTicks);
                super.renderTooltip(matrixStack, mouseX, mouseY);
            }
        } else {
            this.renderBackground(matrixStack);
            renderBackgroundRect(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        modularSelectionLabel.renderLabel(matrixStack, 0, 1);
    }
}