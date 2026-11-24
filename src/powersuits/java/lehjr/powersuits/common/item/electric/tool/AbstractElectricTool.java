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

package lehjr.powersuits.common.item.electric.tool;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.AdditionalInfo;
import lehjr.powersuits.common.capability.legacy.PowerFistLegacyCap;
import lehjr.powersuits.common.capability.tool.PowerFistCap;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class AbstractElectricTool extends DiggerItem {
    public AbstractElectricTool(Item.Properties properties) {
        super(0.0F,
                0.0F,
                new Tier() {
                    @Override
                    public int getUses() {
                        return 0;
                    }

                    @Override
                    public float getSpeed() {
                        return 0;
                    }

                    @Override
                    public float getAttackDamageBonus() {
                        return 0;
                    }

                    @Override
                    public int getLevel() {
                        return 0;
                    }

                    @Override
                    public int getEnchantmentValue() {
                        return 0;
                    }

                    @Override
                    public Ingredient getRepairIngredient() {
                        return Ingredient.EMPTY;
                    }
                },
                TagKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(Blocks.AIR)),
                properties);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (worldIn != null) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            AdditionalInfo.appendHoverText(stack, worldIn, tooltip, flagIn);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(stack.is(MPSItems.POWER_FIST_LEGACY.get())) {
            return new PowerFistLegacyCap(stack);
        }
        return new PowerFistCap(stack);
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    /**
     * Checks module first and falls back to default value of 72000
     * @param stack
     * @return
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        if(mci != null) {
            return Math.min(mci.getUseDuration(), 7200);
        }
        return 7200;
    }

    /**
     * Item.releaseUsing(ItemStack, Level, LivingEntity, int) (ItemStack, Level, LivingEntity)} for when the player releases but the full timer has not passed
     * @param stack
     * @param level
     * @param entityLiving
     * @param timeLeft
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        if(mci != null) {
//            NuminaLogger.logDebug("releaseUsing tool, timeleft: " + timeLeft);
            mci.releaseUsing(stack, level, entityLiving, timeLeft);
        }
    }

    /**
     * Item.finishUsingItem(ItemStack, Level, LivingEntity) for when the player releases and enough ticks have passed
     * @param stack
     * @param level
     * @param livingEntity
     * @return
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        if(mci != null) {
//            NuminaLogger.logDebug("finishUsing tool");
            return mci.finishUsingItem(stack, level, livingEntity);
        }
        return stack;
    }

    /** Durability bar for showing energy level ------------------------------------------------------------------ */
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).map(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() > 1).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double retVal = 1;
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
        if(energy != null) {
            retVal =  energy.getEnergyStored() * 13D / energy.getMaxEnergyStored();
        }
        return (int) retVal;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }
}