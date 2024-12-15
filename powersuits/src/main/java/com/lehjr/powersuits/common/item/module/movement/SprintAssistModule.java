package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.common.base.Numina;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.MathUtils;
import com.lehjr.numina.common.utils.TagUtils;
import com.lehjr.powersuits.common.config.module.MovementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.event.MovementManager;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.serverbound.SetSprintAssistDoubleAndDrainServerBound;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.math.MathContext;

/**
 * Ported by leon on 10/18/16.
 */
public class SprintAssistModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            // Sprinting ------------------------------------------------------------------------------------------------------------------
            addBaseProperty(MPSConstants.SPRINT_ENERGY_CONSUMPTION,MovementModuleConfig.sprintAssistModuleSprintEnergyConsumptionBase, "RF");
            addTradeoffProperty(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_ENERGY_CONSUMPTION, MovementModuleConfig.sprintAssistModuleSprintEnergyConsumptionAssistMultiplier);
            addBaseProperty(MPSConstants.SPRINT_SPEED_MULTIPLIER, MovementModuleConfig.sprintAssistModuleSprintSpeedBase, "%");
            addTradeoffProperty(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_SPEED_MULTIPLIER, MovementModuleConfig.sprintAssistModuleSprintSpeedAssistMultiplier);

            // Sprint Food Compensation -------------------------------------------------------------------------------
            addBaseProperty(MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.sprintAssistModuleExhaustionCompensationBase, "%");
            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.SPRINT_ENERGY_CONSUMPTION, MovementModuleConfig.sprintAssistExhaustionCompensationEnergyConsumptionMultiplier);
            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.sprintAssistExhaustionCompensationCompensationMultiplier);


            // Walking --------------------------------------------------------------------------------------------------------------------
            addBaseProperty(MPSConstants.WALKING_ENERGY_CONSUMPTION, MovementModuleConfig.sprintAssistModuleWalkingEnergyBase, "RF");
            addTradeoffProperty(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_ENERGY_CONSUMPTION, MovementModuleConfig.sprintAssistModuleWalkingEnergyWalkingAssistanceMultiplier);
            addBaseProperty(MPSConstants.WALKING_SPEED_MULTIPLIER, MovementModuleConfig.sprintAssistModuleWalkingSpeedMultiplierBase, "%");
            addTradeoffProperty(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_SPEED_MULTIPLIER, MovementModuleConfig.sprintAssistModuleWalkingSpeedMultiplierWalkingAssistMultiplier);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.sprintAssistModuleIsAllowed;
        }


        /*
         * Testing shoes neither walkDistO or deltaMovement update on the server side, and deltaMovement often returns NaN
         */
        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack armorLegs) {
            // Normal:
            // ----------------------------------------
            // player speed .1 walking, .13 sprinting
            // ----------------------------------------
            //  1x Boost:
            // ----------------------------------------
            // 1.2 walking, .156 with
            // ----------------------------------------
            //  2x Boost:
            // ----------------------------------------
            // 0.14 walking, 0.182 sprinting
            //            NuminaLogger.logDebug("speed: " + player.getSpeed());
            //
            if (player.getAbilities().flying || player.isPassenger() || player.isFallFlying()  || player.isSwimming()) {
                onPlayerTickInactive(player, level, armorLegs);
                return;
            }

            if (level.isClientSide()) {
                double horzMovement = player.walkDist - player.walkDistO;
                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);

                if (horzMovement > 0.001) { // stop doing drain calculations or when player hasn't moved
                    if (player.isSprinting()) {
                        double exhaustion = Math.round(horzMovement * 100.0F) * 0.01;
                        double sprintCost = applyPropertyModifiers(MPSConstants.SPRINT_ENERGY_CONSUMPTION);
                        if (sprintCost < totalEnergy) {
                            double exhaustionComp = applyPropertyModifiers(MPSConstants.EXHAUSTION_COMPENSATION);
                            double sprintMultiplier = applyPropertyModifiers(MPSConstants.SPRINT_SPEED_MULTIPLIER) * 0.13;
                            setPlayerSpeed(sprintMultiplier, (int)(sprintCost * horzMovement) * 20);
                            player.getFoodData().addExhaustion((float) (-0.01 * exhaustion * exhaustionComp));
                            player.getAbilities().setFlyingSpeed(player.getSpeed() * 0.2f);
                        }
                    } else {
                        double walkCost = applyPropertyModifiers(MPSConstants.WALKING_ENERGY_CONSUMPTION);
                        if (walkCost < totalEnergy) {
                            double walkMultiplier = applyPropertyModifiers(MPSConstants.WALKING_SPEED_MULTIPLIER) * 0.1; // value * normalWalkingSpeed
                            setPlayerSpeed(walkMultiplier, (int) (walkCost * horzMovement));
                            player.getAbilities().setFlyingSpeed(player.getSpeed() * 0.2f);
                        }
                    }
                } else {
                    setPlayerSpeed(0, 0);
                }
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack itemStack) {
            setPlayerSpeed(0, 0);
        }

        void setPlayerSpeed(double value, int drainAmount) {
            MPSPackets.sendToServer(new SetSprintAssistDoubleAndDrainServerBound(value, drainAmount));
        }
    }
}
