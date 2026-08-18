package lehjr.powersuits.common.capabilities.armor;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lehjr.numina.common.registration.NuminaCapabilities.getModularItem;

public class PowerArmorFluidWrapper implements IFluidHandlerItem {
    int moduleIndex = -1;
    // TODO: use this map in if multiple modules have fluid..
//    Map<IFluidHandlerItem, Integer> fluidHandlers = new HashMap<>();
    IFluidHandlerItem fluidHandler = null;
    IModularItem modularItem;
    ItemStack container = ItemStack.EMPTY;

    public PowerArmorFluidWrapper(ItemStack host) {
        modularItem = getModularItem(host);
        if(modularItem != null) {
            Pair<Integer, Integer> range = modularItem.getRangeForCategory(ModuleCategory.FLUID_STORAGE);
            if(range.getFirst() >= 0 && range.getSecond() >= range.getFirst()) {
                for(int i = range.getFirst(); i < range.getSecond(); i++) {
                    ItemStack module = modularItem.getStackInSlot(i);
                    fluidHandler = module.getCapability(Capabilities.FluidHandler.ITEM, null);
                    if(fluidHandler != null) {
                        moduleIndex = i;
                        container = module;
                        break;
                    }
                }
            }

            // --------------------------------------
//            Pair<Integer, Integer> range = modularItem.getRangeForCategory(ModuleCategory.FLUID_STORAGE);
//            if(range.getFirst() >= 0 && range.getSecond() >= range.getFirst()) {
//                for(int i = range.getFirst(); i < range.getSecond(); i++) {
//                    ItemStack module = modularItem.getStackInSlot(i);
//                    IFluidHandler fluidHandler= module.getCapability(Capabilities.FluidHandler.ITEM, null);
//                    if(fluidHandler != null) {
//                        fluidHandlers.put(fluidHandler, moduleIndex);
//                        moduleIndex = i;
//                        container = module;
//                    }
//                }
//            }
        }
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    @Override
    public int getTanks() {
        if(fluidHandler != null) {
            return fluidHandler.getTanks();
        }
        return 0;
    }

    @Override
    public FluidStack getFluidInTank(int i) {
        if(fluidHandler != null) {
            return fluidHandler.getFluidInTank(i);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int i) {
        if(fluidHandler != null) {
            return fluidHandler.getTankCapacity(i);
        }
        return 0;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        if(fluidHandler != null) {
            return fluidHandler.isFluidValid(i, fluidStack);
        }
        return false;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        if(fluidHandler != null && modularItem != null) {
            int result = fluidHandler.fill(fluidStack, fluidAction);
            modularItem.updateModuleInSlot(moduleIndex, fluidHandler.getContainer());
            return result;
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        if(fluidHandler != null && modularItem != null) {
            FluidStack result = fluidHandler.drain(fluidStack, fluidAction);
            modularItem.updateModuleInSlot(moduleIndex, fluidHandler.getContainer());
            return result;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int i, FluidAction fluidAction) {
        if(fluidHandler != null && modularItem != null) {
            FluidStack result = fluidHandler.drain(i, fluidAction);
            modularItem.updateModuleInSlot(moduleIndex, fluidHandler.getContainer());
            return result;
        }
        return FluidStack.EMPTY;
    }
}
