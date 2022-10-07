package com.lehjr.powersuits.client.gui.common.selection.module;

import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.client.gui.geometry.RelativeRect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.powersuits.client.gui.common.selection.modularitem.ModularItemSelectionFrame;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleSelectionFrame extends GuiComponent implements Widget, GuiEventListener, IGuiFrame /*, NarratableEntry, RecipeShownListener, PlaceRecipe<Ingredient> */ {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(MPSConstants.MOD_ID, "textures/gui/container/moduleselection.png");

    private int xOffset = 0;
    private int width; // total width available to whole GUI
    private int height; // total height available to whole GUI

    protected Minecraft minecraft = null;
    public static final int IMAGE_WIDTH = 194; // 147
    public static final int IMAGE_HEIGHT = 215;
    IRect rect = new RelativeRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

    private static final int OFFSET_X_POSITION = 85;// not the best way to do this really

    private boolean visible = false;
    private boolean enabled = true;
    private boolean widthTooNarrow = true;

    ModularItemSelectionFrame itemSelectionFrame;

    public void init(int width, int height, Minecraft minecraft, boolean widthTooNarrow, ModularItemSelectionFrame itemSelectionFrame) {
        this.minecraft = minecraft;
        this.width = width;
        this.height = height;
        this.widthTooNarrow = widthTooNarrow;
        this.itemSelectionFrame = itemSelectionFrame;
        this.visible = itemSelectionFrame.shouldShowModuleSelectionFrame();
        if (this.visible) {
            this.initVisuals();
        }
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    public List<ClickableModuleWidget> createModuleList() {
        List<ClickableModuleWidget> modules = new ArrayList();


        Optional<IModularItem> modularItem = itemSelectionFrame.getModularItem();


        List<RecipeCollection> recipes = getMinecraft().player.getRecipeBook().getCollections();








    }



    public void initVisuals() {
        this.xOffset = this.widthTooNarrow ? 0 : OFFSET_X_POSITION;
        int left = (int) ((this.width - IMAGE_WIDTH) * 0.5 - this.xOffset);
        int top = (int) ((this.height - IMAGE_HEIGHT) * 0.5);
        this.setUL(left, top);
        System.out.println("widthTooNarrow: " + widthTooNarrow);






//        this.stackedContents.clear();
//        this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
//        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
//        String s = this.searchBox != null ? this.searchBox.getValue() : "";
//        this.searchBox = new EditBox(this.minecraft.font, top + 25, left + 14, 80, 9 + 5, new TranslatableComponent("itemGroup.search"));
//        this.searchBox.setMaxLength(50);
//        this.searchBox.setBordered(false);
//        this.searchBox.setVisible(true);
//        this.searchBox.setTextColor(16777215);
//        this.searchBox.setValue(s);
//        this.recipeBookPage.init(this.minecraft, top, left);
//        this.recipeBookPage.addListener(this);
//        this.filterButton = new StateSwitchingButton(top + 110, left + 12, 26, 16, this.book.isFiltering(this.menu));
//        this.initFilterButtonTextures();
//        this.tabButtons.clear();
//
//        for(RecipeBookCategories recipebookcategories : this.menu.getRecipeBookCategories()) {
//            this.tabButtons.add(new RecipeBookTabButton(recipebookcategories));
//        }
//
//        if (this.selectedTab != null) {
//            this.selectedTab = this.tabButtons.stream().filter((p_100329_) -> {
//                return p_100329_.getCategory().equals(this.selectedTab.getCategory());
//            }).findFirst().orElse((RecipeBookTabButton)null);
//        }
//
//        if (this.selectedTab == null) {
//            this.selectedTab = this.tabButtons.get(0);
//        }
//
//        this.selectedTab.setStateTriggered(true);
//        this.updateCollections(false);
//        this.updateTabs();
    }


    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.isVisible()) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.0D, 0.0D, 100.0D);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, BACKGROUND);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (int) ((this.width - IMAGE_WIDTH) * 0.5 - this.xOffset);
            int j = (int) ((this.height - IMAGE_HEIGHT) * 0.5);
            this.blit(pPoseStack, i, j, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
//            if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
//                drawString(pPoseStack, this.minecraft.font, SEARCH_HINT, i + 25, j + 14, -1);
//            } else {
//                this.searchBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            }

//            for(RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
//                recipebooktabbutton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            }
//
//            this.filterButton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            this.recipeBookPage.render(pPoseStack, i, j, pMouseX, pMouseY, pPartialTick);
            pPoseStack.popPose();
        }
    }

//    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
//        if (this.isVisible()) {
//            pPoseStack.pushPose();
//            pPoseStack.translate(0.0D, 0.0D, 100.0D);
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            int i = (this.width - IMAGE_WIDTH) / 2 - this.xOffset;
//            int j = (this.height - IMAGE_HEIGHT) / 2;
//            this.blit(pPoseStack, i, j, 1, 1, IMAGE_WIDTH, IMAGE_HEIGHT);
//            if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
//                drawString(pPoseStack, this.minecraft.font, SEARCH_HINT, i + 25, j + 14, -1);
//            } else {
//                this.searchBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            }
//
//            for(RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
//                recipebooktabbutton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            }
//
//            this.filterButton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
//            this.recipeBookPage.render(pPoseStack, i, j, pMouseX, pMouseY, pPartialTick);
//            pPoseStack.popPose();
//        }
//    }































































    public boolean changeFocus(boolean pFocus) {
        return false;
    }

    protected void initFilterButtonTextures() {
//        this.filterButton.initTextureValues(152, 41, 28, 18, RECIPE_BOOK_LOCATION);
    }

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }



    ClientRecipeBook getRecipeBookCategories() {
        return minecraft.player.getRecipeBook();
    }



    private boolean isVisibleAccordingToBookData() {
        return false; //this.book.isOpen(this.menu.getRecipeBookType());
    }



    public void slotClicked(@Nullable Slot pSlot) {
//        if (pSlot != null && pSlot.index < this.menu.getSize()) {
//            this.ghostRecipe.clear();
//            if (this.isVisible()) {
//                this.updateStackedContents();
//            }
//        }

    }

    private void updateCollections(boolean p_100383_) {
//        List<RecipeCollection> list = this.book.getCollection(this.selectedTab.getCategory());
//        list.forEach((p_100381_) -> {
//            p_100381_.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), this.book);
//        });
//        List<RecipeCollection> list1 = Lists.newArrayList(list);
//        list1.removeIf((p_100368_) -> {
//            return !p_100368_.hasKnownRecipes();
//        });
//        list1.removeIf((p_100360_) -> {
//            return !p_100360_.hasFitting();
//        });
//        String s = this.searchBox.getValue();
//        if (!s.isEmpty()) {
//            ObjectSet<RecipeCollection> objectset = new ObjectLinkedOpenHashSet<>(this.minecraft.getSearchTree(SearchRegistry.RECIPE_COLLECTIONS).search(s.toLowerCase(Locale.ROOT)));
//            list1.removeIf((p_100334_) -> {
//                return !objectset.contains(p_100334_);
//            });
//        }
//
//        if (this.book.isFiltering(this.menu)) {
//            list1.removeIf((p_100331_) -> {
//                return !p_100331_.hasCraftable();
//            });
//        }
//
//        this.recipeBookPage.updateCollections(list1, p_100383_);
    }

    private void updateTabs() {
//        int i = (this.width - IMAGE_WIDTH) / 2 - this.xOffset - 30;
//        int j = (this.height - IMAGE_HEIGHT) / 2 + 3;
//        int k = 27;
//        int l = 0;
//
//        for(RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
//            RecipeBookCategories recipebookcategories = recipebooktabbutton.getCategory();
//            if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
//                if (recipebooktabbutton.updateVisibility(this.book)) {
//                    recipebooktabbutton.setPosition(i, j + 27 * l++);
//                    recipebooktabbutton.startAnimation(this.minecraft);
//                }
//            } else {
//                recipebooktabbutton.visible = true;
//                recipebooktabbutton.setPosition(i, j + 27 * l++);
//            }
//        }

    }

    public void tick() {
//        boolean flag = this.isVisibleAccordingToBookData();
//        if (this.isVisible() != flag) {
//            this.setVisible(flag);
//        }
//
//        if (this.isVisible()) {
//            if (this.timesInventoryChanged != this.minecraft.player.getInventory().getTimesChanged()) {
//                this.updateStackedContents();
//                this.timesInventoryChanged = this.minecraft.player.getInventory().getTimesChanged();
//            }
//
//            this.searchBox.tick();
//        }
    }

    private void updateStackedContents() {
//        this.stackedContents.clear();
//        this.minecraft.player.getInventory().fillStackedContents(this.stackedContents);
//        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
//        this.updateCollections(false);
    }



//    public void renderTooltip(PoseStack p_100362_, int p_100363_, int p_100364_, int p_100365_, int p_100366_) {
//        if (this.isVisible()) {
//            this.recipeBookPage.renderTooltip(p_100362_, p_100365_, p_100366_);
//            if (this.filterButton.isHoveredOrFocused()) {
//                Component component = this.getFilterButtonTooltip();
//                if (this.minecraft.screen != null) {
//                    this.minecraft.screen.renderTooltip(p_100362_, component, p_100365_, p_100366_);
//                }
//            }
//
//            this.renderGhostRecipeTooltip(p_100362_, p_100363_, p_100364_, p_100365_, p_100366_);
//        }
//    }

//    private Component getFilterButtonTooltip() {
//        return this.filterButton.isStateTriggered() ? this.getRecipeFilterName() : ALL_RECIPES_TOOLTIP;
//    }
//
//    protected Component getRecipeFilterName() {
//        return ONLY_CRAFTABLES_TOOLTIP;
//    }

//    private void renderGhostRecipeTooltip(PoseStack p_100375_, int p_100376_, int p_100377_, int p_100378_, int p_100379_) {
//        ItemStack itemstack = null;
//
//        for(int i = 0; i < this.ghostRecipe.size(); ++i) {
//            GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.get(i);
//            int j = ghostrecipe$ghostingredient.getX() + p_100376_;
//            int k = ghostrecipe$ghostingredient.getY() + p_100377_;
//            if (p_100378_ >= j && p_100379_ >= k && p_100378_ < j + 16 && p_100379_ < k + 16) {
//                itemstack = ghostrecipe$ghostingredient.getItem();
//            }
//        }
//
//        if (itemstack != null && this.minecraft.screen != null) {
//            this.minecraft.screen.renderComponentTooltip(p_100375_, this.minecraft.screen.getTooltipFromItem(itemstack), p_100378_, p_100379_, itemstack);
//        }
//
//    }

//    public void renderGhostRecipe(PoseStack p_100323_, int p_100324_, int p_100325_, boolean p_100326_, float p_100327_) {
//        this.ghostRecipe.render(p_100323_, this.minecraft, p_100324_, p_100325_, p_100326_, p_100327_);
//    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
//        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
//            if (this.recipeBookPage.mouseClicked(pMouseX, pMouseY, pButton, (this.width - IMAGE_WIDTH) / 2 - this.xOffset, (this.height - IMAGE_HEIGHT) / 2, IMAGE_WIDTH, IMAGE_HEIGHT)) {
//                Recipe<?> recipe = this.recipeBookPage.getLastClickedRecipe();
//                RecipeCollection recipecollection = this.recipeBookPage.getLastClickedRecipeCollection();
//                if (recipe != null && recipecollection != null) {
//                    if (!recipecollection.isCraftable(recipe) && this.ghostRecipe.getRecipe() == recipe) {
//                        return false;
//                    }
//
//                    this.ghostRecipe.clear();
//                    this.minecraft.gameMode.handlePlaceRecipe(this.minecraft.player.containerMenu.containerId, recipe, Screen.hasShiftDown());
//                    if (!this.isOffsetNextToMainGUI()) {
//                        this.setVisible(false);
//                    }
//                }
//
//                return true;
//            } else if (this.searchBox.mouseClicked(pMouseX, pMouseY, pButton)) {
//                return true;
//            } else if (this.filterButton.mouseClicked(pMouseX, pMouseY, pButton)) {
//                boolean flag = this.toggleFiltering();
//                this.filterButton.setStateTriggered(flag);
//                this.sendUpdateSettings();
//                this.updateCollections(false);
//                return true;
//            } else {
//                for(RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
//                    if (recipebooktabbutton.mouseClicked(pMouseX, pMouseY, pButton)) {
//                        if (this.selectedTab != recipebooktabbutton) {
//                            if (this.selectedTab != null) {
//                                this.selectedTab.setStateTriggered(false);
//                            }
//
//                            this.selectedTab = recipebooktabbutton;
//                            this.selectedTab.setStateTriggered(true);
//                            this.updateCollections(true);
//                        }
//
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//        } else {
        return false;
//        }
    }

//    private boolean toggleFiltering() {
//        RecipeBookType recipebooktype = this.menu.getRecipeBookType();
//        boolean flag = !this.book.isFiltering(recipebooktype);
//        this.book.setFiltering(recipebooktype, flag);
//        return flag;
//    }
//
//    public boolean hasClickedOutside(double p_100298_, double p_100299_, int p_100300_, int p_100301_, int p_100302_, int p_100303_, int p_100304_) {
//        if (!this.isVisible()) {
//            return true;
//        } else {
//
//            boolean flag = p_100298_ < (double)p_100300_ || p_100299_ < (double)p_100301_ || p_100298_ >= (double)(p_100300_ + p_100302_) || p_100299_ >= (double)(p_100301_ + p_100303_);
//            boolean flag1 = (double)(p_100300_ - IMAGE_WIDTH) < p_100298_ && p_100298_ < (double)p_100300_ && (double)p_100301_ < p_100299_ && p_100299_ < (double)(p_100301_ + p_100303_);
//            return flag && !flag1 && !this.selectedTab.isHoveredOrFocused();
//        }
//    }

//    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
//        this.ignoreTextInput = false;
//        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
//            if (pKeyCode == 256 && !this.isOffsetNextToMainGUI()) {
//                this.setVisible(false);
//                return true;
//            } else if (this.searchBox.keyPressed(pKeyCode, pScanCode, pModifiers)) {
//                this.checkSearchStringUpdate();
//                return true;
//            } else if (this.searchBox.isFocused() && this.searchBox.isVisible() && pKeyCode != 256) {
//                return true;
//            } else if (this.minecraft.options.keyChat.matches(pKeyCode, pScanCode) && !this.searchBox.isFocused()) {
//                this.ignoreTextInput = true;
//                this.searchBox.setFocus(true);
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }

//    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
//        this.ignoreTextInput = false;
//        return GuiEventListener.super.keyReleased(pKeyCode, pScanCode, pModifiers);
//    }













//    public boolean charTyped(char pCodePoint, int pModifiers) {
////        if (this.ignoreTextInput) {
////            return false;
////        } else if (this.isVisible() && !this.minecraft.player.isSpectator()) {
////            if (this.searchBox.charTyped(pCodePoint, pModifiers)) {
////                this.checkSearchStringUpdate();
////                return true;
////            } else {
////                return GuiEventListener.super.charTyped(pCodePoint, pModifiers);
////            }
////        } else {
//            return false;
////        }
//    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return getRect().containsPoint(mouseX, mouseY);
    }

    private void checkSearchStringUpdate() {
//        String s = this.searchBox.getValue().toLowerCase(Locale.ROOT);
//        this.pirateSpeechForThePeople(s);
//        if (!s.equals(this.lastSearch)) {
//            this.updateCollections(false);
//            this.lastSearch = s;
//        }

    }



    protected void sendUpdateSettings() {
//        if (this.minecraft.getConnection() != null) {
//            RecipeBookType recipebooktype = this.menu.getRecipeBookType();
//            boolean flag = this.book.getBookSettings().isOpen(recipebooktype);
//            boolean flag1 = this.book.getBookSettings().isFiltering(recipebooktype);
//            this.minecraft.getConnection().send(new ServerboundRecipeBookChangeSettingsPacket(recipebooktype, flag, flag1));
//        }

    }

    /**
     * Check if we should activate the pirate speak easter egg"
     */
    private void pirateSpeechForThePeople(String pText) {
        if ("excitedze".equals(pText)) {
            LanguageManager languagemanager = this.minecraft.getLanguageManager();
            LanguageInfo languageinfo = languagemanager.getLanguage("en_pt");
            if (languagemanager.getSelected().compareTo(languageinfo) == 0) {
                return;
            }

            languagemanager.setSelected(languageinfo);
            this.minecraft.options.languageCode = languageinfo.getCode();
            this.minecraft.reloadResourcePacks();
            this.minecraft.options.save();
        }

    }

    private boolean isOffsetNextToMainGUI() {
        return this.xOffset == OFFSET_X_POSITION;
    }

    public int updateScreenPosition(int width, int imageWidth) {
        int i;

        //fixme... move slightly to the right
        // this isn't correct... needs to be wider?
        if (this.isVisible() && !this.widthTooNarrow) {
            i = (int) (199 + (width - imageWidth - 200) * 0.5);
            System.out.println(("width - imagewidth"));


        } else {
            i = (int) ((width - imageWidth) * 0.5);
        }

        System.out.println("i here: " + i);

        return i;
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggleVisibility() {
        setVisible(!isVisible());
    }

    public void setVisible(boolean visible) {
        System.out.println("set visible: " + visible);

        if (visible) {
            this.initVisuals();
        }

        this.visible = visible;
//        this.book.setOpen(this.menu.getRecipeBookType(), visible);
//        if (!visible) {
//            this.recipeBookPage.setInvisible();
//        }


        this.doThisOnSomeEvent();
        this.sendUpdateSettings(); // todo?
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setRect(IRect rect) {
        this.rect = rect;
    }

    @Override
    public IRect getRect() {
        return rect;
    }

    @Override
    public void doThisOnChange() {
        System.out.println("module selection frame do this on change");

        if (this.isVisible() && this.isEnabled()) {
            updateTabs();
            if (doThis != null) {
                this.doThis.doThis(this);
            }
        }
    }


    @Override
    public void doThisOnSomeEvent() {
        if (doThisOnToggleVisibility != null) {
            System.out.println("doing this");
            doThisOnToggleVisibility.doThis(this);
        }
    }

    IDoThis doThisOnToggleVisibility = null;
    @Override
    public void setDoThisOnSomeEvent(IDoThis iDoThis) {
        doThisOnToggleVisibility = iDoThis;
    }

    IDoThis doThis = null;
    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {
        this.doThis = iDoThis;
    }
}