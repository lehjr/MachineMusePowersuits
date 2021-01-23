package com.github.lehjr.powersuits.item.module.weapon;

import com.github.lehjr.numina.util.capabilities.module.powermodule.*;
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

public class MeleeAssistModule extends AbstractPowerModule {
    public MeleeAssistModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPowerModule moduleCap;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.moduleCap = new PowerModule(module, EnumModuleCategory.WEAPON, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.moduleCap.addBaseProperty(MPSConstants.PUNCH_ENERGY, 10, "FE");
            this.moduleCap.addBaseProperty(MPSConstants.PUNCH_DAMAGE, 2, "pt");
            this.moduleCap.addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.PUNCH_ENERGY, 1000, "FE");
            this.moduleCap.addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.PUNCH_DAMAGE, 8, "pt");
            this.moduleCap.addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.PUNCH_ENERGY, 200, "FE");
            this.moduleCap.addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.PUNCH_KNOCKBACK, 1, "P");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> moduleCap));
        }
    }
}