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

package com.lehjr.numina.client.screen;

import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.client.gui.screen.NuminaAbstractContainerScreen;
import com.lehjr.numina.common.string.StringUtils;
import com.lehjr.numina.common.menu.ChargingBaseMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ChargingBaseScreen extends NuminaAbstractContainerScreen<ChargingBaseMenu, RelativeRect> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/container/chargingbase.png");

    public ChargingBaseScreen(ChargingBaseMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new RelativeRect(), pPlayerInventory, pTitle);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
//        energyMeter.draw(matrixStack, (float) batterySlot.centerx() - 16,
//                (float) (batterySlot.finalBottom() + spacer * 0.25),
//                menu.getEnergyForMeter(),
//                getBlitOffset() + 2);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

//        playerInventoryFrame.renderLabels(matrixStack, mouseX, mouseY);
        this.font.draw(matrixStack,  new TranslatableComponent("numina.energy").append(": "), 32F, 50F, 4210752);

        String energyString = new StringBuilder()
                .append(StringUtils.formatNumberShort(menu.getEnergy()))
                .append("/")
                .append(StringUtils.formatNumberShort(menu.getMaxEnergy()))
                .append(" FE").toString();

        this.font.draw(matrixStack,
                new TextComponent(energyString),
                (float)((getMenu().slots.get(0).x - font.width(energyString) / 2) - leftPos), // guiLeft here is important
                60F, 4210752);
    }
}