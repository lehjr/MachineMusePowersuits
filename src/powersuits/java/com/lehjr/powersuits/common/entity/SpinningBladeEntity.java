package com.lehjr.powersuits.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class SpinningBladeEntity extends ThrowableProjectile {
    public SpinningBladeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    protected SpinningBladeEntity(EntityType<? extends ThrowableProjectile> entityType, double posX, double posY, double posZ, Level level) {
        super(entityType, posX, posY, posZ, level);
    }

    protected SpinningBladeEntity(EntityType<? extends ThrowableProjectile> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        super(p_37462_, p_37463_, p_37464_);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > this.getMaxLifetime()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0;
    }

    public int getMaxLifetime() {
        return 200;
    }
}
