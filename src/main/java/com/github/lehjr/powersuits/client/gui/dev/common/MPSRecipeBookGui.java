package com.github.lehjr.powersuits.client.gui.dev.common;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.GhostRecipe;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.client.CUpdateRecipeBookStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class MPSRecipeBookGui extends RecipeBookGui {
    private static final ITextComponent field_243411_k = new TranslationTextComponent("gui.recipebook.toggleRecipes.all");
    /** The outer green rectangle */
    protected DrawableRelativeRect outerFrame = new DrawableRelativeRect(0, 0, 0, 0,
            true,
            Colour.GREY_GUI_BACKGROUND,
            Colour.BLACK);


    /** The inner blue rectangle */
    protected DrawableRelativeRect innerFrame = new DrawableRelativeRect(0, 0, 0, 0,
            true,
            Colour.DARK_GREY,
            Colour.BLACK);

    protected static final ResourceLocation RECIPE_BOOK = new ResourceLocation(MPSConstants.MOD_ID,"textures/gui/recipe_book.png");

    protected static final ResourceLocation SEARCH_ICON = new ResourceLocation(MPSConstants.MOD_ID,"textures/gui/search.png");

    private int xOffset;
    private int width;
    private int height;
    protected final GhostRecipe ghostRecipe = new GhostRecipe();

    private final List<MPSRecipeTabToggleWidget> recipeTabs = Lists.newArrayList();
    private MPSRecipeTabToggleWidget currentTab;

    // todo: replace this with a textured button??

    protected ToggleWidget toggleRecipesBtn;
    protected RecipeBookContainer<?> container;
    protected Minecraft mc;
    private TextFieldWidget searchBar;
    private String lastSearch = "";
    protected ClientRecipeBook recipeBook;
    protected final MPSRecipeBookPage recipeBookPage = new MPSRecipeBookPage();
    protected final RecipeItemHelper stackedContents = new RecipeItemHelper();
    private int timesInventoryChanged;
    private boolean field_199738_u;

    @Override
    public void init(int width, int height, Minecraft minecraft, boolean widthTooNarrow, RecipeBookContainer<?> container) {
        this.mc = minecraft;
        this.width = width;
        this.height = height;
        this.container = this.field_201522_g = container;
        minecraft.player.openContainer = container;

        this.recipeBook = minecraft.player.getRecipeBook();

        this.timesInventoryChanged = minecraft.player.inventory.getTimesChanged();
        if (this.isVisible()) {
            this.initSearchBar(widthTooNarrow);
        }
        minecraft.keyboardListener.enableRepeatEvents(true);
    }

    int guiLeft = 0;

    public int getGuiLeft() {
        return guiLeft;
    }

    /** Looks like a second stage init() */
    @Override
    public void initSearchBar(boolean widthTooNarrow) {
        this.xOffset = widthTooNarrow ? 0 : 86;
        guiLeft = (this.width - 147) / 2 - this.xOffset;
        int guiTop = (this.height - 166) / 2;
        outerFrame.setTargetDimensions(new MusePoint2D(guiLeft, guiTop), new MusePoint2D(146, 166));
        innerFrame.setTargetDimensions(new MusePoint2D(guiLeft + 7, guiTop + 7), new MusePoint2D( 146 - 14, 166 -14));
        this.stackedContents.clear();
        this.mc.player.inventory.accountStacks(this.stackedContents);
        this.container.fillStackedContents(this.stackedContents);
        String s = this.searchBar != null ? this.searchBar.getText() : "";
        this.searchBar = new TextFieldWidget(this.mc.fontRenderer, guiLeft + 25, guiTop + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
        this.searchBar.setMaxStringLength(50);
        this.searchBar.setEnableBackgroundDrawing(false);
        this.searchBar.setVisible(true);
        this.searchBar.setTextColor(16777215);
        this.searchBar.setText(s);
        this.recipeBookPage.init(this.mc, guiLeft, guiTop);
        this.recipeBookPage.addListener(this);
//        this.toggleRecipesBtn = new ToggleWidget(guiLeft + 110, guiTop + 12, 26, 16, this.recipeBook.isFilteringCraftable(this.container));
        this.toggleRecipesBtn = new ToggleWidget(guiLeft + 110, guiTop + 12, 26, 16, this.recipeBook.func_242141_a(this.container));

        this.func_205702_a();
        this.recipeTabs.clear();

        for(RecipeBookCategories recipebookcategories : this.container.getRecipeBookCategories()) {
            this.recipeTabs.add(new MPSRecipeTabToggleWidget(recipebookcategories));
        }

        if (this.currentTab != null) {
            this.currentTab = this.recipeTabs.stream().filter((tabOther) -> {
                return tabOther.func_201503_d()/* getCategory */.equals(this.currentTab.func_201503_d());
            }).findFirst().orElse((MPSRecipeTabToggleWidget)null);
        }

        if (this.currentTab == null) {
            this.currentTab = this.recipeTabs.get(0);
        }

        this.currentTab.setStateTriggered(true);
        this.updateCollections(false);
        this.updateTabs();
    }

    @Override
    public boolean changeFocus(boolean p_changeFocus_1_) {
        return false;
    }

    @Override
    protected void func_205702_a() {
        this.toggleRecipesBtn.initTextureValues(152, 41, 28, 18, RECIPE_BOOK);
    }

    @Override
    public void removed() {
        this.searchBar = null;
        this.currentTab = null;
        this.mc.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public int updateScreenPosition(boolean widthTooNarrow, int width, int xSize) {
        int guiLeft;
        if (this.isVisible() && !widthTooNarrow) {
            guiLeft = 177 + (width - xSize - 200) / 2;
        } else {
            guiLeft = (width - xSize) / 2;
        }

        return guiLeft;
    }

    @Override
    public void toggleVisibility() {
        this.setVisible(!this.isVisible());
    }

    @Override
    public boolean isVisible() {
//        return this.recipeBook.isGuiOpen();
        return this.recipeBook.func_242142_a(this.container.func_241850_m());
    }


    @Override
    protected void setVisible(boolean visible) {
//        this.recipeBook.setGuiOpen(visible);
//        if (!visible) {
//            this.recipeBookPage.setInvisible();
//        }
//        this.sendUpdateSettings();

        this.recipeBook.func_242143_a(this.field_201522_g.func_241850_m(), visible);
        if (!visible) {
            this.recipeBookPage.setInvisible();
        }

        this.sendUpdateSettings();
    }


    @Override
    public void slotClicked(@Nullable Slot slotIn) {
        if (slotIn != null && slotIn.slotNumber < this.container.getSize()) {
            this.ghostRecipe.clear();
            if (this.isVisible()) {
                this.updateStackedContents();
            }
        }
    }

    private void updateCollections(boolean p_193003_1_) {
        List<RecipeList> list = this.recipeBook.getRecipes(this.currentTab.func_201503_d());
        list.forEach((p_193944_1_) -> {
            p_193944_1_.canCraft(this.stackedContents, this.container.getWidth(), this.container.getHeight(), this.recipeBook);
        });
        List<RecipeList> list1 = Lists.newArrayList(list);
        list1.removeIf((p_193952_0_) -> {
            return !p_193952_0_.isNotEmpty();
        });
        list1.removeIf((p_193953_0_) -> {
            return !p_193953_0_.containsValidRecipes();
        });
        String s = this.searchBar.getText();
        if (!s.isEmpty()) {
            ObjectSet<RecipeList> objectset = new ObjectLinkedOpenHashSet<>(this.mc.getSearchTree(SearchTreeManager.RECIPES).search(s.toLowerCase(Locale.ROOT)));
            list1.removeIf((p_193947_1_) -> {
                return !objectset.contains(p_193947_1_);
            });
        }

        if (this.recipeBook.func_242141_a/*isFilteringCraftable*/(this.container)) {
            list1.removeIf((p_193958_0_) -> !p_193958_0_.containsCraftableRecipes());
        }

        this.recipeBookPage.updateLists(list1, p_193003_1_);
    }

    private void updateTabs() {
        int i = (this.width - 147) / 2 - this.xOffset - 30;
        int j = (this.height - 166) / 2 + 3;
        int k = 27;
        int l = 0;

        for(MPSRecipeTabToggleWidget MPSRecipeTabToggleWidget : this.recipeTabs) {
            RecipeBookCategories recipebookcategories = MPSRecipeTabToggleWidget.func_201503_d();
            if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
                if (MPSRecipeTabToggleWidget.func_199500_a(this.recipeBook)) {
                    MPSRecipeTabToggleWidget.setPosition(i, j + 27 * l++);
                    MPSRecipeTabToggleWidget.startAnimation(this.mc);
                }
            } else {
                MPSRecipeTabToggleWidget.visible = true;
                MPSRecipeTabToggleWidget.setPosition(i, j + 27 * l++);
            }
        }
    }

    @Override
    public void tick() {
        if (this.isVisible()) {
            if (this.timesInventoryChanged != this.mc.player.inventory.getTimesChanged()) {
                this.updateStackedContents();
                this.timesInventoryChanged = this.mc.player.inventory.getTimesChanged();
            }
        }
    }

    private void updateStackedContents() {
        this.stackedContents.clear();
        this.mc.player.inventory.accountStacks(this.stackedContents);
        this.container.fillStackedContents(this.stackedContents);
        this.updateCollections(false);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            outerFrame.draw(matrixStack, 0);
            innerFrame.draw(matrixStack, 0);

            RenderHelper.enableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.pushMatrix();

            this.mc.getTextureManager().bindTexture(SEARCH_ICON);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int x = (this.width - 147) / 2 - this.xOffset;
            int y = (this.height - 166) / 2;

            this.blit(matrixStack, x + 9, y + 11, 0, 0, 16, 16, 16, 16);

            this.searchBar.render(matrixStack, mouseX, mouseY, partialTicks);
            RenderHelper.disableStandardItemLighting();

            // move this up to before the outer frame once the texture is no longer needed
            for(MPSRecipeTabToggleWidget MPSRecipeTabToggleWidget : this.recipeTabs) {
                MPSRecipeTabToggleWidget.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            this.toggleRecipesBtn.render(matrixStack, mouseX, mouseY, partialTicks);
            this.recipeBookPage.func_238927_a_(matrixStack, x, y, mouseX, mouseY, partialTicks);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void /*renderTooltip*/func_238924_c_(MatrixStack matrixStack, int p_191876_1_, int p_191876_2_, int p_191876_3_, int p_191876_4_) {
        if (this.isVisible()) {
            this.recipeBookPage.func_238926_a_(matrixStack, p_191876_3_, p_191876_4_);
            if (this.toggleRecipesBtn.isHovered()) {
                ITextComponent itextcomponent = this.func_230478_f_();
                if (this.mc.currentScreen != null) {
                    this.mc.currentScreen.renderTooltip(matrixStack, itextcomponent, p_191876_3_, p_191876_4_);
                }
            }

            this.renderGhostRecipeTooltip(matrixStack, p_191876_1_, p_191876_2_, p_191876_3_, p_191876_4_);
        }
    }

//    @Override
//    protected String func_205703_f() {
//        return I18n.format(this.toggleRecipesBtn.isStateTriggered() ? "gui.recipebook.toggleRecipes.craftable" : "gui.recipebook.toggleRecipes.all");
//    }

    private ITextComponent func_230478_f_() {
        return this.toggleRecipesBtn.isStateTriggered() ? this.func_230479_g_() : field_243411_k;
    }


    private void renderGhostRecipeTooltip(MatrixStack matrixStack, int p_193015_1_, int p_193015_2_, int p_193015_3_, int p_193015_4_) {
        ItemStack itemstack = null;

        for(int i = 0; i < this.ghostRecipe.size(); ++i) {
            GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.get(i);
            int j = ghostrecipe$ghostingredient.getX() + p_193015_1_;
            int k = ghostrecipe$ghostingredient.getY() + p_193015_2_;
            if (p_193015_3_ >= j && p_193015_4_ >= k && p_193015_3_ < j + 16 && p_193015_4_ < k + 16) {
                itemstack = ghostrecipe$ghostingredient.getItem();
            }
        }

//        if (itemstack != null && this.mc.currentScreen != null) {
//            this.mc.currentScreen.renderTooltip(this.mc.currentScreen.getTooltipFromItem(itemstack), p_193015_3_, p_193015_4_);
//        }

        if (itemstack != null && this.mc.currentScreen != null) {
            this.mc.currentScreen.func_243308_b(matrixStack, this.mc.currentScreen.getTooltipFromItem(itemstack), p_193015_3_, p_193015_4_);
        }
    }

//    @Override
//    public void renderGhostRecipe(int guiLeft, int guiTop, boolean p_191864_3_, float partialTicks) {
//        this.ghostRecipe.render(this.mc, guiLeft, guiTop, p_191864_3_, partialTicks);
//    }
    @Override
    public void func_230477_a_(MatrixStack matrixStack, int guiLeft, int guiTop, boolean p_230477_4_, float partialTicks) {
        this.ghostRecipe.func_238922_a_(matrixStack, this.mc, guiLeft, guiTop, p_230477_4_, partialTicks);
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.recipeBookPage./*mouseClicked */func_198955_a(mouseX, mouseY, button, (this.width - 147) / 2 - this.xOffset, (this.height - 166) / 2, 147, 166)) {
                IRecipe<?> irecipe = this.recipeBookPage.getLastClickedRecipe();
                RecipeList recipelist = this.recipeBookPage.getLastClickedRecipeList();
                if (irecipe != null && recipelist != null) {
                    if (!recipelist.isCraftable(irecipe) && this.ghostRecipe.getRecipe() == irecipe) {
                        return false;
                    }

                    this.ghostRecipe.clear();
                    this.mc.playerController.sendPlaceRecipePacket(this.mc.player.openContainer.windowId, irecipe, Screen.hasShiftDown());
                    if (!this.isOffsetNextToMainGUI()) {
                        this.setVisible(false);
                    }
                }

                return true;
            } else if (this.searchBar.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (this.toggleRecipesBtn.mouseClicked(mouseX, mouseY, button)) {
                boolean flag = this.toggleCraftableFilter();
                this.toggleRecipesBtn.setStateTriggered(flag);
                this.sendUpdateSettings();
                this.updateCollections(false);
                return true;
            } else {
                for(MPSRecipeTabToggleWidget MPSRecipeTabToggleWidget : this.recipeTabs) {
                    if (MPSRecipeTabToggleWidget.mouseClicked(mouseX, mouseY, button)) {
                        if (this.currentTab != MPSRecipeTabToggleWidget) {
                            this.currentTab.setStateTriggered(false);
                            this.currentTab = MPSRecipeTabToggleWidget;
                            this.currentTab.setStateTriggered(true);
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

    protected boolean toggleCraftableFilter() {
        RecipeBookCategory recipebookcategory = this.field_201522_g.func_241850_m();
        boolean flag = !this.recipeBook.func_242145_b(recipebookcategory);
        this.recipeBook.func_242146_b(recipebookcategory, flag);
        return flag;
    }

//    @Override
//    protected boolean toggleCraftableFilter() {
//        boolean flag = !this.recipeBook.isFilteringCraftable();
//        this.recipeBook.setFilteringCraftable(flag);
//        return flag;
//    }

    @Override
    public boolean func_195604_a(double mouseX, double mouseY, int parentGuiLeft, int parentGuiTop, int parentXSize, int parentYSize, int button) {
        if (!this.isVisible()) {
            return true;
        } else {
            boolean flag = mouseX < (double)parentGuiLeft || mouseY < (double)parentGuiTop || mouseX >= (double)(parentGuiLeft + parentXSize) || mouseY >= (double)(parentGuiTop + parentYSize);
            boolean flag1 = (double)(parentGuiLeft - 147) < mouseX && mouseX < (double)parentGuiLeft && (double)parentGuiTop < mouseY && mouseY < (double)(parentGuiTop + parentYSize);
            return flag && !flag1 && !this.currentTab.isHovered();
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        this.field_199738_u = false;
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (p_keyPressed_1_ == 256 && !this.isOffsetNextToMainGUI()) {
                this.setVisible(false);
                return true;
            } else if (this.searchBar.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
                this.updateSearch();
                return true;
            } else if (this.searchBar.isFocused() && this.searchBar.getVisible() && p_keyPressed_1_ != 256) {
                return true;
            } else if (this.mc.gameSettings.keyBindChat.matchesKey(p_keyPressed_1_, p_keyPressed_2_) && !this.searchBar.isFocused()) {
                this.field_199738_u = true;
                this.searchBar.setFocused2(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        this.field_199738_u = false;
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        if (this.field_199738_u) {
            return false;
        } else if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.searchBar.charTyped(p_charTyped_1_, p_charTyped_2_)) {
                this.updateSearch();
                return true;
            } else {
                return super.charTyped(p_charTyped_1_, p_charTyped_2_); // fixme NPE
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    private void updateSearch() {
        String s = this.searchBar.getText().toLowerCase(Locale.ROOT);
        this.pirateRecipe(s);
        if (!s.equals(this.lastSearch)) {
            this.updateCollections(false);
            this.lastSearch = s;
        }
    }

    /**
     * "Check if we should activate the pirate speak easter egg"
     */
    private void pirateRecipe(String text) {
        if ("excitedze".equals(text)) {
            LanguageManager languagemanager = this.mc.getLanguageManager();
            Language language = languagemanager.getLanguage("en_pt");
            if (languagemanager.getCurrentLanguage().compareTo(language) == 0) {
                return;
            }

            languagemanager.setCurrentLanguage(language);
            this.mc.gameSettings.language = language.getCode();
            net.minecraftforge.client.ForgeHooksClient.refreshResources(this.mc, net.minecraftforge.resource.VanillaResourceType.LANGUAGES);
            this.mc.gameSettings.saveOptions();
        }
    }


    private boolean isOffsetNextToMainGUI() {
        return this.xOffset == 86;
    }

    @Override
    public void recipesUpdated() {
        System.out.println("recipesUpdated");
        this.updateTabs();
        if (this.isVisible()) {
            this.updateCollections(false);
        }
    }

    @Override
    public void recipesShown(List<IRecipe<?>> recipes) {
        System.out.println("recipesShown");

        for(IRecipe<?> irecipe : recipes) {
            this.mc.player.removeRecipeHighlight(irecipe);
        }
    }

    /*
        called from: net.minecraft.client.network.play.ClientPlayNetHandler.handlePlaceGhostRecipe
           which is called from: net.minecraft.network.play.server.SPlaceGhostRecipePacket.processPacket (recipes referenced by resource location)


     */
    /*
        sent from this chain...


        net.minecraft.client.multiplayer.PlayerController.func_203413_a
        net.minecraft.network.play.ServerPlayNetHandler.processPlaceRecipe
        net.minecraft.inventory.container.RecipeBookContainer.func_217056_a
        net.minecraft.item.crafting.ServerRecipePlacer.place
        net.minecraft.client.network.play.ClientPlayNetHandler.handlePlaceGhostRecipe
     */
    @Override
    public void setupGhostRecipe(IRecipe<?> recipe, List<Slot> slots) {
        System.out.println("setupGhostRecipe");

        ItemStack itemstack = recipe.getRecipeOutput();
        this.ghostRecipe.setRecipe(recipe);
        this.ghostRecipe.addIngredient(Ingredient.fromStacks(itemstack), (slots.get(0)).xPos, (slots.get(0)).yPos);

        // IRecipePlacer
        this.placeRecipe(
                this.container.getWidth(), // grid width
                this.container.getHeight(), // grid height
                this.container.getOutputSlot(), //  usually the first slot added to the list during container init
                recipe,
                recipe.getIngredients().iterator(),
                0);
    }

    /** IRecipePlacer ----------------------------------- */
    @Override
    public void setSlotContents(Iterator<Ingredient> ingredients, int slotIn, int maxAmount, int y, int x) {
        Ingredient ingredient = ingredients.next();
        if (!ingredient.hasNoMatchingItems()) {
            Slot slot = this.container.inventorySlots.get(slotIn);
            this.ghostRecipe.addIngredient(ingredient, slot.xPos, slot.yPos);
        } else {
            System.out.println("slot: " + slotIn);
            System.out.println("missing ingredients");
        }
    }

    @Override
    protected void sendUpdateSettings() {
        System.out.println("sending update settings");


//        if (this.mc.getConnection() != null) {
//            this.mc.getConnection().sendPacket(new CRecipeInfoPacket(this.recipeBook.isGuiOpen(), this.recipeBook.isFilteringCraftable(), this.recipeBook.isFurnaceGuiOpen(), this.recipeBook.isFurnaceFilteringCraftable(), this.recipeBook.func_216758_e(), this.recipeBook.func_216761_f()));
//        }

        if (this.mc.getConnection() != null) {
            RecipeBookCategory recipebookcategory = this.field_201522_g.func_241850_m();
            boolean flag = this.recipeBook.func_242139_a().func_242151_a(recipebookcategory);
            boolean flag1 = this.recipeBook.func_242139_a().func_242158_b(recipebookcategory);
            this.mc.getConnection().sendPacket(new CUpdateRecipeBookStatusPacket(recipebookcategory, flag, flag1));
        }
    }
}
