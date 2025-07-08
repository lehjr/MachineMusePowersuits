package com.lehjr.powersuits.common.entity;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.common.block.LuxCapacitorBlock;
import com.lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import com.lehjr.powersuits.common.registration.MPSBlocks;
import com.lehjr.powersuits.common.registration.MPSEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.jetbrains.annotations.Nullable;

public class LuxCapacitorEntity extends ThrowableProjectile implements IEntityWithComplexSpawn {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(LuxCapacitorEntity.class, EntityDataSerializers.INT);

    public LuxCapacitorEntity(EntityType<? extends LuxCapacitorEntity> entityType, Level world) {
        super(entityType, world);
        this.setNoGravity(true);
        setNoGravity(true);
    }

    public void setColor(Color color) {
        if(color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }
        this.entityData.set(COLOR, color.getARGBInt());
    }

    public Color getColor() {
        return new Color(this.entityData.get(COLOR));
    }

    public LuxCapacitorEntity(Level world, LivingEntity shootingEntity, Color color) {
        super(MPSEntities.LUX_CAPACITOR_ENTITY_TYPE.get(), shootingEntity, world);
        this.setNoGravity(true);
        setColor(color);
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

    BlockPlaceContext getUseContext(BlockHitResult hitResult) {
        return new BlockPlaceContext(
            new UseOnContext(
                (Player)this.getOwner(),
                ((Player) this.getOwner()).getUsedItemHand(),
                hitResult));
    }

    @Override
    protected void onHit(HitResult hitResult) {
        HitResult.Type hitResultType = hitResult.getType();

        if (this.level().isClientSide()) {
            return;
        }

        if (this.getOwner() == null) {
            this.remove(RemovalReason.DISCARDED);
        }

        if (this.isAlive() && hitResultType == HitResult.Type.BLOCK) {
            BlockHitResult blockRayTrace = (BlockHitResult)hitResult;
            place(blockRayTrace);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    public boolean place(BlockHitResult blockRayTrace) {
        Direction dir = blockRayTrace.getDirection().getOpposite();
        int x = blockRayTrace.getBlockPos().getX() - dir.getStepX();
        int y = blockRayTrace.getBlockPos().getY() - dir.getStepY();
        int z = blockRayTrace.getBlockPos().getZ() - dir.getStepZ();
        BlockPos blockPos = new BlockPos(x, y, z);

        BlockPlaceContext context = getUseContext(blockRayTrace);

        if (context == null) {
            return false;
        } else {
            BlockState blockstateToSet = MPSBlocks.LUX_CAPACITOR_BLOCK.get().getStateForPlacement(context);

            NuminaLogger.logDebug("context: " + context);
            NuminaLogger.logDebug("context POS: " + context.getClickedPos());
            NuminaLogger.logDebug("context can place: " + context.canPlace());
            NuminaLogger.logDebug("context face: " + context.getClickedFace());
            NuminaLogger.logDebug("context server: " + context.getLevel().getServer());

            if (blockstateToSet == null) {
                NuminaLogger.logDebug("place returning false1");
                return false;
            } else if (!level().setBlock(blockPos, blockstateToSet, 11)) {
                NuminaLogger.logDebug("place returning false2");
                return false;
            } else {
                NuminaLogger.logDebug("place made it here 1");
                Level level = level();
                Player player = (Player)this.getOwner();
                BlockState blockstateCurrent = level.getBlockState(blockPos);

                NuminaLogger.logDebug("place made it here 2");

                if (blockstateCurrent.is(blockstateToSet.getBlock())) {
                    this.updateCustomBlockEntityTag(level, player, blockPos);
                }
                NuminaLogger.logDebug("place made it here 3");
                SoundType soundtype = blockstateCurrent.getSoundType(level, blockPos, player);
                level.playSound(player, blockPos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) * 0.5F, soundtype.getPitch() * 0.8F);
                NuminaLogger.logDebug("place made it here 4");
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(player, blockstateCurrent));
                NuminaLogger.logDebug("place returning true");
                return true;
            }
        }
    }

    public boolean updateCustomBlockEntityTag(Level level, @Nullable Player player, BlockPos pos) {
        MinecraftServer minecraftserver = level.getServer();
        if (minecraftserver == null) {
            NuminaLogger.logDebug("updateCustomBlockEntityTag returning false1");
            return false;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LuxCapacitorBlockEntity) {
                if (!level.isClientSide && blockEntity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
                    NuminaLogger.logDebug("updateCustomBlockEntityTag returning false2");
                    return false;
                }
                NuminaLogger.logDebug("updateCustomBlockEntityTag returning true");
                ((LuxCapacitorBlockEntity) blockEntity).setColor(this.entityData.get(COLOR));
                blockEntity.setChanged();


//                CompoundTag tag = blockEntity.saveCustomOnly(level().registryAccess());
//                tag.putInt(NuminaConstants.COLOR, this.entityData.get(COLOR));
//                   try {
//                        blockEntity.loadCustomOnly(tag, (level().registryAccess()));
//                        blockEntity.setChanged();
//                        return true;
//                    } catch (Exception exception1) {
//                        NuminaLogger.logException("Failed to apply custom data to block entity at {} "+ blockEntity.getBlockPos(), exception1);
//
////                        try {
////                            blockEntity.loadCustomOnly(compoundtag1, levelRegistry);
////                        } catch (Exception exception) {
////                            LOGGER.warn("Failed to rollback block entity at {} after failure", blockEntity.getBlockPos(), exception);
////                        }
//                    }
            }
            NuminaLogger.logDebug("updateCustomBlockEntityTag returning false");
            return false;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(COLOR, 0);
        builder.build();
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
        buffer.writeInt(this.entityData.get(COLOR));
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.entityData.set(COLOR, additionalData.readInt());
    }
}
