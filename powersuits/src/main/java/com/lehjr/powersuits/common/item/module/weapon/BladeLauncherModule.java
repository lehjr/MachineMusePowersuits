package com.lehjr.powersuits.common.item.module.weapon;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.config.module.WeaponModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.entity.SpinningBladeEntity;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class BladeLauncherModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {

        public RightClickie(ItemStack module) {
            super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.bladeLauncherEnergyConsumption, "FE");
            addBaseProperty(MPSConstants.BLADE_DAMAGE, WeaponModuleConfig.bladeLauncherSpinningBladeDamage, "pt");
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            if (hand == InteractionHand.MAIN_HAND) {
                if (ElectricItemUtils.getPlayerEnergy(playerIn) > applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION)) {
                    playerIn.startUsingItem(hand);
                    return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStackIn);
                }
            }
            return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
        }

        @Override
        public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
            if (!worldIn.isClientSide) {
                int energyConsumption = getEnergyUsage();

                if (ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption) {
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, energyConsumption, false);
                    SpinningBladeEntity blade = new SpinningBladeEntity(worldIn, entityLiving);
                    worldIn.addFreshEntity(blade);
                }
            }
        }

        @Override
        public boolean isAllowed() {
            return WeaponModuleConfig.bladeLauncherIsAllowed;
        }

        @Override
        public int getEnergyUsage() {
            return (int) Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
        }

        @Override
        public int getTier() {
            return 1;
        }
    }
}
