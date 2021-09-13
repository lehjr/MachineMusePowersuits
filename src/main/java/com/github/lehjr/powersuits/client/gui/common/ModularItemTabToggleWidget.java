package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class ModularItemTabToggleWidget extends DrawableRelativeRect implements IClickable {
    IPressable onPressed;
    IReleasable onReleased;
    boolean isHovered = false;
    boolean isEnabled = true;
    boolean isVisible = true;
    boolean isStateActive = false;
    private final Colour activeColor = Colour.GREY_GUI_BACKGROUND;
    private final Colour inactiveColor = Colour.DARK_GREY.withAlpha(0.8F);
    @Nonnull
    ItemStack icon;
    EquipmentSlotType type;

    public ModularItemTabToggleWidget(EquipmentSlotType type) {
        super(0,0, 0, 27, Colour.DARK_GREY.withAlpha(0.8F), Colour.BLACK);
        this.type = type;
        ItemStack test = getMinecraft().player.getItemBySlot(type);
        this.icon = test.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> test).orElse(ItemStack.EMPTY);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        int xChange = this.isStateActive ? 2 : 0;
        double right = finalRight();
        this.setWidth(28+ xChange).setRight(right);

        this.isHovered = isVisible && isEnabled && this.containsPoint(mouseX, mouseY);
        this.setBackgroundColour(this.isStateActive ? activeColor : inactiveColor);
        super.render(matrixStack, mouseX, mouseY, frameTime);
        this.renderIcon(matrixStack);
    }

    /**
     * Renders the item icons for the tabs. Some tabs have 2 icons, some just one.
     */
    private void renderIcon(MatrixStack matrixStack) {
        int offset = this.isStateActive? -2 : -3;
        RenderSystem.disableDepthTest();
        if (this.icon.isEmpty()) {
            if (EquipmentSlotType.MAINHAND.equals(type)) {
                MuseIconUtils.drawIconAt((float)left() + (this.isStateActive ? 4 : 5), (float)top() + 7, MuseIconUtils.getIcon().weaponSlotBackground.getSprite(), Colour.WHITE);
            } else {
                Pair<ResourceLocation, ResourceLocation> pair = NuminaObjects.getSlotBackground(type);
                if (pair != null) {
                    TextureAtlasSprite textureatlassprite = getMinecraft().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                    Minecraft.getInstance().getTextureManager().bind(textureatlassprite.atlas().location());
                    getMinecraft().screen.blit(matrixStack, (int)left() + (this.isStateActive ? 5 : 6), (int)top() + 7, getMinecraft().screen.getBlitOffset(), 16, 16, textureatlassprite);
                }
            }
            RenderSystem.enableDepthTest();
        } else {
            getMinecraft().getItemRenderer().renderAndDecorateItem(icon, (int)left() + 9 + offset, (int)top() + 6);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return IClickable.super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean isHovered() {
        return this.isHovered;
    }

    public EquipmentSlotType getSlotType() {
        return this.type;
    }

    public void setStateActive(boolean active) {
        this.isStateActive = active;
    }

    public RecipeBookCategories getCategory() {
        return RecipeBookCategories.CRAFTING_SEARCH;
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void setVisible(boolean b) {
        this.isVisible = b;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onPressed() {
        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void onReleased() {
        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }
}