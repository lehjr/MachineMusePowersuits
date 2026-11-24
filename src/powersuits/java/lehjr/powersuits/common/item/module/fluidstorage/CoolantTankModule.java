package lehjr.powersuits.common.item.module.fluidstorage;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.config.module.FluidStorageConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoolantTankModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }


    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        int tier;

        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        FluidHandlerItemStack fluidHandler;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.tier = getTier(module);

            this.ticker = new Ticker(module, tier);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });

            this.fluidHandler = new FluidHandlerItemStack(module, getCapacity(tier)) {
                @Override
                public boolean canFillFluidType(FluidStack fluid) {
                    return fluid.getFluid() == Fluids.WATER;
                }
            };
        }


        int getTier(ItemStack stack) {
            if(stack.is(MPSItems.COOLANT_TANK_MODULE_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.COOLANT_TANK_MODULE_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.COOLANT_TANK_MODULE_3.get())) {
                return 3;
            }
            if(stack.is(MPSItems.COOLANT_TANK_MODULE_4.get())) {
                return 4;
            }
            return 0;
        }

        static int getCapacity(int tier) {
            return switch (tier) {
                case 1 -> FluidStorageConfig.coolantTankModuleCapacity1;
                case 2 -> FluidStorageConfig.coolantTankModuleCapacity2;
                case 3 -> FluidStorageConfig.coolantTankModuleCapacity3;
                case 4 -> FluidStorageConfig.coolantTankModuleCapacity4;
                default -> 0;
            };
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        public <T> LazyOptional<T> getCapability(final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> capabilityPowerModule = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
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


    public static class Ticker extends PlayerTickModule {
        int tier;
        public Ticker(ItemStack module, int tier) {
            super(module, ModuleCategory.FLUID_STORAGE, ModuleTarget.TORSOONLY);
            this.tier = tier;
            switch (tier) {
                case 1 -> {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentBase1);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentMultiplier1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, FluidStorageConfig.coolantTankModuleEnergyConsumptionCoolingBonusMultiplier1, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, FluidStorageConfig.coolantTankModuleEnergyConsumptionMultiplier1, "RF/t");
                }
                case 2 -> {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentBase2);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentMultiplier2, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, FluidStorageConfig.coolantTankModuleEnergyConsumptionCoolingBonusMultiplier2, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, FluidStorageConfig.coolantTankModuleEnergyConsumptionMultiplier2, "RF/t");
                }
                case 3 -> {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentBase3);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentMultiplier3, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, FluidStorageConfig.coolantTankModuleEnergyConsumptionCoolingBonusMultiplier3, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, FluidStorageConfig.coolantTankModuleEnergyConsumptionMultiplier3, "RF/t");
                }
                case 4 -> {
                    addBaseProperty(MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentBase4);
                    addTradeoffProperty(MPSConstants.ACTIVATION_PERCENT, MPSConstants.HEAT_ACTIVATION_PERCENT, FluidStorageConfig.coolantTankModuleHeatActivationPercentMultiplier4, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.COOLING_BONUS, FluidStorageConfig.coolantTankModuleEnergyConsumptionCoolingBonusMultiplier4, "%");
                    addTradeoffProperty(MPSConstants.ENERGY_CONSUMPTION, MPSConstants.ENERGY_CONSUMPTION, FluidStorageConfig.coolantTankModuleEnergyConsumptionMultiplier4, "RF/t");
                }
            }
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public String getModuleGroup() {
            return "";
        }

        @Override
        public boolean isAllowed() {
            return switch (this.tier) {
                case 1 -> FluidStorageConfig.coolantTankModuleIsAllowed1;
                case 2 -> FluidStorageConfig.coolantTankModuleIsAllowed2;
                case 3 -> FluidStorageConfig.coolantTankModuleIsAllowed3;
                case 4 -> FluidStorageConfig.coolantTankModuleIsAllowed4;
                default -> false;
            };
        }

        // one heat unit per 5 mB of water
        static final double coolingFactor = 1.0 / 5.0;

        @Override
        public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
            boolean ret = false;
            IFluidHandlerItem fluidHandler = getModule().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, null).resolve().orElse(null);
            if (fluidHandler == null) {
                return false;
            }

            // Fill if player in some sort of water
            if (player.getCommandSenderWorld().getGameTime() % 10 == 0) {
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
                            FluidActionResult pickup = FluidUtil.tryPickUpFluid(getModule(), player, level, pos, Direction.UP);
                            if (pickup.isSuccess()) {
                                FluidStack water = new FluidStack(Fluids.WATER, 1000);
                                if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                                    ret = true;
                                    player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                                }
                            }
                        }
                        // fill by being in the rain or bubble column
                    } else if (player.isInWaterRainOrBubble()) {
                        FluidStack water = new FluidStack(Fluids.WATER, 100);
                        if (fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE) > 0) {
                            ret = true;
                            player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                        }
                    }
                }
            }

            // Apply cooling
            double currentHeat = HeatUtils.getPlayerHeat(player).currentHeat();
            double maxHeat = HeatUtils.getPlayerHeat(player).maxHeat();
            if ((currentHeat / maxHeat) >= applyPropertyModifiers(MPSConstants.HEAT_ACTIVATION_PERCENT)) {

                // cool from electric system first
                double coolAmount = Math.min(applyPropertyModifiers(MPSConstants.COOLING_BONUS), currentHeat);
                double energyUsage = coolAmount * applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                if (ElectricItemUtils.getPlayerEnergy(player) >= energyUsage) {
                    HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
                    currentHeat = HeatUtils.getPlayerHeat(player).currentHeat();
                    ElectricItemUtils.drainPlayerEnergy(player, (int) (energyUsage), false);

                    if (coolAmount > 0) {
                        ret = true;
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
                        ret = true;
                        level.playSound(player, player.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.MASTER, 1.0F, 1.0F);
                        for (int i = 0; i < 4; i++) {
                            level.addAlwaysVisibleParticle(ParticleTypes.SMOKE, player.getX(), player.getY() + 0.5, player.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
            return ret;
        }
    }

    //    @Override
    //    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> toolTips, TooltipFlag flags) {
    //        super.appendHoverText(itemStack, context, toolTips, flags);
    //        AdditionalInfo.appendHoverText(itemStack, context, toolTips, flags, Screen.hasShiftDown());
    //    }
}
