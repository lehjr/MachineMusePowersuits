package lehjr.powersuits.common.item.module.miningenchantment;


import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class FortuneModule extends AbstractPowerModule {
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final EnchantmentModule enchantmentModule;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.enchantmentModule = new TickingEnchantment(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.FORTUNE_ENERGY_CONSUMPTION, 500, "FE");
//                addTradeoffProperty(MPSConstants.ENCHANTMENT_LEVEL, MPSConstants.FORTUNE_ENERGY_CONSUMPTION, 9500);
//                addIntTradeoffProperty(MPSConstants.ENCHANTMENT_LEVEL, MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, 3, "", 1, 1);
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                enchantmentModule.loadCapValues();
//                return enchantmentModule;
//            });
//        }
//
//        class TickingEnchantment extends EnchantmentModule {
//            boolean added;
//            boolean removed;
//            public TickingEnchantment(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config, false);
//                // setting to and loading these just allow values to be persistant when capability reloads
//                added = TagUtils.getModuleBooleanOrFalse(module, "added");
//                removed = TagUtils.getModuleBooleanOrFalse(module, "removed");
//            }
//
//            @Override
//            public int getEnergyUsage() {
//                return (int) applyPropertyModifiers(MPSConstants.FORTUNE_ENERGY_CONSUMPTION);
//            }
//
//            @Override
//            public Enchantment getEnchantment() {
//                return Enchantments.BLOCK_FORTUNE;
//            }
//
//            @Override
//            public int getLevel() {
//                return (int) applyPropertyModifiers(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL);
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