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

import lehjr.powersuits.common.base.MPSObjects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class RailgunBoltEntity extends ThrowableProjectile implements IEntityAdditionalSpawnData {
    private double damage = 2.0D;
    private int knockbackStrength;
    private double velocity;
    private double chargePercent;
    int piercedEntities = 0;

    private final SoundEvent hitSound = SoundEvents.GENERIC_EXPLODE;

    public RailgunBoltEntity(EntityType<? extends RailgunBoltEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public RailgunBoltEntity(Level world,
                             LivingEntity shooter,
                             double velocity,
                             double chargePercent,
                             double damage,
                             double knockback) {
        super(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), world);
        this.velocity = velocity;
        this.damage = damage;
        this.knockbackStrength = (int) knockback;


        this.setOwner(shooter);
//        this.func_70029_a(world);

        // todo: replace with something resembling original code
        if (shooter instanceof Player) {
            Vec3 direction = shooter.getLookAngle();//.normalize();
            if (chargePercent >= 0.75) {
                // fire
                setSecondsOnFire((int) (chargePercent * 100));
            }
//            double inaccuracy = (chargePercent * 0.25F);

//            if (inaccuracy > 0.5) {
//                direction = direction
//                        .add(this.random.nextGaussian() * 0.0075 * inaccuracy,
//                                this.random.nextGaussian() * 0.0075 * inaccuracy,
//                                this.random.nextGaussian() * 0.0075 * inaccuracy);
//            }
//
//            this.func_70107_b(
//                    shooter.getX(),
//                    shooter.getY() + shooter.func_70047_e(),
//                    shooter.getZ());

            this.setDeltaMovement(direction.scale(velocity));
        }
        setNoGravity(true);
    }






    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Vec3 hitVec = result.getLocation();
        drawParticleStreamTo(hitVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult traceResult) {
        super.onHitEntity(traceResult);
        Entity entity = traceResult.getEntity();

        int pierceLimit = (int) (5 * chargePercent); // pierceLevel of 5 or less
        if (pierceLimit > 0) {
            if (this.piercedEntities >= pierceLimit + 1) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
            this.piercedEntities ++;
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = causeBoltDamage(this, this);
        } else {
            damagesource = causeBoltDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        int fireTimer = entity.getRemainingFireTicks();
        if (this.isOnFire() /* burn the enderman too && !flag */) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, (float)damage)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                // apply knockback
                if (this.knockbackStrength > 0) {
                    Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                        livingentity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (pierceLimit <= 0) {
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            entity.setRemainingFireTicks(fireTimer);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public static DamageSource causeBoltDamage(RailgunBoltEntity arrow, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("bolt", arrow, indirectEntityIn)).setProjectile();
    }

    @Override
    public void tick() {
        super.tick();

        // copied from AbstractArrow // working?
        if (this.isInWater()) {
//            System.out.println("working?");

            float f2;
            // motion vector with scale applied because the bolt moves too fast to draw bubbles
            Vec3 vector3d = this.getDeltaMovement();//.scale(0.05);
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getX() + d3;
            double d1 = this.getY() + d4;
            double d2 = this.getZ() + d0;

            for(int j = 0; j < 4; ++j) {
                this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
            }

            f2 = this.getWaterDrag();
            this.setDeltaMovement(vector3d.scale(f2));
        }
    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.tickCount > this.getMaxLifetime()) {
            this.remove(RemovalReason.DISCARDED);
        }

        // remove "ghosts"
        Vec3 motion = getDeltaMovement();
        if (motion.x == 0 && motion.y ==0 && motion.z ==0) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    /**
     * onBlockImpact?? Play sound when hitting blocks
     * @param rayTraceResult
     */
    @Override
    protected void onHitBlock(BlockHitResult rayTraceResult) {
        super.onHitBlock(rayTraceResult);
        this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

//    @Override
//    protected float getGravity() {
//        return 0.0F;
//    }


    @Override
    protected void defineSynchedData() {
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
     * Originally this was only drawn only on impact.
     */
    public void drawParticleStreamTo(Vec3 hitVec) {
        Entity source = this.getOwner();
        if (source != null && source instanceof Player) {
            double x = hitVec.x;
            double y = hitVec.y;
            double z = hitVec.z;

            Player shooter = (Player) source;
            Vec3 direction = shooter.getLookAngle().normalize();
            double xoffset = 1.3f;
            double yoffset = -.2;
            double zoffset = 0.3f;
            Vec3 horzdir = direction.normalize();
            horzdir = new Vec3(horzdir.x, 0, horzdir.z);
            horzdir = horzdir.normalize();
            double cx = shooter.getX() + direction.x * xoffset - direction.y * horzdir.x * yoffset - horzdir.z * zoffset;
            double cy = shooter.getY() + shooter.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset;
            double cz = shooter.getZ() + direction.z * xoffset - direction.y * horzdir.z * yoffset + horzdir.x * zoffset;
            double dx = x - cx;
            double dy = y - cy;
            double dz = z - cz;
            double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);

            while (Math.abs(cx - x) > Math.abs(dx / ratio)) {
                level.addParticle(ParticleTypes.CRIT /*.MYCELIUM*/, cx, cy, cz, 0.0D, 0.0D, 0.0D);
                cx += dx * 0.1 / ratio;
                cy += dy * 0.1 / ratio;
                cz += dz * 0.1 / ratio;
            }
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.damage);
        buffer.writeInt(this.knockbackStrength);
        buffer.writeDouble(this.velocity);
        buffer.writeDouble(this.chargePercent);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.damage = additionalData.readDouble();
        this.knockbackStrength = additionalData.readInt();
        this.velocity = additionalData.readDouble();
        this.chargePercent = additionalData.readDouble();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}