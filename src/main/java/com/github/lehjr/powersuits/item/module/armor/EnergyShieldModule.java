package com.github.lehjr.powersuits.item.module.armor;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.heat.HeatCapability;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class EnergyShieldModule extends AbstractPowerModule {
    public EnergyShieldModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
                ticker = new Ticker(module, EnumModuleCategory.ARMOR, EnumModuleTarget.ARMORONLY, MPSSettings::getModuleConfig, true);
                ticker.addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_VALUE_ENERGY, 6, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                ticker.addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_ENERGY_CONSUMPTION, 5000, "FE");
                ticker.addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, HeatCapability.MAXIMUM_HEAT, 500);
                ticker.addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, 0.25F);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            if (ticker == null) {
                return LazyOptional.empty();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config, boolean defBool) {
                super(module, category, target, config, defBool);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack item) {
                int energy = ElectricItemUtils.getPlayerEnergy(player);
                int energyUsage = (int) applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION);

                // turn off module if energy is too low. This will fire on both sides so no need to sync
                if (energy < energyUsage) {
                    this.toggleModule(false);
                }
            }
        }
    }
}