package com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage;

import com.github.lehjr.numina.util.client.gui.ExtendedContainerScreen;
import com.github.lehjr.numina.util.client.gui.frame.*;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrameContainered;
import com.github.lehjr.powersuits.client.gui.common.TabSelectFrame;
import com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest.ScrollableInventoryFrame2;
import com.github.lehjr.powersuits.container.MPSCraftingContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/** TODO: add creative install ability to crafting arrow */
/**
 * Notes....
 * The basic unchanged GUI works almost as it should
 *  -> output for valid slots is NOT working. Recipe shows with no output
 *
 *
 *
 */
@OnlyIn(Dist.CLIENT)
public class CraftInstallSalvageGui extends ExtendedContainerScreen<MPSCraftingContainer> implements IRecipeShownListener {
    /** the recipe book. IRecipeShownListener means it HAS to be an instance of the vanilla recipe book -_- */
    private final MPSRecipeBookGui recipeBookComponent;
    /** determins if the recipe book gui will be over the crafting gui */
    private boolean widthTooNarrow;

    protected PlayerInventoryFrame playerInventoryFrame;
    RectHolderFrame craftingHolder;
    protected TabSelectFrame tabSelectFrame;
    int spacer = 7;
    ModularItemSelectionFrameContainered modularItemSelectionFrame;

    ScrollableInventoryFrame2 modularItemInventory;

    MultiRectHolderFrame mainHolder;

    public CraftInstallSalvageGui(MPSCraftingContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title, 340, 217, false);

        /** clickable buttons on the top of the GUI */
        tabSelectFrame = new TabSelectFrame(playerInventory.player, 0);
        addFrame(tabSelectFrame);

        /** player inventory */
        playerInventoryFrame = new PlayerInventoryFrame(container, 10, 37, ulGetter());

        /** crafting grid, arrow, and result slot */
        CraftingFrame craftingFrame = new CraftingFrame(container, 0, 1, ulGetter());
        craftingFrame.setArrowOnPressed(press-> {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            if (getMinecraft().player.isCreative()) {
                ItemStack module = ItemStack.EMPTY;
                if (getRecipeBookComponent() != null) {
                    module = ((MPSRecipeBookGui)getRecipeBookComponent()).getRecipeOutput();
                }
                System.out.println("result: " + module);

                if (modularItemInventory != null && !module.isEmpty()) {
                    modularItemInventory.creativeInstall(module);
                }
            } else {
                menu.quickMoveStack(this.getMinecraft().player, menu.getResultSlotIndex());
            }
        });

        /** holder for the crafting grid, acts as a spacer for the sides */
        craftingHolder = new RectHolderFrame(craftingFrame, 164.0, craftingFrame.finalHeight() + 14) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return craftingFrame.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return craftingFrame.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public List<ITextComponent> getToolTip(int mouseX, int mouseY) {
                return craftingFrame.getToolTip(mouseX, mouseY);
            }

            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                super.render(matrixStack, mouseX, mouseY, frameTime);
                craftingFrame.render(matrixStack, mouseX, mouseY, frameTime);
            }
        };

        /** the buttons that select the equipped modular item if any */
        modularItemSelectionFrame = new ModularItemSelectionFrameContainered(container);
        addFrame(modularItemSelectionFrame);

        /** the recipeBook GUI */
        recipeBookComponent = new MPSRecipeBookGui(modularItemSelectionFrame, container);

        RectHolderFrame recipeHolder = new RectHolderFrame(recipeBookComponent, 157, 136) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
                    setFocused(recipeBookComponent);
                    return true;
                }
                return false;
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return recipeBookComponent.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public List<ITextComponent> getToolTip(int x, int y) {
                return null;
            }
        };

        /** holder for recipebook and a left side spacer */
        MultiRectHolderFrame recipeBookHolder = new MultiRectHolderFrame(true, true, 0, 0);
        recipeBookHolder.addRect(new GUISpacer(7, 119));
        recipeBookHolder.addRect(recipeHolder);
        recipeBookHolder.doneAdding();

        /** inventory frame for the modular item inventory. Changes with different selected modular items */
        modularItemInventory = new ScrollableInventoryFrame2(container, modularItemSelectionFrame, this.ulGetter());


        /** a box to group these 2 inventory frames together */
        MultiRectHolderFrame rightHolder = new MultiRectHolderFrame(false, true, 0,0);
        rightHolder.addRect(modularItemInventory);
        rightHolder.addRect(playerInventoryFrame);
        rightHolder.doneAdding();

        /** a box to group these crafting related 2 frames together */
        MultiRectHolderFrame leftHolder = new MultiRectHolderFrame(false, true, 0, 0);
        leftHolder.addRect(new GUISpacer(craftingHolder.finalWidth(), 13));
        leftHolder.addRect(recipeBookHolder);
        leftHolder.addRect(craftingHolder);
        leftHolder.doneAdding();

        mainHolder = new MultiRectHolderFrame(true, true, 0, 0);
        mainHolder.addRect(leftHolder);
        mainHolder.addRect(rightHolder);
        mainHolder.doneAdding();
        addFrame(mainHolder);
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        /** do not call anything recipe book related before this */
        this.recipeBookComponent.init();
        this.children.add(this.recipeBookComponent);
        this.setInitialFocus(this.recipeBookComponent);

        modularItemSelectionFrame.setMeLeftOf(mainHolder);
        modularItemSelectionFrame.setTop(mainHolder.finalTop());
        modularItemSelectionFrame.initGrowth();

        this.titleLabelX = 29;

        mainHolder.setPosition(backgroundRect.getPosition());
        mainHolder.initGrowth();

        tabSelectFrame.init(leftPos, getGuiTop(), getGuiLeft() + getXSize(), getGuiTop() + getYSize());
    }

    public void tick() {
        super.tick();
        this.recipeBookComponent.tick();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBookComponent);
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (super.mouseScrolled(mouseX, mouseY, dWheel)) {
            return true;
        }

        return this.recipeBookComponent.mouseScrolled(mouseX, mouseY, dWheel);
    }

    /**
     * Overridden to fix inventory frames rendering behind background
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param frameTime
     */
    @Override
    public void renderBackgroundRect(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        matrixStack.pushPose();
        matrixStack.translate(0,0, -1);
        super.renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        matrixStack.popPose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        modularItemSelectionFrame.setTop(mainHolder.finalTop());
        modularItemSelectionFrame.setRight(mainHolder.finalLeft());

        this.renderBackground(matrixStack);
        if (backgroundRect.doneGrowing()) {
            super.render(matrixStack, mouseX, mouseY, frameTime);
            this.recipeBookComponent.renderGhostRecipe(matrixStack, this.leftPos, this.topPos, true, frameTime);
            this.renderTooltip(matrixStack, mouseX, mouseY);
            this.recipeBookComponent.renderTooltip(matrixStack, this.leftPos, this.topPos, mouseX, mouseY);
            tabSelectFrame.render(matrixStack, mouseX, mouseY, frameTime);
            drawToolTip(matrixStack, mouseX, mouseY);
        } else {
            renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
        }
    }

    @Override
    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        matrixStack.pushPose();
        matrixStack.translate(0,0, 10);
        this.modularItemInventory.renderLabels(matrixStack, mouseX, mouseY);
        this.playerInventoryFrame.renderLabels(matrixStack, mouseX, mouseY);
        matrixStack.popPose();
    }

    /**
     * Vanilla behaviour does not include the tabs, but does include everything contained within the 2 backgrounds
     * @param mouseX
     * @param mouseY
     * @param scaledWidth
     * @param scaledHeight
     * @param mouseButton
     * @return
     */
    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int scaledWidth, int scaledHeight, int mouseButton) {
        boolean flag = mouseX < (double)scaledWidth || mouseY < (double)scaledHeight || mouseX >= (double)(scaledWidth + this.imageWidth) || mouseY >= (double)(scaledHeight + this.imageHeight);
        boolean clickedOutside = this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
        return clickedOutside;
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    @Override
    public void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
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