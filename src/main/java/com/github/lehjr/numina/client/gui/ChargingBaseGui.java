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

package com.github.lehjr.numina.client.gui;

import com.github.lehjr.numina.container.ChargingBaseContainer;
import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.meters.EnergyMeter;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.string.MuseStringUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ChargingBaseGui extends ExtendedContainerScreen<ChargingBaseContainer> {
    /** The outer green rectangle */
    protected DrawableRelativeRect backgroundRect;
    protected InventoryFrame batterySlot, items, hotbar;
    final int slotWidth = 18;
    final int slotHeight = 18;
    int spacer = 7;
    EnergyMeter energyMeter;

    /**
        FIXME:
        battery disapears when putting into battery slot if only battery has energy
        ^ different behaviour when holding another battery in hand to swap
        meter location needs dynamic value
        basic battery energy bar disappears... needs z level adjustment..   

     */
    public ChargingBaseGui(ChargingBaseContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        backgroundRect = new DrawableRelativeRect(0, 0, 0, 0, true,
                Colour.GREY_GUI_BACKGROUND,
//                Colour.LIGHT_GREY,
                Colour.BLACK);
        energyMeter = new EnergyMeter();

        // slot 0
        batterySlot = new InventoryFrame(container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset(),
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.BLACK,
                1, 1, new ArrayList<Integer>(){{
            IntStream.range(0, 1).forEach(i-> add(i));
        }});
        addFrame(batterySlot);

        // slot 1-9
        items = new InventoryFrame(container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset(),
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 3, new ArrayList<Integer>(){{
            IntStream.range(1, 28).forEach(i-> add(i));
        }});
        addFrame(items);

        // slot 28-37
        hotbar = new InventoryFrame(container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                getBlitOffset(),
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 1, new ArrayList<Integer>(){{
            IntStream.range(28, 37).forEach(i-> add(i));
        }});
        addFrame(hotbar);
        // add energy meter
    }

    MusePoint2D getUlOffset () {
        return new MusePoint2D(leftPos + 8, topPos + 8);
    }
    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        backgroundRect.setTargetDimensions(new MusePoint2D(getGuiLeft(), getGuiTop()), new MusePoint2D(getXSize(), getYSize()));

        batterySlot.setUlShift(getUlOffset());
        batterySlot.init(
                backgroundRect.centerx() - (slotWidth * 0.5),
                backgroundRect.finalTop() + 30,
                backgroundRect.centerx() + (slotWidth * 0.5),
                backgroundRect.finalTop() + 30 + slotHeight);
        batterySlot.setzLevel(1);

        hotbar.setUlShift(getUlOffset());
        hotbar.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalBottom() - spacer - slotHeight,
                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
                backgroundRect.finalBottom() - spacer);
        hotbar.setzLevel(1);

        items.setUlShift(getUlOffset());
        items.init(
                backgroundRect.finalLeft() + spacer,
                backgroundRect.finalBottom() - spacer - slotHeight - 3.25F - 3 * slotHeight,
                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
                backgroundRect.finalBottom() - spacer - slotHeight - 3.25F);
        items.setzLevel(1);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        if (backgroundRect.width() == backgroundRect.finalWidth() && backgroundRect.height() == backgroundRect.finalHeight()) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        } else {
            backgroundRect.draw(matrixStack, getBlitOffset());
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        this.font.draw(matrixStack,  new TranslationTextComponent("numina.energy").append(": "), 32F, 50F, 4210752);

        String energyString = new StringBuilder()
                .append(MuseStringUtils.formatNumberShort(menu.getEnergy()))
                .append("/")
                .append(MuseStringUtils.formatNumberShort(menu.getMaxEnergy()))
                .append(" FE").toString();

        this.font.draw(matrixStack,
                new StringTextComponent(energyString),
                (float)((batterySlot.centerx() - font.width(energyString) / 2) - leftPos), // guiLeft here is important
                60F, 4210752);
    }

    @Override
    public void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        backgroundRect.draw(matrixStack, 0);
        super.renderBg(matrixStack, partialTicks, x, y);
        energyMeter.draw(matrixStack, (float) batterySlot.centerx() - 16,
                (float) (batterySlot.finalBottom() + spacer * 0.25),
                menu.getEnergyForMeter(),
                getBlitOffset() + 2);
        this.drawToolTip(matrixStack, x, y);
    }
}