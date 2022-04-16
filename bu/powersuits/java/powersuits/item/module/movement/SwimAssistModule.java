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

package lehjr.powersuits.item.module.movement;

import lehjr.numina.config.NuminaSettings;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.util.client.control.PlayerMovementInputWrapper;
import lehjr.numina.util.client.sound.Musique;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.event.MovementManager;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class SwimAssistModule extends AbstractPowerModule {
    public SwimAssistModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.MOVEMENT, EnumModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_ENERGY, 1000, "FE");
                addTradeoffProperty(MPSConstants.THRUST, MPSConstants.SWIM_BOOST_AMOUNT, 1, "m/s");
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack itemStack) {
//                if (player.isSwimming()) { // doesn't work when strafing without "swimming"
                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);
                if((player.isInWater() && !player.isPassenger()) && (playerInput.strafeKey!=0 || playerInput.forwardKey || playerInput.jumpKey || player.isCrouching())) {
                    double moveRatio = 0;
                    if (playerInput.forwardKey) {
                        moveRatio += 1.0;
                    }
                    if (playerInput.strafeKey != 0) {
                        moveRatio += 1.0;
                    }
                    if (playerInput.jumpKey || player.isCrouching()) {
                        moveRatio += 0.2 * 0.2;
                    }
                    double swimAssistRate = applyPropertyModifiers(MPSConstants.SWIM_BOOST_AMOUNT) * 0.05 * moveRatio;
                    double swimEnergyConsumption = applyPropertyModifiers(MPSConstants.SWIM_ENERGY);

                    int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                    if (swimEnergyConsumption < playerEnergy) {
                        if (player.level.isClientSide && NuminaSettings.useSounds()) {
                            Musique.playerSound(player, MPSSoundDictionary.SWIM_ASSIST, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
                        } else if (
                            // every 20 ticks
                                (player.level.getGameTime() % 5) == 0) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (swimEnergyConsumption) * 5);
                        }
                        MovementManager.INSTANCE.thrust(player, swimAssistRate, true);
//                            setMovementModifier(getModuleStack(), swimAssistRate * 100000, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
                    } else {
                        onPlayerTickInactive(player, itemStack);
                    }
                } else {
                    onPlayerTickInactive(player, itemStack);
                }
            }

            @Override
            public void onPlayerTickInactive(Player player, @Nonnull ItemStack itemStack) {
                if (player.level.isClientSide && NuminaSettings.useSounds()) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SWIM_ASSIST);
                }
                SprintAssistModule.setMovementModifier(getModuleStack(), 0, ForgeMod.SWIM_SPEED.get(), ForgeMod.SWIM_SPEED.get().getDescriptionId());
            }
        }
    }
}