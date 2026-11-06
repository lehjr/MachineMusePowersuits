package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.PlayerUtils;
import lehjr.powersuits.common.config.module.MovementModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class GliderModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY);
        }

        @Override
        public boolean onPlayerTickActive(Player player, Level level, ItemStack chestPlate, int moduleIndex) {
            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

            PlayerUtils.resetFloatKickTicks(player);
            boolean hasParachute = false;
            IModularItem iModularItem = chestPlate.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if(iModularItem != null) {
                hasParachute = iModularItem.isModuleOnline(MPSConstants.PARACHUTE_MODULE);
            }

            if(player.onGround()) {
                player.stopFallFlying();
            } else {
                if (!hasParachute) {
                    if(playerInput.forwardKey) {
                        player.tryToStartFallFlying();
                    }
                }
            }
            return false;
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.gliderModuleIsAllowed;
        }
    }
}
