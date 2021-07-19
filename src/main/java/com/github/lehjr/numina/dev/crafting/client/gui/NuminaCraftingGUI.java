package com.github.lehjr.numina.dev.crafting.client.gui;

import com.github.lehjr.numina.client.gui.dev.TabSelectFrame;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.dev.crafting.container.NuminaCraftingContainer;
import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.clickable.TexturedButton;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
    /** The outer gui rectangle */
    protected DrawableRelativeRect backgroundRect;

    protected final Colour gridColour = new Colour(0.1F, 0.3F, 0.4F, 0.7F);
    protected final Colour gridBorderColour = Colour.LIGHT_BLUE.withAlpha(0.8F);
    protected final Colour gridBackGound = new Colour(0.545F, 0.545F, 0.545F, 1);
    protected InventoryFrame mainInventory, hotbar;
    protected CraftingFrame craftingFrame;
    protected TexturedButton recipeBookButton;
    protected TabSelectFrame tabSelectFrame;
    final int slotWidth = 18;
    final int slotHeight = 18;
    int spacer = 7;

    public NuminaCraftingGUI(NuminaCraftingContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);

        tabSelectFrame = new TabSelectFrame(playerInventory.player, 1, 0);
        addFrame(tabSelectFrame);

        backgroundRect = new DrawableRelativeRect(0, 0, 0, 0, true,
                Colour.GREY_GUI_BACKGROUND,
                Colour.BLACK);

        craftingFrame = new CraftingFrame(container, 0, 1);
        addFrame(craftingFrame);


        // slot 10-36
        mainInventory = new InventoryFrame(container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                0,
                gridBackGound, gridBorderColour, gridColour,

                9, 3, new ArrayList<Integer>(){{
            IntStream.range(10, 37).forEach(i-> add(i));
        }});
        addFrame(mainInventory);


        hotbar = new InventoryFrame(container,
                new MusePoint2D(0,0), new MusePoint2D(0, 0),
                0,
                gridBackGound, gridBorderColour, gridColour,
                9, 1, new ArrayList<Integer>(){{
            IntStream.range(37, 46).forEach(i-> add(i));
        }});
        addFrame(hotbar);

        recipeBookButton = new TexturedButton(
                0, 0, 0, 0,
                gridBackGound,
                gridBackGound,
                gridBorderColour,
                gridBorderColour,
//                true,
                16,
                16,
                new ResourceLocation(NuminaConstants.TEXTURE_PREFIX + "gui/recipe_book_button.png"));
        recipeBookButton.setEnabled(true);
        recipeBookButton.setVisible(true);
        recipeBookButton.setTextureStartX(-8.0);
        recipeBookButton.setTextureStartY(-8.0);
    }

    /**
     * Seems kinda stupid... and may not be needed. Slot coordinates aren't their center,
     * they're the top left corner
     */
    MusePoint2D getUlOffset () {
        return new MusePoint2D(leftPos + 8, topPos + 8);
    }

    /**
     * Only called when the recipe book is opened or closed
     * Once the recipebook becomes part of the gui this won't be needed anymore
     * @param moveAmmount
     */
    private void guiElementsMoveLeft(double moveAmmount) {
        backgroundRect.setLeft(backgroundRect.left() + moveAmmount);

        System.out.println("fixme");
//        hotbar.init(
//                backgroundRect.finalLeft() + spacer,
//                backgroundRect.finalBottom() - spacer - slotHeight,
//                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
//                backgroundRect.finalBottom() - spacer);
//
//        mainInventory.setUlShift(getUlOffset());
//        mainInventory.init(
//                backgroundRect.finalLeft() + spacer,
//                backgroundRect.finalBottom() - spacer - slotHeight - spacer - 3 * slotHeight,
//                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
//                backgroundRect.finalBottom() - spacer - slotHeight - spacer);
//
//        craftingFrame.setUlShift(getUlOffset());
//        craftingFrame.setUL(new MusePoint2D(backgroundRect.finalLeft() + 29, backgroundRect.finalTop() + 16));
//
//        recipeBookButton.setLeft(recipeBookButton.left() + moveAmmount);
//        tabSelectFrame.init((recipeBookComponent.isVisible() ? recipeBookComponent.getGuiLeft() : leftPos),
//                getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
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

        backgroundRect.setLeft(getGuiLeft());
        backgroundRect.setTop(getGuiTop());
        backgroundRect.setWidth(getXSize());
        backgroundRect.setHeight(getYSize());
        backgroundRect.initGrowth();
        System.out.println("fixme");
//        hotbar.init(
//                backgroundRect.finalLeft() + spacer,
//                backgroundRect.finalBottom() - spacer - slotHeight,
//                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
//                backgroundRect.finalBottom() - spacer);
//
//        mainInventory.init(
//                backgroundRect.finalLeft() + spacer,
//                backgroundRect.finalBottom() - spacer - slotHeight - spacer - 3 * slotHeight,
//                backgroundRect.finalLeft() + spacer + 9 * slotWidth,
//                backgroundRect.finalBottom() - spacer - slotHeight - spacer);
//
//        craftingFrame.setUL(new MusePoint2D(backgroundRect.finalLeft() + 29, backgroundRect.finalTop() + 16));
//        craftingFrame.setArrowOnPressed(press-> {
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            menu.quickMoveStack(this.getMinecraft().player, menu.getResultSlotIndex());
//        });
//
//        tabSelectFrame.init((recipeBookComponent.isVisible() ?recipeBookComponent.getGuiLeft() : leftPos),
//                getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
//
//        recipeBookButton.setLeft(getGuiLeft() + 5);
//        recipeBookButton.setTop(this.height / 2 - 49);
//        recipeBookButton.setWidth(18);
//        recipeBookButton.setHeight(20);
//
//        recipeBookButton.setOnPressed((button)-> {
//            /** this is everything the button does when pressed */
//            this.recipeBookComponent.initVisuals(this.widthTooNarrow);
//            this.recipeBookComponent.toggleVisibility();
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
            recipeBookButton.render(matrixStack, mouseX, mouseY, frameTime);
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
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    @Override
    public void renderBg/*drawGuiContainerBackgroundLayer*/(MatrixStack matrixStack, float frameTime, int mouseX, int mouseY) {
        super.renderBg(matrixStack, frameTime, mouseX, mouseY);
    }

//
//    @Override
//    public void renderBackground(MatrixStack matrixStack) {
//        super.renderBackground(matrixStack);
//    }

    @Override
    protected boolean isHovering(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
//        if (this.tabSelectFrame.mouseClicked(mouseX, mouseY, mouseButton)) {
//            return true;
//        }
        if (this.recipeBookButton.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

//        if (this.arrow.mouseClicked(mouseX, mouseY, mouseButton)) {
//            return true;
//        }

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