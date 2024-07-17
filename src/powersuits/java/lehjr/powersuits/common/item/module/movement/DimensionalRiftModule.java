package lehjr.powersuits.common.item.module.movement;


import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.common.util.ITeleporter;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Eximius88 on 2/3/14.
 */
public class DimensionalRiftModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.HEAT_GENERATION, 55);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 200000);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
            if (!playerIn.isPassenger() && !playerIn.isVehicle() && playerIn.canChangeDimensions() && !playerIn.level().isClientSide()) {
                Level level ;
                if (playerIn.level().dimension().location().equals(Level.NETHER.location())) {
                    level = playerIn.getServer().getLevel(Level.OVERWORLD);
                } else if (playerIn.level().dimension().location().equals(Level.OVERWORLD.location())) {
                    level = playerIn.getServer().getLevel(Level.NETHER);
                } else {
                    level = null;
                }

                if (level != null) {
                    BlockPos coords = playerIn.blockPosition();
                    double energyConsumption = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(playerIn);
                    if (playerEnergy >= energyConsumption) {
                        Optional<BlockPos> targetPos = findSafeLocation(coords, Direction.Axis.X, (ServerLevel) level, playerIn);
                        if (targetPos.isPresent()) {
                            playerIn.changeDimension((ServerLevel) level, new CommandTeleporter(targetPos.get()));
                            ElectricItemUtils.drainPlayerEnergy(playerIn, getEnergyUsage(), false);
                            HeatUtils.heatPlayer(playerIn, applyPropertyModifiers(MPSConstants.HEAT_GENERATION));
                            return InteractionResultHolder.success(itemStackIn);
                        }
                    }
                }
                return InteractionResultHolder.fail(itemStackIn);
            }
            return InteractionResultHolder.pass(itemStackIn);
        }


        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }

    private static class CommandTeleporter implements ITeleporter {
        private final BlockPos targetPos;

        private CommandTeleporter(BlockPos targetPos) {
            this.targetPos = targetPos;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            entity = repositionEntity.apply(false);
            entity.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
            return entity;
        }
    }

    static boolean isOutsideWorldBounds(Level world, BlockPos pos) {
        if (world == null || pos == null) {
            return true;
        }
        return pos.getY() <= 0 || pos.getY() >= world.getHeight();
    }

    public static Optional<BlockPos> findSafeLocation(BlockPos targetPos, Direction.Axis axis, ServerLevel level, Player entity) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0D;
        BlockPos destination = null;
        double d1 = -1.0D;
        BlockPos blockpos1 = null;
        WorldBorder worldborder = level.getWorldBorder();
        final int ceilingLimit = Math.min(level.getMaxBuildHeight(), level.getMinBuildHeight() + level.getLogicalHeight()) - 1;

        for(BlockPos.MutableBlockPos mutablePos : BlockPos.spiralAround(targetPos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(ceilingLimit, level.getHeight(Heightmap.Types.MOTION_BLOCKING, mutablePos.getX(), mutablePos.getZ()));
            int k = 1;
            if (worldborder.isWithinBounds(mutablePos) && worldborder.isWithinBounds(mutablePos.move(direction, k))) {
                mutablePos.move(direction.getOpposite(), k);

                for(int l = j; l >= 0; --l) {
                    mutablePos.setY(l);
                    if (level.isEmptyBlock(mutablePos)) {
                        int i1;
                        /* what exactly is the logic behind this? */
                        for(i1 = l; l > 0 && level.isEmptyBlock(mutablePos.move(Direction.DOWN)); --l) {
                        }

                        if (l + 4 <= ceilingLimit) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                mutablePos.setY(l);

                                if(canTeleportTo(level, mutablePos, entity)) {
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
            destination = (new BlockPos(targetPos.getX(), Mth.clamp(targetPos.getY(), 70, level.getHeight() - 10), targetPos.getZ())).immutable();
            if (!worldborder.isWithinBounds(destination)) {
                return Optional.empty();
            }
        }
        return Optional.of(destination);
    }

    private static boolean canTeleportTo(Level world, BlockPos pos, Player playerEntity) {
        if (!isOutsideWorldBounds(world, pos)) {
            BlockState state = world.getBlockState(pos.below());
            BlockPos blockpos = pos.subtract(playerEntity.blockPosition());
            return state.blocksMotion() && state.isSolidRender(world, pos.below()) && world.noCollision(playerEntity, playerEntity.getBoundingBox().move(blockpos));
        }
        return false;
    }
}