package lehjr.powersuits.common.item.module.movement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class JetPackModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "RF/t");
//                addBaseProperty(MPSConstants.JETPACK_THRUST, 0, "N");
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 15000);
//                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, 0.16F);
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
//            public void onPlayerTickActive(Player player, ItemStack torso) {
//                if (player.isInWater()) {
//                    return;
//                }
//
//                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
//
//                ItemStack helmet = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD);
//                boolean hasFlightControl = helmet.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                        .filter(IModularItem.class::isInstance)
//                        .map(IModularItem.class::cast)
//                        .map(m->
//                        m.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false);
//                double jetEnergy = 0;
//                double thrust = 0;
//                jetEnergy += applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//                thrust += applyPropertyModifiers(MPSConstants.JETPACK_THRUST);
//
//                if ((jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) &&
//                        ((hasFlightControl && thrust > 0) || (playerInput.jumpKey))) {
//                        thrust = MovementManager.INSTANCE.thrust(player, thrust, hasFlightControl);
//
//                        if(!player.level().isClientSide()) {
//                            if ((player.level().getGameTime() % 5) == 0) {
//                                ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy * 5));
//                            }
//                        } else if (NuminaSettings.useSounds()) {
//                            Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
//                        }
//                    } else {
//                        onPlayerTickInactive(player, torso);
//                    }
//            }
//
//            @Override
//            public void onPlayerTickInactive(Player player, ItemStack item) {
//                if (player.level().isClientSide && NuminaSettings.useSounds()) {
//                    Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
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