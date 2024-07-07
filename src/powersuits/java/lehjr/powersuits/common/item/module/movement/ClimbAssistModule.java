package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class ClimbAssistModule extends AbstractPowerModule {
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final Ticker ticker;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig);
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                ticker.loadCapValues();
//                return ticker;
//            });
//        }
//
//        class Ticker extends PlayerTickModule {
//            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config, true);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, ItemStack item) {
//////                ForgeMod#STEP_HEIGHT
////                player.getAttribute()
////                player.setMaxUpStep().maxUpStep = 1.001F;
//            }
//
//            @Override
//            public void onPlayerTickInactive(Player player, ItemStack item) {
////                if (player.maxUpStep == 1.001F) {
////                    player.maxUpStep = 0.5001F;
////                }
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