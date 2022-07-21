package com.lehjr.powersuits.client.gui.common.widget;

import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * TODO: 3 states ("no ingredients/disabled", "can craft", "installed"
 *
 *
 *
 *
 */
public class ClickableModuleWidget extends AbstractWidget {
    final ResourceLocation background = ModularItemSelectionTab.BACKGROUND_LOCATION;
    State state;

    ItemStack module;

    public ClickableModuleWidget(@Nonnull ItemStack module, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY,  25,25, pMessage);
        this.module = module;
        this.state = State.DISABLED;
    }

    /**
     *
     *
     * @param isDisabled is disabled by server settings?
     * @param canCraft are ingredients available?
     * @param isInstalled is already installed?
     */
    public void setState(boolean isDisabled, boolean canCraft, boolean isInstalled) {
        if (isDisabled || !canCraft) {
            this.state = State.DISABLED;
            return;
        }

        if(isInstalled) {
            this.state = State.INSTALLED;
            return;
        }

       this.state = State.CRAFTABLE;
    }

    @Override
    public void renderButton(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.background);
        RenderSystem.disableDepthTest();
        int i = state.getTextStartLeft(isHovered);
        int j = state.getTextStartTop(isHovered);

        blit(poseStack, this.x, this.y, i, j, this.width, this.height, 256, 256);
        RenderSystem.enableDepthTest();
        if (this.isHovered) {
            this.renderToolTip(poseStack, pMouseX, pMouseY);
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }
    public enum State {
        CRAFTABLE(197,222, 0, 0),
        DISABLED(197,222, 25, 25),
        INSTALLED(197,222, 50, 50);
        final int textStartLeft;
        final int textStartLeftHovered;
        final int textStartTop;
        final int textStartTopHovered;

        public int getTextStartLeft(boolean hovered) {
            return hovered? textStartLeftHovered : textStartLeft;
        }

        public int getTextStartTop(boolean hovered) {
            return hovered? textStartTopHovered : textStartTop;
        }

        private State(int left, int hoveredLeft, int top, int hoveredTop) {
            this.textStartLeft = left;
            this.textStartLeftHovered = hoveredLeft;
            this.textStartTop = top;
            this.textStartTopHovered = hoveredTop;
        }
    }
}
