package com.github.lehjr.powersuits.capability;

import com.github.lehjr.numina.util.capabilities.heat.IHeatWrapper;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.render.IModelSpecNBT;
import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractModularPowerCap implements ICapabilityProvider {
    ItemStack itemStack;
    IModularItem modularItemCap;
    IHeatWrapper heatStorage;
    IModelSpecNBT modelSpec;
    EquipmentSlotType targetSlot;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        // All
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            modularItemCap.updateFromNBT();
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(()->modularItemCap));
        }

        // All
        if (cap == ModelSpecNBTCapability.RENDER) {
            return ModelSpecNBTCapability.RENDER.orEmpty(cap, LazyOptional.of(() -> modelSpec));
        }

        // All
        // update item handler to gain access to the battery module if installed
        if (cap == CapabilityEnergy.ENERGY) {
            modularItemCap.updateFromNBT();
            // armor first slot is armor plating, second slot is energy
            return modularItemCap.getStackInSlot(targetSlot.getSlotType() == EquipmentSlotType.Group.ARMOR ? 1 : 0).getCapability(cap, side);
        }

        return LazyOptional.empty();
    }
}
