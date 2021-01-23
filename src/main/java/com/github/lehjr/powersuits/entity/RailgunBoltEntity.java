package com.github.lehjr.powersuits.entity;

import com.github.lehjr.powersuits.basemod.MPSObjects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class RailgunBoltEntity extends ThrowableEntity implements IEntityAdditionalSpawnData {
    private double damage = 2.0D;
    private int knockbackStrength;
    private double velocity;
    private double chargePercent;
    int piercedEntities = 0;

    private final SoundEvent hitSound = SoundEvents.ENTITY_GENERIC_EXPLODE;

    public RailgunBoltEntity(EntityType<? extends RailgunBoltEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public RailgunBoltEntity(World world,
                             LivingEntity shooter,
                             double velocity,
                             double chargePercent,
                             double damage,
                             double knockback) {
        super(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), world);
        this.velocity = velocity;
        this.damage = damage;
        this.knockbackStrength = (int) knockback;


        this.setShooter(shooter);
        this.setWorld(world);

        // todo: replace with something resembling original code
        if (shooter instanceof PlayerEntity) {
            Vector3d direction = shooter.getLookVec();//.normalize();

            System.out.println("lookvec: " + shooter.getLookVec());
            System.out.println("lookVec oldCalc: " + getVectorForRotationTest(shooter.rotationPitch, shooter.rotationYaw));



            double xoffset = 1.3f + 0 - direction.y * shooter.getEyeHeight();
            double yoffset = -.2;
            double zoffset = 0.3f;
            double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
            double horzx = direction.x / horzScale;
            double horzz = direction.z / horzScale;

            this.setPositionAndRotation(
                    // x
                    (shooter.getPosX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
                    // y
                    (shooter.getPosY() + shooter.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
                    //z
                    (shooter.getPosZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset),
                    shooter.rotationYaw * -1,
                    shooter.rotationPitch * -1
            );

            if (chargePercent >= 0.75) {
                // fire
                setFire((int) (chargePercent * 100));
            }

//            double inaccuracy = (chargePercent * 0.25F);
//
//            if (inaccuracy > 0.5) {
//                direction = direction
//                        .add(this.rand.nextGaussian() * 0.0075 * inaccuracy,
//                                this.rand.nextGaussian() * 0.0075 * inaccuracy,
//                                this.rand.nextGaussian() * 0.0075 * inaccuracy);
//            }

            this.setMotion(direction.scale(velocity));
        }
        setNoGravity(true);
    }

    protected final Vector3d getVectorForRotationTest(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vector3d(f1 * f2, f3, f * f2);
    }


    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        Vector3d hitVec = result.getHitVec();
        drawParticleStreamTo(hitVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onEntityHit(EntityRayTraceResult traceResult) {
        super.onEntityHit(traceResult);
        Entity entity = traceResult.getEntity();

        int pierceLimit = (int) (5 * chargePercent); // pierceLevel of 5 or less
        if (pierceLimit > 0) {
            if (this.piercedEntities >= pierceLimit + 1) {
                this.remove();
                return;
            }
            this.piercedEntities ++;
        }

        Entity entity1 = this.func_234616_v_();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = causeBoltDamage(this, this);
        } else {
            damagesource = causeBoltDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        int fireTimer = entity.getFireTimer();
        if (this.isBurning() /* burn the enderman too && !flag */) {
            entity.setFire(5);
        }

        if (entity.attackEntityFrom(damagesource, (float)damage)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                // apply knockback
                if (this.knockbackStrength > 0) {
                    Vector3d vector3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
                }

                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (pierceLimit <= 0) {
                this.remove();
            }
        } else {
            entity.forceFireTicks(fireTimer);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.remove();
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
            Vector3d vector3d = this.getMotion();//.scale(0.05);
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;

            for(int j = 0; j < 4; ++j) {
                this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
            }

            f2 = this.getWaterDrag();
            this.setMotion(vector3d.scale(f2));
        }
    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.ticksExisted > this.getMaxLifetime()) {
            this.remove();
        }

        // remove "ghosts"
        Vector3d motion = getMotion();
        if (motion.x == 0 && motion.y ==0 && motion.z ==0) {
            this.remove();
        }
    }

    /**
     * onBlockImpact?? Play sound when hitting blocks
     * @param rayTraceResult
     */
    @Override
    protected void func_230299_a_(BlockRayTraceResult rayTraceResult) {
        super.func_230299_a_(rayTraceResult);
        this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }


    @Override
    protected void registerData() {
    }

    public int getMaxLifetime() {
        return 200;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they
     * walk on. used for spiders and wolves to prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Originally this was only drawn only on impact.
     */
    public void drawParticleStreamTo(Vector3d hitVec) {
        Entity source = this.func_234616_v_();
        if (source != null && source instanceof PlayerEntity) {
            double x = hitVec.x;
            double y = hitVec.y;
            double z = hitVec.z;

            PlayerEntity shooter = (PlayerEntity) source;
            Vector3d direction = shooter.getLookVec().normalize();
            double xoffset = 1.3f;
            double yoffset = -.2;
            double zoffset = 0.3f;
            Vector3d horzdir = direction.normalize();
            horzdir = new Vector3d(horzdir.x, 0, horzdir.z);
            horzdir = horzdir.normalize();
            double cx = shooter.getPosX() + direction.x * xoffset - direction.y * horzdir.x * yoffset - horzdir.z * zoffset;
            double cy = shooter.getPosY() + shooter.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset;
            double cz = shooter.getPosZ() + direction.z * xoffset - direction.y * horzdir.z * yoffset + horzdir.x * zoffset;
            double dx = x - cx;
            double dy = y - cy;
            double dz = z - cz;
            double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);

            while (Math.abs(cx - x) > Math.abs(dx / ratio)) {
                world.addParticle(ParticleTypes.CRIT /*.MYCELIUM*/, cx, cy, cz, 0.0D, 0.0D, 0.0D);
                cx += dx * 0.1 / ratio;
                cy += dy * 0.1 / ratio;
                cz += dz * 0.1 / ratio;
            }
        }
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeDouble(this.damage);
        buffer.writeInt(this.knockbackStrength);
        buffer.writeDouble(this.velocity);
        buffer.writeDouble(this.chargePercent);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.damage = additionalData.readDouble();
        this.knockbackStrength = additionalData.readInt();
        this.velocity = additionalData.readDouble();
        this.chargePercent = additionalData.readDouble();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}