package com.lehjr.powersuits.common.entity;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.powersuits.common.block.LuxCapacitorBlock;
import com.lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import com.lehjr.powersuits.common.registration.MPSBlocks;
import com.lehjr.powersuits.common.registration.MPSEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.jetbrains.annotations.Nullable;

public class LuxCapacitorEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {
    public Color color = LuxCapacitorBlock.defaultColor;

    public LuxCapacitorEntity(EntityType<? extends LuxCapacitorEntity> entityType, Level world) {
        super(entityType, world);
        this.setNoGravity(true);
        if (color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }
        setNoGravity(true);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public LuxCapacitorEntity(Level world, LivingEntity shootingEntity, Color color) {
        super(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), shootingEntity, world);
        this.setNoGravity(true);
        this.color = color != null ? color : LuxCapacitorBlock.defaultColor;
        Vec3 direction = shootingEntity.getLookAngle().normalize();
        double speed = 1.0;
        this.setDeltaMovement(
                direction.x * speed,
                direction.y * speed,
                direction.z * speed
        );

        double r = 0.4375;
        double xoffset = 0.1;
        double yoffset = 0;
        double zoffset = 0;
        double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        double horzx = direction.x / horzScale;
        double horzz = direction.z / horzScale;
        this.setPos(
                (shootingEntity.getX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
                (shootingEntity.getY() + shootingEntity.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
                (shootingEntity.getZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset));
        this.setBoundingBox(new AABB(getX() - r, getY() - 0.0625, getZ() - r, getX() + r, getY() + 0.0625, getZ() + r));
    }

    BlockPlaceContext getUseContext(BlockPos pos, Direction facing, BlockHitResult hitResult) {
        return new BlockPlaceContext(
                new UseOnContext(
                        (Player)this.getOwner(),
                        ((Player) this.getOwner()).getUsedItemHand(),
                        hitResult));
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (level().isClientSide()) {
            return;
        }

        if (this.getOwner() == null) {
            this.kill();
        }

        if (color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }
        if (this.isAlive() && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockRayTrace = (BlockHitResult)hitResult;
            Direction dir = blockRayTrace.getDirection().getOpposite();
            int x = blockRayTrace.getBlockPos().getX() - dir.getStepX();
            int y = blockRayTrace.getBlockPos().getY() - dir.getStepY();
            int z = blockRayTrace.getBlockPos().getZ() - dir.getStepZ();
            BlockPos blockPos = new BlockPos(x, y, z);

            BlockPlaceContext context = getUseContext(blockPos, blockRayTrace.getDirection(), blockRayTrace);
            // in some cases in newer versions, the y coordinate can be less than 0
            if (/*y > 0 && */ level().getBlockState(blockPos).canBeReplaced(context) /* level.getBlockState(blockPos).getMaterial().isReplaceable()*/) {
                BlockState blockState = MPSBlocks.LUX_CAPACITOR_BLOCK.get().getStateForPlacement(getUseContext(blockPos, blockRayTrace.getDirection(), blockRayTrace));
                if (!placedBlock(blockState, blockPos, blockRayTrace.getDirection())) {
                    for (Direction facing : context.getNearestLookingDirections()) {
                        blockState = blockState.setValue(LuxCapacitorBlock.FACING, facing);
                        if(placedBlock(blockState, blockPos, facing)) {
                            break;
                        }
                    }
                }
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }

    /**
     * @param state
     * @param pos
     * @return True if block was placed
     */
    boolean placedBlock(BlockState state, BlockPos pos, Direction facing) {
        if (state.canSurvive(level(), pos)) {
            if (state.hasProperty(LuxCapacitorBlock.FACING)) {
                BlockPos posToCheck = pos.relative(facing.getOpposite());
                BlockState stateToCheck = level().getBlockState(posToCheck);
                if (stateToCheck.isFaceSturdy(level(), posToCheck, facing, SupportType.CENTER)) {
                    level().setBlockAndUpdate(pos, state);
                    level().setBlockEntity(new LuxCapacitorBlockEntity(pos, state));
                    BlockEntity blockEntity = level().getBlockEntity(pos);
                    if (blockEntity instanceof LuxCapacitorBlockEntity) {
                        @Nullable IColorTag cap = level().getCapability(NuminaCapabilities.ColorCap.COLOR_BLOCK, pos, null);
                        if (cap != null) {
                            cap.setColor(color);
                        }

                        ((LuxCapacitorBlockEntity) blockEntity).setColor(color);
                        return true;
                    }
                    NuminaLogger.logError("failed to spawn block entity?");
                }
            }
        }
        return false;
    }

//    @Override
//    protected void defineSynchedData() {
//    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 40) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    /**
     * Called by the server when constructing the spawn packet.
     * Data should be added to the provided stream.
     *
     * @param buffer The packet data stream
     */
    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.color.getARGBInt());
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.color = new Color(additionalData.readInt());
    }
}