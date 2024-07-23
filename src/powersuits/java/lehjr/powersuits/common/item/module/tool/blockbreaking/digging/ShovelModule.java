package lehjr.powersuits.common.item.module.tool.blockbreaking.digging;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.ToolModuleConfig;
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

import javax.annotation.Nonnull;

public class ShovelModule extends AbstractPowerModule {
    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule {
        int tier;

        public BlockBreaker(ItemStack module, int tier) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.stoneShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.stoneShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.stoneShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.stoneShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 2-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.ironShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.ironShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.ironShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.ironShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 3-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.diamondShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.diamondShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.diamondShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.diamondShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 4-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.netheriteShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.netheriteShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.netheriteShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.netheriteShovelModuleHarvestSpeedOverclockMultiplier);
                }
            }
        }

        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy((Player) entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public String getModuleGroup() {
            return "Shovel";
        }

        @Override
        public @Nonnull ItemStack getEmulatedTool() {
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
                case 1 -> ToolModuleConfig.stoneShovelModuleIsAllowed;
                case 2 -> ToolModuleConfig.ironShovelModuleIsAllowed;
                case 3 -> ToolModuleConfig.diamondShovelModuleIsAllowed;
                case 4 -> ToolModuleConfig.netheriteShovelModuleIsAllowed;
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