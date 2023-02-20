/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.client.gui.modding.cosmetic.partmanip;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.capabilities.render.IModelSpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecNBTCapability;
import lehjr.numina.common.capabilities.render.modelspec.EnumSpecType;
import lehjr.numina.common.capabilities.render.modelspec.ModelRegistry;
import lehjr.numina.common.capabilities.render.modelspec.SpecBase;
import lehjr.numina.common.math.Colour;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.client.gui.common.ModularItemTabToggleWidget;
import lehjr.powersuits.client.gui.modding.cosmetic.colourpicker.ColourPickerFrame;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
public class ModelManipFrame extends ScrollableFrame {
    public ModularItemSelectionFrame itemSelect;
    public ColourPickerFrame colourSelect;
    public ModularItemTabToggleWidget lastItemSlot;
    public int lastColour;
    public int lastColourIndex;
    public List<ModelManipSubframe> modelframes;

    VanillaFrameScrollBar scrollBar;

    public ModelManipFrame(double left, double top, double right, double bottom,
                           ModularItemSelectionFrame itemSelect,
                           ColourPickerFrame colourSelect) {
        super(new Rect(left, top, right, bottom));
        this.itemSelect = itemSelect;
        this.colourSelect = colourSelect;
        this.lastItemSlot = null;
        this.lastColour = this.getColour();
        this.lastColourIndex = this.getColourIndex();
        this.enableAndShow();
        scrollBar = new VanillaFrameScrollBar(this, "scroll");
    }

    @Nonnull
    public ItemStack getItem() {
        return itemSelect.getModularItemOrEmpty();
    }

    /**
     * @return get int value representing selected color from color picker frame or default of white
     */
    public int getColour() {
        if (getItem().isEmpty()) {
            return Colour.WHITE.getInt();
        } else if (colourSelect.selectedColour < colourSelect.colours().length && colourSelect.selectedColour >= 0) {
            return colourSelect.colours()[colourSelect.selectedColour];
        }
        return Colour.WHITE.getInt();
    }

    /**
     * @return index of color value. Color values are stored in an NBT INT array
     */
    public int getColourIndex() {
        return this.colourSelect.selectedColour;
    }

    /**
     * creates new frames for displaying parts
     */
    public void refreshModelframes() {
        this.modelframes = new ArrayList<>();
        if (!getItem().isEmpty()) {
            Iterable<SpecBase> specCollection = ModelRegistry.getInstance().getSpecs();
            ModelManipSubframe prev = null;
            ModelManipSubframe newframe;

            for (SpecBase modelspec : specCollection) {
                if (isSpecValid(modelspec) ) {
                    newframe = createNewFrame(modelspec);

                    // empty when the parts are for a different equipment slot
                    if (!newframe.getParts().isEmpty()) {
                        newframe.setBelow(prev);
                        prev = newframe;
                        modelframes.add(newframe);
                    }
                }
            }
        }
    }

    /**
     *  Filters out parts that don't apply to the equipped item. Needed because some models
     * apply to more than just a single slot, such as legs and feet.
     * @param specBase
     * @return
     */
    boolean isSpecValid(SpecBase specBase) {
        if (!getItem().isEmpty()) {
            Item item = getItem().getItem();

            EquipmentSlotType slotType;
            if (item instanceof ArmorItem) {
                slotType = ((ArmorItem) item).getSlot();
                return specBase.getSpecType().equals(EnumSpecType.ARMOR_MODEL) ||
                        specBase.getSpecType().equals(EnumSpecType.ARMOR_SKIN) &&
                                doesSpecHaveSlotType(specBase, slotType);
            } else {
                return specBase.getSpecType().equals(EnumSpecType.HANDHELD) &&
                        (doesSpecHaveSlotType(specBase, EquipmentSlotType.OFFHAND) || doesSpecHaveSlotType(specBase, EquipmentSlotType.MAINHAND));
            }
        }
        return false;
    }

    /**
     * Checks to see if the model has parts for a specific equipment slot.
     * @param specBase
     * @param slot
     * @return
     */
    boolean doesSpecHaveSlotType(SpecBase specBase, EquipmentSlotType slot) {
        AtomicBoolean hasType = new AtomicBoolean(false);
        specBase.getPartSpecs().forEach(spec->{
            if (spec.getBinding().getSlot().equals(slot)) {
                hasType.set(true);
            }
        });
        return hasType.get();
    }

    /**
     * Creates the frame that displays the model and its part controls and part display name
     * @param modelspec
     * @return
     */
    public ModelManipSubframe createNewFrame(SpecBase modelspec) {
        // width = 197, height = 9?? (should be 10 so margin is + & -1 ??
        ModelManipSubframe newFrame = new ModelManipSubframe(
                modelspec,
                left() + 4,
                top() + 4,
                right(),
                top() + 10,
                this.colourSelect, this.itemSelect, this.zLevel);
        return newFrame;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (super.mouseClicked(x, y, button)) {
            itemSelect.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
                    .filter(IModelSpecNBT.class::isInstance)
                    .map(IModelSpecNBT.class::cast).ifPresent(spec ->System.out.println("renderTag: " + spec.getRenderTag()));

            itemSelect.getModularItemOrEmpty().getCapability(ModelSpecNBTCapability.RENDER)
                    .filter(IModelSpecNBT.class::isInstance)
                    .map(IModelSpecNBT.class::cast).ifPresent(spec ->System.out.println("default renderTag: " + spec.getDefaultRenderTag()));

            System.out.println("default full item tag: " + itemSelect.getModularItemOrEmpty().serializeNBT());


            for (ModelManipSubframe frame : modelframes) {
                if (frame.mouseClicked(x, y + getCurrentScrollPixels(), button)) {
                    return true;
                }
            }
            return scrollBar.mouseClicked(x, y, button);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return scrollBar.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void update(double mousex, double mousey) {
        super.update(mousex, mousey);
        // only completely disable this if the player has no items equipped
        if (!itemSelect.playerHasModularItems()) {
            this.disableAndHide();
            return;
        }

        if (!Objects.equals(lastItemSlot, itemSelect.getSelectedTab().get())) {
            colourSelect.selectedColour = 0;
            setCurrentScrollPixels(0); // reset scroll
            lastItemSlot = itemSelect.getSelectedTab().get();
            refreshModelframes();
        }

        if(!itemSelect.getModularItemOrEmpty().isEmpty()) {
            if (colourSelect.decrAbove > -1) {
                decrAbove(colourSelect.decrAbove);
                colourSelect.decrAbove = -1;
            }

            if (!itemSelect.getModularItemOrEmpty().isEmpty()) {
                double y = 0;
                for (ModelManipSubframe subframe : modelframes) {
                    // FIXME!!!
                    subframe.refreshPartSpecs(false);
                    y += subframe.height() + 1;
                }
                this.totalSize = (int) y + 10;
            }
        }
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mousey);
        setCurrentScrollPixels(scrollBar.getValue());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
        scrollBar.setValue(currentScrollPixels);
        return retVal;
    }

    /**
     * Only used when a color is removed
     * @param index
     */
    public void decrAbove(int index) {
        System.out.println("dec above : " + index);

        for (ModelManipSubframe frame : modelframes) frame.decrAbove(index);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        scrollBar.render(matrixStack, mouseX, mouseY, partialTicks);

        if (this.isVisible() && !modelframes.isEmpty()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            matrixStack.pushPose();
            matrixStack.translate(0.0, -this.currentScrollPixels, 0.0);
            for (ModelManipSubframe f : modelframes) {
                f.render(matrixStack, mouseX, (int)(this.currentScrollPixels + mouseY), partialTicks);
            }
            matrixStack.popPose();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }
}