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

package lehjr.powersuits.client.gui.keymap;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.settings.KeyModifier;


/**
 * Fixme: buttons rendering on top of module icons and their label/text
 */
public class TinkerKeymapGui extends ContainerlessGui {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/background/keybinds.png");
    TabSelectFrame tabSelectFrame;
    Player player;
    KeymapFrame kbFrame;

    public TinkerKeymapGui(Player player, Component titleIn) {
        super(titleIn, 352, 217);
//        Minecraft.getInstance().keyboardHandler.m_90926_(true);
        this.player = player;
        this./*xSize*/imageWidth = 352 /*340 */;
        this./*ySize*/imageHeight = 217;
        KeymappingKeyHandler.loadKeyBindings();
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
//        frames.clear();
        tabSelectFrame = new TabSelectFrame(this.leftPos, this.topPos, this.imageWidth, player, 2);
        tabSelectFrame.setPosition(center());
        tabSelectFrame.setBottom(topPos);
        addFrame(tabSelectFrame);
        MusePoint2D kbTL =  new MusePoint2D(leftPos + 9, topPos + 9);
        kbFrame = new KeymapFrame( kbTL, kbTL.plus(334, 201));
        addFrame(tabSelectFrame);
        addFrame(kbFrame);
    }

    @Override
    public void renderBackground(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(gfx, mouseX, mouseY, partialTick);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, imageWidth, imageHeight, 512, 512);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) { // int pKeyCode, int pScanCode, int pModifiers
        if (kbFrame.keybindingToRemap != null) {
            if (keyCode == 256) {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputConstants.UNKNOWN);
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputConstants.UNKNOWN);
            } else {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputConstants.getKey(keyCode, scanCode));
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputConstants.getKey(keyCode, scanCode));
            }

            if (!KeyModifier.isKeyCodeModifier(kbFrame.keybindingToRemap.getKey())) {
                kbFrame.keybindingToRemap = null;
            }

//            this.lastKeySelection = Util.getMillis();
//            KeyBinding.resetMapping();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!minecraft.isWindowActive()) {
            KeyMappingReaderWriter.INSTANCE.writeOutKeybindSetings();
            Minecraft.getInstance().options.save(); // fixme
//            this.player.closeAbstractContainerMenu();
        }
    }
}