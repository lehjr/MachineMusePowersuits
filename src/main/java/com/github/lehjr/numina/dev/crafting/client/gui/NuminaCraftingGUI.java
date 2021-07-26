package com.github.lehjr.numina.dev.crafting.client.gui;

import com.github.lehjr.numina.client.gui.dev.TabSelectFrame;
import com.github.lehjr.numina.dev.crafting.container.NuminaCraftingContainer;
import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.frame.PlayerInventoryFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Notes....
 * The basic unchanged GUI works almost as it should
 *  -> output for valid slots is NOT working. Recipe shows with no output
 *
 *
 *
 */
@OnlyIn(Dist.CLIENT)
public class NuminaCraftingGUI extends ExtendedContainerScreen<NuminaCraftingContainer> implements IRecipeShownListener {
    /** the recipe book */
    private final NuminaRecipeBookGui recipeBookComponent = new NuminaRecipeBookGui();
    /** determins if the recipe book gui will be over the crafting gui */
    private boolean widthTooNarrow;

    protected PlayerInventoryFrame playerInventoryFrame;
    protected CraftingFrame2 craftingFrame;
    protected TabSelectFrame tabSelectFrame;
    int spacer = 7;

    public NuminaCraftingGUI(NuminaCraftingContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title, 340, 200);
        tabSelectFrame = new TabSelectFrame(playerInventory.player, 1, 0);
        addFrame(tabSelectFrame);

        craftingFrame = new CraftingFrame2(container, 0, 1, ulGetter());
        addFrame(craftingFrame);

        playerInventoryFrame = new PlayerInventoryFrame(container, 10, 37, ulGetter());
        addFrame(playerInventoryFrame);


    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        /** do not call anything recipe book related before this */
        this.recipeBookComponent.init(this.width, this.height, this.getMinecraft(), this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.widthTooNarrow, this.width, this.imageWidth);
        this.children.add(this.recipeBookComponent);
        this.setInitialFocus(this.recipeBookComponent);








        playerInventoryFrame.setRight(backgroundRect.finalRight());
        playerInventoryFrame.setBottom(backgroundRect.finalBottom());
        playerInventoryFrame.initGrowth();
        craftingFrame.setLeft( backgroundRect.finalLeft() + spacer);
        craftingFrame.setBottom( backgroundRect.finalBottom() - spacer);
        craftingFrame.initGrowth();

        // FIXME: location?
        this.inventoryLabelX = (int)playerInventoryFrame.finalLeft();
        this.inventoryLabelY = (int)playerInventoryFrame.finalTop();

        /*

            try 738 x 442 and center

            space between arrow and left and right = 7 each
            space between right side of outer frame and result is 31

            vanilla total inventory height = 96

            inventory spacing:
            top = 13
            middle = 4
            bottom = 7


space between crafting grid and result = 108/3 = 36








            vanilla scaled rect 528 x 428
            mine 528 x 498

            scale 3 pixes on screenshot = 1

Center: x: 240.0, y: 136.0
Left: 152.0
Right: 328.0
Bottom: 219.0
Top: 53.0
Width: 176.0
Height: 166.0
Background Colour: Colour{r=0.776, g=0.776, b=0.776, a=1.0}
Background Colour 2: null
Border Colour: Colour{r=0.0, g=0.0, b=0.0, a=1.0}




         */


        System.out.println("fixme");


        craftingFrame.setArrowOnPressed(press-> {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            menu.quickMoveStack(this.getMinecraft().player, menu.getResultSlotIndex());
        });
//
//        tabSelectFrame.init((recipeBookComponent.isVisible() ?recipeBookComponent.getGuiLeft() : leftPos),
//                getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());

//
//        recipeBookButton.setOnPressed((button)-> {
//            /** this is everything the button does when pressed */
//            this.recipeBookComponent.initVisuals(this.widthTooNarrow);
//            if(!this.recipeBookComponent.isVisible()) {
//                this.recipeBookComponent.toggleVisibility();
//            }
//            double oldLeft = leftPos;
//            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.widthTooNarrow, this.width, this.imageWidth);
//            guiElementsMoveLeft(leftPos - oldLeft);
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//        });
        this.titleLabelX = 29;
    }

    @Override
    public void tick() {
        super.tick();
        this.recipeBookComponent.tick();
    }

    @Override
    public void update(double x, double y) {
        super.update(x, y);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        update(mouseX, mouseY);
        this.renderBackground(matrixStack);
//        backgroundRect.render(matrixStack, mouseX, mouseY, frameTime); // FIXME!!
        if (backgroundRect.doneGrowing()) {
//            arrow.render(matrixStack, mouseX, mouseY, frameTime, getBlitOffset());
            if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
                this.renderBg(matrixStack, frameTime, mouseX, mouseY);
                this.recipeBookComponent.render(matrixStack, mouseX, mouseY, frameTime);
            } else {
                this.recipeBookComponent.render(matrixStack, mouseX, mouseY, frameTime);
                super.render(matrixStack, mouseX, mouseY, frameTime);
                this.recipeBookComponent.renderGhostRecipe(matrixStack, this.leftPos, this.topPos, true, frameTime);
            }
            this.renderTooltip(matrixStack, mouseX, mouseY);
            this.recipeBookComponent.renderTooltip(matrixStack, this.leftPos, this.topPos, mouseX, mouseY);
//            this.setListenerDefault(this.recipeBookComponent); // what did this do?
            tabSelectFrame.render(matrixStack, mouseX, mouseY, frameTime);
            drawToolTip(matrixStack, mouseX, mouseY);

//            renderLabels(matrixStack, mouseX, mouseY);
        } else {
            renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        matrixStack.pushPose();
        matrixStack.translate(0,0, 10);
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
        matrixStack.popPose();
    }

    @Override
    protected boolean isHovering(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, mouseButton)) {
            this.setFocused(this.recipeBookComponent);
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int scaledWidth, int scaledHeight, int mouseButton) {
        boolean flag = mouseX < (double)scaledWidth || mouseY < (double)scaledHeight || mouseX >= (double)(scaledWidth + this.imageWidth) || mouseY >= (double)(scaledHeight + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    @Override
    protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slotIn, slotId, mouseButton, type);
        this.recipeBookComponent.slotClicked(slotIn);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public void removed() {
        this.recipeBookComponent.removed();
        super.removed();
    }

    @Override
    public RecipeBookGui getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}