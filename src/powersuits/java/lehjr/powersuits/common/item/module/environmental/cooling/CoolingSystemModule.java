package lehjr.powersuits.common.item.module.environmental.cooling;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

/**
 * Created by Eximius88 on 1/17/14.
 */
public class CoolingSystemModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true) {{
//                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
//                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                ticker.loadCapValues();
//                return ticker;
//            });
//        }
//
//        class Ticker extends PlayerTickModule {
//            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
//                super(module, category, target, config, defBool);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//                double heatBefore = HeatUtils.getPlayerHeat(player);
//
//                HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
//                double cooling = heatBefore - HeatUtils.getPlayerHeat(player);
//                ElectricItemUtils.drainPlayerEnergy(player, (int) (cooling * applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION)));
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