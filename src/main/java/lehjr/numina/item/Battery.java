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

package lehjr.numina.item;

import lehjr.numina.basemod.NuminaObjects;
import lehjr.numina.config.NuminaSettings;
import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.util.capabilities.energy.ForgeEnergyModuleWrapper;
import lehjr.numina.util.capabilities.energy.IEnergyWrapper;
import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.numina.util.string.AdditionalInfo;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Battery extends Item {
    /**
     * these values are only used during initialization to set up the config.
     * They can be overridden in the config.
     */
    protected int maxEnergy;
    protected int maxTransfer;
    protected final int tier;

    public Battery(int maxEnergy, int maxTransfer, int tier) {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(NuminaObjects.creativeTab)
                .defaultDurability(-1)
                .setNoRepair());
        this.maxEnergy = maxEnergy;
        this.maxTransfer = maxTransfer;
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World worldIn, List<ITextComponent> tooltips, ITooltipFlag flagIn) {
        if (worldIn != null) {
            super.appendHoverText(itemStack, worldIn, tooltips, flagIn);
            AdditionalInfo.appendHoverText(itemStack, worldIn, tooltips, flagIn);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPowerModule moduleCap;
        IEnergyWrapper energyStorage;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.moduleCap = new PowerModule(module, ModuleCategory.ENERGY_STORAGE, ModuleTarget.ALLITEMS, NuminaSettings::getModuleConfig) {
                @Override
                public int getTier() {
                    return tier;
                }

                @Override
                public String getModuleGroup() {
                    return "Battery";
                }

                {
                    addBaseProperty(NuminaConstants.MAX_ENERGY, maxEnergy, "FE");
                    addBaseProperty(NuminaConstants.MAX_TRAMSFER, maxTransfer, "FE");
                }};

            this.energyStorage = new ForgeEnergyModuleWrapper(
                    module,
                    (int) moduleCap.applyPropertyModifiers(NuminaConstants.MAX_ENERGY),
                    (int) moduleCap.applyPropertyModifiers(NuminaConstants.MAX_TRAMSFER)
            );
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityEnergy.ENERGY) {
                energyStorage.updateFromNBT();
                return CapabilityEnergy.ENERGY.orEmpty(cap, LazyOptional.of(() -> energyStorage));
            }
            if (cap == PowerModuleCapability.POWER_MODULE) {
                return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> moduleCap));
            }
            return LazyOptional.empty();
        }
    }

    @Override
    public void fillItemCategory(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (allowdedIn(group)) {
            ItemStack out = new ItemStack(this);
            CapProvider provider = new CapProvider(out);
            int maxEnergy = (int) provider.moduleCap.applyPropertyModifiers(NuminaConstants.MAX_ENERGY);
            provider.energyStorage.receiveEnergy(maxEnergy, false);
            items.add(out);
        }
    }

    @Override
    public boolean showDurabilityBar(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map(energyCap -> energyCap.getMaxEnergyStored() > 0).orElse(false);
    }

    @Override
    public double getDurabilityForDisplay(final ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map(energyCap -> 1 - energyCap.getEnergyStored() / (double) energyCap.getMaxEnergyStored()).orElse(1D);
    }
}
