package net.machinemuse.powersuits.client.gui.module.tinker;

import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;
import net.machinemuse.numina.client.gui.scrollable.ScrollableFrame;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.client.sound.Musique;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.module.IPowerModule;
import net.machinemuse.powersuits.client.gui.common.ClickableModule;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.client.sound.SoundDictionary;
import net.machinemuse.powersuits.common.base.ModuleManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.SoundCategory;

import java.util.*;

public class ModuleSelectionFrame extends ScrollableFrame {
    protected ItemSelectionFrame target;
    protected Map<String, ModuleSelectionSubFrame> categories = new LinkedHashMap<>();
    protected List<ClickableModule> moduleButtons = new LinkedList<>();
    protected int selectedModule = -1;
    protected IPowerModule prevSelection;
    protected ClickableItem lastItem;
    protected MuseRect lastPosition;

    public ModuleSelectionFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour borderColour, Colour insideColour, ItemSelectionFrame target) {
        super(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
        this.target = target;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.refreshButtonPositions();
        }
        if (target.getSelectedItem() != null) {
            if (lastItem != target.getSelectedItem()) {
                loadModules();
            }
            this.totalSize = 0;
            for (ModuleSelectionSubFrame frame : categories.values()) {
                totalSize = (int) Math.max(frame.border.bottom() - top(), totalSize);
            }
            this.currentScrollPixels = Math.min(currentScrollPixels, getMaxScrollPixels());

            super.preRender(mouseX, mouseY, partialTicks);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, -currentScrollPixels, 0);
            drawItems();
            drawSelection();
            GlStateManager.popMatrix();
            super.postRender(mouseX, mouseY, partialTicks);
        }
    }

    private void drawItems() {
        for (ModuleSelectionSubFrame frame : categories.values()) {
            frame.drawPartial((int) (this.currentScrollPixels + top() + 4),
                    (int) (this.currentScrollPixels + top() + height() - 4));
        }
    }

    private void drawSelection() {
        ClickableModule module = getSelectedModule();
        if (module != null) {
            MusePoint2D pos = moduleButtons.get(selectedModule).getPosition();
            if (pos.getY() > this.currentScrollPixels + top() + 4 && pos.getY() < this.currentScrollPixels + top() + height() - 4) {
                MuseRenderer.drawCircleAround(pos.getX(), pos.getY(), 10);
            }
        }
    }

    public ClickableModule getSelectedModule() {
        if (moduleButtons.size() > selectedModule && selectedModule != -1) {
            return moduleButtons.get(selectedModule);
        } else {
            return null;
        }
    }

    public void loadModules() {
        this.lastPosition = null;
        ClickableItem selectedItem = target.getSelectedItem();
        if (selectedItem != null) {
            moduleButtons = new LinkedList<>();
            categories = new LinkedHashMap<>();

            List<IPowerModule> workingModules = ModuleManager.INSTANCE.getValidModulesForItem(selectedItem.getItem());

            // Prune the list of disallowed modules, if not installed on this item.
            for (Iterator<IPowerModule> it = workingModules.iterator(); it.hasNext(); ) {
                IPowerModule module = it.next();
                if (!module.isAllowed() && !ModuleManager.INSTANCE.itemHasModule(selectedItem.getItem(), module.getDataName())) {
                    it.remove();
                }
            }

            if (workingModules.size() > 0) {
                this.selectedModule = -1;
                for (IPowerModule module : workingModules) {
                    ModuleSelectionSubFrame frame = getOrCreateCategory(module.getCategory().getName());
                    ClickableModule moduleClickable = frame.addModule(module);
                    // Indicate installed modules
                    if (!module.isAllowed()) {
                        // If a disallowed module made it to the list, indicate
                        // it as disallowed
                        moduleClickable.setAllowed(false);
                    } else if (ModuleManager.INSTANCE.itemHasModule(selectedItem.getItem(), module.getDataName())) {
                        moduleClickable.setInstalled(true);
                    }
                    if (moduleClickable.getModule().equals(this.prevSelection)) {
                        this.selectedModule = moduleButtons.size();
                    }
                    moduleButtons.add(moduleClickable);
                }
            }
            for (ModuleSelectionSubFrame frame : categories.values()) {
                frame.refreshButtonPositions();
            }
        }
    }

    private ModuleSelectionSubFrame getOrCreateCategory(String category) {
        if (categories.containsKey(category)) {
            return categories.get(category);
        } else {
            MuseRect position = new MuseRect(
                    left() + 4,
                    top() + 4,
                    right() - 4,
                    top() + 32);
            position.setBelow(lastPosition);
            lastPosition = position;
            ModuleSelectionSubFrame frame = new ModuleSelectionSubFrame(
                    category,
                    position);

            categories.put(category, frame);
            return frame;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            if (left() < mouseX && right() > mouseX && top() < mouseY && bottom() > mouseY) {
                mouseY += currentScrollPixels;
                // loadModules();
                int i = 0;
                for (ClickableModule module : moduleButtons) {
                    if (module.containsPoint(mouseX, mouseY)) {
                        Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, SoundCategory.BLOCKS, 1, null);
                        selectedModule = i;
                        prevSelection = module.getModule();
                        return true;
                    } else {
                        i++;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        if (left() < mouseX && right() > mouseX && top() < mouseY && bottom() > mouseY) {
            mouseY += currentScrollPixels;
            if (moduleButtons != null) {
                int moduleHover = -1;
                int i = 0;
                for (ClickableModule module : moduleButtons) {
                    if (module.containsPoint(mouseX, mouseY)) {
                        moduleHover = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (moduleHover > -1) {
                    return moduleButtons.get(moduleHover).getToolTip(mouseX, mouseY);
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}