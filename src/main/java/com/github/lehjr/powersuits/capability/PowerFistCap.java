package com.github.lehjr.powersuits.capability;

import com.github.lehjr.numina.util.capabilities.heat.HeatCapability;
import com.github.lehjr.numina.util.capabilities.heat.HeatItemWrapper;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.ModeChangingModularItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.NuminaRangedWrapper;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.powersuits.client.render.PowerFistSpecNBT;
import com.github.lehjr.powersuits.config.MPSSettings;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PowerFistCap extends AbstractModularPowerCap {
    public PowerFistCap(@Nonnull ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.targetSlot = EquipmentSlotType.MAINHAND;

        this.modularItemCap = new ModeChangingModularItem(itemStack, 40)  {{
            Map<EnumModuleCategory, NuminaRangedWrapper> rangedWrapperMap = new HashMap<>();
            rangedWrapperMap.put(EnumModuleCategory.ENERGY_STORAGE, new NuminaRangedWrapper(this, 0, 1));
            rangedWrapperMap.put(EnumModuleCategory.NONE, new NuminaRangedWrapper(this, 1, this.getSlots() - 1));
            this.setRangedWrapperMap(rangedWrapperMap);
        }};
        this.modelSpec = new PowerFistSpecNBT(itemStack);
        this.heatStorage = new HeatItemWrapper(itemStack, MPSSettings.getMaxHeatPowerFist());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        if (cap == HeatCapability.HEAT) {
            heatStorage.updateFromNBT();
            return HeatCapability.HEAT.orEmpty(cap, LazyOptional.of(()->heatStorage));
        }

        return super.getCapability(cap, side);
    }
}
