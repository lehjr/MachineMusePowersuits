package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class JetPackModule extends AbstractPowerModule {
    public static class Ticker extends PlayerTickModule {
        public Ticker(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "RF/t");
            addBaseProperty(MPSConstants.JETPACK_THRUST, 0, "N");
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 15000);
            addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, 0.16F);
        }

        @Override
        public void onPlayerTickActive(Player player, ItemStack torso) {
            if (player.isInWater()) {
                return;
            }

            PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
            boolean hasFlightControl = false;
            ItemStack helmet = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.HEAD);
            IModularItem modularItem = helmet.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
            if(modularItem != null) {
                hasFlightControl = modularItem.isModuleOnline(MPSConstants.FLIGHT_CONTROL_MODULE);
            }

            double jetEnergy = 0;
            double thrust = 0;
            jetEnergy += applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            thrust += applyPropertyModifiers(MPSConstants.JETPACK_THRUST);

            if ((jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) &&
                    ((hasFlightControl && thrust > 0) || (playerInput.jumpKey))) {
                thrust = MovementManager.thrust(player, thrust, hasFlightControl);

                if(!player.level().isClientSide()) {
                    if ((player.level().getGameTime() % 5) == 0) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy * 5), false);
                    }
                } else if (NuminaClientConfig.useSounds) {
                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                }
            } else {
                onPlayerTickInactive(player, torso);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, ItemStack item) {
            if (player.level().isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
            }
        }
    }
}
