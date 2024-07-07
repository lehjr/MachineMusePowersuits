package lehjr.powersuits.client.gui.module.install_salvage;

import lehjr.numina.client.gui.clickable.ClickableModule;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.powersuits.client.gui.module.tweak.ModuleSelectionFrame;
import lehjr.powersuits.client.gui.module.tweak.ModuleSelectionSubFrame;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CompatibleModuleDisplayFrame extends ModuleSelectionFrame {
    NonNullList<ItemStack> possibleItems = NonNullList.create();

    public CompatibleModuleDisplayFrame(ModularItemSelectionFrame itemSelectFrameIn, Rect rect) {
        super(itemSelectFrameIn, rect);
    }

    public NonNullList<ItemStack> getPossibleItems() {
        return possibleItems;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        target.getModularItemCapability().ifPresent(iModularItem -> {
            for (ModuleSelectionSubFrame subframe : categories.values()) {
                for (ClickableModule module : subframe.moduleButtons) {
                    module.setInstalled(iModularItem.isModuleInstalled(module.getRegName()));
                }
            }
        });
    }

    /**
     * Populates the module list
     * load this whenever a modular item is selected or when a module is installed
     */
    @Override
    public void loadModules(boolean preserveSelected) {
        this.lastPosition = null;

        possibleItems = NonNullList.create();

        // temp holder
        AtomicReference<ClickableModule> selCopy = new AtomicReference<>(getSelectedModule());
        AtomicBoolean preserve = new AtomicBoolean(preserveSelected);
        categories.clear();

        target.getModularItemCapability().ifPresent(iModularItem -> {
            // get list of all possible modules
            BuiltInRegistries.ITEM.stream().distinct().forEach(item -> {
                boolean isModuleInstalled = iModularItem.isModuleInstalled(item);
                ItemStack module = new ItemStack(item, 1);
                if (iModularItem.isModuleValid(module) && !possibleItems.contains(module)) {
                    NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).ifPresent(m-> {
                        ClickableModule clickie = getOrCreateCategory(m.getCategory()).addModule(module, -1);
                        clickie.setInstalled(isModuleInstalled);
                    });

                    possibleItems.add(module);
                }
            });
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
}
