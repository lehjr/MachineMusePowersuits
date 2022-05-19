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

package com.lehjr.powersuits.common.item.module.tool;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HoeModule extends AbstractPowerModule {


    public HoeModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        @Deprecated
        protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(CapProvider::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT_PATH, Pair.of(CapProvider::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT, Pair.of(CapProvider::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.COARSE_DIRT, Pair.of(CapProvider::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())), Blocks.ROOTED_DIRT, Pair.of((p_150861_) -> {
            return true;
        }, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS))));



        ItemStack module;
        IRightClickModule rightClick;


        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClick = new RightClickie(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.HOE_ENERGY, 500, "FE");
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.HOE_ENERGY, 9500);
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 8, "m", 1, 0);

                addBaseProperty(MPSConstants.HOE_HARVEST_SPEED, 8, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HOE_ENERGY, 9500);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HOE_HARVEST_SPEED, 22);
            }};




        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CapabilityPowerModule.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
        }

        class RightClickie extends RightClickModule implements IBlockBreakingModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

//            @Override
            public InteractionResult useOn1(UseOnContext context) {
                int energyConsumed = this.getEnergyUsage();
                Player player = context.getPlayer();
                Level world = context.getLevel();
                BlockPos pos = context.getClickedPos();
                Direction facing = context.getClickedFace();
                ItemStack itemStack = context.getItemInHand();

                if (!player.mayUseItemAt(pos, facing, itemStack) || ElectricItemUtils.getPlayerEnergy(player) < energyConsumed) {
                    return InteractionResult.PASS;
                } else {
                    int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
                    if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
                    int radius = (int)applyPropertyModifiers(MPSConstants.RADIUS);
                    for (int i = (int) Math.floor(-radius); i < radius; i++) {
                        for (int j = (int) Math.floor(-radius); j < radius; j++) {
                            if (i * i + j * j < radius * radius) {
                                BlockPos newPos = pos.offset(i, 0, j);
//                                if (facing != Direction.DOWN && (world.isEmptyBlock(newPos.above()) || ToolHelpers.blockCheckAndHarvest(player, world, newPos.above()))) {
//                                    if (facing != Direction.DOWN && world.isEmptyBlock(newPos.above())) {
//                                        BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(newPos).getBlock());
//                                        if (blockstate != null) {
//                                            world.playSound(player, newPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
//
//                                            if (!world.isClientSide) {
//                                                world.setBlock(newPos, blockstate, 11);
//                                                ElectricItemUtils.drainPlayerEnergy(player, energyConsumed);
//                                            }
//                                        }
//                                    }
//                                }
                            }
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }

            @Override
            public boolean mineBlock(@NotNull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage());
                    return true;
                }
                return false;
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HOE_HARVEST_SPEED)));
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.HOE_ENERGY);
            }

            @Nonnull
            @Override
            public ItemStack getEmulatedTool() {
                return new ItemStack(Items.IRON_HOE);
            }





            public InteractionResult useOn(UseOnContext pContext) {
                int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(pContext);
                if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
                Level level = pContext.getLevel();
                BlockPos blockpos = pContext.getClickedPos();
                BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.HOE_TILL, false);
                Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));







                if (pair == null) {
                    return InteractionResult.PASS;
                } else {
                    Predicate<UseOnContext> predicate = pair.getFirst();
                    Consumer<UseOnContext> consumer = pair.getSecond();
                    if (predicate.test(pContext)) {
                        Player player = pContext.getPlayer();
                        level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if (!level.isClientSide) {
                            consumer.accept(pContext);
                            if (player != null) {

                                pContext.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                                    p_150845_.broadcastBreakEvent(pContext.getHand());


                                });
                            }
                        }

                        return InteractionResult.sidedSuccess(level.isClientSide);
                    } else {
                        return InteractionResult.PASS;
                    }
                }
            }







//            @Override
//            public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
//                return net.minecraftforge.common.ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
//            }
        }


        public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState pState, ItemLike pItemToDrop) {
            return (useOnContext) -> {
                useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), pState, 11);
                Block.popResourceFromFace(useOnContext.getLevel(), useOnContext.getClickedPos(), useOnContext.getClickedFace(), new ItemStack(pItemToDrop));
            };
        }

        public static boolean onlyIfAirAbove(UseOnContext useOnContext) {
            return useOnContext.getClickedFace() != Direction.DOWN && useOnContext.getLevel().getBlockState(useOnContext.getClickedPos().above()).isAir();
        }

        public static Consumer<UseOnContext> changeIntoState(BlockState pState) {
            return (useOnContext) -> {
                useOnContext.getLevel().setBlock(useOnContext.getClickedPos(), pState, 11);
            };
        }
    }
}