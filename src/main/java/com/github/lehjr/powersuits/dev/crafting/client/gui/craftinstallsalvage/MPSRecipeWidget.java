package com.github.lehjr.powersuits.dev.crafting.client.gui.craftinstallsalvage;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.recipebook.RecipeWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class MPSRecipeWidget extends RecipeWidget {
    RecipeWidgetTile tile = new RecipeWidgetTile();

    public MPSRecipeWidget() {
        super(); // initialize the base class before setting everything else
        tile.setWidth(getWidth());
        tile.setHeight(getHeight());
    }

    public void init(RecipeList recipeList, MPSRecipeBookPage recipeBookPage) {
        this.collection = recipeList;
        this.menu = (RecipeBookContainer)recipeBookPage.getMinecraft().player.containerMenu;
        this.book = recipeBookPage.getRecipeBook();
        List<IRecipe<?>> list = recipeList.getRecipes(this.book.isFiltering(this.menu));

        for(IRecipe<?> irecipe : list) {
            if (this.book.willHighlight(irecipe)) {
                recipeBookPage.recipesShown(list);
                this.animationTime = 15.0F;
                break;
            }
        }
    }

    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (!Screen.hasControlDown()) {
            this.time += frameTime;
        }

        if (!Screen.hasControlDown()) {
            this.time += frameTime;
        }
        if (!this.collection.hasCraftable()) {
            tile.setDisabled();
        } else {
            tile.setEnabled();
        }
        tile.render(matrixStack, mouseX, mouseY, frameTime);





        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(RECIPE_BOOK_LOCATION);
        int i = 29;
        if (!this.collection.hasCraftable()) {
            i += 25;
        }

        int j = 206;
        if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
            j += 25;
        }

        boolean flag = this.animationTime > 0.0F;
        if (flag) {
            float f = 1.0F + 0.1F * (float)Math.sin((double)(this.animationTime / 15.0F * (float)Math.PI));
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(this.x + 8), (float)(this.y + 12), 0.0F);
            RenderSystem.scalef(f, f, 1.0F);
            RenderSystem.translatef((float)(-(this.x + 8)), (float)(-(this.y + 12)), 0.0F);
            this.animationTime -= frameTime;
        }

        this.blit(matrixStack, this.x, this.y, i, j, this.width, this.height);
        List<IRecipe<?>> list = this.getOrderedRecipes();



        this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
        ItemStack itemstack = list.get(this.currentIndex).getResultItem();
        int k = 4;

        if (this.collection.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
            minecraft.getItemRenderer().renderAndDecorateItem(itemstack, this.x + k + 1, this.y + k + 1);
            --k;
        }

        minecraft.getItemRenderer().renderAndDecorateFakeItem(itemstack, this.x + k, this.y + k);

        if (flag) {
            RenderSystem.popMatrix();
        }
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
        super.setWidth(width);
        tile.setWidth(width);
    }

    @Override
    public void setHeight(int value) {
        super.setHeight(value);
        tile.setHeight(value);
    }

    class RecipeWidgetTile extends DrawableTile {
        /*
            super class is 25x25

            TODO:
            shrink background... also add frame and shadow, offset ul drawing by "1"?

         */



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

        // Shadow
        final Colour shadow = Colour.BLACK.withAlpha(1F);

        Colour frameColour = enabledFrame;

        public RecipeWidgetTile() {
            super(new MusePoint2D(0, 0), new MusePoint2D(0, 0));
            setBorderShrinkValue(2.5F);
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
}
