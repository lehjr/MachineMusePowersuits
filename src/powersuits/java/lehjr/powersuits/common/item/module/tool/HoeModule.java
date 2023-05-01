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

package lehjr.powersuits.common.item.module.tool;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class HoeModule extends AbstractPowerModule {
//    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.field_196658_i, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_185774_da, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_150346_d, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_196660_k, Blocks.field_150346_d.defaultBlockState()));

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 9500);
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 8, "m", 1, 0);

                addBaseProperty(MPSConstants.HARVEST_SPEED, 8, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, 9500);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, 22);
            }};
            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }

        class RightClickie extends RightClickModule implements IBlockBreakingModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public InteractionResult useOn(UseOnContext context) {
                int energyConsumed = this.getEnergyUsage();
                Player player = context.getPlayer();
                Level world = context.getLevel();
                BlockPos pos = context.getClickedPos();
                Direction facing = context.getClickedFace();
                ItemStack itemStack = context.getItemInHand();

                if (!player.mayUseItemAt(pos, facing, itemStack) || ElectricItemUtils.getPlayerEnergy(player) < energyConsumed) {
                    return InteractionResult.PASS;
                } else {
//                    int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
//                    if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
//                    int radius = (int)applyPropertyModifiers(MPSConstants.RADIUS);
//                    for (int i = (int) Math.floor(-radius); i < radius; i++) {
//                        for (int j = (int) Math.floor(-radius); j < radius; j++) {
//                            if (i * i + j * j < radius * radius) {
//                                BlockPos newPos = pos.offset(i, 0, j);
////                                if (facing != Direction.DOWN && (world.isEmptyBlock(newPos.func_177984_a()) || ToolHelpers.blockCheckAndHarvest(player, world, newPos.func_177984_a()))) {
////                                    if (facing != Direction.DOWN && world.isEmptyBlock(newPos.func_177984_a())) {
////                                        BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(newPos).getBlock());
////                                        if (blockstate != null) {
////                                            world.func_184133_a(player, newPos, SoundEvents.field_187693_cj, SoundSource.BLOCKS, 1.0F, 1.0F);
////
////                                            if (!world.isClientSide) {
////                                                world.func_180501_a(newPos, blockstate, 11);
////                                                ElectricItemUtils.drainPlayerEnergy(player, energyConsumed);
////                                            }
////                                        }
////                                    }
////                                }
//                            }
//                        }
//                    }
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
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Nonnull
            @Override
            public ItemStack getEmulatedTool() {
                return new ItemStack(Items.IRON_HOE);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}