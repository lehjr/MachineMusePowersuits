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

package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class FluidTankModule extends AbstractPowerModule {
    static final String FLUID_NBT_KEY = "Fluid";
    // one heat unit per 5 mB of water
    static final double coolingFactor = 1.0/5.0;

    public FluidTankModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        FluidHandlerItemStack fluidHandler;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true) {{
                addBaseProperty(MPSConstants.FLUID_TANK_SIZE, 20000);
                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });

            this.fluidHandler = new FluidHandlerItemStack(module, (int)ticker.applyPropertyModifiers(MPSConstants.FLUID_TANK_SIZE)) {
                @Override
                public boolean canFillFluidType(FluidStack fluid) {
                    return fluid.getFluid() == Fluids.WATER;
                }
            };
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
                super(module, category, target, config, defBool);
            }

            @Override
            public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
                if (/*player.world.isRemote() &&*/ player.getCommandSenderWorld().getGameTime() % 10 == 0 ) {
                    // we only have one tank, so index 0;
                    int maxFluid = fluidHandler.getTankCapacity(0);
                    int currentFluid = fluidHandler.getFluidInTank(0).getAmount();

                    // fill the tank
                    if (currentFluid < maxFluid) {
                        BlockPos pos = player.blockPosition();
                        BlockState blockstate = player.level.getBlockState(pos);

                        // fill by being in water
                        if (player.isInWater() && player.level.getBlockState(pos).getBlock() != Blocks.BUBBLE_COLUMN) {
                            if (blockstate.getBlock() instanceof BucketPickup && blockstate.getFluidState().getType() == Fluids.WATER) {
                                FluidActionResult pickup = FluidUtil.tryPickUpFluid(module, player, player.level, pos, null); // fixme?
                                if(pickup.isSuccess()) {
                                    FluidStack water = new FluidStack(Fluids.WATER, 1000);
                                    if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                        player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                                    }
                                }
                            }
                            // fill by being in the rain or bubble column
                        } else if (player.isInWaterRainOrBubble()) {
                            FluidStack water = new FluidStack(Fluids.WATER, 100);
                            if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                            }
                        }
                    }
                }

                // Apply cooling
                double currentHeat = HeatUtils.getPlayerHeat(player);
                double maxHeat = HeatUtils.getPlayerMaxHeat(player);
                if ((currentHeat / maxHeat) >= ticker.applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {

                    // cool 200 per bucket
                    double coolAmount = fluidHandler.drain(
                                    // adjust so cooling does not exceed cooling needed
                                    (int) Math.min(1000, currentHeat/coolingFactor),
                                    // only execute on server, simulate on client
                                    player.level.isClientSide ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE)
                            .getAmount() * coolingFactor;

                    HeatUtils.coolPlayer(player, coolAmount);
                    if (coolAmount > 0) {
                        player.level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            player.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> capabilityPowerModule = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (capabilityPowerModule.isPresent()) {
                return capabilityPowerModule;
            }

            final LazyOptional<T> fluidCapability = fluidHandler.getCapability(capability, side);
            if (fluidCapability.isPresent()) {
                return fluidCapability;
            }
            return LazyOptional.empty();
        }
    }
}