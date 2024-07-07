package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class SwimAssistModule extends AbstractPowerModule {
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
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 1000, "FE");
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, 1, "m/s");
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
//            public void onPlayerTickActive(Player player, ItemStack itemStack) {
////                if (player.isSwimming()) { // doesn't work when strafing without "swimming"
//                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//                if((player.isInWater() && !player.isPassenger()) && (playerInput.strafeLeftKey || playerInput.strafeRightKey || playerInput.forwardKey || playerInput.reverseKey || playerInput.jumpKey || player.isCrouching())) {
//                    double moveRatio = 0;
//                    if (playerInput.forwardKey) {
//                        moveRatio += 1.0;
//                    }
//                    if (playerInput.strafeLeftKey || playerInput.strafeRightKey) {
//                        moveRatio += 1.0;
//                    }
//                    if (playerInput.jumpKey || player.isCrouching()) {
//                        moveRatio += 0.2 * 0.2;
//                    }
//                    double swimAssistRate = applyPropertyModifiers(MPSConstants.SWIM_BOOST_AMOUNT) * 0.05 * moveRatio;
//                    double swimEnergyConsumption = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//
//                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
//
//                    if (swimEnergyConsumption < playerEnergy) {
//                        if (player.level().isClientSide && NuminaSettings.useSounds()) {
//                            Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get(), SoundSource.PLAYERS, 1.0f, 1.0f, true);
//                        } else if (
//                            // every 20 ticks
//                                (player.level().getGameTime() % 5) == 0) {
//                            ElectricItemUtils.drainPlayerEnergy(player, (int) (swimEnergyConsumption) * 5);
//                        }
//                        MovementManager.INSTANCE.thrust(player, swimAssistRate, true);
////                            setMovementModifier(getModule(), swimAssistRate * 100000, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
//                    } else {
//                        onPlayerTickInactive(player, itemStack);
//                    }
//                } else {
//                    onPlayerTickInactive(player, itemStack);
//                }
//            }
//
//            @Override
//            public void onPlayerTickInactive(Player player, @Nonnull ItemStack itemStack) {
//                if (player.level().isClientSide && NuminaSettings.useSounds()) {
//                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_SWIM_ASSIST.get());
//                }
//                SprintAssistModule.setMovementModifier(getModule(), 0, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
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