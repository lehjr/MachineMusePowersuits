package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class JumpAssistModule extends AbstractPowerModule {

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
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
//                addSimpleTradeoff(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, "FE", 0, 250, MPSConstants.MULTIPLIER, "%", 1, 4);
//                addSimpleTradeoff(MPSConstants.COMPENSATION, MPSConstants.ENERGY_CONSUMPTION, "FE", 0, 50, MPSConstants.FOOD_COMPENSATION, "%", 0, 1);
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
//                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//                if (playerInput.jumpKey) {
//                    double multiplier = MovementManager.INSTANCE.getPlayerJumpMultiplier(player);
//                    if (multiplier > 0) {
//                        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.15 * Math.min(multiplier, 1), 0));
//                        MovementManager.INSTANCE.setPlayerJumpTicks(player, multiplier - 1);
//                    }
//                    player.getAbilities().setFlyingSpeed(player.getSpeed() * .2f);
//                } else {
//                    MovementManager.INSTANCE.setPlayerJumpTicks(player, 0);
//                }
//                PlayerUtils.resetFloatKickTicks(player);
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