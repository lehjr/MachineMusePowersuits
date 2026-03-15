package lehjr.powersuits.common.item.module.tool.blockbreaking;

import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.ShovelModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public class ShovelModule extends AbstractPowerModule {
    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule, IRightClickModule {
        int tier;

        public BlockBreaker(ItemStack module, int tier) {
            super(module, ModuleCategory.SHOVEL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
            case 1 -> {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedOverclockMultiplier);
            }
            case 2-> {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedOverclockMultiplier);
            }
            case 3-> {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedOverclockMultiplier);
            }
            case 4-> {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedOverclockMultiplier);
            }
            }
        }

        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
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
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            double energyUsage = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION, 0); // Base energy usage without overclock since no overclock is used
            Player player = context.getPlayer();
            if(player == null) return InteractionResult.PASS;

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if(playerEnergy >= energyUsage) {
                Level level = context.getLevel();
                BlockPos blockpos = context.getClickedPos();
                BlockState blockstate = level.getBlockState(blockpos);

                if (!(context.getClickedFace() == Direction.DOWN)) {
                    BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
                    BlockState blockstate2 = null;
                    if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir()) {
                        level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                        blockstate2 = blockstate1;
                    } else if ((blockstate2 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false)) != null && !level.isClientSide()) {
                        level.levelEvent(null, 1009, blockpos, 0);
                    }

                    if (blockstate2 != null) {
                        if (!level.isClientSide) {
                            level.setBlock(blockpos, blockstate2, 11);
                            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                            ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }
}
