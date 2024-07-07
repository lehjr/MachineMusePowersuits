package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class JetBootsModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0);
//                addBaseProperty(MPSConstants.JETBOOTS_THRUST, 0);
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 750, "FE");
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETBOOTS_THRUST, 0.08F);
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
//            public void onPlayerTickActive(Player player, ItemStack item) {
//                if (player.isInWater())
//                    return;
//
//                boolean hasFlightControl = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD).getCapability(ForgeCapabilities.ITEM_HANDLER)
//                        .filter(IModularItem.class::isInstance)
//                        .map(IModularItem.class::cast)
//                        .map(m-> m.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false);
//
//                double jetEnergy = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//                double thrust = applyPropertyModifiers(MPSConstants.JETBOOTS_THRUST);
//
//                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//                // if player has enough energy to fly
//                if (jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) {
//                    if (hasFlightControl && thrust > 0) {
//                        thrust = MovementManager.INSTANCE.thrust(player, thrust, true);
//                        if ((player.level().isClientSide) && NuminaSettings.useSounds()) {
//                            Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
//                        }
//                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy));
//                    } else if (playerInput.jumpKey && player.getDeltaMovement().y < 0.5) {
//                        thrust = MovementManager.INSTANCE.thrust(player, thrust, false);
//                        if ((player.level().isClientSide) && NuminaSettings.useSounds()) {
//                            Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get(), SoundSource.PLAYERS, (float) (thrust * 12.5), 1.0f, true);
//                        }
//                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy));
//                    } else {
//                        if ((player.level().isClientSide) && NuminaSettings.useSounds()) {
//                            Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
//                        }
//                    }
//                } else {
//                    if (player.level().isClientSide && NuminaSettings.useSounds()) {
//                        Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
//                    }
//                }
//            }
//
//            @Override
//            public void onPlayerTickInactive(Player player, ItemStack item) {
//                if (player.level().isClientSide && NuminaSettings.useSounds()) {
//                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETBOOTS.get());
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