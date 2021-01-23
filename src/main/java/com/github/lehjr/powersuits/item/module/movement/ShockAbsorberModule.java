package com.github.lehjr.powersuits.item.module.movement;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.ToggleableModule;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShockAbsorberModule extends AbstractPowerModule {
    public ShockAbsorberModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IToggleableModule moduleToggle;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.moduleToggle = new ToggleableModule(module, EnumModuleCategory.MOVEMENT, EnumModuleTarget.FEETONLY, MPSSettings::getModuleConfig, true);
            this.moduleToggle.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "FE/m");
            this.moduleToggle.addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, 100);
            this.moduleToggle.addBaseProperty(MPSConstants.MULTIPLIER, 0, "%");
            this.moduleToggle.addTradeoffProperty(MPSConstants.POWER, MPSConstants.MULTIPLIER, 10);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> moduleToggle));
        }
    }
}