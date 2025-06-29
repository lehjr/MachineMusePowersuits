package com.lehjr.powersuits.common.item.module.energy.generation.heat;

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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */




/*

    TODO:
     -> merge cooling system into this, include the fluid handler
     -> generating/cooling depend on water...
     --> venting should be controllable, with peak efficiency at 50% of vent
 */
public class ThermalGeneratorModule extends AbstractPowerModule {
    public static class ThermalGeneratorTickingCapability extends PlayerTickModule {
        final int tier;
        public ThermalGeneratorTickingCapability(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.TORSOONLY);
            this.tier = tier;

            addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");



            // passive energy per heat per tick (offline heat generation)

            // active energy generation:
            // energy per heat per tick
            // water per heat per tick
            // total water amount

            //            switch(tier) {
            //                /* TODO:
            //                 * merge with cooling system.... add a fluid handler for active cooling...
            //                 *
            //                 */
            //
            //                case 1-> {
            //                    //            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
            //                    //            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
            //                }
            //                case 2-> {
            //                    //            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
            //                    //            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
            //                }
            //                case 3-> {
            //                    //            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
            //                    //            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
            //                }
            //                case 4-> {
            //                    //            addBaseProperty(MPSConstants.ENERGY_GENERATION, 250);
            //                    //            addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 250, "FE");
            //                }
            //            }

            //            case 1: {
            //                // FIXME: set up proper config values
            //                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            //                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
            //            }
            //            case 2: {
            //                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            //                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
            //            }
            //            case 3: {
            //                addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            //                addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
            //            }
            //            case 4: {
            //
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, 1, "%");
            //                addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, 40, "RF/t");
            //            }

        }

        @Override
        public boolean isAllowed() {
            //            switch (tier) {
            //                case 1-> {
            //
            //                }
            //                case 2-> {
            //
            //                }
            //                case 3-> {
            //
            //                }
            //                case 4-> {
            //
            //                }
            //            }
            return true;
            //            return false;
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @NotNull ItemStack item) {
            super.onPlayerTickActive(player, level, item);

            IFluidHandlerItem fluidHandler = getFluidHandler(getModule());
            if (fluidHandler == null) {
                return;
            }

            fillTank(fluidHandler,player, level);
            generateAndCoolPlayer(fluidHandler, player, level, item);
        }

        // one heat unit per 5 mB of water
        static final double coolingFactor = 1.0 / 5.0; // FIXME: use config val or trade off mechanic

        /**
         * FixMe... generate energy instead of using energy
         * @param fluidHandler
         * @param player
         * @param level
         * @param item
         */
        public void generateAndCoolPlayer(IFluidHandlerItem fluidHandler , Player player, Level level, @Nonnull ItemStack item) {








//            if (player.level().getGameTime() % 20 == 0) {
//                double currentHeat = HeatUtils.getPlayerHeat(player);
//                double maxHeat = HeatUtils.getPlayerMaxHeat(player);
//                double heatDrainAmount = 0D;
//                double energyGiveAmount = 0D;
//
//
//                // On fire
//                if (player.isOnFire()) {
//                    ElectricItemUtils.givePlayerEnergy(player, (int) (4 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
//                // over 200?    
//                } else if (currentHeat >= 200) {
//                    ElectricItemUtils.givePlayerEnergy(player, (int) (2 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)), false);
//                // over 50% heat
//                } else if ((currentHeat / maxHeat) >= 0.5) {
//                    ElectricItemUtils.givePlayerEnergy(player, (int) applyPropertyModifiers(MPSConstants.ENERGY_GENERATION), false);
//                }
//                ElectricItemUtils.givePlayerEnergy(player, energyGiveAmount, false);
//                HeatUtils.coolPlayer(player, heatDrainAmount);
//            }


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


        public void fillTank(@Nonnull IFluidHandlerItem fluidHandler, Player player, Level level) {
            // Fill if player in some sort of water
            if (/*!player.world.isRemote() &&*/ player.getCommandSenderWorld().getGameTime() % 10 == 0) {
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
        }

        static IFluidHandlerItem getFluidHandler(@Nonnull ItemStack module) {
            return module.getCapability(Capabilities.FluidHandler.ITEM);
        }
    }

    /**
     * Fluid Handler ------------------------------------------------------------------------------
     */
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
                case 1 -> 1000;
                case 2 -> 2000;
                case 3 -> 3000;
                case 4 -> 4000;
                default -> 0;
            };
        }

        // TODO: different fluids for different tier modules
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == Fluids.WATER;
        }
    }
}
