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

package lehjr.numina.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ArmorStandMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandScreen extends AbstractContainerScreen<ArmorStandMenu> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/container/armorstand.png");
    /** The old x position of the mouse pointer */
    /** The old x position of the mouse pointer */
    private float xMouse;
    /** The old y position of the mouse pointer */
    private float yMouse;

    public ArmorStandScreen(ArmorStandMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen
                .renderEntityInInventoryFollowsMouse(
                gfx,
                i + 51, // x
                j + 75, // y
                30, // scale
                (float)(i + 51) - this.xMouse,
                (float)(j + 75 - 50) - this.yMouse,
                this.minecraft.player);
        InventoryScreen
                .renderEntityInInventoryFollowsMouse(
                        gfx,
                        i + 126,
                        j + 75,
                        30,
                        (float)(i + 126) - this.xMouse,
                        (float)(j + 75 - 50) - this.yMouse,
                        this.menu.getArmorStand());
    }

    @Override
    public void render(GuiGraphics gfx, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(gfx);
        super.render(gfx, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(gfx, pMouseX, pMouseY);
        this.xMouse = (float)pMouseX;
        this.yMouse = (float)pMouseY;
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int pMouseX, int pMouseY) {
    }
}