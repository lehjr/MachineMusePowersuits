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

package lehjr.powersuits.common.item.module.movement;


import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Created by Eximius88 on 2/3/14.
 */
public class DimensionalRiftModule extends AbstractPowerModule {
    public DimensionalRiftModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, ModuleCategory.MOVEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.HEAT_GENERATION, 55);
                addBaseProperty(MPSConstants.DIM_RIFT_ENERGY, 200000);
            }};

            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult use(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                if (!playerIn.isPassenger() && !playerIn.isVehicle() && playerIn.canChangeDimensions() && !playerIn.level.isClientSide()) {
                    World level ;
                    if (playerIn.level.dimension().location().equals(World.NETHER.location())) {
                        level = playerIn.getServer().getLevel(World.OVERWORLD);
                    } else if (playerIn.level.dimension().location().equals(World.OVERWORLD.location())) {
                        level = playerIn.getServer().getLevel(World.NETHER);
                    } else {
                        level = null;
                    }

                    if (level != null) {
                        BlockPos coords = playerIn.blockPosition();
                        int energyConsumption = (int) applyPropertyModifiers(MPSConstants.DIM_RIFT_ENERGY);
                        int playerEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
                        if (playerEnergy >= energyConsumption) {
                            Optional<BlockPos> targetPos = findSafeLocation(coords, Direction.Axis.X, level, playerIn);
                            if (targetPos.isPresent()) {
                                playerIn.changeDimension((ServerWorld) level, new CommandTeleporter(targetPos.get()));
                                ElectricItemUtils.drainPlayerEnergy(playerIn, getEnergyUsage());
                                HeatUtils.heatPlayer(playerIn, applyPropertyModifiers(MPSConstants.HEAT_GENERATION));
                                return ActionResult.success(itemStackIn);
                            }
                        }
                    }
                    return ActionResult.fail(itemStackIn);
                }
                return ActionResult.pass(itemStackIn);
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.DIM_RIFT_ENERGY);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }


    private class CommandTeleporter implements ITeleporter {
        private final BlockPos targetPos;

        private CommandTeleporter(BlockPos targetPos) {
            this.targetPos = targetPos;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            entity = repositionEntity.apply(false);
            entity.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
            return entity;
        }
    }

    static boolean isOutsideWorldBounds(World world, BlockPos pos) {
        if (world == null || pos == null) {
            return true;
        }
        return pos.getY() <= 0 || pos.getY() >= world.getHeight();
    }

    public Optional<BlockPos> findSafeLocation(BlockPos targetPos, Direction.Axis axis, World world, PlayerEntity entity) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0D;
        BlockPos destination = null;
        double d1 = -1.0D;
        BlockPos blockpos1 = null;
        WorldBorder worldborder = world.getWorldBorder();
        final int ceilingLimit = world.getHeight() - 1;

        for(BlockPos.Mutable mutablePos : BlockPos.spiralAround(targetPos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(ceilingLimit, world.getHeight(Heightmap.Type.MOTION_BLOCKING, mutablePos.getX(), mutablePos.getZ()));
            int k = 1;
            if (worldborder.isWithinBounds(mutablePos) && worldborder.isWithinBounds(mutablePos.move(direction, k))) {
                mutablePos.move(direction.getOpposite(), k);

                for(int l = j; l >= 0; --l) {
                    mutablePos.setY(l);
                    if (world.isEmptyBlock(mutablePos)) {
                        int i1;
                        /* what exactly is the logic behind this? */
                        for(i1 = l; l > 0 && world.isEmptyBlock(mutablePos.move(Direction.DOWN)); --l) {
                        }

                        if (l + 4 <= ceilingLimit) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                mutablePos.setY(l);

                                if(canTeleportTo(world, mutablePos, entity)) {
                                    return Optional.of(mutablePos);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (d0 == -1.0D && d1 != -1.0D) {
            destination = blockpos1;
            d0 = d1;
        }

        if (d0 == -1.0D) {
            destination = (new BlockPos(targetPos.getX(), MathHelper.clamp(targetPos.getY(), 70, world.getHeight() - 10), targetPos.getZ())).immutable();
            if (!worldborder.isWithinBounds(destination)) {
                return Optional.empty();
            }
        }
        return Optional.of(destination);
    }

    private static boolean canTeleportTo(World world, BlockPos pos, PlayerEntity playerEntity) {
        if (!isOutsideWorldBounds(world, pos)) {
            BlockState state = world.getBlockState(pos.below());
            BlockPos blockpos = pos.subtract(playerEntity.blockPosition());
            return state.getMaterial().blocksMotion() && state.isSolidRender(world, pos.below()) && world.noCollision(playerEntity, playerEntity.getBoundingBox().move(blockpos));
        }
        return false;
    }
}