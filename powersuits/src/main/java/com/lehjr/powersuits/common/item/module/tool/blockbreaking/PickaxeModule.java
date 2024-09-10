package com.lehjr.powersuits.common.item.module.tool.blockbreaking;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.PickaxeModuleConfig;
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


public class PickaxeModule extends AbstractPowerModule {
    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule {
        int tier;

        public BlockBreaker(ItemStack module, int tier) {
            super(module, ModuleCategory.PICKAXE, ModuleTarget.TOOLONLY);
            this.tier = tier;
            //            NuminaLogger.logDebug("module: " + module + ", tier: " + tier + ", isAllowed: " + isAllowed());

            switch(tier) {
            case 1: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionBase, "FE");
                addBaseProperty(MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedOverclockMultiplier);
                break;
            }
            case 2: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.ironPickAxeModuleEnergyConsumptionBase, "FE");
                addBaseProperty(MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.ironPickAxeModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.ironPickAxeModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.ironPickAxeModuleHarvestSpeedOverclockMultiplier);
                break;
            }
            case 3: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.diamondPickAxeModuleEnergyConsumptionBase, "FE");
                addBaseProperty(MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.diamondPickAxeModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.diamondPickAxeModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.diamondPickAxeModuleHarvestSpeedOverclockMultiplier);
                break;
            }
            case 4: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.netheritePickAxeModuleEnergyConsumptionBase, "FE");
                addBaseProperty(MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.netheritePickAxeModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.netheritePickAxeModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, PickaxeModuleConfig.netheritePickAxeModuleHarvestSpeedOverclockMultiplier);
                break;
            }
            }
        }

        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            NuminaLogger.logDebug("energy usage: " + getEnergyUsage() +", player energy: " + playerEnergy);

            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                NuminaLogger.logDebug("returning true");
                return true;
            }
            NuminaLogger.logDebug("returning false");
            return false;
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Nonnull
        @Override
        public ItemStack getEmulatedTool() {
            return switch (tier) {
                case 1 -> new ItemStack(Items.STONE_PICKAXE);
                case 2 -> new ItemStack(Items.IRON_PICKAXE);
                case 3 -> new ItemStack(Items.DIAMOND_PICKAXE);
                case 4 -> new ItemStack(Items.NETHERITE_PICKAXE);
                default -> new ItemStack(Items.STICK);
            };
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> PickaxeModuleConfig.stonePickAxeModuleIsAllowed;
                case 2 -> PickaxeModuleConfig.ironPickAxeModuleIsAllowed;
                case 3 -> PickaxeModuleConfig.diamondPickAxeModuleIsAllowed;
                case 4 -> PickaxeModuleConfig.netheritePickAxeModuleIsAllowed;
                default -> false;
            };
        }

        @Override
        public String getModuleGroup() {
            return "Pickaxe";
        }

        @Override
        public int getTier() {
            return this.tier;
        }
    }
}
