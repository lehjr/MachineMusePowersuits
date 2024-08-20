package com.lehjr.powersuits.common.item.module.tool.misc;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IShearable;

import javax.annotation.Nonnull;

/**
 * Created by User: Andrew2448
 * 7:13 PM 4/21/13
 */
public class LeafBlowerModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
            addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 9500);
            addBaseProperty(MPSConstants.RADIUS, 1, "m");
            addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 15);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            int radius = (int) applyPropertyModifiers(MPSConstants.RADIUS);
            if (useBlower(radius, playerIn, level, playerIn.blockPosition()))
                return InteractionResultHolder.success(itemStackIn);
            return InteractionResultHolder.pass(itemStackIn);
        }

        private boolean useBlower(int radius,Player player, Level world, BlockPos pos) {
            int totalEnergyDrain = 0;
            BlockPos newPos;
            for (int i = pos.getX() - radius; i < pos.getX() + radius; i++) {
                for (int j = pos.getY() - radius; j < pos.getY() + radius; j++) {
                    for (int k = pos.getZ() - radius; k < pos.getZ() + radius; k++) {
                        newPos = new BlockPos(i, j, k);
                        if (blockCheckAndHarvest(player, world, newPos)) {
                            totalEnergyDrain += getEnergyUsage();
                        }
                    }
                }
            }
            ElectricItemUtils.drainPlayerEnergy(player, totalEnergyDrain, false);
            return true;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }

    static boolean blockCheckAndHarvest(Player player, Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == null || world.isEmptyBlock(pos) || block == Blocks.BEDROCK)
            return false;
        if ((block instanceof IShearable || block instanceof BushBlock || block instanceof LeavesBlock)
                && block.canHarvestBlock(state, world, pos, player) || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.IRON_SHOVEL));
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }
}