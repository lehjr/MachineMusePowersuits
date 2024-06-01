package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.mpsrecipecreator.basemod.DataPackWriter;
import lehjr.mpsrecipecreator.basemod.MPSRCConstants;
import lehjr.mpsrecipecreator.container.MPSRCMenu;
import lehjr.numina.client.gui.ExtendedContainerScreen;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;
import java.util.Arrays;

public class MPSRCGui extends ExtendedContainerScreen<MPSRCMenu> {
    static final ResourceLocation BACKGROUND = new ResourceLocation(MPSRCConstants.MOD_ID, "textures/gui/mpsrc_background.png");
    private final RecipeOptionsFrame recipeOptions;
    private final RecipeDisplayFrame recipeDisplayFrame;
    private final SlotOptionsFrame slotOptions;
    // text box
    public StackTextDisplayFrame tokenTxt;
    SlotButton[] slotButtons;


    // separate frame for each slot
//    private final SlotOptionsFrame slotOptions;
    public RecipeGen recipeGen;

    public MPSRCGui(MPSRCMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title, 400, 300);
        recipeOptions = new RecipeOptionsFrame(this);
        recipeGen = new RecipeGen(container, recipeOptions);
        tokenTxt = new StackTextDisplayFrame();
        slotOptions = new SlotOptionsFrame(recipeGen, container);
        recipeDisplayFrame = new RecipeDisplayFrame();
        slotButtons = new SlotButton[9];
        for(int i = 0; i< 9; i++) {
            slotButtons[i] = new SlotButton(container.getSlot(i+1), this.ulGetter());
        }
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.player.containerMenu = this.menu;
        recipeOptions.setUL(new MusePoint2D(left() + 8, top() + 6));
        recipeOptions.setWH(new MusePoint2D(218, 115));
        recipeOptions.init();
        recipeOptions.enableAndShow();

        slotOptions.setUL(new MusePoint2D( left() + 8, top() + 125));
        slotOptions.setWH(new MusePoint2D(218,  51));
        slotOptions.enableAndShow();
        slotOptions.init();

        tokenTxt.setUL(new MusePoint2D(left() + 8, top() + 180));
        tokenTxt.setWH(new MusePoint2D(384, 16));
        tokenTxt.enableAndShow();

        recipeDisplayFrame.setUL(new MusePoint2D(left() + 8, top()  + 200));
        recipeDisplayFrame.setWH(new MusePoint2D(384, 94));
        recipeDisplayFrame.enableAndShow();

        Arrays.stream(slotButtons).forEach(slotButton -> slotButton.setULBySlot());

        ulGetter().getULShift();

        addFrame(recipeOptions);
        addFrame(tokenTxt);
        addFrame(slotOptions);
        addFrame(recipeDisplayFrame);
    }

    public void resetRecipes() {
        slotOptions.reset();
        recipeGen.reset();
        getMenu().craftMatrix.clearContent();
        getMenu().craftResult.clearContent();
    }

    void resetOtherButtons(int index) {
        for(int i = 0; i< 9; i++) {
            if(i != index) {
                slotButtons[i].active = false;
            }
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        for(int i = 0; i< 9; i++) {
            if(slotButtons[i].mouseClicked(x, y, button)) {
                resetOtherButtons(i);
                selectSlot(slotButtons[i].active ? (i + 1) : -1);
                return true;
            }
        }
//        NuminaLogger.logDebug("event listeners size:" + getEventListeners().size());
//
//
////        boolean test = super.mouseClicked(x, y, button);
////        NuminaLogger.logDebug("test: " + test);
////        return test;
//
//        // pick block = middle mouse button (2)
//        // attack button = left mouse button (0)
//        // place block/use item = right mounse button (1)
//
//        NuminaLogger.logDebug("selected slot index: " + getSelectedSlot(x, y) == null ? null : container.inventorySlots.indexOf(getSelectedSlot(x, y)));

//            InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrMakeInput(button);
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
    public void renderLabels(GuiGraphics gfx, int x, int y) {
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

    public void selectSlot(int index) {
        slotOptions.selectSlot(index);
        tokenTxt.setSlot(index);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.render(gfx, mouseX, mouseY, partialTicks);
        super.renderFrames(gfx, mouseX, mouseY, partialTicks);
        super.renderFrameLabels(gfx, mouseX, mouseY);

        // Title
        StringUtils.drawShadowedStringCentered(gfx, "MPS-RecipeCreator", centerX(), topPos - 20);
        renderTooltip(gfx, mouseX, mouseY);
        gfx.pose().popPose();
        Arrays.stream(slotButtons).forEach(button -> button.render(gfx, mouseX, mouseY, partialTicks));
    }

    @Override
    public void renderBg(GuiGraphics gfx, float frameTime, int mouseX, int mouseY) {
        super.renderBg(gfx, frameTime, mouseX, mouseY);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.leftPos;
        int j = this.topPos;
        gfx.blit(BACKGROUND, i, j, 0, 0, imageWidth, imageHeight, 512, 512);

//        this.blit(gfx, i, j, 0, 0, 0, imageWidth, imageHeight, 512, 512);
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}