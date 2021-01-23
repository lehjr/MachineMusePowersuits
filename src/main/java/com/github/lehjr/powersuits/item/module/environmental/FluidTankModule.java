package com.github.lehjr.powersuits.item.module.environmental;

import com.github.lehjr.numina.util.capabilities.IItemStackUpdate;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
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
            this.ticker = new Ticker(module, EnumModuleCategory.ENVIRONMENTAL, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true);
            this.ticker.addBaseProperty(MPSConstants.FLUID_TANK_SIZE, 20000);
            this.ticker.addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5);
            this.ticker.addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, 0.5, "%");
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
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config, boolean defBool) {
                super(module, category, target, config, defBool);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack item) {
                if (/*player.world.isRemote() &&*/ player.getEntityWorld().getGameTime() % 10 == 0 ) {
                    // we only have one tank, so index 0;
                    int maxFluid = fluidHandler.getTankCapacity(0);
                    int currentFluid = fluidHandler.getFluidInTank(0).getAmount();

                    // fill the tank
                    if (currentFluid < maxFluid) {
                        BlockPos pos = player.getPosition();
                        BlockState blockstate = player.world.getBlockState(pos);

                        // fill by being in water
                        if (player.isInWater() && player.world.getBlockState(pos).getBlock() != Blocks.BUBBLE_COLUMN) {
                            if (blockstate.getBlock() instanceof IBucketPickupHandler && blockstate.getFluidState().getFluid() == Fluids.WATER) {
                                Fluid fluid = ((IBucketPickupHandler) blockstate.getBlock()).pickupFluid(player.world, pos, blockstate);
                                FluidStack water = new FluidStack(fluid, 1000);
                                // only play sound if actually filling
                                if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                    player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                                }
                            }
                            // fill by being in the rain or bubble column
                        } else if (player.isInWaterRainOrBubbleColumn()) {
                            FluidStack water = new FluidStack(Fluids.WATER, 100);
                            if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
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
                            player.world.isRemote ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE)
                            .getAmount() * coolingFactor;

                    MuseHeatUtils.coolPlayer(player, coolAmount);
                    if (coolAmount > 0) {
                        player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            player.world.addOptionalParticle(ParticleTypes.SMOKE, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }
    }
}