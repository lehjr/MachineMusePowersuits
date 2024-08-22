package com.lehjr.powersuits.common.item.module.energy.generation.kinetic;

import com.lehjr.powersuits.common.item.module.AbstractPowerModule;

public class KineticGeneratorModule extends AbstractPowerModule {
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
//            this.ticker = new Ticker(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_GENERATION, 2000);
//                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 6000, "FE");
//                addBaseProperty(MPSConstants.MOVEMENT_RESISTANCE, 0.01F);
//                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.MOVEMENT_RESISTANCE, 0.49F, "%");
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
//            public void onPlayerTickActive(Player player, @Nonnull ItemStack itemStackIn) {
//                if (player.getAbilities().flying || player.isPassenger() || player.isFallFlying() || !player.onGround())
//                    onPlayerTickInactive(player, itemStackIn);
//
//                // really hate running this check on every tick but needed for player speed adjustments
//                if (ElectricItemUtils.getPlayerEnergy(player) < ElectricItemUtils.getMaxPlayerEnergy(player)) {
//                    // server side
//                    if (!player.level().isClientSide &&
//                            // every 20 ticks
//                            (player.level().getGameTime() % 20) == 0 &&
//                            // player not jumping, flying, or riding
//                            player.onGround()) {
//                        double distance = player.walkDist - player.walkDistO;
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (distance * 20 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)));
//                    }
//                }
//            }
//
//            @Override
//            public void onPlayerTickInactive(Player player, ItemStack itemStackIn) {
//                // remove attribute modifier when not active
//                getModule().removeTagKey("AttributeModifiers");
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