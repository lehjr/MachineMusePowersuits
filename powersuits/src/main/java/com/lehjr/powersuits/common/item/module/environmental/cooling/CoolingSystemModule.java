package com.lehjr.powersuits.common.item.module.environmental.cooling;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCodecs;
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




    // FIXME: how to mix fluid handler with Player Tick module while still passing both capability checks






    public static class FluidHandlerItemStackMPS extends FluidHandlerItemStack {
        /**
         * @param container     The container itemStack, data is stored on it directly under a component.
         * @param capacity      The maximum capacity of this fluid tank. Set with config value based on tier
         */
        public FluidHandlerItemStackMPS(ItemStack container, int capacity) {
            super(()-> NuminaCodecs.ITEM_FLUID_CODEC, container, capacity);
        }

        // TODO: different fluids for different tier modules
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == Fluids.WATER;
        }
    }
//
//
//
//    // TODO: merge tank code with cooling system code, but with separate fluid cap
//    public static class Ticker extends PlayerTickModule {
//        int tier;
//        public Ticker(@Nonnull ItemStack module, int tier) {
//            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
//            this.tier = tier;

    //        }
//
//        @Override
//        public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
//            double heatBefore = HeatUtils.getPlayerHeat(player);
//
//            HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
//            double cooling = heatBefore - HeatUtils.getPlayerHeat(player);
//            ElectricItemUtils.drainPlayerEnergy(player, (int) (cooling * applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION)), false);
//        }
//
//        @Override
//        public int getTier() {
//            return tier;
//        }
//    }
//
    static final String FLUID_NBT_KEY = "Fluid";
    // one heat unit per 5 mB of water
    static final double coolingFactor = 1.0/5.0;

    public static class Ticker2 extends PlayerTickModule {
        public Ticker2(@Nonnull ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
//            addBaseProperty(MPSConstants.FLUID_TANK_SIZE, 20000);
            addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
            addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
            IFluidHandlerItem fluidHandler = getFluidHandler(getModule());
            if (fluidHandler == null ) {
                return;
            }

            if (/*player.world.isRemote() &&*/ player.getCommandSenderWorld().getGameTime() % 10 == 0 ) {
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
            if ((currentHeat / maxHeat) >= applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {

                // cool 200 per bucket
                double coolAmount = fluidHandler.drain(
                                // adjust so cooling does not exceed cooling needed
                                (int) Math.min(1000, currentHeat/coolingFactor),
                                // only execute on server, simulate on client
                                level.isClientSide ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE)
                        .getAmount() * coolingFactor;

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