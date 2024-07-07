package lehjr.powersuits.common.item.module.energy.generation.solar;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class BasicSolarGeneratorModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 15000, "FE");
//                addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
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
//            public int getTier() {
//                return 1;
//            }
//
//            @Override
//            public String getModuleGroup() {
//                return "Energy Generation";
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, ItemStack itemStack) {
//                Level world = player.level();
//                boolean isRaining, canRain = true;
//                if (world.getGameTime() % 20 == 0) {
//                    canRain = world.getBiome(player.blockPosition()).value().hasPrecipitation();
//                }
//
//                isRaining = canRain && (world.isRaining() || world.isThundering());
//                boolean sunVisible = world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 1, 0));
//                boolean moonVisible = !world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 1, 0));
//                if (!world.isClientSide && world.dimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
//                    double lightLevelScaled = (world.getBrightness(LightLayer.SKY, player.blockPosition().above()) - world.getSkyDarken())/15D;
//                    if (sunVisible) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled));
//                    } else if (moonVisible) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled));
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