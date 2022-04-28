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

package lehjr.powersuits.client.gui.modding.module.tweak;

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.client.gui.clickable.ClickableModule;
import lehjr.numina.util.client.gui.frame.ScrollableFrame;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Component;
import net.minecraftforge.common.util.LazyOptional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleSelectionFrame extends ScrollableFrame {
    protected ModularItemSelectionFrame target;
    protected Map<EnumModuleCategory, ModuleSelectionSubFrame> categories = new LinkedHashMap<>();
    protected RelativeRect lastPosition;
    Optional<ClickableModule> selectedModule = Optional.ofNullable(null);
    LazyOptional<IPowerModule> moduleCap = LazyOptional.empty();

    public ModuleSelectionFrame(ModularItemSelectionFrame itemSelectFrameIn, MusePoint2D topleft, MusePoint2D bottomright,
                                Color background,
                                Color topBorder,
                                Color bottomBorder) {
        super(topleft, bottomright, background, topBorder, bottomBorder);
        this.target = itemSelectFrameIn;
    }

    protected ModuleSelectionSubFrame getOrCreateCategory(EnumModuleCategory category) {
        if (categories.containsKey(category)) {
            return categories.get(category);
        } else {
            RelativeRect position = new RelativeRect(
                    getRect().left() + 4,
                    getRect().top() + 4,
                    getRect().right() - 4,
                    getRect().top() + 36);
            position.setMeBelow(lastPosition);
            lastPosition = position;
            ModuleSelectionSubFrame frame = new ModuleSelectionSubFrame(
                    category,
                    position);

            categories.put(category, frame);
            frame.setDoOnNewSelect(thing-> {
                selectedModule = Optional.of(thing.getSelectedModule());
                moduleCap = selectedModule.map(clickableModule -> clickableModule.getModule()).orElse(ItemStack.EMPTY).getCapability(PowerModuleCapability.POWER_MODULE);
            });
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
        AtomicReference<Optional<ClickableModule>> selCopy = new AtomicReference<>(getSelectedModule());
        AtomicBoolean preserve = new AtomicBoolean(preserveSelected);
        categories.clear();

        target.getModularItemCapability().ifPresent(iModularItem -> {
            if (!preserve.get()) {
                selCopy.set(Optional.empty());
            } else if(getSelectedModule().isPresent()) {
                getSelectedModule().ifPresent(sm-> {
                    selCopy.set(Optional.of(new ClickableModule(sm.getModule(), new MusePoint2D(0, 0), -1, sm.category)));
                });
            }

            // Occupied slots in the Modular Item
            for (int index = 0; index < iModularItem.getSlots(); index++) {
                ItemStack module = iModularItem.getStackInSlot(index);
                int finalIndex = index;
                module.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(m->{
                    if (m.isAllowed()) {
                        getOrCreateCategory(m.getCategory()).addModule(module, finalIndex);
                    }
                });
            }
        });

        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.refreshButtonPositions();
            // actually preserve the module selection during call to init due to it being called on gui resize
            if(preserveSelected && selCopy.get().isPresent() && frame.category == selCopy.get().get().category) {
                for (ClickableModule button : frame.moduleButtons) {
                    if (button.getModule().sameItem(selCopy.get().get().getModule())) {
                        frame.selectedModule = frame.moduleButtons.indexOf(button);
                        preserveSelected = false; // just to skip checking the rest
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        Optional<IModularItem> iModularItem = target.getModularItemCapability();

        categories.values().forEach(frame ->{
            frame.refreshButtonPositions();
        });

        if (iModularItem.isPresent()) {
            this.totalSize = 0;

            for (ModuleSelectionSubFrame frame : categories.values()) {
                totalSize = (int) Math.max(frame.border.bottom() - this.getRect().top(), totalSize);
            }

            this.currentScrollPixels = Math.min(currentScrollPixels, getMaxScrollPixels());
            super.preRender(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0, -currentScrollPixels, 0);
            drawItems(matrixStack, partialTicks);
            drawSelection(matrixStack);
            RenderSystem.popPose();
            super.postRender(mouseX, mouseY, partialTicks);
        } else {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    private void drawItems(PoseStack matrixStack, float partialTicks) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.drawPartial(matrixStack, (int) (this.currentScrollPixels + getRect().top() + 4),
                    (int) (this.currentScrollPixels + getRect().top() + getRect().height() - 4), partialTicks);
        }
    }

    float getzLevel() {
        return Minecraft.getInstance().screen.getBlitOffset();
    }

    private void drawSelection(PoseStack matrixStack) {
        getSelectedModule().ifPresent(module ->{
            MusePoint2D pos = module.getPosition();
            if (pos.getY() > this.currentScrollPixels + getRect().top() + 4 && pos.getY() < this.currentScrollPixels + getRect().top() + getRect().height() - 4) {
                MuseRenderer.drawCircleAround(matrixStack, pos.getX(), pos.getY(), 10, getzLevel());
            }
        });
    }

    public Optional<ClickableModule> getSelectedModule() {
//        if (!categories.isEmpty()) {
//            return categories.values().stream().filter(frame -> frame !=null && frame.getSelectedModule() != null).findFirst().map(frame->frame.getSelectedModule());
//        }
//        return Optional.empty();
        return selectedModule;
    }

    public LazyOptional<IPowerModule> getModuleCap() {
//        return getSelectedModule()
//                .map(clickableModule -> clickableModule.getModule()).orElse(ItemStack.EMPTY).getCapability(PowerModuleCapability.POWER_MODULE);
        return moduleCap;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (super.mouseClicked(x, y, button)) {
            ModuleSelectionSubFrame sel = null;

            if (getRect().containsPoint(x, y)) {
                y += currentScrollPixels;
                for (ModuleSelectionSubFrame frame : categories.values()) {
                    if (frame.mouseClicked(x, y, button)) {
                        sel = frame;
                    }
                }

                if (sel != null && sel.getSelectedModule() != null) {
                    for (ModuleSelectionSubFrame frame : categories.values()) {
                        if (frame != sel) {
                            frame.resetSelection();
                        }
//                        else {
//                            final ClickableModule selectedOther = sel.getSelectedModule();
//
//                            if (selectedModule.map(module-> module.equals(selectedOther)).orElse(false)) {
//                                System.out.println("do something here???");
//                            }
//                        }
                    }
                }
            }
            return sel != null;
        }
        return false;
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        if (getRect().containsPoint(x, y)) {
            y += currentScrollPixels;
            if (!categories.isEmpty()) {
                for (ModuleSelectionSubFrame category : categories.values()) {
                    List<Component> tooltip = category.getToolTip(x, y);
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