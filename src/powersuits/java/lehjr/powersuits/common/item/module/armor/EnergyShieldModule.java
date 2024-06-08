package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class EnergyShieldModule extends AbstractPowerModule {

    // FiXME: Implement ticking capability instead
    public static class EnergyShieldCapabilityWrapper extends PowerModule {
        public EnergyShieldCapabilityWrapper(ItemStack module) {
            super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);

            addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,
                    () -> () -> MPSCommonConfig.netheritePlatingArmorPhysical,
                    NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);

            addBaseProperty(NuminaConstants.MAXIMUM_HEAT,
                    () -> () -> MPSCommonConfig.netheritePlatingMaxHeat);

            addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,
                    () -> () -> MPSCommonConfig.netheritePlatingKnockBackResistance);

            addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,
                    () -> () -> MPSCommonConfig.netheritePlatingArmorToughness);

            //                        addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_VALUE_ENERGY, 6, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
//                        addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_ENERGY_CONSUMPTION, 5000, "FE");
//                        addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, NuminaConstants.MAXIMUM_HEAT, 500);
//                        addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, 0.25F);

        }

        @Override
        public String getModuleGroup() {
            return "Armor";
        }

        @Override
        public int getTier() {
            return 4;
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.netheritePlatingIsAllowed;
        }
    }









//        @Nullable
//        @Override
//        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
//            return new CapProvider(stack);
//        }
//
//        public class CapProvider implements ICapabilityProvider {
//            ItemStack module;
//            private final Ticker ticker;
//            private final LazyOptional<IPowerModule> powerModuleHolder;
//
//            public CapProvider(@Nonnull ItemStack module) {
//                this.module = module;
//                ticker = new Ticker(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY, MPSSettings::getModuleConfig, true) {
//                    @Override
//                    public int getTier() {
//                        return 4;
//                    }
//
//                    @Override
//                    public String getModuleGroup() {
//                        return "Armor";
//                    }
//
//                    {

//                    }};
//
//                powerModuleHolder = LazyOptional.of(() -> {
//                    ticker.loadCapValues();
//                    return ticker;
//                });
//            }
//
//            class Ticker extends PlayerTickModule {
//                public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
//                    super(module, category, target, config, defBool);
//                }
//
//                @Override
//                public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//                    double energy = ElectricItemUtils.getPlayerEnergy(player);
//                    double energyUsage = applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION);
//
//                    // turn off module if energy is too low. This will fire on both sides so no need to sync
//                    if (energy < energyUsage) {
//                        this.toggleModule(false);
//                    }
//                }
//            }
//
//            /** ICapabilityProvider ----------------------------------------------------------------------- */
//            @Override
//            @Nonnull
//            public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//                final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//                if (powerModuleCapability.isPresent()) {
//                    return powerModuleCapability;
//                }
//                return LazyOptional.empty();
//            }
//        }
//    }
}
