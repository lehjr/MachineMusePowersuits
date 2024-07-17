package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
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
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 10000, "FE");
            addBaseProperty(MPSConstants.BLINK_DRIVE_RANGE, 5, "m");
            addTradeoffProperty(MPSConstants.RANGE, MPSConstants.ENERGY_CONSUMPTION, 140000);
            addTradeoffProperty(MPSConstants.RANGE, MPSConstants.BLINK_DRIVE_RANGE, 95);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
            int range = (int) applyPropertyModifiers(MPSConstants.BLINK_DRIVE_RANGE);
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
            return true;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }
}
