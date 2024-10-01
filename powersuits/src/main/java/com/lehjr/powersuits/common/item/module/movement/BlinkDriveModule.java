package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.PlayerUtils;
import com.lehjr.powersuits.common.config.module.MovementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nonnull;

public class BlinkDriveModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.blinkDriveModuleRangeBase, "FE");
            addBaseProperty(MPSConstants.RANGE, MovementModuleConfig.blinkDriveModuleRangeBase, "m");
            addTradeoffProperty(MPSConstants.RANGE, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.blinkDriveModuleEnergyConsumptionRangeMultiplier);
            addTradeoffProperty(MPSConstants.RANGE, MPSConstants.RANGE, MovementModuleConfig.blinkDriveModuleRangeMultiplier);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
            int range = (int) applyPropertyModifiers(MPSConstants.RANGE);
            int energyConsumption = getEnergyUsage();
            HitResult hitRayTrace = rayTrace(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY, range);
            if (hitRayTrace != null && hitRayTrace.getType() == HitResult.Type.BLOCK) {
                double distance = hitRayTrace.getLocation().distanceTo(playerIn.position());

                // adjust energy consumption for actual distance.NuminaLogger.logDebug("get energy usage after calculation: " + energyConsumption);
                energyConsumption = (int) (energyConsumption * (distance/range));
                if (ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption,  true) == energyConsumption) {
                    PlayerUtils.resetFloatKickTicks(playerIn);
                    ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption, false);
                    worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                    PlayerUtils.teleportEntity(playerIn, hitRayTrace);
                    return InteractionResultHolder.success(itemStackIn);
                }
            }
            return InteractionResultHolder.pass(itemStackIn);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.blinkDriveModuleIsAllowed;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }
}
