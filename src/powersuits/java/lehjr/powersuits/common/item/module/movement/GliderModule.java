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
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.player.PlayerUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class GliderModule extends AbstractPowerModule {
    public GliderModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;


        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.MOVEMENT, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.updateFromNBT();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, false);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack chestPlate) {
                Vector3d playerHorzFacing = player.getLookAngle();
                playerHorzFacing = new Vector3d(playerHorzFacing.x, 0, playerHorzFacing.z);
                playerHorzFacing.normalize();
                PlayerMovementInputWrapper.PlayerMovementInput playerInput = PlayerMovementInputWrapper.get(player);

                PlayerUtils.resetFloatKickTicks(player);
                boolean hasParachute = chestPlate.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .map(m-> m.isModuleOnline(MPSRegistryNames.PARACHUTE_MODULE)).orElse(false);

                if (player.isCrouching() && player.getDeltaMovement().y < 0 && (!hasParachute || playerInput.forwardKey)) {
                    Vector3d motion = player.getDeltaMovement();
                    if (motion.y < -0.1) {
                        double motionYchange = Math.min(0.08, -0.1 - motion.y);

                        player.setDeltaMovement(motion.add(
                                playerHorzFacing.x * motionYchange,
                                motionYchange,
                                playerHorzFacing.z * motionYchange
                        ));

                        // sprinting speed
                        player.flyingSpeed += 0.03f;
                    }
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