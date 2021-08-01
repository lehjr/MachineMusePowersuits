package com.github.lehjr.powersuits.dev.crafting.client.gui.craftinstallsalvage;

import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipePlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * FIXME: this whole thing looks like a bad wrapper for the Recipe Overlay Gui... too many layers of complexity
 *
 */


@OnlyIn(Dist.CLIENT)
public class MPSRecipeBookPage extends AbstractGui implements IRenderable, IGuiEventListener, IGuiFrame {
    /** background rect and holder of coordinates */
    DrawableTile backgroundRect = new DrawableTile(0,0,0,0);
    /** whether or not this can be seen */
    boolean isVisible = true;
    /** whether or not this is enabled */
    boolean isEnabled = true;
    /** recipe widget buttons */
    protected final List<MPSRecipeWidget> buttons = Lists.newArrayListWithCapacity(20);
    /** apparently the button the mouse cursor is over, if any */
    protected MPSRecipeWidget hoveredButton;
    /** Minecraft instance */
    protected Minecraft minecraft;



    /** WHY???????? */
    protected final MPSRecipeOverlayGui overlay = new MPSRecipeOverlayGui();

    protected final List<IRecipeUpdateListener> showListeners = Lists.newArrayList();

    protected List<RecipeList> recipeCollections;






    /** STUFF TO GET REPLACED ----------------------------------------------------------------------------------------- */


    /** navigation buttons */
    protected ToggleWidget forwardButton;
    protected ToggleWidget backButton;
    /** number of recipe pages */
    protected int totalPages;
    protected int currentPage;


    protected RecipeBook recipeBook;
    protected IRecipe<?> lastClickedRecipe;
    protected RecipeList lastClickedRecipeCollection;





    public MPSRecipeBookPage() {
        // FIXME: why adding 20 buttons? should be limited to number of recipes

        for(int i = 0; i < 20; ++i) {
            this.buttons.add(new MPSRecipeWidget());
        }
        this.backgroundRect.setBackgroundColour(Colour.BLACK);
        this.backgroundRect.setBottomBorderColour(Colour.DARK_GREY);
        this.backgroundRect.setTopBorderColour(Colour.DARK_GREY);
    }


    // Why does Minecraft instance get initialized here when it can be done in the constructor?
    public void init(Minecraft minecraft, int posLeft, int posTop) {
        this.minecraft = minecraft;
        this.recipeBook = minecraft.player.getRecipeBook();

        for(int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).setPosition(posLeft + 11 + 25 * (i % 5), posTop + 31 + 25 * (i / 5));
        }

        this.forwardButton = new ToggleWidget(posLeft + 93, posTop + 137, 12, 17, false);
        this.forwardButton.initTextureValues(1, 208, 13, 18, MPSRecipeBookGui.RECIPE_BOOK_LOCATION);
        this.backButton = new ToggleWidget(posLeft + 38, posTop + 137, 12, 17, true);
        this.backButton.initTextureValues(1, 208, 13, 18, MPSRecipeBookGui.RECIPE_BOOK_LOCATION);
    }

    public void addListener(MPSRecipeBookGui recipeBookGui) {
        this.showListeners.remove(recipeBookGui);
        this.showListeners.add(recipeBookGui);
    }

    public void updateCollections(List<RecipeList> recipeLists, boolean p_194192_2_) {
        this.recipeCollections = recipeLists;
        this.totalPages = (int)Math.ceil((double)recipeLists.size() / 20.0D);
        if (this.totalPages <= this.currentPage || p_194192_2_) {
            this.currentPage = 0;
        }

        this.updateButtonsForPage();
    }

    protected void updateButtonsForPage() {
        int i = 20 * this.currentPage;
        for(int j = 0; j < this.buttons.size(); ++j) {
            MPSRecipeWidget recipewidget = this.buttons.get(j);
            if (i + j < this.recipeCollections.size()) {
                RecipeList recipelist = this.recipeCollections.get(i + j);
                recipewidget.init(recipelist, this);
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

    public void render(MatrixStack matrixStack, int guiLeft, int guiTop, int mouseX, int mouseY, float frameTime) {
        if (this.totalPages > 1) {
            String s = this.currentPage + 1 + "/" + this.totalPages;
            int i = this.minecraft.font.width(s);
            this.minecraft.font.draw(matrixStack, s, (float)(guiLeft - i / 2 + 73), (float)(guiTop + 141), -1);
        }

        this.hoveredButton = null;

        for(MPSRecipeWidget recipewidget : this.buttons) {
            recipewidget.render(matrixStack, mouseX, mouseY, frameTime);
            if (recipewidget.visible && recipewidget.isHovered()) {
                this.hoveredButton = recipewidget;
            }
        }

        this.backButton.render(matrixStack, mouseX, mouseY, frameTime);
        this.forwardButton.render(matrixStack, mouseX, mouseY, frameTime);
        this.overlay.render(matrixStack, mouseX, mouseY, frameTime);
    }

    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.minecraft.screen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
            this.minecraft.screen.renderComponentTooltip(matrixStack, this.hoveredButton.getTooltipText(this.minecraft.screen), mouseX, mouseY);
        }
    }

    @Nullable
    public IRecipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    @Nullable
    public RecipeList getLastClickedRecipeCollection() {
        return this.lastClickedRecipeCollection;
    }

    public void setInvisible() {
        this.overlay.setVisible(false);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, int left, int top, int right, int bottom) {
        this.lastClickedRecipe = null;
        this.lastClickedRecipeCollection = null;
        if (this.overlay.isVisible()) {
            if (this.overlay.mouseClicked(mouseX, mouseY, button)) {
                this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
                this.lastClickedRecipeCollection = this.overlay.getRecipeCollection();
            } else {
                this.overlay.setVisible(false);
            }

            return true;
        } else if (this.forwardButton.mouseClicked(mouseX, mouseY, button)) {
            ++this.currentPage;
            this.updateButtonsForPage();
            return true;
        } else if (this.backButton.mouseClicked(mouseX, mouseY, button)) {
            --this.currentPage;
            this.updateButtonsForPage();
            return true;
        } else {
            for(MPSRecipeWidget recipewidget : this.buttons) {
                if (recipewidget.mouseClicked(mouseX, mouseY, button)) {
                    if (button == 0) {
                        this.lastClickedRecipe = recipewidget.getRecipe();
                        this.lastClickedRecipeCollection = recipewidget.getCollection();
                    } else if (button == 1 && !this.overlay.isVisible() && !recipewidget.isOnlyOption()) {
                        this.overlay.init(this.minecraft, recipewidget.getCollection(), recipewidget.x, recipewidget.y, left + right / 2, top + 13 + bottom / 2, (float)recipewidget.getWidth());
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public void recipesShown(List<IRecipe<?>> iRecipes) {
        for(IRecipeUpdateListener irecipeupdatelistener : this.showListeners) {
            irecipeupdatelistener.recipesShown(iRecipes);
        }
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }

    public RecipeBook getRecipeBook() {
        return this.recipeBook;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public IRect getRect() {
        return this.backgroundRect;
    }

    @Override
    public void update(double v, double v1) {

    }

    @Override
    public void render(MatrixStack matrixStack, int i, int i1, float v) {
        backgroundRect.render(matrixStack, i, i1, v);
    }



    class MPSRecipeOverlayGui extends AbstractGui implements IRenderable, IGuiEventListener {
        private final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
        private final List<RecipeButtonWidget> recipeButtons = Lists.newArrayList();
        private boolean isVisible;
        private int x;
        private int y;
        private Minecraft minecraft;
        private RecipeList collection;
        private IRecipe<?> lastRecipeClicked;
        private float time;
        private boolean isFurnaceMenu;

        public void init(Minecraft minecraft, RecipeList recipeList, int posX, int posY, int p_201703_5_, int p_201703_6_, float p_201703_7_) {
            this.minecraft = minecraft;
            this.collection = recipeList;
            if (minecraft.player.containerMenu instanceof AbstractFurnaceContainer) {
                this.isFurnaceMenu = true;
            }

            boolean flag = minecraft.player.getRecipeBook().isFiltering((RecipeBookContainer)minecraft.player.containerMenu);
            List<IRecipe<?>> list = recipeList.getDisplayRecipes(true);
            List<IRecipe<?>> list1 = flag ? Collections.emptyList() : recipeList.getDisplayRecipes(false);
            int i = list.size();
            int j = i + list1.size();
            int k = j <= 16 ? 4 : 5;
            int l = (int)Math.ceil((double)((float)j / (float)k));
            this.x = posX;
            this.y = posY;
            int i1 = 25;
            float f = (float)(this.x + Math.min(j, k) * 25);
            float f1 = (float)(p_201703_5_ + 50);
            if (f > f1) {
                this.x = (int)((float)this.x - p_201703_7_ * (float)((int)((f - f1) / p_201703_7_)));
            }

            float f2 = (float)(this.y + l * 25);
            float f3 = (float)(p_201703_6_ + 50);
            if (f2 > f3) {
                this.y = (int)((float)this.y - p_201703_7_ * (float) MathHelper.ceil((f2 - f3) / p_201703_7_));
            }

            float f4 = (float)this.y;
            float f5 = (float)(p_201703_6_ - 100);
            if (f4 < f5) {
                this.y = (int)((float)this.y - p_201703_7_ * (float)MathHelper.ceil((f4 - f5) / p_201703_7_));
            }

            this.isVisible = true;
            this.recipeButtons.clear();

            for(int j1 = 0; j1 < j; ++j1) {
                boolean flag1 = j1 < i;
                IRecipe<?> irecipe = flag1 ? list.get(j1) : list1.get(j1 - i);
                int k1 = this.x + 4 + 25 * (j1 % k);
                int l1 = this.y + 5 + 25 * (j1 / k);
                if (this.isFurnaceMenu) {
                    this.recipeButtons.add(new FurnaceRecipeButtonWidget(k1, l1, irecipe, flag1));
                } else {
                    this.recipeButtons.add(new RecipeButtonWidget(k1, l1, irecipe, flag1));
                }
            }

            this.lastRecipeClicked = null;
        }

        public boolean changeFocus(boolean p_231049_1_) {
            return false;
        }

        public RecipeList getRecipeCollection() {
            return this.collection;
        }

        public IRecipe<?> getLastRecipeClicked() {
            return this.lastRecipeClicked;
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button != 0) {
                return false;
            } else {
                for(RecipeButtonWidget recipebuttonwidget : this.recipeButtons) {
                    if (recipebuttonwidget.mouseClicked(mouseX, mouseY, button)) {
                        this.lastRecipeClicked = recipebuttonwidget.recipe;
                        return true;
                    }
                }

                return false;
            }
        }

        public boolean isMouseOver(double mouseX, double mouseY) {
            return false;
        }

        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
            if (this.isVisible) {
                this.time += frameTime;
                RenderSystem.enableBlend();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(RECIPE_BOOK_LOCATION);
                RenderSystem.pushMatrix();
                RenderSystem.translatef(0.0F, 0.0F, 170.0F);
                int i = this.recipeButtons.size() <= 16 ? 4 : 5;
                int j = Math.min(this.recipeButtons.size(), i);
                int k = MathHelper.ceil((float)this.recipeButtons.size() / (float)i);
                int l = 24;
                int i1 = 4;
                int j1 = 82;
                int k1 = 208;
                this.nineInchSprite(matrixStack, j, k, 24, 4, 82, 208);
                RenderSystem.disableBlend();

                for(RecipeButtonWidget recipeoverlaygui$recipebuttonwidget : this.recipeButtons) {
                    recipeoverlaygui$recipebuttonwidget.render(matrixStack, mouseX, mouseY, frameTime);
                }

                RenderSystem.popMatrix();
            }
        }

        private void nineInchSprite(MatrixStack matrixStack, int p_238923_2_, int p_238923_3_, int p_238923_4_, int p_238923_5_, int p_238923_6_, int p_238923_7_) {
            this.blit(matrixStack, this.x, this.y, p_238923_6_, p_238923_7_, p_238923_5_, p_238923_5_);
            this.blit(matrixStack, this.x + p_238923_5_ * 2 + p_238923_2_ * p_238923_4_, this.y, p_238923_6_ + p_238923_4_ + p_238923_5_, p_238923_7_, p_238923_5_, p_238923_5_);
            this.blit(matrixStack, this.x, this.y + p_238923_5_ * 2 + p_238923_3_ * p_238923_4_, p_238923_6_, p_238923_7_ + p_238923_4_ + p_238923_5_, p_238923_5_, p_238923_5_);
            this.blit(matrixStack, this.x + p_238923_5_ * 2 + p_238923_2_ * p_238923_4_, this.y + p_238923_5_ * 2 + p_238923_3_ * p_238923_4_, p_238923_6_ + p_238923_4_ + p_238923_5_, p_238923_7_ + p_238923_4_ + p_238923_5_, p_238923_5_, p_238923_5_);

            for(int i = 0; i < p_238923_2_; ++i) {
                this.blit(matrixStack, this.x + p_238923_5_ + i * p_238923_4_, this.y, p_238923_6_ + p_238923_5_, p_238923_7_, p_238923_4_, p_238923_5_);
                this.blit(matrixStack, this.x + p_238923_5_ + (i + 1) * p_238923_4_, this.y, p_238923_6_ + p_238923_5_, p_238923_7_, p_238923_5_, p_238923_5_);

                for(int j = 0; j < p_238923_3_; ++j) {
                    if (i == 0) {
                        this.blit(matrixStack, this.x, this.y + p_238923_5_ + j * p_238923_4_, p_238923_6_, p_238923_7_ + p_238923_5_, p_238923_5_, p_238923_4_);
                        this.blit(matrixStack, this.x, this.y + p_238923_5_ + (j + 1) * p_238923_4_, p_238923_6_, p_238923_7_ + p_238923_5_, p_238923_5_, p_238923_5_);
                    }

                    this.blit(matrixStack, this.x + p_238923_5_ + i * p_238923_4_, this.y + p_238923_5_ + j * p_238923_4_, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_4_, p_238923_4_);
                    this.blit(matrixStack, this.x + p_238923_5_ + (i + 1) * p_238923_4_, this.y + p_238923_5_ + j * p_238923_4_, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_5_, p_238923_4_);
                    this.blit(matrixStack, this.x + p_238923_5_ + i * p_238923_4_, this.y + p_238923_5_ + (j + 1) * p_238923_4_, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_4_, p_238923_5_);
                    this.blit(matrixStack, this.x + p_238923_5_ + (i + 1) * p_238923_4_ - 1, this.y + p_238923_5_ + (j + 1) * p_238923_4_ - 1, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_5_ + 1, p_238923_5_ + 1);
                    if (i == p_238923_2_ - 1) {
                        this.blit(matrixStack, this.x + p_238923_5_ * 2 + p_238923_2_ * p_238923_4_, this.y + p_238923_5_ + j * p_238923_4_, p_238923_6_ + p_238923_4_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_5_, p_238923_4_);
                        this.blit(matrixStack, this.x + p_238923_5_ * 2 + p_238923_2_ * p_238923_4_, this.y + p_238923_5_ + (j + 1) * p_238923_4_, p_238923_6_ + p_238923_4_ + p_238923_5_, p_238923_7_ + p_238923_5_, p_238923_5_, p_238923_5_);
                    }
                }

                this.blit(matrixStack, this.x + p_238923_5_ + i * p_238923_4_, this.y + p_238923_5_ * 2 + p_238923_3_ * p_238923_4_, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_4_ + p_238923_5_, p_238923_4_, p_238923_5_);
                this.blit(matrixStack, this.x + p_238923_5_ + (i + 1) * p_238923_4_, this.y + p_238923_5_ * 2 + p_238923_3_ * p_238923_4_, p_238923_6_ + p_238923_5_, p_238923_7_ + p_238923_4_ + p_238923_5_, p_238923_5_, p_238923_5_);
            }

        }

        public void setVisible(boolean p_192999_1_) {
            this.isVisible = p_192999_1_;
        }

        public boolean isVisible() {
            return this.isVisible;
        }

        @OnlyIn(Dist.CLIENT)
        class FurnaceRecipeButtonWidget extends RecipeButtonWidget {
            public FurnaceRecipeButtonWidget(int p_i48747_2_, int p_i48747_3_, IRecipe<?> p_i48747_4_, boolean p_i48747_5_) {
                super(p_i48747_2_, p_i48747_3_, p_i48747_4_, p_i48747_5_);
            }

            protected void calculateIngredientsPositions(IRecipe<?> p_201505_1_) {
                ItemStack[] aitemstack = p_201505_1_.getIngredients().get(0).getItems();
                this.ingredientPos.add(new RecipeButtonWidget.Child(10, 10, aitemstack));
            }
        }

        @OnlyIn(Dist.CLIENT)
        class RecipeButtonWidget extends Widget implements IRecipePlacer<Ingredient> {
            private final IRecipe<?> recipe;
            private final boolean isCraftable;
            protected final List<RecipeButtonWidget.Child> ingredientPos = Lists.newArrayList();

            public RecipeButtonWidget(int p_i47594_2_, int p_i47594_3_, IRecipe<?> p_i47594_4_, boolean p_i47594_5_) {
                super(p_i47594_2_, p_i47594_3_, 200, 20, StringTextComponent.EMPTY);
                this.width = 24;
                this.height = 24;
                this.recipe = p_i47594_4_;
                this.isCraftable = p_i47594_5_;
                this.calculateIngredientsPositions(p_i47594_4_);
            }

            protected void calculateIngredientsPositions(IRecipe<?> p_201505_1_) {
                this.placeRecipe(3, 3, -1, p_201505_1_, p_201505_1_.getIngredients().iterator(), 0);
            }

            public void addItemToSlot(Iterator<Ingredient> ingredientIterator, int p_201500_2_, int p_201500_3_, int p_201500_4_, int p_201500_5_) {
                ItemStack[] aitemstack = ingredientIterator.next().getItems();
                if (aitemstack.length != 0) {
                    this.ingredientPos.add(new RecipeButtonWidget.Child(3 + p_201500_5_ * 7, 3 + p_201500_4_ * 7, aitemstack));
                }

            }

            public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                RenderSystem.enableAlphaTest();
                minecraft.getTextureManager().bind(RECIPE_BOOK_LOCATION);
                int i = 152;
                if (!this.isCraftable) {
                    i += 26;
                }

                int j = isFurnaceMenu ? 130 : 78;
                if (this.isHovered()) {
                    j += 26;
                }

                this.blit(matrixStack, this.x, this.y, i, j, this.width, this.height);

                for(RecipeButtonWidget.Child recipebuttonwidgetchild : this.ingredientPos) {
                    RenderSystem.pushMatrix();
                    float f = 0.42F;
                    int k = (int)((float)(this.x + recipebuttonwidgetchild.x) / 0.42F - 3.0F);
                    int l = (int)((float)(this.y + recipebuttonwidgetchild.y) / 0.42F - 3.0F);
                    RenderSystem.scalef(0.42F, 0.42F, 1.0F);
                    minecraft.getItemRenderer().renderAndDecorateItem(recipebuttonwidgetchild.ingredients[MathHelper.floor(time / 30.0F) % recipebuttonwidgetchild.ingredients.length], k, l);
                    RenderSystem.popMatrix();
                }

                RenderSystem.disableAlphaTest();
            }

            @OnlyIn(Dist.CLIENT)
            public class Child {
                public final ItemStack[] ingredients;
                public final int x;
                public final int y;

                public Child(int posX, int posY, ItemStack[] ingredients) {
                    this.x = posX;
                    this.y = posY;
                    this.ingredients = ingredients;
                }
            }
        }
    }










    @Override
    public float getZLevel() {
        return this.overlay.getBlitOffset();
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        this.overlay.setBlitOffset((int)zLevel);
        return this;
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
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
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
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
    public IRect setUL(MusePoint2D upperLeft) {
        return backgroundRect.setUL(upperLeft);
    }

    @Override
    public IRect setWH(MusePoint2D widthHeight) {
        return backgroundRect.setWH(widthHeight);
    }

    @Override
    public IRect setLeft(double left) {
        return backgroundRect.setLeft(left);
    }

    @Override
    public IRect setRight(double right) {
        return backgroundRect.setRight(right);
    }

    @Override
    public IRect setTop(double top) {
        return backgroundRect.setTop(top);
    }

    @Override
    public IRect setBottom(double bottomn) {
        return backgroundRect.setBottom(bottomn);
    }

    @Override
    public IRect setWidth(double width) {
        return backgroundRect.setWidth(width);
    }

    @Override
    public IRect setHeight(double height) {
        return backgroundRect.setHeight(height);
    }

    @Override
    public void move(MusePoint2D xyAmount) {
        backgroundRect.move(xyAmount);
    }

    @Override
    public void move(double xAmount, double yAmount) {
        backgroundRect.move(xAmount, yAmount);
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
    public IRect setMeLeftOf(IRect iRect) {
        return backgroundRect.setMeLeftOf(iRect);
    }

    @Override
    public IRect setMeRightOf(IRect iRect) {
        return backgroundRect.setMeRightOf(iRect);
    }

    @Override
    public IRect setMeAbove(IRect iRect) {
        return backgroundRect.setMeAbove(iRect);
    }

    @Override
    public IRect setMeBelow(IRect iRect) {
        return backgroundRect.setMeBelow(iRect);
    }

    @Override
    public void setOnInit(IInit iInit) {

    }

    @Override
    public void onInit() {

    }
}