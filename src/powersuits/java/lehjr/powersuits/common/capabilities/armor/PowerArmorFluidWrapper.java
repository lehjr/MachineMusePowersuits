package lehjr.powersuits.common.capabilities.armor;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;

public class PowerArmorFluidWrapper implements IFluidHandlerItem {
    private static ItemStack itemStack;

    public PowerArmorFluidWrapper(ItemStack container) {
        itemStack = container;
    }

    @Nullable
    IModularItem getModularItem() {
        return NuminaCapabilities.getModularItem(itemStack);
    }

    @Nullable
    IFluidHandlerItem getFluidHandler() {
        IModularItem modularItem = getModularItem();
        if(modularItem != null) {
            Pair<Integer, Integer> range = modularItem.getRangeForCategory(ModuleCategory.FLUID_STORAGE);
            for(int i = range.getFirst(); i < range.getSecond(); i++) {
                ItemStack module = modularItem.getStackInSlot(i);
                IFluidHandlerItem fluidHandler = module.getCapability(Capabilities.FluidHandler.ITEM, null);
                if(fluidHandler != null) {
                    return fluidHandler;
                }
            }
        }
        return null;
    }

    @Override
    public ItemStack getContainer() {
        IModularItem modularItem = getModularItem();
        if(modularItem != null) {
            Pair<Integer, Integer> range = modularItem.getRangeForCategory(ModuleCategory.FLUID_STORAGE);
            for(int i = range.getFirst(); i < range.getSecond(); i++) {
                ItemStack module = modularItem.getStackInSlot(i);
                IFluidHandlerItem fluidHandler = module.getCapability(Capabilities.FluidHandler.ITEM, null);
                if(fluidHandler != null) {
                    return module;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getTanks() {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.getTanks();
        }
        return 0;
    }

    @Override
    public FluidStack getFluidInTank(int i) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.getFluidInTank(i);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int i) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.getTankCapacity(i);
        }
        return 0;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.isFluidValid(i, fluidStack);
        }
        return false;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.fill(fluidStack, fluidAction);
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.drain(fluidStack, fluidAction);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int i, FluidAction fluidAction) {
        IFluidHandlerItem fluidHandler = getFluidHandler();
        if(fluidHandler != null) {
            return fluidHandler.drain(i, fluidAction);
        }
        return FluidStack.EMPTY;
    }
}
