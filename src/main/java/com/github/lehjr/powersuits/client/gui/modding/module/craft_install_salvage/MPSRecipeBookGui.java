package com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.clickable.TexturedToggleButton;
import com.github.lehjr.numina.util.client.gui.frame.*;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.client.recipe.ModuleRecipeGroup;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import com.github.lehjr.powersuits.client.gui.common.ModularItemTabToggleWidget;
import com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest.RectTextFieldWidget;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.GhostRecipe;
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class MPSRecipeBookGui extends RecipeBookGui implements IGuiFrame {
    ModularItemSelectionFrame modularItemSelectionFrame;
    /**
     * needed for showing the recipe
     */
    protected final GhostRecipe ghostRecipe = new GhostRecipe();
    protected ClientRecipeBook book;
    protected TestRecipeBookPage recipeBookPage = new TestRecipeBookPage();

    /**
     * =============================================================================
     */

    protected static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");


    protected static final ITextComponent SEARCH_HINT = (new TranslationTextComponent("gui.recipebook.search_hint")).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.GRAY);
    private static final ITextComponent ONLY_CRAFTABLES_TOOLTIP = new TranslationTextComponent("gui.recipebook.toggleRecipes.craftable");
    private static final ITextComponent ALL_RECIPES_TOOLTIP = new TranslationTextComponent("gui.recipebook.toggleRecipes.all");
    protected int xOffset;

    /**
     * maybe needed ??? ------------------------------------------------------------
     */
    float zLevel = 0;
    protected RectTextFieldWidget searchBox;
    protected String lastSearch = "";
    protected TexturedToggleButton filterButton;
    MultiRectHolderFrame mainFrame;

    public MPSRecipeBookGui(ModularItemSelectionFrame modularItemSelectionFrame, RecipeBookContainer<?> container) {
        this.minecraft = Minecraft.getInstance();

        int frameWidth = 157;
        int topFrameHeight = 16; // button is taller than the text box
        /** TOP SECTION [TEXT INPUT BOX | FILTER TOGGLE BUTTON] */
        /** search box wrapped in a holder --------------------------------------------- */
        this.searchBox = new RectTextFieldWidget(this.minecraft.font, 0, 0, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);

        RectHolderFrame topLeft = new RectHolderFrame(searchBox, 80, topFrameHeight, RectHolderFrame.RectPlacement.CENTER_LEFT) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return searchBox.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return searchBox.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public List<ITextComponent> getToolTip(int mouseX, int mouseY) {
                return searchBox.getToolTip(mouseX, mouseY);
            }

            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                if (!searchBox.isFocused() && searchBox.getValue().isEmpty()) {
                    drawString(matrixStack, Minecraft.getInstance().font, SEARCH_HINT, searchBox.x + 4, searchBox.y + 4, -1);
                } else {
                    super.render(matrixStack, mouseX, mouseY, frameTime);
                }
            }
        };

        filterButton = new TexturedToggleButton(0, 0, 26, 16,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.BLACK, Colour.BLACK,
                0, 0,
                RECIPE_BOOK_LOCATION);
        filterButton.setTextureStartX(152).setTextureStartY(41);
        filterButton.setTexDiffX(28).setTexDiffY(18);
        filterButton.setTextureHeight(256).setTextureWidth(256);
        filterButton.setIconWidth(26).setIconHeight(16);
        filterButton.setOnPressed(pressed -> {
            setFiltering(filterButton.getState());
            this.sendUpdateSettings();
            this.updateCollections(false);
        });


        RectHolderFrame topRight = new RectHolderFrame(filterButton, 77, topFrameHeight, RectHolderFrame.RectPlacement.CENTER_RIGHT) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return filterButton.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return filterButton.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public List<ITextComponent> getToolTip(int mouseX, int mouseY) {
                return filterButton.getToolTip(mouseX, mouseY);
            }
        };

        MultiRectHolderFrame topFrame = new MultiRectHolderFrame(true, true, 0, 0);
        topFrame.addRect(topLeft).addRect(topRight).doneAdding();
        topFrame.setBackground(new DrawableTile(0, 0, 0, 0).setBackgroundColour(Colour.BLACK));

        /** Finally, a frame to bring it all together */
        mainFrame = new MultiRectHolderFrame(false, true, 0, 0);
        mainFrame.addRect(topFrame);
        mainFrame.addRect(new GUISpacer(157, 4));
        mainFrame.addRect(recipeBookPage);
        mainFrame.doneAdding();
        //.setBackground(new DrawableTile(0,0,0,0).setBackgroundColour(Colour.BLACK));

        this.modularItemSelectionFrame = modularItemSelectionFrame;
        this.menu = container;
    }

    public void init() {
        this.mainFrame.initGrowth();
        minecraft.player.containerMenu = this.menu;
        this.book = minecraft.player.getRecipeBook();
        this.timesInventoryChanged = minecraft.player.inventory.getTimesChanged();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);

        this.setVisible(true);

        this.xOffset = 86;

        this.stackedContents.clear();
        this.minecraft.player.inventory.fillStackedContents(this.stackedContents);
        this.menu.fillCraftSlotsStackedContents(this.stackedContents);

        String s = this.searchBox != null ? this.searchBox.getValue() : "";
        this.searchBox.setValue(s);
        this.recipeBookPage.init();
        this.recipeBookPage.addListener(this);
        this.updateCollections(false);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return super.mouseScrolled(mouseX, mouseY, dWheel);
    }

    @Override
    public boolean changeFocus(boolean unused) {
        return false;
    }

    Optional<Map<EnumModuleCategory, Pair<String, Integer>>> isValidModuleAndRecipe(IModularItem modularItemHandler, IRecipe<?> recipe) {
        Map<EnumModuleCategory, Pair<String, Integer>> ret = new HashMap<>();
        recipe.getResultItem().getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
            if (iPowerModule.isAllowed() && modularItemHandler.isModuleValid(recipe.getResultItem())) {
                EnumModuleCategory moduleCategory = iPowerModule.getCategory();
                ret.put(moduleCategory, Pair.of(iPowerModule.getModuleGroup(), iPowerModule.getTier()));
            }
        });
        return ret.isEmpty() ? Optional.empty() : Optional.of(ret);
    }

    Optional<Map<EnumModuleCategory, ModuleRecipeGroup>> isValidModuleAndList(@Nonnull ItemStack test, IModularItem modularItemHandler, RecipeList recipeList) {
        Map<EnumModuleCategory, ModuleRecipeGroup> ret = new HashMap<>();
        test.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
            if (iPowerModule.isAllowed() && modularItemHandler.isModuleValid(test)) {
                EnumModuleCategory moduleCategory = iPowerModule.getCategory();
                ModuleRecipeGroup tempGroup = new ModuleRecipeGroup(recipeList, iPowerModule.getTier(), iPowerModule.getModuleGroup());
                /** needed for this stupid implementation since it isn't carried over */
                tempGroup.updateKnownRecipes(this.book);
                /** update whether or not can be crafted */
                tempGroup.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), this.book);
                ret.put(moduleCategory, tempGroup);
            }
        });
        return ret.isEmpty() ? Optional.empty() : Optional.of(ret);
    }


    /**
     * Note for future reference: addCriterion seems to be related to unlocking recipes
     * Also, yes, this is actually a bigger mess than the vanilla setup
     *
     */
    protected void updateCollections(boolean p_193003_1_) {
        Map<EnumModuleCategory, List<ModuleRecipeGroup>> unsortedRecipes = new HashMap<>();

        /** Fetch a list of modules valid for the selected ModularItem. Note recipes need to be unlocked to show */
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab -> {
            if (tab instanceof ModularItemTabToggleWidget) {
                ItemStack modularItem = minecraft.player.getItemBySlot(tab.getSlotType());
                modularItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                    if (iItemHandler instanceof IModularItem) {
                        List<RecipeList> list1 = this.book.getCollection(tab.getCategory());
                        for (RecipeList recipeList : list1) {
                            if (recipeList.hasSingleResultItem()) {
                                isValidModuleAndList(recipeList.getRecipes().get(0)
                                        .getResultItem(), (IModularItem) iItemHandler, recipeList).ifPresent(moduleRecipeMap -> {
                                    // yes, a Pair may have been better
                                    for (Map.Entry<EnumModuleCategory, ModuleRecipeGroup> entry : moduleRecipeMap.entrySet()) {
                                        List<ModuleRecipeGroup> anotherList = unsortedRecipes.getOrDefault(entry.getKey(), new ArrayList<>());
                                        ModuleRecipeGroup newList = entry.getValue();

                                        if (!anotherList.contains(newList)) {
                                            /** filter out unknown recipes */
                                            if (newList.hasKnownRecipes() &&
                                                    /** filter out recipes that won't fit in the grid */
                                                    newList.hasFitting() &&
                                                    /** filter out uncraftable recipes if the filter is enabled */
                                                    (!this.book.isFiltering(this.menu) ||
                                                            (this.book.isFiltering(this.menu) && newList.hasCraftable()))) {
                                                anotherList.add(newList);
                                                unsortedRecipes.put(entry.getKey(), anotherList);
                                            }
                                        }
                                    }
                                });
                            } else {
                                // TODO: finish this when a recipe finally passes this test
                                List<IRecipe> fixedList = new ArrayList<>();

                                for (IRecipe<?> recipe : recipeList.getRecipes()) {
                                    isValidModuleAndRecipe((IModularItem) iItemHandler, recipe).ifPresent(map -> {
                                        fixedList.add(recipe);
                                    });
                                }
                            }

//                            for (IRecipe<?> recipe : recipeList.getRecipes()) {
//                                recipe.getResultItem().getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
//                                    if (iPowerModule.isAllowed() && ((IModularItem) iItemHandler).isModuleValid(recipe.getResultItem())) {
//                                        EnumModuleCategory moduleCategory = iPowerModule.getCategory();
//                                        String group = iPowerModule.getModuleGroup();
//                                        int tier = iPowerModule.getTier();
//
//                                        List<ModuleRecipeGroup> list2 = unsortedRecipes.getOrDefault(moduleCategory, new ArrayList<>());
//                                        ModuleRecipeGroup outGroup =
//                                                new ModuleRecipeGroup(recipeList, tier, group);
//
//                                        if (!list2.contains(outGroup)) {
//                                            list2.add(outGroup);
//                                            if (!list2.isEmpty()) {
//                                                unsortedRecipes.put(moduleCategory, list2);
//                                            }
//                                        }
//                                    }
//                                });
//                            }
                        }
                    }
                });
            }
        });

        String s = this.searchBox.getValue();
        if (!s.isEmpty()) {
            ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<>(this.minecraft.getSearchTree(SearchTreeManager.RECIPE_COLLECTIONS).search(s.toLowerCase(Locale.ROOT)));

            // fixme... allow searching by catagory???
            unsortedRecipes.entrySet().stream().forEach(e -> e.getValue().removeIf((recipeList) -> !objectset.contains(recipeList)));
        }

        Map<EnumModuleCategory, List<ModuleRecipeGroup>> sortedRecipes = new HashMap<>();
        Map<EnumModuleCategory, List<ModuleRecipeGroup>> unGroupedRecipes = new HashMap<>();

        // like a map but with duplicate keys
        List<Pair<EnumModuleCategory, List<ModuleRecipeGroup>>> recipes = new ArrayList<>();


        for (Map.Entry<EnumModuleCategory, List<ModuleRecipeGroup>> entry : unsortedRecipes.entrySet()) {
            EnumModuleCategory category = entry.getKey();
            List<ModuleRecipeGroup> moduleRecipeGroupList = entry.getValue();

            if (moduleRecipeGroupList.isEmpty()) {
                continue;
            }

            List<ModuleRecipeGroup> tempList = new ArrayList<>();
            entry.getValue().stream().distinct().forEach(moduleRecipeGroup -> {
                // filter out unsortable recipes to add at the end of the list
                if (moduleRecipeGroup.getGroup().isEmpty()) {
                    List<ModuleRecipeGroup> anotherRecipeList = unGroupedRecipes.getOrDefault(category, new ArrayList<>());
                    if (!anotherRecipeList.contains(moduleRecipeGroup)) {
                        anotherRecipeList.add(moduleRecipeGroup);
                        if (!anotherRecipeList.isEmpty()) {
                            unGroupedRecipes.put(category, anotherRecipeList);
                        }
                    }
                } else {
                    tempList.add(moduleRecipeGroup);
                }
            });

            // Sort the list
            Collections.sort(tempList, Comparator.comparing(ModuleRecipeGroup::getTier));

            // add the unsorted recipes to the end of the list
            if (!unGroupedRecipes.getOrDefault(category, new ArrayList<>()).isEmpty()) {
                tempList.addAll(unGroupedRecipes.getOrDefault(category, new ArrayList<>()));
            }

            if (!tempList.isEmpty()) {
                sortedRecipes.put(category, tempList);
            }
        }

        this.recipeBookPage.updateCollectionsMap(sortedRecipes, p_193003_1_);
    }


    /**
     * TODO: turn this into a recipe display widget of some sort... incorporate categories
     */
    class TestRecipeBookPage extends MultiRectHolderFrame implements IRenderable, /*IGuiEventListener,*/ IGuiFrame {
        static final int maxRecipesPerPage = 18;

        /**
         * whether or not this can be seen
         */
        boolean isVisible = true;
        /**
         * whether or not this is enabled
         */
        boolean isEnabled = true;
        /**
         * apparently the button the mouse cursor is over, if any
         */
        protected MPSRecipeWidget hoveredButton;

        /**
         * recipe lists accessible by EnumModuleCategory
         */
        protected List<Pair<EnumModuleCategory, List<ModuleRecipeGroup>>> recipeCollection;

        /**
         * recipe widget buttons
         */
        protected final List<MPSRecipeWidget> buttons = Lists.newArrayListWithCapacity(maxRecipesPerPage);


        protected final List<IRecipeUpdateListener> showListeners = Lists.newArrayList();


        /** STUFF TO GET REPLACED ----------------------------------------------------------------------------------------- */


        /**
         * navigation buttons
         */
        protected ToggleWidget forwardButton;
        protected ToggleWidget backButton;
        /**
         * number of recipe pages
         */
        protected int totalPages;
        /**
         * index of current page
         */
        protected int currentPage; // TODO: use for index of map keys


        protected RecipeBook recipeBook;
        protected IRecipe<?> lastClickedRecipe;
        protected RecipeList lastClickedRecipeCollection;
        RelativeRect labelBox = new RelativeRect(0, 0, 157, 16);
        RelativeRect labelSpacer = new RelativeRect();


        public TestRecipeBookPage() {
            super(false, true, 0, 0);// 157, 116);
            this.forwardButton = new ToggleWidget(0, 0, 12, 17, false);
            this.forwardButton.initTextureValues(1, 208, 13, 18, MPSRecipeBookGui.RECIPE_BOOK_LOCATION);
            this.backButton = new ToggleWidget(0, 0, 12, 17, true);
            this.backButton.initTextureValues(1, 208, 13, 18, MPSRecipeBookGui.RECIPE_BOOK_LOCATION);


            setBackground(new DrawableTile(0, 0, 0, 0).setBackgroundColour(Colour.DARK_GREY));

//            setBackgroundColour(Colour.DARK_GREY);
//            for(int i = 0; i < 28; ++i) {
//                this.buttons.add(new MPSRecipeWidget());
//            }

            // fixme: add holder for label

            addRect(labelBox);

            for (int row = 0; row < 3; row++) {
                MultiRectHolderFrame rowHolder = new MultiRectHolderFrame(true, true, 0, 0);

                for (int col = 0; col < 6; col++) {
                    MPSRecipeWidget widget = new MPSRecipeWidget();
                    buttons.add(widget);
                    rowHolder.addRect(widget);
                }
                rowHolder.doneAdding();
                addRect(rowHolder);
            }

            /** Bottom row of forward/back buttons and page label */
            MultiRectHolderFrame bottomRow = new MultiRectHolderFrame(true, true, 0, 0);
            bottomRow.addRect(new WidgetHolderFrame(backButton, 40, 27, RectHolderFrame.RectPlacement.CENTER_RIGHT) {
                @Override
                public boolean mouseClicked(double v, double v1, int i) {
                    return false;
                }

                @Override
                public boolean mouseReleased(double v, double v1, int i) {
                    return false;
                }

                @Override
                public List<ITextComponent> getToolTip(int i, int i1) {
                    return null;
                }
            });
            bottomRow.addRect(labelSpacer.setWidth(77).setHeight(27));

            bottomRow.addRect(new WidgetHolderFrame(forwardButton, 40, 27, RectHolderFrame.RectPlacement.CENTER_LEFT) {
                @Override
                public boolean mouseClicked(double v, double v1, int i) {
                    return false;
                }

                @Override
                public boolean mouseReleased(double v, double v1, int i) {
                    return false;
                }

                @Override
                public List<ITextComponent> getToolTip(int i, int i1) {
                    return null;
                }
            });
            bottomRow.doneAdding();
            addRect(bottomRow);
            doneAdding();
        }

        // Why does Minecraft instance get initialized here when it can be done in the constructor?
        public void init() {
            this.recipeBook = minecraft.player.getRecipeBook();

        }

        public void addListener(MPSRecipeBookGui recipeBookGui) {
            this.showListeners.remove(recipeBookGui);
            this.showListeners.add(recipeBookGui);
        }

        /**
         * TODO: preparing to sort in groups by EnumModuleCategory
         *
         * @param recipeMap
         * @param p_194192_2_
         */
        public void updateCollectionsMap(Map<EnumModuleCategory, List<ModuleRecipeGroup>> recipeMap, boolean p_194192_2_) {
            // like a map but with duplicate keys
            List<Pair<EnumModuleCategory, List<ModuleRecipeGroup>>> recipes = new ArrayList<>();

            for (Map.Entry<EnumModuleCategory, List<ModuleRecipeGroup>> entry : recipeMap.entrySet()) {
                List<List<ModuleRecipeGroup>> recipeGroups = Lists.partition(entry.getValue().stream().distinct().collect(Collectors.toList()), maxRecipesPerPage);
                recipeGroups.stream().distinct().filter(list -> !list.isEmpty()).forEach(moduleRecipeGroups -> {
                    recipes.add(Pair.of(entry.getKey(), moduleRecipeGroups));
                });
            }
            this.recipeCollection = recipes;

            this.totalPages = recipes.size();
            if (this.totalPages <= this.currentPage || p_194192_2_) {
                this.currentPage = 0;
            }
            this.updateMapButtonsForPage();
        }

        @Override
        public List<ITextComponent> getToolTip(int i, int i1) {
            return null;
        }

        @Override
        public void setEnabled(boolean enabled) {
            this.isEnabled = enabled;
        }

        @Override
        public boolean isEnabled() {
            return isEnabled;
        }

        @Override
        public void setVisible(boolean isVisible) {
            this.isVisible = isVisible;
        }

        @Override
        public boolean isVisible() {
            return isVisible;
        }

        protected void updateMapButtonsForPage() {
            int i = maxRecipesPerPage * this.currentPage;
            List<ModuleRecipeGroup> recipeCollections;
            if (recipeCollection.size() > 0) {
//                    EnumModuleCategory category = recipeCollection.get(currentPage).getLeft();
                recipeCollections = recipeCollection.get(currentPage).getRight();
            } else {
                recipeCollections = new ArrayList<>();
            }

            for (int index = 0; index < buttons.size(); index++) {
                MPSRecipeWidget recipewidget = this.buttons.get(index);
                if (index < recipeCollections.size()) {
                    RecipeList recipelist = recipeCollections.get(index);
                    recipewidget.init(recipelist, this, ItemStack.EMPTY);
                    recipewidget.visible = true;
                } else {
                    recipewidget.visible = false;
                }
            }
            this.updateArrowButtons();
        }

        protected void updateArrowButtons() {
            this.forwardButton.visible = this.totalPages > 1 && this.currentPage < this.totalPages - 1;
            this.backButton.visible = this.totalPages > 1 && this.currentPage > 0;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
            super.render(matrixStack, mouseX, mouseY, frameTime);
            if (this.totalPages > 1) {
                String s = this.currentPage + 1 + "/" + this.totalPages;
                int i = minecraft.font.width(s);
                minecraft.font.draw(matrixStack, s,
                        (float) labelSpacer.centerx() - i / 2,
                        (float) labelSpacer.centery() - 4, -1);
            }

            this.hoveredButton = null;

            for (MPSRecipeWidget recipewidget : this.buttons) {
                recipewidget.render(matrixStack, mouseX, mouseY, frameTime);
                if (recipewidget.visible && recipewidget.isHovered()) {
                    this.hoveredButton = recipewidget;
                }
            }

            this.backButton.render(matrixStack, mouseX, mouseY, frameTime);
            this.forwardButton.render(matrixStack, mouseX, mouseY, frameTime);

            renderLabel(matrixStack);
        }

        void renderLabel(MatrixStack matrixStack) {
            if (currentPage < recipeCollection.size()) {
                EnumModuleCategory label = recipeCollection.get(currentPage).getLeft();
                if (label != null) {
                    drawString(matrixStack, Minecraft.getInstance().font, label.getTranslation(), (int) labelBox.finalLeft() + 4, (int) labelBox.finalTop() + 4, -1);
                }
            }
        }

        @Override
        public void update(double v, double v1) {
        }

        public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (minecraft.screen != null && this.hoveredButton != null) {
                minecraft.screen.renderComponentTooltip(matrixStack, this.hoveredButton.getTooltipText(minecraft.screen), mouseX, mouseY);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.lastClickedRecipe = null;
            this.lastClickedRecipeCollection = null;

            if (this.forwardButton.mouseClicked(mouseX, mouseY, button)) {
                ++this.currentPage;
                this.updateMapButtonsForPage();
                return true;
            } else if (this.backButton.mouseClicked(mouseX, mouseY, button)) {
                --this.currentPage;
                this.updateMapButtonsForPage();
                return true;
            } else {
                for (MPSRecipeWidget recipewidget : this.buttons) {
                    if (recipewidget.mouseClicked(mouseX, mouseY, button)) {
                        if (button == 0) {
                            this.lastClickedRecipe = recipewidget.getRecipe();
                            this.lastClickedRecipeCollection = recipewidget.getCollection();
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        @Nullable
        public IRecipe<?> getLastClickedRecipe() {
            return this.lastClickedRecipe;
        }

        @Nullable
        public RecipeList getLastClickedRecipeCollection() {
            return this.lastClickedRecipeCollection;
        }

        public void recipesShown(List<IRecipe<?>> iRecipes) {
            for (IRecipeUpdateListener irecipeupdatelistener : this.showListeners) {
                irecipeupdatelistener.recipesShown(iRecipes);
            }
        }
    }


    public void tick() {
        if (this.isVisible()) {
            if (this.timesInventoryChanged != this.minecraft.player.inventory.getTimesChanged()) {
                this.updateStackedContents();
                this.timesInventoryChanged = this.minecraft.player.inventory.getTimesChanged();
            }

            this.searchBox.tick();
        }
    }

    private void updateStackedContents() {
        this.stackedContents.clear();
        this.minecraft.player.inventory.fillStackedContents(this.stackedContents);
        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
        this.updateCollections(false);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (this.isVisible()) {
            mainFrame.render(matrixStack, mouseX, mouseY, frameTime);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 200.0F);
            this.minecraft.getTextureManager().bind(RECIPE_BOOK_LOCATION);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.recipeBookPage.render(matrixStack, mouseX, mouseY, frameTime);
            RenderSystem.popMatrix();
        }
    }

    @Override
    public void renderTooltip(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        if (this.isVisible()) {
            this.recipeBookPage.renderTooltip(matrixStack, mouseX, mouseY);
            // FIXME tooltip for filter button


            if (this.filterButton.hitBox(mouseX, mouseY)) {
                ITextComponent itextcomponent = this.getFilterButtonTooltip();
                if (this.minecraft.screen != null) {
                    this.minecraft.screen.renderTooltip(matrixStack, itextcomponent, mouseX, mouseY);
                }
            }

            this.renderGhostRecipeTooltip(matrixStack, guiLeft, guiTop, mouseX, mouseY);
        }
    }

    private ITextComponent getFilterButtonTooltip() {
        return this.filterButton.getState() ? this.getRecipeFilterName() : ALL_RECIPES_TOOLTIP;
    }

    protected ITextComponent getRecipeFilterName() {
        return ONLY_CRAFTABLES_TOOLTIP;
    }

    private void renderGhostRecipeTooltip(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY) {
        ItemStack itemstack = null;

        for (int i = 0; i < this.ghostRecipe.size(); ++i) {
            GhostRecipe.GhostIngredient ghostIngredient = this.ghostRecipe.get(i);
            int j = ghostIngredient.getX() + guiLeft;
            int k = ghostIngredient.getY() + guiTop;
            if (mouseX >= j && mouseY >= k && mouseX < j + 16 && mouseY < k + 16) {
                itemstack = ghostIngredient.getItem();
            }
        }

        if (itemstack != null && this.minecraft.screen != null) {
            this.minecraft.screen.renderComponentTooltip(matrixStack, this.minecraft.screen.getTooltipFromItem(itemstack), mouseX, mouseY);
        }

    }

    @Override
    public void renderGhostRecipe(MatrixStack matrixStack, int p_230477_2_, int p_230477_3_, boolean p_230477_4_, float p_230477_5_) {
        this.ghostRecipe.render(matrixStack, this.minecraft, p_230477_2_, p_230477_3_, p_230477_4_, p_230477_5_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (this.recipeBookPage.mouseClicked(mouseX, mouseY, button)) {
                IRecipe<?> irecipe = this.recipeBookPage.getLastClickedRecipe();
                RecipeList recipelist = this.recipeBookPage.getLastClickedRecipeCollection();
                if (irecipe != null && recipelist != null) {
                    if (!recipelist.isCraftable(irecipe) && this.ghostRecipe.getRecipe() == irecipe) {
                        return false;
                    }

                    this.ghostRecipe.clear();
                    this.minecraft.gameMode.handlePlaceRecipe(this.minecraft.player.containerMenu.containerId, irecipe, Screen.hasShiftDown());
                    if (!this.isOffsetNextToMainGUI()) {
                        this.setVisible(false);
                    }
                }
                return true;
            } else if (this.searchBox.mouseClicked(mouseX, mouseY, button)) {
                return true;

                // fixme: move to frame holder ?
            } else if (this.filterButton.mouseClicked(mouseX, mouseY, button)) {

                return true;
            } else {
                if (modularItemSelectionFrame.mouseCLicked(button, mouseX, mouseY)) {
                    this.updateCollections(true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double x, double y) {
        mainFrame.update(x, y);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void setFiltering(boolean filtering) {
        RecipeBookCategory recipebookcategory = this.menu.getRecipeBookType();
        this.book.setFiltering(recipebookcategory, filtering);
    }

    private boolean toggleFiltering1() {
        RecipeBookCategory recipebookcategory = this.menu.getRecipeBookType();
        boolean flag = !this.book.isFiltering(recipebookcategory);
        this.book.setFiltering(recipebookcategory, flag);
        return flag;
    }

    @Override
    public boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int width, int height, int mouseButton) {
        if (!this.isVisible()) {
            return true;
        } else {
            boolean flag = mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + width) || mouseY >= (double) (top + height);
            boolean flag1 = (double) (left - 147) < mouseX && mouseX < (double) left && (double) top < mouseY && mouseY < (double) (top + height);
            return flag && !flag1 && this.modularItemSelectionFrame.selectedIsSlotHovered();
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    private boolean isOffsetNextToMainGUI() {
        return true;
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        this.ignoreTextInput = false;
        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (this.searchBox.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
                this.checkSearchStringUpdate();
                return true;
            } else if (this.searchBox.isFocused() && this.searchBox.isVisible() && p_231046_1_ != 256) {
                return true;
            } else if (this.minecraft.options.keyChat.matches(p_231046_1_, p_231046_2_) && !this.searchBox.isFocused()) {
                this.ignoreTextInput = true;
                this.searchBox.setFocus(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        this.ignoreTextInput = false;
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        if (this.ignoreTextInput) {
            return false;
        } else if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (this.searchBox.charTyped(p_231042_1_, p_231042_2_)) {
                this.checkSearchStringUpdate();
                return true;
            }
        }
        return false;
    }

    @Override
    public void toggleVisibility() {
    }

    @Override
    public boolean isVisible() {
        return this.book.isOpen(this.menu.getRecipeBookType());
    }

    /**
     * Always visible
     *
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {
        this.book.setOpen(this.menu.getRecipeBookType(), true);
        this.sendUpdateSettings();
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
            if (this.isVisible()) {
                this.updateStackedContents();
            }
        }
    }

    private void checkSearchStringUpdate() {
        String s = this.searchBox.getValue().toLowerCase(Locale.ROOT);
        this.pirateSpeechForThePeople(s);
        if (!s.equals(this.lastSearch)) {
            this.updateCollections(false);
            this.lastSearch = s;
        }
    }

    private void pirateSpeechForThePeople(String string) {
        if ("excitedze".equals(string)) {
            LanguageManager languagemanager = this.minecraft.getLanguageManager();
            Language language = languagemanager.getLanguage("en_pt");
            if (languagemanager.getSelected().compareTo(language) == 0) {
                return;
            }

            languagemanager.setSelected(language);
            this.minecraft.options.languageCode = language.getCode();
            net.minecraftforge.client.ForgeHooksClient.refreshResources(this.minecraft, net.minecraftforge.resource.VanillaResourceType.LANGUAGES);
            this.minecraft.options.save();
        }

    }

    public void recipesUpdated() {
        if (this.isVisible()) {
            this.updateCollections(false);
        }
    }

    public void recipesShown(List<IRecipe<?>> iRecipes) {
        for (IRecipe<?> irecipe : iRecipes) {
            this.minecraft.player.removeRecipeHighlight(irecipe);
        }
    }

    public void setupGhostRecipe(IRecipe<?> iRecipe, List<Slot> slots) {
        ItemStack itemstack = iRecipe.getResultItem();
        this.ghostRecipe.setRecipe(iRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), (slots.get(0)).x, (slots.get(0)).y);
        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), iRecipe, iRecipe.getIngredients().iterator(), 0);
    }

    /**
     * Called by IRecipePlacer
     *
     * @param ingredientIterator
     * @param index
     * @param p_201500_3_
     * @param p_201500_4_
     * @param p_201500_5_
     */
    public void addItemToSlot(Iterator<Ingredient> ingredientIterator, int index, int p_201500_3_, int p_201500_4_, int p_201500_5_) {
        Ingredient ingredient = ingredientIterator.next();
        if (!ingredient.isEmpty()) {
            Slot slot = this.menu.slots.get(index);
            this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
        }
    }

    protected void sendUpdateSettings() {
        if (this.minecraft.getConnection() != null) {
            RecipeBookCategory recipebookcategory = this.menu.getRecipeBookType();
            boolean flag = this.book.getBookSettings().isOpen(recipebookcategory);
            boolean flag1 = this.book.getBookSettings().isFiltering(recipebookcategory);
            this.minecraft.getConnection().send(new CUpdateRecipeBookStatusPacket(recipebookcategory, flag, flag1));
        }
    }

    @Override
    public void removed() {
        this.searchBox = null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    /**
     * IGuiFrame stuff below -----------------------------------------------------------------------------------------
     */
    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public IDrawable setZLevel(float v) {
        this.zLevel = v;
        return this;
    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public IRect getRect() {
        return mainFrame;
    }

    @Override
    public void setOnInit(IInit iInit) {
    }

    @Override
    public void onInit() {
    }
}
