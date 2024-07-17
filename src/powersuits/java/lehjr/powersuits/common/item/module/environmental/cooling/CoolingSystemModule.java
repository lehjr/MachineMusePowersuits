package lehjr.powersuits.common.item.module.environmental.cooling;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

/**
 * Created by Eximius88 on 1/17/14.
 */
public class CoolingSystemModule extends AbstractPowerModule {

    // TODO: merge tank code with cooling system code, but with separate fluid cap
//    public static class Ticker extends PlayerTickModule {
//        int tier;
//        public Ticker(@Nonnull ItemStack module, int tier) {
//            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
//            this.tier = tier;
//
//
//
//            addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
//            addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
//        }
//
//        @Override
//        public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//            double heatBefore = HeatUtils.getPlayerHeat(player);
//
//            HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
//            double cooling = heatBefore - HeatUtils.getPlayerHeat(player);
//            ElectricItemUtils.drainPlayerEnergy(player, (int) (cooling * applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION)), false);
//        }
//
//        @Override
//        public int getTier() {
//            return tier;
//        }
//    }

    //    static final String FLUID_NBT_KEY = "Fluid";
//    // one heat unit per 5 mB of water
//    static final double coolingFactor = 1.0/5.0;
//
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
//        FluidHandlerItemStack fluidHandler;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true) {{
//                addBaseProperty(MPSConstants.FLUID_TANK_SIZE, 20000);
//                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
//                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                ticker.loadCapValues();
//                return ticker;
//            });
//
//            this.fluidHandler = new FluidHandlerItemStack(module, (int)ticker.applyPropertyModifiers(MPSConstants.FLUID_TANK_SIZE)) {
//                @Override
//                public boolean canFillFluidType(FluidStack fluid) {
//                    return fluid.getFluid() == Fluids.WATER;
//                }
//            };
//        }
//
//        class Ticker extends PlayerTickModule {
//            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
//                super(module, category, target, config, defBool);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//                if (/*player.world.isRemote() &&*/ player.getCommandSenderWorld().getGameTime() % 10 == 0 ) {
//                    // we only have one tank, so index 0;
//                    int maxFluid = fluidHandler.getTankCapacity(0);
//                    int currentFluid = fluidHandler.getFluidInTank(0).getAmount();
//
//                    // fill the tank
//                    if (currentFluid < maxFluid) {
//                        BlockPos pos = player.blockPosition();
//                        BlockState blockstate = player.level().getBlockState(pos);
//
//                        // fill by being in water
//                        if (player.isInWater() && player.level().getBlockState(pos).getBlock() != Blocks.BUBBLE_COLUMN) {
//                            if (blockstate.getBlock() instanceof BucketPickup && blockstate.getFluidState().getType() == Fluids.WATER) {
//                                FluidActionResult pickup = FluidUtil.tryPickUpFluid(module, player, player.level(), pos, null); // fixme?
//                                if(pickup.isSuccess()) {
//                                    FluidStack water = new FluidStack(Fluids.WATER, 1000);
//                                    if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
//                                        player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
//                                    }
//                                }
//                            }
//                            // fill by being in the rain or bubble column
//                        } else if (player.isInWaterRainOrBubble()) {
//                            FluidStack water = new FluidStack(Fluids.WATER, 100);
//                            if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
//                                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
//                            }
//                        }
//                    }
//                }
//
//                // Apply cooling
//                double currentHeat = HeatUtils.getPlayerHeat(player);
//                double maxHeat = HeatUtils.getPlayerMaxHeat(player);
//                if ((currentHeat / maxHeat) >= ticker.applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {
//
//                    // cool 200 per bucket
//                    double coolAmount = fluidHandler.drain(
//                                    // adjust so cooling does not exceed cooling needed
//                                    (int) Math.min(1000, currentHeat/coolingFactor),
//                                    // only execute on server, simulate on client
//                                    player.level().isClientSide ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE)
//                            .getAmount() * coolingFactor;
//
//                    HeatUtils.coolPlayer(player, coolAmount);
//                    if (coolAmount > 0) {
//                        player.level().playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.MASTER, 1.0F, 1.0F);
//                        for (int i = 0; i < 4; i++) {
//                            player.level().addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
//                        }
//                    }
//                }
//            }
//        }
//
//        /** ICapabilityProvider ----------------------------------------------------------------------- */
//        @Override
//        @Nonnull
//        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//            final LazyOptional<T> capabilityPowerModule = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//            if (capabilityPowerModule.isPresent()) {
//                return capabilityPowerModule;
//            }
//
//            final LazyOptional<T> fluidCapability = fluidHandler.getCapability(capability, side);
//            if (fluidCapability.isPresent()) {
//                return fluidCapability;
//            }
//            return LazyOptional.empty();
//        }
//    }

}