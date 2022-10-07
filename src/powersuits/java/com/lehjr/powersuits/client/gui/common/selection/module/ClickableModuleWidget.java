package com.lehjr.powersuits.client.gui.common.selection.module;

import com.lehjr.numina.client.gui.GuiIcon;
import com.lehjr.numina.client.gui.clickable.ClickableModule;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.render.NuminaRenderer;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * TODO: 3 states ("no ingredients/disabled", "can craft", "installed"
 *
 *
 *
 *
 */
public class ClickableModuleWidget extends ClickableModule {
    final ResourceLocation background = ModularItemSelectionTab.BACKGROUND_LOCATION;
    State state;

    ItemStack module;

    public ClickableModuleWidget(@Nonnull ItemStack module, MusePoint2D pos, int index, ModuleCategory category) {
        super(module, pos, index, category);
        super.setWH(25,25);
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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        boolean isHovered = hitBox(mouseX, mouseY);
        int x = (int) this.getPosition().getX();
        int y = (int) this.getPosition().getY();



//

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.background);
        RenderSystem.disableDepthTest();
        int i = state.getTextStartLeft(isHovered);
        int j = state.getTextStartTop(isHovered);

        GuiComponent.blit(matrixStack, x, y, i, j, (int)this.width(), (int)this.height(), 256, 256);
        RenderSystem.enableDepthTest();

//        NuminaRenderer.getItemRenderer().renderAndDecorateFakeItem(module, (int) (this.getPosition().getX() + 9 + i), (int) (this.getPosition().getY() + 5));

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (isHovered) {
            System.out.println("render Clickable module widget tooltip");

//            this.renderToolTip(matrixStack, pMouseX, pMouseY);
        }
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
