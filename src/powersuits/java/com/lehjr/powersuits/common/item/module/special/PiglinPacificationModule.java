package com.lehjr.powersuits.common.item.module.special;

import com.lehjr.numina.common.capabilities.module.powermodule.*;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PiglinPacificationModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        private final PowerModule powerModule;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        ItemStack module;

        public CapProvider(final ItemStack module) {
            this.module = module;
            powerModule = new PowerModule(module, ModuleCategory.SPECIAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
            powerModuleHolder = LazyOptional.of(() -> powerModule);
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> capabilityPowerModule = CapabilityPowerModule.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (capabilityPowerModule.isPresent()) {
                return capabilityPowerModule;
            }
            return LazyOptional.empty();
        }
    }
}