package com.lehjr.powersuits.client.gui.inventory;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionFrame;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.menu.ModularItemInventoryMenu;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ModularItemInventoryScreen extends AbstractContainerScreen<ModularItemInventoryMenu> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/container/modulariteminventory.png");
    public static ModularItemSelectionFrame itemSelectionFrame;



    /** whether or not the second panel is visible */
    boolean showingSidePanel = false;


    /*
    todo:
        2 backgrounds (split window for displaying compatible modules for modular item)
        sync modular item selection to JEI search window.

     */


    public ModularItemInventoryScreen(ModularItemInventoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 215;
        this.inventoryLabelY = 123;
        this.titleLabelY = 4;
        this.itemSelectionFrame = new ModularItemSelectionFrame(getMenu().getEquipmentSlot());




//        this.height * 0.5;

//        System.out.println("frame location here (before): " + itemSelectionFrame.getUL());


        // fixme: move somewhere else, maybe in init. Also move down a bit

//        itemSelectionFrame.setP.setTop(this.topPos + 4);

//        System.out.println("frame location here (after): " + itemSelectionFrame.getUL());
    }


    @Override
    protected void init() {
        super.init();
        itemSelectionFrame.setUL(new MusePoint2D(this.leftPos - itemSelectionFrame.width(), this.topPos + 4));
        this.itemSelectionFrame.setDoThisOnChange(doThis ->
                itemSelectionFrame.getSelectedTab().ifPresent(modularItemSelectionTab ->
                        MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(modularItemSelectionTab.getEquipmentSlot()))));

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(itemSelectionFrame.mouseClicked(pMouseX, pMouseY, pButton)) {
            return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    int xoffset = 0;

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int limit = menu.getModularItemInventorySize();
        outerLoop:
        for (int index = 0; index < limit; index ++) {
            for (int row = 0; row < limit; row++) {  // limit here is almost an arbitrary number
                for (int col = 0; col < 9; col++) {
                    if (index >= limit) {
                        break outerLoop;
                    }
                    Slot slot = this.menu.getSlot(index);
                    this.blit(pPoseStack, slot.x + i -1, slot.y + j -1, 7 /*left offset */, 190 /* y offset */, 18, 18);

                    index++;
                }
            }
        }

        itemSelectionFrame.render(pPoseStack, pMouseX, pMouseY, pPartialTick);



        // leftOffset = 7
        // topOffset = 190
        // slot width == 18



        // todo: boxes for the modular item slots
    }

//    @Override
//    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
//        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
//        this.font.draw(pPoseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
//    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        xoffset += (int) pDelta;
        System.out.println("xoffset: " + xoffset);

        itemSelectionFrame.setRight(this.leftPos + xoffset);

        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }
}
