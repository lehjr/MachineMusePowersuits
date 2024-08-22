package com.lehjr.powersuits.common.item.module.energy.generation.heat;

import com.lehjr.powersuits.common.item.module.AbstractPowerModule;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
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
//            this.ticker = new Ticker(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
//                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
//            }};
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
//                double currentHeat = HeatUtils.getPlayerHeat(player);
//                double maxHeat = HeatUtils.getPlayerMaxHeat(player);
//                if (player.level().getGameTime() % 20 == 0) {
//                    if (player.isOnFire()) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (4 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)));
//                    } else if (currentHeat >= 200) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (2 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)));
//                    } else if ((currentHeat / maxHeat) >= 0.5) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) applyPropertyModifiers(MPSConstants.ENERGY_GENERATION));
//                    }
//                }
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