package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.ArmorModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyShieldModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final EnergyShieldCapabilityWrapper ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            ticker = new EnergyShieldCapabilityWrapper(module);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        public static class EnergyShieldCapabilityWrapper extends PlayerTickModule {
            public EnergyShieldCapabilityWrapper(ItemStack module) {
                super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);
                addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_VALUE_ENERGY, ArmorModuleConfig.energyShieldFieldStrengthMultiplier, "pts");
                addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ENERGY_CONSUMPTION, ArmorModuleConfig.energyShieldEnergyConsumptionMultiplier, "FE");
                addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.energyShieldMaxHeatMultiplier);
                addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.energyShieldToughnessMultiplier);
                addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.energyShieldKnockBackResistanceMultiplier);
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
                return ArmorModuleConfig.energyShieldIsAllowed;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item, int moduleIndex) {
                double energy = getPlayerEnergy(player);
                double energyUsage = getEnergyUsage();
                if (energy < energyUsage) {
                    toggleModule(false);
                } else {
                    ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                }
                return false;
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}