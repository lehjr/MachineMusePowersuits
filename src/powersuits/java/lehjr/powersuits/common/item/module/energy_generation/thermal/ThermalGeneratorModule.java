package lehjr.powersuits.common.item.module.energy_generation.thermal;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.config.module.EnergyGenerationModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final ThermalGeneratorTickingCapability ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new ThermalGeneratorTickingCapability(module);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }


        public static class ThermalGeneratorTickingCapability extends PlayerTickModule {
            final int tier;
            public ThermalGeneratorTickingCapability(ItemStack module) {
                super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY);
                this.tier = getTier(module);
                switch(tier) {
                    case 1 -> {
                        addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_thermoelectricEnergyGeneration);

                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGenerationBase, "FE");
                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricWaterConsumptionBase, "mb");
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGenerationMultiplier);
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricWaterConsumptionMultiplier);
                    }
                    case 2 -> {
                        addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_2_thermoelectricEnergyGeneration);

                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_2_steamElectricEnergyGenerationBase, "FE");
                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_2_steamElectricWaterConsumptionBase, "mb");
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_2_steamElectricEnergyGenerationMultiplier);
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_2_steamElectricWaterConsumptionMultiplier);
                    }
                    case 3 -> {
                        addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_3_thermoelectricEnergyGeneration);

                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_3_steamElectricEnergyGenerationBase, "FE");
                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_3_steamElectricWaterConsumptionBase, "mb");
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_3_steamElectricEnergyGenerationMultiplier);
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_3_steamElectricWaterConsumptionMultiplier);
                    }
                    case 4 -> {
                        addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_4_thermoelectricEnergyGeneration);

                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_4_steamElectricEnergyGenerationBase, "FE");
                        addBaseProperty(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_4_steamElectricWaterConsumptionBase, "mb");
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_4_steamElectricEnergyGenerationMultiplier);
                        addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION, EnergyGenerationModuleConfig.thermalEnergyGenerator_4_steamElectricWaterConsumptionMultiplier);
                    }
                }
            }

            static int getTier(ItemStack stack) {
                if(stack.is(MPSItems.THERMAL_GENERATOR_MODULE_1.get())) {
                    return 1;
                }
                if(stack.is(MPSItems.THERMAL_GENERATOR_MODULE_2.get())) {
                    return 2;
                }
                if(stack.is(MPSItems.THERMAL_GENERATOR_MODULE_3.get())) {
                    return 3;
                }
                if (stack.is(MPSItems.THERMAL_GENERATOR_MODULE_4.get())) {
                    return 4;
                }
                return 0;
            }

            int ticksPerAction = 20; // Todo: test and implement where possible... maybe add another config for this

            @Override
            public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
                if(level.isClientSide) {
                    return false;
                }

                double currentHeat = HeatUtils.getPlayerHeat(player).currentHeat();
                double maxHeat = HeatUtils.getPlayerHeat(player).maxHeat();
                double steamPowerPercent = 1;
                double coolingAmount = 0;
                double coolingFactor = 1;
                double generationAmount = 0;
                if (level.getGameTime() % ticksPerAction == 0) {
                    int waterAmount = 0;
                    double waterConsumption = 0;
                    HeatUtils.PlayerHeat heat = HeatUtils.getPlayerHeat(player);
                    if(heat.currentHeat() == 0) {
                        return false;
                    }
                    double efficiency = heat.currentHeat() / heat.maxHeat();

                    // Steam electric
                    IFluidHandlerItem fluidHander = item.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null) ;
                    if (fluidHander != null) {
                        FluidStack fluidStack = fluidHander.getFluidInTank(0);
                        if (fluidStack.getFluid() == Fluids.WATER && fluidStack.getAmount() > 0) {
                            waterAmount = fluidStack.getAmount();
                        }
                        waterConsumption = ticksPerAction * applyPropertyModifiers(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION);
                    }

                    // fixme: water consumption is 0???????!!!!!!!

                    if(waterAmount > 0 && waterConsumption > 0) {
//                    NuminaLogger.logDebug("using steam power");

                        if(waterConsumption > waterAmount){
                            steamPowerPercent = waterAmount/waterConsumption;
                        }
//                    NuminaLogger.logDebug("steam power percent: " +  steamPowerPercent);


                        generationAmount = ElectricItemUtils.givePlayerEnergy(player, (int) (ticksPerAction * steamPowerPercent * applyPropertyModifiers(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION)), true);
//                    NuminaLogger.logDebug("generation amount first calc: " + generationAmount);
//                    NuminaLogger.logDebug("steamPowerPercent * waterConsumption: " + (steamPowerPercent * waterConsumption));

                        fluidHander.drain((int)(steamPowerPercent * waterConsumption), IFluidHandler.FluidAction.EXECUTE);

                    } else {
                        coolingFactor = currentHeat/maxHeat;
                        efficiency = coolingFactor;
                        generationAmount = ElectricItemUtils.givePlayerEnergy(player, (int) (ticksPerAction *  applyPropertyModifiers(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION)),
                                true);

//                    if (player.isOnFire()) {
//                        generationAmount = ElectricItemUtils.givePlayerEnergy(player, (int) ( ticksPerAction * 4 * applyPropertyModifiers(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION)),
//                            true);
//                        NuminaLogger.logDebug("cooling on fire");
//                        coolingFactor = 0.5;
//                    } else if (currentHeat > maxHeat) {
//                        generationAmount = ElectricItemUtils.givePlayerEnergy(player, (int) (ticksPerAction* 4 * applyPropertyModifiers(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION)),
//                            true);
//                    } else if(currentHeat > maxHeat * 0.75) {
//
//                    }
//
//
//
//
//                        currentHeat >= 200) {
//                        generationAmount = ElectricItemUtils.givePlayerEnergy(player, (int) (ticksPerAction* 2 * applyPropertyModifiers(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION)),
//                            true);
//
//                        NuminaLogger.logDebug("cooling heat over 200");
//                        coolingFactor = 0.6;
//                    } else if ((currentHeat / maxHeat) >= 0.5) {
//                        NuminaLogger.logDebug("cooling on fire");
//
//                        generationAmount = ElectricItemUtils.givePlayerEnergy(player,
//                            (int) (ticksPerAction * applyPropertyModifiers(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION)), true);
//                        coolingFactor = 0.3;
//                    }
                    }

                    coolingAmount = Math.min(generationAmount, currentHeat) * coolingFactor;
                    generationAmount = generationAmount * efficiency;

//                NuminaLogger.logDebug("generation: " + generationAmount);
//                NuminaLogger.logDebug("cooling: " + coolingAmount);

                    if(coolingAmount > 0 && generationAmount > 0) {
                        double gaveEnergy = ElectricItemUtils.givePlayerEnergy(player, generationAmount, false);
//                    NuminaLogger.logDebug("gave energy: " + gaveEnergy);

                        HeatUtils.coolPlayer(player, coolingAmount);
                    }
                }
                return false;
            }

            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public String getModuleGroup() {
                return "thermal generators";
            }

            @Override
            public boolean isAllowed() {
                return switch (tier) {
                    case 1-> EnergyGenerationModuleConfig.thermalEnergyGenerator_1_isAllowed;
                    case 2-> EnergyGenerationModuleConfig.thermalEnergyGenerator_2_isAllowed;
                    case 3-> EnergyGenerationModuleConfig.thermalEnergyGenerator_3_isAllowed;
                    case 4-> EnergyGenerationModuleConfig.thermalEnergyGenerator_4_isAllowed;
                    default -> false;
                };
            }

            @Override
            public boolean onPlayerTickInactive(Player player, Level level, ItemStack item, int moduleIndex) {
                return super.onPlayerTickInactive(player, level, item, moduleIndex);
            }
        }
        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        public <T> LazyOptional<T> getCapability(final Capability<T> capability, final Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}