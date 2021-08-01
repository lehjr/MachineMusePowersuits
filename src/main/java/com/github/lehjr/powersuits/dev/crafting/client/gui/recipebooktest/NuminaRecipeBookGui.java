package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;//package com.github.lehjr.numina.dev.crafting.client.gui;
//
//import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
//import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
//import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.recipebook.RecipeBookGui;
//import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
//import net.minecraft.client.gui.widget.TextFieldWidget;
//import net.minecraft.client.gui.widget.ToggleWidget;
//import net.minecraft.client.util.RecipeBookCategories;
//import net.minecraft.inventory.container.RecipeBookContainer;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.List;
//
//@OnlyIn(Dist.CLIENT)
//public class NuminaRecipeBookGui extends RecipeBookGui implements IGuiFrame {
//    /** rectangle to store all the values for placement */
//    RelativeRect storageRect = new RelativeRect();
//
//    int guiLeft = 0;
//
//    public NuminaRecipeBookGui() {
//        /*
//
//        needed width: 9 inch (229mm)
//        needed height: 5 1/8 inch (130mm)
//
//         */
//
//
////        this.recipeBookPage = new RecipeBookPage();
//        this.recipeBookPage = new NuminaRecipeBookPage();
//    }
//
//    public int getGuiLeft() {
//        return guiLeft;
//    }
//
//    @Override
//    public void init(int p_201520_1_, int p_201520_2_, Minecraft p_201520_3_, boolean p_201520_4_, RecipeBookContainer<?> p_201520_5_) {
//        super.init(p_201520_1_, p_201520_2_, p_201520_3_, p_201520_4_, p_201520_5_);
//    }
//
//    /**
//     * TODO:
//     *  - move tab toggle widgets over to edge of background frame
//     *  - resize recipe book page background
//     *  - add side scroll bar
//     *  - create recipe box with a stationary modular item category but recipe widgets that scroll like with creative screen
//     *  - create scrollable inventory frame like with creative screen, scroll amount is 1 full slot height
//     *  - create JEI crafting plugin
//     *  - change recipe list to show based on selected modular item
//     *  - recipe tab toggle widgets to show modular items equipped, if any, or recipe for the corresponding armor for that slot
//     *
//     *
//     *
//     */
//
//
//
//    @Override
//    public void initVisuals(boolean widthTooNarrow) {
//        System.out.println("initVisuals");
//
////        super.initVisuals(widthTooNarrow);
//        guiLeft = (this.width - 147) / 2 - this.xOffset;
//
//
//        /*
//           protected int imageWidth = 176;
//   protected int imageHeight = 166;
//         */
//
//        this.xOffset = widthTooNarrow ? 0 : 86;
//        int guiTop = (this.height - 166) / 2;
//        this.stackedContents.clear();
//        this.minecraft.player.inventory.fillStackedContents(this.stackedContents);
//        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
//        String s = this.searchBox != null ? this.searchBox.getValue() : "";
//        this.searchBox = new TextFieldWidget(this.minecraft.font, guiLeft + 25, guiTop + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
//        this.searchBox.setMaxLength(50);
//        this.searchBox.setBordered(false);
//        this.searchBox.setVisible(true);
//        this.searchBox.setTextColor(16777215);
//        this.searchBox.setValue(s);
//        this.recipeBookPage.init(this.minecraft, guiLeft, guiTop);
//        this.recipeBookPage.addListener(this);
//        this.filterButton = new ToggleWidget(guiLeft + 110, guiTop + 12, 26, 16, this.book.isFiltering(this.menu));
//        this.initFilterButtonTextures();
//        this.tabButtons.clear();
//
//        // FIXME: this is where we replace with modular equipment
//        for(RecipeBookCategories recipebookcategories : this.menu.getRecipeBookCategories()) {
//            this.tabButtons.add(new RecipeTabToggleWidget(recipebookcategories));
//        }
//
//        if (this.selectedTab != null) {
//            this.selectedTab = this.tabButtons.stream().filter((toggleWidget) ->
//                    toggleWidget.getCategory().equals(this.selectedTab.getCategory())).findFirst().orElse((RecipeTabToggleWidget)null);
//        }
//
//        if (this.selectedTab == null) {
//            this.selectedTab = this.tabButtons.get(0);
//        }
//
//        this.selectedTab.setStateTriggered(true);
//        this.updateCollections(false);
//        this.updateTabs();
//    }
//
//    @Override
//    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
//        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
//    }
//
//    @Override
//    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
//        return super.mouseScrolled(p_231043_1_, p_231043_3_, p_231043_5_);
//    }
//
//    @Override
//    public void update(double mouseX, double mouseY) {
//
//    }
//
//    @Override
//    public List<ITextComponent> getToolTip(int x, int y) {
//        return null;
//    }
//
//    @Override
//    public void setEnabled(boolean enabled) {
//
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public void setVisible(boolean visible) {
//        super.setVisible(visible);
//    }
//
//    @Override
//    public float getZLevel() {
//        return getBlitOffset();
//    }
//
//    @Override
//    public IDrawable setZLevel(float zLevel) {
//        setBlitOffset((int) zLevel);
//        return this;
//    }
//
//    @Override
//    public MusePoint2D getUL() {
//        return storageRect.getUL();
//    }
//
//    @Override
//    public MusePoint2D getWH() {
//        return storageRect.getWH();
//    }
//
//    @Override
//    public double left() {
//        return storageRect.left();
//    }
//
//    @Override
//    public double finalLeft() {
//        return storageRect.finalLeft();
//    }
//
//    @Override
//    public double top() {
//        return storageRect.top();
//    }
//
//    @Override
//    public double finalTop() {
//        return storageRect.finalTop();
//    }
//
//    @Override
//    public double right() {
//        return storageRect.right();
//    }
//
//    @Override
//    public double finalRight() {
//        return storageRect.finalRight();
//    }
//
//    @Override
//    public double bottom() {
//        return storageRect.bottom();
//    }
//
//    @Override
//    public double finalBottom() {
//        return storageRect.finalBottom();
//    }
//
//    @Override
//    public double width() {
//        return storageRect.width();
//    }
//
//    @Override
//    public double finalWidth() {
//        return storageRect.finalWidth();
//    }
//
//    @Override
//    public double height() {
//        return storageRect.height();
//    }
//
//    @Override
//    public double finalHeight() {
//        return storageRect.finalHeight();
//    }
//
//    @Override
//    public IRect setUL(MusePoint2D ul) {
//        return storageRect.setUL(ul);
//    }
//
//    @Override
//    public IRect setWH(MusePoint2D wh) {
//        return storageRect.setWH(wh);
//    }
//
//    @Override
//    public IRect setLeft(double value) {
//        return storageRect.setLeft(value);
//    }
//
//    @Override
//    public IRect setRight(double value) {
//        return storageRect.setRight(value);
//    }
//
//    @Override
//    public IRect setTop(double value) {
//        return storageRect.setTop(value);
//    }
//
//    @Override
//    public IRect setBottom(double value) {
//        return storageRect.setBottom(value);
//    }
//
//    @Override
//    public IRect setWidth(double value) {
//        return storageRect.setWidth(value);
//    }
//
//    @Override
//    public IRect setHeight(double value) {
//        return storageRect.setHeight(value);
//    }
//
//    @Override
//    public void move(MusePoint2D moveAmount) {
//        storageRect.move(moveAmount);
//    }
//
//    @Override
//    public void move(double x, double y) {
//        storageRect.move(x, y);
//    }
//
//    @Override
//    public void setPosition(MusePoint2D position) {
//        storageRect.setPosition(position);
//    }
//
//    @Override
//    public boolean growFromMiddle() {
//        return storageRect.growFromMiddle();
//    }
//
//    @Override
//    public void initGrowth() {
//        storageRect.initGrowth();
//    }
//
//    @Override
//    public IRect setMeLeftOf(RelativeRect otherRightOfMe) {
//        return storageRect.setMeLeftOf(otherRightOfMe);
//    }
//
//    @Override
//    public IRect setMeRightOf(RelativeRect otherLeftOfMe) {
//        return storageRect.setMeRightOf(otherLeftOfMe);
//    }
//
//    @Override
//    public IRect setMeAbove(RelativeRect otherBelowMe) {
//        return storageRect.setMeAbove(otherBelowMe);
//    }
//
//    @Override
//    public IRect setMeBelow(RelativeRect otherAboveMe) {
//        return storageRect.setMeBelow(otherAboveMe);
//    }
//
//    @Override
//    public void setOnInit(IInit onInit) {
//
//    }
//
//    @Override
//    public void onInit() {
//
//    }
//}
