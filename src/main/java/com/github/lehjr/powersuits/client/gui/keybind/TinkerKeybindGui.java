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
//package com.github.lehjr.powersuits.client.gui.keybind;
//
//import com.github.lehjr.numina.util.client.gui.ContainerlessGui;
//import com.github.lehjr.powersuits.client.control.KeybindManager;
//import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.util.InputMappings;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.util.text.ITextComponent;
//import org.lwjgl.glfw.GLFW;
//
//public class TinkerKeybindGui extends ContainerlessGui {
//    protected KeybindConfigFrame kbFrame;
//    private PlayerEntity player;
//    TabSelectFrame tabSelectFrame;
//
//    public TinkerKeybindGui(PlayerInventory playerInventory, ITextComponent title) {
//        super(title);
//        KeybindManager.INSTANCE.readInKeybinds();
//        this.player = playerInventory.player;
//        this.minecraft = Minecraft.getInstance();
//        rescale();
//        kbFrame = new KeybindConfigFrame(backgroundRect, player);
//        addFrame(kbFrame);
//
//        tabSelectFrame = new TabSelectFrame(player, 1, getBlitOffset());
//        addFrame(tabSelectFrame);
//    }
//
//    public void rescale() {
//        this.setXSize((Math.min(minecraft.getWindow().getGuiScaledWidth()- 50, 500)));
//        this.setYSize((Math.min(minecraft.getWindow().getGuiScaledHeight() - 50, 300)));
//    }
//
//    /**
//     * Add the buttons (and other controls) to the screen.
//     */
//    @Override
//    public void init() {
//        super.init();
//        rescale();
//        kbFrame.init(absX(-0.95), absY(-0.95), absX(0.95), absY(0.95));
//        tabSelectFrame.init(getGuiLeft(), getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
//    }
//
//
//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int p_keyPressed_3_) {
////        System.out.println("keyCode: " + keyCode);
////        System.out.println("scanCode: " + scanCode);
////        System.out.println("p_keyPressed_3_: "+ p_keyPressed_3_);
//        InputMappings.Input mouseKey = InputMappings.getKey(keyCode, scanCode);
//        if (keyCode == GLFW.GLFW_KEY_ESCAPE || this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
//            KeybindManager.INSTANCE.writeOutKeybinds();
//            this.minecraft.player.closeContainer();
//            return true; // Forge MC-146650: Needs to return true when the key is handled.
//        }
//
//        if ( kbFrame.keyPressed(keyCode, scanCode, p_keyPressed_3_)) {
//            return true;
//        }
//
//        if (super.keyPressed(keyCode, scanCode, p_keyPressed_3_)) {
//            return true;
//        }
//        return false;
//    }
//
////    @Override
////    public void renderBackground(MatrixStack matrixStack) {
////        super.renderBackground(matrixStack);
////    }
//}