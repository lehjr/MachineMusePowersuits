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

package lehjr.powersuits.item.module.tool;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.numina.util.helper.ToolHelpers;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;

public class HoeModule extends AbstractPowerModule {
    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(), Blocks.GRASS_PATH, Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(), Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState()));

    public HoeModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
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
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
        }

        class RightClickie extends RightClickModule implements IBlockBreakingModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResultType useOn(ItemUseContext context) {
                int energyConsumed = this.getEnergyUsage();
                PlayerEntity player = context.getPlayer();
                World world = context.getLevel();
                BlockPos pos = context.getClickedPos();
                Direction facing = context.getClickedFace();
                ItemStack itemStack = context.getItemInHand();

                if (!player.mayUseItemAt(pos, facing, itemStack) || ElectricItemUtils.getPlayerEnergy(player) < energyConsumed) {
                    return ActionResultType.PASS;
                } else {
                    int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
                    if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
                    int radius = (int)applyPropertyModifiers(MPSConstants.RADIUS);
                    for (int i = (int) Math.floor(-radius); i < radius; i++) {
                        for (int j = (int) Math.floor(-radius); j < radius; j++) {
                            if (i * i + j * j < radius * radius) {
                                BlockPos newPos = pos.offset(i, 0, j);
                                if (facing != Direction.DOWN && (world.isEmptyBlock(newPos.above()) || ToolHelpers.blockCheckAndHarvest(player, world, newPos.above()))) {
                                    if (facing != Direction.DOWN && world.isEmptyBlock(newPos.above())) {
                                        BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(newPos).getBlock());
                                        if (blockstate != null) {
                                            world.playSound(player, newPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                                            if (!world.isClientSide) {
                                                world.setBlock(newPos, blockstate, 11);
                                                ElectricItemUtils.drainPlayerEnergy(player, energyConsumed);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return ActionResultType.SUCCESS;
            }

            @Override
            public boolean onBlockDestroyed(ItemStack itemStack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, int playerEnergy) {
                if (this.canHarvestBlock(itemStack, state, (PlayerEntity) entityLiving, pos, playerEnergy)) {
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
        }
    }
}