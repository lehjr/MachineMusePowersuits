package com.lehjr.powersuits.common.item.module.environmental.cooling;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCodecs;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.HeatUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

/**
 * Created by Eximius88 on 1/17/14.
 */
public class CoolingSystemModule extends AbstractPowerModule {
    public static class FluidHandlerItemStackMPS extends FluidHandlerItemStack {
        /**
         * @param container The container itemStack, data is stored on it directly under a component.
         * @param tier
         */
        public FluidHandlerItemStackMPS(ItemStack container, int tier) {
            super(() -> NuminaCodecs.ITEM_FLUID_CODEC, container, getCapacity(tier));
        }

        /*
            returns The maximum capacity of this fluid tank. Set with config value based on tier
         */
        static int getCapacity(int tier) {
            return switch (tier) {
                // FIXME: set up proper config values
                case 1 -> 1;
                case 2 -> 2;
                case 3 -> 3;
                case 4 -> 4;
                default -> 0;
            };
        }

        // TODO: different fluids for different tier modules
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == Fluids.WATER;
        }
    }

    /*
     * Cooling system tier/types
     * -------------------------
     * 1) passive: bucket of water (no power consumption, just a trigger value)
     * 2) passive: tank of water (increased capacity, no power consumption, just a trigger value)
     * 3) active: tank and circulation pump (increased capacity, powered circulation pump, with a trigger value)
     * 4) becomes part of top tier thermal generator?
     */

    // one heat unit per 5 mB of water
    static final double coolingFactor = 1.0 / 5.0;

    public static class Ticker extends PlayerTickModule {
        int tier;

        public Ticker(@Nonnull ItemStack module, int tier) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
            this.tier = tier;
            switch (tier) {
                case 1: {
                    // FIXME: set up proper config values
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
                }
                case 2: {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
                }
                case 3: {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
                }
                case 4: {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
                }
            }
        }

        @Override
        public boolean isAllowed() {
            // FIXME: set up proper config values
            return switch (this.tier) {
                case 1 -> true;
                case 2 -> true;
                case 3 -> true;
                case 4 -> true;
                default -> false;
            };
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
            IFluidHandlerItem fluidHandler = getFluidHandler(getModule());
            if (fluidHandler == null) {
                return;
            }

            // Fill if player in some sort of water
            if (/*player.world.isRemote() &&*/ player.getCommandSenderWorld().getGameTime() % 10 == 0) {
                // we only have one tank, so index 0;
                int maxFluid = fluidHandler.getTankCapacity(0);
                int currentFluid = fluidHandler.getFluidInTank(0).getAmount();

                // fill the tank
                if (currentFluid < maxFluid) {
                    BlockPos pos = player.blockPosition();
                    BlockState blockstate = level.getBlockState(pos);

                    // fill by being in water
                    if (player.isInWater() && level.getBlockState(pos).getBlock() != Blocks.BUBBLE_COLUMN) {
                        if (blockstate.getBlock() instanceof BucketPickup && blockstate.getFluidState().getType() == Fluids.WATER) {
                            FluidActionResult pickup = FluidUtil.tryPickUpFluid(getModule(), player, level, pos, null); // fixme?
                            if (pickup.isSuccess()) {
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
            if ((currentHeat / maxHeat) >= applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {

                // cool from electric system first
                double coolAmount = Math.min(applyPropertyModifiers(MPSConstants.COOLING_BONUS), currentHeat);
                double energyUsage = coolAmount * applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                if (ElectricItemUtils.getPlayerEnergy(player) >= energyUsage) {
                    HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
                    currentHeat = HeatUtils.getPlayerHeat(player);
                    ElectricItemUtils.drainPlayerEnergy(player, (int) (energyUsage), false);

                    if (coolAmount > 0) {
                        level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }

                    // cool from liquid at 200 per bucket
                    coolAmount = fluidHandler.drain(
                        // adjust so cooling does not exceed cooling needed
                        (int) Math.min(1000, currentHeat / coolingFactor),
                        // only execute on server, simulate on client
                        level.isClientSide ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE).getAmount() * coolingFactor;
                    HeatUtils.coolPlayer(player, coolAmount);
                    if (coolAmount > 0) {
                        level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

        }

        static IFluidHandlerItem getFluidHandler(@Nonnull ItemStack module) {
            return module.getCapability(Capabilities.FluidHandler.ITEM);
        }
    }
}
