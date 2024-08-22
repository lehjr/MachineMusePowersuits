package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.lehjr.numina.client.control.PlayerMovementInputWrapper;
import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.client.sound.MPSSoundDictionary;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.event.MovementManager;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
        public void onPlayerTickActive(Player player, Level level, ItemStack torso) {
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

                if(!level.isClientSide()) {
                    if ((level.getGameTime() % 5) == 0) {
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy * 5), false);
                    }
                } else if (NuminaClientConfig.useSounds) {
                    Musique.playerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                }
            } else {
                onPlayerTickInactive(player, level, torso);
            }
        }

        @Override
        public void onPlayerTickInactive(Player player, Level level, ItemStack item) {
            if (level.isClientSide && NuminaClientConfig.useSounds) {
                Musique.stopPlayerSound(player, MPSSoundDictionary.SOUND_EVENT_JETPACK.get());
            }
        }
    }
}
