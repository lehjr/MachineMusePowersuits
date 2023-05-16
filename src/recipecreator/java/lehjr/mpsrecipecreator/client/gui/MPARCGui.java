package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.mpsrecipecreator.basemod.Constants;
import lehjr.mpsrecipecreator.basemod.DataPackWriter;
import lehjr.mpsrecipecreator.container.MPSRCContainer;
import lehjr.numina.client.gui.ExtendedContainerScreen2;
import lehjr.numina.client.gui.geometry.DrawableTile;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

/**
 * @author Dries007
 */
public class MPARCGui extends ExtendedContainerScreen2<MPSRCContainer> {
    static final ResourceLocation BACKGROUND = new ResourceLocation(Constants.MOD_ID, "textures/gui/mpsrc_background.png");

    Rect backgroundRect;
    final int spacer = 4;

    private final RecipeOptionsFrame recipeOptions;
    private final RecipeDisplayFrame recipeDisplayFrame;
    // separate frame for each slot
    private final SlotOptionsFrame slotOptions;

    // text box
    public StackTextDisplayFrame tokenTxt;
    protected final Colour gridBorderColour = Colour.LIGHT_BLUE.withAlpha(0.8F);
    protected final Colour gridBackGound = new Colour(0.545F, 0.545F, 0.545F, 1);
    public RecipeGen recipeGen;

    public MPARCGui(MPSRCContainer menu, PlayerInventory playerInventory, ITextComponent title) {
        super(menu, playerInventory, title, 400, 300);//, false);
        float zLevel = getBlitOffset();
        backgroundRect = new DrawableTile(getUL(), getUL().plus(getWH()));

        recipeOptions = new RecipeOptionsFrame(new DrawableTile(MusePoint2D.ZERO, MusePoint2D.ZERO)
                .setBackgroundColour(Colour.DARKBLUE)
                .setTopBorderColour(gridBackGound),
                this);
        addFrame(recipeOptions);
        recipeGen = new RecipeGen(menu, recipeOptions);

        // display for stack string in slot
        tokenTxt = new StackTextDisplayFrame(new DrawableTile(MusePoint2D.ZERO, MusePoint2D.ZERO).setBackgroundColour(Colour.DARKBLUE));
        addFrame(tokenTxt);

        slotOptions = new SlotOptionsFrame(
                new MusePoint2D(0, 0),
                new MusePoint2D(0, 0),
                recipeGen,
                menu,
                Colour.DARKBLUE,
                gridBorderColour,
                Colour.DARK_GREY,
                Colour.LIGHT_GREY,
                Colour.BLACK);
        addFrame(slotOptions);

        recipeDisplayFrame = new RecipeDisplayFrame(new DrawableTile(MusePoint2D.ZERO, MusePoint2D.ZERO).setBackgroundColour(Colour.DARKBLUE));
        addFrame(recipeDisplayFrame);
    }

    @Override
    public void init() {
        super.init();
        backgroundRect.setUL(getUL());
        backgroundRect.setWH(getWH());
        System.out.println("backgroundRect: " + backgroundRect.toString());



        this.minecraft.player.containerMenu = this.menu;
        // left side of inventory slots
        double inventoryWidth = 168;//(spacer * 2) + (9 * slotWidth);

        double inventoryLeft = backgroundRect.right() - inventoryWidth - spacer;


        double inventoryHeight = 192;
        System.out.println("inventory height: " + inventoryHeight);

        /** */
        recipeOptions.setUL(new MusePoint2D(backgroundRect.left() + spacer, backgroundRect.top() + spacer));
        recipeOptions.setWH(new MusePoint2D(recipeOptions.left() - (inventoryLeft - spacer * 2), recipeOptions.top() - (backgroundRect.top() + spacer + 150)));
        recipeOptions.enableAndShow();

        /** */
        slotOptions.setUL(new MusePoint2D( backgroundRect.left() + spacer, backgroundRect.top() + spacer * 2 + 150));
        slotOptions.setWH(new MusePoint2D(slotOptions.left() - (inventoryLeft - spacer * 2),  slotOptions.top() - (backgroundRect.top() + spacer + 188)));
        slotOptions.enableAndShow();
        slotOptions.init();

        /** */
        tokenTxt.setUL(new MusePoint2D(backgroundRect.left() + spacer, backgroundRect.top() + spacer * 2 + 188));
        tokenTxt.setWH(new MusePoint2D(tokenTxt.left() - (backgroundRect.right() - spacer), tokenTxt.top() - (backgroundRect.top() + spacer * 2 + 188 + 20)));
        tokenTxt.enableAndShow();

        /** displays the recipe in json format */
        recipeDisplayFrame.setUL(new MusePoint2D(backgroundRect.left() + spacer + 1, backgroundRect.top()  + 214));
        recipeDisplayFrame.setWH(new MusePoint2D(384, 80));
        recipeDisplayFrame.enableAndShow();
    }

    public void resetRecipes() {
        slotOptions.reset();
        recipeGen.reset();
        getMenu().craftMatrix.clearContent();
        getMenu().craftResult.clearContent();
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
//        System.out.println("event listeners size:" + getEventListeners().size());


//        boolean test = super.mouseClicked(x, y, button);
//        System.out.println("test: " + test);
//        return test;

        // pick block = middle mouse button (2)
        // attack button = left mouse button (0)
        // place block/use item = right mounse button (1)

        System.out.println("selected slot index: " + getSelectedSlot(x, y) == null ? null : menu.slots.indexOf(getSelectedSlot(x, y)));

//            InputMappings.Input mouseKey = InputMappings.Type.MOUSE.getOrMakeInput(button);
//            boolean flag = Minecraft.getInstance().gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey);
//            boolean flag = Minecraft.getInstance().gameSettings.keyBindUseItem.isActiveAndMatches(mouseKey);
//            boolean flag = Minecraft.getInstance().gameSettings.keyBindAttack.isActiveAndMatches(mouseKey);

        return super.mouseClicked(x, y, button);
    }

    @Nullable
    private Slot getSelectedSlot(double mouseX, double mouseY) {
        for(int i = 0; i < this.menu.slots.size(); ++i) {
            Slot slot = this.menu.slots.get(i);
            // isSlotSelected
            if (this.isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY) && slot.isActive()) {
                return slot;
            }
        }

        return null;
    }

    public void save() {
        if(menu.getSlot(0).hasItem()) {
            DataPackWriter.writeRecipe(recipeGen.getRecipeJson(),recipeDisplayFrame.title + ".json");
        }
    }

    @Override
    public void renderLabels(MatrixStack matrixStack, int x, int y) {
//        super.renderLabels(matrixStack, x, y);
    }

    @Override
    public void update(double x, double y) {
        super.update(x, y);

        // reset's the slot's settings when teh contents changed.
        int slotChanged = menu.getSlotChanged();
        if (slotChanged != -1) {
            recipeGen.useOredict.put(slotChanged, false);
            recipeGen.setOreTagIndex(slotChanged,0);
            // no oredict for result
            if (slotChanged < 0) {
                slotOptions.useOreDictCheckbox[slotChanged - 1].setChecked(false);
            }
        }

        int activeSlot = slotOptions.getActiveSlotID();

        if (activeSlot >= 0) {
            tokenTxt.setLabel(recipeGen.getStackToken(activeSlot));
        }

        recipeDisplayFrame.setFileName(recipeGen.getFileName());
        recipeDisplayFrame.setRecipe(recipeGen.getRecipeJson());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        backgroundRect.setWH(getWH());
        backgroundRect.setUL(getUL());
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        recipeOptions.render(matrixStack, mouseX, mouseY, partialTicks);;
        slotOptions.render(matrixStack, mouseX, mouseY, partialTicks);
        recipeDisplayFrame.render(matrixStack, mouseX, mouseY, partialTicks);;
        slotOptions.render(matrixStack, mouseX, mouseY, partialTicks);;

        renderLabels(matrixStack, mouseX, mouseY);
        renderFrameLabels(matrixStack, mouseX, mouseY);



        // Title
        StringUtils.drawShadowedStringCentered(matrixStack, "MPS-RecipeCreator", backgroundRect.centerX(), backgroundRect.top() - 20);
        renderTooltip(matrixStack, mouseX, mouseY);
    }



    public void selectSlot(int index) {
        slotOptions.selectSlot(index);
        tokenTxt.setSlot(index);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(this.BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, this.getBlitOffset(), 0, 0, imageWidth, imageHeight, 512, 512);
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}