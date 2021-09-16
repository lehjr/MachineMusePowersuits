package com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.recipebook.RecipeWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;

public class MPSRecipeWidget extends RecipeWidget implements IGuiFrame {
    RecipeWidgetTile tile = new RecipeWidgetTile();
    Minecraft minecraft;
    @Nonnull ItemStack containerStack;
    boolean isEnabled = true;
    boolean isVisible = true;


    public MPSRecipeWidget() {
        super(); // initialize the base class before setting everything else
        tile.setWidth(getWidth());
        tile.setHeight(getHeight());
        this.minecraft = Minecraft.getInstance();
    }

    public void init(RecipeList recipeList, MPSRecipeBookGui.TestRecipeBookPage recipeBookPage, @Nonnull ItemStack containerStack) {
        this.containerStack = containerStack;
        this.collection = recipeList;
        this.menu = (RecipeBookContainer<?>) minecraft.player.containerMenu;
        this.book = this.minecraft.player.getRecipeBook();

        List<IRecipe<?>> list = recipeList.getRecipes(this.book.isFiltering(this.menu));

        for(IRecipe<?> irecipe : list) {
            if (this.book.willHighlight(irecipe)) {
                recipeBookPage.recipesShown(list);
                break;
            }
        }
    }

    boolean hasInstalled(@Nonnull ItemStack module) {
        return this.containerStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .map(iItemHandler -> iItemHandler.isModuleInstalled(module)).orElse(false);
    }


    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (!Screen.hasControlDown()) {
            this.time += frameTime;
        }

        if (!Screen.hasControlDown()) {
            this.time += frameTime;
        }
        List<IRecipe<?>> list = this.getOrderedRecipes();
        this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
        ItemStack itemstack = list.get(this.currentIndex).getResultItem();

        if (hasInstalled(itemstack)) {
            tile.setAleadyInstalled();
        } else if (!this.collection.hasCraftable()) {
            tile.setDisabled();
        } else {
            tile.setEnabled();
        }
        tile.render(matrixStack, mouseX, mouseY, frameTime);

        int k = 4;

        if (this.collection.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
            minecraft.getItemRenderer().renderAndDecorateItem(itemstack, this.x + k + 1, this.y + k + 1);
            --k;
        }

        minecraft.getItemRenderer().renderAndDecorateFakeItem(itemstack, this.x + k, this.y + k);
    }

    @Override
    public boolean isOnlyOption() {
        return this.getOrderedRecipes().size() == 1;
    }

    @Override
    public IRecipe<?> getRecipe() {
        List<IRecipe<?>> collection = this.getOrderedRecipes();
        return collection.get(this.currentIndex);
    }

    @Override
    public List<ITextComponent> getTooltipText(Screen screen) {
        ItemStack itemstack = this.getOrderedRecipes().get(this.currentIndex).getResultItem();
        List<ITextComponent> collection = Lists.newArrayList(screen.getTooltipFromItem(itemstack));
        if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
            collection.add(MORE_RECIPES_TOOLTIP);
        }
        return collection;
    }

    private List<IRecipe<?>> getOrderedRecipes() {
        List<IRecipe<?>> list = this.collection.getDisplayRecipes(true);
        if (!this.book.isFiltering(this.menu)) {
            list.addAll(this.collection.getDisplayRecipes(false));
        }

        return list;
    }

    @Override
    protected boolean isValidClickButton(int mouseButton) {
        return mouseButton == 0 || mouseButton == 1;
    }

    /**
     * Position is not the actual center position but UL (upper left)
     * @param posX
     * @param posY
     */
    @Override
    public void setPosition(int posX, int posY) {
        super.setPosition(posX, posY);
        tile.setLeft(x);
        tile.setTop(y);
        tile.setWidth(getWidth());
        tile.setHeight(getHeight());
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int value) {
    }

    class RecipeWidgetTile extends DrawableTile {
        // Disabled
        final Colour disabledBackground = new Colour(0.416F, 0.416F, 0.416F, 1F);
        final Colour disabledTopLeft = new Colour(0.314F, 0.106F, 0.106F, 1F);
        final Colour disabledBottomRight = new Colour(0.761F, 0.310F, 0.310F, 1F);
        final Colour disabledFrame = new Colour(0.541F, 0.133F, 0.133F, 1F);

        // Enabled
        final Colour enabledBackground = new Colour(0.545F, 0.545F, 0.545F, 1F);
        final Colour enabledTopLeft = new Colour(0.216F, 0.216F, 0.216F, 1F);
        final Colour enabledBottomRight = Colour.WHITE.withAlpha(0.8F);
        final Colour enabledFrame = new Colour(0.8F, 0.8F, 0.8F, 1F);

        // Already Installed
        final Colour installedBackground = new Colour(0.545F, 0.545F, 0.545F, 1F);
        final Colour installedTopLeft = new Colour(6, 59, 0);
        final Colour installedBottomRight = new Colour(13, 255, 0);
        final Colour installedFrame = new Colour(8, 144, 0);

        // Shadow
        final Colour shadow = Colour.BLACK.withAlpha(1F);

        Colour frameColour = enabledFrame;

        public RecipeWidgetTile() {
            super(new MusePoint2D(0, 0), new MusePoint2D(0, 0));
            setBorderShrinkValue(2.5F);
            setDoThisOnChange(change->updateWidgetPos());
        }

        Colour getFrameColour() {
            return this.frameColour;
        }

        RecipeWidgetTile setFrameColour(Colour frameColour) {
            this.frameColour = frameColour;
            return this;
        }

        public RecipeWidgetTile setDisabled() {
            setBackgroundColour(disabledBackground);
            setBottomBorderColour(disabledBottomRight);
            setTopBorderColour(disabledTopLeft);
            setFrameColour(disabledFrame);
            return this;
        }

        public RecipeWidgetTile setEnabled() {
            setBackgroundColour(enabledBackground);
            setBottomBorderColour(enabledBottomRight);
            setTopBorderColour(enabledTopLeft);
            setFrameColour(enabledFrame);
            return this;
        }

        public RecipeWidgetTile setAleadyInstalled() {
            setBackgroundColour(installedBackground);
            setBottomBorderColour(installedBottomRight);
            setTopBorderColour(installedTopLeft);
            setFrameColour(installedFrame);
            return this;
        }

        @Override
        public void drawBackground(MatrixStack matrixStack) {
            this.internalDraw(matrixStack, this.getFrameColour(), GL11.GL_QUADS, 1);
            this.internalDraw(matrixStack, this.getBackgroundColour(), GL11.GL_QUADS, 2);
            this.internalDrawRect(matrixStack,
                    left()+2,
                    bottom() - 1,
                    right(),
                    bottom(),
                    shadow,
                    GL11.GL_QUADS
            );
            this.internalDrawRect(matrixStack,
                    right()-1,
                    top() + 2,
                    right(),
                    bottom(),
                    shadow,
                    GL11.GL_QUADS
            );
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public MusePoint2D getUL() {
        return tile.getUL();
    }

    @Override
    public MusePoint2D getWH() {
        return tile.getWH();
    }

    @Override
    public double left() {
        return tile.left();
    }

    @Override
    public double finalLeft() {
        return tile.finalLeft();
    }

    @Override
    public double top() {
        return tile.top();
    }

    @Override
    public double finalTop() {
        return tile.finalTop();
    }

    @Override
    public double right() {
        return tile.right();
    }

    @Override
    public double finalRight() {
        return tile.finalRight();
    }

    @Override
    public double bottom() {
        return tile.bottom();
    }

    @Override
    public double finalBottom() {
        return tile.finalBottom();
    }

    @Override
    public double width() {
        return tile.width();
    }

    @Override
    public double finalWidth() {
        return tile.finalWidth();
    }

    @Override
    public double height() {
        return tile.height();
    }

    @Override
    public double finalHeight() {
        return tile.finalWidth();
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        return tile.setUL(ul);
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        return tile;
    }

    @Override
    public IRect setLeft(double value) {
        return tile.setLeft(value);
    }

    @Override
    public IRect setRight(double value) {
        return tile.setRight(value);
    }

    @Override
    public IRect setTop(double value) {
        return tile.setTop(value);
    }

    @Override
    public IRect setBottom(double value) {
        return tile.setBottom(value);
    }

    @Override
    public IRect setWidth(double value) {
        return tile;
    }

    @Override
    public IRect setHeight(double value) {
        return tile;
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        tile.move(moveAmount);
    }

    @Override
    public void move(double x, double y) {
        tile.move(x, y);
    }

    @Override
    public void setPosition(MusePoint2D position) {
        tile.setPosition(position);
    }

    @Override
    public MusePoint2D getPosition() {
        return tile.getPosition();
    }

    @Override
    public boolean growFromMiddle() {
        return tile.growFromMiddle();
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return tile.containsPoint(x, y);
    }

    @Override
    public double centerx() {
        return tile.centerx();
    }

    @Override
    public double centery() {
        return tile.centery();
    }

    @Override
    public boolean doneGrowing() {
        return tile.doneGrowing();
    }

    @Override
    public void initGrowth() {
        tile.initGrowth();
    }

    @Override
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        return tile.setMeLeftOf(otherRightOfMe);
    }

    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        return tile.setMeRightOf(otherLeftOfMe);
    }

    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        return tile.setMeAbove(otherBelowMe);
    }

    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        return tile.setMeBelow(otherAboveMe);
    }

    void updateWidgetPos() {
        super.x = (int) finalLeft();
        super.y = (int) finalTop();
    }

    @Override
    public IRect getRect() {
        return this.tile;
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
    public void setOnInit(IInit iInit) {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void doThisOnChange() {

    }

    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {

    }

    @Override
    public float getZLevel() {
        return tile.getZLevel();
    }

    @Override
    public IDrawable setZLevel(float v) {
        return this.tile;
    }
}
