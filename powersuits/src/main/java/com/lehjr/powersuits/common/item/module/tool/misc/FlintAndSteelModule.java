package com.lehjr.powersuits.common.item.module.tool.misc;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nonnull;

/**
 * Created by User: Andrew2448
 * 10:48 PM 6/11/13
 */
public class FlintAndSteelModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
        }

        /**
         * Called when this item is used when targeting a Block. Code pretty much same as vanilla
         */
        @Override
        public InteractionResult useOn(UseOnContext context) {
            int energyConsumption = getEnergyUsage();
            Player player = context.getPlayer();

            if (player == null) {
                return InteractionResult.FAIL;
            }

            if (ElectricItemUtils.getPlayerEnergy(player) < energyConsumption) {
                return InteractionResult.FAIL;
            }
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) && !CandleCakeBlock.canLight(blockstate)) {
                BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, context.getHorizontalDirection())) {
                    level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                    level.setBlock(blockpos1, blockstate1, 11);
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    ItemStack itemstack = context.getItemInHand();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos1, itemstack);
                        ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide());
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                level.playSound(player, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(blockpos, blockstate.setValue(BlockStateProperties.LIT, true), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockpos);
                ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }
}