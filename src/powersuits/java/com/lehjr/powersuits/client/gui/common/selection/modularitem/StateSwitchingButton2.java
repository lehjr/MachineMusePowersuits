package com.lehjr.powersuits.client.gui.common.selection.modularitem;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class StateSwitchingButton2 extends StateSwitchingButton {
    int textureWidth = 256;
    int textureHeight = 256;

    public StateSwitchingButton2(int posX, int posY, int width, int height, boolean initialState) {
        super(posX, posY, width, height, initialState);
    }

    public StateSwitchingButton2(int posX, int posY, int width, int height, boolean initialState,
                                 int xTexStart, int yTextStart, int XDiffTex, int yDiffTex,
                                 int textureWidth, int textureHeight, ResourceLocation pResourceLocation) {
        super(posX, posY, width, height, initialState);
        this.initTextureValues(xTexStart, yTextStart, XDiffTex, yDiffTex, textureWidth, textureHeight, pResourceLocation);
    }

    public void initTextureValues(int xTexStart, int yTextStart, int XDiffTex, int yDiffTex, int textureWidth, int textureHeight, ResourceLocation pResourceLocation) {
        super.initTextureValues(xTexStart, yTextStart, XDiffTex, yDiffTex, pResourceLocation);
        this.textureHeight = textureHeight;
        this.textureWidth = textureWidth;
    }

    @Override
    public void renderButton(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (textureWidth == 256 && textureHeight == 256) {
            super.renderButton(poseStack, pMouseX, pMouseY, pPartialTick);
        } else {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.resourceLocation);
            RenderSystem.disableDepthTest();
            int i = this.xTexStart;
            int j = this.yTexStart;
            if (this.isStateTriggered) {
                i += this.xDiffTex;
            }

            if (this.isHoveredOrFocused()) {
                j += this.yDiffTex;
            }
            blit(poseStack, this.x, this.y, i, j, this.width, this.height, this.textureWidth, this.textureHeight);
            RenderSystem.enableDepthTest();
            if (this.isHovered) {
                this.renderToolTip(poseStack, pMouseX, pMouseY);
            }
        }
    }
}
