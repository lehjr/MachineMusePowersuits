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

package lehjr.powersuits.common.item.module.weapon;


import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.WeaponModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

/**
 * Created by User: Andrew2448
 * 5:56 PM 6/14/13
 */
public class LightningModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        RightClickie rightClickie;

        public CapProvider(ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module);
        }

        public static class RightClickie extends RightClickModule {
            public RightClickie(ItemStack module) {
                super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.lightningSummonerEnergyConsumption, "FE");
                addBaseProperty(MPSConstants.HEAT_EMISSION, WeaponModuleConfig.lightningSummonerHeatEmission, "");
            }

            @Override
            public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
                if (hand == InteractionHand.MAIN_HAND) {
                    double energyConsumption = getEnergyUsage();
                    if (energyConsumption < ElectricItemUtils.getPlayerEnergy(playerIn)) {
                        if (!level.isClientSide()) {
                            double range = 64;

                            HitResult raytraceResult = rayTrace(level, playerIn, ClipContext.Fluid.SOURCE_ONLY, range);
                            if (raytraceResult != null && raytraceResult.getType() != HitResult.Type.MISS) {
                                BlockPos targetPos = new BlockPos((int)raytraceResult.getLocation().x, (int)raytraceResult.getLocation().y, (int)raytraceResult.getLocation().z);
                                if (level.canSeeSky(targetPos)) {
                                    ElectricItemUtils.drainPlayerEnergy(playerIn, energyConsumption, false);
                                    HeatUtils.heatPlayer(playerIn, applyPropertyModifiers(MPSConstants.HEAT_EMISSION));
                                    LightningBolt sparkie = EntityType.LIGHTNING_BOLT.create(level);

                                    if (sparkie != null) {
                                        sparkie.setCause((ServerPlayer) playerIn);
                                        sparkie.moveTo(Vec3.atBottomCenterOf(targetPos));
                                        sparkie.setCause(playerIn instanceof ServerPlayer ? (ServerPlayer)playerIn : null);
                                        level.addFreshEntity(sparkie);
                                    }
                                }
                            }
                        }
                        return InteractionResultHolder.success(itemStackIn);
                    }
                }
                return InteractionResultHolder.pass(itemStackIn);
            }

            @Override
            public boolean isAllowed() {
                return WeaponModuleConfig.lightningSummonerIsAllowed;
            }

            @Override
            public boolean isModuleOnline() {
                return true;
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public int getTier() {
                return 2;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
            return NuminaCapabilities.Module.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClickie));
        }
    }
}