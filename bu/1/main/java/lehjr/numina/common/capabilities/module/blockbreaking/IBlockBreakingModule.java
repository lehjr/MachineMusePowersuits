/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.capabilities.module.blockbreaking;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public interface IBlockBreakingModule extends IPowerModule {
    default boolean canHarvestBlock(@Nonnull ItemStack stack, BlockState state, Player player, BlockPos pos, double playerEnergy) {
        return (playerEnergy >= this.getEnergyUsage() && isToolEffective(player.level(), pos, getEmulatedTool()));
    }

    boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy);

    void handleBreakSpeed(PlayerEvent.BreakSpeed event);

    int getEnergyUsage();

    @Nonnull
    ItemStack getEmulatedTool();


    default boolean isToolEffective(BlockGetter world, BlockPos pos, @Nonnull ItemStack emulatedTool) {
        BlockState state = world.getBlockState(pos);

        if(emulatedTool.isCorrectToolForDrops(state)) {
            return true;
        }

        if (Float.compare(state.getDestroySpeed(world, pos), -1.0F) <= 0 ) {// unbreakable
            return false;
        }

        if (emulatedTool.getItem().isCorrectToolForDrops(state)) {
            return true;
        }

        return false;
    }

    default boolean blockCheckAndHarvest(Player player, Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == null || world.isEmptyBlock(pos) || block == Blocks.BEDROCK)
            return false;
        if ((block instanceof IShearable || block instanceof FlowerBlock || block instanceof BushBlock || block instanceof LeavesBlock)
                && block.canHarvestBlock(state, world, pos, player) || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.IRON_SHOVEL));
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }
}