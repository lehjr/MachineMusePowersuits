package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class ShockAbsorberModule extends AbstractPowerModule {
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final ToggleableModule moduleToggle;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.moduleToggle = new ToggleableModule(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY, MPSSettings::getModuleConfig, true) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "FE/m");
//                addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, 100);
//                addBaseProperty(MPSConstants.MULTIPLIER, 0, "%");
//                addTradeoffProperty(MPSConstants.POWER, MPSConstants.MULTIPLIER, 10);
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                moduleToggle.loadCapValues();
//                return moduleToggle;
//            });
//        }
//
//        /** ICapabilityProvider ----------------------------------------------------------------------- */
//        @Override
//        @Nonnull
//        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//            if (powerModuleCapability.isPresent()) {
//                return powerModuleCapability;
//            }
//            return LazyOptional.empty();
//        }
//    }
}