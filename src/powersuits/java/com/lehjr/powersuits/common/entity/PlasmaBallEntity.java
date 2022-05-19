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

package com.lehjr.powersuits.common.entity;


import com.lehjr.powersuits.common.base.MPSObjects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class PlasmaBallEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Float> CHARGE_PERCENT = SynchedEntityData.defineId(PlasmaBallEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> EXPLOSIVENESS = SynchedEntityData.defineId(PlasmaBallEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DAMAGINESS = SynchedEntityData.defineId(PlasmaBallEntity.class, EntityDataSerializers.FLOAT);

    public PlasmaBallEntity(EntityType<? extends PlasmaBallEntity> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * @param world  the world this spawns in
     * @param shootingEntity the shooter
     * @param finalExplosiveness explosiveness * chargePercent
     * @param finalDamaginess damaginess * chargePercent
     * @param chargePercent percent of charge in decimal form (0 - 1)
     */
    public PlasmaBallEntity(Level world, LivingEntity shootingEntity, float finalExplosiveness, float finalDamaginess, float chargePercent) {
        super(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), world);
        this.setOwner(shootingEntity);

        this.entityData.set(CHARGE_PERCENT, chargePercent);
        this.entityData.set(EXPLOSIVENESS, finalExplosiveness);
        this.entityData.set(DAMAGINESS, finalDamaginess);

        Vec3 direction = shootingEntity.getLookAngle().normalize();
        double radius = chargePercent;
        double xoffset = 1.3f + radius - direction.y * shootingEntity.getEyeHeight();
        double yoffset = -.2;
        double zoffset = 0.3f;
        double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        double horzx = direction.x / horzScale;
        double horzz = direction.z / horzScale;
        this.setPos(
                // x
                (shootingEntity.getX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
                // y
                (shootingEntity.getY() + shootingEntity.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
                //z
                (shootingEntity.getZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset)
        );

        this.setDeltaMovement(direction);
        this.setBoundingBox(new AABB(getX() - radius, getY() - radius, getZ()- radius, getX() + radius, getY() + radius, getZ() + radius));
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(CHARGE_PERCENT, 0F);
        entityData.define(EXPLOSIVENESS, 0F);
        entityData.define(DAMAGINESS, 0F);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.tickCount > this.getMaxLifetime()) {
            this.remove(RemovalReason.DISCARDED);
        }

        if (this.isInWater()) {
            this.remove(RemovalReason.DISCARDED);
            for (int i = 0; i <  getChargePercent() * 50F; ++i) {
                this.level.addParticle(ParticleTypes.FLAME,
                        this.getX() + Math.random() * 1,
                        this.getY() + Math.random() * 1,
                        this.getZ() + Math.random() * 0.1,
                        0.0D, 0.0D, 0.0D);
            }
        }
    }

    public int getMaxLifetime() {
        return 200;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they
     * walk on. used for spiders and wolves to prevent them from trampling crops
     */
//    @Override
//    protected boolean isMovementNoisy() {
//        return false;
//    }


    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0;
    }

    @Override
    protected void onHit(HitResult result) {
        switch (result.getType()) {
            case ENTITY:
                EntityHitResult rayTraceResult = (EntityHitResult)result;
                if (rayTraceResult.getEntity() != null && rayTraceResult.getEntity() != getOwner()) {
                    rayTraceResult.getEntity().hurt(DamageSource.thrown(this, getOwner()), this.entityData.get(DAMAGINESS));
                }
                break;
            case BLOCK:
                break;
            default:
                break;
        }
        if (!this.level.isClientSide) { // Dist.SERVER
            boolean flag = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            // FIXME: this is probably all wrong
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3 * this.entityData.get(EXPLOSIVENESS), flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.BREAK);
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.level.addParticle(ParticleTypes.FLAME,
                    this.getX() + Math.random() * 0.1,
                    this.getY() + Math.random() * 0.1,
                    this.getZ() + Math.random() * 0.1,
                    0.0D, 0.0D, 0.0D);
        }
        if (!this.level.isClientSide) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public float getChargePercent() {
        return this.entityData.get(CHARGE_PERCENT);
    }

    // FIXME!!! using data manager AND spawnData?


    /**
     * Called by the server when constructing the spawn packet.
     * Data should be added to the provided stream.
     *
     * @param buffer The packet data stream
     */
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.entityData.get(CHARGE_PERCENT));
        buffer.writeFloat(this.entityData.get(EXPLOSIVENESS));
        buffer.writeFloat(this.entityData.get(DAMAGINESS));
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.entityData.set(CHARGE_PERCENT, additionalData.readFloat());
        this.entityData.set(EXPLOSIVENESS, additionalData.readFloat());
        this.entityData.set(DAMAGINESS, additionalData.readFloat());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}