package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WaterElectrolyzerModule extends AbstractPowerModule {

    public class Ticker extends PlayerTickModule {

        public Ticker(ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);

//            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
        }

        @Override
        public void onPlayerTickInactive(Player player, @NotNull ItemStack item) {
            super.onPlayerTickInactive(player, item);
        }

            @Override
            public void onPlayerTickActive(Player player, ItemStack item) {
//                double energy = ElectricItemUtils.getPlayerEnergy(player);
//                double energyConsumption = Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
//                if (energy > energyConsumption && player.getAirSupply() < 10) {
//                    if ((player.level().isClientSide()) && NuminaClientConfig.useSounds) {
//                        player.playSound(MPSSoundDictionary.SOUND_EVENT_ELECTROLYZER.get(), 1.0f, 1.0f);
//                    }
//                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
//                    player.setAirSupply(300);
//                }
            }
    }


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
//            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
//
//            }};
//            powerModuleHolder = LazyOptional.of(() -> {
//                ticker.loadCapValues();
//                return ticker;
//            });
//        }
//
//        class Ticker extends PlayerTickModule {
//            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config, false);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, ItemStack item) {
//                double energy = ElectricItemUtils.getPlayerEnergy(player);
//                double energyConsumption = Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
//                if (energy > energyConsumption && player.getAirSupply() < 10) {
//                    if ((player.level().isClientSide()) && NuminaSettings.useSounds()) {
//                        player.playSound(MPSSoundDictionary.SOUND_EVENT_ELECTROLYZER.get(), 1.0f, 1.0f);
//                    }
//                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
//                    player.setAirSupply(300);
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