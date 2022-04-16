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

package lehjr.powersuits.item.module.special;

import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
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
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.SPECIAL, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.MAGNET_ENERGY, 0, "FE");
                addTradeoffProperty(MPSConstants.MAGNET_POWER, MPSConstants.MAGNET_ENERGY, 2000);
                addBaseProperty(MPSConstants.RADIUS, 5);
                addTradeoffProperty(MPSConstants.MAGNET_POWER, MPSConstants.RADIUS, 10);
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack stack) {
                int energyUSage = (int) applyPropertyModifiers(MPSConstants.MAGNET_ENERGY);

                if (ElectricItemUtils.getPlayerEnergy(player) > energyUSage) {
                    boolean isServerSide = !player.level.isClientSide;

                    if ((player.level.getGameTime() % 20) == 0 && isServerSide) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyUSage);
                    }
                    int range = (int) applyPropertyModifiers(MPSConstants.RADIUS);
                    World world = player.level;
                    AxisAlignedBB bounds = player.getBoundingBox().inflate(range);

                    if (isServerSide) {
                        bounds.expandTowards(0.2000000029802322D, 0.2000000029802322D, 0.2000000029802322D);
                        if (stack.getDamageValue() >> 1 >= 7) {
                            List<ArrowEntity> arrows = world.getEntitiesOfClass(ArrowEntity.class, bounds);
                            for (ArrowEntity arrow : arrows) {
                                if ((arrow.pickup == ArrowEntity.PickupStatus.ALLOWED) && (world.random.nextInt(6) == 0)) {
                                    ItemEntity replacement = new ItemEntity(world, arrow.getX(), arrow.getY(), arrow.getZ(), new ItemStack(Items.ARROW));
                                    world.addFreshEntity(replacement);
                                }
                                arrow.remove();
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
                                world.playLocalSound(e.getX(), e.getY(), e.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.6F, pitch, true);
                            }
                        }
                    }
                }
            }
        }
    }
}