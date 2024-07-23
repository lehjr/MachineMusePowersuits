package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class JumpAssistModule extends AbstractPowerModule {

    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
            addSimpleTradeoff(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, "FE", 0, 250, MPSConstants.MULTIPLIER, "%", 1, 4);
            addSimpleTradeoff(MPSConstants.COMPENSATION, MPSConstants.ENERGY_CONSUMPTION, "FE", 0, 50, MPSConstants.FOOD_COMPENSATION, "%", 0, 1);
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