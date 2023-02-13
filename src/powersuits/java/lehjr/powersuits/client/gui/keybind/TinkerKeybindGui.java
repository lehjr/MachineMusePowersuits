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

package lehjr.powersuits.client.gui.keybind;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.ContainerlessGui2;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.settings.KeyModifier;


/**
 * Fixme: buttons rendering on top of module icons and their label/text
 */
public class TinkerKeybindGui extends ContainerlessGui {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/keybinds.png");
    TabSelectFrame tabSelectFrame;
    PlayerEntity player;
    KeyBindFrame kbFrame;

    public TinkerKeybindGui(PlayerEntity player, ITextComponent titleIn) {
        super(titleIn, 352, 217);
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);
        this.player = player;
        this./*xSize*/imageWidth = 352 /*340 */;
        this./*ySize*/imageHeight = 217;
    }

//    double scrollVal = 0;
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
//
//        scrollVal += dWheel;
//
//
//
//        return super.mouseScrolled(mouseX, mouseY, dWheel);
//    }


    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        frames.clear();
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, player, 2);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);
        MusePoint2D kbTL =  new MusePoint2D(leftPos + 9, topPos + 9);
        kbFrame = new KeyBindFrame( kbTL, kbTL.plus(334, 201));
        addFrame(tabSelectFrame);
        addFrame(kbFrame);
        System.out.println("kbFrame: " + kbFrame.getBackgroundRect());
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
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (kbFrame.keybindingToRemap != null) {
            if (p_231046_1_ == 256) {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputMappings.UNKNOWN);
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputMappings.UNKNOWN);
            } else {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputMappings.getKey(p_231046_1_, p_231046_2_));
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputMappings.getKey(p_231046_1_, p_231046_2_));
            }

            if (!KeyModifier.isKeyCodeModifier(kbFrame.keybindingToRemap.getKey())) {
                kbFrame.keybindingToRemap = null;
            }

//            this.lastKeySelection = Util.getMillis();
//            KeyBinding.resetMapping();
            return true;
        } else {
            return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!minecraft.isWindowActive()) {
            KeybindManager.INSTANCE.writeOutKeybindSetings();
            this.player.closeContainer();

//            super.onClose();
//            container.onContainerClosed(player);
        }
    }
}