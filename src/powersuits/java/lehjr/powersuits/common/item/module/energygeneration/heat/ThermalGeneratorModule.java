package lehjr.powersuits.common.item.module.energygeneration.heat;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.config.module.EnergyGenerationModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jspecify.annotations.NonNull;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
    public static class ThermalGeneratorTickingCapability extends PlayerTickModule {
        final int tier;
        public ThermalGeneratorTickingCapability(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY);
            this.tier = tier;
            switch(tier) {
                case  1:{
                    addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_thermoelectricEnergyGeneration);

                    addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration);
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration, "FE");
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_COMSUMPTION, 250, "mB");
                }
                case  2:{
                    addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_thermoelectricEnergyGeneration);

                    addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration);
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration, "FE");
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_COMSUMPTION, 250, "mB");
                }
                case  3:{
                    addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_thermoelectricEnergyGeneration);

                    addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration);
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration, "FE");
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_COMSUMPTION, 250, "mB");
                }
                case  4:{
                    addBaseProperty(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_thermoelectricEnergyGeneration);

                    addBaseProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration);
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, EnergyGenerationModuleConfig.thermalEnergyGenerator_1_steamElectricEnergyGeneration, "FE");
                    addTradeoffProperty(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION, MPSConstants.STEAM_ELECTRIC_WATER_COMSUMPTION, 250, "mB");
                }
            }


            ///  FIXME: Passive vs active energy generation (maybe tier based)


            // steam generation


            // thermoelectric


//
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NonNull ItemStack item) {
            double currentHeat = HeatUtils.getPlayerHeat(player).currentHeat();
            double maxHeat = HeatUtils.getPlayerHeat(player).maxHeat();
            if (level.getGameTime() % 20 == 0) {
                int waterAmount = 0;
                IFluidHandlerItem fluidHander = item.getCapability(Capabilities.FluidHandler.ITEM);
                if(fluidHander != null) {
                    FluidStack fluidStack = fluidHander.getFluidInTank(0);
                    if(fluidStack != null && fluidStack.is(Fluids.WATER) && fluidStack.getAmount() > 0) {
                        waterAmount = fluidStack.getAmount();
                    }
                }




                if (player.isOnFire()) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (4 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
                } else if (currentHeat >= 200) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) (2 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
                } else if ((currentHeat / maxHeat) >= 0.5) {
                    ElectricItemUtils.givePlayerEnergy(player, (int) applyPropertyModifiers(MPSConstants.ENERGY_GENERATION), false);
                }
            }
        }

        @Override
        public boolean isAllowed() {
            switch (tier) {
                case 1-> {
                    return EnergyGenerationModuleConfig.thermalEnergyGenerator_1_isAllowed;
                }
                case 2-> {
                    return EnergyGenerationModuleConfig.thermalEnergyGenerator_2_isAllowed;
                }
                case 3-> {
                    return EnergyGenerationModuleConfig.thermalEnergyGenerator_3_isAllowed;
                }
                case 4-> {
                    return EnergyGenerationModuleConfig.thermalEnergyGenerator_4_isAllowed;
                }
            }
            return false;
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @NonNull ItemStack item) {
            super.onPlayerTickInactive(player, level, item);
        }
    }
}
