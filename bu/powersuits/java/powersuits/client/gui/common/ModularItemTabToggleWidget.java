package lehjr.powersuits.client.gui.common;

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import lehjr.numina.basemod.NuminaObjects;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.client.gui.clickable.IClickable;
import lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import lehjr.numina.util.client.render.MuseIconUtils;
import lehjr.numina.util.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.inventory.EquipmentSlot;
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
    private final Color activeColor = Color.GREY_GUI_BACKGROUND;
    private final Color inactiveColor = Color.DARK_GREY.withAlpha(0.8F);
    @Nonnull
    ItemStack icon;
    EquipmentSlot type;

    public ModularItemTabToggleWidget(EquipmentSlot type) {
        super(0,0, 0, 27, Color.DARK_GREY.withAlpha(0.8F), Color.BLACK);
        this.type = type;
        ItemStack test = getMinecraft().player.getItemBySlot(type);
        this.icon = test.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> test).orElse(ItemStack.EMPTY);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
        int xChange = this.isStateActive ? 2 : 0;
        double right = finalRight();
        this.setWidth(28+ xChange).setRight(right);

        this.isHovered = isVisible && isEnabled && this.containsPoint(mouseX, mouseY);
        this.setBackgroundColor(this.isStateActive ? activeColor : inactiveColor);
        super.render(matrixStack, mouseX, mouseY, frameTime);
        this.renderIcon(matrixStack);
    }

    /**
     * Renders the item icons for the tabs. Some tabs have 2 icons, some just one.
     */
    private void renderIcon(PoseStack matrixStack) {
        int offset = this.isStateActive? -2 : -3;
        RenderSystem.disableDepthTest();
        if (this.icon.isEmpty()) {
            if (EquipmentSlot.MAINHAND.equals(type)) {
                MuseIconUtils.drawIconAt((int)left() + 9 + offset, (float)top() + 7, MuseIconUtils.getIcon().weaponSlotBackground.getSprite(), Color.WHITE);
            } else {
                Pair<ResourceLocation, ResourceLocation> pair = NuminaObjects.getSlotBackground(type);
                if (pair != null) {
                    TextureAtlasSprite textureatlassprite = getMinecraft().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                    Minecraft.getInstance().getTextureManager().bind(textureatlassprite.atlas().location());
                    getMinecraft().screen.blit(matrixStack, (int)left() + 9 + offset, (int)top() + 7, getMinecraft().screen.getBlitOffset(), 16, 16, textureatlassprite);
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

    public EquipmentSlot getSlotType() {
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