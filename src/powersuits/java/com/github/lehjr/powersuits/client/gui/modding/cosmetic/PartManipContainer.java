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

package com.github.lehjr.powersuits.client.gui.modding.cosmetic;

import com.github.lehjr.numina.util.capabilities.render.modelspec.EnumSpecType;
import com.github.lehjr.numina.util.capabilities.render.modelspec.ModelRegistry;
import com.github.lehjr.numina.util.capabilities.render.modelspec.SpecBase;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import com.github.lehjr.powersuits.client.gui.common.ModularItemTabToggleWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
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
public class PartManipContainer extends ScrollableFrame {
    public ModularItemSelectionFrame itemSelect;
    public ColourPickerFrame colourSelect;
    public ModularItemTabToggleWidget lastItemSlot;
    public int lastColour;
    public int lastColourIndex;
    public List<PartSpecManipSubFrame> modelframes;

    public PartManipContainer(ModularItemSelectionFrame itemSelect,
                              ColourPickerFrame colourSelect) {
        super();
        this.itemSelect = itemSelect;
        this.colourSelect = colourSelect;
        this.lastItemSlot = null;
        this.lastColour = this.getColour();
        this.lastColourIndex = this.getColourIndex();
        this.enableAndShow();
    }

    @Override
    public RelativeRect init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);
        if (!itemSelect.playerHasModularItems()) {
            this.disable();
            this.hide();
        } else {
            this.enable();
            this.show();
            this.refreshModelframes();
        }
        return this;
    }

    @Nonnull
    public ItemStack getItem() {
        return itemSelect.getModularItemOrEmpty();
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

    public void refreshModelframes() {
        this.modelframes = new ArrayList<>();
        if (!getItem().isEmpty()) {
            Iterable<SpecBase> specCollection = ModelRegistry.getInstance().getSpecs();
            PartSpecManipSubFrame prev = null;
            PartSpecManipSubFrame newframe;
            for (SpecBase modelspec : specCollection) {
                if (isSpecValid(modelspec) ) {
                    newframe = createNewFrame(modelspec, prev);
                    if (!newframe.getPartSpecs().isEmpty()) {
                        prev = newframe;
                        modelframes.add(newframe);
                    }
                }
            }
        }
    }

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
        PartSpecManipSubFrame newFrame = new PartSpecManipSubFrame(
                modelspec,
                finalLeft() + 4,
                finalTop() + 4,
                finalRight(),
                finalTop() + 10,
                this.colourSelect, this.itemSelect, this.zLevel);
        newFrame.setMeBelow((prev != null) ? prev : null);
        return newFrame;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (this.isEnabled() && this.isVisible()) {
            if (this.containsPoint(x, y) && button == 0) {
                for (PartSpecManipSubFrame frame : modelframes) {
                    if (frame.tryMouseClick(x, y + currentScrollPixels)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void update(double mousex, double mousey) {
        super.update(mousex, mousey);
        // only completely disable this if the player has no items equipped
        if (!itemSelect.playerHasModularItems()) {
            this.disableAndHide();
        } else if (!Objects.equals(lastItemSlot, itemSelect.getSelectedTab().get())) {
            setCurrentScrollPixels(0); // reset scroll
            lastItemSlot = itemSelect.getSelectedTab().get();
            refreshModelframes();
            if (!itemSelect.getModularItemOrEmpty().isEmpty()) {
                double y = 0;
                for (PartSpecManipSubFrame subframe : modelframes) {
                    subframe.refreshPartSpecs();
                    y += subframe.finalHeight() + 1;
                }
                if (colourSelect.decrAbove > -1) {
                    decrAbove(colourSelect.decrAbove);
                    colourSelect.decrAbove = -1;
                }
                this.totalSize = (int) y + 10;
            }
        }
    }

    public void decrAbove(int index) {
        for (PartSpecManipSubFrame frame : modelframes) frame.decrAbove(index);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.isVisible() && !modelframes.isEmpty()) {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translated(0.0, -this.currentScrollPixels, 0.0);
            for (PartSpecManipSubFrame f : modelframes) {
                f.drawPartial(matrixStack, currentScrollPixels + 4 + finalTop(), this.currentScrollPixels + finalBottom() - 4);
            }
            RenderSystem.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }
}