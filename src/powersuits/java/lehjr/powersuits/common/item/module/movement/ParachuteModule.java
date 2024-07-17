package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class ParachuteModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY);
        }

        @Override
        public void onPlayerTickActive (Player player, ItemStack itemStack){
            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            boolean hasGlider = false;
            PlayerUtils.resetFloatKickTicks(player);
            if (player.isCrouching() && player.getDeltaMovement().y < -0.1 && (!hasGlider || !playerInput.forwardKey)) {
                double totalVelocity = Math.sqrt(player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z + player.getDeltaMovement().y * player.getDeltaMovement().y);
                if (totalVelocity > 0) {
                    Vec3 motion = player.getDeltaMovement();
                    player.setDeltaMovement(
                            motion.x * 0.1 / totalVelocity,
                            motion.y * 0.1 / totalVelocity,
                            motion.z * 0.1 / totalVelocity);
                }
            }
        }
    }
}