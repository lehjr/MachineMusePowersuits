package com.github.lehjr.powersuits.client.gui.modding.cosmetic;

import com.github.lehjr.numina.util.capabilities.render.modelspec.EnumSpecType;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelRegistry;
import com.github.lehjr.numina.util.capabilities.render.modelspec.SpecBase;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableItem;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:39 PM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/9/16.
 */
public class PartManipContainer extends ScrollableFrame {
    public ItemSelectionFrame itemSelect;
    public ColourPickerFrame colourSelect;
    public ClickableItem lastItemSlot;
    public int lastColour;
    public int lastColourIndex;
    public List<PartSpecManipSubFrame> modelframes;

    public PartManipContainer(ItemSelectionFrame itemSelect,
                              ColourPickerFrame colourSelect,
                              MusePoint2D topleft,
                              MusePoint2D bottomright,
                              float zlevel,
                              Colour borderColour,
                              Colour insideColour) {
        super(topleft, bottomright, zlevel, borderColour, insideColour);

        this.itemSelect = itemSelect;
        this.colourSelect = colourSelect;
        this.lastItemSlot = null;
        this.lastColour = this.getColour();
        this.lastColourIndex = this.getColourIndex();
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        if (itemSelect.hasNoItems()) {
            this.disable();
            this.hide();
        } else {
            this.enable();
            this.show();
            this.getModelframes();
        }
    }

    @Nonnull
    public ItemStack getItem() {
        return (itemSelect.getSelectedItem() != null) ? itemSelect.getSelectedItem().getStack() : ItemStack.EMPTY;
    }

    public int getColour() {
        if (getItem().isEmpty()) {
            return Colour.WHITE.getInt();
        } else if (colourSelect.selectedColour < colourSelect.colours().length && colourSelect.selectedColour >= 0) {
            return colourSelect.colours()[colourSelect.selectedColour];
        }
        return Colour.WHITE.getInt();
    }

    public int getColourIndex() {
        return this.colourSelect.selectedColour;
    }

    public void getModelframes() {
        this.modelframes = new ArrayList<>();
        if (!getItem().isEmpty()) {
            Iterable<SpecBase> specCollection = ModelRegistry.getInstance().getSpecs();
            PartSpecManipSubFrame prev = null;
            PartSpecManipSubFrame newframe;
            for (SpecBase modelspec : specCollection) {
                if (isSpecValid(modelspec)) {
                    newframe = createNewFrame(modelspec, prev);
                    prev = newframe;
                    modelframes.add(newframe);
                }
            }
        }
    }

    boolean isSpecValid(SpecBase specBase) {
        if (!getItem().isEmpty()) {
            Item item = getItem().getItem();

            EquipmentSlotType slotType;
            if (item instanceof ArmorItem) {
                slotType = ((ArmorItem) item).getEquipmentSlot();
                return specBase.getSpecType().equals(EnumSpecType.ARMOR_MODEL) ||
                        specBase.getSpecType().equals(EnumSpecType.ARMOR_SKIN) &&
                                doesSpecHaveSlotType(specBase, slotType);
            } else if (item instanceof ToolItem) {
                return specBase.getSpecType().equals(EnumSpecType.HANDHELD) &&
                        doesSpecHaveSlotType(specBase, EquipmentSlotType.MAINHAND);
            } else {
                return specBase.getSpecType().equals(EnumSpecType.HANDHELD) &&
                        doesSpecHaveSlotType(specBase, EquipmentSlotType.OFFHAND);
            }
        }
        return false;
    }

    boolean doesSpecHaveSlotType(SpecBase specBase, EquipmentSlotType slot) {
        AtomicBoolean hasType = new AtomicBoolean(false);
        specBase.getPartSpecs().forEach(spec->{
            if (spec.getBinding().getSlot().equals(slot)) {
                hasType.set(true);
            }
        });
        return hasType.get();
    }

    public PartSpecManipSubFrame createNewFrame(SpecBase modelspec, PartSpecManipSubFrame prev) {
        MuseRelativeRect newborder = new MuseRelativeRect(
                border.finalLeft() + 4,
                border.finalTop() + 4,
                border.finalRight(),
                border.finalTop() + 10
        );
        newborder.setMeBelow((prev != null) ? prev.border : null);
        return new PartSpecManipSubFrame(modelspec, this.colourSelect, this.itemSelect, newborder, this.zLevel);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (this.isEnabled() && this.isVisible()) {
            if (button == 0) {
                for (PartSpecManipSubFrame frame : modelframes) {
                    if (frame.tryMouseClick(x, y + currentscrollpixels))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double mousex, double mousey) {
        super.update(mousex, mousey);

        // only completely disable this if the player has no items equipped
        if (itemSelect.hasNoItems()) {
            this.disable();
            this.hide();
        } else if (itemSelect.getSelectedItem() != null) {
            this.enable();
            this.show();

            if (!Objects.equals(lastItemSlot, itemSelect.getSelectedItem())) {
                lastItemSlot = itemSelect.getSelectedItem();
                double x = 0;
                for (PartSpecManipSubFrame subframe : modelframes) {
                    subframe.updateItems();
                    x += subframe.border.bottom();
                }
                this.totalsize = (int) x;
            }
            if (colourSelect.decrAbove > -1) {
                decrAbove(colourSelect.decrAbove);
                colourSelect.decrAbove = -1;
            }
        }
    }

    public void decrAbove(int index) {
        for (PartSpecManipSubFrame frame : modelframes) frame.decrAbove(index);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible() && !modelframes.isEmpty()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translated(0.0, -this.currentscrollpixels, 0.0);
            for (PartSpecManipSubFrame f : modelframes) {
                f.drawPartial(matrixStack, currentscrollpixels + 4 + border.finalTop(), this.currentscrollpixels + border.finalBottom() - 4);
            }
            RenderSystem.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }
}