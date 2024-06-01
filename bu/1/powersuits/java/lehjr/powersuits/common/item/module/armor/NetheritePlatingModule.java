package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * TODO
 */
public class NetheritePlatingModule extends AbstractPowerModule {
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

            powerModule = new PowerModule(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY, MPSSettings::getModuleConfig) {
                @Override
                public int getTier() {
                    return 3;
                }

                @Override
                public String getModuleGroup() {
                    return "Armor";
                }
                {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, 7.5, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, 750);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, 1.5F);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, 3.5F);
                }};

            powerModuleHolder = LazyOptional.of(() -> powerModule);
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}