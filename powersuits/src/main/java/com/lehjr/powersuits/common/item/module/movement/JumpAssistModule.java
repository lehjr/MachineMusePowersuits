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
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "FE");
            //            addBasePropertyDouble(MPSModuleConstants.JUMP_ENERGY_CONSUMPTION, 0, "RF");
            //            put( "jumpAssist.jumpEnergyCon.base", 0.0D );

            addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, 250);
            //            addTradeoffPropertyDouble(MPSModuleConstants.POWER, MPSModuleConstants.JUMP_ENERGY_CONSUMPTION, 250);
            //            put( "jumpAssist.jumpEnergyCon.power.multiplier", 250.0D );

            addBaseProperty(MPSConstants.MULTIPLIER, 1, "%");
            //            addBasePropertyDouble(MPSModuleConstants.JUMP_MULTIPLIER, 1, "%");
            //            put( "jumpAssist.jumpBoost.base", 1.0D );

            addTradeoffProperty(MPSConstants.POWER, MPSConstants.MULTIPLIER, 4);
            //            addTradeoffPropertyDouble(MPSModuleConstants.POWER, MPSModuleConstants.JUMP_MULTIPLIER, 4);
            //            put( "jumpAssist.jumpBoost.power.multiplier", 4.0D );

            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.ENERGY_CONSUMPTION, 50);
            //            addTradeoffPropertyDouble(MPSModuleConstants.COMPENSATION, MPSModuleConstants.JUMP_ENERGY_CONSUMPTION, 50);
            //            put( "jumpAssist.jumpEnergyCon.compensation.multiplier", 50.0D );

            addBaseProperty(MPSConstants.FOOD_COMPENSATION, 0, "%");
            //            addBasePropertyDouble(MPSModuleConstants.JUMP_FOOD_COMPENSATION, 0, "%");
            //            put( "jumpAssist.jumpExhaustComp.base", 0.0D );

            addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.FOOD_COMPENSATION, 1);
            //            addTradeoffPropertyDouble(MPSModuleConstants.COMPENSATION, MPSModuleConstants.JUMP_FOOD_COMPENSATION, 1);
            //            put( "jumpAssist.jumpExhaustComp.compensation.multiplier", 1.0D );
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
                player.getAbilities().setFlyingSpeed(player.getSpeed() * .2f);
            } else {
                MovementManager.INSTANCE.setPlayerJumpTicks(player, 0);
            }
            PlayerUtils.resetFloatKickTicks(player);
        }
    }
}
