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

package com.github.lehjr.powersuits.client.gui.modding.module;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableItem;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.basemod.MPSModules;
import com.github.lehjr.powersuits.client.gui.common.ItemSelectionFrame;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModuleSelectionFrame extends ScrollableFrame {
    protected ItemSelectionFrame target;
    protected Map<EnumModuleCategory, ModuleSelectionSubFrame> categories = new LinkedHashMap<>();
    protected MuseRect lastPosition;

    public ModuleSelectionFrame(ItemSelectionFrame itemSelectFrameIn, MusePoint2D topleft, MusePoint2D bottomright, float zLevel, Colour backgroundColour, Colour borderColour) {
        super(topleft, bottomright, zLevel, backgroundColour, borderColour);
        this.target = itemSelectFrameIn;
    }

    private ModuleSelectionSubFrame getOrCreateCategory(EnumModuleCategory category) {
        if (categories.containsKey(category)) {
            return categories.get(category);
        } else {
            MuseRelativeRect position = new MuseRelativeRect(
                    border.left() + 4,
                    border.top() + 4,
                    border.right() - 4,
                    border.top() + 36);
            position.setMeBelow(lastPosition);
            lastPosition = position;
            ModuleSelectionSubFrame frame = new ModuleSelectionSubFrame(
                    category,
                    position);

            categories.put(category, frame);
            return frame;
        }
    }

    /**
     * Populates the module list
     * load this whenever a modular item is selected or when a module is installed
     */
    public void loadModules(boolean preserveSelected) {
        this.lastPosition = null;
        // temp holder
        ClickableModule selCopy = getSelectedModule();

        ClickableItem selectedItem = target.getSelectedItem();
        if (selectedItem != null) {
            if (!preserveSelected) {
                selCopy = null;
            } else if(getSelectedModule() != null) {
                ClickableModule sel = getSelectedModule();
                selCopy = new ClickableModule(sel.getModule(), new MusePoint2D(0, 0), -1, sel.category);
            }

            categories = new LinkedHashMap<>();
            selectedItem.getStack().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler->{
                if (itemHandler instanceof IModularItem) {
                    List<ResourceLocation> moduleRegNameList = new ArrayList<>(MPSModules.INSTANCE.getModuleRegNames()); // copy of the list
                    // check the list of all possible modules
                    for (ResourceLocation regName : moduleRegNameList) {
                        if(!((IModularItem) itemHandler).isModuleInstalled(regName)) {
                            ItemStack module = new ItemStack(ForgeRegistries.ITEMS.getValue(regName));
                            EnumModuleCategory category = module.getCapability(PowerModuleCapability.POWER_MODULE).map(m->m.getCategory()).orElse(EnumModuleCategory.NONE);

                            if (((IModularItem) itemHandler).isModuleValid(module)) {
                                ModuleSelectionSubFrame frame = getOrCreateCategory(category);
                                ClickableModule clickie = frame.addModule(module,  -1);
                                clickie.setInstalled(false);
                            }
                        }
                    }

                    // Occupied slots in the Modular Item
                    for (int index = 0; index < itemHandler.getSlots(); index++) {
                        ItemStack module = itemHandler.getStackInSlot(index);
                        int finalIndex = index;
                        module.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(m->{
                            if (m.isAllowed()) {
                                ModuleSelectionSubFrame frame = getOrCreateCategory(m.getCategory());
                                ClickableModule clickie =  frame.addModule(module, finalIndex);
                                clickie.setInstalled(true);
                            }
                        });
                    }
                }
            });
        }

        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.refreshButtonPositions();
            // actually preserve the module selection during call to init due to it being called on gui resize
            if(preserveSelected && selCopy != null && frame.category == selCopy.category) {
                for (ClickableModule button : frame.moduleButtons) {
                    if (button.getModule().isItemEqual(selCopy.getModule())) {
                        frame.selectedModule = frame.moduleButtons.indexOf(button);
                        preserveSelected = false; // just to skip checking the rest
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.refreshButtonPositions();
            if (target.getSelectedItem() != null) {
                frame.refreshModules(target.getSelectedItem().getStack());
            }
        }

        if (target.getSelectedItem() != null) {
            this.totalsize = 0;

            for (ModuleSelectionSubFrame frame : categories.values()) {
                totalsize = (int) Math.max(frame.border.bottom() - this.border.top(), totalsize);
            }

            this.currentscrollpixels = Math.min(currentscrollpixels, getMaxScrollPixels());
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0, -currentscrollpixels, 0);
            drawItems(matrixStack, partialTicks, getzLevel());
            drawSelection(matrixStack);
            RenderSystem.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    private void drawItems(MatrixStack matrixStack, float partialTicks, float zLevel) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.drawPartial(matrixStack, (int) (this.currentscrollpixels + border.top() + 4),
                    (int) (this.currentscrollpixels + border.top() + border.height() - 4), partialTicks, zLevel);
        }
    }

    private void drawSelection(MatrixStack matrixStack) {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = module.getPosition();
            if (pos.getY() > this.currentscrollpixels + border.top() + 4 && pos.getY() < this.currentscrollpixels + border.top() + border.height() - 4) {
                MuseRenderer.drawCircleAround(matrixStack, pos.getX(), pos.getY(), 10, getzLevel());
            }
        }
    }

    public ClickableModule getSelectedModule() {
        ClickableModule ret = null;
        if (!categories.isEmpty()) {
            for (ModuleSelectionSubFrame frame : categories.values()) {
                ret = frame.getSelectedModule();
                if (ret != null)
                    break;
            }
        }
        return ret;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (super.mouseClicked(x, y, button))
            return true;

        ModuleSelectionSubFrame sel = null;

        if (border.containsPoint(x, y)) {
            y += currentscrollpixels;
            int i = 0;

            for (ModuleSelectionSubFrame frame : categories.values()) {
                if (frame.mouseClicked(x, y, button)) {
                    sel = frame;
                }
            }

            if(sel != null && sel.getSelectedModule() != null) {
                for (ModuleSelectionSubFrame frame : categories.values()) {
                    if (frame != sel) {
                        frame.resetSelection();
                    }
                }
            }
        }
        return sel != null;
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (border.containsPoint(x, y)) {
            y += currentscrollpixels;
            if (!categories.isEmpty()) {
                for (ModuleSelectionSubFrame category : categories.values()) {
                    List<ITextComponent> tooltip = category.getToolTip(x, y);
                    if(tooltip != null) {
                        return tooltip;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Sets code to be executed when a new item is selected
     * @param doThisIn
     */
    OnSelectNewModule doThis;
    public void setDoOnNewSelect(OnSelectNewModule doThisIn) {
        doThis = doThisIn;
    }

    /**
     * runs preset code when new module is selected
     */
    void onSelected() {
        if(this.doThis != null) {
            this.doThis.onSelected(this);
        }
    }

    public interface OnSelectNewModule {
        void onSelected(ModuleSelectionFrame doThis);
    }
}