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

package com.github.lehjr.powersuits.client.gui.keybind;

import com.github.lehjr.numina.util.client.gui.ContainerlessGui;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableMuseRect;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.control.KeybindManager;
import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.glfw.GLFW;

public class TinkerKeybindGui extends ContainerlessGui {
    protected KeybindConfigFrame kbFrame;
    protected DrawableMuseRect backgroundRect;
    private PlayerEntity player;
    TabSelectFrame tabSelectFrame;

    public TinkerKeybindGui(PlayerInventory playerInventory, ITextComponent title) {
        super(title);
        KeybindManager.INSTANCE.readInKeybinds();
        this.player = playerInventory.player;
        this.minecraft = Minecraft.getInstance();
        rescale();
        backgroundRect = new DrawableMuseRect(absX(-1), absY(-1), absX(1), absY(1), true,
                Colour.DARK_GREY,
                //new Colour(0.776F, 0.776F, 0.776F, 1F),
                Colour.BLACK);
//        new Colour(0.0F, 0.2F, 0.0F, 0.8F),
//                new Colour(0.1F, 0.9F, 0.1F, 0.8F));

        kbFrame = new KeybindConfigFrame(backgroundRect, player);
        addFrame(kbFrame);

        tabSelectFrame = new TabSelectFrame(player, 1, getBlitOffset());
        addFrame(tabSelectFrame);
    }

    public void rescale() {
        this.setXSize((Math.min(minecraft.getMainWindow().getScaledWidth()- 50, 500)));
        this.setYSize((Math.min(minecraft.getMainWindow().getScaledHeight() - 50, 300)));
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        rescale();
        backgroundRect.setTargetDimensions(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
        kbFrame.init(absX(-0.95), absY(-0.95), absX(0.95), absY(0.95));
        tabSelectFrame.init(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int p_keyPressed_3_) {
//        System.out.println("keyCode: " + keyCode);
//        System.out.println("scanCode: " + scanCode);
//        System.out.println("p_keyPressed_3_: "+ p_keyPressed_3_);
        InputMappings.Input mouseKey = InputMappings.getInputByCode(keyCode, scanCode);
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)) {
            KeybindManager.INSTANCE.writeOutKeybinds();
            this.minecraft.player.closeScreen();
            return true; // Forge MC-146650: Needs to return true when the key is handled.
        }

        if ( kbFrame.keyPressed(keyCode, scanCode, p_keyPressed_3_)) {
            return true;
        }

        if (super.keyPressed(keyCode, scanCode, p_keyPressed_3_)) {
            return true;
        }
        return false;
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        this.backgroundRect.draw(matrixStack, getBlitOffset());
    }
}