/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.client.control.PlayerMovementInputWrapper;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.event.MovementManager;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class JetPackModule extends AbstractPowerModule {
    public JetPackModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "RF/t");
                addBaseProperty(MPSConstants.JETPACK_THRUST, 0, "N");
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.ENERGY_CONSUMPTION, 15000);
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.JETPACK_THRUST, 0.16F);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack torso) {
                if (player.isInWater()) {
                    return;
                }

                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                boolean hasFlightControl = helmet.getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(m->
                        m.isModuleOnline(MPSRegistryNames.FLIGHT_CONTROL_MODULE)).orElse(false);
                double jetEnergy = 0;
                double thrust = 0;
                jetEnergy += applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                thrust += applyPropertyModifiers(MPSConstants.JETPACK_THRUST);

                if ((jetEnergy < ElectricItemUtils.getPlayerEnergy(player)) &&
                        ((hasFlightControl && thrust > 0) || (playerInput.jumpKey))) {
                        thrust = MovementManager.INSTANCE.thrust(player, thrust, hasFlightControl);

                        if(!player.level.isClientSide()) {
                            if ((player.level.getGameTime() % 5) == 0) {
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (thrust * jetEnergy * 5));
                            }
                        } else if (NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.JETPACK.get(), SoundSource.PLAYERS, (float) (thrust * 6.25), 1.0f, true);
                        }
                    } else {
                        onPlayerTickInactive(player, torso);
                    }
            }

            @Override
            public void onPlayerTickInactive(Player player, ItemStack item) {
                if (player.level.isClientSide && NuminaSettings.useSounds()) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK.get());
                }
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}