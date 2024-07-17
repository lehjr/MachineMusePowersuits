package lehjr.powersuits.common.item.module.tool.blockbreaking.mining;

import lehjr.numina.common.base.NuminaLogger;
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


public class PickaxeModule extends AbstractPowerModule {
    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule {
        int tier;

        public BlockBreaker(ItemStack module, int tier) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            this.tier = tier;
//            NuminaLogger.logDebug("module: " + module + ", tier: " + tier + ", isAllowed: " + isAllowed());

            switch(tier) {
                case 1: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.stonePickAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.stonePickAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.stonePickAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.stonePickAxeModuleHarvestSpeedOverclockMultiplier);
                    break;
                }
                case 2: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.ironPickAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.ironPickAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.ironPickAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.ironPickAxeModuleHarvestSpeedOverclockMultiplier);
                    break;
                }
                case 3: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.diamondPickAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.diamondPickAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.diamondPickAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.diamondPickAxeModuleHarvestSpeedOverclockMultiplier);
                    break;
                }
                case 4: {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.netheritePickAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, MPSCommonConfig.netheritePickAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MPSCommonConfig.netheritePickAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, MPSCommonConfig.netheritePickAxeModuleHarvestSpeedOverclockMultiplier);
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
                case 1 -> MPSCommonConfig.stonePickAxeModuleIsAllowed;
                case 2 -> MPSCommonConfig.ironPickAxeModuleIsAllowed;
                case 3 -> MPSCommonConfig.diamondPickAxeModuleIsAllowed;
                case 4 -> MPSCommonConfig.netheritePickAxeModuleIsAllowed;
                default -> false;
            };
        }

        @Override
        public int getTier() {
            return this.tier;
        }
    }
}