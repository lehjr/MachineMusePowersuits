package com.lehjr.powersuits.client.gui.common.selection.modularitem;

import com.lehjr.numina.client.gui.IconUtils;
import com.lehjr.numina.client.render.NuminaRenderer;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;


/**
 * Based on Minecraft's RecipeBookButton
 */
public class ModularItemSelectionTab extends StateSwitchingButton2 {
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/container/tabs.png");
    ItemStack modularItem;
    EquipmentSlot equipmentSlot;
    Pair<ResourceLocation, ResourceLocation> pair;
    private static final float ANIMATION_TIME = 15.0F;
    private float animationTime;

    public ModularItemSelectionTab(EquipmentSlot equipmentSlot) {
        super(0, 0, 35, 27, false);
        this.modularItem = Minecraft.getInstance().player.getItemBySlot(equipmentSlot);
        this.equipmentSlot = equipmentSlot;
        pair = NuminaRenderer.getSlotBackground(equipmentSlot);
        this.initTextureValues(0, 0, 35, 29, 76, 164, BACKGROUND_LOCATION);
        this.animationTime = 0;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public void renderButton(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
//        super.renderButton(poseStack, pMouseX, pMouseY, pPartialTick);
//
        if (this.animationTime > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin(this.animationTime / 15.0F * (float)Math.PI);
            poseStack.pushPose();
            poseStack.translate(this.x + 8, this.y + 12, 0.0D);
            poseStack.scale(1.0F, f, 1.0F);
            poseStack.translate(-(this.x + 8), -(this.y + 12), 0.0D);
        }

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

        int k = this.x;
        if (this.isStateTriggered) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(poseStack, k, this.y, i, j, this.width, this.height, this.textureWidth, this.textureHeight);

        RenderSystem.enableDepthTest();
        this.renderIcon(poseStack);
        if (this.animationTime > 0.0F) {
            poseStack.popPose();
            this.animationTime -= pPartialTick;
        }
    }

    private void renderIcon(PoseStack matrixStack) {
        int i = this.isStateTriggered ? -2 : 0;
        if (!modularItem.isEmpty()) {
            NuminaRenderer.getItemRenderer().renderAndDecorateFakeItem(modularItem, this.x + 9 + i, this.y + 5);
        } else {
            if (equipmentSlot.equals(EquipmentSlot.MAINHAND)) {
                IconUtils.getIcon().weaponSlotBackground.draw(matrixStack, this.x + 9 + i, this.y + 5, Color.WHITE);
            } else {
                if (pair != null) {
                    TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                    RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
                    blit(matrixStack, this.x + 9 + i, this.y + 5, this.getBlitOffset(), 16, 16, textureatlassprite);
                }
            }
        }
    }
}