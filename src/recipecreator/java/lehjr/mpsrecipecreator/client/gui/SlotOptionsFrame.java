package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.mpsrecipecreator.container.MPARCContainer;
import lehjr.numina.common.math.Colour;
import lehjr.numina.client.gui.clickable.CheckBox;
import lehjr.numina.client.gui.clickable.ClickableArrow;
import lehjr.numina.client.gui.clickable.ClickableLabel;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.gemoetry.DrawableArrow;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.RelativeRect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lehjr
 */
public class SlotOptionsFrame extends ScrollableFrame {
    private ClickableLabel title;
    int activeSlotID;
    MPARCContainer container;
    CheckBox[] useOreDictCheckbox = new CheckBox[9];
    RecipeGen recipeGen;

    private Map<Integer, ResourceLocation> oreTags = new HashMap<>();
    final int spacer = 4;

    private ClickableArrow prevOreDictArrow, nextOreDictArrow;

    public SlotOptionsFrame(MusePoint2D topleft,
                            MusePoint2D bottomright,
                            RecipeGen recipeGenIn,
                            MPARCContainer container,
                            Colour backgroundColour,

                            Colour borderColour,
                            Colour arrowNormalBackGound,
                            Colour arrowHighlightedBackground,
                            Colour arrowBorderColour) {
        super(topleft, bottomright, backgroundColour, borderColour, arrowBorderColour);
        this.container = container;
        this.recipeGen = recipeGenIn;

        MusePoint2D starterPoint = this.getUL().copy().plus(4, 4);

        this.title = new ClickableLabel("Slot Options", starterPoint.copy());
        title.setMode(ClickableLabel.JustifyMode.LEFT);

        nextOreDictArrow = new ClickableArrow(0, 0, 0, 0, true, arrowNormalBackGound, arrowHighlightedBackground, arrowBorderColour);
        nextOreDictArrow.setDrawShaft(false);
        nextOreDictArrow.setOnPressed(pressed-> {
            this.recipeGen.setOreDictIndexForward(activeSlotID);
        });

        prevOreDictArrow = new ClickableArrow(0, 0, 0, 0, true, arrowNormalBackGound, arrowHighlightedBackground, arrowBorderColour);
        prevOreDictArrow.setDrawShaft(false);
        prevOreDictArrow.setDirection(DrawableArrow.ArrowDirection.LEFT);
        prevOreDictArrow.setOnPressed(pressed-> {
            this.recipeGen.setOreDictIndexReverse(activeSlotID);
        });

        activeSlotID = -1;

        for(int i=0; i < 9; i++) {
            useOreDictCheckbox[i] = new CheckBox(new MusePoint2D(0, 0), "Use ore dictionary", false);
            useOreDictCheckbox[i].disableAndHide();
            useOreDictCheckbox[i].setOnPressed(pressed -> {
                if (getActiveSlotID() > 0) {
                    recipeGen.useOredict.put(getActiveSlotID(), useOreDictCheckbox[getActiveSlotID()-1].isChecked());
                }
            });
        }
        reset();
    }

    public int getActiveSlotID() {
        return activeSlotID;
    }

    @Override
    public RelativeRect init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        // Slot-specific controls
        MusePoint2D slotSpecificCol = this.getUL().plus(spacer, spacer);
        float nextLineSC = 0;

        title.setPosition(slotSpecificCol.plus(0,spacer));

        for(int i=0; i<9; i++) {
            useOreDictCheckbox[i].setPosition(slotSpecificCol.plus(4, nextLineSC + 18));
        }
        prevOreDictArrow.setWH(new MusePoint2D(12, 17)).setLeft(right - 40).setTop(top + 8);
        nextOreDictArrow.setWH(new MusePoint2D(12, 17)).setLeft(right - 20).setTop(top + 8);
        return this;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);

        for(int i=0; i< 9; i++) {
            if (activeSlotID >= 1 && i + 1 == activeSlotID) {
                continue;
            }
            useOreDictCheckbox[i].disableAndHide();

            if (!container.getSlot(i+1).hasItem()) {
                useOreDictCheckbox[i].setChecked(false);
            }
        }

        if (activeSlotID >= 1) {
            ItemStack stack = container.getSlot(activeSlotID).getItem();
            if (stack.isEmpty()) {
                useOreDictCheckbox[activeSlotID -1].setChecked(false);
                useOreDictCheckbox[activeSlotID -1].disableAndHide();
                nextOreDictArrow.disableAndHide();
                prevOreDictArrow.disableAndHide();
            } else {
                Item item = stack.getItem();
                final ArrayList<ResourceLocation> ids = new ArrayList<>(ItemTags.getAllTags().getMatchingTags(item));
                if (!ids.isEmpty()) {
                    useOreDictCheckbox[activeSlotID -1].enableAndShow();
                    if (useOreDictCheckbox[activeSlotID -1].isChecked()) {
                        int oreIndex = recipeGen.getOreIndex(activeSlotID);
                        if (oreIndex + 1 < ids.size()) {
                            nextOreDictArrow.enableAndShow();
                        } else {
                            nextOreDictArrow.disableAndHide();
                        }

                        if (oreIndex > 0) {
                            prevOreDictArrow.enableAndShow();
                        } else {
                            prevOreDictArrow.disableAndHide();
                        }
                    } else {
                        nextOreDictArrow.disableAndHide();
                        prevOreDictArrow.disableAndHide();
                    }
                } else {
                    useOreDictCheckbox[activeSlotID -1].disableAndHide();
                    nextOreDictArrow.disableAndHide();
                    prevOreDictArrow.disableAndHide();
                }
            }
        } else {
            nextOreDictArrow.disableAndHide();
            prevOreDictArrow.disableAndHide();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            title.render(matrixStack, mouseX, mouseY, partialTicks);
            for (int i =0; i < 9; i++) {
                useOreDictCheckbox[i].render(matrixStack, mouseX, mouseY, partialTicks);
            }
            nextOreDictArrow.render(matrixStack, mouseX, mouseY, partialTicks);
            prevOreDictArrow.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);
            for (int i =0; i < 9; i++) {
                if (useOreDictCheckbox[i].mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }

            if (nextOreDictArrow.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }

            if (prevOreDictArrow.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    public void selectSlot(int slot) {
        this.activeSlotID = slot;
        setLabel();
    }

    void setLabel() {
        this.title.setLabel("Slot " + (activeSlotID >=0 && activeSlotID <=10 ? activeSlotID + " " : "") + "Options");
    }

    public void reset() {
        for(int i=0; i<9; i++) {
            useOreDictCheckbox[i].disableAndHide();
        }

        activeSlotID = -1;
        setLabel();
    }
}