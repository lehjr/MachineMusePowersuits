package com.lehjr.powersuits.client.gui.common.selection.modularitem;

import com.lehjr.numina.client.gui.IconUtils;
import com.lehjr.numina.client.render.NuminaRenderer;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;


/**
 * Based on Minecraft's RecipeBookButton
 */
public class ModularItemSelectionTab extends StateSwitchingButton {
    protected static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");

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

        // fixme!!!
        this.initTextureValues(153, 2, 35, 0, RECIPE_BOOK_LOCATION);
    }

    public void startAnimation(Minecraft pMinecraft) {
        this.animationTime = ANIMATION_TIME;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        System.out.println(equipmentSlot.name() + "clicked:? " + super.mouseClicked(pMouseX, pMouseY, pButton));

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.animationTime > 0.0F) {
            float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
            pPoseStack.pushPose();
            pPoseStack.translate((double)(this.x + 8), (double)(this.y + 12), 0.0D);
            pPoseStack.scale(1.0F, f, 1.0F);
            pPoseStack.translate((double)(-(this.x + 8)), (double)(-(this.y + 12)), 0.0D);
        }

        Minecraft minecraft = Minecraft.getInstance();
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
        this.blit(pPoseStack, k, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcon(pPoseStack, minecraft.getItemRenderer());
        if (this.animationTime > 0.0F) {
            pPoseStack.popPose();
            this.animationTime -= pPartialTick;
        }
    }

    private void renderIcon(PoseStack matrixStack, ItemRenderer pItemRenderer) {
        int i = this.isStateTriggered ? -2 : 0;
        if (!modularItem.isEmpty()) {
            pItemRenderer.renderAndDecorateFakeItem(modularItem, this.x + 9 + i, this.y + 5);
        } else {
            if (equipmentSlot.getType().equals(EquipmentSlot.MAINHAND)) {
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