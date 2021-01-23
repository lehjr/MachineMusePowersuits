package com.github.lehjr.powersuits.entity;

import com.github.lehjr.powersuits.basemod.MPSObjects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class PlasmaBallEntity extends ThrowableEntity implements IEntityAdditionalSpawnData {
    private static final DataParameter<Float> CHARGE_PERCENT = EntityDataManager.createKey(PlasmaBallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> EXPLOSIVENESS = EntityDataManager.createKey(PlasmaBallEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGINESS = EntityDataManager.createKey(PlasmaBallEntity.class, DataSerializers.FLOAT);

    public PlasmaBallEntity(EntityType<? extends PlasmaBallEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @param world  the world this spawns in
     * @param shootingEntity the shooter
     * @param finalExplosiveness explosiveness * chargePercent
     * @param finalDamaginess damaginess * chargePercent
     * @param chargePercent percent of charge in decimal form (0 - 1)
     */
    public PlasmaBallEntity(World world, LivingEntity shootingEntity, float finalExplosiveness, float finalDamaginess, float chargePercent) {
        super(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), world);
        this.setShooter(shootingEntity);

        this.dataManager.set(CHARGE_PERCENT, chargePercent);
        this.dataManager.set(EXPLOSIVENESS, finalExplosiveness);
        this.dataManager.set(DAMAGINESS, finalDamaginess);

        Vector3d direction = shootingEntity.getLookVec().normalize();
        double radius = chargePercent;
        double xoffset = 1.3f + radius - direction.y * shootingEntity.getEyeHeight();
        double yoffset = -.2;
        double zoffset = 0.3f;
        double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        double horzx = direction.x / horzScale;
        double horzz = direction.z / horzScale;
        this.setPosition(
                // x
                (shootingEntity.getPosX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
                // y
                (shootingEntity.getPosY() + shootingEntity.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
                //z
                (shootingEntity.getPosZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset)
        );

        this.setMotion(direction);
        this.setBoundingBox(new AxisAlignedBB(getPosX() - radius, getPosY() - radius, getPosZ()- radius, getPosX() + radius, getPosY() + radius, getPosZ() + radius));
    }

    @Override
    protected void registerData() {
        dataManager.register(CHARGE_PERCENT, 0F);
        dataManager.register(EXPLOSIVENESS, 0F);
        dataManager.register(DAMAGINESS, 0F);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.ticksExisted > this.getMaxLifetime()) {
            this.remove();
        }

        if (this.isInWater()) {
            this.remove();
            for (int i = 0; i <  getChargePercent() * 50F; ++i) {
                this.world.addParticle(ParticleTypes.FLAME,
                        this.getPosX() + Math.random() * 1,
                        this.getPosY() + Math.random() * 1,
                        this.getPosZ() + Math.random() * 0.1,
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
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }


    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravityVelocity() {
        return 0;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        switch (result.getType()) {
            case ENTITY:
                EntityRayTraceResult rayTraceResult = (EntityRayTraceResult) result;
                if (rayTraceResult.getEntity() != null && rayTraceResult.getEntity() != func_234616_v_()) {
                    rayTraceResult.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, func_234616_v_()), this.dataManager.get(DAMAGINESS));
                }
                break;
            case BLOCK:
                break;
            default:
                break;
        }
        if (!this.world.isRemote) { // Dist.SERVER
            boolean flag = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
            // FIXME: this is probably all wrong
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 3 * this.dataManager.get(EXPLOSIVENESS), flag ? Explosion.Mode.DESTROY : Explosion.Mode.BREAK);
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.world.addParticle(ParticleTypes.FLAME,
                    this.getPosX() + Math.random() * 0.1,
                    this.getPosY() + Math.random() * 0.1,
                    this.getPosZ() + Math.random() * 0.1,
                    0.0D, 0.0D, 0.0D);
        }
        if (!this.world.isRemote) {
            this.remove();
        }
    }

    public float getChargePercent() {
        return this.dataManager.get(CHARGE_PERCENT);
    }

    // FIXME!!! using data manager AND spawnData?


    /**
     * Called by the server when constructing the spawn packet.
     * Data should be added to the provided stream.
     *
     * @param buffer The packet data stream
     */
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeFloat(this.dataManager.get(CHARGE_PERCENT));
        buffer.writeFloat(this.dataManager.get(EXPLOSIVENESS));
        buffer.writeFloat(this.dataManager.get(DAMAGINESS));
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.dataManager.set(CHARGE_PERCENT, additionalData.readFloat());
        this.dataManager.set(EXPLOSIVENESS, additionalData.readFloat());
        this.dataManager.set(DAMAGINESS, additionalData.readFloat());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}