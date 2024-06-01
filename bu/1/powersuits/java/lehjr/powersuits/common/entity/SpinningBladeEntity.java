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

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.powersuits.common.base.MPSEntities;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class SpinningBladeEntity extends ThrowableProjectile {
    public static final int SIZE = 24;
    public double damage;
    public ItemStack shootingItem = ItemStack.EMPTY;

    public SpinningBladeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SpinningBladeEntity(Level worldIn, LivingEntity shootingEntity) {
        super(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), shootingEntity, worldIn);
        this.setOwner(shootingEntity);
        if (shootingEntity instanceof Player) {
            AtomicDouble atomicDamage = new AtomicDouble(0);

            this.shootingItem = ((Player) shootingEntity).getInventory().getSelected();
            this.shootingItem.getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iModeChangingItem -> {
                    iModeChangingItem.getActiveModule().getCapability(NuminaCapabilities.POWER_MODULE)
                            .ifPresent(m-> atomicDamage.getAndAdd(m.applyPropertyModifiers(MPSConstants.BLADE_DAMAGE)));
            });
            damage = atomicDamage.get();
        }
        Vec3 direction = shootingEntity.getLookAngle().normalize();
        double speed = 1.0;
        double scale = 1;
        this.setDeltaMovement(
                direction.x * speed,
                direction.y * speed,
                direction.z * speed
        );
        double r = 1;
//        double xoffset = 1.3f + r - direction.y * shootingEntity.getEyeHeight();
//        double yoffset = -.2;
//        double zoffset = 0.3f;
//        double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
//        double horzx = direction.x / horzScale;
//        double horzz = direction.z / horzScale;
//        this.setPos(
//                // x
//                (shootingEntity.getX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
//                // y
//                (shootingEntity.getY() + shootingEntity.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
//                // z
//                (shootingEntity.getZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset));

        this.setPos(
                shootingEntity.getX(),
                shootingEntity.getY() + shootingEntity.getEyeHeight(),
                shootingEntity.getZ());


        this.setBoundingBox(new AABB(getX() - r, getY() - r, getZ() - r, getX() + r, getY() + r, getZ() + r));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > this.getMaxLifetime()) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Level world = this.level();
            if (world == null) {
                return;
            }

            BlockHitResult result = (BlockHitResult) hitResult;
            Block block = world.getBlockState(result.getBlockPos()).getBlock();
            if (block instanceof IForgeShearable && this.getOwner() instanceof Player) {
                IForgeShearable target = (IForgeShearable) block;
                if (target.isShearable(this.shootingItem, world, result.getBlockPos()) && !world.isClientSide) {
                    // onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune)

                    List<ItemStack> drops = target.onSheared((Player) this.getOwner(), this.shootingItem, world, result.getBlockPos(),
                            EnchantmentHelper.getItemEnchantmentLevel(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("fortune")), this.shootingItem));
                    Random rand = new Random();

                    for (ItemStack stack : drops) {
                        float f = 0.7F;
                        double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        ItemEntity entityitem = new ItemEntity(world, result.getBlockPos().getX() + d, result.getBlockPos().getY() + d1, result.getBlockPos().getZ() + d2, stack);
                        entityitem.setPickUpDelay(10);
                        world.addFreshEntity(entityitem);
                    }
//                    if (this.shootingEntity instanceof Player) {
//                        ((Player) shootingEntity).addStat(StatList.getBlockStats(block), 1);
//                    }
                }
                world.destroyBlock(result.getBlockPos(), true);// Destroy block and drop item
            } else { // Block hit was not IForgeShearable
                this.discard();
            }
        } else if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hitResult).getEntity() != getOwner()) {
            EntityHitResult result = (EntityHitResult) hitResult;
            if (result.getEntity() instanceof IForgeShearable) {
                IForgeShearable target = (IForgeShearable) result.getEntity();
                Entity entity = result.getEntity();
                if (target.isShearable(this.shootingItem, entity.level(), entity.blockPosition()) && this.getOwner() instanceof Player) {
                    List<ItemStack> drops = target.onSheared((Player) getOwner(), this.shootingItem, entity.level(),
                            entity.blockPosition(),
                            EnchantmentHelper.getItemEnchantmentLevel(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("fortune")), this.shootingItem));

                    Random rand = new Random();
                    for (ItemStack drop : drops) {
                        ItemEntity ent = entity.spawnAtLocation(drop, 1.0F);

                        ent.setDeltaMovement(ent.getDeltaMovement().add(
                                (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                                rand.nextFloat() * 0.05,
                                (rand.nextFloat() - rand.nextFloat()) * 0.1F
                        ));
                    }
                }
            } else {
                result.getEntity().hurt(this.damageSources().thrown(this, getOwner()), (int) damage);
            }
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

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
