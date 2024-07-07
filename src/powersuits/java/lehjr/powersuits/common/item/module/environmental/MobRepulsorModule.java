package lehjr.powersuits.common.item.module.environmental;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

/**
 * Created by User: Andrew2448
 * 8:26 PM 4/25/13
 */
public class MobRepulsorModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 2500, "FE");
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
//                super(module, category, target, config, false);
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//                int energyConsumption = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//                if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
//                    if (player.level().getGameTime() % 20 == 0) {
//                        ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
//                    }
//                    repulse(player.level(), player.blockPosition());
//                }
//            }
//
//            // FIXME: check for instances instead of direct references
//            public void repulse(Level world, BlockPos playerPos) {
//                int distance = 5;
//                AABB area = new AABB(
//                        playerPos.offset(-distance, -distance, -distance),
//                        playerPos.offset(distance, distance, distance));
//
//                for (Entity entity : world.getEntitiesOfClass(Mob.class, area)) {
//                    push(entity, playerPos);
//                }
//
//                for (Arrow entity : world.getEntitiesOfClass(Arrow.class, area)) {
//                    push(entity, playerPos);
//                }
//
//                for (Fireball entity : world.getEntitiesOfClass(Fireball.class, area)) {
//                    push(entity, playerPos);
//                }
//
//                for (ThrownPotion entity : world.getEntitiesOfClass(ThrownPotion.class, area)) {
//                    push(entity, playerPos);
//                }
//            }
//
//            private void push(Entity entity, BlockPos playerPos) {
//                if (!(entity instanceof Player) && !(entity instanceof EnderDragon)) {
//                    BlockPos distance = playerPos.subtract(entity.blockPosition());
//
//                    double dX = distance.getX();
//                    double dY = distance.getY();
//                    double dZ = distance.getZ();
//                    double d4 = dX * dX + dY * dY + dZ * dZ;
//                    d4 *= d4;
//                    if (d4 <= Math.pow(6.0D, 4.0D)) {
//                        double d5 = -(dX * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
//                        double d6 = -(dY * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
//                        double d7 = -(dZ * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
//                        if (d5 > 0.0D) {
//                            d5 = 0.22D;
//                        } else if (d5 < 0.0D) {
//                            d5 = -0.22D;
//                        }
//                        if (d6 > 0.2D) {
//                            d6 = 0.12D;
//                        } else if (d6 < -0.1D) {
//                            d6 = 0.12D;
//                        }
//                        if (d7 > 0.0D) {
//                            d7 = 0.22D;
//                        } else if (d7 < 0.0D) {
//                            d7 = -0.22D;
//                        }
//                        Vec3 motion = entity.getDeltaMovement();
//                        entity.setDeltaMovement(motion.x + d5, motion.y + d6, motion.z + d7);
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