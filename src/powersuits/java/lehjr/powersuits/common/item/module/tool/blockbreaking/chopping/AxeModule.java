package lehjr.powersuits.common.item.module.tool.blockbreaking.chopping;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.imixin.common.item.IUseOnContextMixn;
import lehjr.powersuits.common.config.ToolModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class AxeModule extends AbstractPowerModule {

    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule, IRightClickModule {
        int tier;
        public BlockBreaker(@Nonnull ItemStack module, int tier) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.stoneAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.stoneAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.stoneAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.stoneAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 2 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.ironAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.ironAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.ironAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.ironAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 3 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.diamondAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.diamondAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.diamondAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.diamondAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 4 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.netheriteAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, ToolModuleConfig.netheriteAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.netheriteAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, ToolModuleConfig.netheriteAxeModuleHarvestSpeedOverclockMultiplier);
                }
            }
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Player player = context.getPlayer();
            if (player == null) {
                return InteractionResult.PASS;
            }

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if(playerEnergy < getEnergyUsage()) {
                return InteractionResult.PASS;
            }

            UseOnContext alteredContext = new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), getEmulatedTool(), ((IUseOnContextMixn)context).machineMusePowersuits$getBlockHitResult());
            if(getEmulatedTool().getItem() instanceof AxeItem) {
                Optional<BlockState> optional = evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), alteredContext);
                if (optional.isEmpty()) {
                    return InteractionResult.PASS;
                } else {
                    ItemStack itemstack = getEmulatedTool();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                    }

                    level.setBlock(blockpos, optional.get(), 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, optional.get()));
                    ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
            return InteractionResult.PASS;
        }

        private Optional<BlockState> evaluateNewBlockState(Level pLevel, BlockPos pPos, @Nullable Player pPlayer, BlockState pState, UseOnContext context) {
            Optional<BlockState> optional = Optional.ofNullable(pState.getToolModifiedState(context, net.neoforged.neoforge.common.ToolActions.AXE_STRIP, false));
            if (optional.isPresent()) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return optional;
            } else {
                Optional<BlockState> optional1 = Optional.ofNullable(pState.getToolModifiedState(context, net.neoforged.neoforge.common.ToolActions.AXE_SCRAPE, false));
                if (optional1.isPresent()) {
                    pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    pLevel.levelEvent(pPlayer, 3005, pPos, 0);
                    return optional1;
                } else {
                    Optional<BlockState> optional2 = Optional.ofNullable(pState.getToolModifiedState(context, net.neoforged.neoforge.common.ToolActions.AXE_WAX_OFF, false));
                    if (optional2.isPresent()) {
                        pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                        pLevel.levelEvent(pPlayer, 3004, pPos, 0);
                        return optional2;
                    } else {
                        return Optional.empty();
                    }
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

        @Nonnull
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
                case 1 -> ToolModuleConfig.stoneAxeModuleIsAllowed;
                case 2 -> ToolModuleConfig.ironAxeModuleIsAllowed;
                case 3 -> ToolModuleConfig.diamondAxeModuleIsAllowed;
                case 4 -> ToolModuleConfig.netheriteAxeModuleIsAllowed;
                default -> false;
            };
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public String getModuleGroup() {
            return "Axe";
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