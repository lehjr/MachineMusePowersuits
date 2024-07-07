package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class GliderModule extends AbstractPowerModule {
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
//
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);
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
//            public void onPlayerTickActive(Player player, ItemStack chestPlate) {
//                Vec3 playerHorzFacing = player.getLookAngle();
//                playerHorzFacing = new Vec3(playerHorzFacing.x, 0, playerHorzFacing.z);
//                playerHorzFacing.normalize();
//                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//
//                PlayerUtils.resetFloatKickTicks(player);
//                boolean hasParachute = chestPlate.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                        .filter(IModularItem.class::isInstance)
//                        .map(IModularItem.class::cast)
//                        .map(m-> m.isModuleOnline(MPSRegistryNames.PARACHUTE_MODULE)).orElse(false);
//
//                if (player.isCrouching() && player.getDeltaMovement().y < 0 && (!hasParachute || playerInput.forwardKey)) {
//                    Vec3 motion = player.getDeltaMovement();
//                    if (motion.y < -0.1) {
//                        double motionYchange = Math.min(0.08, -0.1 - motion.y);
//
//                        player.setDeltaMovement(motion.add(
//                                playerHorzFacing.x * motionYchange,
//                                motionYchange,
//                                playerHorzFacing.z * motionYchange
//                        ));
//
//                        // sprinting speed
//                        float flySpeed = player.getAbilities().getFlyingSpeed() + 0.03f;
//                        player.getAbilities().setFlyingSpeed(flySpeed);
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