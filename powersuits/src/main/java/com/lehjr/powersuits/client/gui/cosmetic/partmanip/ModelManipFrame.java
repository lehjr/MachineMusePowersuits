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

package com.lehjr.powersuits.client.gui.cosmetic.partmanip;

import com.lehjr.numina.client.gui.clickable.ModularItemTabToggleWidget;
import com.lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import com.lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.capabilities.render.modelspec.NuminaModelSpecRegistry;
import com.lehjr.numina.common.capabilities.render.modelspec.SpecBase;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.powersuits.client.gui.cosmetic.colorpicker.ColorPickerFrame;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 6:39 PM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/9/16.
 */
public class ModelManipFrame extends ScrollableFrame {
    public ModularItemSelectionFrame itemSelect;
    public ColorPickerFrame colorSelect;
    public ModularItemTabToggleWidget lastItemSlot;
    public int lastColor;
    public int lastColorIndex;
    public List<ModelManipSubframe> modelframes;
    CompoundTag oldTag;

    VanillaFrameScrollBar scrollBar;

    public ModelManipFrame(double left, double top, double right, double bottom,
                           ModularItemSelectionFrame itemSelect,
                           ColorPickerFrame colorSelect) {
        super(new Rect(left, top, right, bottom));
        this.itemSelect = itemSelect;
        this.colorSelect = colorSelect;
        this.lastItemSlot = null;
        this.lastColor = this.getColor();
        this.lastColorIndex = this.getColorIndex();
        this.enableAndShow();
        scrollBar = new VanillaFrameScrollBar(this, "scroll");
    }

    @Nonnull
    public ItemStack getItem() {
        return itemSelect.getModularItemOrEmpty();
    }

    public Optional<EquipmentSlot> getSlot() {
        return itemSelect.selectedType();
    }

    @Nullable
    public IModelSpec getRenderCapability() {
        return itemSelect.getModularItemOrEmpty().getCapability(NuminaCapabilities.RENDER);
    }

    @Nonnull
    public CompoundTag getRenderTag() {
        IModelSpec iModelSpec = getRenderCapability();
        return iModelSpec != null ? iModelSpec.getRenderTag() : new CompoundTag();
    }


    /**
     * @return get int value representing selected color from color picker frame or default of white
     */
    public int getColor() {
        if (getItem().isEmpty()) {
            return Color.WHITE.getARGBInt();
        } else if (colorSelect.selectedColor < colorSelect.colors.size() && colorSelect.selectedColor >= 0) {
            return colorSelect.colors.get(colorSelect.selectedColor);
        }
        return Color.WHITE.getARGBInt();
    }

    /**
     * @return index of color value. Color values are stored in an NBT INT array
     */
    public int getColorIndex() {
        return this.colorSelect.selectedColor;
    }

    /**
     * creates new frames for displaying parts
     */
    public void refreshModelframes() {
        this.modelframes = new ArrayList<>();
        if (!getItem().isEmpty()) {
            NuminaModelSpecRegistry.getInstance().getSpecsAsStream().forEach(specBase -> {
                if (isSpecValid(specBase)) {
//                    NuminaLogger.logDebug("spec is valid for slot:  " + specBase.getOwnName() + ", slot: " + getSlot().get());

                    ModelManipSubframe newframe = createNewFrame(specBase);
                    // empty when the parts are for a different equipment slot
                    if (!newframe.getPartFrames().isEmpty()) {
                        ModelManipSubframe prev = modelframes.size() > 0 ? modelframes.get(modelframes.size() - 1) : null;
                        newframe.setBelow(prev);
                        modelframes.add(newframe);
                    } else {
//                        NuminaLogger.logDebug("newframe.getParts().isEmpty()");
                    }
                } else {
                    NuminaLogger.logDebug("spec not valid for slot:  " + specBase.getOwnName() + ", slot: " + getSlot().get());
                }
            });
        }
    }

    /**
     *  Filters out parts that don't apply to the equipped item. Needed because some models
     * apply to more than just a single slot, such as legs and feet.
     * @param specBase
     * @return
     */
    boolean isSpecValid(SpecBase specBase) {
        if (getSlot().isPresent() && !getItem().isEmpty()) {
            EquipmentSlot slot = getSlot().get();

            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                NuminaLogger.logDebug("slot is armor and specBase.hasArmorEquipmentSlot(slot): " + specBase.hasArmorEquipmentSlot(slot));
                return specBase.hasArmorEquipmentSlot(slot);
            } else {
                HumanoidArm arm = slot.equals(EquipmentSlot.MAINHAND) ? getMinecraft().player.getMainArm() : getMinecraft().player.getMainArm().getOpposite();
//                NuminaLogger.logDebug("isForHand: " + specBase.getPartsAsStream().anyMatch(partSpecBase -> partSpecBase.isForHand(arm, getMinecraft().player)));

                return specBase.getPartsAsStream().anyMatch(partSpecBase -> partSpecBase.isForHand(arm, getMinecraft().player));
            }
        }
        NuminaLogger.logDebug("returning false ");
        return false;
    }

    /**
     * Checks to see if the model has parts for a specific equipment slot.
     * @param specBase
     * @param slot
     * @return
     */
    boolean doesSpecHaveSlotType(SpecBase specBase, EquipmentSlot slot) {
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
                this.colorSelect, this.itemSelect, this.zLevel);
        return newFrame;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (super.mouseClicked(x, y, button)) {
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

        CompoundTag testTag = getRenderTag();
        if (!Objects.equals(testTag, oldTag)) {
            NuminaLogger.logDebug("need to update controls for render tag: " + testTag);
            oldTag = testTag;
        }




        if (!Objects.equals(lastItemSlot, itemSelect.getSelectedTab().get())) {
            colorSelect.selectedColor = 0;
            setCurrentScrollPixels(0); // reset scroll
            lastItemSlot = itemSelect.getSelectedTab().get();
            refreshModelframes();
        }




        if(!itemSelect.getModularItemOrEmpty().isEmpty()) {
            if (colorSelect.decrAbove > -1) {
                decrAbove(colorSelect.decrAbove);
                colorSelect.decrAbove = -1;
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
        for (ModelManipSubframe frame : modelframes) frame.decrAbove(index);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
        scrollBar.render(gfx, mouseX, mouseY, partialTick);

        if (this.isVisible() && !modelframes.isEmpty()) {
            super.preRender(gfx, mouseX, mouseY, partialTick);
            gfx.pose().pushPose();
            gfx.pose().translate(0.0, -this.currentScrollPixels, 0.0);
            for (ModelManipSubframe f : modelframes) {
                f.render(gfx, mouseX, (int)(this.currentScrollPixels + mouseY), partialTick);
            }
            gfx.pose().popPose();
            super.postRender(gfx, mouseX, mouseY, partialTick);
        } else {
            super.preRender(gfx, mouseX, mouseY, partialTick);
            super.postRender(gfx, mouseX, mouseY, partialTick);
        }
    }
}
