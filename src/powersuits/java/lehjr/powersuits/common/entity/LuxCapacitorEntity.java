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

package lehjr.powersuits.common.entity;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

public class LuxCapacitorEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {
    public Color color;

    public LuxCapacitorEntity(EntityType<? extends LuxCapacitorEntity> entityType, Level world) {
        super(entityType, world);
        this.setNoGravity(true);
        if (color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }
    }

    public LuxCapacitorEntity(Level world, LivingEntity shootingEntity, Color color) {
        super(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), shootingEntity, world);
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
        if (level.isClientSide()) {
            return;
        }

        if (color == null) {
            color = Color.WHITE;
        }
        if (this.isAlive() && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockRayTrace = (BlockHitResult)hitResult;
            Direction dir = blockRayTrace.getDirection().getOpposite();
            int x = blockRayTrace.getBlockPos().getX() - dir.getStepX();
            int y = blockRayTrace.getBlockPos().getY() - dir.getStepY();
            int z = blockRayTrace.getBlockPos().getZ() - dir.getStepZ();
            BlockPos blockPos = new BlockPos(x, y, z);

            BlockPlaceContext context = getUseContext(blockPos, blockRayTrace.getDirection(), blockRayTrace);

            if (/*y > 0 && */ level.getBlockState(blockPos).canBeReplaced(context) /* level.getBlockState(blockPos).getMaterial().isReplaceable()*/) {
                BlockState blockState = MPSObjects.LUX_CAPACITOR_BLOCK.get().getStateForPlacement(getUseContext(blockPos, blockRayTrace.getDirection(), blockRayTrace));
                if (!placedBlock(blockState, blockPos)) {
                    for (Direction facing : context.getNearestLookingDirections()) {
                        blockState = blockState.setValue(LuxCapacitorBlock.FACING, facing);
                        if(placedBlock(blockState, blockPos)) {
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
    boolean placedBlock(BlockState state, BlockPos pos) {
        if (state.canSurvive(level, pos)) {


//            BlockEntity beCheck = level.isEmptyBlock(pos);
//            if ()

            level.setBlockAndUpdate(pos, state);
            level.setBlockEntity(new LuxCapacitorBlockEntity(pos, state));
            BlockEntity blockEntity = level.getBlockEntity(pos);

            /** todo: how to find solid block to stick to */
            if (state.hasProperty(LuxCapacitorBlock.FACING)) {
                System.out.println("sturdy?: " +  state.isFaceSturdy(level, pos, state.getValue(LuxCapacitorBlock.FACING).getOpposite()));
            }

            if (blockEntity instanceof LuxCapacitorBlockEntity) {
                ((LuxCapacitorBlockEntity) blockEntity).setColor(color);
                return true;
            } else {
                NuminaLogger.logError("failed to spawn block entity?");
                return false;
            }
        }
        return false;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 400) {
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
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color.getARGBInt());
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.color = new Color(additionalData.readInt());
    }
}