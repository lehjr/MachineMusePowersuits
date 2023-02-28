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

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ModuleSelectionFrame extends ScrollableFrame {
    protected ModularItemSelectionFrame target;
    protected Map<ModuleCategory, ModuleSelectionSubFrame> categories = new LinkedHashMap<>();
    protected Rect lastPosition;
    Optional<ClickableModule> selectedModule = Optional.ofNullable(null);
    LazyOptional<IPowerModule> moduleCap = LazyOptional.empty();
    VanillaFrameScrollBar scrollBar;


    public ModuleSelectionFrame(ModularItemSelectionFrame itemSelectFrameIn, Rect rect) {
        super(rect);
        this.target = itemSelectFrameIn;
        this.scrollBar = new VanillaFrameScrollBar(this, "scrollbar");
        this.scrollBar.setValue(0);
        this.currentScrollPixels = 0;
        loadModules(false);
    }

    protected ModuleSelectionSubFrame getOrCreateCategory(ModuleCategory category) {
        if (categories.containsKey(category)) {
            return categories.get(category);
        } else {
            Rect position = new Rect(
                    left() + 4,
                    top() + 4,
                    right() - 4,
                    top() + 36);
            position.setBelow(lastPosition);
            lastPosition = position;
            ModuleSelectionSubFrame frame = new ModuleSelectionSubFrame(category, position);

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
        if (!preserveSelected) {
            setCurrentScrollPixels(0);
        }

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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        super.render(matrixStack, mouseX, mouseY, partialTick);
        this.scrollBar.render(matrixStack, mouseX, mouseY, partialTick);

        Optional<IModularItem> iModularItem = target.getModularItemCapability();

        categories.values().forEach(frame ->{
            frame.refreshButtonPositions();
        });

        if (iModularItem.isPresent()) {
            int totalHeight=0;

            for (ModuleSelectionSubFrame frame : categories.values()) {
//                totalSize = (int) Math.max(frame.border.bottom() - this.top(), totalSize);
                totalHeight += frame.border.height();
            }
            setTotalSize(totalHeight);

            super.preRender(matrixStack, mouseX, mouseY, partialTick);
            matrixStack.pushPose();
            matrixStack.translate(0, (float)-currentScrollPixels, 0);
            drawItems(matrixStack, partialTick);
            drawSelection(matrixStack);
            matrixStack.popPose();
            super.postRender(mouseX, mouseY, partialTick);
        } else {
            super.render(matrixStack, mouseX, mouseY, partialTick);
        }
    }

    private void drawItems(PoseStack matrixStack, float partialTicks) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.drawPartial(matrixStack, (int) (this.currentScrollPixels + top() + 4),
                    (int) (this.currentScrollPixels + top() + height() - 4), partialTicks);
        }
    }

    float getzLevel() {
        return Minecraft.getInstance().screen.getBlitOffset();
    }

    private void drawSelection(PoseStack matrixStack) {
        getSelectedModule().ifPresent(module ->{
            MusePoint2D pos = module.center();
            if (pos.y() > this.currentScrollPixels + top() + 4 && pos.y() < this.currentScrollPixels + top() + height() - 4) {
                NuminaRenderer.drawCircleAround(matrixStack, pos.x(), pos.y(), 10, getzLevel());
            }
        });
    }

    public Optional<ClickableModule> getSelectedModule() {
        if (!categories.isEmpty()) {
            return categories.values().stream().filter(frame -> frame !=null && frame.getSelectedModule() != null).findFirst().map(frame->frame.getSelectedModule());
        }
        return Optional.empty();
//        return selectedModule;
    }

    public LazyOptional<IPowerModule> getModuleCap() {
        return getSelectedModule()
                .map(clickableModule -> clickableModule.getModule()).orElse(ItemStack.EMPTY).getCapability(PowerModuleCapability.POWER_MODULE);
//        return moduleCap;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
        scrollBar.setValue(currentScrollPixels);
        return retVal;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mouseY);
        setCurrentScrollPixels(scrollBar.getValue());
        super.update(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (scrollBar.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(scrollBar.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (super.mouseClicked(mouseX, mouseY, button)) {
            ModuleSelectionSubFrame sel = null;

            if (containsPoint(mouseX, mouseY)) {
                mouseY += currentScrollPixels;
                for (ModuleSelectionSubFrame frame : categories.values()) {
                    if (frame.mouseClicked(mouseX, mouseY, button)) {
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
        if (containsPoint(x, y)) {
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