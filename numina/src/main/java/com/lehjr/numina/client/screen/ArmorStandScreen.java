package com.lehjr.numina.client.screen;

import com.lehjr.numina.client.gui.ExtendedContainerScreen;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.container.ArmorStandMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandScreen extends ExtendedContainerScreen<ArmorStandMenu> {
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "textures/gui/container/armorstand.png");

    public ArmorStandScreen(ArmorStandMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }


    @Override
    public void renderTooltip(GuiGraphics gfx, int mouseX, int mouseY) {
        super.renderTooltip(gfx, mouseX, mouseY);
        super.superRenderTooltip(gfx, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;

        gfx.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.minecraft != null && this.minecraft.player != null) {
            InventoryScreen
                    .renderEntityInInventoryFollowsMouse(
                            gfx,
                            i + 25, // x start
                            j + 7,// + (int)(scroll), // y start
                            i + 25 + 49, // x end
                            j + 7 + 70,// + (int)(scroll), // y end
                            30, // scale
                            0.0625F, // y offset

                            (i + 25 + mouseX - 150),
                            (float) (j + 7 - 60 + mouseY),
                            this.minecraft.player);
        }

        InventoryScreen
                .renderEntityInInventoryFollowsMouse(
                        gfx,
                        i + 103,// + (int)(scroll),
                        j + 7,// + (int)(scroll),
                        i + 103 + 49,// + (int)(scroll),
                        j + 7 + 70,// + (int)(scroll),
                        30,
                        0.0625F, // y offset
                        (float) (i + 103 + mouseX - 257),
                        (float) (j + 7 + mouseY - 60),
                        this.menu.getArmorStand());
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        renderBackground(gfx, mouseX, mouseY, partialTick);
        super.render(gfx, mouseX, mouseY, partialTick);
        this.renderTooltip(gfx, mouseX, mouseY);
    }

    @Override
    public void renderLabels(GuiGraphics gfx, int pMouseX, int pMouseY) {
    }
}