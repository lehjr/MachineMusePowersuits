package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class GliderModule extends AbstractPowerModule {
        public static class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module) {
                super(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack chestPlate) {
                Vec3 playerHorzFacing = player.getLookAngle();
                playerHorzFacing = new Vec3(playerHorzFacing.x, 0, playerHorzFacing.z);
                playerHorzFacing.normalize();
                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

                PlayerUtils.resetFloatKickTicks(player);
                boolean hasParachute = false;
                IModularItem iModularItem = chestPlate.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
                if(iModularItem != null) {
                    hasParachute = iModularItem.isModuleOnline(MPSConstants.PARACHUTE_MODULE);
                }

                if (player.isCrouching() && player.getDeltaMovement().y < 0 && (!hasParachute || playerInput.forwardKey)) {
                    Vec3 motion = player.getDeltaMovement();
                    if (motion.y < -0.1) {
                        double motionYchange = Math.min(0.08, -0.1 - motion.y);

                        player.setDeltaMovement(motion.add(
                                playerHorzFacing.x * motionYchange,
                                motionYchange,
                                playerHorzFacing.z * motionYchange
                        ));

                        // sprinting speed
                        float flySpeed = player.getAbilities().getFlyingSpeed() + 0.03f;
                        player.getAbilities().setFlyingSpeed(flySpeed);
                    }
                }
            }
        }
}