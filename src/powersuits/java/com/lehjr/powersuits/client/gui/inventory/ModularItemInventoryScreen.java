package com.lehjr.powersuits.client.gui.inventory;

import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.frame.IGuiScreen;
import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.client.gui.screen.NuminaAbstractContainerScreen;
import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionFrame;
import com.lehjr.powersuits.client.gui.common.selection.module.ModuleSelectionFrame;
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

public class ModularItemInventoryScreen extends NuminaAbstractContainerScreen<ModularItemInventoryMenu, RelativeRect> implements IGuiScreen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/container/modulariteminventory.png");
    private static final ModuleSelectionFrame moduleSelectionFrame = new ModuleSelectionFrame();


    public static ModularItemSelectionFrame itemSelectionFrame;



    /** whether the module selection panel is visible */
    boolean showingSidePanel = false;
    boolean widthTooNarrow = false;



    /*
    todo:
        2 backgrounds (split window for displaying compatible modules for modular item)
        sync modular item selection to JEI search window.

     */


    public ModularItemInventoryScreen(ModularItemInventoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new RelativeRect(), pPlayerInventory, pTitle);
        this.imageHeight = 215;
        this.imageWidth = 176;
        this.inventoryLabelY = 123;
        this.titleLabelY = 4;

        // ModularItemSelectionFrame(EquipmentSlot equipmentSlot, @Nullable ModuleSelectionFrame moduleSelectionFrame, ModularItemInventoryMenu menu)
        this.itemSelectionFrame = new ModularItemSelectionFrame(getMenu().getEquipmentSlot(), moduleSelectionFrame, getMenu());
        this.itemSelectionFrame.setDoThisOnSomeEvent(doThis ->
                itemSelectionFrame.getSelectedTab().ifPresent(modularItemSelectionTab ->
                        MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(modularItemSelectionTab.getEquipmentSlot()))));






//        moduleSelectionFrame.setDoThisOnSomeEvent(doThis -> {
//            if (moduleSelectionFrame.isVisible()) {
//                itemSelectionFrame.setMeLeftOf(moduleSelectionFrame);
//                System.out.println("setMeLeftOf(moduleSelectionFrame)");
//
//            } else {
//                itemSelectionFrame.setMeLeftOf(this);
//                System.out.println("setMeLeftOf(this)");
//            }
//        });

        this.itemSelectionFrame.setDoThisOnVisibilityToggle(doThis -> {
            updatePositions();
        });







    }

    void updatePositions() {
        this.setLeft(this.moduleSelectionFrame.updateScreenPosition(getScreenWidth(), getImageWidth()));
        if (moduleSelectionFrame.isVisible()) {
            itemSelectionFrame.setUL(moduleSelectionFrame.getUL().minus(itemSelectionFrame.width(), -2 ));
        } else {
            itemSelectionFrame.setUL(this.getUL().minus(itemSelectionFrame.width(), -2 ));
        }
    }


//    @Override
//    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
//        IGuiScreen.super.renderLabels(matrixStack, mouseX, mouseY);
//    }

    @Override
    protected void init() {
//        System.out.println("width before init: " + width); // width value is writen when the GUI is opened or resized.. pretty sure it related to the window size
        super.init();
//        System.out.println("width after init: " + this.width + ", this.height: " + this.height);
        this.widthTooNarrow = this.width < 426; // 323 is total without tabs what is this number indicitive of?  ( recipebook width is 147, container screen default is 176
        this.moduleSelectionFrame.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.itemSelectionFrame);
        updatePositions();



//        moduleSelectionFrame.setTop(this.top());


//        this.addWidget(this.moduleSelectionFrame);
        this.setInitialFocus(this.moduleSelectionFrame);


//        if (moduleSelectionFrame.isVisible()) {
//            itemSelectionFrame.setPosition(new MusePoint2D(this.left() - (itemSelectionFrame.width() * .05) , this.centery()));
//
//
//
////            itemSelectionFrame
//        }




        /*

           protected void init() {
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
      this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
      this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (p_98484_) -> {
         this.recipeBookComponent.toggleVisibility();
         this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
         ((ImageButton)p_98484_).setPosition(this.leftPos + 5, this.height / 2 - 49);
      }));
      this.addWidget(this.recipeBookComponent);
      this.setInitialFocus(this.recipeBookComponent);
      this.titleLabelX = 29;
   }

         */



    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (moduleSelectionFrame.isVisible()) {
            moduleSelectionFrame.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }

    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public int getScreenWidth() {
        return width;
    }

    @Override
    public int getScreenHeight() {
        return height;
    }

    @Override
    public void setRect(IRect rect) {

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
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

        //        itemSelectionFrame.setOffset(delta);
        return true;
    }

//    @Override
//    public void update(double mouseX, double mouseY) {
//        IGuiScreen.super.update(mouseX, mouseY);
//    }

    @Override
    public IRect setLeft(double value) {
        this.leftPos = (int) value;
        return super.setLeft((int)value);
    }

    @Override
    public IRect setTop(double value) {
        this.topPos = (int) value;
        return super.setTop((int)value);
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        this.topPos = (int) ul.getY();
        this.leftPos = (int) ul.getX();
        return super.setUL(ul);
    }
}
