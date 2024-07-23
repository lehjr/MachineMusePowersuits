package lehjr.powersuits.common.item.module.weapon;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.utils.MathUtils;
import lehjr.powersuits.common.config.WeaponModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.PlasmaBallEntity;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class PlasmaCannonModule extends AbstractPowerModule {

    public static class RightClickie extends RightClickModule {
        public RightClickie(ItemStack module) {
            super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_PER_TICK, WeaponModuleConfig.plasmaCannonEnergyPerTickBase, "FE");
            addBaseProperty(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, WeaponModuleConfig.plasmaCannonDamageAtFullChargeBase, "pt");
            addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.ENERGY_PER_TICK, WeaponModuleConfig.plasmaCannonEnergyPerTickAmperageMultiplier, "FE");
            addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, WeaponModuleConfig.plasmaCannonDamageAtFullChargeAmperageMultiplier, "pt");
            addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.ENERGY_PER_TICK, WeaponModuleConfig.plasmaCannonEnergyPerTickVoltageMultiplier, "FE");
            addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, WeaponModuleConfig.plasmaCannonExplosivenessVoltageMultiplier, MPSConstants.CREEPER);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            NuminaLogger.logDebug("use here() ");

            if (hand == InteractionHand.MAIN_HAND && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
                playerIn.startUsingItem(hand);
                return InteractionResultHolder.consume(itemStackIn);
            }
            return InteractionResultHolder.fail(itemStackIn);
        }

        @Override
        public void releaseUsing(@Nonnull ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
            int chargeTicks = (int) MathUtils.clampDouble(stack.getUseDuration() - timeLeft, 10, 50);

            NuminaLogger.logDebug("release using with charge ticks " + chargeTicks);

            if (!worldIn.isClientSide && entityLiving instanceof Player) {
                double chargePercent = chargeTicks * 0.02; // chargeticks/50
                double energyConsumption = getEnergyUsage() * chargePercent;
                Player player = (Player) entityLiving;
                if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
                    float explosiveness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS) * chargePercent);
                    float damagingness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE) * chargePercent);
                    PlasmaBallEntity plasmaBolt = new PlasmaBallEntity(worldIn, player, explosiveness, damagingness, (float) chargePercent);
                    if (worldIn.addFreshEntity(plasmaBolt)) {
                        HeatUtils.heatPlayer(player, energyConsumption / 5000F * chargePercent);
                        ElectricItemUtils.drainPlayerEnergy(player, (int) energyConsumption, false);
                    }
                }
            }
        }

        @Override
        public boolean isAllowed() {
            return WeaponModuleConfig.plasmaCannonIsAllowed;
        }

        @Override
        public int getEnergyUsage() {
            return (int) Math.round(applyPropertyModifiers(MPSConstants.ENERGY_PER_TICK));
        }
    }
}