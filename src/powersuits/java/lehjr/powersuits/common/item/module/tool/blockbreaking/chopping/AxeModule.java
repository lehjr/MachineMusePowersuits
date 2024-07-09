package lehjr.powersuits.common.item.module.tool.blockbreaking.chopping;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public class AxeModule extends AbstractPowerModule {

    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule {
        int tier;
        public BlockBreaker(@Nonnull ItemStack module, int tier) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.stoneAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.stoneAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.stoneAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.stoneAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 2: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.ironAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.ironAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.ironAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.ironAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 3: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.diamondAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.diamondAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.diamondAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.diamondAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 4: {
//            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
//            addBaseProperty(MPSConstants.HARVEST_SPEED, 8, "x");
//            addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, 9500);
//            addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, 22);

                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.netheriteAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.netheriteAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.netheriteAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.netheriteAxeModuleHarvestSpeedOverclockMultiplier);
                }
            }
        }

        @Override
        public boolean mineBlock(@NotNull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy((Player) entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @NotNull
        @Override
        public ItemStack getEmulatedTool() {
            return switch (tier) {
                case 1 -> new ItemStack(Items.STONE_AXE);
                case 2 -> new ItemStack(Items.IRON_AXE);
                case 3 -> new ItemStack(Items.DIAMOND_AXE);
                case 4 -> new ItemStack(Items.NETHERITE_AXE);
                default -> new ItemStack(Items.STICK);
            };
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> MPSCommonConfig.stoneAxeModuleIsAllowed;
                case 2 -> MPSCommonConfig.ironAxeModuleIsAllowed;
                case 3 -> MPSCommonConfig.diamondAxeModuleIsAllowed;
                case 4 -> MPSCommonConfig.netheriteAxeModuleIsAllowed;
                default -> false;
            };
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
        }
    }
}