package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class BlinkDriveModule extends AbstractPowerModule {
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final RightClickie rightClickie;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.rightClickie = new RightClickie(module, ModuleCategory.MOVEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
//                addBaseProperty(MPSConstants.BLINK_DRIVE_RANGE, 5, "m");
//                addTradeoffProperty(MPSConstants.RANGE, MPSConstants.ENERGY_CONSUMPTION, 140000);
//                addTradeoffProperty(MPSConstants.RANGE, MPSConstants.BLINK_DRIVE_RANGE, 95);
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> rightClickie);
//        }
//
//        class RightClickie extends RightClickModule {
//            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config);
//            }
//
//            @Override
//            public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
//                int range = (int) applyPropertyModifiers(MPSConstants.BLINK_DRIVE_RANGE);
//                int energyConsumption = getEnergyUsage();
//                HitResult hitRayTrace = rayTrace(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY, range);
//                if (hitRayTrace != null && hitRayTrace.getType() == HitResult.Type.BLOCK) {
//                    double distance = hitRayTrace.getLocation().distanceTo(playerIn.position());
//
//                    // adjust energy consumption for actual distance.NuminaLogger.logDebug("get energy usage after calculation: " + energyConsumption);
//                    energyConsumption = (int) (energyConsumption * (distance/range));
//                    if (ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption,  true) == energyConsumption) {
//                        PlayerUtils.resetFloatKickTicks(playerIn);
//                        ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption, false);
//                        worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
//                        PlayerUtils.teleportEntity(playerIn, hitRayTrace);
//                        return InteractionResultHolder.success(itemStackIn);
//                    }
//                }
//                return InteractionResultHolder.pass(itemStackIn);
//            }
//
//            @Override
//            public int getEnergyUsage() {
//                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
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