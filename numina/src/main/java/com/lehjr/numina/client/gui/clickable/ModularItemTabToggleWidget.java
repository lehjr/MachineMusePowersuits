package com.lehjr.numina.client.gui.clickable;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.IconUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * A left hand toggleable tab widget based on Minecraft's "RecipeBookTabButton"
 */
public class ModularItemTabToggleWidget extends Clickable {
    static final ResourceLocation TAB = ResourceLocation.withDefaultNamespace( "recipe_book/tab");
    static final ResourceLocation SELECTED_TAB = ResourceLocation.withDefaultNamespace("recipe_book/tab_selected");
    protected boolean isStateActive;
    private static final float ANIMATION_TIME = 15.0F;
    private float animationTime;

    ItemStack icon;
    EquipmentSlot type;

    public ModularItemTabToggleWidget(EquipmentSlot type) {
        super(0, 0, 35, 27, false);
        this.isStateActive = false;
        this.type = type;
        Player player = getMinecraft().player;
        assert player != null;
        ItemStack test = ItemUtils.getItemFromEntitySlot(player, type);
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(test);
        icon = iModularItem != null? test : ItemStack.EMPTY;
    }

    float scale = 1F;
    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (this.containsPoint(mouseX, mouseY)) {
            this.animationTime = 15.0F;
        }

        if (this.animationTime > 0.0F) {
            scale = 1.0F + 0.1F * (float)Math.sin(this.animationTime / 15.0F * (float) Math.PI);
            gfx.pose().pushPose();
            gfx.pose().translate((float)(this.left() + 8), (float)(this.top() + 12), 0.0F);
            gfx.pose().scale(1.0F, scale, 1.0F);
            gfx.pose().translate((float)(-(this.left() + 8)), (float)(-(this.top() + 12)), 0.0F);
        }

        RenderSystem.disableDepthTest();
        int i = (int) this.left();
        if (this.isStateActive) {
            i -= 2;
        }

        gfx.blitSprite(isStateActive? SELECTED_TAB: TAB, i, (int)this.top(), (int)this.width(), (int)this.height());
        RenderSystem.enableDepthTest();
        this.renderIcon(gfx);
        if (this.animationTime > 0.0F) {
            gfx.pose().popPose();
            this.animationTime -= partialTick;
        }
    }

    public void setStateActive(boolean active) {
        this.isStateActive = active;
    }

    public EquipmentSlot getSlotType() {
        return type;
    }

    /**
     * Renders the item icons for the tabs. Some tabs have 2 icons, some just one.
     */
    private void renderIcon(GuiGraphics gfx) {
        int offset = -2;
        RenderSystem.disableDepthTest();
        if (this.icon.isEmpty()) {
            Pair<ResourceLocation, ResourceLocation> pair = IconUtils.getSlotBackground(type);
            TextureAtlasSprite textureatlassprite = getMinecraft().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
            RenderSystem.setShaderTexture(0, textureatlassprite.atlasLocation());
            gfx.blit((int)left() + 10 + offset, (int)top() + 5, 0, 16, 16, textureatlassprite);

            RenderSystem.enableDepthTest();
        } else {
            gfx.renderItem(icon, (int)left() + 9 + offset, (int)top() + 6);
            gfx.renderItemDecorations(Minecraft.getInstance().font, icon, (int)left() + 9 + offset, (int)top() + 6);
        }
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        assert Minecraft.getInstance().player != null;
        return ItemUtils.getItemFromEntitySlot(Minecraft.getInstance().player, type).getTooltipLines(Item.TooltipContext.of(Minecraft.getInstance().level),
            Minecraft.getInstance().player, Screen.hasShiftDown() ?
                TooltipFlag.Default.ADVANCED :
                TooltipFlag.Default.NORMAL);
    }
}
