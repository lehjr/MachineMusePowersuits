package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.PlayerUtils;
import com.lehjr.powersuits.common.config.module.MovementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.event.MovementManager;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class JumpAssistModule extends AbstractPowerModule {

    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionBase, "FE");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionPowerMultiplier);
            addBaseProperty(MPSConstants.JUMP_BOOST, MovementModuleConfig.jumpAssistModuleJumpBoostBase, "%");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.JUMP_BOOST, MovementModuleConfig.jumpAssistModuleJumpBoostPowerMultiplier);
            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.jumpAssistModuleEnergyConsumptionCompensationMultiplier);
            addBaseProperty(MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.jumpAssistModuleExhaustionCompensationBase, "%");
            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.EXHAUSTION_COMPENSATION, MovementModuleConfig.jumpAssistModuleExhaustionCompensationCompensationMultiplier);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.jumpAssistModuleIsAllowed;
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, ItemStack item) {
            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            if (playerInput.jumpKey) {
                double multiplier = MovementManager.INSTANCE.getPlayerJumpMultiplier(player);
                if (multiplier > 0) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0, 0.15 * Math.min(multiplier, 1), 0));
                    MovementManager.INSTANCE.setPlayerJumpTicks(player, multiplier - 1);
                }
                PlayerUtils.resetFloatKickTicks(player);
//                player.getAbilities().setFlyingSpeed(player.getSpeed() * .2f); // FixMe? What was this for?
            } else {
                MovementManager.INSTANCE.setPlayerJumpTicks(player, 0);
            }
            PlayerUtils.resetFloatKickTicks(player);
        }
    }
}
