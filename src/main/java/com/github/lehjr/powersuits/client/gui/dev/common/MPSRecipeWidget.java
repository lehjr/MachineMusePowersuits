package com.github.lehjr.powersuits.client.gui.dev.common;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.recipebook.RecipeWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class MPSRecipeWidget extends RecipeWidget {
    float mult = 0.003921569F;

    private static final ITextComponent field_243412_b = new TranslationTextComponent("gui.recipebook.moreRecipes");
    Colour disabledOuterBorder = new Colour(mult * 54.1F, mult * 13.3F, mult * 13.3F, 1);
    Colour disabledBackground = //new Colour(0.416F, 0.416F, 0.416F, 1);
//    new Colour(138, 34, 34);
    new Colour(0.541F,
            0.133F,
            0.133F,
            1F);

    Colour disabledTopBorder = new Colour(0.541F, 0.133F, 0.133F, 1);
    Colour disabledBottomBorder = Colour.RED;
// outer_red =
    Colour enabledOuterBorder = new Colour(mult * 80F, mult * 80F, mult * 80F, 1);
    Colour enabledBackground = new Colour(0.545F, 0.545F, 0.545F, 1);
    Colour enabledTopBorder = Colour.DARK_GREY;
    Colour enabledBottomBorder = new Colour(0.8F, 0.8F, 0.8F, 1);

    Colour test = Colour.fromHexString("8a2222");


    private RecipeBookContainer<?> bookContainer;
    private RecipeBook book;
    private RecipeList list;
    private float time;
    private float animationTime;
    private int currentIndex;

    DrawableTile tileBorder;
    DrawableTile tile;

    public MPSRecipeWidget() {
        tile = new DrawableTile(0, 0, 0, 0, false)
                .setBackgroundColour(disabledBackground)
                .setTopBorderColour(disabledTopBorder)
                .setBottomBorderColour(disabledBottomBorder);
        tileBorder = new DrawableTile(0, 0, 0, 0, false)
                .setBackgroundColour(disabledBackground.withAlpha(0))
                .setTopBorderColour(disabledOuterBorder)
                .setBottomBorderColour(disabledOuterBorder);
    }

    @Override
    public void func_203400_a(RecipeList p_203400_1_, RecipeBookPage page) {
        this.list = p_203400_1_;
        this.bookContainer = (RecipeBookContainer)page.func_203411_d().player.openContainer;
        this.book = page.func_203412_e();
//        List<IRecipe<?>> list = p_203400_1_.getRecipes(this.book.isFilteringCraftable(this.bookContainer));
        List<IRecipe<?>> list = p_203400_1_.getRecipes(this.book.func_242141_a(this.bookContainer));

        for(IRecipe<?> irecipe : list) {
            if (this.book.isNew(irecipe)) {
                page.recipesShown(list);
                this.animationTime = 15.0F;
                break;
            }
        }
    }

    @Override
    public RecipeList getList() {
        return this.list;
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        System.out.println("");



        tile.setTargetDimensions(new MusePoint2D(x, y), new MusePoint2D(20, 20));
        tile.setTargetDimensions(new MusePoint2D(x, y).minus(1, 1), new MusePoint2D(24, 24));
        if (!this.list.containsCraftableRecipes()) {
            tile.setBackgroundColour(disabledBackground);
            tile.setTopBorderColour/*.setBorderColour*/(disabledTopBorder);
            tile.setBottomBorderColour/*.setBorderColour*/(disabledBottomBorder);
            tileBorder
                    .setBackgroundColour(disabledBackground.withAlpha(0))
                    .setTopBorderColour(disabledOuterBorder)
                    .setBottomBorderColour(disabledOuterBorder);
        } else {
            tile.setBackgroundColour(enabledBackground.withAlpha(0));
            tile.setTopBorderColour/*.setBorderColour*/(enabledTopBorder);
            tile.setBottomBorderColour/*.setBorderColour*/(enabledBottomBorder);
            tileBorder
                    .setBackgroundColour(enabledBackground.withAlpha(0))
                    .setTopBorderColour(enabledOuterBorder)
                    .setBottomBorderColour(enabledOuterBorder);
        }
//        tileBorder.draw(matrixStack, 0);
        tile.draw(matrixStack, 1);

        if (!Screen.hasControlDown()) {
            this.time += partialTicks;
        }

        RenderHelper.enableStandardItemLighting();;
        Minecraft minecraft = Minecraft.getInstance();
//        GlStateManager.disableLighting();
        List<IRecipe<?>> list = this.getOrderedRecipes();
        this.currentIndex = MathHelper.floor(this.time / 30.0F) % list.size();
        ItemStack itemstack = list.get(this.currentIndex).getRecipeOutput();
        int k = 4;
        if (this.list.hasSingleResultItem() && this.getOrderedRecipes().size() > 1) {
            minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemstack, this.x + k + 1, this.y + k + 1);
            --k;
        }

        minecraft.getItemRenderer().renderItemAndEffectIntoGUI(itemstack, this.x + k, this.y + k);
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
    }

    private List<IRecipe<?>> getOrderedRecipes() {
        List<IRecipe<?>> list = this.list.getDisplayRecipes(true);
        if (!this.book.func_242141_a(this.bookContainer)) {
            list.addAll(this.list.getDisplayRecipes(false));
        }
        return list;
    }

    @Override
    public boolean isOnlyOption() {
        return this.getOrderedRecipes().size() == 1;
    }

    @Override
    public IRecipe<?> getRecipe() {
        List<IRecipe<?>> list = this.getOrderedRecipes();
        return list.get(this.currentIndex);
    }

    @Override
    public List<ITextComponent> getToolTipText(Screen screen) {
        ItemStack itemstack = this.getOrderedRecipes().get(this.currentIndex).getRecipeOutput();
        List<ITextComponent> list = Lists.newArrayList(screen.getTooltipFromItem(itemstack));
        if (this.list.getRecipes(this.book.func_242141_a(this.bookContainer)).size() > 1) {
            list.add(field_243412_b);
        }

        return list;
    }

    @Override
    protected boolean isValidClickButton(int mouseButton) {
        return mouseButton == 0 || mouseButton == 1;
    }

    class ReipeTileWidget extends DrawableTile {
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


        public ReipeTileWidget(double left, double top, double right, double bottom, boolean growFromMiddle) {
            super(new MusePoint2D(x, y), new MusePoint2D(20, 20));
        }

        public ReipeTileWidget setEnabled() {
            return this;
        }

        public ReipeTileWidget  setDisabled() {

            return this;
        }



    }
}