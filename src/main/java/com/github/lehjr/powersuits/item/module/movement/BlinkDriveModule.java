package com.github.lehjr.powersuits.item.module.movement;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.player.PlayerUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class BlinkDriveModule extends AbstractPowerModule {
    public BlinkDriveModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClickie;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, EnumModuleCategory.MOVEMENT, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClickie.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
            this.rightClickie.addBaseProperty(MPSConstants.BLINK_DRIVE_RANGE, 5, "m");
            this.rightClickie.addTradeoffProperty(MPSConstants.RANGE, MPSConstants.ENERGY_CONSUMPTION, 30000);
            this.rightClickie.addTradeoffProperty(MPSConstants.RANGE, MPSConstants.BLINK_DRIVE_RANGE, 59);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClickie));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                int range = (int) applyPropertyModifiers(MPSConstants.BLINK_DRIVE_RANGE);
                int energyConsumption = getEnergyUsage();

                RayTraceResult hitRayTrace = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY, range);
                if (hitRayTrace != null && hitRayTrace.getType() == RayTraceResult.Type.BLOCK) {
                    double distance = hitRayTrace.getHitVec().distanceTo(playerIn.getPositionVec());

                    // adjust energy consumption for actual distance.
                    energyConsumption = (int) (energyConsumption * (distance/range));

                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > energyConsumption) {
                        PlayerUtils.resetFloatKickTicks(playerIn);
                        int amountDrained = ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption);
                        worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                        PlayerUtils.teleportEntity(playerIn, hitRayTrace);
                        worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                        return ActionResult.resultSuccess(itemStackIn);
                    }
                }
                return ActionResult.resultPass(itemStackIn);
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }
    }
}
