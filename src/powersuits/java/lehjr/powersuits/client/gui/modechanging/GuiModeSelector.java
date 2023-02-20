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

package lehjr.powersuits.client.gui.modechanging;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class GuiModeSelector extends ContainerlessGui {
    PlayerEntity player;
    RadialModeSelectionFrame radialSelect;

    public GuiModeSelector(PlayerEntity player, ITextComponent titleIn) {
        super(titleIn);
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);
        this.player = player;
        MainWindow screen = Minecraft.getInstance().getWindow();
        this./*xSize*/imageWidth = Math.min(screen.getGuiScaledWidth() - 50, 500);
        this./*ySize*/imageHeight = Math.min(screen.getGuiScaledHeight() - 50, 300);
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        frames.clear();
        radialSelect = new RadialModeSelectionFrame(
                new MusePoint2D(absX(-0.5), absY(-0.5)),
                new MusePoint2D(absX(0.5), absY(0.5)),
                player, this.getBlitOffset());
        addFrame(radialSelect);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        this.renderBackground(matrixStack);
        this.update((double)mouseX, (double)mouseY);
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 0.0D, 10.0D);
        this.renderFrames(matrixStack, mouseX, mouseY, frameTime);
        matrixStack.translate(0.0D, 0.0D, 10.0D);
        matrixStack.translate(0.0D, 0.0D, 100.0D);
        this.renderLabels(matrixStack, mouseX, mouseY);
        matrixStack.popPose();
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyHotbarSlots[player.inventory.selected].matches(keyCode, scanCode)) {
            this.player.closeContainer();
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!minecraft.isWindowActive()) {
            this.player.closeContainer();
//            super.onClose();
//            container.onContainerClosed(player);
        }
    }


//
//    @Override
//    public void update() {
//        super.update();
//        if (!Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindsHotbar[player.inventory.currentItem].getKeyCode())) {
//            //close animation
//            //TODO
//            //close Gui
//            try {
//                keyTyped('1', 1);
//            } catch (IOException e) {
//            }
//        }
//    }
}