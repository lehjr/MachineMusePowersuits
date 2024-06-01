package lehjr.numina.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.ExtendedContainerScreen;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ArmorStandMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArmorStandScreen extends ExtendedContainerScreen<ArmorStandMenu> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/container/armorstand.png");

    public ArmorStandScreen(ArmorStandMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    double scroll = 0;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        scroll += scrollY;
        NuminaLogger.logDebug("scroll: " + scroll + ", total: " + ((this.leftPos + 103 + mouseX -240 + scroll)));
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);

    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight);
        //         GuiGraphics pGuiGraphics,
        //        int pX1,
        //        int pY1,
        //        int pX2,
        //        int pY2,
        //        int pScale,
        //        float pYOffset,
        //        float pMouseX,
        //        float pMouseY,
        //        LivingEntity pEntity
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
                        (float) (i + 103 + mouseX - 240 + scroll),
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