package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class FlightControlModule extends AbstractPowerModule {
//
//    /**
//     * Legend has it that the nbt value may sometimes not be null. I have yet to witness this phenomenon for myself.
//     * @param stack The ItemStack
//     * @param nbt   NBT of this item serialized, or null.
//     * @return
//     */
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
//            this.moduleToggle = new ToggleableModule(module, ModuleCategory.MOVEMENT, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig, false) {{
//                addTradeoffProperty(MPSConstants.VERTICALITY, MPSConstants.FLIGHT_VERTICALITY, 1.0F, "%");
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