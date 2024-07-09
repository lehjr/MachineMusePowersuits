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

package lehjr.powersuits.client.gui.module.select;

import com.mojang.blaze3d.platform.Window;
import lehjr.numina.client.gui.ContainerlessGui;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class GuiModeSelector extends ContainerlessGui {
    Player player;
    RadialModeSelectionFrame radialSelect;

    public GuiModeSelector(Player player, Component titleIn) {
        super(titleIn);
//        Minecraft.getInstance().keyboardHandler.m_90926_(true);
        this.player = player;
        Window screen = Minecraft.getInstance().getWindow();
        this./*xSize*/imageWidth = Math.min(screen.getGuiScaledWidth() - 50, 500);
        this./*ySize*/imageHeight = Math.min(screen.getGuiScaledHeight() - 50, 300);
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
//        frames.clear();
        radialSelect = new RadialModeSelectionFrame(
                new MusePoint2D(absX(-0.5), absY(-0.5)),
                new MusePoint2D(absX(0.5), absY(0.5)),
                player, 0);
        addFrame(radialSelect);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
//        this.renderBackground(gfx, mouseX, mouseY, partialTick);
//        this.update(mouseX, mouseY);
//        gfx.pose().pushPose();
//        gfx.pose().translate(0.0D, 0.0D, 10.0D);
//
////        this.radialSelect.render(gfx, mouseX, mouseY, partialTick);
//        gfx.pose().translate(0.0D, 0.0D, 10.0D);
//        gfx.pose().translate(0.0D, 0.0D, 100.0D);
//        this.renderLabels(gfx, mouseX, mouseY);
//        gfx.pose().popPose();
//        renderTooltip(gfx, mouseX, mouseY);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (getMinecraft().options.keyHotbarSlots[player.getInventory().selected].matches(keyCode, scanCode)) {
            this.player.closeContainer();
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!getMinecraft().isWindowActive()) {
            this.player.closeContainer();
//            super.onClose();
//            container.onAbstractContainerMenuClosed(player);
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