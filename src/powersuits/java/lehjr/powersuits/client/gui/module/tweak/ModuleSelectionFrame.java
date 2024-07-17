package lehjr.powersuits.client.gui.module.tweak;

import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.gui.geometry.SwirlyMuseCircle;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
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
    ClickableModule selectedModule = null;
    VanillaFrameScrollBar scrollBar;
    SwirlyMuseCircle circle;

    public ModuleSelectionFrame(ModularItemSelectionFrame itemSelectFrameIn, Rect rect) {
        super(rect);
        this.target = itemSelectFrameIn;
        this.scrollBar = new VanillaFrameScrollBar(this, "scrollbar");
        this.scrollBar.setValue(0);
        this.currentScrollPixels = 0;
        loadModules(false);
        circle = new SwirlyMuseCircle();
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
                this.selectedModule = thing.getSelectedModule();
                this.onSelected();
            });
            return frame;
        }
    }

    /**
     * This is required for the new ItemStack data setup in order for changes to propagate through the GUI code
     */
    public void refreshModule() {
        if(selectedModule != null) {
            target.getModularItemCapability().ifPresent(iModularItem -> {
                final int selected = selectedModule.getInventorySlot();
                ItemStack otherModule = iModularItem.getStackInSlot(selected);
                if(selectedModule.getModule().is(otherModule.getItem())) {
                    selectedModule.setModule(otherModule);
                }
            });
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
        AtomicReference<ClickableModule> selCopy = new AtomicReference<>(getSelectedModule());
        AtomicBoolean preserve = new AtomicBoolean(preserveSelected);
        categories.clear();

        target.getModularItemCapability().ifPresent(iModularItem -> {
            if (!preserve.get()) {
                selCopy.set(null);
            } else if(selectedModule != null) {
                selCopy.set(new ClickableModule(selectedModule.getModule(), new MusePoint2D(0, 0), -1, selectedModule.category));
            }

            // Occupied slots in the Modular Item
            for (int index = 0; index < iModularItem.getSlots(); index++) {
                ItemStack module = iModularItem.getStackInSlot(index);
                if (!module.isEmpty()) {
                    int finalIndex = index;
                    NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).ifPresent(m -> {
                        if (m.isAllowed()) {
                            getOrCreateCategory(m.getCategory()).addModule(module, finalIndex);
                        }
                    });
                }
            }
        });

        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.refreshButtonPositions();
            // actually preserve the module selection during call to init due to it being called on gui resize
            if(preserveSelected && selCopy.get() != null && frame.category == selCopy.get().category) {
                for (ClickableModule button : frame.moduleButtons) {
                    if (ItemStack.isSameItem(button.getModule(), selCopy.get().getModule())) {
                        frame.selectedModule = frame.moduleButtons.indexOf(button);
                        preserveSelected = false; // just to skip checking the rest
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
        this.scrollBar.render(gfx, mouseX, mouseY, partialTick);

        Optional<IModularItem> iModularItem = target.getModularItemCapability();

        categories.values().forEach(ModuleSelectionSubFrame::refreshButtonPositions);

        if (iModularItem.isPresent()) {
            int totalHeight=0;

            for (ModuleSelectionSubFrame frame : categories.values()) {
                //                totalSize = (int) Math.max(frame.border.bottom() - this.top(), totalSize);
                totalHeight += (int)frame.border.height();
            }
            setTotalSize(totalHeight);

            super.preRender(gfx, mouseX, mouseY, partialTick);
            gfx.pose().pushPose();
            gfx.pose().translate(0, (float)-currentScrollPixels, 0);
            drawItems(gfx, partialTick);
            drawSelection(gfx);
            gfx.pose().popPose();
            super.postRender(gfx, mouseX, mouseY, partialTick);
        } else {
            super.render(gfx, mouseX, mouseY, partialTick);
        }
    }

    private void drawItems(GuiGraphics gfx, float partialTicks) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.drawPartial(gfx, (int) (this.currentScrollPixels + top() + 4),
                    (int) (this.currentScrollPixels + top() + height() - 4), partialTicks);
        }
    }

    public void drawSelection(GuiGraphics gfx) {
        if (selectedModule != null) {
            MusePoint2D pos = selectedModule.center();
            if (pos.y() > this.currentScrollPixels + top() + 4 && pos.y() < this.currentScrollPixels + top() + height() - 4) {
                IconUtils.drawCircleAround(circle, gfx.pose(), pos.x(), pos.y(), 10, 100);
            }
        }
    }

    @Nullable
    public ClickableModule getSelectedModule() {
        return selectedModule;
    }

    @Nullable
    public IPowerModule getModuleCap() {
        if (selectedModule != null) {
            return selectedModule.getModule().getCapability(NuminaCapabilities.Module.POWER_MODULE);
        }
        return null;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        boolean retVal = super.mouseScrolled(mouseX, mouseY, dWheel);
        scrollBar.setValue(currentScrollPixels);
        return retVal;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        refreshModule();
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
                    else {
                        final ClickableModule selectedOther = sel.getSelectedModule();
                        if(selectedModule!= selectedOther) {
                            NuminaLogger.logDebug("do something here???");
                            selectedModule = selectedOther;
                            onSelected();
                        } else {
                            NuminaLogger.logDebug("not updating here either, selectedOther: "
                                    + (selectedOther == null ? "NULL" : selectedOther.getModule() +
                                    ", selectedModule: ") + (selectedModule == null ? "NULL": selectedOther.getModule()));
                        }
                    }
                }
            } else {
                NuminaLogger.logDebug("not updating ");
            }


            return sel != null;
        }
        return false;
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        if (containsPoint(x, y)) {
            y += (int)currentScrollPixels;
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
            NuminaLogger.logDebug("onSelected running");
            this.doThis.onSelected(this);
        }
    }

    public interface OnSelectNewModule {
        void onSelected(ModuleSelectionFrame doThis);
    }
}