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

package lehjr.powersuits.common.item.module.special;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;

public class MagnetModule extends AbstractPowerModule {
    public MagnetModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.SPECIAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.RADIUS, 1, "m");
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 5, "FE");
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 2000);
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 9, "m", 1, 0);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack stack) {
                int energyUSage = (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                if (ElectricItemUtils.getPlayerEnergy(player) > energyUSage) {
                    boolean isServerSide = !player.level.isClientSide;

                    if ((player.level.getGameTime() % 20) == 0 && isServerSide) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyUSage);
                    }
                    int range = (int) applyPropertyModifiers(MPSConstants.RADIUS);
                    Level world = player.level;
                    AABB bounds = player.getBoundingBox().inflate(range);

                    if (isServerSide) {
                        bounds.expandTowards(0.2000000029802322D, 0.2000000029802322D, 0.2000000029802322D);
                        if (stack.getDamageValue() >> 1 >= 7) {
                            List<Arrow> arrows = world.getEntitiesOfClass(Arrow.class, bounds);
                            for (Arrow arrow : arrows) {
                                if ((arrow.pickup == AbstractArrow.Pickup.ALLOWED) && (world.random.nextInt(6) == 0)) {
                                    ItemEntity replacement = new ItemEntity(world, arrow.getX(), arrow.getY(), arrow.getZ(), new ItemStack(Items.ARROW));
                                    world.addFreshEntity(replacement);
                                }
                                arrow.remove(Entity.RemovalReason.DISCARDED);
                            }
                        }
                    }

                    for (ItemEntity e : world.getEntitiesOfClass(ItemEntity.class, bounds)) {
                        if (e.isAlive() && !e.getItem().isEmpty() && !e.hasPickUpDelay()) {
                            if (isServerSide) {
                                double x = player.getX() - e.getX();
                                double y = player.getY() - e.getY();
                                double z = player.getZ() - e.getZ();

                                double length = Math.sqrt(x * x + y * y + z * z) * 0.75D;

                                x = x / length + player.getDeltaMovement().x * 22.0D;
                                y = y / length + player.getDeltaMovement().y / 22.0D;
                                z = z / length + player.getDeltaMovement().z * 22.0D;

                                e.setDeltaMovement(x, y, z);

                                e.hasImpulse = true;
                                if (e.horizontalCollision) {
                                    e.setDeltaMovement(e.getDeltaMovement().add(0, 1, 0));
                                }
                            } else if (world.random.nextInt(20) == 0) {
                                float pitch = 0.85F - world.random.nextFloat() * 3.0F / 10.0F;
                                world.playLocalSound(e.getX(), e.getY(), e.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.6F, pitch, true);
                            }
                        }
                    }
                }
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
}