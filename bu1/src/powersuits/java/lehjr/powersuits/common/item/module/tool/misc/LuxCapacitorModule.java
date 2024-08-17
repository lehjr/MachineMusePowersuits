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

package lehjr.powersuits.common.item.module.tool.misc;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.HeatUtils;
import lehjr.powersuits.common.config.ToolModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.LuxCapacitorEntity;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class LuxCapacitorModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.luxCapacitorEnergyConsumptionBase, "FE");
            addTradeoffProperty(MPSConstants.RED, MPSConstants.RED_HUE, 1.0, "%");
            addTradeoffProperty(MPSConstants.GREEN, MPSConstants.GREEN_HUE, 1.0, "%");
            addTradeoffProperty(MPSConstants.BLUE, MPSConstants.BLUE_HUE, 1.0, "%");
            addTradeoffProperty(MPSConstants.ALPHA, MPSConstants.OPACITY, 1.0, "%");
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@Nonnull ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            float energyConsumption = getEnergyUsage();
            if (ElectricItemUtils.getPlayerEnergy(playerIn) > energyConsumption) {
                if (!level.isClientSide) {
                    HeatUtils.heatPlayer(playerIn, energyConsumption / 500);
                    ElectricItemUtils.drainPlayerEnergy(playerIn, (int) energyConsumption, false);
                    float red = (float) applyPropertyModifiers(MPSConstants.RED_HUE);
                    float green = (float) applyPropertyModifiers(MPSConstants.GREEN_HUE);
                    float blue = (float) applyPropertyModifiers(MPSConstants.BLUE_HUE);
                    float alpha = (float) applyPropertyModifiers(MPSConstants.OPACITY);

                    LuxCapacitorEntity luxCapacitor = new LuxCapacitorEntity(level, playerIn, new Color(red, green, blue, alpha));
                    level.addFreshEntity(luxCapacitor);
                }
                return InteractionResultHolder.sidedSuccess(itemStackIn, level.isClientSide());
            }
            return InteractionResultHolder.pass(itemStackIn);
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public boolean isAllowed() {
            return ToolModuleConfig.luxCapacitorModuleIsAllowed;
        }
    }
}