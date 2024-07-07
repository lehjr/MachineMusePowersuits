package lehjr.powersuits.common.item.module.miningenchantment;

import lehjr.powersuits.common.item.module.AbstractPowerModule;


// Note: tried as an enchantment, but failed to function properly due to how block breaking code works
public class AquaAffinityModule extends AbstractPowerModule {
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final EnchantmentModule enchantmentModule;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.enchantmentModule = new TickingEnchantment(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 50000, "FE");
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                enchantmentModule.loadCapValues();
//                return enchantmentModule;
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
//                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//            }
//
//            @Override
//            public Enchantment getEnchantment() {
//                return Enchantments.AQUA_AFFINITY;
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