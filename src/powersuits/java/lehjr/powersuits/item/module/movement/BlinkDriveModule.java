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

import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.numina.util.player.PlayerUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import lehjr.powersuits.item.module.armor.EnergyShieldModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class BlinkDriveModule extends AbstractPowerModule {
    public BlinkDriveModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, ModuleCategory.MOVEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.BLINK_DRIVE_ENERGY, 10000, "FE");
                addBaseProperty(MPSConstants.BLINK_DRIVE_RANGE, 5, "m");
                addTradeoffProperty(MPSConstants.RANGE, MPSConstants.BLINK_DRIVE_ENERGY, 30000);
                addTradeoffProperty(MPSConstants.RANGE, MPSConstants.BLINK_DRIVE_RANGE, 59);
            }};

            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult use(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                int range = (int) applyPropertyModifiers(MPSConstants.BLINK_DRIVE_RANGE);
                int energyConsumption = getEnergyUsage();

                RayTraceResult hitRayTrace = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY, range);
                if (hitRayTrace != null && hitRayTrace.getType() == RayTraceResult.Type.BLOCK) {
                    double distance = hitRayTrace.getLocation().distanceTo(playerIn.position());

                    // adjust energy consumption for actual distance.
                    energyConsumption = (int) (energyConsumption * (distance/range));

                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > energyConsumption) {
                        PlayerUtils.resetFloatKickTicks(playerIn);
                        int amountDrained = ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption);
                        worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                        PlayerUtils.teleportEntity(playerIn, hitRayTrace);
                        worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
                        return ActionResult.success(itemStackIn);
                    }
                }
                return ActionResult.pass(itemStackIn);
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.BLINK_DRIVE_ENERGY);
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