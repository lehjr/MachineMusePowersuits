package lehjr.powersuits.common.item.module.miningenchantment;

import lehjr.powersuits.common.item.module.AbstractPowerModule;


public class SilkTouchModule extends AbstractPowerModule {
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final TickingEnchantment tickingEnchantment;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.tickingEnchantment = new TickingEnchantment(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, 50000, "FE");
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                tickingEnchantment.loadCapValues();
//                return tickingEnchantment;
//            });
//        }
//
//        class TickingEnchantment extends EnchantmentModule {
//            public TickingEnchantment(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config, false);
//            }
//
//            @Override
//            public int getEnergyUsage() {
//                return (int) (applyPropertyModifiers(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION) * getLevel());
//            }
//
//            @Override
//            public Enchantment getEnchantment() {
//                return Enchantments.SILK_TOUCH;
//            }
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