package com.lehjr.powersuits.common.item.module.tool.blockbreaking;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.config.module.ShovelModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
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
            super(module, ModuleCategory.SHOVEL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 2-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 3-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedOverclockMultiplier);
                }
                case 4-> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedOverclockMultiplier);
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
                case 1 -> ShovelModuleConfig.stoneShovelModuleIsAllowed;
                case 2 -> ShovelModuleConfig.ironShovelModuleIsAllowed;
                case 3 -> ShovelModuleConfig.diamondShovelModuleIsAllowed;
                case 4 -> ShovelModuleConfig.netheriteShovelModuleIsAllowed;
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
