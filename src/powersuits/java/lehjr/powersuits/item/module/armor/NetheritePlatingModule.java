package lehjr.powersuits.item.module.armor;

import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.util.capabilities.heat.HeatCapability;
import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * TODO
 */
public class NetheritePlatingModule extends AbstractPowerModule {
    public NetheritePlatingModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        private final PowerModule powerModule;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        ItemStack module;

        public CapProvider(final ItemStack module) {
            this.module = module;

            powerModule = new PowerModule(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY, MPSSettings::getModuleConfig) {
                @Override
                public int getTier() {
                    return 2;
                }

                @Override
                public String getModuleGroup() {
                    return "Armor";
                }

                {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, 7.5, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(HeatCapability.MAXIMUM_HEAT, 750);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, 1.5F);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, 3.5F);
                }};

            powerModuleHolder = LazyOptional.of(() -> powerModule);
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}