package com.github.lehjr.powersuits.client.gui.modding.module.install_salvage;

import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableModule;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import com.github.lehjr.powersuits.client.gui.modding.module.tweak.ModuleSelectionFrame;
import com.github.lehjr.powersuits.client.gui.modding.module.tweak.ModuleSelectionSubFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CompatibleModuleDisplayFrame extends ModuleSelectionFrame {
    public CompatibleModuleDisplayFrame(ModularItemSelectionFrame itemSelectFrameIn, MusePoint2D topleft, MusePoint2D bottomright, Colour background, Colour topBorder, Colour bottomBorder) {
        super(itemSelectFrameIn, topleft, bottomright, background, topBorder, bottomBorder);
    }



    /**
     * Populates the module list
     * load this whenever a modular item is selected or when a module is installed
     */
    @Override
    public void loadModules(boolean preserveSelected) {
        this.lastPosition = null;

        NonNullList<ItemStack> possibleItems = NonNullList.create();

        // temp holder
        AtomicReference<Optional<ClickableModule>> selCopy = new AtomicReference<>(getSelectedModule());
        AtomicBoolean preserve = new AtomicBoolean(preserveSelected);
        categories.clear();

        target.getModularItemCapability().ifPresent(iModularItem -> {
            // get list of all possible modules
            ForgeRegistries.ITEMS.getValues().forEach(item -> {
                ItemStack module = new ItemStack(item, 1);
                if (iModularItem.isModuleValid(module) && !possibleItems.contains(module)) {
                    module.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(m->
                            getOrCreateCategory(m.getCategory()).addModule(module, -1));
                    possibleItems.add(module);
                }
            });
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
}
