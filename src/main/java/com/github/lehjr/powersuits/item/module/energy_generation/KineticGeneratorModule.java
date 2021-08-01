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

package com.github.lehjr.powersuits.item.module.energy_generation;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.github.lehjr.powersuits.event.MovementManager;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class KineticGeneratorModule extends AbstractPowerModule {
    public KineticGeneratorModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.ENERGY_GENERATION, EnumModuleTarget.TORSOONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_GENERATION, 2000);
                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.ENERGY_GENERATION, 6000, "FE");
                addBaseProperty(MPSConstants.MOVEMENT_RESISTANCE, 0.01F);
                addTradeoffProperty(MPSConstants.ENERGY_GENERATED, MPSConstants.MOVEMENT_RESISTANCE, 0.49F, "%");
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack itemStackIn) {
                if (player.abilities.flying || player.isPassenger() || player.isFallFlying() || !player.isOnGround())
                    onPlayerTickInactive(player, itemStackIn);

                // really hate running this check on every tick but needed for player speed adjustments
                if (ElectricItemUtils.getPlayerEnergy(player) < ElectricItemUtils.getMaxPlayerEnergy(player)) {
                    itemStackIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h->{
                        if(h instanceof IModularItem && !((IModularItem) h).isModuleOnline(MPSRegistryNames.SPRINT_ASSIST_MODULE_REGNAME));
                        // only fires if the sprint assist module isn't installed and active
                        MovementManager.INSTANCE.setMovementModifier(itemStackIn, 0, player);
                    });

                    // server side
                    if (!player.level.isClientSide &&
                            // every 20 ticks
                            (player.level.getGameTime() % 20) == 0 &&
                            // player not jumping, flying, or riding
                            player.isOnGround()) {
                        double distance = player.walkDist - player.walkDistO;
                        ElectricItemUtils.givePlayerEnergy(player, (int) (distance * 10 * applyPropertyModifiers(MPSConstants.ENERGY_GENERATION)));
                    }
                }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, ItemStack itemStackIn) {
                itemStackIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h->{
                    if (h instanceof IModularItem && !((IModularItem) h).isModuleOnline(MPSRegistryNames.SPRINT_ASSIST_MODULE_REGNAME)) {
                        // only fire if sprint assist module not installed.
                        MovementManager.INSTANCE.setMovementModifier(itemStackIn, 0, player);
                    }
                });
            }
        }
    }
}