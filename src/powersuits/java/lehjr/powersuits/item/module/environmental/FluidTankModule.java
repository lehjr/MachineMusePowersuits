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

package lehjr.powersuits.item.module.environmental;

import lehjr.numina.util.capabilities.IItemStackUpdate;
import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.heat.MuseHeatUtils;
import lehjr.numina.util.nbt.MuseNBTUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;
        IFluidHandlerItem fluidHandler;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true) {{
                addBaseProperty(MPSConstants.FLUID_TANK_SIZE, 20000);
                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            }};
            this.fluidHandler = new ModuleTank((int)ticker.applyPropertyModifiers(MPSConstants.FLUID_TANK_SIZE));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
                return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> fluidHandler));
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class ModuleTank extends FluidTank implements IItemStackUpdate, IFluidTank, IFluidHandler, IFluidHandlerItem, INBTSerializable<CompoundNBT> {
            public ModuleTank(int capacity) {
                super(capacity);
                this.updateFromNBT();
            }

            @Override
            protected void onContentsChanged() {
                MuseNBTUtils.getModuleTag(module).put(FLUID_NBT_KEY, writeToNBT(new CompoundNBT()));
            }

            @Nonnull
            @Override
            public ItemStack getContainer() {
                return module;
            }

            @Override
            public void updateFromNBT() {
                CompoundNBT nbt = MuseNBTUtils.getModuleTag(module);
                if (nbt != null && nbt.contains(FLUID_NBT_KEY, Constants.NBT.TAG_COMPOUND)) {
                    this.deserializeNBT(nbt.getCompound(FLUID_NBT_KEY));
                }
            }

            @Override
            public CompoundNBT serializeNBT() {
                return this.writeToNBT(new CompoundNBT());
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                this.setFluid(FluidStack.loadFluidStackFromNBT(nbt));
            }
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
                super(module, category, target, config, defBool);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack item) {
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
                            if (blockstate.getBlock() instanceof IBucketPickupHandler && blockstate.getFluidState().getType() == Fluids.WATER) {
                                Fluid fluid = ((IBucketPickupHandler) blockstate.getBlock()).takeLiquid(player.level, pos, blockstate);
                                FluidStack water = new FluidStack(fluid, 1000);
                                // only play sound if actually filling
                                if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                    player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
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
                double currentHeat = MuseHeatUtils.getPlayerHeat(player);
                double maxHeat = MuseHeatUtils.getPlayerMaxHeat(player);
                if ((currentHeat / maxHeat) >= ticker.applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {

                    // cool 200 per bucket
                    double coolAmount = fluidHandler.drain(
                            // adjust so cooling does not exceed cooling needed
                            (int) Math.min(1000, currentHeat/coolingFactor),
                            // only execute on server, simulate on client
                            player.level.isClientSide ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE)
                            .getAmount() * coolingFactor;

                    MuseHeatUtils.coolPlayer(player, coolAmount);
                    if (coolAmount > 0) {
                        player.level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            player.level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }
    }
}