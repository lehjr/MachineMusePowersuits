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

package lehjr.powersuits.common.item.module.weapon;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.tags.TagUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class RailgunModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.RAILGUN_TOTAL_IMPULSE, 500, "Ns");
                addBaseProperty(MPSConstants.RAILGUN_ENERGY_COST, 5000, "FE");
                addBaseProperty(MPSConstants.RAILGUN_HEAT_EMISSION, 2, "");
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_TOTAL_IMPULSE, 2500);
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_ENERGY_COST, 25000);
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.RAILGUN_HEAT_EMISSION, 10);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        /**
         * using a ticking module to provide a timeout mechanic
         */
        class Ticker extends PlayerTickModule implements IRightClickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(Player player, @Nonnull ItemStack itemStackIn) {
                double timer = TagUtils.getModularItemDoubleOrZero(itemStackIn, MPSConstants.TIMER);
                if (timer > 0) {
                    TagUtils.setModularItemDoubleOrRemove(itemStackIn, MPSConstants.TIMER, timer - 1 > 0 ? timer - 1 : 0);
                }
            }

            @Override
            public InteractionResultHolder<ItemStack> use(ItemStack itemStack, Level level, Player player, InteractionHand hand) {
                if (hand == InteractionHand.MAIN_HAND) {
                    double range = 200;
                    double timer = TagUtils.getModularItemDoubleOrZero(itemStack, MPSConstants.TIMER);
                    double energyConsumption = getEnergyUsage();
                    if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption && timer == 0) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) energyConsumption, false);
                        TagUtils.setModularItemDoubleOrRemove(itemStack, MPSConstants.TIMER, 15);
                        HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.RAILGUN_HEAT_EMISSION));
                        HitResult hitResult = rayTrace(player, range);
                        if (hitResult != null) {
                            float damage = (float) (applyPropertyModifiers(MPSConstants.RAILGUN_TOTAL_IMPULSE) / 100.0);
                            double knockback = damage / 20.0;
                            Vec3 lookVec = player.getLookAngle();

                            switch (hitResult.getType()) {
                                case MISS:
                                    level.playSound(player, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                                    break;

                                case BLOCK:
                                    drawParticleStreamTo(player, level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                                    level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                                    break;

                                case ENTITY:
                                    drawParticleStreamTo(player, level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                                    level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                                    if(hitResult instanceof EntityHitResult entityHitResult) {
                                        Entity entity = entityHitResult.getEntity();
                                        if (entity instanceof LivingEntity livingEntity) {
                                            DamageSource damageSource = player.damageSources().playerAttack(player);
                                            if (livingEntity.hurt(damageSource, (int) damage)) {
                                                livingEntity.push(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
                                            }
                                        }
                                    }

                                    break;
                            }
                            player.push(-lookVec.x * knockback, Math.abs(-lookVec.y + 0.2f) * knockback, -lookVec.z * knockback);
                        }
                    }
                    player.startUsingItem(hand);
                    return InteractionResultHolder.success(itemStack);
                }
                return InteractionResultHolder.pass(itemStack);
            }

            @Override
            public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
                IRightClickModule.super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.RAILGUN_ENERGY_COST));
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }

    @Nullable
    public HitResult rayTrace(Player player, double attackRange) {
        float pPartialTicks = 1.0F;
        HitResult hitResult = null;

        if (player != null) {
            double pickRange = player.getPickRadius();
            Vec3 eyePositionVec = player.getEyePosition(pPartialTicks);
            double range;
            range = Math.max(pickRange, attackRange);
            pickRange = range;
            hitResult = player.pick(range, pPartialTicks, false);
            range *= range;
            if (hitResult.getType() != HitResult.Type.MISS) { // Add != MISS to ensure Attack Range is not clamped at the value of Reach Distance.
                range = hitResult.getLocation().distanceToSqr(eyePositionVec);
            }

            Vec3 viewVector = player.getViewVector(1.0F);
            Vec3 vec32 = eyePositionVec.add(viewVector.x * pickRange, viewVector.y * pickRange, viewVector.z * pickRange);
            AABB aabb = player.getBoundingBox().expandTowards(viewVector.scale(pickRange)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entityhitresult = ProjectileUtil.
                    getEntityHitResult(player, eyePositionVec, vec32, aabb,
                            (entity) -> !entity.isSpectator() && entity.isPickable(), range);

            if (entityhitresult != null && entityhitresult.getEntity() instanceof LivingEntity) {
                Vec3 entityHitLocation = entityhitresult.getLocation();
                double d2 = eyePositionVec.distanceToSqr(entityHitLocation);
                if (d2 < range) {
                    hitResult = entityhitresult;
                }
            }
        }
        return hitResult;
    }

    public void drawParticleStreamTo(Player source, Level level, double x, double y, double z) {
        Vec3 direction = source.getLookAngle().normalize();
        double xoffset = 1.3f;
        double yoffset = -.2;
        double zoffset = 0.3f;
        Vec3 horzdir = direction.normalize();
        horzdir = new Vec3(horzdir.x, 0, horzdir.z);
        horzdir = horzdir.normalize();
        double cx = source.position().x + direction.x * xoffset - direction.y * horzdir.x * yoffset - horzdir.z * zoffset;
        double cy = source.position().y + source.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset;
        double cz = source.position().z + direction.z * xoffset - direction.y * horzdir.z * yoffset + horzdir.x * zoffset;
        double dx = x - cx;
        double dy = y - cy;
        double dz = z - cz;
        double ratio = Math.sqrt(dx * dx + dy * dy + dz * dz);

        while (Math.abs(cx - x) > Math.abs(dx / ratio)) {
            level.addParticle(ParticleTypes.MYCELIUM, cx, cy, cz, 0.0D, 0.0D, 0.0D);
            cx += dx * 0.1 / ratio;
            cy += dy * 0.1 / ratio;
            cz += dz * 0.1 / ratio;
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 25;
    }
}