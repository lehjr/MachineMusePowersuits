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

package com.lehjr.powersuits.common.item.module.miningenhancement;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.miningenhancement.IMiningEnhancementModule;
import com.lehjr.numina.common.capabilities.module.miningenhancement.MiningEnhancement;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by Eximius88 on 1/29/14.
 */
public class AOEPickUpgradeModule extends AbstractPowerModule {
    public AOEPickUpgradeModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }
    /** TODO: Add charge system like plasma cannon */
    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IMiningEnhancementModule miningEnhancement;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.AOE_ENERGY, 500, "FE");

                addTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.AOE_ENERGY, 9500);
                addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.AOE_MINING_RADIUS, 5, "m", 2, 1);
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CapabilityPowerModule.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> miningEnhancement));
        }

        class Enhancement extends MiningEnhancement {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
                if (player.level.isClientSide) {
                    return false; // fixme : check?
                }

                AtomicBoolean harvested = new AtomicBoolean(false);
                RayTraceResult rayTraceResult = getPlayerPOVHitResult(player.level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
                if (rayTraceResult == null || rayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
                    return false;
                }
                int radius = (int) (applyPropertyModifiers(MPSConstants.AOE_MINING_RADIUS) - 1) / 2;
                if (radius == 0) {
                    return false;
                }

                Direction side = ((BlockRayTraceResult) rayTraceResult).getDirection();
                Stream<BlockPos> posList;
                switch (side) {
                    case UP:
                    case DOWN:
                        posList = BlockPos.betweenClosedStream(posIn.north(radius).west(radius), posIn.south(radius).east(radius));
                        break;

                    case EAST:
                    case WEST:
                        posList = BlockPos.betweenClosedStream(posIn.above(radius).north(radius), posIn.below(radius).south(radius));
                        break;

                    case NORTH:
                    case SOUTH:
                        posList = BlockPos.betweenClosedStream(posIn.above(radius).west(radius), posIn.below(radius).east(radius));
                        break;

                    default:
                        posList = new ArrayList<BlockPos>().stream();
                }

                int energyUsage = this.getEnergyUsage();

                AtomicInteger blocksBroken = new AtomicInteger(0);
                itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modeChanging -> {
                        posList.forEach(blockPos-> {
                            BlockState state = player.level.getBlockState(blockPos);
                            // find an installed module to break current block
                            for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                                int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                                if (blockBreakingModule.getCapability(CapabilityPowerModule.POWER_MODULE).map(b -> {
                                    // check if module can break block
                                    if(b instanceof IBlockBreakingModule) {
                                        return ((IBlockBreakingModule) b).canHarvestBlock(itemStack, state, player, blockPos, playerEnergy - energyUsage);
                                    }
                                    return false;
                                }).orElse(false)) {
                                    if (posIn == blockPos) { // center block
                                        harvested.set(true);
                                    }
                                    blocksBroken.getAndAdd(1);
                                    Block.updateOrDestroy(state, Blocks.AIR.defaultBlockState(), player.level, blockPos, Constants.BlockFlags.DEFAULT);
                                    ElectricItemUtils.drainPlayerEnergy(player,
                                            blockBreakingModule.getCapability(CapabilityPowerModule.POWER_MODULE).map(m -> {
                                                if (m instanceof IBlockBreakingModule) {
                                                    return ((IBlockBreakingModule) m).getEnergyUsage();
                                                }
                                                return 0;
                                            }).orElse(0) + energyUsage);
                                    break;
                                }
                            }
                        });
                });
                return harvested.get();
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.AOE_ENERGY);
            }
        }
    }
}