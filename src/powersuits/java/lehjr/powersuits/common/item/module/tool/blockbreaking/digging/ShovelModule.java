package lehjr.powersuits.common.item.module.tool.blockbreaking.digging;

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

public class ShovelModule extends AbstractPowerModule {
    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule {
        int tier;

        public BlockBreaker(ItemStack module, int tier) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()-> ()-> MPSCommonConfig.stoneShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.stoneShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.stoneShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.stoneShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 2: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.ironShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.ironShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.ironShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.ironShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 3: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.diamondShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.diamondShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.diamondShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.diamondShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 4: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.netheriteShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.netheriteShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ()-> ()->MPSCommonConfig.netheriteShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ()-> ()->MPSCommonConfig.netheriteShovelModuleHarvestSpeedOverclockMultiplier);
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

        @Override
        public ItemStack getEmulatedTool() {
            return switch (tier) {
                case 1 -> new ItemStack(Items.STONE_SHOVEL);
                case 2 -> new ItemStack(Items.IRON_SHOVEL);
                case 3 -> new ItemStack(Items.DIAMOND_SHOVEL);
                case 4 -> new ItemStack(Items.NETHERITE_SHOVEL);
                default -> new ItemStack(Items.STICK);
            };
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> MPSCommonConfig.stoneShovelModuleIsAllowed;
                case 2 -> MPSCommonConfig.ironShovelModuleIsAllowed;
                case 3 -> MPSCommonConfig.diamondShovelModuleIsAllowed;
                case 4 -> MPSCommonConfig.netheriteShovelModuleIsAllowed;
                default -> false;
            };
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