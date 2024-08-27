package com.lehjr.numina.common.capabilities.fluid;//package com.lehjr.numina.common.capability.fluid;
//
//import com.lehjr.numina.common.capability.NuminaCapabilities;
//import com.lehjr.numina.common.capability.module.powermodule.IPowerModule;
//import com.lehjr.numina.common.constants.NuminaConstants;
//import net.minecraft.core.component.DataComponentType;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.neoforge.fluids.FluidStack;
//import net.neoforged.neoforge.fluids.SimpleFluidContent;
//import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
//
//import javax.annotation.Nonnull;
//import java.util.function.Supplier;
//
///**
// * Neoforge FluidHandlerItemStack with capacity set from a PowerModule capability
// */
//public class FluidHandlerPowerModule implements IFluidHandlerItem {
//    protected final Supplier<DataComponentType<SimpleFluidContent>> componentType;
//    protected ItemStack container;
//
//    /**
//     * @param componentType The data component type to use for data storage.
//     * @param container     The container itemStack, data is stored on it directly under a component.
//     */
//    public FluidHandlerPowerModule(Supplier<DataComponentType<SimpleFluidContent>> componentType, ItemStack container) {
//        this.componentType = componentType;
//        this.container = container;
//    }
//
//    @Override
//    public ItemStack getContainer() {
//        return container;
//    }
//
//    public FluidStack getFluid() {
//        return container.getOrDefault(componentType, SimpleFluidContent.EMPTY).copy();
//    }
//
//    protected void setFluid(FluidStack fluid) {
//        container.set(componentType, SimpleFluidContent.copyOf(fluid));
//    }
//
//    @Override
//    public int getTanks() {
//        return 1;
//    }
//
//    @Override
//    public FluidStack getFluidInTank(int tank) {
//        return getFluid();
//    }
//
//    @Override
//    public int getTankCapacity(int tank) {
//        IPowerModule pm = getPowerModule(getContainer());
//        if(pm != null) {
//            return pm.applyPropertyModifiers(NuminaConstants.FLUID_TANK_SIZE);
//        }
//        return 0;
//    }
//
//    @Override
//    public boolean isFluidValid(int tank, FluidStack stack) {
//        return true;
//    }
//
//    @Override
//    public int fill(FluidStack resource, FluidAction doFill) {
//        if (container.getCount() != 1 || resource.isEmpty() || !canFillFluidType(resource)) {
//            return 0;
//        }
//
//        int capacity = getTankCapacity(0);
//
//        FluidStack contained = getFluid();
//        if (contained.isEmpty()) {
//            int fillAmount = Math.min(capacity, resource.getAmount());
//
//            if (doFill.execute()) {
//                setFluid(resource.copyWithAmount(fillAmount));
//            }
//
//            return fillAmount;
//        } else {
//            if (FluidStack.isSameFluidSameComponents(contained, resource)) {
//                int fillAmount = Math.min(capacity - contained.getAmount(), resource.getAmount());
//
//                if (doFill.execute() && fillAmount > 0) {
//                    contained.grow(fillAmount);
//                    setFluid(contained);
//                }
//
//                return fillAmount;
//            }
//
//            return 0;
//        }
//    }
//
//    @Override
//    public FluidStack drain(FluidStack resource, FluidAction action) {
//        if (container.getCount() != 1 || resource.isEmpty() || !FluidStack.isSameFluidSameComponents(resource, getFluid())) {
//            return FluidStack.EMPTY;
//        }
//        return drain(resource.getAmount(), action);
//    }
//
//    @Override
//    public FluidStack drain(int maxDrain, FluidAction action) {
//        if (container.getCount() != 1 || maxDrain <= 0) {
//            return FluidStack.EMPTY;
//        }
//
//        FluidStack contained = getFluid();
//        if (contained.isEmpty() || !canDrainFluidType(contained)) {
//            return FluidStack.EMPTY;
//        }
//
//        final int drainAmount = Math.min(contained.getAmount(), maxDrain);
//
//        FluidStack drained = contained.copyWithAmount(drainAmount);
//
//        if (action.execute()) {
//            contained.shrink(drainAmount);
//            if (contained.isEmpty()) {
//                setContainerToEmpty();
//            } else {
//                setFluid(contained);
//            }
//        }
//
//        return drained;
//    }
//
//    public boolean canFillFluidType(FluidStack fluid) {
//        return true;
//    }
//
//    public boolean canDrainFluidType(FluidStack fluid) {
//        return true;
//    }
//
//    /**
//     * Override this method for special handling.
//     * Can be used to swap out or destroy the container.
//     */
//    protected void setContainerToEmpty() {
//        container.remove(componentType);
//    }
//
//    /**
//     * Destroys the container item when it's emptied.
//     */
//    public static class Consumable extends net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack {
//        public Consumable(Supplier<DataComponentType<SimpleFluidContent>> componentType, ItemStack container, int capacity) {
//            super(componentType, container, capacity);
//        }
//
//        @Override
//        protected void setContainerToEmpty() {
//            super.setContainerToEmpty();
//            container.shrink(1);
//        }
//    }
//
//    /**
//     * Swaps the container item for a different one when it's emptied.
//     */
//    public static class SwapEmpty extends net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack {
//        protected final ItemStack emptyContainer;
//
//        public SwapEmpty(Supplier<DataComponentType<SimpleFluidContent>> componentType, ItemStack container, ItemStack emptyContainer, int capacity) {
//            super(componentType, container, capacity);
//            this.emptyContainer = emptyContainer;
//        }
//
//        @Override
//        protected void setContainerToEmpty() {
//            super.setContainerToEmpty();
//            container = emptyContainer;
//        }
//    }
//
//    static IPowerModule getPowerModule(@Nonnull ItemStack module) {
//        return module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
//    }
//}