package com.lehjr.powersuits.common.entity;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.registration.MPSEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SpinningBladeEntity extends ThrowableProjectile {
    public static final int SIZE = 24;
    public static final float DEGREES_FROM_RADIANS = (float) Math.toDegrees(1);




    public double damage;
    public ItemStack shootingItem = ItemStack.EMPTY;

    public SpinningBladeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
    }

    public SpinningBladeEntity(Level worldIn, LivingEntity shootingEntity) {
        super(MPSEntities.SPINNING_BLADE_ENTITY_TYPE.get(), shootingEntity, worldIn);
        this.setOwner(shootingEntity);
        this.setNoGravity(true);
        if (shootingEntity instanceof Player) {


            this.shootingItem = ((Player) shootingEntity).getInventory().getSelected();
            IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(this.shootingItem);

            damage = 0;
            if(mci != null) {
                int index = mci.findInstalledModule(MPSConstants.BLADE_LAUNCHER_MODULE);
                if (index >= 0) {
                    IPowerModule pm = mci.getStackInSlot(index).getCapability(NuminaCapabilities.Module.POWER_MODULE);
                    if(pm != null) {
                        damage = pm.applyPropertyModifiers(MPSConstants.BLADE_DAMAGE);
                    }
                }
            }
        }
        double speed = 1.0;
        double scale = 1;

        Vec3 direction = shootingEntity.getLookAngle().normalize();//
        this.setDeltaMovement(direction.multiply(speed, speed, speed));

        double r = 1;
        this.setPos(
            shootingEntity.getX(),
            shootingEntity.getY() + shootingEntity.getEyeHeight(),
            shootingEntity.getZ());


        this.setBoundingBox(new AABB(getX() - r, getY() - r, getZ() - r, getX() + r, getY() + r, getZ() + r));


        //        this.performShooting(level, player, hand, itemstack, getShootingPower(chargedprojectiles), 1.0F, null);
        //        shoot(shootingEntity, (float) speed, 1F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

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
            if (block instanceof IShearable && this.getOwner() instanceof Player) {
                IShearable target = (IShearable) block;
                if (target.isShearable((Player)getOwner(), this.shootingItem, world, result.getBlockPos()) && !world.isClientSide) {
                    // onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune)

                    List<ItemStack> drops = target.onSheared((Player) this.getOwner(), this.shootingItem, world, result.getBlockPos());//,
                    //                            EnchantmentHelper.getItemEnchantmentLevel(BuiltInRegistries.ENCHANTMENT.get(ResourceLocation.fromNamespaceAndPath("fortune")), this.shootingItem));
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
            } else { // Block hit was not IShearable
                this.discard();
            }
        } else if (hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hitResult).getEntity() != getOwner()) {
            EntityHitResult result = (EntityHitResult) hitResult;
            if (result.getEntity() instanceof IShearable) {
                IShearable target = (IShearable) result.getEntity();
                Entity entity = result.getEntity();
                //                if (target.isShearable(this.shootingItem, entity.level(), entity.blockPosition()) && this.getOwner() instanceof Player) {
                //                    List<ItemStack> drops = target.onSheared((Player) getOwner(), this.shootingItem, entity.level(),
                //                            entity.blockPosition(),
                //                            EnchantmentHelper.getItemEnchantmentLevel(BuiltInRegistries.ENCHANTMENT.get(ResourceLocation.fromNamespaceAndPath("fortune")), this.shootingItem));
                //
                //                    Random rand = new Random();
                //                    for (ItemStack drop : drops) {
                //                        ItemEntity ent = entity.spawnAtLocation(drop, 1.0F);
                //
                //                        ent.setDeltaMovement(ent.getDeltaMovement().add(
                //                                (rand.nextFloat() - rand.nextFloat()) * 0.1F,
                //                                rand.nextFloat() * 0.05,
                //                                (rand.nextFloat() - rand.nextFloat()) * 0.1F
                //                        ));
                //                    }
                //                }
            } else {
                result.getEntity().hurt(this.damageSources().thrown(this, getOwner()), (int) damage);
            }
        }
    }

    public int getMaxLifetime() {
        return 200;
    }
}
