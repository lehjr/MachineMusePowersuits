package com.github.lehjr.powersuits.dev.crafting.client.gui.craftinstallsalvage;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.*;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.dev.crafting.client.gui.common.done.MPSRecipeTabToggleWidget;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class MPSRecipeBookGui extends RecipeBookGui implements IGuiFrame {
    /** Necessary stuff --------------------------------------------------------*/
    final EquipmentSlotType[] equipmentSlotTypes = new EquipmentSlotType[]{
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.OFFHAND
    };
    /** used for placement related functions */
    RelativeRect backgroundRect = new RelativeRect(false);
    /** needed for showing the recipe */
    protected final GhostRecipe ghostRecipe = new GhostRecipe();
    /** needed for recipe functions */
//    protected RecipeBookContainer<?> menu;

    protected ClientRecipeBook book;
    protected MPSRecipeBookPage recipeBookPage = new MPSRecipeBookPage();

    /** =============================================================================*/

    protected static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");


    protected static final ITextComponent SEARCH_HINT = (new TranslationTextComponent("gui.recipebook.search_hint")).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.GRAY);
    private static final ITextComponent ONLY_CRAFTABLES_TOOLTIP = new TranslationTextComponent("gui.recipebook.toggleRecipes.craftable");
    private static final ITextComponent ALL_RECIPES_TOOLTIP = new TranslationTextComponent("gui.recipebook.toggleRecipes.all");
    protected int xOffset;

    /** maybe needed ??? ------------------------------------------------------------*/
    float zLevel = 0;

    protected final List<MPSRecipeTabToggleWidget> tabButtons = Lists.newArrayList();
    protected MPSRecipeTabToggleWidget selectedTab;
    protected ToggleWidget filterButton;
    protected TextFieldWidget searchBox; // needed?
    protected String lastSearch = "";

    DrawableRelativeRect testRect = new DrawableRelativeRect(Colour.ORANGE, Colour.YELLOW, false);
//

    public MPSRecipeBookGui() {
        // Modular Item selection frame







    }




    // FIXME: width and height are not the width and height of this part of the Gui but the whole combined Gui
    public void init(int widthIn, int heightIn, Minecraft minecraft, boolean widthTooNarrow, RecipeBookContainer<?> container) {
        /*
            maybe oroginal values were:
            ------
            height = 166
            width =  147

         */


        this.minecraft = minecraft;
        this.backgroundRect.setWidth(widthIn);
        this.backgroundRect.setHeight(heightIn);
        this.backgroundRect.initGrowth();
        this.testRect.setHeight(166).setWidth(147);
        this.testRect.initGrowth();

        System.out.println("backgroundREct: " + backgroundRect);


        this.menu = container;
        minecraft.player.containerMenu = container;


        this.book = minecraft.player.getRecipeBook();
        this.timesInventoryChanged = minecraft.player.inventory.getTimesChanged();
        if (this.isVisible()) {
            this.initVisuals(widthTooNarrow);
        }

        minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    public void initVisuals(boolean widthTooNarrow) {
//        this.xOffset = widthTooNarrow ? 0 : 86;
        this.xOffset = 86;

        this.stackedContents.clear();
        this.minecraft.player.inventory.fillStackedContents(this.stackedContents);
        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
        String s = this.searchBox != null ? this.searchBox.getValue() : "";
        // FIXME? Really? INT's for placement? Oh wait, it's Minecraft -_-
        this.searchBox = new TextFieldWidget(this.minecraft.font, (int)finalLeft() + 25, (int)finalTop() + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
        this.searchBox.setMaxLength(50);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.searchBox.setValue(s);
        this.recipeBookPage.init(this.minecraft, (int)finalLeft(), (int)finalTop());
        this.recipeBookPage.addListener(this);
        this.filterButton = new ToggleWidget((int)finalLeft() + 110, (int)finalTop() + 12, 26, 16, this.book.isFiltering(this.menu));
        this.initFilterButtonTextures();
        this.tabButtons.clear();


        // look for modular items
        for (EquipmentSlotType slotType : equipmentSlotTypes) {
            ItemStack itemStack = minecraft.player.getItemBySlot(slotType);
            this.tabButtons.add(new MPSRecipeTabToggleWidget(itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> {
                if (iItemHandler instanceof IModularItem) {
                    return itemStack;
                }
                return ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY), slotType));
        }

        if (this.selectedTab != null) {
            this.selectedTab = this.tabButtons.stream().filter((tabToggleWidget) -> {
                return tabToggleWidget.getCategory().equals(this.selectedTab.getCategory());
            }).findFirst().orElse((MPSRecipeTabToggleWidget)null);
        }

        if (this.selectedTab == null) {
            this.selectedTab = this.tabButtons.get(0);
            this.selectedTab = this.tabButtons.get(1);
        }

        this.selectedTab.setStateTriggered(true);
        this.updateCollections(false);
        this.updateTabs();
    }

    public boolean changeFocus(boolean p_231049_1_) {
        return false;
    }

    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(152, 41, 28, 18, RECIPE_BOOK_LOCATION);
    }

    public void removed() {
        this.searchBox = null;
        this.selectedTab = null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public int updateScreenPosition(boolean p_193011_1_, int p_193011_2_, int p_193011_3_) {
        int i;
        if (this.isVisible() && !p_193011_1_) {
            i = 177 + (p_193011_2_ - p_193011_3_ - 200) / 2;
        } else {
            i = (p_193011_2_ - p_193011_3_) / 2;
        }

        return i;
    }

    public void toggleVisibility() {
        this.setVisible(!this.isVisible());
    }

    public boolean isVisible() {
        return this.book.isOpen(this.menu.getRecipeBookType());
    }

    public void setVisible(boolean visible) {
        this.book.setOpen(this.menu.getRecipeBookType(), visible);
        if (!visible) {
            this.recipeBookPage.setInvisible();
        }

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

    /**
     * Fetch a list of modules valid for the selected ModularItem. Note recipes need to be unlocked to show
     */
    protected List<RecipeList> updateCollections2() {
        ItemStack modularItem = minecraft.player.getItemBySlot(selectedTab.getSlotType());
        List<RecipeList> list1 = new ArrayList<>();

        modularItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            if (iItemHandler instanceof IModularItem) {
                List<RecipeList> list = this.book.getCollection(this.selectedTab.getCategory());
                for (RecipeList recipeList : list) {
                    for (IRecipe<?> recipe : recipeList.getRecipes()) {
                        recipe.getResultItem().getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
                            if (iPowerModule.isAllowed() && ((IModularItem) iItemHandler).isModuleValid(recipe.getResultItem())) {
                                if (!list1.contains(recipeList)) {
                                    list1.add(recipeList);
                                }
                            }
                        });
                    }
                }
            }
        });

        list1.forEach((recipeList) -> { // FIXME: This doesn't do squat for non-vanilla recipes
            recipeList.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), this.book);
        });
        return list1;
    }

    protected void updateCollections(boolean p_193003_1_) {
        List<RecipeList> list = updateCollections2();

        /** addCriterion seems to be related to unlocking recipes */

        list.forEach((p_193944_1_) -> p_193944_1_.canCraft(this.stackedContents, this.menu.getGridWidth(), this.menu.getGridHeight(), this.book));
        List<RecipeList> list1 = Lists.newArrayList(list);

        // filter out unknown recipes (needed since they're filtered out again and may cause issues elsewhere)
        list1.removeIf((p_193952_0_) -> !p_193952_0_.hasKnownRecipes());

        // Filters out recipes that either don't fit or are still locked
        list1.removeIf((p_193953_0_) -> !p_193953_0_.hasFitting());

        String s = this.searchBox.getValue();
        if (!s.isEmpty()) {
            ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<>(this.minecraft.getSearchTree(SearchTreeManager.RECIPE_COLLECTIONS).search(s.toLowerCase(Locale.ROOT)));
            list1.removeIf((p_193947_1_) -> !objectset.contains(p_193947_1_));
        }

        if (this.book.isFiltering(this.menu)) {
            list1.removeIf((p_193958_0_) -> !p_193958_0_.hasCraftable());
        }

        this.recipeBookPage.updateCollections(list1, p_193003_1_);
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

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (this.isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 200.0F);
            this.minecraft.getTextureManager().bind(RECIPE_BOOK_LOCATION);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blit(matrixStack, (int)finalLeft(), (int)finalTop(), 1, 1, 147, 166);
            if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
                drawString(matrixStack, this.minecraft.font, SEARCH_HINT, (int)finalLeft() + 25, (int)finalTop() + 14, -1);
            } else {
                this.searchBox.render(matrixStack, mouseX, mouseY, frameTime);
            }

            for(MPSRecipeTabToggleWidget recipetabtogglewidget : this.tabButtons) {
                recipetabtogglewidget.render(matrixStack, mouseX, mouseY, frameTime);
            }

            this.filterButton.render(matrixStack, mouseX, mouseY, frameTime);
            this.recipeBookPage.render(matrixStack, (int)finalLeft(), (int)finalTop(), mouseX, mouseY, frameTime);

//            testRect.render(matrixStack, mouseX, mouseY, frameTime);

            RenderSystem.popMatrix();


        }
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        return super.mouseScrolled(p_231043_1_, p_231043_3_, p_231043_5_);
    }



    public void renderTooltip(MatrixStack p_238924_1_, int p_238924_2_, int p_238924_3_, int p_238924_4_, int p_238924_5_) {
        if (this.isVisible()) {
            this.recipeBookPage.renderTooltip(p_238924_1_, p_238924_4_, p_238924_5_);
            if (this.filterButton.isHovered()) {
                ITextComponent itextcomponent = this.getFilterButtonTooltip();
                if (this.minecraft.screen != null) {
                    this.minecraft.screen.renderTooltip(p_238924_1_, itextcomponent, p_238924_4_, p_238924_5_);
                }
            }

            this.renderGhostRecipeTooltip(p_238924_1_, p_238924_2_, p_238924_3_, p_238924_4_, p_238924_5_);
        }
    }

    private ITextComponent getFilterButtonTooltip() {
        return this.filterButton.isStateTriggered() ? this.getRecipeFilterName() : ALL_RECIPES_TOOLTIP;
    }

    protected ITextComponent getRecipeFilterName() {
        return ONLY_CRAFTABLES_TOOLTIP;
    }

    private void renderGhostRecipeTooltip(MatrixStack p_238925_1_, int p_238925_2_, int p_238925_3_, int p_238925_4_, int p_238925_5_) {
        ItemStack itemstack = null;

        for(int i = 0; i < this.ghostRecipe.size(); ++i) {
            GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.get(i);
            int j = ghostrecipe$ghostingredient.getX() + p_238925_2_;
            int k = ghostrecipe$ghostingredient.getY() + p_238925_3_;
            if (p_238925_4_ >= j && p_238925_5_ >= k && p_238925_4_ < j + 16 && p_238925_5_ < k + 16) {
                itemstack = ghostrecipe$ghostingredient.getItem();
            }
        }

        if (itemstack != null && this.minecraft.screen != null) {
            this.minecraft.screen.renderComponentTooltip(p_238925_1_, this.minecraft.screen.getTooltipFromItem(itemstack), p_238925_4_, p_238925_5_);
        }

    }

    public void renderGhostRecipe(MatrixStack matrixStack, int p_230477_2_, int p_230477_3_, boolean p_230477_4_, float p_230477_5_) {
        this.ghostRecipe.render(matrixStack, this.minecraft, p_230477_2_, p_230477_3_, p_230477_4_, p_230477_5_);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (this.recipeBookPage.mouseClicked(mouseX, mouseY, button, (int)(this.width() - 147) / 2 - this.xOffset, (int)(this.height() - 166) / 2, 147, 166)) {
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
            } else if (this.filterButton.mouseClicked(mouseX, mouseY, button)) {
                boolean flag = this.toggleFiltering();
                this.filterButton.setStateTriggered(flag);
                this.sendUpdateSettings();
                this.updateCollections(false);
                return true;
            } else {



                for(MPSRecipeTabToggleWidget recipetabtogglewidget : this.tabButtons) {
                    if (recipetabtogglewidget.mouseClicked(mouseX, mouseY, button)) {
                        if (this.selectedTab != recipetabtogglewidget) {
                            this.selectedTab.setStateTriggered(false);
                            this.selectedTab = recipetabtogglewidget;
                            this.selectedTab.setStateTriggered(true);
                            this.updateCollections(true);
                        }

                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }



    @Override
    public void update(double v, double v1) {

    }

    private boolean toggleFiltering() {
        RecipeBookCategory recipebookcategory = this.menu.getRecipeBookType();
        boolean flag = !this.book.isFiltering(recipebookcategory);
        this.book.setFiltering(recipebookcategory, flag);
        return flag;
    }

    public boolean hasClickedOutside(double p_195604_1_, double p_195604_3_, int p_195604_5_, int p_195604_6_, int p_195604_7_, int p_195604_8_, int p_195604_9_) {
        if (!this.isVisible()) {
            return true;
        } else {
            boolean flag = p_195604_1_ < (double)p_195604_5_ || p_195604_3_ < (double)p_195604_6_ || p_195604_1_ >= (double)(p_195604_5_ + p_195604_7_) || p_195604_3_ >= (double)(p_195604_6_ + p_195604_8_);
            boolean flag1 = (double)(p_195604_5_ - 147) < p_195604_1_ && p_195604_1_ < (double)p_195604_5_ && (double)p_195604_6_ < p_195604_3_ && p_195604_3_ < (double)(p_195604_6_ + p_195604_8_);
            return flag && !flag1 && !this.selectedTab.isHovered();
        }
    }

    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        this.ignoreTextInput = false;
        if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (p_231046_1_ == 256 && !this.isOffsetNextToMainGUI()) {
                this.setVisible(false);
                return true;
            } else if (this.searchBox.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
                this.checkSearchStringUpdate();
                return true;
            } else if (this.searchBox.isFocused() && this.searchBox.isVisible() && p_231046_1_ != 256) {
                return true;
            } else if (this.minecraft.options.keyChat.matches(p_231046_1_, p_231046_2_) && !this.searchBox.isFocused()) {
                this.ignoreTextInput = true;
                this.searchBox.setFocus(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        this.ignoreTextInput = false;
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        if (this.ignoreTextInput) {
            return false;
        } else if (this.isVisible() && !this.minecraft.player.isSpectator()) {
            if (this.searchBox.charTyped(p_231042_1_, p_231042_2_)) {
                this.checkSearchStringUpdate();
                return true;
            }
//            else {
//                return super.charTyped(p_231042_1_, p_231042_2_);
//            }
        } else {
            return false;
        }
        return false;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
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

    private boolean isOffsetNextToMainGUI() {
        return this.xOffset == 86;
    }

    public void recipesUpdated() {
        this.updateTabs();
        if (this.isVisible()) {
            this.updateCollections(false);
        }
    }

    protected void updateTabs() {
        int finalLeft = (int) ((this.width() - 147) / 2 - this.xOffset - 30);
        int finalTop = (int) ((this.height() - 166) / 2 + 3);
        int k = 27;
        int l = 0;

        for(RecipeTabToggleWidget recipetabtogglewidget : this.tabButtons) {
            RecipeBookCategories recipebookcategories = recipetabtogglewidget.getCategory();
            if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
                if (recipetabtogglewidget.updateVisibility(this.book)) {
                    recipetabtogglewidget.setPosition((int)finalLeft() + 9, (int)finalTop() + 27 * l++);
                    recipetabtogglewidget.startAnimation(this.minecraft);
                }
            } else {
                recipetabtogglewidget.visible = true;
                recipetabtogglewidget.setPosition((int)finalLeft() + 9, (int)finalTop() + 27 * l++);
            }
        }
    }

    public void recipesShown(List<IRecipe<?>> iRecipes) {
        for(IRecipe<?> irecipe : iRecipes) {
            this.minecraft.player.removeRecipeHighlight(irecipe);
        }
    }

    public void setupGhostRecipe(IRecipe<?> iRecipe, List<Slot> slots) {
        ItemStack itemstack = iRecipe.getResultItem();
        this.ghostRecipe.setRecipe(iRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), (slots.get(0)).x, (slots.get(0)).y);
        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), iRecipe, iRecipe.getIngredients().iterator(), 0);
    }

    public void addItemToSlot(Iterator<Ingredient> ingredientIterator, int index, int p_201500_3_, int p_201500_4_, int p_201500_5_) {
        Ingredient ingredient = ingredientIterator.next();
        if (!ingredient.isEmpty()) {
            Slot slot = this.menu.slots.get(index);
            this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
        }
    }


    // FIXME: check what's needed here
    protected void sendUpdateSettings() {
        if (this.minecraft.getConnection() != null) {
            RecipeBookCategory recipebookcategory = this.menu.getRecipeBookType();
            boolean flag = this.book.getBookSettings().isOpen(recipebookcategory);
            boolean flag1 = this.book.getBookSettings().isFiltering(recipebookcategory);
            this.minecraft.getConnection().send(new CUpdateRecipeBookStatusPacket(recipebookcategory, flag, flag1));
        }
    }





    /** IGuiFrame stuff below ----------------------------------------------------------------------------------------- */
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
        return backgroundRect;
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }

    @Override
    public MusePoint2D getUL() {
        return backgroundRect.getUL();
    }

    @Override
    public MusePoint2D getWH() {
        return backgroundRect.getWH();
    }

    @Override
    public double left() {
        return backgroundRect.left();
    }

    @Override
    public double finalLeft() {
        return backgroundRect.finalLeft();
    }

    @Override
    public double top() {
        return backgroundRect.top();
    }

    @Override
    public double finalTop() {
        return backgroundRect.finalTop();
    }

    @Override
    public double right() {
        return backgroundRect.right();
    }

    @Override
    public double finalRight() {
        return backgroundRect.finalRight();
    }

    @Override
    public double bottom() {
        return backgroundRect.bottom();
    }

    @Override
    public double finalBottom() {
        return backgroundRect.finalBottom();
    }

    @Override
    public double width() {
        return backgroundRect.width();
    }

    @Override
    public double finalWidth() {
        return backgroundRect.finalWidth();
    }

    @Override
    public double height() {
        return backgroundRect.height();
    }

    @Override
    public double finalHeight() {
        return backgroundRect.finalHeight();
    }

    @Override
    public IRect setUL(MusePoint2D musePoint2D) {
        testRect.setUL(musePoint2D);

        return backgroundRect.setUL(musePoint2D);
    }

    @Override
    public IRect setWH(MusePoint2D musePoint2D) {
        return backgroundRect;
    }

    @Override
    public IRect setLeft(double v) {
        testRect.setLeft(v);

        return backgroundRect.setLeft(v);
    }

    @Override
    public IRect setRight(double v) {
        return backgroundRect.setRight(v);
    }

    @Override
    public IRect setTop(double v) {
        testRect.setTop(v);

        return backgroundRect.setTop(v);
    }

    @Override
    public IRect setBottom(double v) {
        return backgroundRect.setBottom(v);
    }

    @Override
    public IRect setWidth(double v) {
        return backgroundRect;
    }

    @Override
    public IRect setHeight(double v) {
        return backgroundRect;
    }

    @Override
    public void move(MusePoint2D musePoint2D) {
        backgroundRect.move(musePoint2D);
    }

    @Override
    public void move(double v, double v1) {
        backgroundRect.move(v, v1);
    }

    @Override
    public void setPosition(MusePoint2D musePoint2D) {
        backgroundRect.setPosition(musePoint2D);
    }

    @Override
    public boolean growFromMiddle() {
        return backgroundRect.growFromMiddle();
    }

    @Override
    public void initGrowth() {
        backgroundRect.initGrowth();
    }

    @Override
    public IRect setMeLeftOf(IRect relativeRect) {
        return backgroundRect.setMeLeftOf(relativeRect);
    }

    @Override
    public IRect setMeRightOf(IRect relativeRect) {
        return backgroundRect.setMeRightOf(relativeRect);
    }

    @Override
    public IRect setMeAbove(IRect relativeRect) {
        return backgroundRect.setMeAbove(relativeRect);
    }

    @Override
    public IRect setMeBelow(IRect relativeRect) {
        return backgroundRect.setMeBelow(relativeRect);
    }

    @Override
    public void setOnInit(IInit iInit) {

    }

    @Override
    public void onInit() {

    }
}
