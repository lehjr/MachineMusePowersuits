package lehjr.mpsrecipecreator.client.gui;

import com.lehjr.mpsrecipecreator.container.MPSRCMenu;
import com.lehjr.numina.client.gui.clickable.Checkbox;
import com.lehjr.numina.client.gui.clickable.ClickableLabel;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lehjr
 */
public class SlotOptionsFrame extends ScrollableFrame {
    private ClickableLabel title;
    int activeSlotID;
    MPSRCMenu container;
    Checkbox[] useOreDictCheckbox = new Checkbox[9];
    RecipeGen recipeGen;

    private Map<Integer, ResourceLocation> oreTags = new HashMap<>();
    final int spacer = 4;

    private ClickableNavArrow prevOreDictArrow;
    private ClickableNavArrow nextOreDictArrow;

    public SlotOptionsFrame(RecipeGen recipeGenIn, MPSRCMenu container){
        super(new Rect(MusePoint2D.ZERO, MusePoint2D.ZERO));
        this.container = container;
        this.recipeGen = recipeGenIn;

        MusePoint2D starterPoint = this.getUL().copy().plus(4, 4);

        this.title = new ClickableLabel(Component.literal("Slot Options"), starterPoint.copy());
        title.setMode(ClickableLabel.JustifyMode.LEFT);

        nextOreDictArrow = new ClickableNavArrow(0, 0,true, ClickableNavArrow.ArrowDirection.RIGHT);
        nextOreDictArrow.setOnPressed(pressed-> {
            this.recipeGen.setOreDictIndexForward(activeSlotID);
        });

        prevOreDictArrow = new ClickableNavArrow(0, 0,true, ClickableNavArrow.ArrowDirection.LEFT);
        prevOreDictArrow.setOnPressed(pressed-> {
            this.recipeGen.setOreDictIndexReverse(activeSlotID);
        });

        activeSlotID = -1;

        for(int i=0; i < 9; i++) {
            useOreDictCheckbox[i] = new Checkbox(new MusePoint2D(0, 0), "Use ore dictionary", false);
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

    public void init() {
        // Slot-specific controls
        MusePoint2D slotSpecificCol = this.getUL().plus(spacer, spacer);
        float nextLineSC = 0;

        title.setPosition(slotSpecificCol.plus(0,spacer));

        for(int i=0; i<9; i++) {
            useOreDictCheckbox[i].setPosition(slotSpecificCol.plus(4, nextLineSC + 18));
        }
        prevOreDictArrow.setLeft(right() - 40).setTop(top() + 8);
        nextOreDictArrow.setLeft(right() - 20).setTop(top() + 8);
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
                final List<ResourceLocation> ids = stack.getTags().map(TagKey::location).collect(Collectors.toList());
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
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.render(gfx, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            title.render(gfx, mouseX, mouseY, partialTicks);
            for (int i =0; i < 9; i++) {
                useOreDictCheckbox[i].render(gfx, mouseX, mouseY, partialTicks);
            }
            nextOreDictArrow.render(gfx, mouseX, mouseY, partialTicks);
            prevOreDictArrow.render(gfx, mouseX, mouseY, partialTicks);
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
        this.title.setLabel(Component.literal("Slot " + (activeSlotID >=0 && activeSlotID <=10 ? activeSlotID + " " : "") + "Options"));
    }

    public void reset() {
        for(int i=0; i<9; i++) {
            useOreDictCheckbox[i].disableAndHide();
        }

        activeSlotID = -1;
        setLabel();
    }
}